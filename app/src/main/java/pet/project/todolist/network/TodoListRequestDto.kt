package pet.project.todolist.network

/**
 * TodoListRequestDto creates a wrapper over list of todoItems
 * */

data class TodoListRequestDto(
    val list: List<TodoItemDto>,
)