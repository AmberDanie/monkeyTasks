package pet.project.todolist.data.repositories.todoSettingsRepository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pet.project.todolist.data.datastore.AppPreferences.THEME_SETTING
import pet.project.core.domain.ThemeSetting
import pet.project.core.domain.themeIdToThemeSetting
import javax.inject.Inject

class TodoSettingsRepository @Inject constructor(
    private val settingsDataStore: DataStore<Preferences>
) : SettingsRepository {
    override suspend fun updateTheme(setting: ThemeSetting) {
        withContext(Dispatchers.IO) {
            settingsDataStore.edit { preferences ->
                preferences[THEME_SETTING] = setting.themeId
            }
        }
    }

    override suspend fun getTheme(): ThemeSetting {
        return withContext(Dispatchers.IO) {
            settingsDataStore.data.map { preferences ->
                preferences[THEME_SETTING] ?: ThemeSetting.AUTO.themeId
            }.first().themeIdToThemeSetting()
        }
    }
}
