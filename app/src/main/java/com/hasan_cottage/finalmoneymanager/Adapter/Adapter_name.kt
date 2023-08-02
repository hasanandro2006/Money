package com.hasan_cottage.finalmoneymanager.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hasan_cottage.finalmoneymanager.Activity.MainActivity
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.Roomdatabase.DataSignup
import com.hasan_cottage.finalmoneymanager.databinding.DegineForNameBinding

class Adapter_name(
    val context: Context,
    val arrayList: List<DataSignup>,
    val oldposition:Int,

): RecyclerView.Adapter<Adapter_name.ViewHolder_A>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder_A {
        return ViewHolder_A(LayoutInflater.from(context).inflate(R.layout.degine_for_name,parent,false))
    }
    class ViewHolder_A(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding=DegineForNameBinding.bind(itemView)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder_A, position: Int) {
        holder.binding.nameR.text=arrayList[position].name
        holder.binding.carrencyNameR.text=arrayList[position].currencySymbol

        holder.binding.redioName.isChecked=(oldposition == position)

        holder.itemView.setOnClickListener {

            val sharedPreferences = context.getSharedPreferences("Name", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt("oldPosition", position)
            editor.apply()
            notifyDataSetChanged()
            val intent=Intent(context,MainActivity::class.java)
            context.startActivity(intent)

        }
    }
}