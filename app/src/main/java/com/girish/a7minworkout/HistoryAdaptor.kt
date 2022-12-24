package com.girish.a7minworkout

import android.graphics.Color
import android.graphics.Color.*
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.girish.a7minworkout.databinding.ActivityFinishBinding
import com.girish.a7minworkout.databinding.ItemHistoryRowBinding

class HistoryAdaptor(private val items:ArrayList<String>):RecyclerView.Adapter<HistoryAdaptor.ViewHolder>() {

class ViewHolder(binding: ItemHistoryRowBinding):RecyclerView.ViewHolder(binding.root){
    val llHistoryItemMain=binding.llHistoryItemMain
    val tvItem=binding.tvItem
    val tvPosition=binding.tvPosition
}

    override fun getItemCount(): Int {
     return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val date:String= items[position]
        holder.tvItem.text=(position).toString()
        holder.tvItem.text=date
        holder.tvPosition.text=(position+1).toString()
        if(position%2==0){

         holder.llHistoryItemMain.setBackgroundColor(parseColor("#EBEBEB"))
        }else{
            holder.llHistoryItemMain.setBackgroundColor(parseColor("#FFFFFF"))
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return  ViewHolder(ItemHistoryRowBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        ))
    }

}