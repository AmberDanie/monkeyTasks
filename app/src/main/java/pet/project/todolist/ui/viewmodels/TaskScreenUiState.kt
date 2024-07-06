package pet.project.todolist.ui.viewmodels

import pet.project.todolist.data.TodoItem

/**
 * TaskScreenUiState handle TaskScreen UI state
 * */

data class TaskScreenUiState(
    val currentItem: TodoItem? = null,
    val errorString: String = ""
)
