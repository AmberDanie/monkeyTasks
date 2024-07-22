package pet.project.settings

import pet.project.domain.ThemeSetting

data class SettingsScreenUiState(
    val currentTheme: ThemeSetting = ThemeSetting.AUTO
)
