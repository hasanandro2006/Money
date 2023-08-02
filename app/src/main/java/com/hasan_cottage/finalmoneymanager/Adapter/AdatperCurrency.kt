package com.hasan_cottage.finalmoneymanager.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hasan_cottage.finalmoneymanager.Activity.Signup_Activity
import com.hasan_cottage.finalmoneymanager.Model.myModel
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.Roomdatabase.DataSignup
import com.hasan_cottage.finalmoneymanager.Roomdatabase.DatabaseDao
import com.hasan_cottage.finalmoneymanager.viewmodelclass.Appviewmodel.Appviewmodel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Locale

class AdatperCurrency(
    val dao: DatabaseDao,
    val context: Context,
    var arrayList: ArrayList<myModel>,
    val viemodel: Appviewmodel,
    val nameCome: String,
    var selectedPosition: Int,
) : RecyclerView.Adapter<AdatperCurrency.viewHolder>(), Filterable {

    var stoke: ArrayList<myModel> = ArrayList()

    init {
        stoke.addAll(arrayList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.deging_for_currency, parent, false)
        )
    }


    override fun onBindViewHolder(holder: viewHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.text1.text = arrayList[position].currencyName + " -"
        holder.text2.text = arrayList[position].currencyCode
        holder.text3.text = "(" + arrayList[position].currencySymbol + ")"

        var color = "#ECEDF2"
        if (position % 2 == 0) {
            color = "#DEEEEE"
        }
        holder.layout.setBackgroundColor(Color.parseColor(color))

        holder.redio.isChecked = (selectedPosition == position)// for radio button selected

        holder.layout.setOnClickListener {

            GlobalScope.launch {
                viemodel.insert(
                    DataSignup(
                        arrayList[position].currencyCode,
                        arrayList[position].currencyName,
                        arrayList[position].currencySymbol,
                        nameCome,
                        position
                    )
                )


            }
            context.startActivity(Intent(context, Signup_Activity::class.java))
            (context as Activity).finish() // Finish the current activity
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class viewHolder(itemView: View) : ViewHolder(itemView) {

        var text1 = itemView.findViewById<TextView>(R.id.textView1)
        var text2 = itemView.findViewById<TextView>(R.id.textView2)
        var text3 = itemView.findViewById<TextView>(R.id.textView3)
        var redio = itemView.findViewById<RadioButton>(R.id.redio)
        var layout = itemView.findViewById<RelativeLayout>(R.id.container)

    }


    // filter data
    override fun getFilter(): Filter {
        return filterView
    }

    private val filterView: Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filtarData: java.util.ArrayList<myModel> =
                java.util.ArrayList<myModel>()
            if (charSequence.toString().isEmpty()) {
                filtarData.addAll(stoke)
            } else {
                for (obj in stoke) {
                    if (obj.currencyName.toLowerCase()
                            .contains(charSequence.toString().lowercase(Locale.getDefault()))
                    ) {
                        filtarData.add(obj)
                    }
                }
            }
            val results = FilterResults()
            results.values = filtarData
            return results
        }

        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            arrayList.clear();
            @Suppress("UNCHECKED_CAST")
            arrayList.addAll(filterResults.values as ArrayList<myModel>)
            notifyDataSetChanged()


        }
    }
}