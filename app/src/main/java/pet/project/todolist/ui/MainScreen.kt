package pet.project.todolist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pet.project.todolist.R
import pet.project.todolist.ui.data.TaskImportance
import pet.project.todolist.ui.data.TodoItem
import pet.project.todolist.ui.theme.CustomTheme

@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel,
    modifier: Modifier = Modifier) {
    MainScreenTitle(mainScreenViewModel)
}

@Composable
fun MainScreenTitle(
    mainScreenViewModel: MainScreenViewModel,
    modifier: Modifier = Modifier) {
    val toDoItems = mainScreenViewModel.getTodoItems()
    var tasksDone = remember { mutableIntStateOf(toDoItems.size) }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(id = R.string.main_title),
            style = CustomTheme.typography.largeTitle,
            color = CustomTheme.colors.labelPrimary,
            modifier = Modifier.padding(start = 60.dp, top = 82.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 60.dp, end = 20.dp)
        ) {
            Text(text = stringResource(R.string.tasks_done,
                tasksDone.intValue),
                style = CustomTheme.typography.body,
                color = CustomTheme.colors.labelSecondary)
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_visibility_24),
                    contentDescription = "Visibility on",
                    tint = CustomTheme.colors.blue
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ElevatedCard(colors = CardDefaults.elevatedCardColors(
            containerColor = CustomTheme.colors.backSecondary,
            contentColor = CustomTheme.colors.labelSecondary
        ),
            modifier = Modifier.padding(horizontal = 8.dp)) {
            LazyColumn {
                items(toDoItems) {
                    ListItem(item = it)
                }
                item(1) {}
            }
        }
    }
}

@Composable
fun ListItem(
    item: TodoItem,
    modifier: Modifier = Modifier) {
    Row {
        Checkbox(checked = item.isMade,
            onCheckedChange = {
                item.isMade != item.isMade
            },
            colors = CheckboxDefaults.colors(
                checkedColor = CustomTheme.colors.green,
                checkmarkColor = CustomTheme.colors.backSecondary
            ))
        when (item.importance) {
            TaskImportance.LOW ->
                Icon(painter = painterResource(id = R.drawable.baseline_arrow_downward_24),
                    contentDescription = null,
                    tint = CustomTheme.colors.gray,
                    modifier = Modifier.padding(top = 12.dp))
            TaskImportance.HIGH ->
                Icon(painter = painterResource(id = R.drawable.baseline_priority_high_24),
                    contentDescription = null,
                    tint = CustomTheme.colors.red,
                    modifier = Modifier.padding(top = 12.dp))
            else -> {}
        }
        Column {
            Text(
                item.text,
                style = CustomTheme.typography.body,
                textDecoration = if (item.isMade) TextDecoration.LineThrough
                else TextDecoration.None,
                modifier = Modifier.padding(top = 12.dp)
            )
            if (item.deadline != null) {
                Text(item.deadline.toString(),
                    style = CustomTheme.typography.subhead,
                    color = CustomTheme.colors.labelTertiary)
            }
        }

    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun MainScreenPreview() {
    MainScreen(MainScreenViewModel())
}