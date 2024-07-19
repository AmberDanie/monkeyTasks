package pet.project.domain

import androidx.annotation.StringRes

enum class ThemeSetting(@StringRes val themeId: Int) {
    DARK(R.string.Dark_theme), AUTO(R.string.Auto_theme), LIGHT(R.string.Light_theme)
}

fun Int.themeIdToThemeSetting(): ThemeSetting {
    return when (this) {
        R.string.Dark_theme -> ThemeSetting.DARK
        R.string.Light_theme -> ThemeSetting.LIGHT
        else -> ThemeSetting.AUTO
    }
}