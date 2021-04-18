package daniel.ornelas.misnotas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.io.File
import java.lang.Exception

class AdaptadorNotas: BaseAdapter {
    var context: Context
    var notas = ArrayList<Nota>()

    constructor(context: Context, notas: ArrayList<Nota>){
        this.context =  context
        this.notas = notas
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflador = LayoutInflater.from(context)
        var  vista = inflador.inflate(R.layout.nota_layout, null)
        var nota = notas[position]

        var titulo = vista.findViewById(R.id.tv_titulo_det) as TextView
        var contenido = vista.findViewById(R.id.tv_contenido_det) as  TextView
        var btn_borrar = vista.findViewById(R.id.btn_borrar) as ImageView

        titulo.text = nota.titulo
        contenido.text = nota.contenido
        
        btn_borrar.setOnClickListener { 
            eliminar(nota.titulo)
            notas.remove(nota)
            this.notifyDataSetChanged()
        }

        return vista

    }

    override fun getItem(position: Int): Any {
        return notas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return notas.size
    }

    private fun eliminar(titulo: String){
        if(titulo == ""){
            Toast.makeText(context, "Error: titulo vacio", Toast.LENGTH_SHORT).show()
        } else{
            try {
                val archivo = File(ubicacion(), titulo + ".txt")
                archivo.delete()
            }catch (e: Exception){
                Toast.makeText(context, "Error al eliminar el archivo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun ubicacion(): String {
        val album = File(context?.getExternalFilesDir(null), "notas")
        if (!album.exists()){
            album.mkdir()
        }
        return album.absolutePath
    }
}