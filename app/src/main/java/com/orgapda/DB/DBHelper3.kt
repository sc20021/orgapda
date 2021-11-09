package com.orgapda.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper3 (
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int,


    ) : SQLiteOpenHelper(context, name, factory, version) {


    //val bcode: String?="bcode"
    //val pname: String?="pname"
    //val price: String?="price"


    override fun onCreate(db: SQLiteDatabase) {

        var sql : String = "CREATE TABLE if not exists DISPLAY3"+
                "(Dcode TEXT," +
                "SKU integer," +
                "Dname TEXT," +
                "Bcode TEXT," +
                "pname TEXT," +
                "qty TEXT);";

        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql : String = "DROP TABLE if exists "

        db.execSQL(sql)
        //onCreate(db)
    }


}