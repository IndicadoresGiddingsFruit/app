package com.example.indicadoresapp.muestreos

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.indicadoresapp.ApiService.MainViewModelFactory
import com.example.indicadoresapp.ApiService.Repository
import com.example.indicadoresapp.ApiService.ViewModel
import com.example.indicadoresapp.DBRoom.viewModelRoom
import com.example.indicadoresapp.R
import com.example.indicadoresapp.modelos.catalogos.CamposItem
import com.example.indicadoresapp.modelos.muestreos.ProdMuestreo
import kotlinx.android.synthetic.main.alert_dialog.*
import kotlinx.android.synthetic.main.fragment_nuevo_muestreo.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NuevoMuestreo : Fragment() {
    private lateinit var viewModel: ViewModel
    private val viewModelRoom: viewModelRoom by viewModels()
    private lateinit var cod_prodTxt: EditText
    private lateinit var productorTxt: EditText
    private lateinit var telefonoTxt: EditText
    private lateinit var inicio_cosechaTxt: EditText
    private lateinit var cajasEstimadasTxt: EditText
    var campos: List<String> = mutableListOf<String>()

    private val adapter by lazy {
        ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, mutableListOf<String>())
    }

    var campoSelected=0
    var cod_Prod=""

    private lateinit var cod_camposSpinner: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_nuevo_muestreo, container, false)
        inicio_cosechaTxt = view.findViewById<EditText>(R.id.inicio_cosecha)
        inicio_cosechaTxt.setOnClickListener{ showDate()}

        cod_prodTxt = view.findViewById<EditText>(R.id.cod_prod)

        cod_prodTxt.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?)  = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)  = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    cod_Prod=cod_prodTxt.text.toString()
                    initSpinner(cod_Prod,0)
                }
                catch(e: Exception){

                }
            }
        })

        productorTxt = view.findViewById<EditText>(R.id.productor)
        productorTxt.setEnabled(false)

        cod_camposSpinner = view.findViewById(R.id.cod_campos)

        if(cod_camposSpinner!=null) {
            //val adapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_item, campos)
            //cod_camposSpinner.setOnTouchListener(this)

            /* val adapter = ArrayAdapter<Int>(activity!!, android.R.layout.simple_spinner_dropdown_item)
             cod_camposSpinner.adapter = adapter
             adapter.addAll(Arrays.asList(1))*/

            cod_camposSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    cod_Prod=cod_prodTxt.text.toString()
                    //itemselected=adapter.getItem(position).toString();  //adapter.getItem(position)!!
                    val fila: String = adapter.getItem(position).toString()
                    val parts = fila.split("-").toTypedArray()
                    val cod_Campo = parts[0]
                    val x=cod_Campo.replace(" ", "")
                    campoSelected = x.toInt()
                }
            }
        }

        telefonoTxt = view.findViewById<EditText>(R.id.telefono)
        cajasEstimadasTxt= view.findViewById<EditText>(R.id.cajasEstimadas)

        val postNvoMuestreo_button=view.findViewById<Button>(R.id.postNvoMuestreo_button)

        postNvoMuestreo_button.setOnClickListener(){
            addDB()
            //peticionPost()
        }

        //ROOM
        //viewModelRoom=ViewModelProvider(this).get(viewModelRoom::class.java)

        //API
        val repository= Repository()
        val viewModelFactory= MainViewModelFactory(repository)
        viewModel= ViewModelProvider(this,viewModelFactory).get(ViewModel::class.java)

        viewModel.responsecampos.observe(viewLifecycleOwner, Observer { response ->
            response.clone().enqueue(object : Callback<List<CamposItem>?> {

                override fun onFailure(call: Call<List<CamposItem>?>, t: Throwable) {
                    Toast.makeText(activity, "Error: " + t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<List<CamposItem>?>, response: Response<List<CamposItem>?>) {
                    val responseBody: List<CamposItem>? = response.body()
                    if (responseBody != null)
                    {
                        if(cod_Prod.length==5 && campoSelected==0)
                        {
                            for (x in responseBody) {
                                productorTxt.setText(x.productor)
                            }
                            adapter.clear()
                            adapter.addAll(responseBody.map{ it.info })
                            cod_camposSpinner.adapter = adapter
                            //itemselected=adapter.getItem(0).toString(); //campoSelected = adapter.getItem(0)!!
                        }

                        else{
                            for (x in responseBody)
                            {
                                productorTxt.setText(x.productor)
                            }
                        }

                    }
                }
            })
        })
        return view
    }

    private fun limpiar() {
        //campos = mutableListOf<Int>()
        cod_camposSpinner.setAdapter(null)
        cod_prodTxt.setText("")
        productorTxt.setText("")
        telefonoTxt.setText("")
        inicio_cosechaTxt.setText("")
        cajasEstimadasTxt.setText("")
    }

    private fun validacion():Boolean{
        if(cod_prodTxt.length()!=5){
            cod_prodTxt.setError("El código debe ser de 5 dígitos, rellene con 0 al principio")
            cod_prodTxt.requestFocus()
            return false
        }
        if(telefonoTxt.length() != 10){
            telefonoTxt.setError("El teléfono debe ser de 10 digitos")
            telefonoTxt.requestFocus()
            return false
        }
        if(inicio_cosechaTxt.text.isNullOrEmpty()){
            inicio_cosechaTxt.setError("Seleccione una fecha válida")
            inicio_cosechaTxt.requestFocus()
            return false
        }
        if(cajasEstimadasTxt.text.isNullOrEmpty()){
            cajasEstimadasTxt.setError("Este campo no puede quedar vacío")
            cajasEstimadasTxt.requestFocus()
            return false
        }
        return true
    }

    private fun showDate() {
        val datePicker= com.example.indicadoresapp.DatePicker { year, month, day ->
            onDateSelected(
                year ,
                month,
                day
            )
        }
        datePicker.show(requireFragmentManager(),"inicio_cosecha")
    }

    fun onDateSelected(year:Int,month:Int,day:Int){
        inicio_cosecha.setText("$year-$month-$day")
    }

    private fun initSpinner(cod_Prod:String,cod_Campo:Int)
    {
        if(cod_Prod.length==5)
        {
            //campos = mutableListOf<Int>()
            viewModel.getCampos(cod_Prod, cod_Campo)
             viewModel.responsecampos.observe(this, Observer { response ->
                 response.clone().enqueue(object : Callback<List<CamposItem>?> {

                     override fun onFailure(call: Call<List<CamposItem>?>, t: Throwable) {
                         Toast.makeText(activity, "Error: " + t.message, Toast.LENGTH_LONG).show()
                     }

                     override fun onResponse(call: Call<List<CamposItem>?>,response: Response<List<CamposItem>?>) {
                         val responseBody: List<CamposItem>? = response.body()
                         if (responseBody != null)
                         {
                             val adapter = ArrayAdapter(
                                activity!!,
                                android.R.layout.simple_spinner_item,
                                campos
                            )

                                for (x in responseBody) {
                                    productorTxt.setText(x.productor)
                                   // campos += x.cod_Campo
                                }
                                adapter.addAll(responseBody.map{ it.info })
                                cod_camposSpinner.adapter = adapter
                                //itemselected=adapter.getItem(0).toString(); //campoSelected = adapter.getItem(0)!!
                        }
                    }
                })
            })
        }
        else {
            cod_prodTxt.setError("El código debe ser de 5 digitos, rellene con 0 al principio")
            cod_prodTxt.requestFocus()
        }
    }

    private fun loadInfo(cod_Prod:String,cod_Campo:Int){
        if(cod_Prod.length==5 && cod_Campo>0)
        {
            viewModel.getCampos(cod_Prod, cod_Campo)
            /*viewModel.responsecampos.observe(this, Observer { response ->
                response.clone().enqueue(object : Callback<List<CamposItem>?> {

                    override fun onFailure(call: Call<List<CamposItem>?>, t: Throwable) {
                        Toast.makeText(activity, "Error: " + t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<List<CamposItem>?>,response: Response<List<CamposItem>?>) {
                        val responseBody: List<CamposItem>? = response.body()
                        if (responseBody != null)
                        {
                                for (x in responseBody)
                                {
                                    productorTxt.setText(x.productor)
                                    campoTxt.setText(x.campo)
                                    localidadTxt.setText(x.ubicacion)
                                    tipoTxt.setText(x.tipo)
                                    productoTxt.setText(x.producto)
                                }
                        }
                    }
                })
            })*/
        }
        else {
            cod_prodTxt.setError("El código debe ser de 5 digitos, rellene con 0 al principio")
            cod_prodTxt.requestFocus()
        }
    }

    private fun peticionPost() {
        if (validacion()) {
            val post = ProdMuestreo(
                0,
                2,
                cod_prodTxt.text.toString(),
                campoSelected,
                null,
                telefonoTxt.text.toString(),
                inicio_cosechaTxt.text.toString(),
                1,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                cajasEstimadasTxt.text.toString().toInt()
            )
            viewModel.postMuestreo("revisar",post)

            viewModel.muestreos.observe(viewLifecycleOwner, Observer { response ->
                response.clone().enqueue(object : Callback<ProdMuestreo> {
                    override fun onFailure(call: Call<ProdMuestreo>, t: Throwable) {
                        Toast.makeText(activity, "Error: " + t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<ProdMuestreo>, response: Response<ProdMuestreo>) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                            activity,
                            "Datos enviados correctamente",
                            Toast.LENGTH_LONG
                        ).show()
                            limpiar()

                        } else {
                            Log.d("Main", response.errorBody().toString())
                            Log.d("Main", response.code().toString())
                            Log.d("Main", response.errorBody().toString())

                            //alert si la solicitud ya existe
                            if(response.errorBody()!!.string()=="La solicitud ya existe") {
                                val builder = AlertDialog.Builder(activity!!)
                                val alert = layoutInflater.inflate(R.layout.alert_dialog, null)
                                builder.setView(alert)
                                val dialog = builder.create()
                                dialog.show()

                                val ok=alert.findViewById<Button>(R.id.ok_button)
                                val cancel=alert.findViewById<Button>(R.id.cancel_button)

                                ok.setOnClickListener(){
                                    viewModel.postMuestreo("param",post)

                                    viewModel.muestreos.observe(viewLifecycleOwner, Observer { response ->
                                        response.clone().enqueue(object : Callback<ProdMuestreo> {
                                            override fun onFailure(call: Call<ProdMuestreo>, t: Throwable) {
                                                Toast.makeText(activity, "Error: " + t.message, Toast.LENGTH_LONG).show()
                                            }
                                            override fun onResponse(call: Call<ProdMuestreo>, response: Response<ProdMuestreo>) {
                                                if (response.isSuccessful) {
                                                    Toast.makeText(
                                                        activity,
                                                        "Datos enviados correctamente",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                    //limpiar()

                                                } else {
                                                    Log.d("Main", response.errorBody().toString())
                                                    Log.d("Main", response.code().toString())
                                                    Log.d("Main", response.errorBody().toString())
                                                    Toast.makeText(activity,  response.errorBody()!!.string(), Toast.LENGTH_LONG).show()
                                                }
                                            }
                                        })
                                    })
                                    dialog.hide()
                                }

                                cancel.setOnClickListener() {
                                    dialog.hide()
                                }
                            }
                            else {
                                Toast.makeText(activity,  response.errorBody()!!.string(), Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                })
            })
        }
    }

     private fun addDB() {
         if (validacion()) {
             val muestreo = ProdMuestreo(
                 0,
                 2,
                 cod_prodTxt.text.toString(),
                 campoSelected,
                 null,
                 telefonoTxt.text.toString(),
                 inicio_cosechaTxt.text.toString(),
                 1,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 cajasEstimadasTxt.text.toString().toInt()
             )

             viewModelRoom.insertMuestreo(muestreo)
             Toast.makeText(activity, "Datos guardados correctamente", Toast.LENGTH_LONG ).show()

         }
     }
}
