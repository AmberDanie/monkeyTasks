package pet.project.todolist.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pet.project.todolist.BuildConfig
import pet.project.todolist.data.datastore.AppPreferences.DATABASE_REVISION
import pet.project.todolist.data.datastore.AppPreferences.DEVICE_ID
import pet.project.todolist.data.datastore.AppPreferences.SERVER_REVISION
import pet.project.todolist.domain.ServerResponse
import pet.project.todolist.domain.TodoItem
import pet.project.todolist.data.database.TodoItemDao
import pet.project.todolist.network.TodoItemDto
import pet.project.todolist.network.TodoItemRequestDto
import pet.project.todolist.network.TodoItemService
import pet.project.todolist.network.TodoListRequestDto
import pet.project.todolist.network.toNetworkDto
import java.lang.Thread.sleep
import java.util.UUID
import javax.inject.Inject

/**
 * TodoItemsRepository handle data from sources
 * */

class TodoItemsRepository @Inject constructor(
    private val todoItemService: TodoItemService,
    private val settingsDataStore: DataStore<Preferences>,
    private val itemDao: TodoItemDao
) : ItemsRepository {
    override suspend fun changeMadeStatus(itemId: String) {
        withContext(Dispatchers.IO) {
            val oldItem = itemDao.getTodoItem(itemId).toTodoItem()
            val newItem = oldItem.copy(
                isMade = !oldItem.isMade
            )
            updateItem(oldItemId = itemId, newItem = newItem)
        }
    }

    override suspend fun getItemById(itemId: String): TodoItemDto {
        return withContext(Dispatchers.IO) {
            itemDao.getTodoItem(itemId)
        }
    }

    override suspend fun updateItem(oldItemId: String, newItem: TodoItem) {
        withContext(Dispatchers.IO) {
            val revision = getLastServerRevision()
            val itemToAdd = newItem.copy(
                id = oldItemId
            )
            itemDao.updateTodoItem(itemToAdd.toNetworkDto(getDeviceId()))
            val databaseRevision = getLastDatabaseRevision()
            refreshDatabaseRevision(databaseRevision + 1)
            try {
                val response = todoItemService.updateItemOnServer(
                    BuildConfig.BEARER_TOKEN,
                    revision,
                    oldItemId,
                    TodoItemRequestDto(itemToAdd.toNetworkDto(getDeviceId()))
                )
                refreshServerRevision(response.revision)
            } catch (_: Exception) {
            }
        }
    }

    override suspend fun updateList(itemList: List<TodoItem>) {
        withContext(Dispatchers.IO) {
            val revision = getLastServerRevision()
            try {
                val response = todoItemService.updateListOnServer(
                    BuildConfig.BEARER_TOKEN,
                    revision,
                    TodoListRequestDto(
                        itemList.map {
                            it.toNetworkDto(getDeviceId())
                        }
                    )
                )
                refreshServerRevision(response.revision)
                refreshDatabaseRevision(getLastServerRevision())
            } catch (_: Exception) {
            }
        }
    }

    override suspend fun removeItem(itemId: String) {
        withContext(Dispatchers.IO) {
            val revision = getLastServerRevision()
            val item = itemDao.getTodoItem(itemId)
            itemDao.deleteTodoItem(item)
            val databaseRevision = getLastDatabaseRevision()
            refreshDatabaseRevision(databaseRevision + 1)
            try {
                val response = todoItemService.deleteItemFromServer(
                    BuildConfig.BEARER_TOKEN,
                    revision,
                    itemId
                )
                refreshServerRevision(response.revision)
            } catch (_: Exception) {
            }
        }
    }

    override suspend fun addItem(item: TodoItem) {
        withContext(Dispatchers.IO) {
            val revision = getLastServerRevision()
            val itemDto = item.toNetworkDto(getDeviceId())
            itemDao.insertTodoItem(itemDto)
            val databaseRevision = getLastDatabaseRevision()
            refreshDatabaseRevision(databaseRevision + 1)
            try {
                val response = todoItemService.addItemToServer(
                    BuildConfig.BEARER_TOKEN,
                    revision,
                    TodoItemRequestDto(itemDto)
                )
                refreshServerRevision(response.revision)
            } catch (_: Exception) {
            }
        }
    }

    private suspend fun getLastServerRevision(): Int {
        val lastRevision = settingsDataStore.data.map { preferences ->
            preferences[SERVER_REVISION] ?: 0
        }.first()
        return lastRevision
    }

    private suspend fun getLastDatabaseRevision(): Int {
        val lastRevision = settingsDataStore.data.map { preferences ->
            preferences[DATABASE_REVISION] ?: -1
        }.first()
        return lastRevision
    }

    private suspend fun refreshServerRevision(serverRevision: Int) {
        settingsDataStore.edit { preferences ->
            preferences[SERVER_REVISION] = serverRevision
        }
    }

    private suspend fun refreshDatabaseRevision(databaseRevision: Int) {
        settingsDataStore.edit { preferences ->
            preferences[DATABASE_REVISION] = databaseRevision
        }
    }

    private suspend fun replaceDatabaseData(items: List<TodoItemDto>) {
        val daoItems = itemDao.getAllTodoItems().first()
        for (daoItem in daoItems) {
            itemDao.deleteTodoItem(daoItem)
        }
        for (serverItem in items) {
            itemDao.insertTodoItem(serverItem)
        }
    }

    private suspend fun getDeviceId(): String {
        val deviceId = settingsDataStore.data.map { preferences ->
            val uuid = UUID.randomUUID().toString()
            preferences[DEVICE_ID] ?: uuid.also {
                settingsDataStore.edit { preference ->
                    preference[DEVICE_ID] = uuid
                }
            }
        }.first()
        return deviceId
    }

    private suspend fun getServerResponse(): ServerResponse? {
        var response: ServerResponse? = null
        withContext(Dispatchers.IO) {
            try {
                response = todoItemService.getServerResponse(BuildConfig.BEARER_TOKEN)
                refreshServerRevision(response!!.revision)
            } catch (_: Exception) {
            }
        }
        return response
    }

    private suspend fun getNetworkData(): List<TodoItemDto>? {
        var serverItems: List<TodoItemDto>? = null
        withContext(Dispatchers.IO) {
            for (i in 0 until 5) {
                serverItems = getServerResponse()?.list
                if (serverItems != null) {
                    break
                }
                sleep(1000)
            }
        }
        return serverItems
    }

    override suspend fun getItemsFlow(): Pair<Flow<List<TodoItemDto>>, Boolean> {
        var items: Flow<List<TodoItemDto>>
        var serverIsAvailable = false
        withContext(Dispatchers.IO) {
            val serverItems = getNetworkData()
            if (serverItems != null) {
                serverIsAvailable = true
            }
            val serverRevision = getLastServerRevision()
            val databaseRevision = getLastDatabaseRevision()
            if (databaseRevision < serverRevision && serverItems != null) {
                replaceDatabaseData(serverItems)
                refreshDatabaseRevision(serverRevision)
            } else if (databaseRevision > serverRevision && serverItems != null) {
                updateList(itemDao.getAllTodoItems().first().map { it.toTodoItem() })
            }
            items = itemDao.getAllTodoItems()
        }
        return Pair(items, serverIsAvailable)
    }
}
