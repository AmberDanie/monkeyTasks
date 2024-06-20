package pet.project.todolist.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

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
    isLight = true,
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