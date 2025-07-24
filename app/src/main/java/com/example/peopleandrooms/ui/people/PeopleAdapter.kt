package com.example.peopleandrooms.ui.people

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.peopleandrooms.R
import com.example.peopleandrooms.data.peopledata.PeopleData
import com.example.peopleandrooms.data.peopledata.PeopleDataItemModel
import com.example.peopleandrooms.databinding.ItemPeopleBinding

class PeopleAdapter(val list: PeopleData, val onClickAction: (PeopleDataItemModel) -> Unit ):
    RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PeopleAdapter.PeopleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_people,parent,false)
        return PeopleViewHolder(itemView)
    }
    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PeopleAdapter.PeopleViewHolder, position: Int) {
        holder.initUI(list[position])
        holder.binding.avatar.setOnClickListener {
            onClickAction.invoke(
                list[position] ?: PeopleDataItemModel()
            )
        }
    }

    class PeopleViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemPeopleBinding.bind(view)
        @SuppressLint("SetTextI18n")
        fun initUI(model: PeopleDataItemModel) {
            binding.apply {
                fullName.text = "${model.firstName} ${model.lastName}"
                Glide.with(view.context)
                    .load(model.avatar)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(avatar)
            }
            }
        }

}