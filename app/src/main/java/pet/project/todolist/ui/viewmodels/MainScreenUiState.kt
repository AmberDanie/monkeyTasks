package pet.project.todolist.ui.viewmodels

import pet.project.todolist.domain.LoadingState
import pet.project.todolist.domain.TodoItem

/**
 * MainScreenUiState handle MainScreen UI state
 * */

data class MainScreenUiState(
    val showCompleted: Boolean = true,
    val itemsList: List<TodoItem> = listOf(),
    val loadingState: LoadingState = LoadingState.LOADING,
    val showSnackbar: Boolean = true
)
