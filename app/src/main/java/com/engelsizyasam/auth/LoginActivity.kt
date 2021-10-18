package com.engelsizyasam.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.engelsizyasam.databinding.ActivityLoginBinding
import com.engelsizyasam.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val email = binding.emailEditText
        val password = binding.passwordEditText
        val button = binding.girisButton

        button.setOnClickListener {
            if (TextUtils.isEmpty(email.text.toString())) {
                email.error = "Mail adresinizi giriniz"
            } else if (TextUtils.isEmpty(password.text.toString())) {
                password.error = "Şifrenizi giriniz"
            } else {
                loginAccount(email.text.toString(), password.text.toString())
                binding.progressBar.visibility = View.VISIBLE
            }

        }

        binding.kayitOl.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun reload() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loginAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Giriş Başarılı", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.INVISIBLE
                    reload()

                } else {
                    Toast.makeText(baseContext, "Giriş Başarısız", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.INVISIBLE

                }
            }
    }
}