package pet.project.todolist.network
import com.google.gson.annotations.SerializedName

/**
 * TodoItemRequestDto creates a wrapper over the todoItem element
 * */

data class TodoItemRequestDto(
    @SerializedName(value = "element")
    val element: TodoItemDto,
)
