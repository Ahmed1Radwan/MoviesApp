package com.ahmedhamdy.myapplication2.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker

import androidx.work.WorkerParameters
import com.ahmedhamdy.myapplication2.R
import com.ahmedhamdy.myapplication2.data.local.database.MoviesDatabase
import io.reactivex.rxjava3.disposables.CompositeDisposable

@JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
    "Verbose WorkManager Notifications"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
    "Shows notifications whenever work starts"
@JvmField val NOTIFICATION_TITLE: CharSequence = "WorkRequest Starting"
const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
const val NOTIFICATION_ID = 1


class DeleteWorker(private val appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    override fun doWork(): Result {

        return try{
           compositeDisposable.add(MoviesDatabase.getDatabase(appContext).movieDao().deleteAllExceptFavorite().subscribe())
            compositeDisposable.add(MoviesDatabase.getDatabase(appContext).movieDao().deleteAllReviews().subscribe())
            compositeDisposable.add(MoviesDatabase.getDatabase(appContext).movieDao().deleteAllTrailers().subscribe())
            Result.success()
        }catch (throwable: Throwable){
            Result.failure()
        }

    }

    override fun onStopped() {
        super.onStopped()
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }


    // uses only to invoke notification to check if worker works fine
    fun makeStatusNotification(message: String, context: Context) {

        // Make a channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
            val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description

            // Add the channel
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }

        // Create the notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        // Show the notification
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
    }

}