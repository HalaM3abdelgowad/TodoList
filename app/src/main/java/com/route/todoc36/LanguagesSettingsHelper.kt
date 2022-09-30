package com.route.todoc36

import android.content.Context
import android.content.SharedPreferences

class LanguagesSettingsHelper {

    //singltone design patteren : make object shared in any status of app
    // for save last language for app if kill it in mobile and reopen it
    companion object{
        var sharedPreferences:SharedPreferences?=null

        private  fun getSharedPreferencesInstance(context:Context):SharedPreferences{

            if(sharedPreferences==null){
                sharedPreferences =  context.getSharedPreferences("lang", Context.MODE_PRIVATE)

            }
            return sharedPreferences!!
        }
        public fun putData(lang:String, context: Context){

            var editor= getSharedPreferencesInstance(context).edit()
            editor.putString("lang",lang)
            editor.commit()

        }
        public fun retreiveDataFromSharedPreferences(lang: String, context: Context): String{
            var data = getSharedPreferencesInstance(context).getString(lang,"en")
            return data!!
        }


    }


}