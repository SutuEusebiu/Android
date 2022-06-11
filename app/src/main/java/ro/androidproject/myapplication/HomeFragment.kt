package ro.androidproject.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import ro.androidproject.myapplication.adapter.TicketsAdapter

import ro.androidproject.myapplication.database.TicketsDatabase
import ro.androidproject.myapplication.entities.Tickets
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : BaseFragment(){

    val ticketsAdapter:TicketsAdapter?= TicketsAdapter()
    var arrTickets = ArrayList<Tickets>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        Thread {
            context?.let {
                var tickets=TicketsDatabase.getDatabase(it).ticketDao().allTickets()
                ticketsAdapter!!.setData(tickets)
                arrTickets = tickets as ArrayList<Tickets>
                recycler_view?.let{
                    recycler_view.adapter=ticketsAdapter
                }
            }
        }.start()

        ticketsAdapter!!.setOnClickListener(onClicked)

        btnCreateTicket.setOnClickListener {
            replacefragment(CreateTicketFragment.newInstance(),false)
        }

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val tempArr = ArrayList<Tickets>()
                for ( arr in arrTickets){
                    if ( arr.name?.lowercase(locale = Locale.getDefault())!!.contains(newText.toString())){
                        tempArr.add(arr)
                    }
                }
                ticketsAdapter.setData(tempArr)
                ticketsAdapter.notifyDataSetChanged()
                return true
            }

        })

    }

    fun replacefragment(fragment: Fragment, istransition:Boolean){
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()
        if(istransition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransition.replace(R.id.frame_layout,fragment).addToBackStack(fragment.javaClass.simpleName)
        fragmentTransition.commit()
    }


    private val onClicked = object :TicketsAdapter.OnTicketItemClickListener {
        override fun onTicketClick(position: Int) {
            var fragment: Fragment
            var bundle = Bundle()
            bundle.putInt("noteId", position)
            fragment = CreateTicketFragment.newInstance()
            fragment.arguments = bundle
            replacefragment(fragment, false)
        }
    }

}