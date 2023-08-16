package com.hasan_cottage.finalmoneymanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hasan_cottage.finalmoneymanager.model.AccountModel
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.bottomFragment.BottomSheetFragment
import com.hasan_cottage.finalmoneymanager.databinding.DegineForAccountBinding

class AdapterAccount(
    val context: Context,
    private val arrayList: ArrayList<AccountModel>,
    private val clickItem: BottomSheetFragment
) : RecyclerView.Adapter<AdapterAccount.AccountHolder>() {

    interface ClickItem {
        fun getItemData(accountModel: AccountModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountHolder {
        return AccountHolder(
            LayoutInflater.from(context).inflate(R.layout.degine_for_account, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AccountHolder, position: Int) {

        val accountModel: AccountModel = arrayList[position]
        holder.binding.account.text = arrayList[position].account
        holder.itemView.setOnClickListener {
            clickItem.getItemData(accountModel)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class AccountHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = DegineForAccountBinding.bind(itemView)
    }
}