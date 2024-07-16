package pet.project.todolist.application.di

import dagger.Binds
import dagger.Module
import pet.project.todolist.data.repository.ItemsRepository
import pet.project.todolist.data.repository.TodoItemsRepository

@Module
interface RepositoryModule {
    @Binds
    fun bindItemsRepository(impl: TodoItemsRepository): ItemsRepository
}