package com.example.sistemacontrolefinancas.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sistemacontrolefinancas.entity.Financa


class DatabaseHandler (context : Context) : SQLiteOpenHelper ( context, DATABASE_NAME, null, DATABASE_VERSION ) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL( "CREATE TABLE IF NOT EXISTS ${TABLE_NAME} ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " tipo TEXT, detalhe TEXT, valor DOUBLE, dataLancamento TEXT )")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL( "DROP TABLE IF EXISTS ${TABLE_NAME}" )
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "dbfile.sqlite"
        private const val DATABASE_VERSION = 3
        private const val TABLE_NAME = "financas"
        public const val ID = 0
        public const val TIPO = 1
        public const val DETALHE = 2
        public const val VALOR = 3
        public const val DATALANCAMENTO = 4
    }

    fun insert( financa: Financa) {
        val db = this.writableDatabase

        val registro = ContentValues()
        registro.put( "tipo", financa.tipo )
        registro.put( "detalhe", financa.detalhe )
        registro.put( "valor", financa.valor )
        registro.put( "dataLancamento", financa.dataLancamento )

        db.insert( TABLE_NAME, null, registro )
    }

    fun update( financa : Financa ) {
        val db = this.writableDatabase

        val registro = ContentValues()
        registro.put( "tipo", financa.tipo )
        registro.put( "detalhe", financa.detalhe )
        registro.put( "valor", financa.valor )
        registro.put( "dataLancamento", financa.dataLancamento )

        db.update( TABLE_NAME, registro, "_id=${financa._id}", null )
    }

    fun delete( id : Int ) {
        val db = this.writableDatabase

        db.delete( TABLE_NAME, "_id=${id}", null )
    }

    fun find(id : Int) : Financa? {
        val db = this.writableDatabase

        val registro : Cursor = db.query( TABLE_NAME,
            null,
            "_id=${id}",
            null,
            null,
            null,
            null
        )

        if ( registro.moveToNext() ) {
            val financa = Financa(
                id,
                registro.getString( TIPO ),
                registro.getString( DETALHE ),
                registro.getDouble( VALOR ),
                registro.getString( DATALANCAMENTO )
            )
            return financa
        } else {
            return null
        }
    }

    fun list() : MutableList<Financa> {
        val db = this.writableDatabase

        val registro = db.query( TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        var registros = mutableListOf<Financa>()

        while ( registro.moveToNext() ) {

            val financa = Financa(
                registro.getInt( ID ),
                registro.getString( TIPO ),
                registro.getString( DETALHE ),
                registro.getDouble( VALOR ),
                registro.getString( DATALANCAMENTO )
            )
            registros.add( financa )
        }

        return registros

    }

    fun listCursor() : Cursor {
        val db = this.writableDatabase

        val registro = db.query( TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        return registro
    }

}