package pet.project.todolist.application.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import pet.project.database.TodoDatabase
import pet.project.database.TodoItemDao
import pet.project.utils.AppScope

@Module
interface DataModule {
    companion object {
        @Provides
        @AppScope
        fun provideDatabase(context: Context): TodoDatabase =
            TodoDatabase.getDataBase(context)

        @Provides
        @AppScope
        fun provideTodoDao(db: TodoDatabase): TodoItemDao {
            return db.todoItemDao()
        }

        @Provides
        @AppScope
        fun provideTodoDataStore(context: Context): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                produceFile = {
                    context.preferencesDataStoreFile("settings")
                }
            )
        }
    }
}