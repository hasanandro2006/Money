package codewithcal.au.calendarappexample

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.helper.HelperClass
import com.hasan_cottage.finalmoneymanager.model.CalenderCa
import com.hasan_cottage.finalmoneymanager.roomDatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.roomDatabase.Repository
import com.hasan_cottage.finalmoneymanager.viewModelClass.AppViewModel
import com.hasan_cottage.finalmoneymanager.viewModelClass.ViewModelFactory
import java.util.Calendar


class CalendarAdapter(
    private val daysOfMonth: ArrayList<String>,
    private val onItemListener: OnItemListener,
    private var myViewModel: AppViewModel,
    private var data:String
)
    : RecyclerView.Adapter<CalendarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.166666666).toInt()
        return CalendarViewHolder(view, onItemListener)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val dayText = daysOfMonth[position]
        holder.dayOfMonth.text = dayText

        var store="$dayText $data"
        when (dayText) {
            "1" -> allSetData("01 $data", holder)
            "2" -> allSetData("02 $data", holder)
            "3" -> allSetData("03 $data", holder)
            "4" -> allSetData("04 $data", holder)
            "5" -> allSetData("05 $data", holder)
            "6" -> allSetData("06 $data", holder)
            "7" -> allSetData("07 $data", holder)
            "8" -> allSetData("08 $data", holder)
            "9" -> allSetData("09 $data", holder)
            "10", "11", "12" , "13" ,"14","15","16","17","22","18","19","20","21","23","24","25","26", "27", "28", "29" , "30" ,"31"-> allSetData(store, holder)
        }


    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }
    interface OnItemListener {
        fun onItemClick(position: Int, dayText: String)

    }

 private fun  allSetData(store:String,holder:CalendarViewHolder){
     myViewModel.getDataDaily(store).observe(holder.itemView.context as LifecycleOwner , Observer { data->


         if (data.isNotEmpty()) {
             var storeT:Int= 0
             var incomeT:Int= 0
             var expenseT:Int= 0

             data.forEach { item ->
                 storeT += item.amount.toInt()
                 if (item.type == HelperClass.INCOME) {
                     incomeT += item.amount.toInt()
                 } else if (item.type == HelperClass.EXPENSE) {
                     expenseT += item.amount.toInt()
                 }
             }

             holder.totalC.text = storeT.toString()
             holder.incomeC.text = incomeT.toString()
             holder.expenseC.text = "- $expenseT"
         }
     })
 }

}
