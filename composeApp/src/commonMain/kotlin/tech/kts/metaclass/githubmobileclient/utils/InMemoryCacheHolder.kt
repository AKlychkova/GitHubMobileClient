package tech.kts.metaclass.githubmobileclient.utils

interface InMemoryCacheHolder<T> {
    fun set(cache: T)
    fun get(): T?
    fun remove()
}

class InMemoryCacheHolderImpl<T> : InMemoryCacheHolder<T> {
    private var field: T? = null

    override fun set(cache: T) {
        field = cache
    }

    override fun get(): T? = field

    override fun remove() {
        field = null
    }
}