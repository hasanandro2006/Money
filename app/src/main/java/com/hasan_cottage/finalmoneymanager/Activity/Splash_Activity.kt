package com.hasan_cottage.finalmoneymanager.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.Roomdatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.Roomdatabase.DatabaseDao
import com.hasan_cottage.finalmoneymanager.Roomdatabase.Repostry
import com.hasan_cottage.finalmoneymanager.viewmodelclass.Appviewmodel.Appviewmodel
import com.hasan_cottage.finalmoneymanager.viewmodelclass.ViewmodelFactory

class Splash_Activity : AppCompatActivity() {
    lateinit var viewmodelAll:Appviewmodel
    lateinit var dao: DatabaseDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
// Initialize  database
        dao = DatabaseAll.getInstanceAll(application).getAllDao()
        val daoM = DatabaseAll.getInstanceAll(applicationContext).getAllDaoM()
        val repostry= Repostry(dao,daoM)
        viewmodelAll = ViewModelProvider(this, ViewmodelFactory(repostry)).get(Appviewmodel::class.java)

        Handler().postDelayed(Runnable {

            viewmodelAll.getData().observe(this, Observer {
                if (it.isNullOrEmpty()){
                    startActivity(Intent(this,Signup_Activity::class.java))
                    finish()
                }
                else{
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
            })

//            startActivity(Intent(this,Signup_Activity::class.java))
//            finish()
        },2000)




    }
}