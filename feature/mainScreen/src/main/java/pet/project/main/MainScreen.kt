package pet.project.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    moveToInfoScreen: () -> Unit,
    updateList: () -> Unit,
    hideSnackbar: () -> Unit,
    onDelete: (itemId: String) -> Unit,
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
            onInfoScreenClick = {
                moveToInfoScreen()
            },
            onInfoClick = {
                moveToTaskScreen(it.id)
            },
            onSettingsClick = {
                moveToSettingsScreen()
            },
            showCompleted = showCompleted,
            loadingState = loadingStatus,
            onDelete = onDelete
        )
        FloatingActionButton(
            onClick = {
                moveToTaskScreen("default")
            },
            modifier = Modifier
                .padding(end = 16.dp, bottom = 64.dp)
                .align(Alignment.BottomEnd),
            shape = CircleShape,
            containerColor = CustomTheme.colors.yellow,
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
    onInfoScreenClick: () -> Unit,
    onInfoClick: (TodoItem) -> Unit,
    onDelete: (itemId: String) -> Unit,
    showCompleted: Boolean,
    loadingState: LoadingState
) {
    val tasksDone = toDoItems.count { it.isMade }
    Box {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Crossfade(targetState = showCompleted, label = "Иконка обезьяны") { showCompleted ->
                    if (showCompleted) {
                        Image(
                            painter = painterResource(R.drawable.monkey),
                            contentDescription = "Нажми чтобы скрыть выполненные дела",
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 72.dp)
                                .size(92.dp).clip(CircleShape).clickable {
                                    showOrHideTasks()
                                }
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.monkey_with_tongue),
                            contentDescription = "Нажми чтобы показать выполненные дела",
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 72.dp)
                                .size(92.dp).clip(CircleShape).clickable {
                                    showOrHideTasks()
                                }
                        )
                    }
                }
                Column {
                    Text(
                        text = stringResource(id = R.string.main_title),
                        style = CustomTheme.typography.largeTitle,
                        color = CustomTheme.colors.labelPrimary,
                        modifier = Modifier.padding(top = 82.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                    ) {
                        Text(
                            text = stringResource(
                                R.string.tasks_done,
                                tasksDone,
                                toDoItems.size
                            ),
                            style = CustomTheme.typography.body,
                            color = CustomTheme.colors.labelSecondary,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        IconButton(
                            onClick = { onSettingsClick() },
                            modifier = Modifier
                                .padding(start = 24.dp)
                                .weight(0.25f)
                        ) {
                            Icon(
                                Icons.Filled.Settings,
                                contentDescription = "Экран настроек",
                                tint = CustomTheme.colors.yellow
                            )
                        }
                        IconButton(
                            onClick = { onInfoScreenClick() },
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .weight(0.25f)
                        ) {
                            Icon(
                                Icons.Filled.Info,
                                contentDescription = "Экран информации",
                                tint = CustomTheme.colors.yellow
                            )
                        }
                    }
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
                                AnimatedListItem(
                                    showCompleted = showCompleted,
                                    item = it,
                                    onCheckedChange = onCheckedChange,
                                    onInfoClick = onInfoClick,
                                    onDelete = onDelete
                                )
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> CustomTheme.colors.green
        SwipeToDismissBoxValue.EndToStart -> CustomTheme.colors.red
        SwipeToDismissBoxValue.Settled -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd) {
            Icon(Icons.Filled.Check, contentDescription = null, tint = CustomTheme.colors.white)
        }
        Spacer(modifier = Modifier)
        if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
            Icon(Icons.Filled.Delete, contentDescription = null, tint = CustomTheme.colors.white)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColumnScope.AnimatedListItem(
    showCompleted: Boolean,
    item: TodoItem,
    onDelete: (itemId: String) -> Unit,
    onCheckedChange: (TodoItem) -> Unit,
    onInfoClick: (TodoItem) -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            return@rememberSwipeToDismissBoxState when (it) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    onCheckedChange(item)
                    false
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    onDelete(item.id)
                    true
                }

                SwipeToDismissBoxValue.Settled -> false
            }
        },
        positionalThreshold = { it * .25f }
    )

    AnimatedVisibility(
        visible = showCompleted || !item.isMade,
        enter = expandVertically(animationSpec = tween(200)),
        exit = shrinkVertically(animationSpec = tween(200))
    ) {
        SwipeToDismissBox(
            state = dismissState,
            backgroundContent = { DismissBackground(dismissState = dismissState) },
            content = {
                ListItem(
                    item = item,
                    onCheckedChange = onCheckedChange,
                    onInfoClick = onInfoClick,
                    modifier = Modifier.background(CustomTheme.colors.backSecondary)
                )
            }
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
    Row(
        modifier = modifier
    ) {
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
                    contentDescription = "Низкий приоритет",
                    tint = if (!item.isMade) CustomTheme.colors.gray else CustomTheme.colors.green,
                    modifier = modifier.padding(top = 12.dp)
                )

            TaskImportance.HIGH ->
                Icon(
                    painter = painterResource(id = R.drawable.baseline_priority_high_24),
                    contentDescription = "Высокий приоритет",
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
                contentDescription = "Редактировать задачу",
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
                moveToInfoScreen = {},
                moveToTaskScreen = {},
                moveToSettingsScreen = {},
                updateList = { /*TODO*/ },
                hideSnackbar = {},
                onDelete = {}
            )
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
                moveToInfoScreen = {},
                moveToTaskScreen = {},
                moveToSettingsScreen = {},
                updateList = { /*TODO*/ },
                hideSnackbar = {},
                onDelete = {}
            )
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
