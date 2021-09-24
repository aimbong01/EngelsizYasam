package com.engelsizyasam.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.engelsizyasam.databinding.CardItemSeriesBinding
import com.engelsizyasam.model.SeriesModel

class SeriesAdapter(private val application: Application, private val clickListener: SeriesListener) : RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {

    var data = arrayListOf<SeriesModel>()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, application, clickListener)
    }

    class ViewHolder private constructor(val binding: CardItemSeriesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SeriesModel, application: Application, clickListener: SeriesListener) {
            binding.seriesModel = item
            val id = application.resources.getIdentifier("com.engelsizyasam:drawable/series_${item.seriesImage}", null, null)
            binding.image.setImageResource(id)
            binding.clickListener = clickListener

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardItemSeriesBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount() = data.size

}

class SeriesListener(val clickListener: (link: String, link2: String, link3: Int) -> Unit) {
    fun onClick(seriesModel: SeriesModel) = clickListener(seriesModel.seriesId, seriesModel.seriesName, seriesModel.seriesPage)
}