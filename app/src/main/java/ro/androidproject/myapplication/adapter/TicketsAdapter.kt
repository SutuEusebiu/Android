package ro.androidproject.myapplication.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_rv_tickets.view.*
import ro.androidproject.myapplication.R
import ro.androidproject.myapplication.entities.Tickets

class TicketsAdapter () : RecyclerView.Adapter<TicketsAdapter.TicketsAdapterViewHolder>() {

    private var arrList= ArrayList<Tickets>()
    var clickListener:OnTicketItemClickListener?=null

    class TicketsAdapterViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
    }

    fun setOnClickListener(listener : OnTicketItemClickListener){
        clickListener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsAdapterViewHolder {
       return TicketsAdapterViewHolder(
           LayoutInflater.from(parent.context).inflate(R.layout.item_rv_tickets,parent,false)
       )
    }

    override fun getItemCount(): Int {
        return arrList.count()
    }

    fun setData( arrTicketsList: List<Tickets>){
        arrList=arrTicketsList as ArrayList<Tickets>
    }

    override fun onBindViewHolder(holder: TicketsAdapterViewHolder, position: Int) {
        holder.itemView.tvdatetime.text = arrList[position].dateTime
        holder.itemView.tvtitle.text = arrList[position].name
        holder.itemView.tvcontent.text = arrList[position].details

//            holder.itemView.cardView.setCardBackgroundColor(Color.)



            holder.itemView.imagenoteitem.visibility = View.GONE

        holder.itemView.cardView.setOnClickListener{
            clickListener!!.onTicketClick(arrList[position].id!!)
        }
    }

    interface OnTicketItemClickListener{
        fun onTicketClick(position:Int)
    }
}

