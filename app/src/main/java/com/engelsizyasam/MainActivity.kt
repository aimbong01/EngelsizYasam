package com.engelsizyasam


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.engelsizyasam.bookDatabase.Books
import com.engelsizyasam.bookDatabase.DBHelper
import com.engelsizyasam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val db by lazy { DBHelper(this) }

    private lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout

        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

//        db.deleteAllData()
        db.insertData(Books(bookName = "Kürk Mantolu Madonna", bookAuthor = "Sabahattin Ali", bookImage = "madonna", bookPDF = "madonna.pdf"))
        db.insertData(Books(bookName = "İçimizdeki Çocuk", bookAuthor = "Doğan Cüceloğlu", bookImage = "icimizdekicocuk", bookPDF = "icimizdekicocuk.pdf"))
        db.insertData(Books(bookName = "Şeker Portakalı", bookAuthor = "José Mauro de Vasconcelos", bookImage = "sekerportakali", bookPDF = "sekerportakali.pdf"))
        db.insertData(Books(bookName = "Suç ve Ceza", bookAuthor = "Fyodor Mihailoviç Dostoyevski", bookImage = "sucveceza", bookPDF = "sucveceza.pdf"))
        db.insertData(Books(bookName = "Simyacı", bookAuthor = "Paulo Coelho", bookImage = "simyaci", bookPDF = "simyaci.pdf"))

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}