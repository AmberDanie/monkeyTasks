package pet.project.data.repositories.todoSettingsRepository

import pet.project.domain.ThemeSetting

interface SettingsRepository {
    suspend fun updateTheme(setting: ThemeSetting)
    suspend fun getTheme(): ThemeSetting
}