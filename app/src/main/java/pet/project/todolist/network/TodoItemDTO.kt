package pet.project.todolist.network

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import pet.project.core.domain.TaskImportance
import pet.project.todolist.data.TodoItem
import pet.project.todolist.utils.toLocalDate
import pet.project.todolist.utils.toLocalDateTime
import pet.project.todolist.utils.toTimestamp

/**
 * TodoItemDto represents server data format
 * */

@Entity(tableName = "todoItems")
data class TodoItemDto(
    @PrimaryKey
    @SerializedName(value = "id")
    val id: String,
    @SerializedName(value = "text")
    val text: String,
    @SerializedName(value = "importance")
    val importance: String,
    @SerializedName(value = "deadline")
    val deadline: Long? = null,
    @SerializedName(value = "done")
    val done: Boolean,
    @SerializedName(value = "color")
    val color: String? = null,
    @SerializedName(value = "created_at")
    val createdAt: Long,
    @SerializedName(value = "changed_at")
    val changedAt: Long,
    @SerializedName(value = "last_updated_by")
    val updateBy: String,
    @SerializedName(value = "files")
    val files: List<String>?
) {
    fun toTodoItem() =
        TodoItem(
            id = id,
            taskText = text,
            importance = importance.dtoToImportance()!!,
            deadline = deadline?.toLocalDate(),
            isMade = done,
            creationDate = createdAt.toLocalDateTime()!!,
            changeDate = changedAt.toLocalDateTime()!!,
        )
}

fun TodoItem.toNetworkDto(deviceId: String) =
    TodoItemDto(
        id = id,
        text = taskText,
        importance = importance.toNetworkDto(),
        deadline = deadline?.toTimestamp(),
        done = isMade,
        color = null,
        createdAt = creationDate.toTimestamp(),
        changedAt = changeDate.toTimestamp(),
        updateBy = deviceId,
        files = null
    )

private fun TaskImportance.toNetworkDto() = when (this) {
    TaskImportance.DEFAULT -> "basic"
    TaskImportance.LOW -> "low"
    TaskImportance.HIGH -> "important"
}

private fun String.dtoToImportance() = when (this) {
    "basic" -> TaskImportance.DEFAULT
    "low" -> TaskImportance.LOW
    "important" -> TaskImportance.HIGH
    else -> null
}
