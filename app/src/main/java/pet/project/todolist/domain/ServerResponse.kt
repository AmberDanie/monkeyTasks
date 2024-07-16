package pet.project.todolist.domain

import com.google.gson.annotations.SerializedName
import pet.project.todolist.network.TodoItemDto

/**
 * ServerResponse represents fields received from server requesting
 * */

data class ServerResponse(
    @SerializedName(value = "status")
    val status: String,
    @SerializedName(value = "list")
    val list: List<TodoItemDto>,
    @SerializedName(value = "revision")
    val revision: Int
)
