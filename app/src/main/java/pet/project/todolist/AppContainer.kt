package pet.project.todolist

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import pet.project.todolist.data.GetDataFromServerWorker
import pet.project.todolist.data.ItemsRepository
import pet.project.todolist.data.TodoDatabase
import pet.project.todolist.data.TodoItem
import pet.project.todolist.data.TodoItemsRepository
import pet.project.todolist.network.TodoItemService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface AppContainer {
    val todoItemsRepository: ItemsRepository<TodoItem>
}

class TodoAppContainer(private val context: Context) : AppContainer {
    private val baseUrl = BuildConfig.BASE_URL

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val constraints = Constraints.Builder()
        .setRequiresCharging(true)
        .build()

    private val getDataWork = PeriodicWorkRequestBuilder<GetDataFromServerWorker>(
        REPEAT_INTERVAL,
        TimeUnit.HOURS
    )
        .setConstraints(constraints)
        .build()

    private val retrofitService: TodoItemService by lazy {
        retrofit.create(TodoItemService::class.java)
    }

    private val todoItemDao = TodoDatabase.getDataBase(context).todoItemDao()

    override val todoItemsRepository: ItemsRepository<TodoItem> by lazy {
        TodoItemsRepository(
            todoItemService = retrofitService,
            settingsDataStore = context.dataStore,
            itemDao = todoItemDao
        )
    }

    init {
        WorkManager.getInstance(context).enqueue(getDataWork)
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val applicationScope = MainScope()
        connectivityManager.let {
            it.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    applicationScope.launch {
                        todoItemsRepository.getItemsFlow()
                    }
                }
            })
        }
    }

    companion object {
        private const val REPEAT_INTERVAL = 8L
    }
}
