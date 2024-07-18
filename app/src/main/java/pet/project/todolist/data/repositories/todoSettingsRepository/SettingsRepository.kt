package pet.project.todolist.data.repositories.todoSettingsRepository

import pet.project.core.domain.ThemeSetting

interface SettingsRepository {
    suspend fun updateTheme(setting: ThemeSetting)
    suspend fun getTheme(): ThemeSetting
}