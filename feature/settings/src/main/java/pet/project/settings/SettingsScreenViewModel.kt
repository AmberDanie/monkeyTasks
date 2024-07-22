package pet.project.settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pet.project.data.repositories.todoSettingsRepository.SettingsRepository
import pet.project.domain.ThemeSetting

class SettingsScreenViewModel @AssistedInject constructor(
    private val settingsRepository: SettingsRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _ssState = MutableStateFlow(SettingsScreenUiState())
    val ssState = _ssState.asStateFlow()

    init {
        viewModelScope.launch {
            getTheme()
        }
    }

    fun updateTheme(setting: ThemeSetting) {
        viewModelScope.launch {
            settingsRepository.updateTheme(setting)
            getTheme()
        }
    }

    suspend fun getTheme() {
        viewModelScope.launch {
            val themeSetting = settingsRepository.getTheme()
            _ssState.update {
                it.copy(
                    currentTheme = themeSetting
                )
            }
        }
    }

    @dagger.assisted.AssistedFactory
    interface Factory : ViewModelProvider.Factory {
        fun create(savedStateHandle: SavedStateHandle): SettingsScreenViewModel
    }
}