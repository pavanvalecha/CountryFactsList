package prv.com.countryfacts

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import prv.com.countryfacts.db.CountryFactsDB
import org.hamcrest.core.Is.`is`

@RunWith(AndroidJUnit4::class)
class CountryFactsDAOTest {

    private lateinit var mDatabase: CountryFactsDB

    @Before
    fun init(){
        mDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CountryFactsDB::class.java
        ).build()
    }

    @Test
    fun insertDataInDBAndVerifySavedData() = runBlocking {
        val mockData = MockDataUtil.mockData()
        mDatabase.countryDataDAO().insertFactsAndData(mockData)
        val savedData = mDatabase.countryDataDAO().getCountryDataDB()
        MatcherAssert.assertThat(savedData.toString(), `is`(mockData.toString()))
    }


}