package tech.kts.metaclass.githubmobileclient

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import tech.kts.metaclass.githubmobileclient.di.initKoin
import tech.kts.metaclass.githubmobileclient.utils.createImageLoader

class AndroidApp : Application(), SingletonImageLoader.Factory {
    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return createImageLoader(context)
    }

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@AndroidApp)
            androidLogger()
        }
    }
}