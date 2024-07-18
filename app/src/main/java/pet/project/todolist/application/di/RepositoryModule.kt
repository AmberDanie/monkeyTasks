package pet.project.todolist.application.di

import dagger.Binds
import dagger.Module
import pet.project.todolist.data.repositories.todoItemsRepository.ItemsRepository
import pet.project.todolist.data.repositories.todoItemsRepository.TodoItemsRepository
import pet.project.todolist.data.repositories.todoSettingsRepository.SettingsRepository
import pet.project.todolist.data.repositories.todoSettingsRepository.TodoSettingsRepository

@Module
interface RepositoryModule {
    @Binds
    fun bindItemsRepository(impl: TodoItemsRepository): ItemsRepository
    @Binds
    fun bindSettingsRepository(impl: TodoSettingsRepository): SettingsRepository
}
