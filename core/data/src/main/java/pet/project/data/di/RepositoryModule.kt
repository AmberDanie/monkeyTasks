package pet.project.data.di

import dagger.Binds
import dagger.Module
import pet.project.data.repositories.todoItemsRepository.ItemsRepository
import pet.project.data.repositories.todoItemsRepository.TodoItemsRepository
import pet.project.data.repositories.todoSettingsRepository.SettingsRepository
import pet.project.data.repositories.todoSettingsRepository.TodoSettingsRepository

@Module
interface RepositoryModule {
    @Binds
    fun bindItemsRepository(impl: TodoItemsRepository): ItemsRepository
    @Binds
    fun bindSettingsRepository(impl: TodoSettingsRepository): SettingsRepository
}
