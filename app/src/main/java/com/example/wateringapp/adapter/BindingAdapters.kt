package com.example.wateringapp.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wateringapp.model.Plant

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,data: List<Plant>?){
    val adapter = (recyclerView.adapter as PlantAdapter)
    adapter.submitList(data)
}