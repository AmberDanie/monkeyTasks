package pet.project.network

import pet.project.domain.TodoItemDto

/**
 * TodoListRequestDto creates a wrapper over list of todoItems
 * */

data class TodoListRequestDto(
    val list: List<TodoItemDto>,
)
