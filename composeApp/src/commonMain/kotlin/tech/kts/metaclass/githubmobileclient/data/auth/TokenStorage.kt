package tech.kts.metaclass.githubmobileclient.data.auth

import tech.kts.metaclass.githubmobileclient.utils.InMemoryCacheHolder
import tech.kts.metaclass.githubmobileclient.utils.InMemoryCacheHolderImpl

object TokenStorage : InMemoryCacheHolder<AuthToken> by InMemoryCacheHolderImpl()