package tech.kts.metaclass.githubmobileclient.platform

actual fun getFilesDir(): String = appContext.filesDir.absolutePath

actual fun getCacheDir(): String = appContext.cacheDir.absolutePath