package prv.com.countryfacts

import prv.com.countryfacts.models.CountryData
import prv.com.countryfacts.models.CountryFact

object MockDataUtil{

    fun mockData(): CountryData {
        val countryFact1 = CountryFact("Title 1", "Desc 1", "URL 1")
        val countryFact2 = CountryFact("Title 2", "Desc 3", "URL 2")
        return CountryData("Country Data Title 1", arrayListOf(countryFact1, countryFact2))
    }

}