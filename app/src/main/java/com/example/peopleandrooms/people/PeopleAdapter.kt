package com.example.peopleandrooms.people

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.peopleandrooms.R
import com.example.peopleandrooms.data.ApiDetails
import com.example.peopleandrooms.data.PeopleDataItemModel
import com.example.peopleandrooms.data.RoomDataItemModel
import com.example.peopleandrooms.databinding.FragmentPeopleBinding
import com.example.peopleandrooms.databinding.ItemPeopleBinding

class PeopleAdapter(
    private var peopleList: List<PeopleDataItemModel>,
    private val onItemClick: (PeopleDataItemModel) -> Unit):
    RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {
    inner class PeopleViewHolder(val binding: ItemPeopleBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val binding = ItemPeopleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PeopleViewHolder(binding)
    }

    override fun getItemCount(): Int = peopleList.size

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        val person = peopleList[position]

        holder.binding.apply {
            fullName.text = "${person.firstName} ${person.lastName}"
            jobTitle.text = "${person.jobtitle}"
            email.text = "${person.email}"

            Glide.with(root.context)
                .load(person.avatar)
                .placeholder(R.drawable.ic_launcher_background)
                .into(avatar)
        }
    }
    fun updatePeople(newPeople: List<PeopleDataItemModel>) {
        peopleList = newPeople
        notifyDataSetChanged()
    }
}