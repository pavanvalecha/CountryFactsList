package prv.com.countryfacts.remote

import io.reactivex.Single
import prv.com.countryfacts.models.CountryData
import retrofit2.http.GET

interface CountryFactsAPI {

    @GET("2iodh4vg0eortkl/facts.json")
    fun getCountryFactData(): Single<CountryData>

}