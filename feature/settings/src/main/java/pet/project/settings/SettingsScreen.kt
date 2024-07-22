package pet.project.settings

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pet.project.domain.ThemeSetting
import pet.project.domain.themeIdToThemeSetting
import pet.project.theme.AppTheme
import pet.project.theme.CustomTheme

@Composable
fun SettingsScreen(
    settingsState: SettingsScreenUiState,
    changeTheme: (ThemeSetting) -> Unit,
    moveBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentThemeSetting = settingsState.currentTheme
    Box {
        IconButton(
            onClick = moveBack,
            modifier = Modifier.padding(top = 32.dp, start = 8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back",
                tint = CustomTheme.colors.labelPrimary
            )
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Изменить тему",
                style = CustomTheme.typography.title,
                color = CustomTheme.colors.labelPrimary,
                modifier = modifier.padding(8.dp)
            )
            SettingsToggleRow(
                currentThemeSetting = currentThemeSetting,
                changeTheme = changeTheme
            )
        }
    }
}

@Composable
fun SettingsToggleRow(
    currentThemeSetting: ThemeSetting,
    changeTheme: (ThemeSetting) -> Unit
) {
    Card(
        shape = RoundedCornerShape(64.dp),
        colors = CardDefaults.cardColors(
            containerColor = CustomTheme.colors.grayLight
        ),
        modifier = Modifier.height(64.dp)
    ) {
        AnimatedContent(
            targetState = currentThemeSetting,
            label = "",
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(800)
                ) togetherWith fadeOut(
                    animationSpec = tween(800)
                )
            }
        ) { theme ->
            Row {
                SettingsToggleButton(
                    iconId = R.drawable.baseline_dark_mode_24,
                    contentDescription = "Dark mode",
                    isEnabled = theme == ThemeSetting.DARK,
                    changeTheme = { changeTheme(ThemeSetting.DARK) }
                )
                SettingsToggleButton(
                    iconId = R.drawable.baseline_auto_mode_24,
                    contentDescription = "Auto mode",
                    isEnabled = theme == ThemeSetting.AUTO,
                    changeTheme = { changeTheme(ThemeSetting.AUTO) }
                )
                SettingsToggleButton(
                    iconId = R.drawable.baseline_light_mode_24,
                    contentDescription = "Light mode",
                    isEnabled = theme == ThemeSetting.LIGHT,
                    changeTheme = { changeTheme(ThemeSetting.LIGHT) }
                )
            }
        }
    }
}

@Composable
fun SettingsToggleButton(
    @DrawableRes iconId: Int,
    contentDescription: String,
    isEnabled: Boolean,
    changeTheme: (ThemeSetting) -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { changeTheme(iconId.themeIdToThemeSetting()) },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = if (isEnabled) {
                CustomTheme.colors.labelDisable
            } else {
                CustomTheme.colors.grayLight
            }
        ),
        modifier = modifier
            .height(64.dp)
            .width(64.dp)
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = contentDescription,
            tint = CustomTheme.colors.labelPrimary,
            modifier = Modifier.scale(1.5f)
        )
    }
}

@Preview
@Composable
fun LightToggleButtonPreview() {
    AppTheme(themeSetting = ThemeSetting.LIGHT) {
        Surface(
            color = CustomTheme.colors.backPrimary
        ) {
            SettingsToggleRow(ThemeSetting.LIGHT) { }
        }
    }
}

@Preview
@Composable
fun DarkToggleButtonPreview() {
    AppTheme(themeSetting = ThemeSetting.DARK) {
        Surface(
            color = CustomTheme.colors.backPrimary
        ) {
            SettingsToggleRow(ThemeSetting.DARK) {}
        }
    }
}
