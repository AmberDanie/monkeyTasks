package pet.project.todolist.network
import com.google.gson.annotations.SerializedName

data class TodoItemRequestDto(
    @SerializedName(value = "element")
    val element: TodoItemDto,
)
