package com.myprimer.seproductive.Modelo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ProfileViewModel : ViewModel()  {

    // Estado para saber si el usuario está autenticado o no
    private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user: StateFlow<FirebaseUser?> = _user

    // Estado para saber si el usuario ha cerrado sesión
    private val _isLoggedOut = MutableStateFlow(false)
    val isLoggedOut: StateFlow<Boolean> = _isLoggedOut

    init {
        // Cargar la información del usuario actual al iniciar
        loadUser()
    }

    // Cargar la información del usuario desde Firebase
    private fun loadUser() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        _user.value = currentUser

    }

    // Método para cerrar sesión de Firebase
    fun signOut() {
        viewModelScope.launch {
            try {
                FirebaseAuth.getInstance().signOut()
                _user.value = null // Limpiar el usuario almacenado en el ViewModel
                _isLoggedOut.value = true
            } catch (e: Exception) {
                _isLoggedOut.value = false
            }
        }
    }
}
