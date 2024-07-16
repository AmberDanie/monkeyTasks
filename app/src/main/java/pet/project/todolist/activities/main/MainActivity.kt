package pet.project.todolist.activities.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import pet.project.todolist.activities.main.di.ActivityComponent
import pet.project.todolist.application.TodoListApplication
import pet.project.todolist.navigation.TodoListNavHost
import pet.project.todolist.ui.theme.AppTheme
import pet.project.todolist.ui.theme.CustomTheme

/**
 * MainActivity responsible for setting UI-layer content
 * */

class MainActivity : ComponentActivity() {
    private var activityComponent: ActivityComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityComponent =
            (applicationContext as TodoListApplication).appComponent.activityComponent

        val mainScreenViewModelFactory = activityComponent!!.mainScreenViewModelFactory
        val taskScreenViewModelFactory = activityComponent!!.taskScreenViewModelFactory

        setContent {
            AppTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = CustomTheme.colors.backPrimary
                ) {
                    TodoListNavHost(
                        navController = navController,
                        mainScreenViewModelFactory = mainScreenViewModelFactory,
                        taskScreenViewModelFactory = taskScreenViewModelFactory
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppDarkPreview() {
    AppTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CustomTheme.colors.backPrimary
        ) {
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppLightPreview() {
    AppTheme(darkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CustomTheme.colors.backPrimary
        ) {
        }
    }
}
