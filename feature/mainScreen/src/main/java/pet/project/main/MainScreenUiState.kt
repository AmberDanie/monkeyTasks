package pet.project.main

import pet.project.domain.LoadingState
import pet.project.domain.TodoItem

/**
 * MainScreenUiState handle MainScreen UI state
 * */

data class MainScreenUiState(
    val showCompleted: Boolean = false,
    val itemsList: List<TodoItem> = listOf(),
    val loadingState: LoadingState = LoadingState.LOADING,
    val showSnackbar: Boolean = true
)
