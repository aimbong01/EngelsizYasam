package com.engelsizyasam.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.engelsizyasam.database.BookModel
import com.engelsizyasam.databinding.BookCardItemBinding

class RecyclerAdapter(context: Context,val clickListener: SleepNightListener): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    val mcontext = context
    var data =  listOf<BookModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item,mcontext,clickListener)
    }

    class ViewHolder private constructor(val binding: BookCardItemBinding) : RecyclerView.ViewHolder(binding.root){


        val sleepLength: TextView = binding.kitapAdi
        val quality: TextView = binding.kitapYazari
        val qualityImage: ImageView = binding.kitapResmi

        fun bind(item: BookModel,context: Context,clickListener: SleepNightListener) {
            binding.bookModel = item
            val id = context.resources.getIdentifier("com.engelsizyasam:drawable/${item.bookImage}", null, null)
            sleepLength.text = item.bookName
            quality.text = item.bookAuthor
            qualityImage.setImageResource(id)
            binding.clickListener = clickListener

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BookCardItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
    override fun getItemCount() = data.size

}

class SleepNightListener(val clickListener: (bookId: Int) -> Unit) {
    fun onClick(bookModel: BookModel) = clickListener(bookModel.bookId)
}
