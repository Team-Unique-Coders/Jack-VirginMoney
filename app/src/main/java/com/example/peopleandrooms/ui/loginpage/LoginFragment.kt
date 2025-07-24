package com.example.peopleandrooms.ui.loginpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.peopleandrooms.R
import com.example.peopleandrooms.databinding.FragmentLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)

        auth = Firebase.auth

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.helpme.setOnClickListener {
            findNavController().navigate(R.id.createUserFragment)
        }

        loginViewModel.text.observe(viewLifecycleOwner) {
        }

        binding.apply {
            button.setOnClickListener {
                val email = emailText.text.toString()
                val password = passwordText.text.toString()

                if (email.isNullOrBlank() || password.isNullOrBlank()) {
                    Toast.makeText(context, "Enter valid details", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                Toast.makeText(context, "${user?.email} I welcome you!", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.peopleFragment)
                            } else {
                                Toast.makeText(context, "Nope! not allowed. . .", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}