package com.example.fittracker.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fittracker.R
import com.example.fittracker.ViewModel.DiarioViewModel

class DiarioFragment : Fragment() {

    companion object {
        fun newInstance() = DiarioFragment()
    }

    private lateinit var viewModel: DiarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_diario, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DiarioViewModel::class.java)
        // TODO: Use the ViewModel
    }

}