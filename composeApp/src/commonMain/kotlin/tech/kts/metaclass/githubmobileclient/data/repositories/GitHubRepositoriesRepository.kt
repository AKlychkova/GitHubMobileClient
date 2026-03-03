package tech.kts.metaclass.githubmobileclient.data.repositories

import tech.kts.metaclass.githubmobileclient.entities.GitHubRepository
import tech.kts.metaclass.githubmobileclient.entities.Owner
import tech.kts.metaclass.githubmobileclient.entities.ProgrammingLanguage


interface GitHubRepositoriesRepository {
    fun getRepositories() : List<GitHubRepository>
}

class GitHubRepositoriesRepositoryImpl : GitHubRepositoriesRepository {
    override fun getRepositories(): List<GitHubRepository> {
        return listOf(
            GitHubRepository(
                name = "My first repository",
                description = "Test project",
                language = ProgrammingLanguage.KOTLIN,
                stars = 5,
                owner = Owner(
                    username = "AKlychkova",
                    avatarUrl = "https://avatars.githubusercontent.com/u/90353866?v=4"
                )
            ),
            GitHubRepository(
                name = "Mao project",
                description = "No description",
                language = ProgrammingLanguage.JAVA,
                stars = 3,
                owner = Owner(
                    username = "Mursik",
                    avatarUrl = "https://cataas.com/cat/cute?width=400&height=400"
                )
            ),
            GitHubRepository(
                name = "ProstokvashinoApp",
                description = "Чтобы отрефакторить плохой код, нужно сначала написать новый код, а у нас времени нет",
                language = ProgrammingLanguage.C_SHARP,
                stars = 3,
                owner = Owner(
                    username = "Matroskin",
                    avatarUrl = "https://cataas.com/cat/cute?width=400&height=400"
                )
            ),
            GitHubRepository(
                name = "FriendshipIsMagic",
                description = "Ребята, давайте жить дружно!",
                language = ProgrammingLanguage.GO,
                stars = 4,
                owner = Owner(
                    username = "Leopold",
                    avatarUrl = "https://cataas.com/cat/cute?width=400&height=400"
                )
            ),
            GitHubRepository(
                name = "FireStation",
                description = "Тили-тили-тили-бом, не сгорел чтоб больше дом",
                language = ProgrammingLanguage.PYTHON,
                stars = 3,
                owner = Owner(
                    username = "Koshka",
                    avatarUrl = "https://cataas.com/cat/cute?width=400&height=400"
                )
            )
        )
    }
}
