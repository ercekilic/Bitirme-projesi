package com.example.mybitirmeproject.profil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mybitirmeproject.databinding.FragmentProfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_profil.view.*


class ProfilFragment : Fragment() {
    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    private var mAuth: String? = null
    val database= Firebase.database
    val myRef = database.getReference("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        val view = binding.root

        mAuth = FirebaseAuth.getInstance().uid.toString()
        myRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.getChildren()){
                   if (postSnapshot.key == mAuth){
                       binding.rating = if (postSnapshot.hasChild("rating")){
                           postSnapshot.child("rating").value as Int
                       }else 3

                       binding.textViewName2.text = postSnapshot.child("fullname").value.toString()
                       binding.textViewPhoneNumber2.text = postSnapshot.child("tel").value.toString()
                   }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })



        return view
    }


}