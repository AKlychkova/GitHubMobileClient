package tech.kts.metaclass.githubmobileclient.entities

data class Email(private val value: String) {

    init {
        require(value.isNotBlank()) { "Email cannot be blank" }
        require(REGEX.matches(value.trim())) { "Invalid email format: $value" }
    }

    override fun toString(): String = value

    companion object {
        private val REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    }
}