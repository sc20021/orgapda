package com.orgapda

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.widget.Button


import android.content.Intent
import android.graphics.Point
import android.util.Log
import android.widget.ImageButton
import com.example.orga_pda.R


import com.orgapda.Balju.Balju
import com.orgapda.Display.Display
import com.orgapda.Stock.Stock
import com.orgapda.product.test


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this,LodingActivity::class.java)
        startActivity(intent)

        val display = windowManager.defaultDisplay // in case of Activity

        val size = Point()
        display.getRealSize(size) // or getSize(size)
        val width = size.x
        val height = size.y

        Log.d("가로",width.toString())
        Log.d("가로2",height.toString())

        val button : ImageButton = findViewById(R.id.imageButton1)

        button.setOnClickListener{
            val intent = Intent(this, test::class.java)

            startActivity(intent)


        }
        val button2 : ImageButton = findViewById(R.id.imageButton2)
        button2.setOnClickListener{
            val intent = Intent(this, Balju::class.java)

            startActivity(intent)


        }
        val button3 : ImageButton = findViewById(R.id.imageButton3)
        button3.setOnClickListener{
            val intent = Intent(this, Display::class.java)
            startActivity(intent)
        }

        val button4 : ImageButton = findViewById(R.id.imageButton4)
        button4.setOnClickListener {
//            val intent = Intent(this, Stock::class.java)
//            startActivity(intent)

            val intent = Intent(this, Stock::class.java)
            startActivity(intent)
        }

    }

}