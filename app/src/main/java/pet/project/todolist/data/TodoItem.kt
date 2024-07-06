package pet.project.todolist.data

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

data class TodoItem(
    @SerializedName(value = "id")
    val id: String,
    @SerializedName(value = "text")
    val text: String,
    @SerializedName(value = "importance")
    val importance: TaskImportance = TaskImportance.DEFAULT,
    @SerializedName(value = "deadline")
    val deadline: LocalDate? = null,
    @SerializedName(value = "done")
    val isMade: Boolean = false,
    @SerializedName(value = "created_at")
    val creationDate: LocalDateTime = LocalDateTime.now(ZoneId.of("UTC")),
    @SerializedName(value = "changed_at")
    val changeDate: LocalDateTime = creationDate
)
