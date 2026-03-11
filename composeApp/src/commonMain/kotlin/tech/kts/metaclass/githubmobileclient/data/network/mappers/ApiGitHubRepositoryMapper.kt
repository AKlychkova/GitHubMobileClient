package tech.kts.metaclass.githubmobileclient.data.network.mappers

import tech.kts.metaclass.githubmobileclient.data.network.models.ApiGitHubRepository
import tech.kts.metaclass.githubmobileclient.entities.GitHubRepository
import tech.kts.metaclass.githubmobileclient.entities.ProgrammingLanguage

class ApiGitHubRepositoryMapper(
    private val apiOwnerMapper: ApiOwnerMapper
) {
    fun toDomainModel(repository: ApiGitHubRepository) : GitHubRepository {
        return GitHubRepository(
            id = repository.id,
            name = repository.name,
            description = repository.description,
            language = mapLanguage(repository.language),
            stars = repository.stars,
            owner = apiOwnerMapper.toDomainModel(repository.owner)
        )
    }

    private fun mapLanguage(rawLanguage: String): ProgrammingLanguage {
        return when(rawLanguage) {
            "Kotlin" -> ProgrammingLanguage.KOTLIN
            "Java" -> ProgrammingLanguage.JAVA
            "Swift" -> ProgrammingLanguage.SWIFT
            "Objective-C" -> ProgrammingLanguage.OBJECTIVE_C
            "JavaScript" -> ProgrammingLanguage.JAVASCRIPT
            "TypeScript" -> ProgrammingLanguage.TYPESCRIPT
            "Python" -> ProgrammingLanguage.PYTHON
            "C" -> ProgrammingLanguage.C
            "C++" -> ProgrammingLanguage.CPP
            "C#" -> ProgrammingLanguage.C_SHARP
            "Go" -> ProgrammingLanguage.GO
            "Rust" -> ProgrammingLanguage.RUST
            "PHP" -> ProgrammingLanguage.PHP
            "Ruby" -> ProgrammingLanguage.RUBY
            "Dart" -> ProgrammingLanguage.DART
            "Scala" -> ProgrammingLanguage.SCALA
            "Shell" -> ProgrammingLanguage.SHELL
            "HTML" -> ProgrammingLanguage.HTML
            "CSS" -> ProgrammingLanguage.CSS
            "Hack" -> ProgrammingLanguage.HACK
            "Groovy" -> ProgrammingLanguage.GROOVY
            "PowerShell" -> ProgrammingLanguage.POWERSHELL
            else -> ProgrammingLanguage.UNKNOWN
        }
    }
}