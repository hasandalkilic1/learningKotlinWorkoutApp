package eu.tutorials.workoutapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eu.tutorials.workoutapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    flStart.setOnClickListener {
      val intent = Intent(this, ExerciseActivity::class.java)
      startActivity(intent)
    }

    flBMI.setOnClickListener {
      val intent = Intent(this, BMIActivity::class.java)
      startActivity(intent)
    }
  }

}