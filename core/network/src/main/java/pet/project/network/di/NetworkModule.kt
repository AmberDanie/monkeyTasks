package pet.project.network.di

import dagger.Module
import dagger.Provides
import pet.project.network.BuildConfig
import pet.project.network.TodoItemService
import pet.project.utils.AppScope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
interface NetworkModule {
    companion object {
        @Provides
        @AppScope
        fun provideTodoService(): TodoItemService {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build()
                .create(TodoItemService::class.java)
        }
    }
}