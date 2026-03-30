package tech.kts.metaclass.githubmobileclient.utils

import io.ktor.utils.io.CancellationException

internal inline fun <R> runSuspendCatching(block: () -> R): Result<R> = try {
    Result.success(block())
} catch(e: CancellationException) {
    throw e
} catch(e: Throwable) {
    Result.failure(e)
}