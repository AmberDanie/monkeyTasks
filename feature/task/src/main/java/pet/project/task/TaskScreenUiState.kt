package pet.project.task

import pet.project.domain.TodoItem

/**
 * TaskScreenUiState handle TaskScreen UI state
 * */

data class TaskScreenUiState(
    val currentItem: TodoItem? = null,
    val errorString: String = "",
    val removed: Boolean = true
)
