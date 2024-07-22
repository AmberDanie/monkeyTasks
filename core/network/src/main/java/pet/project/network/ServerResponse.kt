package pet.project.network

import com.google.gson.annotations.SerializedName

/**
 * ServerResponse represents fields received from server requesting
 * */

data class ServerResponse(
    @SerializedName(value = "status")
    val status: String,
    @SerializedName(value = "list")
    val list: List<pet.project.domain.TodoItemDto>,
    @SerializedName(value = "revision")
    val revision: Int
)
