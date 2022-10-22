package com.example.fittracker.aggiungi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fittracker.R
import com.example.fittracker.databinding.ActivityAggiungiBinding
import com.google.android.material.tabs.TabLayoutMediator

class AggiungiActivity : AppCompatActivity() {

    lateinit var binding: ActivityAggiungiBinding
    var tabTitle = arrayOf(R.drawable.ricerca,R.drawable.add_to_playlist,R.drawable.heart)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAggiungiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var pager = binding.tabContainer
        var tab = binding.tabLayout
        pager.adapter = MyAdapterTab(supportFragmentManager, lifecycle)

        TabLayoutMediator(tab, pager){
            tab, position -> tab.setIcon(tabTitle[position])

        }.attach()

    }
}