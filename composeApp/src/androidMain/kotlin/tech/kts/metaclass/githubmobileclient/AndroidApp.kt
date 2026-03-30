package tech.kts.metaclass.githubmobileclient

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import tech.kts.metaclass.githubmobileclient.utils.createImageLoader

class AndroidApp : Application(), SingletonImageLoader.Factory {
    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return createImageLoader(context)
    }

    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())
    }
}