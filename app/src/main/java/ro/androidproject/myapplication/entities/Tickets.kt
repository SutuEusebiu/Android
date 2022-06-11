package ro.androidproject.myapplication.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Tickets")
data class Tickets(
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,

    @ColumnInfo(name="name")
    var name:String?=null,

    @ColumnInfo(name="details")
    var details:String?=null,

    @ColumnInfo(name="date_time")
    var dateTime:String?=null,

    @ColumnInfo(name="first_number")
    var firstNumber:Int?=0,

    @ColumnInfo(name="second_number")
    var secondNumber:Int?=0,

    @ColumnInfo(name="third_number")
    var thirdNumber:Int?=0,

    @ColumnInfo(name="fourth_number")
    var fourthNumber:Int?=0,

    @ColumnInfo(name="fifth_number")
    var fifthNumber:Int?=0

) : Serializable
{
    override fun toString(): String {
        return "$name : $dateTime"
    }
}