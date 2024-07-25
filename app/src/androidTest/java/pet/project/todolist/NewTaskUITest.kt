package pet.project.todolist

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test
import pet.project.theme.AppTheme

class NewTaskUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun addNewTask() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            AppTheme {

            }
        }
    }
}
