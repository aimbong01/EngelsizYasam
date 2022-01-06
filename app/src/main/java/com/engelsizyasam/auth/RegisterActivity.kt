package com.engelsizyasam.auth

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.engelsizyasam.databinding.ActivityRegisterBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private var storage: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private lateinit var uri: Uri
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        uri = Uri.parse("https:///i.hizliresim.com/rbzlchf.jpg")
        Log.d("aley", uri.toString())

        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("profile")

        storage = FirebaseStorage.getInstance()
        storageReference = storage?.reference?.child("profileImg")

        val firstname = binding.firstNameEditText
        val surname = binding.surnameEditText
        val email = binding.emailEditText
        val password = binding.passwordEditText
        val button = binding.kayitButon
        val imgProfile = binding.imgProfile

        imgProfile.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }


        button.setOnClickListener {

            if (TextUtils.isEmpty(firstname.text.toString())) {
                firstname.error = "Adınızı giriniz"
            } else if (TextUtils.isEmpty(surname.text.toString())) {
                surname.error = "Soyadınızı giriniz"
            } else if (TextUtils.isEmpty(email.text.toString())) {
                email.error = "Mail adresinizi giriniz"
            } else if (TextUtils.isEmpty(password.text.toString()) || password.text.toString().length < 6) {
                password.error = "Şifreniz minimum 6 karakter olmalıdır"
            } else {
                createAccount(firstname.text.toString(), surname.text.toString(), email.text.toString(), password.text.toString())
                binding.progressBar.visibility = View.VISIBLE
            }
        }

        binding.girisYap.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            uri = data?.data!!
            binding.imgProfile.setImageURI(uri)

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun reload() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun createAccount(firstName: String, surName: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Kayıt Başarılı", Toast.LENGTH_SHORT).show()

                    val user = auth.currentUser
                    val imageDB = storageReference?.child(user?.uid!!)

                    try {
                        imageDB?.child("profile.jpg")?.putFile(uri)
                    } catch (e: Exception) {

                    }

                    val currentUserDb = databaseReference.child(user?.uid!!)
                    currentUserDb.child("firstname").setValue(firstName)
                    currentUserDb.child("surname").setValue(surName)

                    binding.progressBar.visibility = View.INVISIBLE
                    auth.signOut()
                    reload()
                } else {
                    Toast.makeText(baseContext, "Kayıt Başarısız", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.INVISIBLE

                }
            }
    }
}