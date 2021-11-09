package com.orgapda.Balju

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent

import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.marginRight
import com.example.orga_pda.R
import com.orgapda.DB.DBHelper
import com.orgapda.DB.DBHelper2
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class Balju_view() : Activity() {

    lateinit var database : SQLiteDatabase
    lateinit var dbHelper : DBHelper

    lateinit var database2 : SQLiteDatabase
    lateinit var dbHelper2 : DBHelper2
    public var temp:String ="813523000583"
    public var count:Int = 0
    var tablecount:Int =0


   // var tableLayout = findViewById(R.id.myTable) as TableLayout
   // var editText2 = findViewById(R.id.editText3) as EditText
   // var textView10 = findViewById(R.id.textView10) as TextView
   // var textView11 = findViewById(R.id.textView11) as TextView
   // var editText = findViewById(R.id.editText) as EditText
    @SuppressLint("Range")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balju_view)

        dbHelper = DBHelper(this, "PRODUCT3.db", null, 1)
        database = dbHelper.writableDatabase

        dbHelper2 = DBHelper2(this,"BALJU3.db",null,1)
        database2 = dbHelper2.writableDatabase





        var editText = findViewById(R.id.editText) as EditText
        var tableLayout = findViewById(R.id.myTable) as TableLayout
        var textView4 = findViewById(R.id.textView4) as TextView
        var textView10 = findViewById(R.id.textView10) as TextView
        var textView11 = findViewById(R.id.textView11) as TextView
        var textView12 = findViewById(R.id.textView12) as TextView

        var editText3 = findViewById(R.id.editText3) as EditText




       var baljueditText = findViewById(R.id.baljuEditText) as EditText
       var baljueditfake1 = findViewById(R.id.baljueditfake1) as EditText



       var imageButton = findViewById(R.id.imageButton16) as ImageButton

       imageButton.setOnClickListener {
           super.onBackPressed()
       }
       // editText.bringToFront()
        editText3.bringToFront()
       editText.bringToFront()
       baljueditText.bringToFront()
        editText.requestFocus()




       baljueditText.setOnKeyListener { v, keyCode, event ->

           var handled = false
           textView10.setText(" ")
           if(keyCode == KeyEvent.KEYCODE_ENTER){
               if(baljueditText.text.length==0){
                   Toast.makeText(this,"바코드를 입력하세요.",Toast.LENGTH_SHORT).show()
                   baljueditText.requestFocus()
                   handled =true
               }
               else{

                   textView10.text = baljueditText.text
                   textView11.text = ""
                   temp = baljueditText.text.toString()
                   baljueditText.setText("")

                   val sql = "select * from product3 where bcode like '$temp'"

                   try{
                   val c:Cursor = database.rawQuery(sql,null)
                   if(textView11.text==""){
                       textView11.text ="등록되지 않은 상품입니다."
                   }

                       c.moveToNext()
                       Log.d("로글",c.getString(c.getColumnIndex("pname")))
                       textView11.text = " "
                       textView11.text = c.getString(c.getColumnIndex("pname"))
                       textView12.setText("")
                       cham(temp)
                       editText.requestFocus()
                   }
                   catch(e:Exception){
                       textView11.setText("등록되지 않은 상품입니다.")
                       textView12.setText("")
                       baljueditText.requestFocus()
                   }
               }


            handled=true
           }



           handled

       }

//       editText.setOnKeyListener { v, keyCode, event ->
//           var handled =false
//           if(keyCode==KeyEvent.KEYCODE_ENTER){
//               handled = true
//           }
//           handled
//       }

        editText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                textView11.text = " "
                textView10.text = s.toString()
                temp = s.toString()
                Log.d("로그1",temp)



                var str:String? = temp+"1"


                if(str!!.length >2){

                    var str2 = str!!.substring(0, str.length-1)
                    val sql = "select * from product3 where  bcode like $str2"
                    val c: Cursor = database.rawQuery(sql,null)

                    //  Log.d("로그5",c.getString(c.getColumnIndex("pname")))

                    if(textView11.text== " "){
                        textView11.text="등록되지 않은 상품입니다."


                    }
                    try{
                        c.moveToNext()
                        Log.d("로글",c.getString(c.getColumnIndex("pname")))
                        textView11.text = " "
                        textView11.text = c.getString(c.getColumnIndex("pname"))
                        textView12.setText("")
                        cham(temp)
                        editText3.requestFocus()
                    }
                    catch(e:Exception){
                        textView11.setText("등록되지 않은 상품입니다.")
                        textView12.setText("")
                        editText.requestFocus()
                    }
                }
               // editText3.requestFocus()

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

       editText3.setOnKeyListener { v, keyCode, event ->
           //editText3.requestFocus()
           var handled = false
           if(editText3.text.length>6){
               editText3.setText("")
           }
           if(keyCode == KeyEvent.KEYCODE_ENTER){


               if(textView10.text.length <5){
                   Toast.makeText(this,"바코드를 입력하세요",Toast.LENGTH_SHORT).show()
               }
               else if(editText3.text.length==0){
                   Toast.makeText(this,"신규 발주 수량을 입력하세요",Toast.LENGTH_SHORT).show()
               }

               else if(count ==0 && textView10.text.length>10){
                   var contentValues = ContentValues()
                   val list = listOf(textView10.text, textView11.text, editText3.text)
                   contentValues.put("bcode",list.get(0).toString())
                   contentValues.put("pname",list.get(1).toString())
                   contentValues.put("qty",list.get(2).toString())
                   database2.insert("BALJU3",null,contentValues)
                   textView12.text= editText3.text
                   Readbalju()
               }

               else{
                   var contentValues = ContentValues()
                   contentValues.put("qty",editText3.text.toString())
                   var arr : Array<String> = arrayOf(temp)
                   database2.update("BALJU3",contentValues,"bcode=?",arr)
                   textView12.text= editText3.text
               }
               count =0
               Readbalju()
               editText3.setText("")


               baljueditfake1.requestFocus()

               handled=true
           }



           handled

       }








       Readbalju()


    }

    @SuppressLint("Range")
    public fun Readbalju(){
        val filePath = getExternalFilesDir(null)!!.path
        val filename = "ORDER.txt"
        val path = filePath + "/" + filename

        var tableLayout = findViewById(R.id.myTable) as TableLayout
        tableLayout.removeAllViews()
        val sql = "select * from BALJU3"
        val c: Cursor = database2.rawQuery(sql,null)

        val fullpath = File(path)
        if(!fullpath.exists()) fullpath.createNewFile()



        val writer = FileWriter(fullpath)

        val buffer = BufferedWriter(writer)

        val tableRow = TableRow(this)
        val textView = TextView(this)
        val textView2= TextView(this)
        val textView3 = TextView(this)
        if(tablecount==0){

            tablecount=1
        }
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

      //  textView.setHeight(30)
        textView.setWidth(130)
        textView2.setWidth(240)
        textView3.setWidth(30)

        tableRow.addView(textView)
        tableRow.addView(textView2)
        tableRow.addView(textView3)
        tableRow.setBackgroundResource(R.drawable.border)

        tableLayout.addView(tableRow)
        while(c.moveToNext()){
            val list = listOf(c.getString(c.getColumnIndex("bcode")),c.getString(c.getColumnIndex("pname")),c.getString(c.getColumnIndex("qty")))
            val tableRow = TableRow(this)
            val textView = TextView(this)
            val textView2= TextView(this)
            val textView3 = TextView(this)

            textView.setText(list.get(0).toString())
            textView2.setText(list.get(1).toString())
            textView3.setText(list.get(2).toString())
            textView.setTextColor(Color.BLACK)
            textView2.setTextColor(Color.BLACK)
            textView3.setTextColor(Color.BLACK)

            textView3.setGravity(Gravity.CENTER)

            textView.setWidth(130)
            textView2.setWidth(240)
            textView3.setWidth(30)





            tableRow.addView(textView)
            tableRow.addView(textView2)
            tableRow.addView(textView3)
            tableRow.setBackgroundResource(R.drawable.border)

            tableLayout.addView(tableRow)
            Log.d("로그4",c.getString(c.getColumnIndex("pname")))
            buffer.write(list.get(0).toString())
            buffer.write("    ")
            buffer.write(list.get(2).toString())
            buffer.newLine()

        }
        buffer.close()
        writer.close()


    }
    @SuppressLint("Range")
    public fun cham(temp : String){
        var textView12 = findViewById(R.id.textView12) as TextView
       // var str:String? = temp+"1"
     //   var str2 = str!!.substring(0, str.length-1)
        val sql = "select * from BALJU3 where bcode like $temp"
        val c: Cursor = database2.rawQuery(sql,null)
        while(c.moveToNext()){
            textView12.text = c.getString(c.getColumnIndex("qty"))
            count =1
        }



    }

    @SuppressLint("Range")
    fun writeTextFile(path: String, content: List<String>) {
        // 앱 기본경로 / files / memo
        val fullpath = File(path)
        if(!fullpath.exists()) fullpath.mkdirs()

        fullpath.createNewFile()

        val writer = FileWriter(fullpath)

        val buffer = BufferedWriter(writer)
        buffer.newLine()
        for (i in 0..content.size-1){
            buffer.write(content[i])
            buffer.write(" ")
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

