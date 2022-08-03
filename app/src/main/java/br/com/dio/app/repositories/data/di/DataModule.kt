package br.com.dio.app.repositories.data.di

import android.util.Log
import br.com.dio.app.repositories.data.Services.GithubService
import br.com.dio.app.repositories.data.repositories.RepoRepository
import br.com.dio.app.repositories.data.repositories.RepoRepositoryImpl
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.experimental.builder.create
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object DataModule {
    private const val OK_HTTP = "okhttp"


    fun load(){
        loadKoinModules(networkModules() + repositoriesModule())
    }



    private fun networkModules(): Module{
        return module {
            single {

               val interceptor = HttpLoggingInterceptor{
                    Log.e(OK_HTTP,it)
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }

            single {
                GsonConverterFactory.create(GsonBuilder().create())
            }

            single {
                createService<GithubService>(get(), get())
            }



        }
    }

    private fun repositoriesModule(): Module{
        return module {
            single <RepoRepository> { RepoRepositoryImpl(get ())

            }
        }
    }


    private inline fun <reified T> createService(client: OkHttpClient, factory: GsonConverterFactory):T{
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .addConverterFactory(factory)
            .build().create(T::class.java)
    }



}