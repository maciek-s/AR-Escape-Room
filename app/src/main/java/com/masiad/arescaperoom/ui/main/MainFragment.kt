package com.masiad.arescaperoom.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.masiad.arescaperoom.R
import com.masiad.arescaperoom.databinding.MainFragmentBinding

/**
 * App start destination fragment
 * Whole navigation game/settings ect
 */
class MainFragment : Fragment(R.layout.main_fragment) {

    //inflate separate ui view?
    private var _binding: MainFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        setupListeners()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListeners() {
        binding.button.setOnClickListener {
            // TODO: Implement level chooser, temporary always start firs
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToGameFragment(1))
        }
    }
}