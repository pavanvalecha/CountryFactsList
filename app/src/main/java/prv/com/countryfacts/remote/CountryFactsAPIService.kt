package prv.com.countryfacts.remote

import io.reactivex.Single
import prv.com.countryfacts.models.CountryData
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountryFactsAPIService {
     private val LIST_DEMO_API_URL = "https://dl.dropboxusercontent.com/s/"
    private val api = Retrofit.Builder()
        .baseUrl(LIST_DEMO_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CountryFactsAPI::class.java)

    fun getCountryFactsData(): Single<CountryData>{
        return api.getCountryFactData()
    }

}