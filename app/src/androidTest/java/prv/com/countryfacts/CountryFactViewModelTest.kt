package prv.com.countryfacts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import prv.com.countryfacts.db.CountryDataDAO
import prv.com.countryfacts.db.CountryFactsDB
import prv.com.countryfacts.models.CountryData
import prv.com.countryfacts.remote.CountryFactsAPI
import prv.com.countryfacts.viewmodels.CountryListViewModel

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CountryFactViewModelTest {

    private lateinit var mDatabase: CountryFactsDB
    private lateinit var countryFactsDAO: CountryDataDAO
    private lateinit var viewModel: CountryListViewModel
    private val countryFactsAPI : CountryFactsAPI = Mockito.mock(CountryFactsAPI::class.java)
    val appContext = ApplicationProvider.getApplicationContext<CountryFactsApp>()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init(){
        mDatabase = Room.inMemoryDatabaseBuilder(
            appContext,
            CountryFactsDB::class.java
        ).build()

        countryFactsDAO = mDatabase.countryDataDAO()

        viewModel = CountryListViewModel(appContext, countryFactsAPI, countryFactsDAO)
    }

    @Test
    fun fetchDataAndObserve() =  runBlocking{
        val mockData = MockDataUtil.mockData()
        val mockSingle = Single.just<CountryData>(mockData)
        whenever(countryFactsAPI.getCountryFactData()).thenReturn(mockSingle)
        val observer: Observer<CountryData> = mock()
        viewModel.countryData.observeForever (observer)
        viewModel.refresh()
        verify(observer).onChanged(mockData)
        viewModel.countryData.removeObserver(observer)
    }

}
