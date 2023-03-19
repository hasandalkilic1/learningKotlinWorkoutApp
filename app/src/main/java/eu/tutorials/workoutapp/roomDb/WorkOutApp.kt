package eu.tutorials.workoutapp.roomDb

import android.app.Application

class WorkOutApp:Application() {

    val db by lazy{
        HistoryDatabase.getInstance(this)
    }
}