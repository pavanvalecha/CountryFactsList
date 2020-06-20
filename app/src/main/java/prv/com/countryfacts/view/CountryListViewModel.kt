package prv.com.countryfacts.view

import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import prv.com.countryfacts.base.BaseViewModel
import prv.com.countryfacts.models.CountryData
import prv.com.countryfacts.models.CountryFact
import prv.com.countryfacts.remote.CountryFactsAPIService

class CountryListViewModel(application: Application) : BaseViewModel(application) {

    private val disposable = CompositeDisposable()
    private val countryFactsAPIService = CountryFactsAPIService()

    val countryData = MutableLiveData<CountryData>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun fetchFromRemote(){
        disposable.add(
            countryFactsAPIService.getCountryFactsData().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith( object: DisposableSingleObserver<CountryData>(){
                    override fun onSuccess(data: CountryData) {
                        countryData.value = data
                    }

                    override fun onError(e: Throwable) {
                        error.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

}
