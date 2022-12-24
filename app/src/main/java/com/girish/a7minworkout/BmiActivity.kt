package com.girish.a7minworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.girish.a7minworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {
    companion object{
        private const val METRIC_UNITS_VIEW="METRIC_UNITS_VIEW"
        private const val US_UNITS_VIEW="US_UNITS_VIEW"
    }
    private var binding:ActivityBmiBinding?=null
    private var currentVisibleViews:String= METRIC_UNITS_VIEW
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title="calculate bmi"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener{
            onBackPressed()
        }
        makeVisibleMetricUnitView()
        binding?.rgUnits?.setOnCheckedChangeListener{_,checkedId:Int->
            if(checkedId==R.id.rbMetricUnits){
                makeVisibleMetricUnitView()
            }
            else{
                makeVisibleUsMetricUnitView()
            }
        }
        binding?.btnCalculateUnits?.setOnClickListener{
            calculateUnits()
        }
    }
    private fun makeVisibleMetricUnitView(){
        currentVisibleViews= METRIC_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility=View.VISIBLE
        binding?.tilMetricUnitHeight?.visibility=View.VISIBLE
        binding?.tilMetricUsUnitHeightFeet?.visibility=View.GONE
        binding?.tilMetricUsUnitHeightInch?.visibility=View.GONE
        binding?.tilUsMetricUnitWeight?.visibility=View.GONE
        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()
        binding?.llDiplayBMIResult?.visibility=View.INVISIBLE
    }
    private fun makeVisibleUsMetricUnitView(){
        currentVisibleViews= US_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility=View.INVISIBLE
        binding?.tilMetricUnitHeight?.visibility=View.INVISIBLE
        binding?.tilMetricUsUnitHeightFeet?.visibility=View.VISIBLE
        binding?.tilMetricUsUnitHeightInch?.visibility=View.VISIBLE
        binding?.tilUsMetricUnitWeight?.visibility=View.VISIBLE
        binding?.etUsMetricUnitHeightFeet?.text!!.clear()
        binding?.etUsMetricUnitWeight?.text!!.clear()
        binding?.etUsMetricUnitHeightInch?.text!!.clear()
        binding?.llDiplayBMIResult?.visibility=View.INVISIBLE
    }
    private fun displaybmiresult(bmi:Float){
        val bmiLabel:String
        val bmiDescription:String
        if(bmi.compareTo(15f)<=0){
            bmiLabel="Very severly underweight"
            bmiDescription="you need to care yourself eat more"

        }
        else if(bmi.compareTo(15f)>0 &&bmi.compareTo(16f)<=0){
                bmiLabel="severly underweight"
            bmiDescription="need to take care"
        }
        else if(bmi.compareTo(16f)>0 &&bmi.compareTo(18.5f)<=0){
            bmiLabel="underweight"
            bmiDescription="need to take care"
        }
        else if(bmi.compareTo(18.5f)>0 &&bmi.compareTo(25f)<=0){
            bmiLabel="Normal"
            bmiDescription="your good in health"
        }
        else{
            bmiLabel="Severly obese"
            bmiDescription="OMG your in dangerous condition"
        }
        val bmiValue=BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()
        binding?.llDiplayBMIResult?.visibility=View.VISIBLE
        binding?.tvBMIValue?.text=bmiValue
        binding?.tvBMIType?.text=bmiLabel
        binding?.tvBMIDescription?.text=bmiDescription
    }
    private fun validateMetricUnits():Boolean {
        var isValid = true
        if (binding?.etMetricUnitWeight?.text.toString().isEmpty()) {
            isValid = false
        }else if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid=false
        }
        return isValid

    }
    private fun calculateUnits(){
        if(currentVisibleViews== METRIC_UNITS_VIEW){
            if(validateMetricUnits()){
                val heightValue:Float=binding?.etMetricUnitHeight?.text.toString().toFloat()/100
                val weightValue:Float=binding?.etMetricUnitWeight?.text.toString().toFloat()
                val bmi=weightValue/(heightValue*heightValue)
                displaybmiresult(bmi)
            }
            else{
                Toast.makeText(this@BmiActivity,"please enter valid values",Toast.LENGTH_SHORT).show()
            }
        }
        else{
            if(validateUsMetricUnits()){

            val usUnitHeightFeet:String=binding?.etUsMetricUnitHeightFeet?.text.toString()
            val usInch:String=binding?.etUsMetricUnitHeightInch?.text.toString()
                val usWeight:Float=binding?.etUsMetricUnitWeight?.text.toString().toFloat()
            val height=usInch.toFloat()+usUnitHeightFeet.toFloat()*12
                val bmi=703*(usWeight/(height*height))
                displaybmiresult(bmi)


            }
            else{
                Toast.makeText(this@BmiActivity,"please enter valid values",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun validateUsMetricUnits():Boolean {
        var isValid = true
        if (binding?.etUsMetricUnitWeight?.text.toString().isEmpty()) {
            isValid = false
        }else if(binding?.etUsMetricUnitHeightInch?.text.toString().isEmpty()){
            isValid=false
        }
        else if(binding?.etUsMetricUnitHeightFeet?.text.toString().isEmpty()){
            isValid=false
        }
        return isValid

    }
}