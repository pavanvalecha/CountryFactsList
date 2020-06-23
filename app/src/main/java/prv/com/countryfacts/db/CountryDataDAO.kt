package prv.com.countryfacts.db

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import prv.com.countryfacts.models.CountryData
import prv.com.countryfacts.models.CountryFact
import timber.log.Timber

@Dao
interface CountryDataDAO {

    fun insertFactsAndData(countryData: CountryData) {
        val dataId = insertAll( CountryDataEntity(title = countryData.title) )
            countryData.rows.forEach { fact ->
                Timber.d ("forEach fact - Inserted CountryFact with ID - ${fact.title}")
                insertCountryFact(
                    CountryFactEntity(
                        title = fact.title,
                        description = fact.description,
                        imageHref = fact.imageHref,
                        countryDataId = dataId
                    )
                )
            }
        }


    fun getCountryDataDB(): CountryData {
        val dataDB = getFactAndDataEntity()
        dataDB.apply {
            var factsArray: ArrayList<CountryFact> = arrayListOf()
            dataDB.factsList.forEach{
                factsArray.add( CountryFact(it.title, it.description, it.imageHref) )
            }
            val data = CountryData(dataDB.countryDataEntity.title, factsArray)
            return data
        }
    }

    /*@Insert(onConflict = REPLACE)
    fun insertCountryFacts(cities: List<CountryFact>): List<Long>*/

    @Query("SELECT * FROM CountryDataEntity")
    fun getFactAndDataEntity(): FactAndDataEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(user: CountryDataEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountryFact(playlist: CountryFactEntity)

}