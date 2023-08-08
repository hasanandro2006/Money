package com.hasan_cottage.finalmoneymanager.Adapter

import android.content.Context
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hasan_cottage.finalmoneymanager.Model.Statas_model
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.databinding.DegineForStatisticBinding

class Adapter_statas(val context: Context,val arraylist: ArrayList<Statas_model>,var inEx:Int): RecyclerView.Adapter<Adapter_statas.ViewAdapter>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAdapter {
       return ViewAdapter(LayoutInflater.from(context).inflate(R.layout.degine_for_statistic,parent,false))
    }
    override fun onBindViewHolder(holder: ViewAdapter, position: Int) {
        holder.binding.statasImage.setImageResource(arraylist[position].image)
        holder.binding.statasImage.backgroundTintList=context.getColorStateList(arraylist[position].color)

        holder.binding.statasCatagoryName.text=arraylist[position].catagoryName
        if (inEx ==0){
            holder.binding.statasCatagoryvalue.text= arraylist[position].catagoryValue.toString()
            holder.binding.statasCatagoryvalue.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.green))

        }
        else{
            holder.binding.statasCatagoryvalue.text="- "+arraylist[position].catagoryValue.toString()
        }


        holder.binding.percent.text=arraylist[position].percent.toString()+"%"
        holder.binding.percent.setTextColor(ContextCompat.getColor(holder.itemView.context, arraylist[position].color))


        holder.binding.transtion.text=arraylist[position].trangtion+" transation"
    }
    override fun getItemCount(): Int {
     return arraylist.size
    }


    class ViewAdapter(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding=DegineForStatisticBinding.bind(itemView)
    }
}