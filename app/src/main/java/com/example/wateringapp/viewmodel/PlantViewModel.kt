package com.example.wateringapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.wateringapp.data.DataSource
import com.example.wateringapp.model.Plant
import com.example.wateringapp.worker.WaterReminderWorker
import java.util.concurrent.TimeUnit

class PlantViewModel(val application: Application) : ViewModel() {

    private var _data = MutableLiveData(DataSource.plants)
    val data: LiveData<List<Plant>> get() = _data

    internal fun scheduleReminder(
        duration: Long,
        unit: TimeUnit,
        plantName: String
    ) {
        val data = Data.Builder().putString(WaterReminderWorker.nameKey, plantName).build()
        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<WaterReminderWorker>()
            .setInitialDelay(duration, unit)
            .setInputData(data)
            .build()
        WorkManager.getInstance(application.applicationContext)
            .beginUniqueWork(
                WaterReminderWorker.nameKey,
                ExistingWorkPolicy.REPLACE,
                oneTimeWorkRequest
            )
            .enqueue()
    }
}

class PlantViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PlantViewModel::class.java)) {
            PlantViewModel(application) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}