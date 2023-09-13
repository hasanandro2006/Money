package com.hasan_cottage.finalmoneymanager.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.adapter.AdapterCurrency
import com.hasan_cottage.finalmoneymanager.model.MyModel
import com.hasan_cottage.finalmoneymanager.roomDatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.roomDatabase.Repository
import com.hasan_cottage.finalmoneymanager.databinding.ActivitySelectCurrencyBinding
import com.hasan_cottage.finalmoneymanager.viewModelClass.AppViewModel
import com.hasan_cottage.finalmoneymanager.viewModelClass.ViewModelFactory
import java.util.Currency
import java.util.Locale

class SelectCurrency : AppCompatActivity() {

    val binding by lazy {
        ActivitySelectCurrencyBinding.inflate(layoutInflater)
    }
    private var arrayListCurrency: ArrayList<MyModel>? = null
    private lateinit var myViewModel: AppViewModel
    private lateinit var repository: Repository
    lateinit var adapterAll: AdapterCurrency


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize  database
        val daoM = DatabaseAll.getInstanceAll(applicationContext).getAllDaoM()
        repository = Repository(daoM)
        myViewModel =
            ViewModelProvider(this, ViewModelFactory(repository))[AppViewModel::class.java]

        arrayListCurrency = java.util.ArrayList()

        getCurrency()
        passDataInAdapter()
        searchViewEvent()

    }


    private fun getCurrency() {

        try {
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
                    val currencyModel = MyModel(currencyCode, currencyName, currencySymbol)
                    arrayListCurrency!!.add(currencyModel)
                }
            }
        } catch (e: IllegalArgumentException) {

            Log.d("show",e.toString())
        }


        // sorted currency
        arrayListCurrency!!.sortWith {
                model1, model2 -> model1.currencyName.compareTo(model2.currencyName)
        }

    }

    private fun passDataInAdapter() {

        val currencyData = intent.getStringExtra("CURRENCY_DATA_KEY")
        val radioPosition = intent.getIntExtra("CURRENCY_DATA_KEY_two", 0)


        binding.recyclerviewSelect.setHasFixedSize(true)
        adapterAll = AdapterCurrency(
            this,
            arrayListCurrency!!,
            currencyData!!,
            radioPosition
        )
        binding.recyclerviewSelect.adapter = adapterAll
        binding.recyclerviewSelect.layoutManager = LinearLayoutManager(this)
    }

    private fun searchViewEvent() {

        // Change the text color
        val textColor = ContextCompat.getColor(this, R.color.black) // Change to your desired text color
        binding.searceViewS.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            .setTextColor(textColor)

        binding.searceViewS.setOnClickListener {

            binding.searceViewS.isIconified = false  // Expand the SearchView
            binding.searceViewS.requestFocus()       // Request focus
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.searceViewS, InputMethodManager.SHOW_IMPLICIT) // Show the keyboard

            binding.searceViewS.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapterAll.filter.filter(newText)
                    return true
                }

            })
        }

        }

}


