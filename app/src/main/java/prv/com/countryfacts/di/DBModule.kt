package prv.com.countryfacts.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import prv.com.countryfacts.db.CountryFactsDB

var dbModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            CountryFactsDB::class.java,
            "countryfactsdatabase"
        ).build()
    }

    single { get<CountryFactsDB>().countryDataDAO() }

}