package com.example.indicadoresapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.indicadoresapp.ApiService.ViewModel
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)

        /*val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 .setAction("Action", null).show()
           */
            navController.navigate(R.id.nav_new)
        }*/

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_tools
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //guardar_token_movil()
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return true
    }*/

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_logout ->{
                Toast.makeText(applicationContext,"Salir", Toast.LENGTH_SHORT).show()
                true
            }
            else->{
                return super.onOptionsItemSelected(item)
            }
        }
    }

    /* private fun guardar_token_movil() {
         val repository= Repository()
         val viewModelFactory= MainViewModelFactory(repository)
         viewModel= ViewModelProvider(this,viewModelFactory).get(ViewModel::class.java)

         FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener{
             it.result?.token?.let{
                 //println("token:: ${it} ")

                 val data = UsuariosItem(
                     0,
                     "",
                     "",
                     "",
                     "",
                     null,
                     null,
                     0,
                     "",
                     null,
                     it
                 )

                 viewModel.patchUsuario(data)
                 viewModel.usuario.observe(this, Observer { response ->
                     response.clone().enqueue(object : Callback<UsuariosItem> {
                         override fun onFailure(call: Call<UsuariosItem>, t: Throwable) {
                             Toast.makeText(applicationContext, "Error: " + t.message, Toast.LENGTH_LONG).show()
                         }

                         override fun onResponse(call: Call<UsuariosItem>, response: Response<UsuariosItem>) {
                             if (response.isSuccessful) {

                             } else {
                                 Log.d("Main", response.errorBody().toString())
                                 Log.d("Main", response.code().toString())
                                 Log.d("Main", response.errorBody().toString())
                             }
                         }
                     })
                 })
             }
         }*/

    //temas
    //FirebaseMessaging.getInstance().subscribeToTopic("Produccion")

    //recuperar info
    /*val url=intent.getStringExtra("url")
    url?.let{
        println("url info:: ${it} ")
    }*/

}