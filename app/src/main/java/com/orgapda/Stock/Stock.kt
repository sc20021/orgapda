package com.orgapda.Stock

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import com.example.orga_pda.R
import com.orgapda.DB.DBHelper3
import java.io.File

class Stock : Activity() {

    lateinit var database3 : SQLiteDatabase
    lateinit var dbHelper3: DBHelper3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)

        val button : ImageButton = findViewById(R.id.imageButton18)


        val button2 : ImageButton = findViewById(R.id.imageButton20)

        val imageButton = findViewById(R.id.imageButton17) as ImageButton

        imageButton.setOnClickListener {
            super.onBackPressed()
        }

        button.setOnClickListener{
            val intent = Intent(this, Stock_view::class.java)
            startActivity(intent)
        }
        button2.setOnClickListener {

            var builder = AlertDialog.Builder(this)

            builder.setTitle("재고 수량 삭제").setMessage("재고 수량을 삭제하시겠습니까?")
            builder.setPositiveButton("확인") { dialog, id ->
                val filePath = getExternalFilesDir(null)!!.path
                val filename = "INVENTORY.txt"
                val path = filePath + "/" + filename
                val fullpath = File(path)

                fullpath.delete()

                dbHelper3 = DBHelper3(this,"DISPLAY3.db",null,1)
                database3 = dbHelper3.writableDatabase
                var qtystr:String? = "0"

                var sqlupdate = "UPDATE display3 set qty = $qtystr"
                database3.execSQL(sqlupdate)
                Toast.makeText(this,"재고 수량 삭제 완료",Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton("취소"){dialog,id->

            }

            val alertDialog = builder.create()
            alertDialog.show()

        }
    }
}