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
        var Bills = "Bills"
        var Clothing = "Clothing"
        var Education = "Education"
        var Entertainment = "Entertainment"
        var Fitness = "Fitness"
        var Food = "Food"
        var Gifts = "Gifts"
        var Health = "Health"
        var Furniture = "Furniture"
        var Shopping = "Shopping"
        var Transportation = "Transportation"
        var Travel = "Travel"


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

            arrayList.add(CategoryModel(R.drawable.bill, "Bills", R.color.bills))
            arrayList.add(CategoryModel(R.drawable.tshirt, "Clothing", R.color.cloth))
            arrayList.add(CategoryModel(R.drawable.mortarboard, "Education", R.color.education))
            arrayList.add(CategoryModel(R.drawable.dancer, "Entertainment", R.color.entertainment))
            arrayList.add(CategoryModel(R.drawable.dumbbell, "Fitness", R.color.fitness))
            arrayList.add(CategoryModel(R.drawable.restaurant, "Food", R.color.foot))
            arrayList.add(CategoryModel(R.drawable.gift, "Gifts", R.color.gigt))
            arrayList.add(CategoryModel(R.drawable.protection, "Health", R.color.helth))
            arrayList.add(CategoryModel(R.drawable.sofa, "Furniture", R.color.furniture))
            arrayList.add(CategoryModel(R.drawable.shopping, "Shopping", R.color.shopping))
            arrayList.add(CategoryModel(R.drawable.transport, "Transportation", R.color.transportation))
            arrayList.add(CategoryModel(R.drawable.travel, "Travel", R.color.travel))
            arrayList.add(CategoryModel(R.drawable.application, "Other", R.color.others))
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

