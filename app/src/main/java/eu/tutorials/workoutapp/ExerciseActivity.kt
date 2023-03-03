package eu.tutorials.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_exercise.*

class ExerciseActivity : AppCompatActivity() {

    private var restTimer:CountDownTimer?=null
    private var restProgress=0

    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setupActionBar()


        setupRestView()
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_exercise_activity)

        val actionBar=supportActionBar

        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back)
        }

        toolbar_exercise_activity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRestView(){
        if (restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }
        setRestProgressBar()
    }

    private fun setupExerciseView(){

        flProgressBar.visibility=View.INVISIBLE
        tvTitle.text="Exercise Name"
        flExerciseView.visibility=View.VISIBLE

        if (restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }
        setExerciseProgressBar()
    }

    private fun setRestProgressBar(){
        progressBar.progress=restProgress

        restTimer=object:CountDownTimer(10000,1000){

            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress=10-restProgress
                tvTimer.text=(10-restProgress).toString()
            }

            override fun onFinish() {
                setupExerciseView()
            }

        }.start()
    }

    private fun setExerciseProgressBar(){
        progressBarExercise.progress=exerciseProgress

        exerciseTimer=object:CountDownTimer(30000,1000){

            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressBarExercise.progress=30-exerciseProgress
                tvTimerExercise.text=(30-exerciseProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,"Well Done! Exercise has finished.",Toast.LENGTH_SHORT).show()
            }

        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        if(exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgress=0
        }

        if (restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }
    }
}