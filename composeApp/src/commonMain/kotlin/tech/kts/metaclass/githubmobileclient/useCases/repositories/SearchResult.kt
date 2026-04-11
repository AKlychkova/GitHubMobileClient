package tech.kts.metaclass.githubmobileclient.useCases.repositories

sealed interface SearchResult<T> {
    data class Success<T>(
        val data: List<T>,
        val nextPageNum: Int?,
        val prevPageNum: Int?
    ) : SearchResult<T>

    data class Cached<T>(
        val data: List<T>,
        val cause: Throwable
    ) : SearchResult<T>

    data class Failure<T>(
        val cause: Throwable
    ) : SearchResult<T>
}