package pet.project.todolist.application.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import pet.project.data.di.RepositoryModule
import pet.project.database.di.DataModule
import pet.project.management.di.ConnectivityManagerModule
import pet.project.management.di.WorkManagerModule
import pet.project.network.di.NetworkModule
import pet.project.todolist.activities.main.di.ActivityComponent
import pet.project.todolist.application.TodoListApplication
import pet.project.utils.AppScope

@AppScope
@Component(
    modules = [
        RepositoryModule::class, NetworkModule::class,
        DataModule::class, WorkManagerModule::class,
        ConnectivityManagerModule::class
    ]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }

    val activityComponent: ActivityComponent

    fun inject(application: TodoListApplication)
}
