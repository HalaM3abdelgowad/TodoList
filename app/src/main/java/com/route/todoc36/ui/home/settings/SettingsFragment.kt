package com.route.todoc36.ui.home.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.route.todoc36.R
import com.route.todoc36.databinding.FragmentSettingsBinding
import com.route.todoc36.databinding.FragmentTasksListBinding
import com.route.todoc36.ui.home.MainActivity

class SettingsFragment :Fragment() {
    lateinit var fragmentSettingsBinding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentSettingsBinding = FragmentSettingsBinding.inflate(layoutInflater,container,false);
        return fragmentSettingsBinding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()
    }

    private fun initSpinner() {
        val languages=resources.getStringArray(R.array.Languages)
        val spinner =fragmentSettingsBinding.spinner
        if (spinner != null) {
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item, languages
            )
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                if(languages[position].equals("Arabic")) {
                 //   LanguagesSettingsHelper.putData("ar",requireContext())
                //    Log.e("setting" , "arabic selected")
                    LocaleHelper.setLocale(requireContext(), "ar")
                    Toast.makeText(requireContext(), "Arabic", Toast.LENGTH_LONG).show()
                    //localizeContext(requireContext(),"en")
                    val intent: Intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                }else{
                   // LanguagesSettingsHelper.putData("en",requireContext())
                   // Log.e("setting" , "english selected")

/*
                      LocaleHelper.setLocale(requireContext(),"en")
                       Toast.makeText(requireContext(),"english", Toast.LENGTH_LONG).show()                 //localizeContext(requireContext(),"en")
                   val intent: Intent = Intent(requireContext(), MainActivity::class.java)
                  startActivity(intent)
                  */

                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }



    companion object{
        val TAG = "Settings-Fragment"
    }
}