package tech.kts.metaclass.githubmobileclient.data.auth

import tech.kts.metaclass.githubmobileclient.utils.InMemoryCacheHolder

val TokenStorage = InMemoryCacheHolder<AuthToken>()