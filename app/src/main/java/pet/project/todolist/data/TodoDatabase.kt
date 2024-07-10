package pet.project.todolist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pet.project.todolist.network.TodoItemDto

/**
 * TodoDatabase creates local database to store data locally
 * */

@Database(
    entities = [
        TodoItemDto::class
    ],
    version = 4,
    exportSchema = true
)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoItemDao(): TodoItemDao

    companion object {
        @Volatile
        private var Instance: TodoDatabase? = null
        fun getDataBase(context: Context): TodoDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TodoDatabase::class.java, "todo_database")
                    .fallbackToDestructiveMigration().build().also { Instance = it }
            }
        }
    }
}
