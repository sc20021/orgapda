package com.orgapda.product

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import com.example.orga_pda.R
import android.widget.TextView

import android.widget.EditText
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.os.Environment
import android.text.Editable

import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isInvisible
import com.example.orga_pda.R
import com.orgapda.Txtread.FileRead

import com.orgapda.DB.DBHelper
import java.io.*
import android.content.ClipData
import android.content.ContentValues
import android.database.Cursor
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.ImageButton
import android.widget.Toast
import java.lang.Exception


class Product_view : Activity() {
    lateinit var database : SQLiteDatabase
    lateinit var dbHelper : DBHelper







   // private var editText: EditText? = null
  //  private var textView: TextView? = null
    public var temp:String ="813523000583"
    //var temp2:String=""
    public var count:Int =0




    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_view)

        dbHelper = DBHelper(this, "PRODUCT3.db", null, 1)
        database = dbHelper.writableDatabase

        var textView = findViewById(R.id.textView2) as TextView
        var editText = findViewById(R.id.EditText) as EditText
        var textView3 = findViewById(R.id.textView3) as TextView
        var textView6 = findViewById(R.id.textView6) as TextView

        var productedittext = findViewById(R.id.productedittext) as EditText
        var button = findViewById(R.id.imageButton) as ImageButton

        var productfake = findViewById(R.id.productfake) as EditText

        var imagebutton = findViewById(R.id.imageButton15) as ImageButton

        imagebutton.setOnClickListener {
            super.onBackPressed()
        }



        productedittext.bringToFront()
        editText.requestFocus()

        productedittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                textView.setText(" ")
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        productedittext.setOnKeyListener { v, keyCode, event ->
            //editText3.requestFocus()
            var handled = false

            if(keyCode == KeyEvent.KEYCODE_ENTER){
                //textView.text = productedittext.text
                textView3.text= " "

                temp = productedittext.text.toString()
                Log.d("바코드1",temp)
                val sql = "select * from product3 where bcode like '$temp'"
                try{
                    val c:Cursor = database.rawQuery(sql,null)

                    //  Log.d("로그5",c.getString(c.getColumnIndex("pname")))

                    if(textView3.text== " "){
                        textView3.text="등록되지 않은 상품입니다."
                        textView6.text="등록되지 않은 상품입니다."

                    }
                    while(c.moveToNext()){
                        textView3.text = " "
                        textView3.text = c.getString(c.getColumnIndex("pname"))
                        Log.d("로그4",c.getString(c.getColumnIndex("pname")))
                        textView6.text = " "
                        textView6.text = c.getString(c.getColumnIndex("price"))


                        productfake.requestFocus()
                        handled=true
                    }
                }
                catch(e:Exception){
                    Toast.makeText(this,"상품코드를 입력하세요.",Toast.LENGTH_SHORT).show()
                    productedittext.setText("")
                    productfake.requestFocus()
                    handled=true
                }




            }




            handled

        }


        button.setOnClickListener {
            var vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator



            var vibrationEffect = VibrationEffect.createOneShot(100,20)
            vibrator.vibrate(vibrationEffect)
            if(productedittext.text.length==0){
                Toast.makeText(this,"상품코드를 입력하세요.",Toast.LENGTH_SHORT).show()
            }
            else{
                textView3.text= " "
                textView.text = productedittext.text
                temp = productedittext.text.toString()
                val sql = "select * from product3 where bcode like '$temp'"
                val c:Cursor = database.rawQuery(sql,null)

                //  Log.d("로그5",c.getString(c.getColumnIndex("pname")))

                if(textView3.text== " "){
                    textView3.text="등록되지 않은 상품입니다."
                    textView6.text="등록되지 않은 상품입니다."

                }
                while(c.moveToNext()){
                    textView3.text = " "
                    textView3.text = c.getString(c.getColumnIndex("pname"))
                    Log.d("로그4",c.getString(c.getColumnIndex("pname")))
                    textView6.text = " "
                    textView6.text = c.getString(c.getColumnIndex("price"))
                }

                textView.setText(" ")
                editText.requestFocus()
            }

        }



        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                productedittext.setText("")
                textView3.text = " "
                textView.text = s.toString()
                temp = s.toString()
                Log.d("로그1",temp)



                var str:String? = temp+"1"



                if(str!!.length >2){

                    var str2 = str!!.substring(0, str.length-1)
                    val sql = "select * from product3 where bcode like '$str2'"
                    val c:Cursor = database.rawQuery(sql,null)

                  //  Log.d("로그5",c.getString(c.getColumnIndex("pname")))

                    if(textView3.text== " "){
                        textView3.text="등록되지 않은 상품입니다."
                       textView6.text="등록되지 않은 상품입니다."

                    }
                    while(c.moveToNext()){
                        textView3.text = " "
                        textView3.text = c.getString(c.getColumnIndex("pname"))
                        Log.d("로그4",c.getString(c.getColumnIndex("pname")))
                        textView6.text = " "
                        textView6.text = c.getString(c.getColumnIndex("price"))
                    }
                }
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if(s.toString().equals(temp)) return

                if(editText.isFocusable() && !s.toString().equals("")){

                    editText.setText("")
                }
            }
            override fun afterTextChanged(s: Editable) {

            }
        })




    }








}