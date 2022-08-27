package com.route.todoc36.ui.home.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.route.todoc36.databinding.FragmentSettingsBinding
import com.route.todoc36.databinding.FragmentTasksListBinding

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
}