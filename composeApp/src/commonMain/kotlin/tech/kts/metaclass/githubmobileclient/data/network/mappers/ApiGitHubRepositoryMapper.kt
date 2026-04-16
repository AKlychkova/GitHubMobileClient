package tech.kts.metaclass.githubmobileclient.data.network.mappers

import tech.kts.metaclass.githubmobileclient.data.network.models.ApiGitHubRepository
import tech.kts.metaclass.githubmobileclient.entities.GitHubRepository

class ApiGitHubRepositoryMapper(
    private val apiUserMapper: ApiUserMapper,
    private val apiProgrammingLanguageMapper: ApiProgrammingLanguageMapper
) {
    fun toDomainModel(repository: ApiGitHubRepository) : GitHubRepository {
        return GitHubRepository(
            id = repository.id,
            name = repository.name,
            description = repository.description,
            visibility = repository.visibility,
            language = apiProgrammingLanguageMapper.toDomainModel(repository.language),
            stars = repository.stars,
            forks = repository.forks,
            openIssues = repository.openIssues,
            owner = apiUserMapper.toDomainModel(repository.owner)
        )
    }
}