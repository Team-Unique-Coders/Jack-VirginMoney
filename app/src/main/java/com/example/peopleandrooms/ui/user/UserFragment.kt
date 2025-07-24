package com.example.peopleandrooms.ui.user

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.peopleandrooms.R
import com.example.peopleandrooms.databinding.FragmentUserBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : Fragment() {
    private val args: UserFragmentArgs by navArgs()
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        binding.revert.setOnClickListener {
            findNavController().navigate(R.id.peopleFragment)
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val firstName = args.firstName
        viewUser(firstName)
    }

    private fun viewUser(userId: String) {
        Toast.makeText(context, userId, Toast.LENGTH_SHORT).show()

        lifecycleScope.launch {
            try {
                val users = viewModel.getUser(userId)
                val user = users.firstOrNull()
                if (user != null) {
                    Toast.makeText(
                        context,
                        "I am here $userId ${user.firstName}",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.fullName.text = user.name
                    binding.email.text = user.email
                    binding.jobTitle.text = user.jobtitle
                    Glide.with(this@UserFragment)
                        .load(user.avatar)
                        .into(binding.avatar)

                } else {
                    Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Failure in User", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}