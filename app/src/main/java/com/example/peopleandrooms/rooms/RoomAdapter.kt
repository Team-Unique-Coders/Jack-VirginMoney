package com.example.peopleandrooms.rooms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.peopleandrooms.R
import com.example.peopleandrooms.data.roomdata.RoomDataItemModel
import com.example.peopleandrooms.databinding.ItemRoomBinding


class RoomAdapter(
    private var roomsList: List<RoomDataItemModel>,
    private val onItemClick: (RoomDataItemModel) -> Unit):
    RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

        class RoomViewHolder(val view: View): RecyclerView.ViewHolder(view){
            val binding = ItemRoomBinding.bind(view)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_room,parent,false)
        return RoomViewHolder(itemView)

    }

    override fun getItemCount(): Int =roomsList.size

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {

        val room = roomsList[position]
        holder.binding.apply {
            maxOccupancy.text = "Max: ${room.maxOccupancy}"
            isOccupied.text = if (room.isOccupied) "Occupied" else "Available"
            createdTime.text = "Created time: ${room.createdAt}"
            roomId.text = "Room id: ${room.id}"
        }

    }
}