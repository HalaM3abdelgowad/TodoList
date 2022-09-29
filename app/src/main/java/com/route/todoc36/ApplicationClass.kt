package com.route.todoc36

import android.app.Application
import android.content.Intent
import android.widget.Toast
import com.route.todoc36.ui.home.MainActivity
import com.route.todoc36.ui.home.settings.LocaleHelper

class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()

        if(Constants.lang.equals("en")){
            LocaleHelper.setLocale(this, "en")
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }else{


            LocaleHelper.setLocale(this,"ar")
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}