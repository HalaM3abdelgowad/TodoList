package com.route.todoc36.ui.home.settings

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.preference.PreferenceManager
import java.util.*


class LocaleHelper {
      companion object {
          var  SELECTED_LANGUAGE :String= "Locale.Helper.Selected.Language";
          var  SELECTED_LANGUAGE_BUTToN :String= "";

          fun setLocale(context: Context, language: String):Context? {
              val myLocale = Locale(language)
              val res = context.resources
              val dm = res.displayMetrics
              val conf = res.configuration
              conf.locale = myLocale
              res.updateConfiguration(conf, dm)
              persist(context, language)

              return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                  updateResources(context, language)
              } else updateResourcesLegacy(context, language)
          }

          private fun persist(context: Context, language: String) {
              val preferences = PreferenceManager.getDefaultSharedPreferences(context)
              val editor = preferences.edit()
              editor.putString(SELECTED_LANGUAGE, language)
              editor.apply()
          }
          @TargetApi(Build.VERSION_CODES.N)
          private fun updateResources(context: Context, language: String): Context? {
              val locale = Locale(language)
              Locale.setDefault(locale)
              val configuration: Configuration = context.resources.configuration
              configuration.setLocale(locale)
              configuration.setLayoutDirection(locale)
              return context.createConfigurationContext(configuration)
          }

          private fun updateResourcesLegacy(context: Context, language: String): Context? {
              val locale = Locale(language)
              Locale.setDefault(locale)
              val resources: Resources = context.resources
              val configuration: Configuration = resources.getConfiguration()
              configuration.locale = locale
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                  configuration.setLayoutDirection(locale)
              }
              resources.updateConfiguration(configuration, resources.getDisplayMetrics())
              return context
          }

      }


}