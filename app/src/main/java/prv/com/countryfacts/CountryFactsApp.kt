package prv.com.countryfacts

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import prv.com.countryfacts.di.dbModule
import prv.com.countryfacts.di.networkModule
import prv.com.countryfacts.di.viewModelModule
import timber.log.Timber

class CountryFactsApp : Application(){

    override fun onCreate() {
        super.onCreate()
        initKoin()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

    private fun initKoin(){
        startKoin {
            androidContext(this@CountryFactsApp)
            modules(networkModule)
            modules(viewModelModule)
            modules(dbModule)
        }
    }

}