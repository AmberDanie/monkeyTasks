package pet.project.todolist.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pet.project.todolist.data.repository.ItemsRepository
import pet.project.todolist.domain.TodoItem

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
        viewModelScope.launch {
            if (itemId != "default") {
                item = repository.getItemById(itemId).toTodoItem()
            }
            _tsState.update {
                it.copy(
                    currentItem = item
                )
            }
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

    @dagger.assisted.AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): TaskScreenViewModel
    }
}
