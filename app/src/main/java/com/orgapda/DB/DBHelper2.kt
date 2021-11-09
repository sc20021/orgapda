package com.orgapda.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper2 (
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int,


    ) : SQLiteOpenHelper(context, name, factory, version) {


    //val bcode: String?="bcode"
    //val pname: String?="pname"
    //val price: String?="price"


    override fun onCreate(db: SQLiteDatabase) {

        var sql : String = "CREATE TABLE if not exists BALJU3"+
                "(bcode TEXT primary key," +
                "pname TEXT," +
                "qty TEXT);";

        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql : String = "DROP TABLE if exists BALJU3"

        db.execSQL(sql)
        onCreate(db)
    }


}