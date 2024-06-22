package pet.project.todolist.ui.data

import java.time.LocalDate
import java.util.Date

enum class TaskImportance(val importanceString: String) {
    LOW("Низкий"), DEFAULT("Нет"), HIGH("Высокий")
}

data class TodoItem(
    val id: String,
    val text: String,
    val importance: TaskImportance = TaskImportance.DEFAULT,
    val deadline: LocalDate? = null,
    val isMade: Boolean = false,
    val creationDate: Date,
    val changeDate: Date? = null
)