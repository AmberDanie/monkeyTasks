package pet.project.todolist.network

import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import pet.project.todolist.data.repositories.todoItemsRepository.ItemsRepository
import javax.inject.Inject

class TodoConnectivityCallback @Inject constructor(
    val repository: ItemsRepository
) : ConnectivityManager.NetworkCallback() {
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        val applicationScope = MainScope()
        applicationScope.launch {
            repository.getItemsFlow()
        }
    }
}