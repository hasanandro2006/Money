package com.hasan_cottage.finalmoneymanager.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hasan_cottage.finalmoneymanager.activity.MainActivity
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.bottomFragment.BottomSheetFragment
import com.hasan_cottage.finalmoneymanager.roomDatabaseNot.DataSignup
import com.hasan_cottage.finalmoneymanager.roomDatabaseNot.DatabaseTow
import com.hasan_cottage.finalmoneymanager.databinding.DegineForNameBinding
import com.hasan_cottage.finalmoneymanager.fragment.MainFragment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AdapterName(
    val context: Context,
    private val arrayList: MutableList<DataSignup>,
    private var oldPosition: Int,
    private var data2:ClickDataCome,

    ) : RecyclerView.Adapter<AdapterName.ItemViewHolder>() {

    interface ClickDataCome{
        fun click(data: DataSignup)
        fun dismis()
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
        if (position >= 0 && position < arrayList.size) {
            val currentItem = arrayList[position]

            val sharedPreferences = context.getSharedPreferences("Name", Context.MODE_PRIVATE)

            var t=sharedPreferences.getInt("oldPosition",0)
            if (t == 0){
                holder.binding.redioName.isChecked = (oldPosition == position)
            }else{
                holder.binding.redioName.isChecked = (currentItem.id == oldPosition)
            }


            holder.binding.nameR.text = arrayList[position].name
            holder.binding.carrencyNameR.text = arrayList[position].currencySymbol


            holder.binding.closeItem.setOnClickListener {
                val contextWrapper = ContextThemeWrapper(context, R.style.PopupMenuStyle)
                val popupMenu = PopupMenu(contextWrapper, it)
                popupMenu.inflate(R.menu.namepopup)

                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.d -> {
                            val databaseDaoTow = DatabaseTow.getInstanceAllTow(context)
                            GlobalScope.launch {
                                databaseDaoTow.getAllDaoTow().deleteId(currentItem.id)

                            }
                            true
                        }

                        else -> false
                    }
                }
                popupMenu.show()
            }

            holder.itemView.setOnClickListener {
                data2.click(currentItem)
                
                val editor = sharedPreferences.edit()
                editor.putInt("oldPosition", arrayList[position].id)
                editor.apply()
                notifyDataSetChanged()
            }


        }
    }
}