package com.orgapda.Display

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.Color.parseColor
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.orga_pda.R
import com.orgapda.DB.DBHelper
import com.orgapda.DB.DBHelper3
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import kotlin.Exception


class Display_view : AppCompatActivity() {

    lateinit var database : SQLiteDatabase
    lateinit var dbHelper: DBHelper

    lateinit var database3 : SQLiteDatabase
    lateinit var dbHelper3: DBHelper3

    var temp :String ="813523000583"
    public var count:Int = 0
    var tempstr:String = "1001"
    var tempint :Int = 0
    var tempstr2 : String= " "




    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_view)

        Toast.makeText(this,"진열대 코드 4자리를 입력하세요",Toast.LENGTH_SHORT).show()

        dbHelper = DBHelper(this,"PRODUCT3.db",null,1)
        database = dbHelper.writableDatabase

        dbHelper3 = DBHelper3(this,"DISPLAY3.db",null,1)
        database3 = dbHelper3.writableDatabase

        var editTextT = findViewById(R.id.editTextT) as EditText

        var editTextT2 = findViewById(R.id.editTextT2) as EditText
        var editTextT4 = findViewById(R.id.editTextT4) as EditText

        var TextView16 = findViewById(R.id.textView16) as TextView

        var TextView20 = findViewById(R.id.textView20) as TextView
        var TextView22 = findViewById(R.id.textView22) as TextView
        var editTextName = findViewById(R.id.editTextName) as EditText
        var editTextName2 = findViewById(R.id.editTextName2) as EditText

        var displaysugi = findViewById(R.id.displaysugi) as EditText

        var imageButton = findViewById(R.id.imageButton19) as ImageButton

        imageButton.setOnClickListener {
            super.onBackPressed()
        }

        editTextT.requestFocus()
        editTextT.bringToFront()
        displaysugi.bringToFront()





        editTextT.setOnKeyListener { v, keyCode, event ->
            TextView16.setText("")
            var handled = false
            if(keyCode ==KeyEvent.KEYCODE_ENTER){
                if (editTextT.text.length==0){
                    Toast.makeText(this,"진열대 코드 4자리를 입력하세요",Toast.LENGTH_SHORT).show()
                    editTextT.requestFocus()

                }
                else{
                    var tableLayout = findViewById(R.id.myTable) as TableLayout
                    tableLayout.removeAllViews()
                    while(true) {
                        val str: String? = editTextT.text.toString()
                        val sql = "select * from display3 where dcode like '$str'"
                        tempstr = editTextT.text.toString()

                        //     tempint = tempstr.toInt()
                        try{
                            val c: Cursor = database3.rawQuery(sql, null)
                        }
                        catch(e:Exception){
                            Toast.makeText(this, "진열대 코드 4자리 입력", Toast.LENGTH_SHORT).show()
                            editTextT.setText("")

                            break
                        }
                        val c: Cursor = database3.rawQuery(sql, null)

                        if (editTextT.length() < 4) {
                            Toast.makeText(this, "진열대 코드 4자리 입력", Toast.LENGTH_SHORT).show()
                            TextView16.setText("")

                            editTextT.requestFocus()
                            break
                        }
                        c.moveToNext()
                        try {
                            Log.d("로그-2", c.getString(c.getColumnIndex("Dname")))

                            tempstr2 = c.getString(c.getColumnIndex("Dname"))
                            c.moveToFirst()
                            while (c.moveToNext()) {
                                TextView16.text = editTextT.text

                            }

                            Log.d("로긍",tempstr2)
                            TextView20.setText(tempstr2)

                            ReadDisplay(tempstr)
                            editTextName2.requestFocus()
                            break

                        } catch (e: RuntimeException) {
                            TextView16.text = editTextT.text
                            editTextT.setText(" ")
                            editTextName.requestFocus()
                            c.moveToFirst()
                            break
                        }

                    }

                }
            handled = true
            }

            handled

        }

        editTextT2.setOnKeyListener { v, keyCode, event ->
            TextView20.setText("")
            var handled = false
            if(keyCode ==KeyEvent.KEYCODE_ENTER){


                TextView20.text = editTextT2.text
                editTextT2.setText("")
                editTextName2.requestFocus()
                handled = true


            }
            handled

        }

        editTextT4.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {


                TextView22.text = s.toString()
                temp = s.toString()
                Log.d("로그1",temp)



                var str:String? = temp+"1"
                Log.d("로그2",str.toString())
                val str3 = str!!.substring(0, str.length-1)
                Log.d("로그3",str3.toString())





                if(str!!.length >2){






                    var str2 = str!!.substring(0, str.length-1)
                    val sql = "select * from product3 where bcode like $str2"
                    val c: Cursor = database.rawQuery(sql,null)


                    //  Log.d("로그5",c.getString(c.getColumnIndex("pname")))




                    if(TextView22.text== " "){
                        TextView22.text="등록되지 않은 상품입니다."


                    }
                    while(c.moveToNext()){

                        val contentValues = ContentValues()
                        val list = listOf(TextView16.text,TextView20.text,TextView22.text,c.getString(c.getColumnIndex("pname")))
                        var Dcodestr = TextView16.text.toString()

                        try{
                            val sql2 = "select * from display3 where Bcode like '$str2' and Dcode like '$Dcodestr'"
                            val c2: Cursor = database3.rawQuery(sql2,null)
                            c2.moveToNext()
                            if(c2.getString(c2.getColumnIndex("Bcode"))==""){
                                break
                            }
                            ReadDisplay(tempstr)
                            break
                        }
                        catch(e:Exception){
                            contentValues.put("dcode",list.get(0).toString())

                            Log.d("로그4",list.get(0).toString())
                            contentValues.put("dname",list.get(1).toString())
                            Log.d("로그5",list.get(1).toString())
                            contentValues.put("bcode",list.get(2).toString())
                            Log.d("로그6",list.get(2).toString())
                            contentValues.put("pname",list.get(3).toString())
                            Log.d("로그7",list.get(3).toString())
                            contentValues.put("qty","0")


                            database3.insert("display3",null,contentValues)

                            //val list =
                            Log.d("로그77",tempint.toString())

                            ReadDisplay(tempstr)
                        }
                    }

                }

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.toString().equals(temp)) return

                if(editTextT4.isFocusable() && !s.toString().equals("")){

                    editTextT4.setText("")
                }
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        displaysugi.setOnKeyListener { v, KeyCode, event ->
            TextView22.setText("")
            var handled = false

            if(KeyCode==KeyEvent.KEYCODE_ENTER){
                TextView22.text = displaysugi.text
                temp = displaysugi.text.toString()
                displaysugi.setText("")


                Log.d("로그1",temp)



                var str:String? = temp+"1"
                Log.d("로그2",str.toString())
                val str3 = str!!.substring(0, str.length-1)
                Log.d("로그3",str3.toString())





                if(str!!.length >2){






                    var str2 = str!!.substring(0, str.length-1)
                    val sql = "select * from product3 where bcode like '$str2'"
                    val c: Cursor = database.rawQuery(sql,null)


                    //  Log.d("로그5",c.getString(c.getColumnIndex("pname")))




                    if(TextView22.text== " "){
                        TextView22.text="등록되지 않은 상품입니다."


                    }
                    while(c.moveToNext()){

                        val contentValues = ContentValues()
                        val list = listOf(TextView16.text,TextView20.text,TextView22.text,c.getString(c.getColumnIndex("pname")))
                        var Dcodestr = TextView16.text.toString()

                        try{
                            val sql2 = "select * from display3 where Bcode like '$str2' and Dcode like '$Dcodestr'"
                            val c2: Cursor = database3.rawQuery(sql2,null)
                            c2.moveToNext()
                            if(c2.getString(c2.getColumnIndex("Bcode"))==""){
                                break
                            }
                            ReadDisplay(tempstr)
                            break
                        }
                        catch(e:Exception){
                            contentValues.put("dcode",list.get(0).toString())

                            Log.d("로그4",list.get(0).toString())
                            contentValues.put("dname",list.get(1).toString())
                            Log.d("로그5",list.get(1).toString())
                            contentValues.put("bcode",list.get(2).toString())
                            Log.d("로그6",list.get(2).toString())
                            contentValues.put("pname",list.get(3).toString())
                            Log.d("로그7",list.get(3).toString())
                            contentValues.put("qty","0")


                            database3.insert("display3",null,contentValues)

                            //val list =
                            Log.d("로그77",tempint.toString())

                            ReadDisplay(tempstr)
                        }
                    }

                }
                editTextName2.requestFocus()
            }
            handled

        }










    }


    @SuppressLint("Range")
    public fun ReadDisplay(str:String){

      //  var textView41 = findViewById(R.id.textView41) as TextView
        var tableLayout = findViewById(R.id.myTable) as TableLayout
        tableLayout.removeAllViews()
        var TextView18 = findViewById(R.id.textView18) as TextView
        val sql = "select * from display3 where dcode like '$str'"
        Log.d("로그10",str.toString())
        val c: Cursor = database3.rawQuery(sql,null)


        //fullpath.createNewFile()

        val tableRow = TableRow(this)
        val textView = TextView(this)
        val textView2 = TextView(this)
        textView.setText("상품코드")
        textView2.setText("상품명")

        textView.setTextColor(Color.WHITE)
        textView2.setTextColor(Color.WHITE)

        textView.setGravity(Gravity.CENTER)
        textView2.setGravity(Gravity.CENTER)
        textView.setBackgroundColor(Color.parseColor("#8B9A3A"))
        textView2.setBackgroundColor(Color.parseColor("#8B9A3A"))

        textView.setWidth(163)
        textView2.setWidth(317)

        tableRow.addView(textView)
        tableRow.addView(textView2)
        tableRow.setBackgroundResource(R.drawable.border)

        tableLayout.addView(tableRow)
        while(c.moveToNext()){
            count+=1
            val list = listOf(c.getString(c.getColumnIndex("Bcode")),c.getString(c.getColumnIndex("pname")))
            val tableRow = TableRow(this)
            val textView = TextView(this)
            val textView2= TextView(this)

            textView.setText(list.get(0).toString())
            textView2.setText(list.get(1).toString())
            textView.setTextColor(Color.BLACK)
            textView2.setTextColor(Color.BLACK)

            textView.setWidth(163)
            textView2.setWidth(317)


            tableRow.addView(textView)
            tableRow.addView(textView2)

            tableRow.setBackgroundResource(R.drawable.border)

            tableLayout.addView(tableRow)

         //   textView41.setText(textView41.getText().toString()+list.get(0).toString()+"\n")


        }
        TextView18.setText(count.toString())
        count=0
        writedisplay()





    }

    @SuppressLint("Range")
    fun writedisplay(){
        val filePath= getExternalFilesDir(null)!!.path
        val filename = "DISPLAY.txt"
        val path = filePath+"/"+filename

        val fullpath = File(path)
        if(!fullpath.exists())fullpath.createNewFile()


        val sql = "select * from display3"

        val c: Cursor = database3.rawQuery(sql,null)
        val writer = FileWriter(fullpath)

        val buffer = BufferedWriter(writer)

        while(c.moveToNext()){
            val list = listOf(c.getString(c.getColumnIndex("Bcode")),c.getString(c.getColumnIndex("pname")))




            buffer.write("      ")
            buffer.write(c.getString(c.getColumnIndex("Dcode")))
            buffer.write(list.get(0).toString())
            val strspl = list.get(0).toString()
            val str2 = 13 - strspl.length
            for (i in 0..str2){
                buffer.write(" ")
            }
            buffer.write("0")
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