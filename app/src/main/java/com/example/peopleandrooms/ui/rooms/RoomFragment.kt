package com.example.peopleandrooms.ui.rooms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.peopleandrooms.data.roomdata.RoomDataItemModel
import com.example.peopleandrooms.databinding.FragmentRoomBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomFragment : Fragment() {
    //for toggle
    private var allRooms: List<RoomDataItemModel> = emptyList()
    private var _binding: FragmentRoomBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RoomViewModel by viewModels()

    private lateinit var roomAdapter: RoomAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        _binding = FragmentRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        viewRooms()
    }

    private fun viewRooms() {
        viewModel.roomData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is RoomsApiResponse.Loading -> {
                    binding.apply {
                        roomError.visibility = View.GONE
                        roomRecyclerView.visibility = View.GONE
                        progressBarRoom.visibility = View.VISIBLE
                    } }

                is RoomsApiResponse.Error -> {
                    binding.apply {
                        roomError.visibility = View.VISIBLE
                        roomRecyclerView.visibility = View.GONE
                        progressBarRoom.visibility = View.GONE
                    } }

                is RoomsApiResponse.Success -> {
                    binding.apply {
                        roomError.visibility = View.GONE
                        roomRecyclerView.visibility = View.VISIBLE
                        progressBarRoom.visibility = View.GONE
                    }
                    setupUI(response.roomsModel)
                }
                else -> {} } }
    }

    private fun setupRecyclerView() {
        roomAdapter = RoomAdapter(emptyList()) { roomItem ->
            Toast.makeText(context, "${roomItem.createdAt}Click!", Toast.LENGTH_SHORT).show()
        }
        binding.roomRecyclerView.apply {
            adapter = roomAdapter
            layoutManager = LinearLayoutManager(requireContext())
        } }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupUI(models: List<RoomDataItemModel>) {
        allRooms = models
        applyFilter(binding.showOccupiedSwitch.isChecked)
        binding.showOccupiedSwitch.setOnCheckedChangeListener{_, isChecked ->
        applyFilter(isChecked)
        } }

    private fun applyFilter(showOnlyOccupied: Boolean) {
        val filterList = if (showOnlyOccupied) {
            allRooms.filter { !it.isOccupied }
        } else { allRooms }
        roomAdapter = RoomAdapter(filterList) { item ->
            Toast.makeText(context, "${item.maxOccupancy}", Toast.LENGTH_SHORT).show()
        }
        binding.roomRecyclerView.adapter = roomAdapter
    }
}

