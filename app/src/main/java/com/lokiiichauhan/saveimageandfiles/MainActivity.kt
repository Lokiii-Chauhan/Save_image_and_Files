package com.lokiiichauhan.saveimageandfiles

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import java.io.File
import java.io.FileNotFoundException
import java.io.OutputStream
import java.net.URI

class MainActivity : AppCompatActivity() {

    private lateinit var imgBG:ImageView
    private lateinit var btnSaveImage:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgBG = findViewById(R.id.imgBG)
        btnSaveImage = findViewById(R.id.btnSaveImage)

        btnSaveImage.setOnClickListener {

            var bitmapDrawable:BitmapDrawable = imgBG.drawable as BitmapDrawable
            var bitmap:Bitmap = bitmapDrawable.bitmap

            saveMyImageToStorage(bitmap)

        }

    }

    private fun saveMyImageToStorage(bitmap: Bitmap) {

        var outputString:OutputStream

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){

            var contentResolver:ContentResolver = contentResolver
            var contentValue = ContentValues()
            contentValue.put(MediaStore.MediaColumns.DISPLAY_NAME,"MyImage" + ".jpg")
            contentValue.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            contentValue.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "MyFolder")
            val uri: Uri? = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValue)

            try {
                outputString = uri?.let { contentResolver.openOutputStream(it) }!!
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputString)
                Toast.makeText(this,"Image Saved",Toast.LENGTH_LONG).show()

            }catch (e:FileNotFoundException){
                e.printStackTrace()
            }
        }

    }
}