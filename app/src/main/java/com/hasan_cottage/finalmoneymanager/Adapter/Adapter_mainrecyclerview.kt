package com.hasan_cottage.finalmoneymanager.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.hasan_cottage.finalmoneymanager.Activity.RecordActivity
import com.hasan_cottage.finalmoneymanager.Helper.HelperClass
import com.hasan_cottage.finalmoneymanager.Model.Catagory_model
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.Roomdatabase.ModelM
import com.hasan_cottage.finalmoneymanager.databinding.DegineForMainactivityBinding

class Adapter_mainrecyclerview(val context: Context,val arrayList: List<ModelM>): RecyclerView.Adapter<Adapter_mainrecyclerview.Adapter_ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter_ViewHolder {
        return Adapter_ViewHolder(LayoutInflater.from(context).inflate(R.layout.degine_for_mainactivity,parent,false))
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: Adapter_ViewHolder, position: Int) {

        val store=arrayList[position]

        holder.binding.cotagore.text=arrayList[position].catagory
        holder.binding.datee.text=arrayList[position].date



        holder.binding.account.text=arrayList[position].account
        holder.binding.account.backgroundTintList=context.getColorStateList(HelperClass.getColorAccount(store.account)!!)




        var  getColorCatogorycome =HelperClass.getColorCatogory(arrayList[position].catagory)
        holder.binding.imageView.setImageResource(getColorCatogorycome!!.image)
        holder.binding.imageView.backgroundTintList=context.getColorStateList(getColorCatogorycome.color)

        if (arrayList[position].type.equals(HelperClass.INCOME)){
            holder.binding.amount.setTextColor(context.getColor(R.color.blue))
            holder.binding.amount.text= arrayList[position].amount.toString()
        }
        else {
            holder.binding.amount.setTextColor(context.getColor(R.color.red))
            holder.binding.amount.text="- "+arrayList[position].amount
        }

        holder.itemView.setOnClickListener {
            val intent=Intent(context,RecordActivity::class.java)
            intent.putExtra("Id",arrayList[position].id)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {


            true
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


    class Adapter_ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding=DegineForMainactivityBinding.bind(itemView)
    }

}