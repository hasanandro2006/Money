package com.hasan_cottage.finalmoneymanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hasan_cottage.finalmoneymanager.model.StatsModel
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.databinding.DegineForStatisticBinding

class AdapterStats(val context: Context, val arraylist: ArrayList<StatsModel>, private var inEx:Int): RecyclerView.Adapter<AdapterStats.ViewAdapter>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAdapter {
       return ViewAdapter(LayoutInflater.from(context).inflate(R.layout.degine_for_statistic,parent,false))
    }
    override fun onBindViewHolder(holder: ViewAdapter, position: Int) {
        holder.binding.statasImage.setImageResource(arraylist[position].image)
        holder.binding.statasImage.backgroundTintList=context.getColorStateList(arraylist[position].color)

        holder.binding.statasCatagoryName.text=arraylist[position].categoryName
        if (inEx ==0){
            holder.binding.statasCatagoryvalue.text= arraylist[position].categoryValue.toString()
            holder.binding.statasCatagoryvalue.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.green))

        }
        else{
            val stores="- "+arraylist[position].categoryValue.toString()
            holder.binding.statasCatagoryvalue.text=stores
        }


        val stores=arraylist[position].percent.toString()+"%"
        holder.binding.percent.text=stores
        holder.binding.percent.setTextColor(ContextCompat.getColor(holder.itemView.context, arraylist[position].color))


        val stores2=arraylist[position].transition+" translation"
        holder.binding.transtion.text=stores2
    }
    override fun getItemCount(): Int {
     return arraylist.size
    }


    class ViewAdapter(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding=DegineForStatisticBinding.bind(itemView)
    }
}