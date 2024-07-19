package pet.project.todolist.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pet.project.settings.SettingsScreen
import pet.project.task.TaskScreen
import pet.project.settings.SettingsScreenViewModel
import pet.project.task.TaskScreenViewModel

@Composable
fun TodoListNavHost(
    navController: NavHostController,
    mainScreenViewModelFactory: pet.project.main.MainScreenViewModel.Factory,
    taskScreenViewModelFactory: pet.project.task.TaskScreenViewModel.Factory,
    settingsScreenViewModel: pet.project.settings.SettingsScreenViewModel
) {
    NavHost(
        navController = navController,
        startDestination = TodoListNavGraph.Main.name,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        composable(
            route = TodoListNavGraph.Main.name,
            enterTransition = { scaleIntoContainer() },
            exitTransition = { scaleOutOfContainer() },
            popEnterTransition = { scaleIntoContainer() },
            popExitTransition = { scaleOutOfContainer() }
        ) { backStackEntry ->
            val mainScreenViewModel = viewModel {
                mainScreenViewModelFactory.create(backStackEntry.savedStateHandle)
            }
            val msState by mainScreenViewModel.msState.collectAsState()
            pet.project.main.MainScreen(
                msState = msState,
                showOrHideTasks = { mainScreenViewModel.showOrHideCompletedTasks() },
                checkBoxClick = { mainScreenViewModel.changeMadeStatus(it.id) },
                moveToTaskScreen = { navController.navigate(TodoListNavGraph.Task.name + "/$it") },
                moveToSettingsScreen = { navController.navigate(TodoListNavGraph.Settings.name) },
                updateList = { mainScreenViewModel.retryToGetData() },
                hideSnackbar = { mainScreenViewModel.hideSnackbar() },
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(
            route = TodoListNavGraph.Task.name + "/{itemId}",
            enterTransition = { scaleIntoContainer() },
            exitTransition = { scaleOutOfContainer() },
            popEnterTransition = { scaleIntoContainer() },
            popExitTransition = { scaleOutOfContainer() },
        ) { backStackEntry ->
            val taskScreenViewModel = viewModel {
                taskScreenViewModelFactory.create(
                    backStackEntry.savedStateHandle.apply {
                        val itemId = backStackEntry.arguments?.getString("itemId")
                        this["itemId"] = itemId
                    }
                )
            }
            pet.project.task.TaskScreen(
                taskScreenViewModel,
                moveBack = { navController.popBackStack() },
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(
            route = TodoListNavGraph.Settings.name,
            enterTransition = { scaleIntoContainer() },
            exitTransition = { scaleOutOfContainer() },
            popEnterTransition = { scaleIntoContainer() },
            popExitTransition = { scaleOutOfContainer() }
        ) {
            val ssState by settingsScreenViewModel.ssState.collectAsState()
            pet.project.settings.SettingsScreen(
                settingsState = ssState,
                changeTheme = { settingsScreenViewModel.updateTheme(it) },
                moveBack = { navController.popBackStack() }
            )
        }
    }
}
