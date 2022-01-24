package com.example.indicadoresapp.muestreos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.indicadoresapp.ApiService.Constants.Companion.formatter
import com.example.indicadoresapp.ApiService.Constants.Companion.parser
import com.example.indicadoresapp.R
import com.example.indicadoresapp.modelos.muestreos.MuestreosItem

class MyMuestreosRecyclerViewAdapter(val clickListener:ClickListener):RecyclerView.Adapter<MyMuestreosRecyclerViewAdapter.MyViewHolder>(),Filterable {
   private var items: List<MuestreosItem> = mutableListOf<MuestreosItem>()
   private var itemsFilter: List<MuestreosItem> = mutableListOf<MuestreosItem>()

    fun setUpdateData(items:List<MuestreosItem>){
        if(this.items!=null){
            this.items=items
            this.itemsFilter=items
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        return object:Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults=FilterResults()
                if(constraint==null || constraint.length<0){
                    filterResults.count=itemsFilter.size
                    filterResults.values=itemsFilter
                }else{
                    var searchCr=constraint.toString().toLowerCase()
                    var itemModal= mutableListOf<MuestreosItem>()
                    for(item in itemsFilter){
                        if(item.productor.contains(searchCr.toUpperCase()) || item.cod_Prod.contains(searchCr)){
                            itemModal.add(item)
                        }
                    }
                    filterResults.count=itemModal.size
                    filterResults.values=itemModal
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
                items=filterResults!!.values as List<MuestreosItem>
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val view= LayoutInflater.from(parent.context).inflate(R.layout.lista_muestreos, parent,false)
       return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        if(items.isNullOrEmpty()){
            return 0
        }
        return items.size
    }

    inner class MyViewHolder (itemView: View):RecyclerView.ViewHolder (itemView) {
        var icono_analisisImage: ImageView=itemView.findViewById(R.id.icono_muestreo)
        var cod_prodTetxView:TextView=itemView.findViewById(R.id.cod_prod)
        var productorTetxView:TextView=itemView.findViewById(R.id.productor)
        var campoTetxView:TextView=itemView.findViewById(R.id.campo)
        //var sectorTetxView:TextView=itemView.findViewById(R.id.sector)
        var fecha_solicitudTetxView:TextView=itemView.findViewById(R.id.fecha_solicitud)
        var asesorTetxView:TextView=itemView.findViewById(R.id.asesor)

        fun bind(data: MuestreosItem){
            cod_prodTetxView.text = "Código: "+ data.cod_Prod
            productorTetxView.text = "Productor: "+ data.productor
            campoTetxView.text = "Campo: " + data.cod_Campo.toString() + " - " + data.campo

            fecha_solicitudTetxView.text = "Fecha de solicitud: " + formatter.format(parser.parse(data.fecha_solicitud))

            if(data.analisis=="L"){
                icono_analisisImage.setBackgroundResource(R.mipmap.icon_liberado)
            }
            else if(data.analisis=="R"){
                icono_analisisImage.setBackgroundResource(R.mipmap.icon_con_residuos)
            }
            else if(data.analisis=="P"){
                icono_analisisImage.setBackgroundResource(R.mipmap.icon_proceso)
            }
            else if(data.analisis=="F"){
                icono_analisisImage.setBackgroundResource(R.mipmap.icon_fuera_limite)
            }
            else{
                icono_analisisImage.setBackgroundResource(R.mipmap.icon_analisis_pendiente)
            }
            asesorTetxView.text = "Asesor: "+data.asesor
        }
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
        holder.bind(items.get(position))
        holder.itemView.setOnClickListener{
            clickListener.onItemClick(items.get(position))
        }
    }

    interface ClickListener{
        fun onItemClick(muestreosModel: MuestreosItem)
    }
}
