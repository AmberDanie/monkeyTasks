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
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pet.project.todolist.R
import pet.project.todolist.data.TaskImportance
import pet.project.todolist.data.TodoItem
import pet.project.todolist.ui.theme.CustomTheme
import pet.project.todolist.viewmodels.MainScreenViewModel
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

    val msState by mainScreenViewModel.msState.collectAsState()
    val items = msState.itemsList
    val item = msState.currentItem

    var menuExpanded by remember { mutableStateOf(false) }
    var switchState: Boolean by remember { mutableStateOf(false) }

    var inputText by remember { mutableStateOf(item?.text ?: "") }
    var importanceStatus by remember {
        mutableIntStateOf(
            item?.importance?.importanceString ?: R.string.Default
        )
    }

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    var mDate by remember { mutableStateOf("") }

    val mDatePickerDialog = DatePickerDialog(
        /* context = */ mContext,
        /* themeResId = */ if (CustomTheme.colors.isLight) R.style.DatePickerLightTheme
                                                       else R.style.DatePickerDarkTheme,
        /* listener = */ { _: DatePicker, year: Int, month: Int, day: Int ->
            mDate = "$year-${if (month <= 8) 0 else ""}" +
                    "${month + 1}-${if (day <= 9) 0 else ""}$day"

        },
        /* year = */ mYear,
        /* monthOfYear = */ mMonth,
        /* dayOfMonth = */ mDay
    )

    Column {
        TaskTitle(
            onClose = {
                mainScreenViewModel.resetCurrentItem()
                navController.popBackStack()
            },
            onAccept = {
                val deadline = if (mDate != "") LocalDate.parse(mDate)
                    else if (switchState) null
                    else if (item?.deadline != null) item.deadline
                    else null
                val importance = when (importanceStatus) {
                    R.string.Default -> TaskImportance.DEFAULT
                     R.string.Low -> TaskImportance.LOW
                    else -> TaskImportance.HIGH
                }
                if (item == null) {
                    mainScreenViewModel.addTodoItem(
                        TodoItem(
                            id = if (items.isNotEmpty())
                                    (items.maxBy { it.id.toInt() }.id.toInt() + 1).toString()
                                 else "0",
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
            inputText = inputText
        )
        Spacer(Modifier.height(24.dp))
        Column(
            modifier = modifier
                .verticalScroll(
                    rememberScrollState()
                )
                .weight(1f, fill = false)
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
                            stringResource(R.string.task_placeholder),
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
                        text = stringResource(R.string.Importance),
                        style = CustomTheme.typography.body,
                        color = CustomTheme.colors.labelPrimary
                    )
                    Text(
                        text = stringResource(importanceStatus),
                        style = CustomTheme.typography.subhead,
                        color = CustomTheme.colors.gray
                    )
                }
                TaskDropDownMenu(
                    menuExpanded = menuExpanded,
                    onDismiss = { menuExpanded = false },
                    onClick = {
                        importanceStatus = it
                        menuExpanded = false
                    }
                )
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
                        text = stringResource(R.string.Deadline_title),
                        style = CustomTheme.typography.body,
                        color = CustomTheme.colors.labelPrimary,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = item?.deadline?.toString() ?: mDate,
                        textDecoration = if (switchState && item?.deadline != null) TextDecoration.LineThrough
                                                          else TextDecoration.None,
                        style = CustomTheme.typography.subhead,
                        color = if (switchState && item?.deadline != null) CustomTheme.colors.gray
                                                          else CustomTheme.colors.blue
                    )
                    Text(
                        text = if (item?.deadline != null && mDate != "" &&
                            mDate != item.deadline.toString()) mDate else "",
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
                        } else {
                            mDate = ""
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
                    text = stringResource(R.string.Delete),
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

@Composable
fun TaskTitle(
    onClose: () -> Unit,
    onAccept: () -> Unit,
    inputText: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = onClose,
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
            onClick = onAccept,
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
}

@Composable
fun TaskDropDownMenu(
    menuExpanded: Boolean,
    onDismiss: () -> Unit,
    onClick: (importanceString: Int) -> Unit
) {
    DropdownMenu(
        expanded = menuExpanded,
        onDismissRequest = onDismiss,
        modifier = Modifier.padding(start = 8.dp, bottom = 16.dp)
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(R.string.Default)) },
            onClick = { onClick(R.string.Default) }
        )
        DropdownMenuItem(
            text = { Text(stringResource(R.string.Low)) },
            onClick = { onClick(R.string.Low) }
        )
        DropdownMenuItem(
            text = {
                Text(
                    stringResource(R.string.High),
                    color = CustomTheme.colors.red
                )
            },
            onClick = { onClick(R.string.High) },
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