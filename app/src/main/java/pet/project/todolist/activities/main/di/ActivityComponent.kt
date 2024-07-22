package pet.project.todolist.activities.main.di

import dagger.Subcomponent
import pet.project.main.MainScreenViewModel
import pet.project.settings.SettingsScreenViewModel
import pet.project.task.TaskScreenViewModel
import pet.project.todolist.activities.main.MainActivity
import pet.project.utils.ActivityScope

@Subcomponent
@ActivityScope
interface ActivityComponent {
    val mainScreenViewModelFactory: MainScreenViewModel.Factory
    val taskScreenViewModelFactory: TaskScreenViewModel.Factory
    val settingsScreenViewModelFactory: SettingsScreenViewModel.Factory

    fun inject(activity: MainActivity)
}