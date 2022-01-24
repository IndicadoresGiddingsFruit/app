package com.example.indicadoresapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.example.indicadoresapp.ApiService.MainViewModelFactory
import com.example.indicadoresapp.ApiService.Repository
import com.example.indicadoresapp.ApiService.ViewModel
import com.example.indicadoresapp.modelos.usuarios.UsuariosItem
import com.google.firebase.analytics.FirebaseAnalytics
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: ViewModel
    val username = ""
    val password = ""
    var bundle=Bundle()
    lateinit var usuario: List<UsuariosItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        val analitycs = FirebaseAnalytics.getInstance(this)

        bundle.putString("Message","Integracion completa")
        analitycs.logEvent("InitScreen",bundle)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ViewModel::class.java)

        login()
        register()
    }

    private fun login(){
        login_button.setOnClickListener(){
            val username=username_input.text.toString().trim()
            val password=pass_input.text.toString().trim()

            if (username.isEmpty()){
                username_input.error="Escriba su usuario"
                username_input.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()){
                pass_input.error="Escriba su contraseña"
                pass_input.requestFocus()
                return@setOnClickListener
            }

            if (username.isNotEmpty() && password.isNotEmpty()){
                iniciar_sesion()
            }
        }
    }

    private fun iniciar_sesion(){
        viewModel.getUsuario(username,password)
        viewModel.usuarios.observe(this, Observer{response ->
            response.clone().enqueue(object : Callback<List<UsuariosItem>?> {
                override fun onFailure(call: Call<List<UsuariosItem>?>, t: Throwable) {
                    Toast.makeText(applicationContext,"Error: " + t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<List<UsuariosItem>?>, response: Response<List<UsuariosItem>?>) {
                    val responseBody=response.body()
                    if (responseBody != null)
                    {
                        usuario=responseBody
                        Toast.makeText(applicationContext,"Bienvenido", Toast.LENGTH_SHORT).show()
                        val home_activity = Intent(applicationContext,HomeActivity::class.java)
                        startActivity((home_activity))
                    }
                }
            })
        })
    }

    private fun register(){
        title="Iniciar sesión"
        register_button.setOnClickListener(){

        }
    }
}