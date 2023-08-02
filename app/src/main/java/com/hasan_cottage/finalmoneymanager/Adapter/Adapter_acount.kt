package com.hasan_cottage.finalmoneymanager.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hasan_cottage.finalmoneymanager.BottomFragment.BottomSheetFragment
import com.hasan_cottage.finalmoneymanager.Model.Account_model
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.databinding.DegineForAccountBinding

class Adapter_acount(
    val context: Context,
    val arrayList: ArrayList<Account_model>,
    val clickItem: BottomSheetFragment
) : RecyclerView.Adapter<Adapter_acount.Account_Holder>() {

     interface ClickItem{
        fun getItemData(accountModel: Account_model)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Account_Holder {
        return Account_Holder(
            LayoutInflater.from(context).inflate(R.layout.degine_for_account, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Account_Holder, position: Int) {

        val accountModel: Account_model =arrayList[position]
        holder.binding.account.setText(arrayList[position].account)
        holder.itemView.setOnClickListener {
            clickItem.getItemData(accountModel)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class Account_Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = DegineForAccountBinding.bind(itemView)
    }
}