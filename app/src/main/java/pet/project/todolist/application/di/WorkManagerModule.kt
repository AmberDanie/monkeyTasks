package pet.project.todolist.application.di

import android.content.Context
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import pet.project.management.GetDataFromServerWorker
import pet.project.utils.AppScope
import java.util.concurrent.TimeUnit

@Module
interface WorkManagerModule {
    companion object {
        @Provides
        @AppScope
        fun provideConstraints(): Constraints {
            return Constraints.Builder()
                .setRequiresCharging(true)
                .build()
        }

        @Provides
        @AppScope
        fun provideDataWork(constraints: Constraints): PeriodicWorkRequest {
            return PeriodicWorkRequestBuilder<GetDataFromServerWorker>(
                8L,
                TimeUnit.HOURS
            ).setConstraints(constraints)
                .build()
        }

        @Provides
        @AppScope
        fun provideWorkManager(context: Context): WorkManager {
            return WorkManager.getInstance(context)
        }
    }
}