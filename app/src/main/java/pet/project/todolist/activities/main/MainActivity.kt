package pet.project.todolist.activities.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import pet.project.core.theme.AppTheme
import pet.project.core.theme.CustomTheme
import pet.project.todolist.activities.main.di.ActivityComponent
import pet.project.todolist.application.TodoListApplication
import pet.project.todolist.navigation.TodoListNavHost
import javax.inject.Inject

/**
 * MainActivity responsible for setting UI-layer content
 * */

class MainActivity : ComponentActivity() {
    private var activityComponent: ActivityComponent? = null

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityComponent =
            (applicationContext as TodoListApplication).appComponent.activityComponent.also {
                it.inject(
                    this@MainActivity
                )
            }

        val mainScreenViewModelFactory = activityComponent!!.mainScreenViewModelFactory
        val taskScreenViewModelFactory = activityComponent!!.taskScreenViewModelFactory
        val settingsScreenViewModelFactory = activityComponent!!.settingsScreenViewModelFactory

        setContent {
            val settingsScreenViewModel = viewModel {
                settingsScreenViewModelFactory.create(savedStateHandle = SavedStateHandle())
            }
            val settingsState by settingsScreenViewModel.ssState.collectAsState()
            val theme = settingsState.currentTheme
            AppTheme(
                themeSetting = theme
            ) {
                window.navigationBarColor =
                    CustomTheme.colors.backPrimary.toArgb()
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = CustomTheme.colors.backPrimary
                ) {
                    TodoListNavHost(
                        navController = navController,
                        mainScreenViewModelFactory = mainScreenViewModelFactory,
                        taskScreenViewModelFactory = taskScreenViewModelFactory,
                        settingsScreenViewModel = settingsScreenViewModel
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityComponent = null
    }
}