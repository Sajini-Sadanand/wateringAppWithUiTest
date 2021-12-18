package com.example.wateringapp

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.wateringapp.adapter.PlantAdapter
import com.example.wateringapp.adapter.PlantListener
import com.example.wateringapp.databinding.ActivityMainBinding
import com.example.wateringapp.ui.ReminderDialogFragment
import com.example.wateringapp.viewmodel.PlantViewModel
import com.example.wateringapp.viewmodel.PlantViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: PlantViewModel by viewModels {
        PlantViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setDataForRecycler()
        setContentView(binding.root)
    }

    private fun setDataForRecycler() {
        binding.apply {
            viewModel = this@MainActivity.viewModel
            lifecycleOwner = this@MainActivity
            recyclerView.adapter = PlantAdapter(PlantListener {
                val dialog = ReminderDialogFragment(plantName = it.name)
                dialog.show(supportFragmentManager,ReminderDialogFragment.TAG)
                return@PlantListener true
            })
        }
    }
}