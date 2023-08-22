package codewithcal.au.calendarappexample

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hasan_cottage.finalmoneymanager.R

class CalendarViewHolder(itemView: View, private val onItemListener: CalendarAdapter.OnItemListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    val dayOfMonth: TextView = itemView.findViewById(R.id.cellDayText)
    val totalC:TextView = itemView.findViewById(R.id.totalC)
    val incomeC: TextView = itemView.findViewById(R.id.incomeC)
    val expenseC: TextView = itemView.findViewById(R.id.expenseC)

    init {
        itemView.setOnClickListener(this)
    }
    override fun onClick(view: View) {
        onItemListener.onItemClick(adapterPosition, dayOfMonth.text.toString())

    }


}
