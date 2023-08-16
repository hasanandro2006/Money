package com.hasan_cottage.finalmoneymanager.adapter

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
import com.hasan_cottage.finalmoneymanager.model.MyModel
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.activity.SignupActivity
import java.util.Locale

class AdapterCurrency(

    val context: Context,
    var arrayList: ArrayList<MyModel>,
    private val nameCome: String,
    private var selectedPosition: Int,
) : RecyclerView.Adapter<AdapterCurrency.HolderItem>(), Filterable {

    var stoke: ArrayList<MyModel> = ArrayList()

    init {
        stoke.addAll(arrayList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderItem {
        return HolderItem(
            LayoutInflater.from(parent.context).inflate(R.layout.deging_for_currency, parent, false)
        )
    }


    override fun onBindViewHolder(holder: HolderItem, @SuppressLint("RecyclerView") position: Int) {

        val stores = "${arrayList[position].currencyName} -"
        holder.text1.text = stores
        holder.text2.text = arrayList[position].currencyCode
        val stores2 = "(" + arrayList[position].currencySymbol + ")"
        holder.text3.text = stores2

        var color = "#ECEDF2"
        if (position % 2 == 0) {
            color = "#DEEEEE"
        }
        holder.layout.setBackgroundColor(Color.parseColor(color))

        holder.radioButton.isChecked = (selectedPosition == position)// for radio button selected

        holder.layout.setOnClickListener {
            val sharedPreferencesC = context.getSharedPreferences("Currency", Context.MODE_PRIVATE)
            val editor = sharedPreferencesC.edit()
            editor.putString("cName", arrayList[position].currencyName)
            editor.putString("cCode", arrayList[position].currencyCode)
            editor.putString("cSymbol", arrayList[position].currencySymbol)
            editor.putString("name", nameCome)
            editor.putInt("oldPosition", position)
            editor.apply() // Use apply() to save changes asynchronously

            val intent = Intent(context, SignupActivity::class.java)
            context.startActivity(intent)
            (context as Activity).finish()
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class HolderItem(itemView: View) : ViewHolder(itemView) {

        var text1: TextView = itemView.findViewById(R.id.textView1)
        var text2: TextView = itemView.findViewById(R.id.textView2)
        var text3: TextView = itemView.findViewById(R.id.textView3)
        var radioButton: RadioButton = itemView.findViewById(R.id.redio)
        var layout: RelativeLayout = itemView.findViewById(R.id.container)

    }


    // filter data
    override fun getFilter(): Filter {
        return filterView
    }

    private val filterView: Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterData: java.util.ArrayList<MyModel> = java.util.ArrayList<MyModel>()
            if (charSequence.toString().isEmpty()) {
                filterData.addAll(stoke)
            } else {
                for (obj in stoke) {
                    if (obj.currencyName.lowercase()
                            .contains(charSequence.toString().lowercase(Locale.getDefault()))
                    ) {
                        filterData.add(obj)
                    }
                }
            }
            val results = FilterResults()
            results.values = filterData
            return results
        }

        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            arrayList.clear();
            @Suppress("UNCHECKED_CAST") arrayList.addAll(filterResults.values as ArrayList<MyModel>)
            notifyDataSetChanged()
        }
    }
}