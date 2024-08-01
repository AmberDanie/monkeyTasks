package pet.project.todolist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pet.project.theme.AppTheme
import pet.project.theme.CustomTheme
import pet.project.todolist.activities.main.di.ActivityComponent
import pet.project.todolist.application.TodoListApplication
import pet.project.todolist.application.di.AppComponent
import pet.project.todolist.navigation.TodoListNavHost

class AppUiTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    private var activityComponent: ActivityComponent? = null

    private lateinit var appComponent: AppComponent

    @Before
    fun setContent() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        appComponent = (context as TodoListApplication).appComponent
        val activityComponent = appComponent.activityComponent

        val settingsScreenViewModelFactory = activityComponent.settingsScreenViewModelFactory

        composeTestRule.setContent {
            val settingsScreenViewModel = viewModel {
                settingsScreenViewModelFactory.create(savedStateHandle = SavedStateHandle())
            }
            AppTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = CustomTheme.colors.backPrimary
                ) {
                    TodoListNavHost(
                        navController = navController,
                        mainScreenViewModelFactory = activityComponent.mainScreenViewModelFactory,
                        taskScreenViewModelFactory = activityComponent.taskScreenViewModelFactory,
                        settingsScreenViewModel = settingsScreenViewModel
                    )
                }
            }
        }
    }

    @Test
    fun addNewItem() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)
            composeTestRule.awaitIdle()
            composeTestRule.onNodeWithTag("add_button").performClick()
            composeTestRule.onNodeWithTag("text_field").performTextInput("My new task")
            composeTestRule.onNodeWithTag("save_task").performClick()
            composeTestRule.onNodeWithText("My new task").assertExists()
        }
    }

    @Test
    fun removeSomeItem() {
        addNewItem()
        CoroutineScope(Dispatchers.IO).launch {
            composeTestRule.onNodeWithText("My new task").onChildAt(2).performClick()
            composeTestRule.onNodeWithTag("delete_task").performClick()
            composeTestRule.onNodeWithText("My new task").assertDoesNotExist()
        }
    }
}