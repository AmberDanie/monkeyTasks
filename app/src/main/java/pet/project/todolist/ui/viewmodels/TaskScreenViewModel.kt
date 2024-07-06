package pet.project.todolist.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pet.project.todolist.TodoListApplication
import pet.project.todolist.data.ItemsRepository
import pet.project.todolist.data.TodoItem

class TaskScreenViewModel(private val repository: ItemsRepository<TodoItem>) : ViewModel() {
    private val _tsState = MutableStateFlow(TaskScreenUiState())
    val tsState = _tsState.asStateFlow()

    fun updateCurrentItem(item: TodoItem) {
        _tsState.update {
            it.copy(
                currentItem = item
            )
        }
    }

    fun resetCurrentItem() {
        _tsState.update {
            it.copy(
                currentItem = null
            )
        }
    }

    fun updateItemInList(oldItemId: String, newItem: TodoItem) {
        viewModelScope.launch {
            repository.updateItem(oldItemId, newItem)
            resetCurrentItem()
        }
    }

    fun addTodoItem(item: TodoItem) {
        viewModelScope.launch {
            repository.addItem(item)
            resetCurrentItem()
        }
    }

    fun removeTodoItem(itemId: String) {
        viewModelScope.launch {
            repository.removeItem(itemId)
        }
        resetCurrentItem()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as TodoListApplication
                    )
                val todoItemsRepository = application.container.todoItemsRepository
                TaskScreenViewModel(repository = todoItemsRepository)
            }
        }
    }
}
