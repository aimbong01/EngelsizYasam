package com.engelsizyasam.presentation.book

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.engelsizyasam.databinding.ItemBookBinding
import com.engelsizyasam.domain.model.BookModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class BookAdapter(private val application: Application) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    private var auth: FirebaseAuth = Firebase.auth
    private var databaseReference: DatabaseReference
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    var countryList = listOf<BookModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var countryFilterList = ArrayList<BookModel>()

    init {
        databaseReference = database.reference.child("profile")

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
        holder.bind(item, application)
    }

    inner class ViewHolder(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BookModel, context: Context) {
            val id = context.resources.getIdentifier("com.engelsizyasam:drawable/book_${item.bookImage}", null, null)
            binding.kitapAdi.text = item.bookName
            binding.kitapYazari.text = item.bookAuthor
            binding.kitapSayfasi.text = (item.bookPageSize + " Sayfa")
            binding.kitapResmi.setImageResource(id)
            val yes = context.resources.getIdentifier("com.engelsizyasam:drawable/book_yes", null, null)
            val no = context.resources.getIdentifier("com.engelsizyasam:drawable/book_no", null, null)

            val user = auth.currentUser
            val currentUserDb = databaseReference.child(user?.uid!!)
            val bookRef = currentUserDb.child("book")
            bookRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.child(item.bookId.toString()).value == null) {
                        bookRef.child(item.bookId.toString()).setValue("0")

                    } else {
                        val bookState = snapshot.child(item.bookId.toString()).value.toString()

                        if (bookState == "1") {
                            binding.state.setBackgroundResource(yes)
                        } else {
                            binding.state.setBackgroundResource(no)
                        }

                        binding.state.setOnClickListener {
                            if (bookState == "1") {
                                bookRef.child(item.bookId.toString()).setValue("0")
                                binding.state.setBackgroundResource(yes)
                            } else {
                                bookRef.child(item.bookId.toString()).setValue("1")
                                binding.state.setBackgroundResource(no)
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

            binding.pdfButton.setOnClickListener {
                it.findNavController().navigate(BookFragmentDirections.actionBookFragmentToBookDetailFragment(item.bookId))
            }
            binding.sesliButton.setOnClickListener {
                it.findNavController().navigate(BookFragmentDirections.actionBookFragmentToBookVoiceDetailFragment(item.bookId))
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBookBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = when (count) {
        0 -> countryList.size
        else -> countryFilterList.size
    }

}
