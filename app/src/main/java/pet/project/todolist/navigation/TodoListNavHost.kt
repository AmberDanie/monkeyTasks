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
import pet.project.todolist.ui.screens.MainScreen
import pet.project.todolist.ui.screens.SettingsScreen
import pet.project.todolist.ui.screens.TaskScreen
import pet.project.todolist.ui.viewmodels.mainScreen.MainScreenViewModel
import pet.project.todolist.ui.viewmodels.settingsScreen.SettingsScreenViewModel
import pet.project.todolist.ui.viewmodels.taskScreen.TaskScreenViewModel

@Composable
fun TodoListNavHost(
    navController: NavHostController,
    mainScreenViewModelFactory: MainScreenViewModel.Factory,
    taskScreenViewModelFactory: TaskScreenViewModel.Factory,
    settingsScreenViewModel: SettingsScreenViewModel
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
            MainScreen(
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
            TaskScreen(
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
            SettingsScreen(
                settingsState = ssState,
                changeTheme = { settingsScreenViewModel.updateTheme(it) },
                moveBack = { navController.popBackStack() }
            )
        }
    }
}
