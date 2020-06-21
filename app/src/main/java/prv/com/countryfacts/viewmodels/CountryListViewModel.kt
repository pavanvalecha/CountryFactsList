package prv.com.countryfacts.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import prv.com.countryfacts.base.BaseViewModel
import prv.com.countryfacts.db.CountryDataDAO
import prv.com.countryfacts.models.CountryData
import prv.com.countryfacts.remote.CountryFactsAPI
import prv.com.countryfacts.util.SharedPreferenceHelper

class CountryListViewModel(application: Application,
                           val countryFactsAPI: CountryFactsAPI,
                           val countryDataDAO: CountryDataDAO) : BaseViewModel(application) {

    private var refreshTime = 2 * 60 * 1000 * 1000 * 1000L
    private val disposable = CompositeDisposable()
    private var prefHelper = SharedPreferenceHelper(getApplication())

    val countryData = MutableLiveData<CountryData>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(){
        val updateTime = prefHelper.getUpdateTime()
        if (updateTime != null && updateTime != 0L && (System.nanoTime() - updateTime) < refreshTime)
        {
            fetchFromDB()
        } else {
            fetchFromRemote()
        }
    }

    private fun fetchFromRemote(){
        disposable.add(
            countryFactsAPI.getCountryFactData().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith( object: DisposableSingleObserver<CountryData>(){
                    override fun onSuccess(data: CountryData) {
                        countryData.value = data
                        storeCountryDataDB(data)
                    }

                    override fun onError(e: Throwable) {
                        error.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
        Toast.makeText(getApplication(), "Fetching Data from Remote", Toast.LENGTH_LONG).show()
    }

    private fun storeCountryDataDB(countryData: CountryData){
        launch {
            val insertedid = countryDataDAO.insertFactsAndData(countryData)
            prefHelper.saveUpdateTime(System.nanoTime())
            Log.d("storeCountryDataDB()", "Inserted CountryData with ID - $insertedid")
        }
    }

    private fun fetchFromDB(){
        loading.value = true
        launch {
            val countryData = countryDataDAO.getCountryDataDB()
            countryDatasRetrived(countryData)
        }
        Toast.makeText(getApplication(), "Fetching Data from DB", Toast.LENGTH_LONG).show()
    }

    private fun countryDatasRetrived(data: CountryData){
        countryData.postValue(data)
        error.postValue(false)
        loading.postValue(false)
    }

    fun refreshBypassCache(){
        fetchFromRemote()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}
