package pet.project.todolist.ui.viewmodels

import pet.project.todolist.data.TodoItem

data class TaskScreenUiState(
    val currentItem: TodoItem? = null,
    val errorString: String = ""
)
