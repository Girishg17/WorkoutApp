package com.girish.a7minworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.girish.a7minworkout.databinding.ActivityExerciseBinding
import com.girish.a7minworkout.databinding.DialogCustomBackConformationBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(),TextToSpeech.OnInitListener{
    private var binding:ActivityExerciseBinding?=null
    private var restTimer:CountDownTimer?=null
    private var restProgress=0
    private var ExerciseTimer:CountDownTimer?=null
    private var ExerciseProgress=0
    private var exerciseList:ArrayList<ExerciseModel>? =null
    private var currentExposition=-1
    private var tts:TextToSpeech?=null
    private  var player:MediaPlayer?=null
    private var restTimeDuration:Long=1
    private var restExercisetimeDuration:Long=1
    private var exerciseadapter:ExerciseStatusAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding=ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)
       if(supportActionBar!=null){
           supportActionBar?.setDisplayHomeAsUpEnabled(true)
       }
        binding?.toolbarExercise?.setNavigationOnClickListener{
        customDialogueforbackbutton()
        }
        exerciseList = Constants.defaultExerciseList()
        tts= TextToSpeech(this,this)




        setupRestView()
        setupExerciseStatusRecylerView()


    }

    override fun onBackPressed() {
        customDialogueforbackbutton()
        //super.onBackPressed()
    }
    private fun customDialogueforbackbutton(){
        val customDialogu=Dialog(this)
        val dialogbinding=DialogCustomBackConformationBinding.inflate(layoutInflater)
        customDialogu.setContentView(dialogbinding.root)
        customDialogu.setCanceledOnTouchOutside(false)
        dialogbinding.tvYes.setOnClickListener{
            this@ExerciseActivity.finish()
            customDialogu.dismiss()
        }
        dialogbinding.tvNo.setOnClickListener{
            customDialogu.dismiss()
        }
        customDialogu.show()
    }
    private fun setupExerciseStatusRecylerView(){
        binding?.rvExerciseStatus?.layoutManager=
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseadapter= ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter=exerciseadapter
    }
    private fun setupRestView(){
        try{
            val soundUri=Uri.parse("android.resource://com.girish.a7minworkout/"+R.raw.press_start)
            player=MediaPlayer.create(applicationContext,soundUri)
            player?.isLooping=false
            player?.start()
        }catch (e:Exception){
            e.printStackTrace()
        }
        binding?.flRestView?.visibility=View.VISIBLE
        binding?.tvTitle?.visibility=View.VISIBLE
        binding?.flExerciseView?.visibility=View.INVISIBLE
        binding?.tvExerciseName?.visibility=View.INVISIBLE
        binding?.ivImage?.visibility=View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility=View.VISIBLE
        binding?.tvupcomingExerciseName?.visibility=View.VISIBLE
        if(restTimer!=null){
            restTimer?.cancel()
            restProgress=0
        }
binding?.tvupcomingExerciseName?.text=exerciseList!![currentExposition+1].getName()
        setRestProgressBar()

    }
    private fun setupExerciseView(){
        binding?.flRestView?.visibility=View.INVISIBLE
        binding?.tvTitle?.visibility=View.INVISIBLE
        binding?.flExerciseView?.visibility=View.VISIBLE
        binding?.tvExerciseName?.visibility=View.VISIBLE
        binding?.ivImage?.visibility=View.VISIBLE
        binding?.tvUpcomingLabel?.visibility=View.INVISIBLE
        binding?.tvupcomingExerciseName?.visibility=View.INVISIBLE
        if(ExerciseTimer!=null){
            ExerciseTimer?.cancel()
            ExerciseProgress=0
        }
        speakOut(exerciseList!![currentExposition].getName())
        binding?.ivImage?.setImageResource(exerciseList!![currentExposition].getImage())
        binding?.tvExerciseName?.text=exerciseList!![currentExposition].getName()
        setExerciseProgressBar()
    }
    private fun setRestProgressBar(){

        binding?.progressBar?.progress=restProgress
        restTimer=object :CountDownTimer(restTimeDuration+1000,1000){

            override fun onTick(p0: Long) {
            restProgress++
                binding?.progressBar?.progress=10-restProgress
                binding?.tvTimer?.text=(10-restProgress).toString()
            }

            override fun onFinish() {
                currentExposition++
                exerciseList!![currentExposition].setIsSelected(true)
                exerciseadapter!!.notifyDataSetChanged()
               setupExerciseView()
            }

        }.start()


    }
    private fun setExerciseProgressBar(){

        binding?.progressBar?.progress=ExerciseProgress
        ExerciseTimer=object :CountDownTimer(restExercisetimeDuration+1000,1000){
            override fun onTick(p0: Long) {
                ExerciseProgress++
                binding?.progressBarExercise?.progress=10-ExerciseProgress
                binding?.tvTimerExercise?.text=(10-ExerciseProgress).toString()
            }

            override fun onFinish() {

                if(currentExposition<exerciseList?.size!!-1){
                    exerciseList!![currentExposition].setIsSelected(false)
                    exerciseList!![currentExposition].setIsCompleted(true)
                    exerciseadapter!!.notifyDataSetChanged()

                    setupRestView()
                }else{
                    finish()
                    val intent=Intent(this@ExerciseActivity,finish_activity::class.java)
                    startActivity(intent)

                }
            }

        }.start()


    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer!=null){
            restTimer?.cancel()
            restProgress=0
        }
        if(ExerciseTimer!=null){
            ExerciseTimer?.cancel()
            ExerciseProgress=0
        }
        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()

        }
        if(player!=null){
            player!!.stop()
        }
        binding=null
    }

    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS){
            val result=tts?.setLanguage(Locale.US)
            if (result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){

            Log.e("TTS","the language specified is not supported")
            }
            else{
                Log.e("TTS","Initialisation failed")
            }
        }
    }
    private fun speakOut(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")

    }
}