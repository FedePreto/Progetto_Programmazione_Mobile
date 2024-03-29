package com.example.fittracker.autenticazione

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittracker.databaseFB.UtenteDB
import com.example.fittracker.model.Utente
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private var auth: FirebaseAuth = Firebase.auth
    private val utenteDB = UtenteDB()
    private var _utente = MutableLiveData(Utente())
    val utente: LiveData<Utente>
        get() = _utente

    suspend fun singUp(email: String, password: String): FirebaseUser? {
        return try {
            val response = auth.createUserWithEmailAndPassword(email, password).await()
            response.user
        } catch (e: Exception) {
            null
        }
    }

    suspend fun signIn(email: String, password: String): FirebaseUser? {
        return try {
            val signin = auth.signInWithEmailAndPassword(email, password).await()
            signin.user
        } catch (e: Exception) {
            null
        }
    }


    suspend fun addAuthUtenteOnDB(nome:String, cognome:String, email:String, LAF:Double, agonistico:Boolean,
                                  sesso:String, data_nascita:String, altezza:Int, peso_attuale:Double,
                                  sport:String?,contesto: Context) {
        try {
            val user = auth.currentUser
            val profileUpdates = userProfileChangeRequest {
                displayName = nome + ' ' + cognome
            }
            user!!.updateProfile(profileUpdates)
            utenteDB.addUtente(nome, cognome, email,LAF, agonistico ,sesso, data_nascita, altezza,
                                peso_attuale, sport, contesto)

        } catch (e: Exception) {
        }
    }

    fun checkUtenteisLoggato() : Boolean{
        if(auth.currentUser != null)
            return true
        return false
    }

    fun resetPassword(email : String) : Task<Void>{
        return auth.sendPasswordResetEmail(email)
    }
}
