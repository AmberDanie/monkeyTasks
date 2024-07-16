package pet.project.todolist.ui.viewmodels

import pet.project.todolist.domain.TodoItem

/**
 * TaskScreenUiState handle TaskScreen UI state
 * */

data class TaskScreenUiState(
    val currentItem: TodoItem? = null,
    val errorString: String = ""
)
