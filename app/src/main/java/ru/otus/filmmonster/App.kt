package ru.otus.filmmonster

import android.app.Application
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.otus.filmmonster.repository.DBinstance
import ru.otus.filmmonster.repository.FilmsApi
import java.util.concurrent.Executors

class App : Application() {

    lateinit var api: FilmsApi

    override fun onCreate() {
        super.onCreate()

        instance = this

        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()

            .addInterceptor { chain ->
                val response = chain.proceed(
                    chain.request()
                        .newBuilder()
                        .addHeader("accept", "application/json")
                        .addHeader("X-API-KEY", "e6def55d-d34f-45c7-94cb-516d02c0143d")
                        .build()
                )
                return@addInterceptor response
            }
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        api = retrofit.create(FilmsApi::class.java)


        DBinstance.getDBinstance(this)

    }

    companion object {
        const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/"

        lateinit var instance: App
            private set
    }
}