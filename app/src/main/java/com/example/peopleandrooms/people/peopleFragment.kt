package com.example.peopleandrooms.people

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.peopleandrooms.data.peopledata.PeopleDataItemModel
import com.example.peopleandrooms.databinding.FragmentPeopleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class peopleFragment : Fragment() {
    private var _binding: FragmentPeopleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PeopleViewModel by viewModels()
    private lateinit var personAdapter: PeopleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        _binding = FragmentPeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        viewPerson()
    }

    private fun viewPerson() {
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
                    setupUI(response.personSuccess)
                }

                else -> {}
            }
        }


    }

    private fun setupRecyclerView() {
        personAdapter = PeopleAdapter(emptyList()) { personItem ->
            Toast.makeText(context, "${personItem.createdAt}Click!", Toast.LENGTH_SHORT).show()
        }
        binding.peopleRecyclerView.apply {
            adapter = personAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupUI(models: List<PeopleDataItemModel>) {
        personAdapter = PeopleAdapter(models) { item ->
            Toast.makeText(context, "${item.firstName} Clicked! ${item.lastName}", Toast.LENGTH_SHORT).show()
        }
        binding.peopleRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.peopleRecyclerView.adapter = personAdapter

    }


}