package eu.tutorials.workoutapp.activities


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import eu.tutorials.workoutapp.R
import kotlinx.android.synthetic.main.activity_bmi.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        setupActionBar()

        btnCalculateUnits.setOnClickListener {
            if (validateMetricUnits()){
                val height:Float=et_metric_unit_height.text.toString().toFloat()/100

                val weight:Float=et_metric_unit_weight.text.toString().toFloat()

                val bmi=weight/(height*height)

                displayBMIResult(bmi)
            }
            else{
                Toast.makeText(this,resources.getString(R.string.please_enter_valid_values),Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun displayBMIResult(bmi:Float){

        val bmiLabel:String
        val bmiDescription:String

        if(bmi.compareTo(15f)<=0){
            bmiLabel=resources.getString(R.string.very_severely_underweight)
            bmiDescription=resources.getString(R.string.you_really_need_to_take_better_care_of_yourself_You_are_too_skinny)
        }
        else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = resources.getString(R.string.severely_underweight)
            bmiDescription = resources.getString(R.string.you_really_need_to_take_better_care_of_yourself_You_are_skinny)
        }
        else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = resources.getString(R.string.underweight)
            bmiDescription = resources.getString(R.string.you_really_need_to_take_better_care_of_yourself_You_are_little_bit_skinny)
        }
        else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = resources.getString(R.string.normal)
            bmiDescription = resources.getString(R.string.congratulations_you_are_in_a_good_shape)
        }
        else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = resources.getString(R.string.overweight)
            bmiDescription = resources.getString(R.string.you_really_need_to_take_care_of_your_yourself_Workout_maybe)
        }
        else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = resources.getString(R.string.obese_class_moderately_obese)
            bmiDescription = resources.getString(R.string.you_really_need_to_take_care_of_your_yourself_Workout_maybe)
        }
        else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = resources.getString(R.string.obese_class_severely_obese)
            bmiDescription = resources.getString(R.string.You_are_in_a_very_dangerous_condition_act_now)
        }
        else {
            bmiLabel = resources.getString(R.string.obese_class_very_severely_obese)
            bmiDescription = resources.getString(R.string.You_are_in_a_very_dangerous_condition_act_now)
        }

        val bmiValue=BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()

        llDisplayBMIResult.visibility=View.VISIBLE
        tvBMIValue.text=bmiValue
        tvBMIType.text=bmiLabel
        tvBMIDescription.text=bmiDescription
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_bmi_activity)

        val actionBar=supportActionBar

        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back)
            actionBar.title="CALCULATE BMI"
        }

        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun validateMetricUnits():Boolean{
        var isValid=true

        if(et_metric_unit_weight.text.toString().isEmpty() || et_metric_unit_height.text.toString().isEmpty()){
            isValid=false
        }

        return isValid
    }
}