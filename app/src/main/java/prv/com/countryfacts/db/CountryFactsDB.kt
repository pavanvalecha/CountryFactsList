package prv.com.countryfacts.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import prv.com.countryfacts.models.CountryData
import prv.com.countryfacts.models.CountryFact

@Database(entities = arrayOf(CountryDataEntity::class, CountryFactEntity::class), version = 1, exportSchema = false)
abstract class CountryFactsDB : RoomDatabase(){
    abstract fun countryDataDAO(): CountryDataDAO
}