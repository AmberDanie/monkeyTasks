package pet.project.todolist.ui

import pet.project.todolist.ui.data.TaskImportance
import pet.project.todolist.ui.data.TodoItem
import java.time.LocalDate
import java.util.Date

class TodoItemsRepository {
    val todoItems = mutableListOf<TodoItem>(
        TodoItem(
            id = "0",
            text = "Купить что-то",
            isMade = true,
            creationDate = Date()
        ),
        TodoItem(
            id = "1",
            text = "Купить что-то",
            isMade = true,
            creationDate = Date()
        ),
        TodoItem(
            id = "2",
            text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно",
            isMade = false,
            creationDate = Date()
        ),
        TodoItem(
            id = "3",
            text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, " +
                    "но точно чтобы показать как образовывается список из элементов",
            isMade = false,
            creationDate = Date()
        ),
        TodoItem(
            id = "4",
            text = "Купить что-то",
            importance = TaskImportance.HIGH,
            isMade = false,
            creationDate = Date()
        ),
        TodoItem(
            id = "5",
            text = "Купить что-то",
            isMade = false,
            importance = TaskImportance.LOW,
            creationDate = Date()
        ),
        TodoItem(
            id = "6",
            text = "Доделать ДЗ",
            isMade = false,
            deadline = LocalDate.parse("2024-06-22"),
            importance = TaskImportance.LOW,
            creationDate = Date()
        ),
    )
    fun addItemToList(item: TodoItem) {
        todoItems.add(item)
    }
    fun returnTodoItemsList(): MutableList<TodoItem> {
        return todoItems
    }
}