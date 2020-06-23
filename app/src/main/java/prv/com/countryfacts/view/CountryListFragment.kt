package prv.com.countryfacts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_country_list.*
import org.koin.android.viewmodel.ext.android.getViewModel
import prv.com.countryfacts.R
import prv.com.countryfacts.databinding.FragmentCountryListBinding
import prv.com.countryfacts.models.CountryFact
import prv.com.countryfacts.view.adapter.CountryFactsListAdapter
import prv.com.countryfacts.viewmodels.CountryListViewModel
import timber.log.Timber

class CountryListFragment : Fragment() {

    private lateinit var viewModel: CountryListViewModel
    private val countryFactsListAdapter = CountryFactsListAdapter(arrayListOf<CountryFact>())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("onCreateView()")
        return inflater.inflate(R.layout.fragment_country_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated()")
        viewModel = getViewModel<CountryListViewModel>().apply { refresh() }

        countryRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countryFactsListAdapter
        }

        observeViewModels()

        refreshLayout.setOnRefreshListener {
            countryRecyclerView.visibility = View.GONE
            listError.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            viewModel.refreshBypassCache()
            refreshLayout.isRefreshing = false
        }

    }

    private fun setTitle(title: String){
        Timber.d("setTitle()")
        (activity as MainActivity)?.setActionBarTitle(title)
    }

    private fun showToast(toastMsg: String){
        Toast.makeText(this@CountryListFragment.context, toastMsg, Toast.LENGTH_LONG).show()
    }

    fun observeViewModels(){
        Timber.d("observeViewModels()")
        viewModel.countryData.observe(this, Observer {facts ->
            facts?.let {
                countryRecyclerView.visibility = View.VISIBLE
                listError.visibility = View.GONE
                progressBar.visibility = View.GONE
                setTitle(it.title)
                countryFactsListAdapter.updateCountryFactList(facts.rows)
            }
        })

        viewModel.error.observe(this, Observer{isError ->
            isError?.let {
                listError.visibility  = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.toastLiveData.observe(this, Observer{toastMsg ->
            toastMsg?.let {
                showToast(it)
            }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                progressBar.visibility = if(it) View.VISIBLE else View.GONE
                if(it){
                    listError.visibility = View.GONE
                    countryRecyclerView.visibility = View.GONE
                }
            }

        })


    }

}
