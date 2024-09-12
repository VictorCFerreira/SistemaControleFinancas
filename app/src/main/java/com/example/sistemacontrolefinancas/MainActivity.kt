package com.example.sistemacontrolefinancas

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import com.example.sistemacontrolefinancas.database.DatabaseHandler
import com.example.sistemacontrolefinancas.entity.Financa

class MainActivity : AppCompatActivity() {

    private lateinit var spTipo: Spinner
    private lateinit var spDetalhe: Spinner
    private lateinit var etDataLancamento: EditText
    private lateinit var etValor : EditText
    private lateinit var btLancar: Button
    private lateinit var btVerLancamentos: Button
    private lateinit var btSaldo: Button

    private lateinit var banco : DatabaseHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spTipo = findViewById( R.id.spTipo )
        spDetalhe = findViewById( R.id.spDetalhe )
        etDataLancamento = findViewById( R.id.etDataLancamento )
        etValor = findViewById( R.id.etValor )
        btLancar = findViewById( R.id.btLancar )
        btVerLancamentos = findViewById( R.id.btVerLancamentos )
        btSaldo = findViewById( R.id.btSaldo )

        banco = DatabaseHandler( this )

        val detalhesCredito = listOf<String>("Salario", "Extra")
        val adpDetalhesCredito = ArrayAdapter<String> ( this, android.R.layout.simple_list_item_1, detalhesCredito )

        val detalhesDebito = listOf<String>( "Alimentacao", "Transporte", "Saude", "Moradia" )
        val adpDetalhesDebito = ArrayAdapter<String> ( this, android.R.layout.simple_list_item_1, detalhesDebito )

        val tipos = listOf<String>( "Creditos", "Debitos" )
        val adpTipos = ArrayAdapter<String> ( this, android.R.layout.simple_list_item_1, tipos )

        spTipo.adapter = adpTipos;


        spTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                spDetalhe.adapter = if (spTipo.selectedItem.toString() == "Creditos") adpDetalhesCredito else adpDetalhesDebito
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("Not yet implemented")
            }

        }

    }

    fun btLancarOnClick(view: View) {
        if(etValor.text.length < 1){
            etValor.error = "Informe um valor válido"
            return
        }
        if(etDataLancamento.text.length < 1){
            etDataLancamento.error = "Informe uma data válida"
            return
        }

        val valor = if (spTipo.selectedItem.toString() == "Creditos")  etValor.text.toString().toDouble() else (etValor.text.toString().toDouble() * -1)


        val financa = Financa(
            -1,
            spTipo.selectedItem.toString(),
            spDetalhe.selectedItem.toString(),
            valor,
            etDataLancamento.text.toString())

        banco.insert(financa)

        Toast.makeText(this, "Financa incluida! ", Toast.LENGTH_LONG ).show()

        etValor.error = null
        etDataLancamento.error = null
    }

    fun btVerLancamentosOnClick(view: View) {
        val intent = Intent( this, ListarActivity::class.java )
        startActivity(intent)

    }

    override fun onStart() {
        super.onStart()
    }

    fun btSaldoOnClick(view: View) {
        val builder = AlertDialog.Builder(this)
            .setTitle("Saldo")
            .setMessage("Seu Saldo é de: " + banco.getSumOfValor().toString())
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = builder.create()
        dialog.show()

        Toast.makeText( baseContext, "Saldo: " + banco.getSumOfValor().toString(), Toast.LENGTH_LONG ).show()

    }

}