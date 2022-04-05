package com.phoncleaner.boosterapp.phonemaster.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phoncleaner.boosterapp.phonemaster.databinding.FragmentAppsBinding


class FragmentTips : Fragment() {
    private var _binding: FragmentAppsBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppsBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

}