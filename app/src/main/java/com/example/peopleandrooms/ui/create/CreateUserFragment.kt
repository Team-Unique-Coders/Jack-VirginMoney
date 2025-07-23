package com.example.peopleandrooms.ui.create

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.peopleandrooms.R
import com.example.peopleandrooms.databinding.FragmentCreateUserBinding
import com.example.peopleandrooms.databinding.FragmentLoginBinding
import com.example.peopleandrooms.ui.loginpage.LoginViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class CreateUserFragment : Fragment() {
    private var _binding: FragmentCreateUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        val createViewModel =
            ViewModelProvider(this).get(CreateUserViewModel::class.java)
        auth = Firebase.auth

        _binding = FragmentCreateUserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.revert.setOnClickListener {
            findNavController().navigate(R.id.loginFragment) }

        createViewModel.text.observe(viewLifecycleOwner) {}

        binding.apply {
            button.setOnClickListener{
                val email = newEmailText.text.toString()
                val pwd = newPasswordText.text.toString()
                if(email.isEmpty()){
                    Toast.makeText(context, "email can't be null",
                        Toast.LENGTH_SHORT).show() }
                if(pwd.isEmpty()){
                    Toast.makeText(context, "pwd can't be null",
                        Toast.LENGTH_SHORT).show() }
                else{
                    auth.createUserWithEmailAndPassword(email, pwd).
                    addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success")
                                val user = auth.currentUser
                                Toast.makeText(context, "${user} you have created an account", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.loginFragment)
                            } } } } }
        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}