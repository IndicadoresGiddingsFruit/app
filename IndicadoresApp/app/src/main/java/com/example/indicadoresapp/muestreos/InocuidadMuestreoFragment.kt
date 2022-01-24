package com.example.indicadoresapp.muestreos

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.indicadoresapp.ApiService.Constants
import com.example.indicadoresapp.ApiService.MainViewModelFactory
import com.example.indicadoresapp.ApiService.Repository
import com.example.indicadoresapp.ApiService.ViewModel
import com.example.indicadoresapp.R
import com.example.indicadoresapp.modelos.muestreos.ProdMuestreo
import com.example.indicadoresapp.modelos.muestreos.ProdMuestreoSector
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InocuidadMuestreoFragment :Fragment(), Sectores_adapter.ClickListener {
    private lateinit var adapter: Sectores_adapter
    private var data: InocuidadMuestreoFragmentArgs? = null
    private lateinit var viewModel: ViewModel
    private lateinit var txtcod_prod: TextView
    private lateinit var txtproductor: TextView
    private lateinit var txtcampo: TextView
    private lateinit var txtfecha_ejecucion: TextView
    private lateinit var sectortxt: EditText
    private lateinit var fecha_ejecuciontxt: EditText
    private lateinit var titulo_recycler: TextView
    private lateinit var recycler_sectores: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = InocuidadMuestreoFragmentArgs.fromBundle(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_inocuidad_muestreo, container, false)
        txtcod_prod = view.findViewById<TextView>(R.id.txtcod_prod)
        txtproductor = view.findViewById<TextView>(R.id.txtproductor)
        txtcampo = view.findViewById<TextView>(R.id.txtcampo)
        txtfecha_ejecucion = view.findViewById<TextView>(R.id.txtfecha_ejecucion)
        sectortxt = view.findViewById<EditText>(R.id.sectortxt)
        fecha_ejecuciontxt = view.findViewById<EditText>(R.id.fecha_ejecuciontxt)
        //tbl_sectores= view.findViewById<TableLayout>(R.id.tbl_sectores)
        titulo_recycler = view.findViewById<TextView>(R.id.titulo_recycler)
        recycler_sectores= view.findViewById<RecyclerView>(R.id.recycler_sectores)

        txtcod_prod.text = "Código: " + data!!.idMuestreo
        txtproductor.text = "Productor: " + data!!.productor
        txtcampo.text = "Campo: " + data!!.codCampo + " - " + data!!.campo

        var fechaEjecucion = ""
        if (data!!.fechaEjecucion != "") {
            fechaEjecucion = Constants.formatter.format(Constants.parser.parse(data!!.fechaEjecucion))
        }

        txtfecha_ejecucion.setText("Fecha de muestreo: " + fechaEjecucion)

        fecha_ejecuciontxt.setOnClickListener { showDate() }

        val put_button = view.findViewById<Button>(R.id.put_button)
        put_button.setOnClickListener {
            peticionPut()
        }

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ViewModel::class.java)

        setHasOptionsMenu(true)


        initViewModel(view)
        cargar_tblSectores()
        return view
    }

    private fun initViewModel(view: View){
        val recyclerView= view.findViewById<RecyclerView>(R.id.recycler_sectores)
        recyclerView.layoutManager= LinearLayoutManager(requireActivity())
        adapter= Sectores_adapter(this)
        recyclerView.adapter=adapter
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
        fecha_ejecuciontxt.setText("$day-$month-$year")
    }

    private fun validacion(): Boolean {
        if (sectortxt.length() == 0) {
            sectortxt.setError("Este campo no puede quedar vacío")
            sectortxt.requestFocus()
            return false
        }
        /*if (txtfecha_ejecucion.text.isNullOrEmpty()) {
            txtfecha_ejecucion.setError("Seleccione una fecha válida")
            txtfecha_ejecucion.requestFocus()
            return false
        }*/
        return true
    }

    private fun peticionPut() {
        if (validacion()) {
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
                data!!.idMuestreo,
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
        }
    }

    private fun limpiar() {
        sectortxt.setText("")
        fecha_ejecuciontxt.setText("")
    }

    private fun cargar_tblSectores(){
        viewModel.getSectores(data!!.idMuestreo)
        viewModel.responsesectores.observe(viewLifecycleOwner, Observer { response ->
            response.clone().enqueue(object : Callback<List<ProdMuestreoSector>?> {

                override fun onFailure(call: Call<List<ProdMuestreoSector>?>, t: Throwable) {
                    Log.d("Main", "Error: " + t.message.toString())
                    //Toast.makeText(activity, "Error: " + t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<List<ProdMuestreoSector>?>,
                    response: Response<List<ProdMuestreoSector>?>
                ) {
                    val responseBody: List<ProdMuestreoSector>? = response.body()
                    if (responseBody != null) {
                        titulo_recycler.setVisibility(View.VISIBLE)

                        adapter.setUpdateData(responseBody)
                        /* for (x in responseBody)
                         {
                             val row = TableRow(activity!!)
                             val tv =  TextView(activity!!)

                             row.setPadding(10,10,10,10)
                             tv.setPadding(80,10,0,10)

                             tv.gravity=Gravity.CENTER


                             tv.setText(x.id.toString())
                             tv.setText( x.sector.toString())

                            // tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT))

                             delete_button.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                             delete_button.setBackgroundResource(R.drawable.ic_clear)
                             // add Button to LinearLayout
                             row.addView(delete_button)

                             row.addView(tv)
                             tbl_sectores.addView(row)
                         }*/
                    }
                }
            })
        })
    }

    override fun onItemClick(sectoresModel: ProdMuestreoSector) {

    }
}