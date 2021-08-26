package com.engelsizyasam.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.engelsizyasam.R
import com.engelsizyasam.databinding.WelcomePageFragmentBinding

class WelcomePageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: WelcomePageFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.welcome_page_fragment, container, false)

        return binding.root
    }
}