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

    companion object{
        private const val METRIC_UNIT_VIEW="METRIC_UNIT_VIEW"
        private const val US_UNIT_VIEW="US_UNIT_VIEW"
    }

    private var currentVisibleView:String= METRIC_UNIT_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        setupActionBar()

        makeVisibleMetricUnitsView()

        btnCalculateUnits.setOnClickListener {
            calculateUnits()
        }

        rgUnits.setOnCheckedChangeListener { _, checkedId:Int ->
            if (checkedId==R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }
            else if(checkedId==R.id.rbUsUnits){
                makeVisibleUsUnitsView()
            }
        }
    }

    private fun makeVisibleMetricUnitsView(){
        currentVisibleView= METRIC_UNIT_VIEW
        tilMetricUnitHeight.visibility=View.VISIBLE
        tilMetricUnitWeight.visibility=View.VISIBLE
        tilUsUnitFeet.visibility=View.GONE
        tilUsUnitInch.visibility=View.GONE
        tilUsUnitWeight.visibility=View.GONE

        et_metric_unit_height.text!!.clear()
        et_metric_unit_weight.text!!.clear()

        llDisplayBMIResult.visibility=View.INVISIBLE
    }

    private fun makeVisibleUsUnitsView(){
        currentVisibleView= US_UNIT_VIEW
        tilMetricUnitHeight.visibility=View.INVISIBLE
        tilMetricUnitWeight.visibility=View.INVISIBLE
        tilUsUnitFeet.visibility=View.VISIBLE
        tilUsUnitInch.visibility=View.VISIBLE
        tilUsUnitWeight.visibility=View.VISIBLE

        etUsUnitFeet.text!!.clear()
        etUsUnitInch.text!!.clear()
        etUsUnitWeight.text!!.clear()

        llDisplayBMIResult.visibility=View.INVISIBLE
    }

    private fun calculateUnits(){
        if (currentVisibleView== METRIC_UNIT_VIEW){
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
        else if(currentVisibleView== US_UNIT_VIEW){
            if(validateUsUnits()){
                val usUnitFeetValue:String=etUsUnitFeet.text.toString()
                val usUnitInchValue:String=etUsUnitInch.text.toString()
                val usUnitWeightValue:Float=etUsUnitWeight.text.toString().toFloat()

                val heightValue=usUnitInchValue.toFloat()+usUnitFeetValue.toFloat()*12

                val bmiValue=703*(usUnitWeightValue/(heightValue*heightValue))

                displayBMIResult(bmiValue)
            }
            else{
                Toast.makeText(this,resources.getString(R.string.please_enter_valid_values),Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateUsUnits():Boolean{
        var isValid=true

        when{
            etUsUnitWeight.text.toString().isEmpty()->{
                isValid=false
            }
            etUsUnitFeet.text.toString().isEmpty()->{
                isValid=false
            }
            etUsUnitInch.text.toString().isEmpty()->{
                isValid=false
            }
        }

        return isValid
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