package tech.kts.metaclass.githubmobileclient.data.network.models

data class Page<T> (
    val items: List<T>,
    val prevPageNum: Int?,
    val nextPageNum: Int?
)