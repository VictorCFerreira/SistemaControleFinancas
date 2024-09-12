package com.example.sistemacontrolefinancas.adapter

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistemacontrolefinancas.R
import com.example.sistemacontrolefinancas.database.DatabaseHandler.Companion.DATALANCAMENTO
import com.example.sistemacontrolefinancas.database.DatabaseHandler.Companion.DETALHE
import com.example.sistemacontrolefinancas.database.DatabaseHandler.Companion.ID
import com.example.sistemacontrolefinancas.database.DatabaseHandler.Companion.TIPO
import com.example.sistemacontrolefinancas.database.DatabaseHandler.Companion.VALOR
import com.example.sistemacontrolefinancas.entity.Financa
import org.w3c.dom.Text

class FinancaListaAdapter (val context : Context, val cursor : Cursor) : BaseAdapter() {

    override fun getCount(): Int {
        return cursor.count
    }

    override fun getItem(position: Int): Any {
        cursor.moveToPosition( position )

        return Financa(
            cursor.getInt( ID ),
            cursor.getString( TIPO ),
            cursor.getString( DETALHE ),
            cursor.getDouble( VALOR ),
            cursor.getString( DATALANCAMENTO )
        )
    }

    override fun getItemId(position: Int): Long {
        cursor.moveToPosition( position )

        return cursor.getInt( ID ).toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService( Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate( R.layout.elemento_lista, null )

        val tvValor = v.findViewById<TextView>( R.id.tvValor )
        val tvDataLancamento = v.findViewById<TextView>( R.id.tvDataLancamento )
        val tvTipo = v.findViewById<TextView>( R.id.tvTipo )
        val tvDetalhe = v.findViewById<TextView>( R.id.tvDetalhe )
        val tvIcone = v.findViewById<TextView>(R.id.tvIcone)


        cursor.moveToPosition( position )

        val cor = if (cursor.getString( TIPO ) == "Creditos") Color.GREEN else Color.RED

        tvValor.setText( "Valor: " + cursor.getDouble( VALOR ).toString() )
        tvDataLancamento.setText( "Data: " + cursor.getString( DATALANCAMENTO ) )
        tvTipo.setText( "Tipo: " + cursor.getString( TIPO ) )
        tvDetalhe.setText("Detalhe: " +  cursor.getString( DETALHE ) )
        tvIcone.setText(" $")
        tvIcone.setTextColor(cor)

        return v
    }
}