package com.engelsizyasam.adapter

import android.annotation.SuppressLint
import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.engelsizyasam.databinding.CardItemSeriesBinding
import com.engelsizyasam.model.SeriesModel
import com.squareup.picasso.Picasso

class SeriesAdapter(private val application: Application, private val clickListener: SeriesListener) : RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {

    var data: List<SeriesModel.İtem> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, application, clickListener)

    }

    class ViewHolder private constructor(val binding: CardItemSeriesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SeriesModel.İtem, application: Application, clickListener: SeriesListener) {
            binding.seriesModel = item
            Picasso.get().load(item.snippet.thumbnails.medium.url).into(binding.image)
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

class SeriesListener(val clickListener: (link: String, link2: String) -> Unit) {
    fun onClick(seriesModel: SeriesModel.İtem) = clickListener(seriesModel.id, seriesModel.snippet.title)
}