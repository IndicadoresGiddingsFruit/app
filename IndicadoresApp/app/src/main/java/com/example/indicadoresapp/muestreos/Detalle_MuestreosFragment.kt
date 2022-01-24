package com.example.indicadoresapp.muestreos

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.indicadoresapp.ApiService.Constants
import com.example.indicadoresapp.ApiService.MainViewModelFactory
import com.example.indicadoresapp.ApiService.Repository
import com.example.indicadoresapp.ApiService.ViewModel
import com.example.indicadoresapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_detalle__muestreos.*


class Detalle_MuestreosFragment : Fragment(){

    private var data: Detalle_MuestreosFragmentArgs? = null
    private lateinit var viewModel: ViewModel
    private lateinit var txtcod_prod: EditText
    private lateinit var txtproductor: EditText
    private lateinit var txtcampo: TextView
    private lateinit var txtinicio_cosecha: EditText
    private lateinit var telefonotxt: EditText
    private lateinit var cjs_estimadastxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = Detalle_MuestreosFragmentArgs.fromBundle(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detalle__muestreos, container, false)
        txtcod_prod = view.findViewById<EditText>(R.id.cod_prod)
        txtproductor = view.findViewById<EditText>(R.id.productor)
        txtcampo = view.findViewById<EditText>(R.id.campo)
        txtinicio_cosecha = view.findViewById<EditText>(R.id.inicio_cosecha)
        telefonotxt = view.findViewById<EditText>(R.id.telefono)
        cjs_estimadastxt = view.findViewById<EditText>(R.id.cajasEstimadas)

        txtcod_prod.setText(data!!.codProd)
        txtproductor.setText(data!!.productor)
        txtcampo.setText(data!!.campo)
        txtinicio_cosecha.setText(data!!.inicioCosecha)
        telefonotxt.setText(data!!.telefono)
        cjs_estimadastxt.setText((data!!.cajasEstimadas).toString())

        val put_button = view.findViewById<Button>(R.id.put_buttonMuestreo)
        put_button.setOnClickListener {
            peticionPut()
        }

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ViewModel::class.java)

        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail_muestreo, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.inocuidad -> {//action_inocuidadMuestreoFragment_to_detalle_MuestreosFragment
                val nextAction=Detalle_MuestreosFragmentDirections.actionDetalleMuestreosFragmentToInocuidadMuestreoFragment(
                    data!!.idMuestreo!!,
                    data!!.codProd!!,
                    data!!.productor!!,
                    data!!.codCampo!!,
                    data!!.campo!!,
                    data!!.fechaEjecucion!!,
                    data!!.sector!!,
                )
                nextAction.setIdMuestreo(data!!.idMuestreo)
                nextAction.setCodProd(data!!.codProd!!)
                nextAction.setProductor(data!!.productor!!)
                nextAction.setCodCampo(data!!.codCampo!!)
                nextAction.setCampo(data!!.campo!!)
                nextAction.setFechaEjecucion(data!!.fechaEjecucion!!)
                nextAction.setSector(data!!.sector!!)

                Navigation.findNavController(requireView()).navigate(nextAction)

                findNavController().navigate(R.id.inocuidadMuestreo)
                true
            }
            R.id.calidad -> {
                findNavController().navigate(R.id.calidadMuestreo)
                true
            }
            R.id.produccion -> {
                findNavController().navigate(R.id.produccionMuestreo)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDate() {
        val datePicker = com.example.indicadoresapp.DatePicker { day, month, year ->
            onDateSelected(
                day,
                month,
                year
            )
        }
        datePicker.show(requireFragmentManager(), "fecha_ejecucion")
    }

    fun onDateSelected(day: Int, month: Int, year: Int) {
        inicio_cosecha.setText("$day-$month-$year")
    }

    private fun validacion(): Boolean {
       /* if (sectortxt.length() == 0) {
            sectortxt.setError("Este campo no puede quedar vacío")
            sectortxt.requestFocus()
            return false
        }
        /*if (txtfecha_ejecucion.text.isNullOrEmpty()) {
            txtfecha_ejecucion.setError("Seleccione una fecha válida")
            txtfecha_ejecucion.requestFocus()
            return false
        }*/*/
        return true
    }

    private fun peticionPut() {
        /*if (validacion()) {
            val put = ProdMuestreo(
                null,
                2,
                null,
                null,
                null,
                null,
                null,
                1,
                null,
                null,
                fecha_ejecuciontxt.text.toString(),
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
            viewModel.putMuestreoInocuidad(
                data!!.id,
                1,
                (sectortxt.text.toString()).toInt(),
                put
            )
            viewModel.muestreos.observe(viewLifecycleOwner, Observer { response ->
                response.clone().enqueue(object : Callback<ProdMuestreo> {
                    override fun onFailure(call: Call<ProdMuestreo>, t: Throwable) {
                        Toast.makeText(activity, "Error: " + t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<ProdMuestreo>,
                        response: Response<ProdMuestreo>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                activity,
                                "Datos guardados correctamente",
                                Toast.LENGTH_LONG
                            ).show()
                            limpiar()
                        } else {
                            Log.d("Main", response.body().toString())
                            Log.d("Main", response.code().toString())
                            Log.d("Main", response.message())
                            Toast.makeText(
                                activity,
                                "Error al enviar la información intentelo mas tarde. Código: " + response.code(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                })
            })
        }*/
    }

    private fun limpiar() {

    }

}


