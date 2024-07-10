package pet.project.todolist.data

import kotlinx.coroutines.flow.Flow
import pet.project.todolist.network.TodoItemDto

interface ItemsRepository<Item> {
    suspend fun changeMadeStatus(itemId: String)
    suspend fun updateItem(oldItemId: String, newItem: Item)
    suspend fun updateList(itemList: List<Item>)
    suspend fun removeItem(itemId: String)
    suspend fun addItem(item: Item)
    suspend fun replaceDatabaseData(items: List<TodoItemDto>)
    suspend fun getServerResponse(): ServerResponse?
    suspend fun getItemsFlow(): Pair<Flow<List<TodoItemDto>>, Boolean>
}
