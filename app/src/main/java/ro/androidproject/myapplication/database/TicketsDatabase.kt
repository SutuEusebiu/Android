package ro.androidproject.myapplication.database

import android.content.Context
import androidx.room.*
import ro.androidproject.myapplication.dao.TicketDao
import ro.androidproject.myapplication.entities.Tickets
import ro.androidproject.myapplication.util.Convertor


@Database(entities = [Tickets::class],version = 1,exportSchema = false)
@TypeConverters(Convertor::class)
abstract class TicketsDatabase: RoomDatabase() {

    companion object{
        var ticketsDatabase:TicketsDatabase?=null
        //tot din acest bloc va fi protejat in accesul concurential
        @Synchronized

        fun getDatabase(context: Context):TicketsDatabase{
            if(ticketsDatabase==null){
                ticketsDatabase= Room.databaseBuilder(
                    context,
                    TicketsDatabase::class.java,
                    "tickets.db"
                ).allowMainThreadQueries().build()
            }
            //cand pun !! fac un assert si verific daca nu e null.
            return ticketsDatabase!!
        }
    }

    abstract fun ticketDao(): TicketDao
}