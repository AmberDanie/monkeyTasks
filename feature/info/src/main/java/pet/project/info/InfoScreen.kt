package pet.project.info

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.picasso.PicassoDivImageLoader
import com.yandex.div.rive.OkHttpDivRiveNetworkDelegate
import com.yandex.div.rive.RiveCustomViewAdapter
import okhttp3.OkHttpClient
import pet.project.info.databinding.DivFragmentBinding

@Composable
fun InfoScreen(
    goBack: () -> Unit
) {
    val context = LocalContext.current
    val themeContext = ContextThemeWrapper(context, R.style.Theme_TodoList)
    val lifeCycleOwner = LocalLifecycleOwner.current

    AndroidViewBinding(DivFragmentBinding::inflate) {
        val assetReader = AssetReader(context)

        val divJson = assetReader.read("info.json")
        val templatesJson = divJson.optJSONObject("templates")
        val cardJson = divJson.getJSONObject("card")

        val divContext = Div2Context(
            baseContext = themeContext,
            configuration = createDivConfiguration(context, goBack = goBack),
            lifecycleOwner = lifeCycleOwner,
        )

        val divView = Div2ViewFactory(divContext, templatesJson).createView(cardJson)
        fragmentContainerView.addView(divView)
    }
}

private fun createDivConfiguration(context: Context, goBack: () -> Unit): DivConfiguration {
    return DivConfiguration.Builder(PicassoDivImageLoader(context))
        .actionHandler(MyDivActionHandler(goBack))
        .divCustomContainerViewAdapter(
            RiveCustomViewAdapter.Builder(
                context,
                OkHttpDivRiveNetworkDelegate(OkHttpClient.Builder().build())
            ).build()
        )
        .visualErrorsEnabled(true)
        .build()
}