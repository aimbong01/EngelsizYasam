package com.engelsizyasam.presentation.news

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.engelsizyasam.databinding.ItemNewsBinding
import com.engelsizyasam.domain.model.News
import com.squareup.picasso.Picasso

class NewsAdapter(private val context: Context) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var data = listOf<News>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, context)
    }

    class ViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: News, context: Context) {
            Picasso.get().load(item.urlToImage).into(binding.haberResmi)
            binding.haberBaslik.text = item.title
            binding.constraint.setOnClickListener {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("${item.url}")))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = data.size

}