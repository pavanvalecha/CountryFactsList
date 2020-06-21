package prv.com.countryfacts.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import prv.com.countryfacts.R
import prv.com.countryfacts.databinding.ListItemCountryFactBinding
import prv.com.countryfacts.models.CountryFact

class CountryFactsListAdapter(val factsList: ArrayList<CountryFact>): RecyclerView.Adapter<CountryFactsListAdapter.CountryFactsViewHolder>() {

    class CountryFactsViewHolder(var view: ListItemCountryFactBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryFactsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ListItemCountryFactBinding>(inflater, R.layout.list_item_country_fact, parent, false)
        return CountryFactsViewHolder(view)
    }

    override fun getItemCount(): Int = factsList.size

    override fun onBindViewHolder(holder: CountryFactsViewHolder, position: Int) {
        holder.view.fact = factsList[position]
    }

    fun updateCountryFactList(updatedFactsList: ArrayList<CountryFact>){
        factsList.clear()
        factsList.addAll(updatedFactsList)
        notifyDataSetChanged()
    }

}