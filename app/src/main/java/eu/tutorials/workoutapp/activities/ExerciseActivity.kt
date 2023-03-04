package eu.tutorials.workoutapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import eu.tutorials.workoutapp.R
import eu.tutorials.workoutapp.models.ExerciseModel
import eu.tutorials.workoutapp.utils.Constants
import kotlinx.android.synthetic.main.activity_exercise.*

class ExerciseActivity : AppCompatActivity() {

    private var restTimer:CountDownTimer?=null
    private var restProgress=0

    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress=0

    private var exerciseList:ArrayList<ExerciseModel>? = null
    private var currentExercisePosition=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        exerciseList=Constants.defaultExerciseList()

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

        flRestView.visibility=View.VISIBLE
        tvTitle.visibility=View.VISIBLE
        tvExerciseName.visibility=View.INVISIBLE
        flExerciseView.visibility=View.INVISIBLE
        ivImage.visibility=View.INVISIBLE

        if (restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }
        setRestProgressBar()
    }

    private fun setupExerciseView(){

        flRestView.visibility=View.INVISIBLE
        tvTitle.visibility=View.INVISIBLE
        tvExerciseName.visibility=View.VISIBLE
        flExerciseView.visibility=View.VISIBLE
        ivImage.visibility=View.VISIBLE

        if (exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgress=0
        }

        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text=exerciseList!![currentExercisePosition].getName()

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
                currentExercisePosition++
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
                if(currentExercisePosition < exerciseList!!.size-1){
                    setupRestView()
                }
                else{
                    Toast.makeText(this@ExerciseActivity,"Well Done! You have completed workout",Toast.LENGTH_SHORT).show()
                }
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