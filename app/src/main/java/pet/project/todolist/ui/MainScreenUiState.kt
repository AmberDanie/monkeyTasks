package pet.project.todolist.ui

import pet.project.todolist.ui.data.TodoItem

data class MainScreenUiState(
    val showCompleted: Boolean = true,
    val itemsList: List<TodoItem> = listOf(),
    val currentItem: TodoItem? = null
)