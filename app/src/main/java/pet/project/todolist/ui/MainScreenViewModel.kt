package pet.project.todolist.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import pet.project.todolist.ui.data.TodoItem

class MainScreenViewModel(val repository: TodoItemsRepository = TodoItemsRepository()) : ViewModel() {
    
    fun getTodoItems(): MutableList<TodoItem> {
        return repository.returnTodoItemsList()
    }
}