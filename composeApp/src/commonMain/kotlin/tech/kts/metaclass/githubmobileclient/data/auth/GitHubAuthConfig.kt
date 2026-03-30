package tech.kts.metaclass.githubmobileclient.data.auth

object GitHubAuthConfig: AuthConfig {
    override val authUri = "https://github.com/login/oauth/authorize"
    override val tokenUri = "https://github.com/login/oauth/access_token"
    override val scopes = listOf("user", "repo")
    override val clientId = "Ov23li4RnGxcZmbGh3rH"
    override val clientSecret = "39b1587e61d41c3e4498889779357d3beb78a3fa"
    override val callbackUrl = "tech.kts.metaclass.oauth://github.com/callback"
}
