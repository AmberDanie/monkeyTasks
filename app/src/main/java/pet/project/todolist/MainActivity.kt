package pet.project.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import pet.project.todolist.navigation.TodoListNavHost
import pet.project.todolist.ui.theme.AppTheme
import pet.project.todolist.ui.theme.CustomTheme
import pet.project.todolist.ui.viewmodels.MainScreenViewModel
import pet.project.todolist.ui.viewmodels.TaskScreenViewModel

/* part 2 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val mainScreenViewModel: MainScreenViewModel by viewModels(factoryProducer = {
            MainScreenViewModel.Factory
        })

        val taskScreenViewModel: TaskScreenViewModel by viewModels(factoryProducer = {
            TaskScreenViewModel.Factory
        })

        setContent {
            AppTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = CustomTheme.colors.backPrimary
                ) {
                    TodoListNavHost(
                        navController = navController,
                        mainScreenViewModel = mainScreenViewModel,
                        taskScreenViewModel = taskScreenViewModel
                    )
                }
            }
        }
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
