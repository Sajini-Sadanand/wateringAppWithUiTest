package com.example.wateringapp.worker

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.wateringapp.BaseApplication
import com.example.wateringapp.MainActivity
import com.example.wateringapp.R
import java.lang.Exception

class WaterReminderWorker(context: Context, parameter: WorkerParameters) :
    Worker(context, parameter) {
    private val notificationID: Int = 18

    companion object {
        const val nameKey = "NAME"
    }

    override fun doWork(): Result {
        return try {
            val intent = Intent(applicationContext, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE
            )
            val plantName = inputData.getString(nameKey)
            val builder = NotificationCompat.Builder(applicationContext, BaseApplication.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_local_florist_24)
                .setContentTitle("Water me!")
                .setContentText("It's time to water your $plantName")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(applicationContext)) {
                notify(notificationID, builder.build())
            }

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}