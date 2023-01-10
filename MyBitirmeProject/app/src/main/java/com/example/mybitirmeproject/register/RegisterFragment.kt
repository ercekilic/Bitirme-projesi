package com.example.mybitirmeproject.register

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mybitirmeproject.R
import com.example.mybitirmeproject.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class RegisterFragment : Fragment() {
    private var mAuth: FirebaseAuth? = null
    lateinit var et_name:TextView
    lateinit var et_regemail:TextView
    lateinit var et_regpassword:TextView
    lateinit var bt_registerfire: Button
    lateinit var button_giris:TextView
    lateinit var et_tel: TextView
    lateinit var im_back:ImageView
    lateinit var etToLogin:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_register, container, false)
        et_name=view.findViewById(R.id.et_name)
        et_regemail=view.findViewById(R.id.et_regemail)
        et_regpassword=view.findViewById(R.id.et_regpassword)
        button_giris=view.findViewById(R.id.textView5)
        et_tel=view.findViewById(R.id.editTextPhone)
        im_back=view.findViewById(R.id.im_back)
        etToLogin=view.findViewById(R.id.textView5)

        etToLogin.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }

        im_back.setOnClickListener {
            findNavController().popBackStack()

        }

        button_giris.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }

        bt_registerfire=view.findViewById(R.id.bt_registerfire)
        bt_registerfire.setOnClickListener {
            registeruser()
        }

        mAuth = FirebaseAuth.getInstance()
        return view

    }
    private fun registeruser() {
        val email=et_regemail.text.toString().trim()
        val sifre=et_regpassword.text.toString().trim()
        val isim=et_name.text.toString().trim()
        val uid="bilinmiyor"
        var kullanici_type:String=""
        val tel=et_tel.text.toString().trim()


        if (isim.isEmpty()){
            et_name.setError("İsim Gerekli")
            et_name.requestFocus()
            return
        }
        if (email.isEmpty()){
            et_regemail.setError("E-Mail gerekli.")
            et_regemail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_regemail.setError("Geçerli E-Mail giriniz.")
            et_regemail.requestFocus()
            return
        }
        if (sifre.isEmpty()){
            et_regpassword.setError("Sifre Gerekli")
            et_regpassword.requestFocus()
            return
        }
        if (sifre.length<6){
            et_regpassword.setError("Şifre en az 6 uzunlukta olmalı")
            et_regpassword.requestFocus()
            return
        }

        if (tel.isEmpty()){
            et_tel.setError("Lütfen geçerli telefon numarası giriniz")
            et_tel.requestFocus()
            return
        }



        mAuth?.createUserWithEmailAndPassword(email, sifre)
            ?.addOnCompleteListener {
                if (it.isSuccessful){
                    val user= User(
                        isim,
                        email,
                        sifre,
                        tel
                    )
                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .setValue(user).addOnCompleteListener {

                        }
                }
                else{
                    Toast.makeText(requireContext(), "Kullanıcı kayıt işlemi başarısız.", Toast.LENGTH_LONG).show()
                }

            }}

}