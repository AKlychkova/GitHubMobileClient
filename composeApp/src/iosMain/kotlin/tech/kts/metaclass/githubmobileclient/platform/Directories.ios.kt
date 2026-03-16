package tech.kts.metaclass.githubmobileclient.platform

import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

actual fun getFilesDir(): String {
    return NSSearchPathForDirectoriesInDomains(
        NSDocumentDirectory, NSUserDomainMask, true
    ).first() as String
}

actual fun getCacheDir(): String {
    return NSSearchPathForDirectoriesInDomains(
        platform.Foundation.NSCachesDirectory, NSUserDomainMask, true
    ).first() as String
}