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
        private const val DATABASE_VERSION = 1
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

    fun listCursor() : Cursor {
        val db = this.readableDatabase

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

    fun getSumOfValor(): Double {
        val db = this.readableDatabase
        var sum = 0.0

        val cursor = db.rawQuery("SELECT SUM(valor) as total FROM ${TABLE_NAME}", null)

        if (cursor.moveToFirst()) {
            sum = cursor.getDouble(0)
        }
        cursor.close()
        return sum
    }

}