package com.example.android.mynotesapp.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.mynotesapp.R
import com.example.android.mynotesapp.data.Note
import com.example.android.mynotesapp.data.room.NoteDatabase
import kotlinx.coroutines.coroutineScope
import java.lang.Exception


class NoteWorker(context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters) {

    companion object {
        const val CHANNEL_ID = "channel_id"
        const val CHANNEL_NAME = "channel_name"
        const val NOTIFICATION_ID = 1
    }

    private var resultState: Result = Result.failure()
    override suspend fun doWork(): Result = coroutineScope {
        try {
            val data :Note? = NoteDatabase.getInstance(applicationContext).noteDao().getNote()
                if (data != null){
                    sendNotification(data.title, data.data)
                }
            resultState
        } catch (e: Exception) {
            Result.failure()
        }
    }

    fun sendNotification(title: String, msg: String) {
        val notificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notification: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_note)
            .setContentTitle("Reminder Note : \n$title")
            .setContentText(msg)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notification.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NOTIFICATION_ID, notification.build())
        resultState = Result.success()
    }
}