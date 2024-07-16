package pet.project.todolist.activities.main.di

import dagger.Subcomponent
import pet.project.todolist.activities.main.MainActivity
import pet.project.todolist.utils.ActivityScope
import pet.project.todolist.ui.viewmodels.MainScreenViewModel
import pet.project.todolist.ui.viewmodels.TaskScreenViewModel

@Subcomponent
@ActivityScope
interface ActivityComponent {
    val mainScreenViewModelFactory: MainScreenViewModel.Factory
    val taskScreenViewModelFactory: TaskScreenViewModel.Factory

    fun inject(activity: MainActivity)
}