package pet.project.todolist.ui.viewmodels.mainScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pet.project.todolist.data.repositories.todoItemsRepository.ItemsRepository
import pet.project.core.domain.LoadingState

/**
 *  MainScreenViewModel control flow between MainScreen and Repository class
 * */

class MainScreenViewModel @AssistedInject constructor(
    private val repository: ItemsRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _msState = MutableStateFlow(MainScreenUiState())
    val msState = _msState.asStateFlow()

    fun showOrHideCompletedTasks() {
        _msState.update {
            it.copy(
                showCompleted = !it.showCompleted
            )
        }
    }

    fun changeMadeStatus(itemId: String) {
        viewModelScope.launch {
            repository.changeMadeStatus(itemId)
        }
    }

    fun hideSnackbar() {
        _msState.update { stateValue ->
            stateValue.copy(
                showSnackbar = false
            )
        }
    }

    private suspend fun updateList() {
        withContext(Dispatchers.IO) {
            val flow = repository.getItemsFlow()
            flow.first.collect { repositoryData ->
                _msState.update { stateValue ->
                    stateValue.copy(
                        itemsList = repositoryData.map { it.toTodoItem() },
                        loadingState = if (flow.second) LoadingState.SUCCESS else LoadingState.ERROR
                    )
                }
            }
        }
    }

    fun retryToGetData() {
        viewModelScope.launch {
            _msState.update { stateValue ->
                stateValue.copy(
                    loadingState = LoadingState.LOADING
                )
            }
            updateList()
        }
    }

    init {
        viewModelScope.launch {
            updateList()
        }
    }

    @dagger.assisted.AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): MainScreenViewModel
    }
}
