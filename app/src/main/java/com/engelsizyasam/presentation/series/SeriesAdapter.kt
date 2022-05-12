package com.engelsizyasam.presentation.series

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.engelsizyasam.databinding.ItemSeriesBinding
import com.engelsizyasam.domain.model.Series
import com.squareup.picasso.Picasso

class SeriesAdapter : RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {

    var data = listOf<Series>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    var seriesFilterList = ArrayList<Series>()

    init {
        seriesFilterList.addAll(data)
    }

    var count = 0

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                count = 0
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    seriesFilterList = data as ArrayList<Series>
                } else {
                    val resultList = ArrayList<Series>()
                    for (row in data) {
                        if (row.title?.toLowerCase()?.contains(constraint.toString().toLowerCase()) == true) {
                            resultList.add(row)
                        }
                    }
                    seriesFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = seriesFilterList
                count = 1
                return filterResults

            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results!!.count > 0) {
                    seriesFilterList = results.values as ArrayList<Series>
                }
                notifyDataSetChanged()

            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = when (count) {
            0 -> data[position]
            else -> seriesFilterList[position]
        }
        holder.bind(item)

    }

    inner class ViewHolder(val binding: ItemSeriesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Series) {
            Picasso.get().load(item.url).into(binding.image)
            binding.image.setOnClickListener {
                it.findNavController().navigate(SeriesFragmentDirections.actionSeriesFragmentToSeriesDetailFragment(item.id, item.title))

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSeriesBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = when (count) {
        0 -> data.size
        else -> seriesFilterList.size
    }

}