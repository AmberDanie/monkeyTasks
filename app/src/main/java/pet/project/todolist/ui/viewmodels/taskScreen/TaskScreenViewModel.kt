package pet.project.todolist.ui.viewmodels.taskScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pet.project.todolist.data.TodoItem
import pet.project.todolist.data.repositories.todoItemsRepository.ItemsRepository

/**
 *  TaskScreenViewModel control flow between TaskScreen and Repository class
 * */

class TaskScreenViewModel @AssistedInject constructor(
    private val repository: ItemsRepository,
    @Assisted val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _tsState = MutableStateFlow(TaskScreenUiState())
    val tsState = _tsState.asStateFlow()

    init {
        updateCurrentItem()
    }

    private fun updateCurrentItem() {
        val itemId: String = checkNotNull(savedStateHandle["itemId"])
        var item: TodoItem? = null
        var isRemoved = true
        viewModelScope.launch {
            if (itemId != "default") {
                item = repository.getItemById(itemId).toTodoItem()
                isRemoved = false
            }
            _tsState.update {
                it.copy(
                    currentItem = item,
                    removed = isRemoved
                )
            }
        }
    }

    fun resetRemovedStatus(removed: Boolean) {
        viewModelScope.launch {
            _tsState.update {
                it.copy(
                    removed = removed
                )
            }
        }
    }

    fun updateItemInList(oldItemId: String, newItem: TodoItem) {
        viewModelScope.launch {
            repository.updateItem(oldItemId, newItem)
        }
    }

    fun addTodoItem(item: TodoItem) {
        viewModelScope.launch {
            repository.addItem(item)
        }
    }

    fun removeTodoItem(itemId: String) {
        viewModelScope.launch {
            repository.removeItem(itemId)
        }
    }

    @dagger.assisted.AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): TaskScreenViewModel
    }
}
