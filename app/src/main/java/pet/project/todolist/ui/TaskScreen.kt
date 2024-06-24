package pet.project.todolist.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pet.project.todolist.R
import pet.project.todolist.ui.data.TaskImportance
import pet.project.todolist.ui.data.TodoItem
import pet.project.todolist.ui.theme.CustomTheme
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

@Composable
fun TaskScreen(
    mainScreenViewModel: MainScreenViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val mContext = LocalContext.current

    val msState = mainScreenViewModel.msState.collectAsState()
    val items = msState.value.itemsList
    val item = msState.value.currentItem

    var menuExpanded by remember { mutableStateOf(false) }
    var switchState: Boolean by remember { mutableStateOf(false)}

    var inputText by remember {mutableStateOf(item?.text ?: "")}
    var importanceStatus by remember { mutableStateOf(item?.importance?.importanceString ?: "Нет") }

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDate = remember { mutableStateOf("") }

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        if (CustomTheme.colors.isLight) R.style.DatePickerLightTheme
                                   else R.style.DatePickerDarkTheme,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mYear-${if (mMonth <= 9) 0 else ""}${mMonth + 1}-$mDayOfMonth"
        },
        mYear,
        mMonth,
        mDay
    )

    Box {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        mainScreenViewModel.resetCurrentItem()
                        navController.popBackStack()
                    },
                    modifier = Modifier.padding(top = 16.dp, start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        tint = CustomTheme.colors.labelPrimary,
                        modifier = Modifier
                    )
                }
                TextButton(
                    enabled = inputText != "",
                    onClick = {
                        val deadline = if (mDate.value != "") LocalDate.parse(mDate.value) else null
                        val importance = when (importanceStatus) {
                            "Нет" -> TaskImportance.DEFAULT
                            "Низкий" -> TaskImportance.LOW
                            else -> TaskImportance.HIGH
                        }
                        if (item == null) {
                            mainScreenViewModel.addTodoItem(
                                TodoItem(
                                    id = (items.maxBy { it.id.toInt() }.id.toInt() + 1).toString(),
                                    text = inputText,
                                    importance = importance,
                                    deadline = deadline,
                                    creationDate = Date()
                                )
                            )
                        } else {
                            mainScreenViewModel.updateItemInList(
                                item, item.copy(
                                    text = inputText,
                                    importance = importance,
                                    deadline = deadline,
                                    changeDate = Date()
                                )
                            )
                        }
                        navController.popBackStack()
                    },
                    modifier = Modifier.padding(end = 4.dp, top = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.save),
                        style = CustomTheme.typography.button,
                        color = if (inputText != "") CustomTheme.colors.blue
                        else CustomTheme.colors.gray,
                        modifier = Modifier
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            Column(
                modifier = Modifier.verticalScroll(
                    rememberScrollState()
                ).weight(1f, fill = false)
            ) {
                ElevatedCard(
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = CustomTheme.colors.backSecondary,
                        contentColor = CustomTheme.colors.labelSecondary
                    ),
                    elevation = CardDefaults.elevatedCardElevation(
                        2.dp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    TextField(
                        value = inputText,
                        onValueChange = {
                            inputText = it
                        },
                        minLines = 3,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = CustomTheme.colors.backSecondary,
                            focusedContainerColor = CustomTheme.colors.backSecondary,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),

                        placeholder = {
                            Text(
                                "Что надо сделать...",
                                color = CustomTheme.colors.gray,
                                style = CustomTheme.typography.body
                            )
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Box {
                    Column(modifier = Modifier
                        .padding(start = 16.dp, top = 12.dp)
                        .clickable {
                            menuExpanded = true
                        }) {
                        Text(
                            text = "Важность",
                            style = CustomTheme.typography.body,
                            color = CustomTheme.colors.labelPrimary
                        )
                        Text(
                            text = importanceStatus,
                            style = CustomTheme.typography.subhead,
                            color = CustomTheme.colors.gray
                        )
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                        modifier = Modifier.padding(start = 8.dp, bottom = 16.dp)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Нет") },
                            onClick = {
                                importanceStatus = "Нет"
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Низкий") },
                            onClick = {
                                importanceStatus = "Низкий"
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Высокий", color = CustomTheme.colors.red) },
                            onClick = {
                                importanceStatus = "Высокий"
                                menuExpanded = false
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(
                                        id = R.drawable.baseline_priority_high_24
                                    ),
                                    contentDescription = null,
                                    tint = CustomTheme.colors.red
                                )
                            }
                        )
                    }
                }
                HorizontalDivider(
                    thickness = 2.dp,
                    color = CustomTheme.colors.supportSeparator,
                    modifier = Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text(
                            text = "Сделать до",
                            style = CustomTheme.typography.body,
                            color = CustomTheme.colors.labelPrimary,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(
                            text = mDate.value,
                            style = CustomTheme.typography.subhead,
                            color = CustomTheme.colors.blue
                        )
                    }
                    Switch(
                        checked = switchState,
                        onCheckedChange = {
                            switchState = !switchState
                            if (switchState) {
                                mDatePickerDialog.show()
                            }
                        },
                        colors = SwitchDefaults.colors(
                            uncheckedThumbColor = CustomTheme.colors.white,
                            uncheckedBorderColor = Color.Transparent,
                            uncheckedTrackColor = CustomTheme.colors.grayLight,
                            checkedBorderColor = Color.Transparent,
                            checkedThumbColor = CustomTheme.colors.blue,
                            checkedTrackColor = CustomTheme.colors.grayLight
                        ),
                        modifier = Modifier
                            .scale(0.75f)
                            .padding(end = 12.dp, top = 8.dp)
                    )
                }
                HorizontalDivider(
                    thickness = 2.dp,
                    color = CustomTheme.colors.supportSeparator,
                    modifier = Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(start = 12.dp, top = 16.dp)
                        .clickable(enabled = item != null) {
                            mainScreenViewModel.removeTodoItem(item!!)
                            navController.popBackStack()
                        }
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Delete item",
                        tint = if (item != null) CustomTheme.colors.red
                        else CustomTheme.colors.gray
                    )
                    Text(
                        text = "Удалить",
                        color = if (item != null) CustomTheme.colors.red
                        else CustomTheme.colors.gray,
                        style = CustomTheme.typography.body,
                        modifier = Modifier.padding(start = 4.dp, top = 3.dp)
                    )
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TaskScreenPreview() {
    CustomTheme(darkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CustomTheme.colors.backPrimary
        ) {
            TaskScreen(MainScreenViewModel(), rememberNavController())
        }
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TaskScreenDarkPreview() {
    CustomTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CustomTheme.colors.backPrimary
        ) {
            TaskScreen(MainScreenViewModel(), rememberNavController())
        }
    }
}