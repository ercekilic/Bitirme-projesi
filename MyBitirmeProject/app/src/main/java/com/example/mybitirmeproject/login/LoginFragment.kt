package com.example.mybitirmeproject.login

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mybitirmeproject.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.view.*


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        //val view: View = inflater.inflate(R.layout.fragment_login, container, false)

        val userNameText: EditText =view.et_username
        val userPasswordText: EditText =view.et_password
        val loginButton: Button =view.bt_login
        val registerButton: Button =view.bt_register

        userNameText.setText("ercekilic@hotmail.com")
        userPasswordText.setText("123456")


        registerButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener {
            loginUser(userNameText,userPasswordText)
        }
        return view
    }
    private fun loginUser(userName: EditText, etPassword: EditText) {
        val email=userName.text.toString().trim()
        val password=etPassword.text.toString().trim()

        if (email.isEmpty()){
            userName.setError("E-Mail giriniz.")
            userName.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userName.setError("Geçerli bir mail adresi giriniz")
            userName.requestFocus()
            return
        }
        if (password.isEmpty()){
            etPassword.setError("Parola girmeniz gerekli.")
            etPassword.requestFocus()
            return
        }
        if (password.length<6){
            etPassword.setError("Parola uzunluğu en az 6 olmalı.")
            etPassword.requestFocus()
            return
        }

        mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener {
            if (it.isSuccessful){

                val id= mAuth!!.currentUser?.uid.toString()

                Toast.makeText(requireContext(), "Giriş işlemi başarılı "+id, Toast.LENGTH_LONG).show()
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainScreenFragment())
            } else {
                Toast.makeText(requireContext(), "Giriş işlemi başarısız" + it.exception, Toast.LENGTH_LONG).show()
            }
        }
    }

}