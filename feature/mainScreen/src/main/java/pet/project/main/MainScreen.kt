package pet.project.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pet.project.domain.LoadingState
import pet.project.domain.TaskImportance
import pet.project.domain.ThemeSetting
import pet.project.domain.TodoItem
import pet.project.mainScreen.R
import pet.project.theme.AppTheme
import pet.project.theme.CustomTheme
import java.time.LocalDate

@Composable
fun MainScreen(
    msState: MainScreenUiState,
    showOrHideTasks: () -> Unit,
    checkBoxClick: (TodoItem) -> Unit,
    moveToTaskScreen: (String) -> Unit,
    moveToSettingsScreen: () -> Unit,
    updateList: () -> Unit,
    hideSnackbar: () -> Unit,
    modifier: Modifier = Modifier
) {
    val items = msState.itemsList
    val loadingStatus = msState.loadingState
    var showSnackbar = msState.showSnackbar
    val showCompleted = msState.showCompleted
    Box(modifier = modifier) {
        MainScreenTitle(
            toDoItems = items,
            showOrHideTasks = {
                showOrHideTasks()
            },
            onCheckedChange = {
                checkBoxClick(it)
            },
            onInfoClick = {
                moveToTaskScreen(it.id)
            },
            onSettingsClick = {
                moveToSettingsScreen()
            },
            showCompleted = showCompleted,
            loadingState = loadingStatus
        )
        FloatingActionButton(
            onClick = {
                moveToTaskScreen("default")
            },
            modifier = Modifier
                .padding(end = 16.dp, bottom = 64.dp)
                .align(Alignment.BottomEnd),
            shape = CircleShape,
            containerColor = CustomTheme.colors.blue,
            contentColor = CustomTheme.colors.white
        ) {
            Icon(Icons.Filled.Add, "Action button")
        }
        if (loadingStatus == LoadingState.ERROR && showSnackbar) {
            Snackbar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 48.dp),
                dismissAction = {
                    TextButton(onClick = { hideSnackbar() }) {
                        Text(text = "ЗАКРЫТЬ", style = CustomTheme.typography.button)
                    }
                },
                action = {
                    TextButton(onClick = { updateList() }) {
                        Text(text = "ОБНОВИТЬ", style = CustomTheme.typography.button)
                    }
                }
            ) {
                Text("Ошибка сети! Данные неактуальны")
            }
        }
    }
}

@Composable
private fun MainScreenTitle(
    toDoItems: List<TodoItem>,
    showOrHideTasks: () -> Unit,
    onCheckedChange: (TodoItem) -> Unit,
    onSettingsClick: () -> Unit,
    onInfoClick: (TodoItem) -> Unit,
    showCompleted: Boolean,
    loadingState: LoadingState
) {
    val tasksDone = toDoItems.count { it.isMade }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.main_title),
            style = CustomTheme.typography.largeTitle,
            color = CustomTheme.colors.labelPrimary,
            modifier = Modifier.padding(start = 60.dp, top = 82.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 60.dp, end = 8.dp)
        ) {
            Text(
                text = stringResource(
                    R.string.tasks_done,
                    tasksDone,
                    toDoItems.size
                ),
                style = CustomTheme.typography.body,
                color = CustomTheme.colors.labelSecondary
            )
            IconButton(onClick = {
                showOrHideTasks()
            }) {
                Icon(
                    painter = if (!showCompleted) {
                        painterResource(R.drawable.baseline_visibility_24)
                    } else {
                        painterResource(R.drawable.baseline_visibility_off_24)
                    },
                    contentDescription = "Visibility on",
                    tint = CustomTheme.colors.blue
                )
            }
            IconButton(onClick = { onSettingsClick() }) {
                Icon(Icons.Filled.Settings, contentDescription = "settings",
                    tint = CustomTheme.colors.blue)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ElevatedCard(
            colors = CardDefaults.elevatedCardColors(
                containerColor = CustomTheme.colors.backSecondary,
                contentColor = CustomTheme.colors.labelSecondary
            ),
            elevation = CardDefaults.elevatedCardElevation(
                2.dp
            ),
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.animateContentSize(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessHigh
                    )
                )
            ) {
                when (loadingState) {
                    LoadingState.LOADING -> item {
                        LoadingLayout()
                    }

                    else -> {
                        items(items = toDoItems, key = { it.id }) {
                            AnimatedListItem(showCompleted, it, onCheckedChange, onInfoClick)
                        }
                    }
                }
                item(1) {
                    Spacer(Modifier.height(100.dp))
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.AnimatedListItem(
    showCompleted: Boolean,
    item: TodoItem,
    onCheckedChange: (TodoItem) -> Unit,
    onInfoClick: (TodoItem) -> Unit
) {
    AnimatedVisibility(
        visible = showCompleted || !item.isMade,
        enter = expandVertically(animationSpec = tween(200)),
        exit = shrinkVertically(animationSpec = tween(200))
    ) {
        ListItem(
            item = item,
            onCheckedChange = onCheckedChange,
            onInfoClick = onInfoClick
        )
    }
}

@Composable
private fun LoadingLayout() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 12.dp)
    ) {
        Image(
            modifier = Modifier.size(400.dp),
            painter = painterResource(R.drawable.loading_img),
            alignment = Alignment.Center,
            contentDescription = null
        )
    }
}

@Composable
fun ListItem(
    item: TodoItem,
    onCheckedChange: (TodoItem) -> Unit,
    onInfoClick: (TodoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row {
        Checkbox(
            checked = item.isMade,
            onCheckedChange = {
                onCheckedChange(item)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = CustomTheme.colors.green,
                checkmarkColor = CustomTheme.colors.backSecondary,
                uncheckedColor = when (item.importance) {
                    TaskImportance.HIGH -> CustomTheme.colors.red
                    else -> CustomTheme.colors.gray
                }
            ),
            modifier = modifier
        )
        when (item.importance) {
            TaskImportance.LOW ->
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_downward_24),
                    contentDescription = null,
                    tint = if (!item.isMade) CustomTheme.colors.gray else CustomTheme.colors.green,
                    modifier = modifier.padding(top = 12.dp)
                )

            TaskImportance.HIGH ->
                Icon(
                    painter = painterResource(id = R.drawable.baseline_priority_high_24),
                    contentDescription = null,
                    tint = if (!item.isMade) CustomTheme.colors.red else CustomTheme.colors.green,
                    modifier = modifier.padding(top = 12.dp)
                )

            else -> {}
        }
        Column(
            modifier = Modifier.weight(0.66f)
        ) {
            Text(
                item.taskText,
                style = CustomTheme.typography.body,
                textDecoration = if (item.isMade) {
                    TextDecoration.LineThrough
                } else {
                    TextDecoration.None
                },
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier.padding(top = 12.dp)
            )
            if (item.deadline != null) {
                Text(
                    item.deadline.toString(),
                    style = CustomTheme.typography.subhead,
                    textDecoration = if (item.isMade) {
                        TextDecoration.LineThrough
                    } else {
                        TextDecoration.None
                    },
                    color = CustomTheme.colors.labelTertiary,
                    modifier = modifier
                )
            }
        }
        IconButton(onClick = {
            onInfoClick(item)
        }) {
            Icon(
                painter = painterResource(
                    id = R.drawable.baseline_info_outline_24
                ),
                contentDescription = null,
                modifier = modifier
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun MainScreenLightPreview() {
    AppTheme(themeSetting = ThemeSetting.LIGHT) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CustomTheme.colors.backPrimary
        ) {
            MainScreen(
                msState = MainScreenUiState(loadingState = LoadingState.SUCCESS),
                showOrHideTasks = { /*TODO*/ },
                checkBoxClick = {},
                moveToTaskScreen = {},
                moveToSettingsScreen = {},
                updateList = { /*TODO*/ },
                hideSnackbar = {})
        }
    }
}

@Composable
@Preview
private fun TodoItemLightPreview() {
    AppTheme(themeSetting = ThemeSetting.LIGHT) {
        val item = TodoItem(
            id = "0",
            taskText = "Сделать превью для итема",
            importance = TaskImportance.HIGH,
            deadline = LocalDate.parse("2024-06-29"),
            isMade = true
        )
        ElevatedCard(
            colors = CardDefaults.elevatedCardColors(
                containerColor = CustomTheme.colors.backSecondary,
                contentColor = CustomTheme.colors.labelSecondary
            ),
            elevation = CardDefaults.elevatedCardElevation(
                2.dp
            )
        ) {
            ListItem(
                item = item,
                onCheckedChange = {},
                onInfoClick = {}
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun MainScreenDarkPreview() {
    AppTheme(themeSetting = ThemeSetting.DARK) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CustomTheme.colors.backPrimary
        ) {
            MainScreen(
                msState = MainScreenUiState(loadingState = LoadingState.SUCCESS),
                showOrHideTasks = { /*TODO*/ },
                checkBoxClick = {},
                moveToTaskScreen = {},
                moveToSettingsScreen = {},
                updateList = { /*TODO*/ },
                hideSnackbar = {})
        }
    }
}

@Composable
@Preview
private fun TodoItemDarkPreview() {
    AppTheme(themeSetting = ThemeSetting.DARK) {
        val item = TodoItem(
            id = "0",
            taskText = "Сделать превью для итема",
            importance = TaskImportance.HIGH,
            deadline = LocalDate.parse("2024-06-29"),
            isMade = true
        )
        ElevatedCard(
            colors = CardDefaults.elevatedCardColors(
                containerColor = CustomTheme.colors.backSecondary,
                contentColor = CustomTheme.colors.labelSecondary
            ),
            elevation = CardDefaults.elevatedCardElevation(
                2.dp
            ),
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
        ) {
            ListItem(
                item = item,
                onCheckedChange = {},
                onInfoClick = {}
            )
        }
    }
}
