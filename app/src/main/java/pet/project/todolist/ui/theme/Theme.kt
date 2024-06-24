package pet.project.todolist.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember

fun lightColors() = CustomColors(
    supportSeparator = SupportLightSeparator,
    supportOverlay = SupportLightOverlay,
    labelPrimary = LabelLightPrimary,
    labelSecondary = LabelLightSecondary,
    labelTertiary = LabelLightTertiary,
    labelDisable = LabelLightDisable,
    red = LightRed,
    green = LightGreen,
    blue = LightBlue,
    gray = LightGray,
    grayLight = LightGrayLight,
    white = LightWhite,
    backPrimary = BackLightPrimary,
    backSecondary = BackLightSecondary,
    backElevated = BackLightElevated,
    isLight = true,
)

fun darkColors() = CustomColors(
    supportSeparator = SupportDarkSeparator,
    supportOverlay = SupportDarkOverlay,
    labelPrimary = LabelDarkPrimary,
    labelSecondary = LabelDarkSecondary,
    labelTertiary = LabelDarkTertiary,
    labelDisable = LabelDarkDisable,
    red = DarkRed,
    green = DarkGreen,
    blue = DarkBlue,
    gray = DarkGray,
    grayLight = DarkGrayLight,
    white = DarkWhite,
    backPrimary = BackDarkPrimary,
    backSecondary = BackDarkSecondary,
    backElevated = BackDarkElevated,
    isLight = false,
)

@Composable
fun CustomTheme(
    spaces: CustomSpaces = CustomTheme.spaces,
    typography: CustomTypography = CustomTheme.typography,
    colors: CustomColors = CustomTheme.colors,
    darkColors: CustomColors = darkColors(),
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val currentColor = remember { if (darkTheme) darkColors else colors }
    val rememberedColors = remember { currentColor.copy() }.apply { updateColorsFrom(currentColor) }
    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalSpaces provides spaces,
        LocalTypography provides typography,
    ) {
        ProvideTextStyle(typography.body, content = content)
    }
}