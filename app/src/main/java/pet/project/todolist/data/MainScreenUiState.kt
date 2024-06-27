package pet.project.todolist.data

data class MainScreenUiState(
    val showCompleted: Boolean = true,
    val itemsList: List<TodoItem> = listOf(),
    val currentItem: TodoItem? = null
)