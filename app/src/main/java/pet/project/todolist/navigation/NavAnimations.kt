package pet.project.todolist.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut

fun scaleIntoContainer(): EnterTransition {
    return scaleIn(
        animationSpec = tween(
            durationMillis = 200,
            delayMillis = 90
        ),
        initialScale = 0.9f
    ) + fadeIn(animationSpec = tween(220, delayMillis = 90))
}

fun scaleOutOfContainer(): ExitTransition {
    return scaleOut(
        animationSpec = tween(
            durationMillis = 220,
            delayMillis = 90
        ),
        targetScale = 1.1f
    ) + fadeOut(tween(delayMillis = 90))
}