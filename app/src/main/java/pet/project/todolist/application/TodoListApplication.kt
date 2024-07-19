package pet.project.todolist.application

import android.app.Application
import android.net.ConnectivityManager
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import pet.project.management.TodoConnectivityCallback
import pet.project.todolist.application.di.DaggerAppComponent
import javax.inject.Inject

/** TodoListApplication responsible for creating application */

class TodoListApplication : Application() {
    val appComponent by lazy {
        DaggerAppComponent.factory().create(context = this)
    }

    @Inject
    lateinit var workManager: WorkManager

    @Inject
    lateinit var work: PeriodicWorkRequest

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    @Inject
    lateinit var connectivityCallback: TodoConnectivityCallback

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)

        // 8-часовой рефреш данных
        workManager.enqueue(work)
        // рефреш при появлении инета
        connectivityManager.registerDefaultNetworkCallback(connectivityCallback)
    }
}
