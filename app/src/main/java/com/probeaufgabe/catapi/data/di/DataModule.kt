package com.probeaufgabe.catapi.data.di

import androidx.room.Room
import com.probeaufgabe.catapi.BuildConfig
import com.probeaufgabe.catapi.data.local.CatDatabase
import com.probeaufgabe.catapi.data.remote.CatApi
import com.probeaufgabe.catapi.data.repository.BreedRepositoryImpl
import com.probeaufgabe.catapi.domain.repository.BreedRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatApi::class.java)
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            CatDatabase::class.java,
            "cat_database"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    single {
        get<CatDatabase>().breedDao()
    }

    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val authInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val requestWithAuth = originalRequest.newBuilder()
                .addHeader("x-api-key", BuildConfig.CAT_API_KEY)
                .build()

            chain.proceed(requestWithAuth)
        }

        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    single<BreedRepository> {
        BreedRepositoryImpl(
            db = get(),
            api = get()
        )
    }
}