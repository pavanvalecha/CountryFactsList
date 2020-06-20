package prv.com.countryfacts.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_country_list.*

import prv.com.countryfacts.R

class CountryListFragment : Fragment() {

    companion object {
        fun newInstance() = CountryListFragment()
    }

    private lateinit var viewModel: CountryListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_country_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CountryListViewModel::class.java)
        viewModel.fetchFromRemote()
        observeViewModels()

    }

    fun observeViewModels(){
        viewModel.countryData.observe(this, Observer {facts ->
            facts?.let {
                countryRecyclerView.visibility = View.VISIBLE
                listError.visibility = View.GONE
                //factsListAdapter.updateDogsList(facts)
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
