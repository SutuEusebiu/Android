package ro.androidproject.myapplication.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Convertor{

    @TypeConverter
    fun toBitmap(bytes: ByteArray): Bitmap?{
        return BitmapFactory.decodeByteArray(bytes,0,bytes.size)
    }

    @TypeConverter
    fun fromBitmap(bmp:Bitmap?):ByteArray{
        val outputSteam=ByteArrayOutputStream()
        bmp?.compress(Bitmap.CompressFormat.PNG,100,outputSteam)
        return outputSteam.toByteArray()
    }
}