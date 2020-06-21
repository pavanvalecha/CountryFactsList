package prv.com.countryfacts.di

import org.koin.dsl.module
import prv.com.countryfacts.remote.CountryFactsAPI
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module{
    single {
        Retrofit.Builder()
            .baseUrl("https://dl.dropboxusercontent.com/s/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CountryFactsAPI::class.java)
    }
}