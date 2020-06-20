package prv.com.countryfacts.models

data class CountryData(
    val title: String,
    val rows: List<CountryFact>
)