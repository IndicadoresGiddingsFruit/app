package com.example.indicadoresapp.muestreos

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.indicadoresapp.ApiService.MainViewModelFactory
import com.example.indicadoresapp.ApiService.Repository
import com.example.indicadoresapp.ApiService.ViewModel
import com.example.indicadoresapp.R
import com.example.indicadoresapp.modelos.muestreos.MuestreosItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MuestreosFragment : Fragment(), MyMuestreosRecyclerViewAdapter.ClickListener{
    private lateinit var adapter:MyMuestreosRecyclerViewAdapter
    private lateinit var viewModel: ViewModel
    lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_muestreos, container, false)

        val searchView= view.findViewById<SearchView>(R.id.searchView_muestreos)
        searchView.isSubmitButtonEnabled=true
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter!!.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter!!.filter.filter(newText)
                return true
            }
        })

        navController = findNavController()
        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            navController.navigate(R.id.nuevoMuestreo)
        }

        val repository= Repository()
        val viewModelFactory= MainViewModelFactory(repository)
        viewModel= ViewModelProvider(this,viewModelFactory).get(ViewModel::class.java)

        initViewModel(view)
        initViewModel()
        return view
    }

    private fun initViewModel(view: View){
        val recyclerView= view.findViewById<RecyclerView>(R.id.recycler_muestreos)
        recyclerView.layoutManager= LinearLayoutManager(requireActivity())
        adapter=MyMuestreosRecyclerViewAdapter(this)
        recyclerView.adapter=adapter
    }

    private fun initViewModel(){
        viewModel.getMuestreos(1,"null","I")
        viewModel.responsegetmuestreos.observe(viewLifecycleOwner, Observer{response ->
            response.clone().enqueue(object : Callback<List<MuestreosItem>?> {
                override fun onFailure(call: Call<List<MuestreosItem>?>, t: Throwable) {
                    Toast.makeText(activity,"Error: " + t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<List<MuestreosItem>?>, response: Response<List<MuestreosItem>?>) {
                    val responseBody=response.body()
                    if (responseBody != null) {
                        adapter.setUpdateData(responseBody)
                    }
                }
            })
        })
    }

    override fun onItemClick(muestreosModel: MuestreosItem) {

        var fecha_ejecucion=""
        if(muestreosModel.fecha_ejecucion!=""){
            fecha_ejecucion= muestreosModel.fecha_ejecucion.toString()
        }
        var sector=""
        if(muestreosModel.sector!=""){
            sector= muestreosModel.sector.toString()
        }

        var estatus=""
        if(muestreosModel.estatus!=""){
            estatus= muestreosModel.estatus.toString()
        }

        var incidencia=""
        if(muestreosModel.incidencia!=""){
            incidencia= muestreosModel.incidencia.toString()
        }

        var propuesta=""
        if(muestreosModel.propuesta!=""){
            propuesta= muestreosModel.propuesta.toString()
        }

        var liberacion=""
        if(muestreosModel.liberacion!=""){
            liberacion= muestreosModel.liberacion.toString()
        }

        val nextAction=MuestreosFragmentDirections.actionMuestreosFragmentToDetalleMuestreosFragment(
            muestreosModel.idMuestreo!!,
            muestreosModel.cod_Prod!!,
            muestreosModel.productor!!,
            muestreosModel.cod_Campo!!,
            muestreosModel.campo!!,
            muestreosModel.inicio_cosecha!!,
            muestreosModel.telefono!!,
            muestreosModel.cajasEstimadas!!,

            fecha_ejecucion,
            sector,
            estatus,
            incidencia,
            propuesta,
            liberacion
        )
        nextAction.setIdMuestreo(muestreosModel.idMuestreo!!)
        nextAction.setCodProd(muestreosModel.cod_Prod!!)
        nextAction.setProductor(muestreosModel.productor!!)
        nextAction.setCodCampo(muestreosModel.cod_Campo!!)
        nextAction.setCampo(muestreosModel.campo!!)
        nextAction.setInicioCosecha(muestreosModel.inicio_cosecha)
        nextAction.setTelefono(muestreosModel.telefono)
        nextAction.setCajasEstimadas(muestreosModel.cajasEstimadas)
        nextAction.setFechaEjecucion(fecha_ejecucion)
        nextAction.setSector(sector)
        nextAction.setEstatus(estatus)
        nextAction.setIncidencia(incidencia)
        nextAction.setPropuesta(propuesta)
        nextAction.setLiberacion(liberacion)

        Navigation.findNavController(requireView()).navigate(nextAction)
    }
}