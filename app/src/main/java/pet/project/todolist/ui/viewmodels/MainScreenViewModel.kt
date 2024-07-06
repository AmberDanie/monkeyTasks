package pet.project.todolist.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pet.project.todolist.TodoListApplication
import pet.project.todolist.data.ItemsRepository
import pet.project.todolist.data.LoadingState
import pet.project.todolist.data.TodoItem


/**
 *  MainScreenViewModel control flow between MainScreen and Repository class
 * */

class MainScreenViewModel(private val repository: ItemsRepository<TodoItem>) : ViewModel() {

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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as TodoListApplication
                    )
                val todoItemsRepository = application.container.todoItemsRepository
                MainScreenViewModel(repository = todoItemsRepository)
            }
        }
    }
}
