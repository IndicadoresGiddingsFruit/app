package com.example.indicadoresapp.muestreos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.indicadoresapp.ApiService.ViewModel
import com.example.indicadoresapp.R
import com.example.indicadoresapp.modelos.muestreos.ProdMuestreoSector


class Sectores_adapter(val clickListener:ClickListener): RecyclerView.Adapter<Sectores_adapter.MyViewHolder>(){
    private var items: List<ProdMuestreoSector> = mutableListOf<ProdMuestreoSector>()
    private lateinit var viewModel: ViewModel

    fun setUpdateData(items:List<ProdMuestreoSector>){
        if(this.items!=null){
            this.items=items
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.lista_sectores, parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        if(items.isNullOrEmpty()){
            return 0
        }
        return items.size
    }

    inner class MyViewHolder (itemView: View): RecyclerView.ViewHolder (itemView) {
        var idSectortxt: TextView =itemView.findViewById(R.id.idSector)
        var sectortx: TextView =itemView.findViewById(R.id.Sector)
        var delete_button: ImageButton =itemView.findViewById(R.id.delete_button)

        fun bind(data: ProdMuestreoSector){
            idSectortxt.text = data.id.toString()
            sectortx.text = data.sector.toString()
            /*delete_button.setOnClickListener{
                peticionDelete(data.id)
            }*/
        }
    }

    private fun peticionDelete(id:Int) {
       /* viewModel.deleteSector(id)
        viewModel.responsedelsector.observe(this, Observer{response ->
            response.clone().enqueue(object : Callback<ProdMuestreoSector> {
                override fun onFailure(call: Call<ProdMuestreoSector>, t: Throwable) {
                    Toast.makeText(this,"Error: " + t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<ProdMuestreoSector>, response: Response<ProdMuestreoSector>) {
                    val responseBody=response.body()
                    if (responseBody != null) {

                    }
                }
            })
        })*/
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
        holder.bind(items.get(position))
        holder.itemView.setOnClickListener{
            clickListener.onItemClick(items.get(position))
        }
    }

    interface ClickListener{
        fun onItemClick(sectoresModel: ProdMuestreoSector)
    }
}
