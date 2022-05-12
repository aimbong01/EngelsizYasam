package com.engelsizyasam.presentation.seriesdetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.engelsizyasam.R
import com.engelsizyasam.databinding.ItemSeriesDetailBinding
import com.engelsizyasam.domain.model.SeriesDetail
import com.squareup.picasso.Picasso

class SeriesDetailAdapter(private val context: Context) : RecyclerView.Adapter<SeriesDetailAdapter.ViewHolder>() {

    var data = listOf<SeriesDetail>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    inner class ViewHolder(val binding: ItemSeriesDetailBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SeriesDetail) {
            binding.title.text = item.title
            try {
                Picasso.get().load(item.url).into(binding.image)
            } catch (e: Exception) {
                Picasso.get().load(R.drawable.null_video).into(binding.image)

            }

            binding.image.setOnClickListener {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$it")))
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSeriesDetailBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = data.size

}
