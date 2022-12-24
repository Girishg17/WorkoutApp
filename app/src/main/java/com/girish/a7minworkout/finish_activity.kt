package com.girish.a7minworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.girish.a7minworkout.databinding.ActivityFinishBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class finish_activity : AppCompatActivity() {
    private var binding:ActivityFinishBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarFinishActivity)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
onBackPressed()}
            binding?.btnFinish?.setOnClickListener {


                 finish()
            }
            val dao = (application as WorkOutApp).db.historyDao()
            addDateToDatabase(dao)

        }

    private fun addDateToDatabase(historyDao: HistoryDao){
       val c=Calendar.getInstance()
        val dateTime=c.time
        Log.e("Date:",""+dateTime)
        val sdf=SimpleDateFormat("dd MM yyyy HH:ss",Locale.getDefault())
        val date=sdf.format(dateTime)
        Log.e("Formated Date",""+date)
        lifecycleScope.launch{
            historyDao.insert(HistoryEntity(date))
            Log.e("Date","Added")


        }
    }
}