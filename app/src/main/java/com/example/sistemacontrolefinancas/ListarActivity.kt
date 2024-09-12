package com.example.sistemacontrolefinancas

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.sistemacontrolefinancas.adapter.FinancaListaAdapter
import com.example.sistemacontrolefinancas.database.DatabaseHandler

class ListarActivity : AppCompatActivity() {

    private lateinit var lvFinanca : ListView
    private lateinit var banco : DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar)

        lvFinanca = findViewById( R.id.lvFinancas )
        banco = DatabaseHandler( this )


    }

    override fun onStart() {
        super.onStart()
        val financas = banco.listCursor()
        val adapter = FinancaListaAdapter( this, financas )
        lvFinanca.adapter = adapter
    }

}