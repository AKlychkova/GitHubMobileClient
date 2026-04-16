package tech.kts.metaclass.githubmobileclient.platform

interface DirectoriesProvider {
    fun getFilesDir(): String
    fun getCacheDir(): String
}