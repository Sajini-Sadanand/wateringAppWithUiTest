package com.example.wateringapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wateringapp.databinding.CellForPlantsBinding
import com.example.wateringapp.model.Plant

class PlantAdapter(private val longClickListener: PlantListener):ListAdapter<Plant, PlantAdapter.PlantViewHolder>(DiffCallback) {
    object DiffCallback:DiffUtil.ItemCallback<Plant>(){
        override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem.name == newItem.name
        }

    }
    class PlantViewHolder(private val bind:CellForPlantsBinding):RecyclerView.ViewHolder(bind.root) {
        fun bind(item: Plant?, listener: PlantListener) {
            bind.apply {
                plant = item
                longClickListner = listener
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        return PlantViewHolder(CellForPlantsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.bind(getItem(position),longClickListener)
    }
}
class PlantListener(val longClickListener: (plant: Plant) -> Boolean) {
    fun onLongClick(plant: Plant) = longClickListener(plant)
}