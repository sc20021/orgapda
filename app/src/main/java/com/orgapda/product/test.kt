package com.orgapda.product

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.orga_pda.R
import com.orgapda.DB.DBHelper
import com.orgapda.DB.DBHelper3
import com.orgapda.MainActivity
import com.orgapda.Txtread.ReadFile
import java.io.*
import java.nio.charset.Charset


class test : Activity() {

    lateinit var progress_Dialog: ProgressDialog

    lateinit var dbHelper : DBHelper
    lateinit var database : SQLiteDatabase
    @SuppressLint("Range")

    var count :Int? =0
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {

        val filePath = getExternalFilesDir(null)!!.path
        //val path = System.getProperty("java.io.tmpdir")
        val dir = Environment.getExternalStorageDirectory()


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val prog : ProgressBar= findViewById(R.id.progressBar2)

        dbHelper = DBHelper(this, "PRODUCT3.db", null, 1)
        database = dbHelper.writableDatabase

        val button : ImageButton = findViewById(R.id.imageButton5)
        val button8 : ImageButton = findViewById(R.id.imageButton6)
        val button9 : ImageButton = findViewById(R.id.imageButton7)
        val imagebutton9 : ImageButton = findViewById(R.id.imageButton8)




        prog.setVisibility(View.INVISIBLE)





        imagebutton9.setOnClickListener {

            super.onBackPressed()

        }

        button8.setOnClickListener{
            try{
                val sql = "select * from product3"
                val c:Cursor = database.rawQuery(sql,null)
                c.moveToNext()
                Log.d("aa",c.getString(c.getColumnIndex("pname")))
                val intent = Intent(this, Product_view::class.java)
                startActivity(intent)
            }
            catch(e:Exception){
                var builder = AlertDialog.Builder(this)

                builder.setTitle("?????? ??????").setMessage("?????? ???????????? ????????? ?????????")
                // loading()
                builder.setPositiveButton("??????") { dialog, id ->

                }


                val alertDialog = builder.create()
                alertDialog.show()
            }




        }
        button.setOnClickListener{

            prog.setVisibility(View.VISIBLE)


         //   onPreExecute()
            var builder = AlertDialog.Builder(this)
            builder.setCancelable(false)
            builder.setTitle("?????? ????????? ??????").setMessage("?????? ???????????? ?????????????????????????")
           // loading()
            builder.setPositiveButton("??????") { dialog, id ->
                //Toast.makeText(this,"?????? ???????????? ?????? ????????????.",Toast.LENGTH_SHORT).show()

                readTextFile()

                //loading()
               // loadingEnd()


            }

            builder.setNegativeButton("??????"){dialog,id->
                prog.setVisibility(View.INVISIBLE)
            }

            val alertDialog = builder.create()
            alertDialog.show()
           // onPostExecute("123")






        }
        button9.setOnClickListener{

            var builder = AlertDialog.Builder(this)

            builder.setTitle("?????? ????????? ??????").setMessage("?????? ???????????? ?????????????????????????")
            builder.setPositiveButton("??????") { dialog, id ->
                val filePath = getExternalFilesDir(null)!!.path
                val filename = "PRODUCT.txt"
                val path = filePath + "/" + filename
                val fullpath = File(path)

                fullpath.delete()
                dbHelper = DBHelper(this,"PRODUCT3.db",null,1)
                database = dbHelper.writableDatabase
                val sql = "DELETE FROM PRODUCT3"
                database.execSQL(sql)
                Toast.makeText(this,"?????? ????????? ?????? ??????",Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton("??????"){dialog,id->

            }

            val alertDialog = builder.create()
            alertDialog.show()




        }


    }
    @SuppressLint("Range")
    fun readTextFile() {


        val filePath = getExternalFilesDir(null)!!.path
        val filename = "PRODUCT.txt"
        val path = filePath + "/" + filename
        val fullpath = File(path)
        val prog : ProgressBar= findViewById(R.id.progressBar2)

        try{
            val reader = BufferedReader(InputStreamReader(FileInputStream(fullpath), "euc-kr"))
            val reader2 = BufferedReader(InputStreamReader(FileInputStream(fullpath), "euc-kr"))

            Log.d("??????",reader.toString())
            val buffer = BufferedReader(reader)
            val buffer2 = BufferedReader(reader2)
            while(true){
                var temstr = buffer2.readLine()
                count= count?.plus(1)
                if(temstr==null) break
            }
            val result = StringBuilder() // StringBuffer()
            var temp:String? = ""

            while(true) {

                var num =0
                temp = buffer.readLine()

                if(temp == null) break
                result.append(temp).append("\n")
                var str1:String? =""
                var str2:String? =""

                var str4 = CharArray(3)

                str2 = temp.toString()
                str1 = str2.replace("???","")
                str1 = str1.replace("???","")
                var str3 = str1.split("???")

                var contentValues = ContentValues()


                contentValues.put("bcode",str3[0])
                Log.d("?????????",str3[0])

                contentValues.put("pname",str3[1])
                contentValues.put("price",str3[2])
                Log.d("??????5",contentValues.toString())
                database.insert("PRODUCT3",null,contentValues)
                num+=1
            }
            buffer.close()
            reader.close()
            Toast.makeText(this,"???????????? ?????? ??????",Toast.LENGTH_SHORT).show()
        }
        catch(e: Exception){
            Toast.makeText(this,"?????? ????????? ????????? ????????? ?????????.",Toast.LENGTH_SHORT).show()
        }

        prog.setVisibility(View.INVISIBLE)

    }
    @SuppressLint("Range")
    fun writeTextFile(path: String, content: String) {
        // ??? ???????????? / files / memo
        val fullpath = File(path)
        if(!fullpath.exists()) fullpath.mkdirs()

        fullpath.createNewFile()

        val writer = FileWriter(fullpath)

        val buffer = BufferedWriter(writer)
        buffer.write(content)
        buffer.close()
        writer.close()
    }



    fun getProgressShow(){
        try{
            var str_tittle = "Please Wait ..."
            var str_message = "????????? ?????????????????? ...\n?????? ???????????? ..."
            progress_Dialog = ProgressDialog(this)
            progress_Dialog.setTitle(str_tittle) //????????? ????????? ??????

            progress_Dialog.setMessage(str_message) //????????? ?????? ??????
            progress_Dialog.setCancelable(false) //?????? ???????????? ???????????? ???????????? ?????????????????? ??????
            progress_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER) //??????????????? ?????? ?????? ??????

            try {
                progress_Dialog.show()
            }
            catch (e : Exception){
                e.printStackTrace()
            }
        }
        catch(e : Exception){
            e.printStackTrace()
        }
    }

    fun getProgressHidden(){
        try {
            progress_Dialog.dismiss()
            progress_Dialog.cancel()
        }
        catch (e : Exception){
            e.printStackTrace()
        }
    }

    fun loading() {
        //??????
        Handler().postDelayed(
            {
                progress_Dialog = ProgressDialog(this)
                progress_Dialog.setIndeterminate(true)
                progress_Dialog.setMessage("????????? ????????? ?????????")
                progress_Dialog.show()

            }, 0

        )

    }

    fun loadingEnd() {
        Handler().postDelayed(
            { progress_Dialog.dismiss() }, 0
        )
    }


}