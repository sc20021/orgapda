package com.orgapda.Stock

import android.annotation.SuppressLint
import android.app.Activity
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.*
import com.example.orga_pda.R
import com.orgapda.DB.DBHelper
import com.orgapda.DB.DBHelper3
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class Stock_view : Activity() {

    lateinit var database : SQLiteDatabase
    lateinit var dbHelper: DBHelper

    lateinit var database3 : SQLiteDatabase
    lateinit var dbHelper3: DBHelper3

    var temp :String ="813523000583"
    public var count:Int = 0
    var qty:Int =0
    var tempstr:String = "1001"
    var tempint :Int = 0
    var tempstr2 :String= ""
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_view)

        Toast.makeText(this,"진열대 코드 4자리를 입력하세요",Toast.LENGTH_SHORT).show()
        dbHelper = DBHelper(this,"PRODUCT3.db",null,1)
        database = dbHelper.writableDatabase

        dbHelper3 = DBHelper3(this,"DISPLAY3.db",null,1)
        database3 = dbHelper3.writableDatabase


        Toast.makeText(this,"진열대 코드 4자리를 입력하세요",Toast.LENGTH_SHORT).show()
        //val textView = findViewById(R.id.textVie)
        val storkfake3 = findViewById(R.id.storkfake3) as EditText
        val editText = findViewById(R.id.editText1) as EditText
        val storkfake1 = findViewById(R.id.storkfake1) as EditText
        val storeditText = findViewById(R.id.storkeditText) as EditText
        val storkfake2 = findViewById(R.id.storkfake2) as EditText
        val editText2 = findViewById(R.id.editTextT2) as EditText
        val editText3 = findViewById(R.id.editTextT3) as EditText
        val storksugi = findViewById(R.id.storksugi) as EditText




        val textView24 = findViewById(R.id.textView24) as TextView
        val textView28 = findViewById(R.id.textView28) as TextView
        val textView32 = findViewById(R.id.textView32) as TextView
        val textView33 = findViewById(R.id.textView33) as TextView
        val textView34 = findViewById(R.id.textView34) as TextView
        val textView36 = findViewById(R.id.textView36) as TextView


        val imageButton = findViewById(R.id.imageButton21) as ImageButton

        imageButton.setOnClickListener {
            super.onBackPressed()
        }

        editText.requestFocus()
        storksugi.bringToFront()


        editText.setOnKeyListener { v, keyCode, event ->
            textView24.setText("")
            var handled = false
            if(keyCode== KeyEvent.KEYCODE_ENTER)
            {
                if(editText.text.length==0){
                    Toast.makeText(this,"진열대 코드 4자리 입력", Toast.LENGTH_SHORT).show()
                }
                else{
                    var tableLayout = findViewById(R.id.myStockTable) as TableLayout
                    tableLayout.removeAllViews()
                    while(true){
                        val str: String? = editText.text.toString()
                        val sql = "select * from DISPLAY3 where dcode like $str"
                        tempstr = str.toString()

                        try{
                            val c:Cursor = database3.rawQuery(sql,null)
                        }
                        catch(e:Exception){
                            Toast.makeText(this,"진열대 코드 4자리 입력", Toast.LENGTH_SHORT).show()
                            editText.setText("")
                            break
                        }



                        val c:Cursor = database3.rawQuery(sql,null)


                        if(tempstr.length <4){
                            Toast.makeText(this,"진열대 코드 4자리 입력", Toast.LENGTH_SHORT).show()
                            textView24.setText("")
                            editText.requestFocus()
                            break
                        }
                        c.moveToNext()
                        try{
                            Log.d("로그-2", c.getString(c.getColumnIndex("Dcode")))
                            c.moveToFirst()
                            textView24.text = editText.text
                            textView28.setText(c.getString(c.getColumnIndex("Dname")))
                            ReadInventory(tempstr)
                            editText.setText("")
                            storkfake2.requestFocus()
                            break

                        }
                        catch(e:Exception){
                            Toast.makeText(this,"등록된 진열대가 없습니다.",Toast.LENGTH_SHORT).show()

                            editText.requestFocus()
                            break
                        }
                    }
                }

                handled=true
            }
            handled
        }

        editText2.setOnKeyListener { v, keyCode, event ->
            var handled =false
            if(keyCode== KeyEvent.KEYCODE_ENTER) {
                handled = true
            }
            handled

        }

        editText2.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                textView33.setText(" ")
                textView32.text = s.toString()
                temp = s.toString()
                var Dstr = textView24.text.toString()
                var str : String? = temp+"1"

                val str3 = str!!.substring(0,str.length-1)


                    if(str!!.length >2){
                        while(true){
                            val sql = "select * from display3 where bcode like '$str3' and dcode like '$Dstr'"
                            val c: Cursor =database3.rawQuery(sql,null)
                            try{
                                c.moveToNext()
                                var tempstr2 = c.getString(c.getColumnIndex("Bcode"))
                            }
                            catch(e:Exception){
                                textView33.setText("등록되지 않은 상품입니다.")
                                editText2.requestFocus()
                                break
                            }
                            c.moveToFirst()




                            textView33.setText("")
                            textView33.text = c.getString(c.getColumnIndex("pname"))
                                while(c.moveToNext()){

                                }
                                cham(temp, Dstr)
                                editText3.requestFocus()
                                break



                        }



                    }
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.toString().equals(temp)) return

                if(editText2.isFocusable() && !s.toString().equals("")){

                    editText2.setText("")
                }
            }
            override fun afterTextChanged(s: Editable) {
            }
        })



        editText3.setOnKeyListener { v, keyCode, event ->
            var handled = false
            if(editText3.text.length>6){
                editText3.setText("")

            }
            if(keyCode ==KeyEvent.KEYCODE_ENTER){

                if(editText3.text.length==0){
                    Toast.makeText(this,"수량을 입력하세요.",Toast.LENGTH_SHORT).show()
                    editText3.requestFocus()

                }

                else{
                    var qtystr = editText3.text.toString()
                    var Bcodestr = textView32.text.toString()
                    var Dcodestr = textView24.text.toString()
                    var sqlupdate = "UPDATE display3 set qty = $qtystr where Bcode = '$Bcodestr' and Dcode = '$Dcodestr'"
                    database3.execSQL(sqlupdate)

                    textView34.setText(qtystr)
                    ReadInventory(textView24.text.toString())
                    editText3.setText("")
                    storkfake2.requestFocus()


                }
                handled=true

            }
            handled
        }

        storksugi.setOnKeyListener { v, keyCode, event ->
            textView32.setText("")
            var handled = false
            if(keyCode==KeyEvent.KEYCODE_ENTER){

                textView33.setText("")
                textView32.text = storksugi.text

                temp = storksugi.text.toString()
                storksugi.setText("")
                var Dstr = textView24.text.toString()
                var str : String? = temp+"1"

                val str3 = str!!.substring(0,str.length-1)


                if(str!!.length >1){
                    while(true){
                        val sql = "select * from display3 where bcode like '$str3' and dcode like '$Dstr'"
                        val c: Cursor =database3.rawQuery(sql,null)

                        try{
                            c.moveToNext()
                            var tempstr2 = c.getString(c.getColumnIndex("Bcode"))
                        }
                        catch(e:Exception){
                            textView33.setText("등록되지 않은 상품입니다.")
                            storksugi.requestFocus()
                            break
                        }
                        c.moveToFirst()
                        textView33.setText("")
                        textView33.text = c.getString(c.getColumnIndex("pname"))
                        cham(temp, Dstr)
                        editText3.requestFocus()
                        break
                    }




                }
                handled=true
            }
            handled
        }

    }

    @SuppressLint("Range")
    public fun cham(temp : String,temp2 :String){
        var textView34 = findViewById(R.id.textView34) as TextView
        // var str:String? = temp+"1"
        //   var str2 = str!!.substring(0, str.length-1)
        val sql = "select * from display3 where Bcode like '$temp' and Dcode like '$temp2'"
        val c: Cursor = database3.rawQuery(sql,null)
        while(c.moveToNext()){
            textView34.text = c.getString(c.getColumnIndex("qty"))
            //count =1
        }



    }

    @SuppressLint("Range")
    public fun ReadInventory(str:String){

        //  var textView41 = findViewById(R.id.textView41) as TextView
        var tableLayout = findViewById(R.id.myStockTable) as TableLayout
        tableLayout.removeAllViews()
        var TextView26 = findViewById(R.id.textView26) as TextView
        val sql = "select * from display3 where dcode like '$str'"
        Log.d("로그10",str.toString())
        val c: Cursor = database3.rawQuery(sql,null)

        val tableRow = TableRow(this)
        val textView = TextView(this)
        val textView2 = TextView(this)
        val textView3 = TextView(this)

        textView.setText("상품코드")
        textView2.setText("상품명")
        textView3.setText("수량")

        textView.setTextColor(Color.WHITE)
        textView2.setTextColor(Color.WHITE)
        textView3.setTextColor(Color.WHITE)

        textView.setGravity(Gravity.CENTER)
        textView2.setGravity(Gravity.CENTER)
        textView3.setGravity(Gravity.CENTER)
        textView.setBackgroundColor(Color.parseColor("#8B9A3A"))
        textView2.setBackgroundColor(Color.parseColor("#8B9A3A"))
        textView3.setBackgroundColor(Color.parseColor("#8B9A3A"))

        textView.setWidth(167)
        textView2.setWidth(273)
        textView3.setWidth(40)

        tableRow.addView(textView)
        tableRow.addView(textView2)
        tableRow.addView(textView3)
        tableRow.setBackgroundResource(R.drawable.border)

        tableLayout.addView(tableRow)

        while(c.moveToNext()){
            qty+=1
            val list = listOf(c.getString(c.getColumnIndex("Bcode")),c.getString(c.getColumnIndex("pname")),c.getString(c.getColumnIndex("qty")))
            val tableRow = TableRow(this)
            val textView = TextView(this)
            val textView2= TextView(this)
            val textView3 = TextView(this)

            textView.setText(list.get(0).toString())
            textView2.setText(list.get(1).toString())
            textView3.setText(list.get(2).toString())

            textView3.setGravity(Gravity.CENTER)

            textView.setTextColor(Color.BLACK)
            textView2.setTextColor(Color.BLACK)
            textView3.setTextColor(Color.BLACK)


            textView3.setGravity(Gravity.CENTER)
            textView.setWidth(167)
            textView2.setWidth(273)
            textView3.setWidth(40)


            tableRow.addView(textView)
            tableRow.addView(textView2)
            tableRow.addView(textView3)

            tableRow.setBackgroundResource(R.drawable.border)

            tableLayout.addView(tableRow)




        }
        TextView26.setText(qty.toString())
        qty=0
        writeInventory()

    }
    @SuppressLint("Range")
    fun writeInventory(){
        val filePath= getExternalFilesDir(null)!!.path
        val filename = "INVENTORY.txt"
        val path = filePath+"/"+filename

        val fullpath = File(path)
        if(!fullpath.exists())fullpath.createNewFile()


        val sql = "select * from display3"

        val c: Cursor = database3.rawQuery(sql,null)
        val writer = FileWriter(fullpath)

        val buffer = BufferedWriter(writer)
        while(c.moveToNext()){
            val list = listOf(c.getString(c.getColumnIndex("Bcode")),c.getString(c.getColumnIndex("pname")), c.getString(c.getColumnIndex("qty")))


            buffer.write("      ")
            buffer.write(c.getString(c.getColumnIndex("Dcode")))
            buffer.write(list.get(0).toString())
            val strspl = list.get(0).toString()
            val str2 = 13 - strspl.length
            for (i in 0..str2){
                buffer.write(" ")
            }
            //buffer.write("    ")
            buffer.write(list.get(2).toString())
            buffer.newLine()


        }
        buffer.close()
        writer.close()
    }
    fun View.setHeight(value: Int){
        val lp = layoutParams
        lp?.let{
            lp.height = value
            layoutParams=lp
        }
    }
    fun View.setWidth(value:Int){
        val lp = layoutParams
        lp?.let{
            lp.width = value
            layoutParams = lp
        }
    }
}