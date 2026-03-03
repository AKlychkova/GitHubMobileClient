package tech.kts.metaclass.githubmobileclient

import androidx.compose.ui.window.ComposeUIViewController
import coil3.SingletonImageLoader
import tech.kts.metaclass.githubmobileclient.ui.App
import tech.kts.metaclass.githubmobileclient.utils.createImageLoader

fun MainViewController() = ComposeUIViewController {
    SingletonImageLoader.setSafe { context ->
        createImageLoader(context)
    }
    App()
}