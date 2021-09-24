package com.engelsizyasam.adapter

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.engelsizyasam.model.BookModel
import com.engelsizyasam.databinding.CardItemBookBinding

class BookAdapter(private val application: Application, private val clickListener: BookListener) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    var countryList = listOf<BookModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var countryFilterList = ArrayList<BookModel>()

    init {
        countryFilterList.addAll(countryList)
    }

    var count = 0

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                count = 0
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    countryFilterList = countryList as ArrayList<BookModel>
                } else {
                    val resultList = ArrayList<BookModel>()
                    for (row in countryList) {
                        if (row.bookName.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    countryFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                count = 1
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results!!.count > 0) {
                    countryFilterList = results?.values as ArrayList<BookModel>
                }
                notifyDataSetChanged()
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = when (count) {
            0 -> countryList[position]
            else -> countryFilterList[position]
        }

        holder.bind(item, application, clickListener)
    }

    class ViewHolder private constructor(val binding: CardItemBookBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BookModel, context: Context, clickListener: BookListener) {
            binding.bookModel = item
            val id = context.resources.getIdentifier("com.engelsizyasam:drawable/book_${item.bookImage}", null, null)
            binding.kitapAdi.text = item.bookName
            binding.kitapYazari.text = item.bookAuthor
            binding.kitapResmi.setImageResource(id)
            binding.clickListener = clickListener

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardItemBookBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount() = when (count) {
        0 -> countryList.size
        else -> countryFilterList.size
    }

}

class BookListener(val clickListener: (bookId: Int) -> Unit) {
    fun onClick(bookModel: BookModel) = clickListener(bookModel.bookId)
}
