package eu.tutorials.workoutapp.activities

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import eu.tutorials.workoutapp.R
import eu.tutorials.workoutapp.adapters.ExerciseAdapter
import eu.tutorials.workoutapp.models.ExerciseModel
import eu.tutorials.workoutapp.utils.Constants
import kotlinx.android.synthetic.main.activity_exercise.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var restTimer:CountDownTimer?=null
    private var restProgress=0

    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress=0

    private var exerciseList:ArrayList<ExerciseModel>? = null
    private var currentExercisePosition=-1

    private var tts:TextToSpeech?=null

    private var player:MediaPlayer?=null

    private var exerciseAdapter:ExerciseAdapter?=null

    private var restTimerDuration:Long=10
    private var exerciseTimerDuration:Long=30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        exerciseList=Constants.defaultExerciseList()

        tts= TextToSpeech(this,this)

        setupActionBar()

        setupRestView()

        setupExerciseRecyclerView()
    }

    private fun setupExerciseRecyclerView(){
        rvExercise.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter= ExerciseAdapter(this,exerciseList!!)
        rvExercise.adapter=exerciseAdapter
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

        try {
            val soundURI= Uri.parse("android.resource://eu.tutorials.workoutapp/"
                    +R.raw.app_src_main_res_raw_press_start)
            player=MediaPlayer.create(applicationContext,soundURI)
            player!!.isLooping=false
            player!!.start()
        }catch (e:Exception){
            e.printStackTrace()
        }

        flRestView.visibility=View.VISIBLE
        tvTitle.visibility=View.VISIBLE
        tvExerciseName.visibility=View.INVISIBLE
        flExerciseView.visibility=View.INVISIBLE
        ivImage.visibility=View.INVISIBLE
        tvUpcomingLabel.visibility=View.VISIBLE
        tvUpcomingExerciseName.visibility=View.VISIBLE

        tvUpcomingExerciseName.text=exerciseList!![currentExercisePosition+1].getName()

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
        tvUpcomingLabel.visibility=View.INVISIBLE
        tvUpcomingExerciseName.visibility=View.INVISIBLE

        if (exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgress=0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())

        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text=exerciseList!![currentExercisePosition].getName()

        setExerciseProgressBar()
    }

    private fun setRestProgressBar(){
        progressBar.progress=restProgress

        restTimer=object:CountDownTimer(restTimerDuration*1000,1000){

            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress=restTimerDuration.toInt()-restProgress
                tvTimer.text=(restTimerDuration.toInt()-restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()

                setupExerciseView()
            }

        }.start()
    }

    private fun setExerciseProgressBar(){
        progressBarExercise.progress=exerciseProgress

        exerciseTimer=object:CountDownTimer(exerciseTimerDuration*1000,1000){

            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressBarExercise.progress=exerciseTimerDuration.toInt()-exerciseProgress
                tvTimerExercise.text=(exerciseTimerDuration.toInt()-exerciseProgress).toString()
            }

            override fun onFinish() {

                if(currentExercisePosition < exerciseList!!.size-1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }
                else{
                    finish()
                    val intent=Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
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

        if (tts !=null){
            tts!!.stop()
            tts!!.shutdown()
        }

        if(player!=null){
            player!!.stop()
        }
    }

    override fun onInit(status: Int) {
        if (status==TextToSpeech.SUCCESS){
            val result=tts!!.setLanguage(Locale.ENGLISH)

            if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("tts","The language specified is not supported")
            }
        }
        else{
            Log.e("tts","Initialization Failed")
        }
    }

    private fun speakOut(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }
}