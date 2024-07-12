package pet.project.todolist.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pet.project.todolist.network.TodoItemDto

@Dao
interface TodoItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoItem(item: TodoItemDto)

    @Delete
    suspend fun deleteTodoItem(item: TodoItemDto)

    @Update
    suspend fun updateTodoItem(item: TodoItemDto)

    @Query("SELECT * FROM todoItems WHERE id = :id")
    fun getTodoItem(id: String): TodoItemDto

    @Query("SELECT * FROM todoItems")
    fun getAllTodoItems(): Flow<List<TodoItemDto>>
}
