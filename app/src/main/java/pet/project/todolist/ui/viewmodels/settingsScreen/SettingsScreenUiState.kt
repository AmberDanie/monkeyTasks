package pet.project.todolist.ui.viewmodels.settingsScreen

import pet.project.core.domain.ThemeSetting

data class SettingsScreenUiState(
    val currentTheme: ThemeSetting = ThemeSetting.AUTO
)
