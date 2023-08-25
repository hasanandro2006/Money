package com.hasan_cottage.finalmoneymanager.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.hasan_cottage.finalmoneymanager.activity.RecordActivity
import com.hasan_cottage.finalmoneymanager.bottomFragment.BottomSheetFragment
import com.hasan_cottage.finalmoneymanager.fragment.MainFragment
import com.hasan_cottage.finalmoneymanager.helper.HelperClass
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.roomDatabase.ModelM
import com.hasan_cottage.finalmoneymanager.databinding.DegineForMainactivityBinding
import com.hasan_cottage.finalmoneymanager.model.MyModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.Locale

class AdapterMainRecyclerview(
    val context: AppCompatActivity,
    private var arrayList: List<ModelM>
) : RecyclerView.Adapter<AdapterMainRecyclerview.AdapterViewHolder>(), Filterable {

    var stoke: List<ModelM> = ArrayList()

    init {
        stoke = ArrayList(arrayList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        return AdapterViewHolder(
            LayoutInflater.from(context).inflate(R.layout.degine_for_mainactivity, parent, false)
        )
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {

        val store = arrayList[position]

        holder.binding.cotagore.text = arrayList[position].category
        holder.binding.datee.text = arrayList[position].date



        holder.binding.account.text = arrayList[position].account
        holder.binding.account.backgroundTintList =
            context.getColorStateList(HelperClass.getColorAccount(store.account)!!)


        val getColorCategoryCome = HelperClass.getColorCategory(arrayList[position].category)
        holder.binding.imageView.setImageResource(getColorCategoryCome!!.image)
        holder.binding.imageView.backgroundTintList =
            context.getColorStateList(getColorCategoryCome.color)

        if (arrayList[position].type == HelperClass.INCOME) {
            holder.binding.amount.setTextColor(context.getColor(R.color.blue))
            holder.binding.amount.text = arrayList[position].amount.toString()
        } else {
            holder.binding.amount.setTextColor(context.getColor(R.color.red))
            val stores = "- " + arrayList[position].amount
            holder.binding.amount.text = stores
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, RecordActivity::class.java)
            intent.putExtra("Id", arrayList[position].id)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            val popupMenu = PopupMenu(context, it)
            popupMenu.inflate(R.menu.popap)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.edit -> {
                        val bottomSheetFragment = BottomSheetFragment(arrayList[position].id, "t")
                        bottomSheetFragment.show(
                            context.supportFragmentManager,
                            bottomSheetFragment.tag
                        )
                        true
                    }

                    R.id.delete -> {
                        AlertDialog.Builder(context)
                            .setTitle("DELETE THIS")
                            .setMessage("Are you sure delete this item")
                            .setPositiveButton("OK") { _, _ ->
                                GlobalScope.launch {
                                    MainFragment.myViewModel.deleteDataId(arrayList[position].id)
                                }
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                            }
                            .setNegativeButton("NO") { _, _ ->
                                Toast.makeText(context, "Not Deleted", Toast.LENGTH_SHORT).show()
                            }
                            .create()
                            .show()
                        true
                    }

                    else -> false
                }
            }

            try {
                val fieldGroup = PopupMenu::class.java.getDeclaredField("mPopup")

                fieldGroup.isAccessible = true

                val mPopup = fieldGroup.get(popupMenu)
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


    class AdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = DegineForMainactivityBinding.bind(itemView)
    }

    override fun getFilter(): Filter {
        return filterView
    }

    private val filterView: Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterData: MutableList<ModelM> = ArrayList()

            if (charSequence.toString().isEmpty()) {
                filterData.addAll(stoke) // Restore original data
            } else {
                for (obj in stoke) {
                    if (obj.category.lowercase()
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
            // Set the filtered data into the arrayList passed from activity
            arrayList = filterResults.values as List<ModelM>
            notifyDataSetChanged()
        }
    }

}