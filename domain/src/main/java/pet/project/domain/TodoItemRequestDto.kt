package pet.project.domain
import com.google.gson.annotations.SerializedName

/**
 * TodoItemRequestDto creates a wrapper over the todoItem element
 * */

data class TodoItemRequestDto(
    @SerializedName(value = "element")
    val element: pet.project.domain.TodoItemDto,
)
