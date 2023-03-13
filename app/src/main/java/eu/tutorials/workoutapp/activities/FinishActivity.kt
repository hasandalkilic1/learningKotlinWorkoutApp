package eu.tutorials.workoutapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import eu.tutorials.workoutapp.R
import kotlinx.android.synthetic.main.activity_finish.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        setupActionBar()

        btnFinish.setOnClickListener {
            finish()
        }
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
}