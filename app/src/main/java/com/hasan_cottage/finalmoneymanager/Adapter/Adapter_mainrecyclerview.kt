package com.hasan_cottage.finalmoneymanager.Adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.hasan_cottage.finalmoneymanager.Activity.RecordActivity
import com.hasan_cottage.finalmoneymanager.BottomFragment.BottomSheetFragment
import com.hasan_cottage.finalmoneymanager.Fragment.MainFragment
import com.hasan_cottage.finalmoneymanager.Helper.HelperClass
import com.hasan_cottage.finalmoneymanager.Model.Catagory_model
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.Roomdatabase.ModelM
import com.hasan_cottage.finalmoneymanager.databinding.DegineForMainactivityBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class Adapter_mainrecyclerview(
    val context: AppCompatActivity,
    val arrayList: List<ModelM>
): RecyclerView.Adapter<Adapter_mainrecyclerview.Adapter_ViewHolder>(){

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
            val popupMenu = PopupMenu(context, it)
            popupMenu.inflate(R.menu.popap)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.edit -> {
                        val bottomSheetFragment = BottomSheetFragment(arrayList[position].id)
                        bottomSheetFragment.show(context.supportFragmentManager, bottomSheetFragment.tag)
                        true
                    }
                    R.id.delete ->{
                        val  alartdilog=AlertDialog.Builder(context)
                            .setTitle("DELETE THIS")
                            .setMessage("Are you sure delete this item")
                            .setPositiveButton("OK"){dilog,where->
                                GlobalScope.launch {
                                    MainFragment.viewmodelM.deletDataId(arrayList[position].id)
                                }
                                Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show()
                            }
                            .setNegativeButton("NO"){dilog,where->
                                Toast.makeText(context,"Not Deleted",Toast.LENGTH_SHORT).show()
                            }
                            .create()
                            .show()
                        true
                    }
                    else -> false
                }
            }

            try {
                val fieldMpgroup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMpgroup.isAccessible = true

                val mPopup = fieldMpgroup.get(popupMenu)
                mPopup.javaClass
                    .getDeclaredMethod("setForceSetIcon", Boolean::class.java)
                    .invoke(mPopup, true)
            } catch (exception: Exception) {
                Log.d("exception", exception.toString())
            }
            popupMenu.gravity = Gravity.END
            popupMenu.show()

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