package tech.kts.metaclass.githubmobileclient.utils

class InMemoryCacheHolder<T> {
    private var field: T? = null

    fun set(cache: T) {
        field = cache
    }

    fun get(): T? = field

    fun remove() {
        field = null
    }
}