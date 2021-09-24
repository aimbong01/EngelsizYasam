package com.engelsizyasam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.engelsizyasam.R
import com.engelsizyasam.databinding.CardItemScholarBinding
import com.engelsizyasam.model.ScholarModel
import com.engelsizyasam.model.SeriesDetailModel
import com.squareup.picasso.Picasso

class ScholarAdapter(private val clickListener: ScholarListener) : RecyclerView.Adapter<ScholarAdapter.ViewHolder>() {

    var data = listOf<ScholarModel.OrganicResult>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding: CardItemScholarBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ScholarModel.OrganicResult, clickListener: ScholarListener) {

            if (item.resources.isNotEmpty()) {
                if (item.resources[0].title == "dergipark.org.tr") {
                    val title = item.title
                    binding.scholarModel = item
                    binding.makaleAdi.text = title[0].uppercase() + title.substring(1).lowercase()
                    binding.makaleAciklama.text = item.publicationİnfo.summary
                    binding.clickListener = clickListener
                } else {
                    binding.constraint.visibility = View.GONE
                    binding.constraint.maxHeight = 0
                }
            } else {
                binding.constraint.visibility = View.GONE
                binding.constraint.maxHeight = 0
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardItemScholarBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount() = data.size

}

class ScholarListener(val clickListener: (link: String) -> Unit) {
    fun onClick(scholarModel: ScholarModel.OrganicResult) = clickListener(scholarModel.resources[0].link)
}