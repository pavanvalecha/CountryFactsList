package prv.com.countryfacts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_country_list.*
import prv.com.countryfacts.R
import prv.com.countryfacts.models.CountryFact
import prv.com.countryfacts.view.adapter.CountryFactsListAdapter
import prv.com.countryfacts.viewmodels.CountryListViewModel

class CountryListFragment : Fragment() {

    private lateinit var viewModel: CountryListViewModel
    private val countryFactsListAdapter = CountryFactsListAdapter(arrayListOf<CountryFact>())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_country_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CountryListViewModel::class.java)
        viewModel.refresh()

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
        (activity as MainActivity)?.setActionBarTitle(title)
    }

    fun observeViewModels(){
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
