package pet.project.todolist

import android.app.Application

class TodoListApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = TodoAppContainer(this)
    }
}
