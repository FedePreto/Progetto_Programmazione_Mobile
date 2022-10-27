package com.example.fittracker.profilo

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.fittracker.R
import com.example.fittracker.databinding.ActivityProfiloBinding
import com.example.fittracker.model.Utente
import java.time.LocalDate
import java.util.*

class ProfiloActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding : ActivityProfiloBinding
    private val model = ProfiloViewModel()

    lateinit var sessoSpinner: Spinner
    lateinit var stileDiVitaSpinner: Spinner
    lateinit var sportSpinner: Spinner
    lateinit var agonisticoSwitch: Switch

    private var sessoS = arrayOf<String>("Uomo", "Donna")
    private var stile = arrayOf<String>("Sedentario","Poco attivo", "Attivo", "Molto attivo")
    private var sport = arrayOf<String>("Calcio","Basket", "Pallavolo", "Nuoto", "Baseball","Karate","Pattinaggio","Cricket",
                                "Tennis","Hockey","Ping Pong","Golf","Rugby","Football americano", "Atletica","Ginnastica artistica",
                                "Ginnastica ritmica", "Ciclismo","Judo","Pallanuoto","Altro")


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profilo)


        model.getDataProfilo()

        //aggiornamento automatico view
        binding.viewModel = model
        binding.lifecycleOwner = this



        // on below line we are initializing spinner with ids.
        sessoSpinner = binding.sWSesso
        stileDiVitaSpinner = binding.sWStileDiVita
        sportSpinner = binding.sWSport
        agonisticoSwitch = binding.switchAgonistico

        agonisticoSwitch.setOnCheckedChangeListener{ _, isChecked ->
            binding.selezioneSport.isVisible = isChecked
        }


        // on below line we are adding click listener for our spinner
        sessoSpinner.onItemSelectedListener = this
        stileDiVitaSpinner.onItemSelectedListener = this
        sportSpinner.onItemSelectedListener = this

        // on below line we are initializing adapter for our spinner
        val adapterSesso: ArrayAdapter<CharSequence> =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, sessoS)
        val adapterStile: ArrayAdapter<CharSequence> =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, stile)
        val adapterSport: ArrayAdapter<CharSequence> =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, sport)

        // on below line we are setting drop down view resource for our adapter.
        adapterSesso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterStile.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterSport.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // on below line we are setting adapter for spinner.
        sessoSpinner.adapter = adapterSesso
        stileDiVitaSpinner.adapter = adapterStile
        sportSpinner.adapter = adapterSport

        val profiloObserver = Observer<Utente>{
            //setto i miei spinner con i valori del db
            setSpinner(adapterSesso,adapterStile,adapterSport)
            //setto lo switcher con il valore del db
            agonisticoSwitch.isChecked = model.profilo.value!!.agonista
            //se lo switch è true faccio vedere la selezione dello sport
            binding.selezioneSport.isVisible = model.profilo.value!!.agonista



            //calendario
            var date = LocalDate.parse(model.profilo.value!!.data_nascita)
            val year = date.year
            val month = date.monthValue
            val day = date.dayOfMonth

            //selezione data
            binding.tVDataNascita.setOnClickListener {
                val dpd = DatePickerDialog(this, { view, mYear, mMonth, mDay ->
                    date = LocalDate.parse(model.profilo.value!!.data_nascita)
                    binding.tVDataNascita.text = "$mDay-" + (mMonth + 1) + "-$mYear"
                }, year, month, day)
                dpd.show()
            }

        }
        model.profilo.observe(this,profiloObserver)

}


    private fun setSpinner(
        adapterSesso: ArrayAdapter<CharSequence>,
        adapterStile: ArrayAdapter<CharSequence>,
        adapterSport: ArrayAdapter<CharSequence>
    ) {
        // on below line we are creating a variable to which we have to set our spinner item selected.
        val selectionSesso = model.profilo.value!!.sesso
        var selectionStile = ""
        when(model.profilo.value!!.LAF){
            1.2 ->  selectionStile = "Sedentario"
            1.375 ->  selectionStile = "Poco attivo"
            1.55 ->  selectionStile = "Attivo"
            1.725 ->  selectionStile = "Molto attivo"
        }
        val selectionSport = model.profilo.value!!.sport

        // on below line we are getting the position  the item by the item name in our adapter.
        val spinnerPositionSesso: Int = adapterSesso.getPosition(selectionSesso)
        val spinnerPositionStile: Int = adapterStile.getPosition(selectionStile)
        val spinnerPositionSport: Int = adapterSport.getPosition(selectionSport)

        // on below line we are setting selection for our spinner to spinner position.
        sessoSpinner.setSelection(spinnerPositionSesso)
        stileDiVitaSpinner.setSelection(spinnerPositionStile)
        sportSpinner.setSelection(spinnerPositionSport)

    }


    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}