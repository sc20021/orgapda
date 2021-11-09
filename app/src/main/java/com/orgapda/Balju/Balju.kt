package com.orgapda.Balju

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.orga_pda.R
import com.orgapda.DB.DBHelper
import com.orgapda.DB.DBHelper2
import com.orgapda.product.Product_view
import java.io.File

class Balju : Activity() {

    lateinit var database : SQLiteDatabase
    lateinit var dbHelper2 : DBHelper2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balju)




        val button : ImageButton = findViewById(R.id.imageButton9) as ImageButton

        val button2 : ImageButton = findViewById(R.id.imageButton10) as ImageButton



        button.setOnClickListener{
            val intent = Intent(this, Balju_view::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener {
            var builder = AlertDialog.Builder(this)

            builder.setTitle("발주 데이터 삭제").setMessage("발주 데이터를 삭제하시겠습니까?")
            builder.setPositiveButton("확인") { dialog, id ->

                val filePath = getExternalFilesDir(null)!!.path
                val filename = "ORDER.txt"
                val path = filePath + "/" + filename
                val fullpath = File(path)

                fullpath.delete()
                dbHelper2 = DBHelper2(this, "BALJU3.db", null, 1)
                database = dbHelper2.writableDatabase
                val sql = "DELETE FROM BALJU3"
                database.execSQL(sql)
                Toast.makeText(this,"발주 데이터 삭제 완료",Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton("취소"){dialog,id->

            }

            val alertDialog = builder.create()
            alertDialog.show()






        }

        val imagebutton11 : ImageButton = findViewById(R.id.imageButton11) as ImageButton
        imagebutton11.setOnClickListener {
            super.onBackPressed()
        }


    }


}