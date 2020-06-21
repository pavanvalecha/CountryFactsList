package prv.com.countryfacts.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import prv.com.countryfacts.viewmodels.CountryListViewModel

val viewModelModule = module {
    viewModel{ CountryListViewModel(get(), get(), get()) }
}