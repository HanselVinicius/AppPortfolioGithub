package br.com.dio.app.repositories.data.Services

import br.com.dio.app.repositories.data.model.Repo
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    interface GitHubService {
        @GET("users/{user}/repos")
        suspend fun listRepositories(@Path("user") user: String) : List<Repo>
    }
}