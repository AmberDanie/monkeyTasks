package pet.project.todolist.network

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import pet.project.todolist.data.repositories.todoItemsRepository.TodoItemsRepository

/**
 * GetDataFromServerWorker creates worker for background tasks management
 * */

class GetDataFromServerWorker(
    private val context: Context,
    workerParams: WorkerParameters,
    private val repository: TodoItemsRepository
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        var result = false
        withContext(Dispatchers.IO) {
            try {
                repository.updateList(repository.getItemsFlow().first.first().map { it.toTodoItem() })
                result = true
            } catch (_: Exception) {}
        }
        if (result) {
            return Result.success()
        }
        return Result.failure()
    }
}
