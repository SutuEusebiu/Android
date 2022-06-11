package ro.androidproject.myapplication

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import kotlinx.android.synthetic.main.fragment_create_ticket.*
import kotlinx.coroutines.launch
import ro.androidproject.myapplication.database.TicketsDatabase
import ro.androidproject.myapplication.entities.Tickets

import java.text.SimpleDateFormat
import java.util.*
import android.os.Bundle as Bundle1


class CreateTicketFragment : BaseFragment()  {


    var currentDate:String?=null
    var selectedColor = "#ffffff"
    var Image:Bitmap?=null
    var tickets: Tickets?=null
    private val CAMERA_RQ=123
    private val IMAGE_CAPTURE_CODE:Int ?=1001
    private var noteID=0


    override fun onCreate(savedInstanceState: Bundle1?) {
        super.onCreate(savedInstanceState)
        noteID=requireArguments().getInt("noteId")
        arguments?.let {
        }
        setFragmentResultListener("requestKey"){requestKey, bundle ->
            selectedColor = bundle.getString("boundleKey").toString()
            ticketView.setBackgroundColor(Color.parseColor(selectedColor))

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle1?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_ticket, container, false)
    }

    companion object {
        var note_view:View?= null

        @JvmStatic
        fun newInstance() =
            CreateTicketFragment().apply {
                arguments = Bundle1().apply {
                }
            }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle1?) {
        super.onViewCreated(view, savedInstanceState)
        val sdf = SimpleDateFormat("dd/mm/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())
        datetime.text = currentDate

        if(noteID!=0){
            launch {
                context?.let {
                    tickets= TicketsDatabase.getDatabase(it).ticketDao().getTicket(noteID)
                    val  auxiliar=tickets
                    edit_ticket_name.setText(auxiliar?.name)
                    edit_ticket_comment.setText(auxiliar?.details)
                    first_number.setText(auxiliar?.firstNumber.toString())
                    second_number.setText(auxiliar?.secondNumber.toString())
                    third_number.setText(auxiliar?.thirdNumber.toString())
                    fourth_number.setText(auxiliar?.fourthNumber.toString())
                    fifth_number.setText(auxiliar?.fifthNumber.toString())
//                    edit_note_content.setText(auxiliar?.noteText)
//                    edit_note_subtitle.setText(auxiliar?.subTitle)
//                    edit_note_title.setText(auxiliar?.title)
//                    if(auxiliar?.image !=null){
//                        Image=auxiliar.image
//                        imgContent.setImageBitmap(Image)
//                        imgContent.visibility=View.VISIBLE
//                    }
                    //if (notes.color != null) {
                    //      System.out.println(notes.color)
                    //     view.cardView.setCardBackgroundColor(Color.parseColor(notes.color))
                    //  } else {
                    //       view.cardView.setCardBackgroundColor(Color.LTGRAY)
                    //  }
                    //  if (arrList[position].image != null) {
                    //     holder.itemView.imagenoteitem.setImageBitmap(arrList[position].image)
                    //       holder.itemView.imagenoteitem.visibility = View.VISIBLE
                    //   } else {
                    //         holder.itemView.imagenoteitem.visibility = View.GONE
                    //    }
                }

            }
        }
        imgDone.setOnClickListener {
            saveNote()
        }

        shareTicketBtn.setOnClickListener{
            val storage = "Biletul: "+ (tickets?.name) +" Cu numerele:"+ (tickets?.firstNumber) + " " + (tickets?.secondNumber)+ " " + (tickets?.thirdNumber)+ " " + (tickets?.fourthNumber)+ " " + (tickets?.fifthNumber)
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT,storage)
            shareIntent.putExtra(Intent.EXTRA_SUBJECT,"subject")
            startActivity(Intent.createChooser(shareIntent,"Share text via"))
        }

        imgBack.setOnClickListener {
            replacefragment(HomeFragment.newInstance(), false)
        }

        deleteNoteBtn.setOnClickListener{
            deleteNote()
            replacefragment(HomeFragment.newInstance(), false)
        }

        addImageBtn.setOnClickListener {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                if(context?.checkSelfPermission(android.Manifest.permission.CAMERA)==PackageManager.PERMISSION_DENIED){
                    val permission = arrayOf(android.Manifest.permission.CAMERA)
                    requestPermissions(permission,CAMERA_RQ)
                }else{
                    openCamera()
                }
            }else{
                openCamera()
            }
        }

    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent,IMAGE_CAPTURE_CODE!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode== Activity.RESULT_OK){
            Image= data?.extras?.get("data") as Bitmap
            imgContent.setImageBitmap(Image)
            imgContent.visibility=View.VISIBLE
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            CAMERA_RQ->{
                if(grantResults.size>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    openCamera()
                }else{
                    Toast.makeText(context,"Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun saveNote(){
        var err = 0
        if(edit_ticket_name.text.isNullOrEmpty()){
            Toast.makeText(context,"Numele este obligatoriu",Toast.LENGTH_SHORT).show()
            err=1
        }
        if(edit_ticket_comment.text.isNullOrEmpty()){
            Toast.makeText(context,"Observatiile sunt obligatorii",Toast.LENGTH_SHORT).show()
            err=1
        }
        if(first_number.text.isNullOrEmpty()){
            Toast.makeText(context,"Sunt obligatorii toate cele 5 numere",Toast.LENGTH_SHORT).show()
            err=1
        }
        if(second_number.text.isNullOrEmpty()){
            Toast.makeText(context,"Sunt obligatorii toate cele 5 numere",Toast.LENGTH_SHORT).show()
            err=1
        }
        if(third_number.text.isNullOrEmpty()){
            Toast.makeText(context,"Sunt obligatorii toate cele 5 numere",Toast.LENGTH_SHORT).show()
            err=1
        }
        if(fourth_number.text.isNullOrEmpty()){
            Toast.makeText(context,"Sunt obligatorii toate cele 5 numere",Toast.LENGTH_SHORT).show()
            err=1
        }
        if(fifth_number.text.isNullOrEmpty()){
            Toast.makeText(context,"Sunt obligatorii toate cele 5 numere",Toast.LENGTH_SHORT).show()
            err=1
        }
//        if(edit_note_content.text.isNullOrEmpty()){
//            Toast.makeText(context,"Content Required",Toast.LENGTH_SHORT).show()
//        }
        if(err==0){
            Thread {

                    var tickets= Tickets()
                    tickets.name=edit_ticket_name.text.toString()
                    tickets.details = edit_ticket_comment.text.toString()
                    tickets.dateTime=currentDate.toString()
                    tickets.firstNumber = first_number.text.toString().toInt()
                    tickets.secondNumber = second_number.text.toString().toInt()
                    tickets.thirdNumber = third_number.text.toString().toInt()
                    tickets.fourthNumber = fourth_number.text.toString().toInt()
                    tickets.fifthNumber = fifth_number.text.toString().toInt()
//            if(Image!=null){
//                tickets.image=Image
//            }

                    context?.let {
                        TicketsDatabase.getDatabase(it).ticketDao().insertTicket(tickets)
                        edit_ticket_comment.setText("")
                        edit_ticket_name.setText("")
                        first_number.setText("")
                        second_number.setText("")
                        third_number.setText("")
                        fourth_number.setText("")
                        fifth_number.setText("")
                        imgContent.visibility=View.GONE
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                }.start()
            }
        }

    private fun deleteNote(){
        launch{
            context?.let{
                TicketsDatabase.getDatabase(it).ticketDao().deleteTicket(noteID)

            }
        }
    }

    fun replacefragment(fragment: Fragment, istransition:Boolean){
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()
        if(istransition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransition.replace(R.id.frame_layout,fragment).addToBackStack(fragment.javaClass.simpleName)
        fragmentTransition.commit()
    }
}



