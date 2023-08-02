package com.hasan_cottage.finalmoneymanager.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hasan_cottage.finalmoneymanager.BottomFragment.BottomSheetFragment
import com.hasan_cottage.finalmoneymanager.Model.Catagory_model
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.databinding.DegineForCatagoryBinding




class Adapter_catogory(
    val context: Context,
    var arrlist:ArrayList<Catagory_model>,
    val catagoryClick: BottomSheetFragment
): RecyclerView.Adapter<Adapter_catogory.Adapter_view_holder>(){

    abstract interface CatagoryClick{
        fun clck(category:Catagory_model)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter_view_holder {
        return Adapter_view_holder(LayoutInflater.from(context).inflate(R.layout.degine_for_catagory,parent,false))

    }

    override fun onBindViewHolder(holder: Adapter_view_holder, position: Int) {
        val catagory_model:Catagory_model=arrlist.get(position)
     holder.binding.catagoryImage.setImageResource(arrlist[position].image)
        holder.binding.catgoryText.text=arrlist[position].name
        holder.binding.catagoryImage.backgroundTintList=(context.getColorStateList(arrlist[position].color))
        holder.itemView.setOnClickListener {
            catagoryClick.clck(catagory_model)
        }
    }
    override fun getItemCount(): Int {
        return arrlist.size
    }

    class Adapter_view_holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val binding=DegineForCatagoryBinding.bind(itemView)

    }
}