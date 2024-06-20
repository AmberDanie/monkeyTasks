package pet.project.todolist.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf

val LocalSpaces = staticCompositionLocalOf { CustomSpaces() }

val LocalColors = staticCompositionLocalOf { lightColors() }

val LocalTypography = staticCompositionLocalOf {
    CustomTypography()
}