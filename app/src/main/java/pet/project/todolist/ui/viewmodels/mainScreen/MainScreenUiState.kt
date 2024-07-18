package pet.project.todolist.ui.viewmodels.mainScreen

import pet.project.core.domain.LoadingState
import pet.project.todolist.data.TodoItem

/**
 * MainScreenUiState handle MainScreen UI state
 * */

data class MainScreenUiState(
    val showCompleted: Boolean = false,
    val itemsList: List<TodoItem> = listOf(),
    val loadingState: LoadingState = LoadingState.LOADING,
    val showSnackbar: Boolean = true
)
