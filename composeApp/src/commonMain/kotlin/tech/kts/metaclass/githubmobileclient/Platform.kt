package tech.kts.metaclass.githubmobileclient

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform