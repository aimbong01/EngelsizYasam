package com.engelsizyasam.adapter

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.engelsizyasam.BaseApplication
import com.engelsizyasam.databinding.CardItemNewsBinding
import com.engelsizyasam.model.NewsModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class NewsAdapter(private val context: Context) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var data = listOf<NewsModel.Article?>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, context)
    }

    class ViewHolder private constructor(val binding: CardItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsModel.Article?, context: Context) {
            Picasso.get().load(item?.urlToImage).into(binding.haberResmi)
            binding.haberBaslik.text = item?.title
            binding.constraint.setOnClickListener {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("${item?.url}")))
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardItemNewsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount() = data.size

}