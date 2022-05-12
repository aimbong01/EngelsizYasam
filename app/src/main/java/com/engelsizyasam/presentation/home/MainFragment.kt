package com.engelsizyasam.presentation.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.engelsizyasam.R
import com.engelsizyasam.presentation.auth.LoginActivity
import com.engelsizyasam.databinding.FragmentMainBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var uri: Uri
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(inflater)

        auth = Firebase.auth
        val user = auth.currentUser

        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("profile")
        val userReference = databaseReference.child(user?.uid!!)

        userReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val firstName = snapshot.child("firstname").value.toString()
                val surname = snapshot.child("surname").value.toString()

                binding.hosgeldin.text = ("$firstName $surname")

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference.child("profileImg").child(user.uid)

        storageReference.child("profile.jpg").downloadUrl.addOnSuccessListener {
            Picasso.get().load(it).into(binding.profileImage)
            binding.progressBar.visibility = View.INVISIBLE
        }.addOnFailureListener {
            binding.profileImage.setImageResource(R.drawable.avatar)
            binding.progressBar.visibility = View.INVISIBLE
        }

        binding.profileImage.setOnClickListener {
            ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }


        binding.cikisYap.setOnClickListener {
            auth.signOut()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }


        binding.instagram.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/")))
        }
        binding.twitter.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com/")))
        }
        binding.youtube.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/")))
        }



        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            uri = data?.data!!
            binding.profileImage.setImageURI(uri)
            storageReference.child("profile.jpg").putFile(uri)

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

}