package pet.project.todolist.application.di

import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import pet.project.todolist.data.repository.ItemsRepository
import pet.project.todolist.utils.AppScope
import pet.project.todolist.network.TodoConnectivityCallback

@Module
interface ConnectivityManagerModule {
    companion object {
        @Provides
        @AppScope
        fun provideConnectivityManager(context: Context): ConnectivityManager {
            return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }

        @Provides
        @AppScope
        fun provideConnectivityCallback(repository: ItemsRepository) : TodoConnectivityCallback {
            return TodoConnectivityCallback(repository)
        }
    }
}
