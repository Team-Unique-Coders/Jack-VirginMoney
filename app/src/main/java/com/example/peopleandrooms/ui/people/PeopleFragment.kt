package com.example.peopleandrooms.ui.people

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.peopleandrooms.data.peopledata.PeopleData
import com.example.peopleandrooms.data.peopledata.PeopleDataItemModel
import com.example.peopleandrooms.databinding.FragmentPeopleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeopleFragment : Fragment() {
    private var _binding: FragmentPeopleBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PeopleViewModel
    private lateinit var personAdapter: PeopleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { super.onCreate(savedInstanceState)
        _binding = FragmentPeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this)[PeopleViewModel::class.java]
        viewModel.personData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is PersonApiResponse.Loading -> {
                    binding.apply {
                        personError.visibility = View.GONE
                        peopleRecyclerView.visibility = View.GONE
                        progressBarPerson.visibility = View.VISIBLE
                    }
                }

                is PersonApiResponse.Error -> {
                    binding.apply {
                        personError.visibility = View.VISIBLE
                        peopleRecyclerView.visibility = View.GONE
                        progressBarPerson.visibility = View.GONE
                    }
                }

                is PersonApiResponse.Success -> {
                    binding.apply {
                        personError.visibility = View.GONE
                        peopleRecyclerView.visibility = View.VISIBLE
                        progressBarPerson.visibility = View.GONE
                    }
                    setupUI(response.personSuccess as PeopleData)
                }

            }
        }
    }

    private fun setupUI(models: PeopleData) {
        personAdapter = PeopleAdapter(models) { item ->
            Toast.makeText(context, "${item.firstName + " " + item.lastName} Clicked!", Toast.LENGTH_SHORT).show()
            val action = PeopleFragmentDirections
                .actionPeopleFragmentToUserFragment(item.firstName)
            println("Action is : ${action}")
            findNavController().navigate(action)
        }
        binding.peopleRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.peopleRecyclerView.adapter = personAdapter
    }
}

