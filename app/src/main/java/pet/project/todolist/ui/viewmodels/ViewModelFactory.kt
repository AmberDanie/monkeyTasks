package pet.project.todolist.ui.viewmodels

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

class ViewModelFactory<T : ViewModel>(
    savedStateRegistryOwner: SavedStateRegistryOwner,
    defaultArgs: Bundle?,
    private val viewModelFactory: (stateHandle: SavedStateHandle) -> T,
) : AbstractSavedStateViewModelFactory(savedStateRegistryOwner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return viewModelFactory.invoke(handle) as T
    }
}