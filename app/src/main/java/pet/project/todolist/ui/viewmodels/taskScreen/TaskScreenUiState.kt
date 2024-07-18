package pet.project.todolist.ui.viewmodels.taskScreen

import pet.project.todolist.data.TodoItem

/**
 * TaskScreenUiState handle TaskScreen UI state
 * */

data class TaskScreenUiState(
    val currentItem: TodoItem? = null,
    val errorString: String = "",
    val removed: Boolean = true
)
