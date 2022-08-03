package br.com.dio.app.repositories.data.repositories

import br.com.dio.app.repositories.core.RemoteException
import br.com.dio.app.repositories.data.Services.GithubService
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class RepoRepositoryImpl(private val service: GithubService.GitHubService) : RepoRepository {

    override suspend fun listRepositories(user: String) = flow {
        try {
            val repoList = service.listRepositories(user)
            emit(repoList)
        } catch (ex: HttpException) {
            throw RemoteException(ex.message ?: "Não foi possivel fazer a busca no momento!")
        }
    }
}