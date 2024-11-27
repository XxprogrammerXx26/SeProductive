package com.myprimer.seproductive.Modelo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)




    //Google Services here
    fun signInWithGoogleCredential(credential: AuthCredential, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Listo", "Logueado con Google Exitoso!")
                            home()
                        }
                    }
                    .addOnFailureListener {
                        Log.d("Error", "Fallo al loguear con Google!")
                    }
            }
            catch (ex:Exception){
                Log.d("Test","Excepcion al loguear con Google:" +
                        "${ex.localizedMessage}")


            }
        }

//End google services






    fun signInWithEmailAndPassword(email: String, password:String, home:()->Unit)
            = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Bienvenido", "SignInWithEmailAndPassword loqueado!!")
                        home()
                    } else {
                        Log.d("Bienvenido", "SignInWithEmailAndPassword: ${task.result.toString()}")
                    }
                }

        } catch (ex: Exception) {
            Log.d("Bienvenido", "signInWithEmailAndPassword: ${ex.message}")

        }
    }












//Create Users

    fun createUserWithEmailAndPassword(
        email:String,
        password: String,
        home: () -> Unit
    ){
        if(_loading.value == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{task->
                    if (task.isSuccessful){
                        val displayName =
                            task.result.user?.email?.split("@")?.get(0)
                        createUser(displayName)
                        home()
                    }
                    else{
                        Log.d("Welcome", "createUserWithEmailAndPassword: ${task.result.toString()}")
                    }
                    _loading.value = false

                }
        }
    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
//    val user = mutableMapOf<String, Any>()
//        user["user_id"] =userId.toString()
//        user["display_name"] =displayName.toString()


        //Usamos el data Class
        val user = User(
            userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            quote = "Welcome user",
            profession = "Frontend",
            id = null
        ).toMap()


        FirebaseFirestore.getInstance().collection("users")

            .add(user)
            .addOnCompleteListener{
                Log.d("Welcome","Creado ${it}")
            }.addOnFailureListener{

            }
    }


    // Estado que refleja si el usuario está desconectado
    private val _isLoggedOut = MutableStateFlow(false)
    val isLoggedOut: StateFlow<Boolean> = _isLoggedOut

    fun signOut() {
        // Actualiza el estado para indicar que el usuario ha cerrado sesión
        _isLoggedOut.value = true

        // Aquí puedes realizar otras tareas adicionales como limpiar cachés, resetear datos, etc.
    }

}



