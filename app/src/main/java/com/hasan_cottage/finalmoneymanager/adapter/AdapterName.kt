package com.hasan_cottage.finalmoneymanager.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hasan_cottage.finalmoneymanager.activity.MainActivity
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.roomDatabaseNot.DataSignup
import com.hasan_cottage.finalmoneymanager.roomDatabaseNot.DatabaseTow
import com.hasan_cottage.finalmoneymanager.databinding.DegineForNameBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AdapterName(
    val context: Context,
    private val arrayList: List<DataSignup>,
    private val oldPosition: Int,
    private var data2:ClickDataCome

    ) : RecyclerView.Adapter<AdapterName.ItemViewHolder>() {

    interface ClickDataCome{
        fun click(data: DataSignup)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(context).inflate(R.layout.degine_for_name, parent, false)
        )
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = DegineForNameBinding.bind(itemView)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = arrayList[position]


        holder.binding.nameR.text = arrayList[position].name
        holder.binding.carrencyNameR.text = arrayList[position].currencySymbol

        holder.binding.redioName.isChecked = (oldPosition == position)
        holder.binding.closeItem.setOnClickListener {
            val databaseDaoTow=DatabaseTow.getInstanceAllTow(context)
            GlobalScope.launch {
                databaseDaoTow.getAllDaoTow().deleteId(arrayList[position].id)
            }
        }

        holder.itemView.setOnClickListener {
            data2.click(currentItem)
            val sharedPreferences = context.getSharedPreferences("Name", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt("oldPosition", position)
            editor.putString("databaseName", arrayList[position].name)
            editor.apply()
            notifyDataSetChanged()
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

    }
}