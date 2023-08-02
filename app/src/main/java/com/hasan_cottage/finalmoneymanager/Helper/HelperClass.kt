package com.hasan_cottage.finalmoneymanager.Helper

import com.hasan_cottage.finalmoneymanager.Model.Account_model
import com.hasan_cottage.finalmoneymanager.Model.Catagory_model
import com.hasan_cottage.finalmoneymanager.R
import java.text.SimpleDateFormat
import java.util.Date

class HelperClass {
    companion object {

        var INCOME = "Income"
        var EXPENSE = "Expense"

       lateinit var arrayList: ArrayList<Catagory_model>
       lateinit var arrayListA: ArrayList<Account_model>

      fun catagoryItem(): ArrayList<Catagory_model> {
            arrayList = ArrayList<Catagory_model>()
            arrayList.add(Catagory_model(R.drawable.assets, "Home", R.color.one))
            arrayList.add(Catagory_model(R.drawable.bars, "Business", R.color.tow))
            arrayList.add(Catagory_model(R.drawable.database, "Loan", R.color.four))
            arrayList.add(Catagory_model(R.drawable.investment, "Investment", R.color.five))
            arrayList.add(Catagory_model(R.drawable.planning, "Planing", R.color.six))
            arrayList.add(Catagory_model(R.drawable.reduction, "Rent", R.color.hol))
            arrayList.add(Catagory_model(R.drawable.reduction, "Other", R.color.hole_s))
            return arrayList
        }

        fun accountItem(): ArrayList<Account_model> {
            arrayListA = ArrayList<Account_model>()
            arrayListA.add(Account_model("Case"))
            arrayListA.add(Account_model("Bank"))
            arrayListA.add(Account_model("Bkash"))
            arrayListA.add(Account_model("Nagad"))
            arrayListA.add(Account_model("Other"))
            return arrayListA
        }

        fun dateFormet(date: Date): String {
            val dateFormat =SimpleDateFormat("dd MMM yyyy")
            return dateFormat.format(date)
        }

        fun getColorCatogory(catogory: String): Catagory_model? {
            arrayList.forEach{ item ->
                if (item.name.equals(catogory)){
                    return item
                }
            }
            return null
        }

            fun getColorAccount(account :String): Int?{
                   when(account){
                       "Case" ->return R.color.one
                       "Bank" -> return R.color.tow
                       "Bkash" ->return R.color.four
                       "Nagad" ->return R.color.five
                       "Other" ->return R.color.six
               }
                return null
        }
}}