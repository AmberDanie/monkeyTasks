package pet.project.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pet.project.todolist.ui.MainScreen
import pet.project.todolist.ui.MainScreenViewModel
import pet.project.todolist.ui.theme.CustomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val mainScreenViewModel = MainScreenViewModel()

        super.onCreate(savedInstanceState)
        setContent {
            CustomTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = CustomTheme.colors.backPrimary
                ) {
                    MainScreen(mainScreenViewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppDarkPreview() {
    CustomTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CustomTheme.colors.backPrimary
        ) {
            MainScreen(MainScreenViewModel())
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppLightPreview() {
    CustomTheme(darkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CustomTheme.colors.backPrimary
        ) {
            MainScreen(MainScreenViewModel())
        }
    }
}