package com.orgapda.Display

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
import com.orgapda.Balju.Balju_view
import com.orgapda.DB.DBHelper3
import java.io.File

class Display : Activity() {

    lateinit var database : SQLiteDatabase
    lateinit var dbHelper3: DBHelper3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        val imageButton = findViewById(R.id.imageButton12) as ImageButton

        imageButton.setOnClickListener {
            super.onBackPressed()
        }

        val button : ImageButton = findViewById(R.id.imageButton13)
        button.setOnClickListener{
            val intent = Intent(this, Display_view::class.java)
            startActivity(intent)


        }
        val button2 : ImageButton = findViewById(R.id.imageButton14)

        button2.setOnClickListener {
            var builder = AlertDialog.Builder(this)

            builder.setTitle("진열대 데이터 삭제").setMessage("진열대 데이터를 삭제하시겠습니까?")
            builder.setPositiveButton("확인") { dialog, id ->


                val filePath = getExternalFilesDir(null)!!.path
                val filename = "DISPLAY.txt"
                val path = filePath + "/" + filename
                val fullpath = File(path)

                fullpath.delete()

                dbHelper3 = DBHelper3(this,"DISPLAY3.db",null,1)
                database = dbHelper3.writableDatabase
                val sql = "DELETE FROM DISPLAY3"
                database.execSQL(sql)
                Toast.makeText(this,"진열대 데이터 삭제 완료",Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton("취소"){dialog,id->

            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }
}