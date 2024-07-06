package pet.project.todolist

import android.app.Application

/** TodoListApplication responsible for creating application */

class TodoListApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = TodoAppContainer(this)
    }
}
