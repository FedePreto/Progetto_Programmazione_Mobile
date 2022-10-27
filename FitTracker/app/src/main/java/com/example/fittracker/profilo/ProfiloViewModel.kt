package com.example.fittracker.profilo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittracker.databaseFB.UtenteDB
import com.example.fittracker.model.Utente
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.launch

class ProfiloViewModel : ViewModel() {

    private val utenteDB = UtenteDB()

    private var _profilo = MutableLiveData<Utente>()

    val profilo : LiveData<Utente>
        get() = _profilo

    private val auth = FirebaseAuth.getInstance()

    fun changePassword(email : String) : Task<Void> {
        return auth.sendPasswordResetEmail(email)
    }

    fun getDataProfilo(){
        viewModelScope.launch {
           _profilo.value =  utenteDB.getUtente(auth.currentUser!!.email!!)
        }
    }
    fun updateAuthUtenteOnDB(
        nome:String, cognome:String, email:String, LAF:Double, agonistico:Boolean,
        sesso:String, data_nascita: String, altezza:Int, peso_attuale:Double,
        sport:String?, contesto: Context
    ){
        try {
            val user = auth.currentUser
            val profileUpdates = userProfileChangeRequest {
                displayName = "$nome $cognome"
            }
            user!!.updateProfile(profileUpdates)
            viewModelScope.launch { utenteDB.updateUtente(nome, cognome, email,LAF, agonistico ,sesso, data_nascita, altezza,
                peso_attuale, sport, contesto) }
        } catch (e: Exception) {
        }
    }



}