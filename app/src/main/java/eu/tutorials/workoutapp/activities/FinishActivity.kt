package eu.tutorials.workoutapp.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import eu.tutorials.workoutapp.R
import eu.tutorials.workoutapp.roomDb.HistoryDao
import eu.tutorials.workoutapp.roomDb.HistoryEntity
import eu.tutorials.workoutapp.roomDb.WorkOutApp
import kotlinx.android.synthetic.main.activity_finish.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        setupActionBar()

        btnFinish.setOnClickListener {
            finish()
        }

        val dao=(application as WorkOutApp).db.historyDao()
        addDateToDB(dao)
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_finish_activity)

        val actionBar=supportActionBar

        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back)
        }

        toolbar_finish_activity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun addDateToDB(historyDao: HistoryDao){

        val calendar= Calendar.getInstance()
        val dateTime=calendar.time
        Log.e("date:",""+dateTime)

        val sdf=SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date =sdf.format(dateTime)
        Log.e("Formatted date:",""+date)

        lifecycleScope.launch{
            historyDao.insert(HistoryEntity(date))
            Log.e("Date: ","Added.")
        }

    }
}