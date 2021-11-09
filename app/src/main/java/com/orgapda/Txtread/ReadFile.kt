package com.orgapda.Txtread

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class ReadFile {

    val path = "C:\\ORGAPDA\\PRODUCT1\\PRODUCT.txt"



   fun readTextFile2(path:String):String{
             val fullpath = File(path)
       Log.d("내용3",path.toString())
//          if(!fullpath.exists()) {
//       fullpath.createNewFile()
//             }
       // val reader = FileReader(fullpath)


        val reader = BufferedReader(InputStreamReader(FileInputStream(fullpath), "euc-kr"))

        Log.d("내용",reader.toString())
        val buffer = BufferedReader(reader)


        val result = StringBuilder() // StringBuffer()
        var temp:String? = ""
        while(true) {
            temp = buffer.readLine()

            if(temp == null) break
            result.append(temp).append("\n")
            var str1:String? =""
            var str2:String? =""

            str2 = temp.toString()
            str1 = str2.replace("┌","")
            str1 = str1.replace("┘","")
            var str3 = str1.split("┼")

            var contentValues = ContentValues()
            //for (str in str3){
            //    Log.d("내용10",str)

            //      contentValues.put(dbHelper.bcode,str)
            //    Log.d("내용11",contentValues.toString())
            //  }



            //  var c = database.query("PRODUCT1",null,null,null,null,null,null)

            //  Log.d("내용7",c.toString())



            //Log.d("내용6",result.toString())
        }
        buffer.close()
        reader.close()

        return result.toString()
    }


}


