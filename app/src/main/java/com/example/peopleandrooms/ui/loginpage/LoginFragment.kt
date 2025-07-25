package com.example.peopleandrooms.ui.loginpage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.peopleandrooms.R
import com.example.peopleandrooms.databinding.FragmentLoginBinding
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    //pop up and pop up inclusive <---
    //compose
    private val binding get() = _binding!!
    private val TAG: String = "FirebaseAuth"

    private lateinit var auth: FirebaseAuth
    private lateinit var credentialManager: CredentialManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)

        auth = Firebase.auth
        credentialManager = CredentialManager.create(requireContext())

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.newUser.setOnClickListener {
            findNavController().navigate(R.id.createUserFragment)
        }

        binding.github.setOnClickListener {
            signInWithGitHub()
        }

        loginViewModel.text.observe(viewLifecycleOwner) {
        }

        binding.apply {
            buttonGoogle.setOnClickListener {
                initFirabaseAuth()
            }
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
                                Toast.makeText(context, "${user?.email} Logged In", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.peopleFragment) {
                                    popUpTo(R.id.loginFragment) {
                                        inclusive = true
                                    }
                                }
                            } else {
                                Toast.makeText(context, "Bad Input", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }

        return root
    }


    private fun initFirabaseAuth(){

        // Instantiate a Google sign-in request
        val googleIdOption = GetGoogleIdOption.Builder()
            // Your server's client ID, not your Android client ID.
            .setServerClientId(getString(R.string.default_web_client_id))
            // Only show accounts previously used to sign in.
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(true)
            .build()

        // Create the Credential Manager request
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
        lifecycleScope.launch {
            try{
                handleSignIn(
                    credentialManager.getCredential(
                        requireContext(),
                        request
                    ).credential
                )
            }
            catch (e: GetCredentialException){
                Log.e(TAG, "Couldn't receive V-Bucks transaction: ${e.localizedMessage}")
            }
        }


    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    Toast.makeText(context, "Hello ${user?.email}", Toast.LENGTH_SHORT).show()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(context, "Error Error Error", Toast.LENGTH_SHORT).show()

                    //updateUI(null)
                }
            }
    }

    private fun signInWithGitHub() {
        val provider = OAuthProvider.newBuilder("github.com")

        auth.startActivityForSignInWithProvider(requireActivity(), provider.build())
            .addOnSuccessListener { authResult ->
                val user = authResult.user
                Log.d(TAG, "GitHub sign-in success: ${user?.email}")
                Toast.makeText(context, "Hello ${user?.displayName}", Toast.LENGTH_SHORT).show()
                requireActivity()
                    .findNavController(R.id.nav_host_fragment)
                    .navigate(R.id.peopleFragment)
                }

            .addOnFailureListener { e ->
                Log.e(TAG, "GitHub sign-in failed: ${e.localizedMessage}")
                Toast.makeText(context, "GitHub sign-in failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun handleSignIn(credential: Credential) {
        // Check if credential is of type Google ID
        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            // Create Google ID Token
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

            // Sign in to Firebase with using the token
            firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
        } else {
            Log.w(TAG, "Credential is not of type Google ID!")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}