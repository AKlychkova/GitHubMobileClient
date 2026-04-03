package tech.kts.metaclass.githubmobileclient.data.network.auth

interface AuthConfig {
    val authUri: String
    val tokenUri: String
    val scopes: List<String>
    val clientId: String
    val clientSecret: String
    val callbackUrl: String
}