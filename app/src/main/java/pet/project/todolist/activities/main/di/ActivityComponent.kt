package pet.project.todolist.activities.main.di

import dagger.Subcomponent
import pet.project.todolist.activities.main.MainActivity
import pet.project.todolist.ui.viewmodels.settingsScreen.SettingsScreenViewModel
import pet.project.todolist.ui.viewmodels.mainScreen.MainScreenViewModel
import pet.project.todolist.ui.viewmodels.taskScreen.TaskScreenViewModel
import pet.project.todolist.utils.ActivityScope

@Subcomponent
@ActivityScope
interface ActivityComponent {
    val mainScreenViewModelFactory: MainScreenViewModel.Factory
    val taskScreenViewModelFactory: TaskScreenViewModel.Factory
    val settingsScreenViewModelFactory: SettingsScreenViewModel.Factory

    fun inject(activity: MainActivity)
}