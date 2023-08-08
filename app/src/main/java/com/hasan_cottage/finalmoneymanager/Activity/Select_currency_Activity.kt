package com.hasan_cottage.finalmoneymanager.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasan_cottage.finalmoneymanager.Adapter.AdatperCurrency
import com.hasan_cottage.finalmoneymanager.Model.myModel
import com.hasan_cottage.finalmoneymanager.Roomdatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.Roomdatabase.DatabaseDao
import com.hasan_cottage.finalmoneymanager.Roomdatabase.Repostry
import com.hasan_cottage.finalmoneymanager.databinding.ActivitySelectCurrencyBinding
import com.hasan_cottage.finalmoneymanager.viewmodelclass.Appviewmodel.Appviewmodel
import com.hasan_cottage.finalmoneymanager.viewmodelclass.ViewmodelFactory
import java.util.Collections
import java.util.Currency
import java.util.Locale

class Select_currency_Activity : AppCompatActivity() {

    lateinit var binding: ActivitySelectCurrencyBinding
    lateinit var databinding: ActivitySelectCurrencyBinding
    var arrayListCurrency: ArrayList<myModel>? = null
    lateinit var viewmodelAll: Appviewmodel
    lateinit var dao: DatabaseDao
    lateinit var repostry: Repostry
    lateinit var adaptrAll: AdatperCurrency


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectCurrencyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize  database
        dao = DatabaseAll.getInstanceAll(application).getAllDao()
        val daoM = DatabaseAll.getInstanceAll(applicationContext).getAllDaoM()
        repostry = Repostry(dao, daoM)
        viewmodelAll =
            ViewModelProvider(this, ViewmodelFactory(repostry)).get(Appviewmodel::class.java)



        arrayListCurrency = java.util.ArrayList()

        getCurrency()
        passDataInAdapter()
        searchViewEvent()

    }


    private fun getCurrency() {

        // Get all country currency name /.....
        val countryCodes = Locale.getISOCountries()
        for (countryCode in countryCodes) {
            val locale = Locale("", countryCode)
            val currency = Currency.getInstance(locale)
            if (currency != null) {
                // Get the currency name and code
                val currencyName = currency.displayName
                val currencyCode = currency.currencyCode
                val currencySymbol = currency.symbol
                val currencyModel = myModel(currencyCode, currencyName, currencySymbol)
                arrayListCurrency!!.add(currencyModel)
            }
        }

        Collections.sort(arrayListCurrency, object : Comparator<myModel> {
            override fun compare(model1: myModel, model2: myModel): Int {
                return model1.currencyName.compareTo(model2.currencyName)
            }
        })

    }

    private fun passDataInAdapter() {

        val currencyData = intent.getStringExtra("CURRENCY_DATA_KEY")
        val redioposition = intent.getIntExtra("CURRENCY_DATA_KEY_two", 20)


        binding.recyclerviewSelect.setHasFixedSize(true)
        adaptrAll = AdatperCurrency(
            dao,
            this,
            arrayListCurrency!!,
            viewmodelAll!!,
            currencyData!!,
            redioposition!!
        )
        binding.recyclerviewSelect.adapter = adaptrAll
        binding.recyclerviewSelect.layoutManager = LinearLayoutManager(this)
    }

    private fun searchViewEvent() {
        binding.searceViewS.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adaptrAll.filter.filter(newText)

                return true
            }

        })

    }

}


