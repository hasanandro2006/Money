package com.hasan_cottage.finalmoneymanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.databinding.DegineForCatagoryBinding
import com.hasan_cottage.finalmoneymanager.model.CategoryModel
import java.util.ArrayList

class AdapterCategory(
    val context: Context,
    private var arrayList: ArrayList<CategoryModel>,
    private val categoryClick: CategoryClick
): RecyclerView.Adapter<AdapterCategory.AdapterViewHolder>(){

     interface CategoryClick{
        fun click(category:CategoryModel)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        return AdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.degine_for_catagory,parent,false))

    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val categoryModel:CategoryModel= arrayList[position]
     holder.binding.catagoryImage.setImageResource(arrayList[position].image)
        holder.binding.catgoryText.text=arrayList[position].name
        holder.binding.catagoryImage.backgroundTintList=(context.getColorStateList(arrayList[position].color))
        holder.itemView.setOnClickListener {
            categoryClick.click(categoryModel)
        }
    }
    override fun getItemCount(): Int {
        return arrayList.size
    }

    class AdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val binding=DegineForCatagoryBinding.bind(itemView)

    }
}