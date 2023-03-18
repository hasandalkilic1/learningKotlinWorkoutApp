package eu.tutorials.workoutapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import eu.tutorials.workoutapp.R
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setupActionBar()
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_history_activity)

        val actionBar=supportActionBar

        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back)
            actionBar.title="HISTORY"
        }

        toolbar_history_activity.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}