package tech.kts.metaclass.githubmobileclient.platform

import android.content.Context

class AndroidDirectoriesProvider(
    private val context: Context
): DirectoriesProvider {
    override fun getFilesDir(): String = context.filesDir.absolutePath
    override fun getCacheDir(): String = context.cacheDir.absolutePath
}