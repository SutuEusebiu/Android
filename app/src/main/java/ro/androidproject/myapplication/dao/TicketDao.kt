package ro.androidproject.myapplication.dao

import androidx.room.*
import ro.androidproject.myapplication.entities.Tickets

@Dao

interface TicketDao{

    @Query("SELECT * from Tickets order by id DESC")
    fun allTickets(): List<Tickets>

    @Query("SELECT * from Tickets where id=:id")
    fun getTicket(id:Int): Tickets

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTicket(ticket:Tickets)

    @Query("Delete from Tickets where id=:id")
    fun deleteTicket(id:Int)


}