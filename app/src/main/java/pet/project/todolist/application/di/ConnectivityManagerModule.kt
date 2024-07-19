package pet.project.todolist.application.di

import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import pet.project.data.repositories.todoItemsRepository.ItemsRepository
import pet.project.management.TodoConnectivityCallback
import pet.project.utils.AppScope

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
