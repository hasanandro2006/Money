package com.hasan_cottage.finalmoneymanager.helper

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.hasan_cottage.finalmoneymanager.model.AccountModel
import com.hasan_cottage.finalmoneymanager.model.CategoryModel
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.roomDatabaseNot.DatabaseTow
import java.text.SimpleDateFormat
import java.util.Date

class HelperClass {
    companion object {

        var INCOME = "Income"
        var EXPENSE = "Expense"

        var Home = "Home"
        var Business = "Business"
        var Loan = "Loan"
        var Investment = "Investment"
        var Planing = "Planing"
        var Rent = "Rent"
        var Other = "Other"

        private lateinit var arrayList: ArrayList<CategoryModel>
        private lateinit var arrayListA: ArrayList<AccountModel>

        fun categoryItem(): ArrayList<CategoryModel> {
            arrayList = ArrayList()
            arrayList.add(CategoryModel(R.drawable.assets, "Home", R.color.one))
            arrayList.add(CategoryModel(R.drawable.bars, "Business", R.color.tow))
            arrayList.add(CategoryModel(R.drawable.database, "Loan", R.color.four))
            arrayList.add(CategoryModel(R.drawable.investment, "Investment", R.color.five))
            arrayList.add(CategoryModel(R.drawable.planning, "Planing", R.color.six))
            arrayList.add(CategoryModel(R.drawable.deal, "Rent", R.color.hol))
            arrayList.add(CategoryModel(R.drawable.reduction, "Other", R.color.hole_s))
            return arrayList
        }

        fun accountItem(): ArrayList<AccountModel> {
            arrayListA = ArrayList()
            arrayListA.add(AccountModel("Cash"))
            arrayListA.add(AccountModel("Bank"))
            arrayListA.add(AccountModel("bKash"))
            arrayListA.add(AccountModel("Nagad"))
            arrayListA.add(AccountModel("Other"))
            return arrayListA
        }

        fun dateFormat(date: Date): String {
            val dateFormat = SimpleDateFormat("dd MMMM yyyy")
            return dateFormat.format(date)
        }

        fun getColorCategory(category: String): CategoryModel? {
            arrayList.forEach { item ->
                if (item.name == category) {
                    return item
                }
            }
            return null
        }

        fun getColorAccount(account: String): Int? {
            when (account) {
                "Cash" -> return R.color.one
                "Bank" -> return R.color.tow
                "bKash" -> return R.color.four
                "Nagad" -> return R.color.five
                "Other" -> return R.color.six
            }
            return null
        }


    }


    }

