package com.example.centro_oculistico

import RestApiService
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.centro_oculistico.adapter.TimeSlot
import com.example.centro_oculistico.adapter.UserToUpdate
import com.example.centro_oculistico.databinding.FragmentDoctorTimeSlotBinding
import org.json.JSONObject


class DoctorTimeSlot : Fragment() {


    private var _binding: FragmentDoctorTimeSlotBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var  editor : SharedPreferences.Editor
    private lateinit var  pref : SharedPreferences
    private var timeSlot1 : TextView?=null
    private var timeSlot2 : TextView?=null
    private var timeSlot3 : TextView?=null
    private var timeSlot4 : TextView?=null
    private var timeSlot5 : TextView?=null
    private var timeSlot6 : TextView?=null
    private var timeSlot7 : TextView?=null
    private var timeSlot8 : TextView?=null
    private var timeSlot9 : TextView?=null
    private var timeSlot10 : TextView?=null
    private var timeSlot11 : TextView?=null
    private var timeSlot12 : TextView?=null
    private var timeSlot13 : TextView?=null
    private var timeSlot14 : TextView?=null
    private var m_Text = ""
    private  var timeSlotsArray = Array<String>(15){""}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         pref = requireActivity().getSharedPreferences("userDataPreferences", Context.MODE_PRIVATE)
        editor=pref.edit()
        _binding = FragmentDoctorTimeSlotBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true);
        timeSlot1=binding.timeslot1
        timeSlot2=binding.timeslot2
        timeSlot3=binding.timeslot3
        timeSlot4=binding.timeslot4
        timeSlot5=binding.timeslot5
        timeSlot6=binding.timeslot6
        timeSlot7=binding.timeslot7
        timeSlot8=binding.timeslot8
        timeSlot9=binding.timeslot9
        timeSlot10=binding.timeslot10
        timeSlot11=binding.timeslot11
        timeSlot12=binding.timeslot12
        timeSlot13=binding.timeslot13
        timeSlot14=binding.timeslot14


        //http call to get the resources
        val queue = Volley.newRequestQueue(activity)
        val url = "http://centrooculistico.hostinggratis.it/centro_oculisticoREST/timeSlot/read.php"
        // Request a json response from the provided URL.

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                for (i in 0 until response.length()) {
                    val item = response.getJSONObject(i) as JSONObject
                    if (item.get("doctor_id").toString()==pref.getString("doctorId","default")){
                        updateRow(1,item.get("slot1").toString())
                        updateRow(2,item.get("slot2").toString())
                        updateRow(3,item.get("slot3").toString())
                        updateRow(4,item.get("slot4").toString())
                        updateRow(5,item.get("slot5").toString())
                        updateRow(6,item.get("slot6").toString())
                        updateRow(7,item.get("slot7").toString())
                        updateRow(8,item.get("slot8").toString())
                        updateRow(9,item.get("slot9").toString())
                        updateRow(10,item.get("slot10").toString())
                        updateRow(11,item.get("slot11").toString())
                        updateRow(12,item.get("slot12").toString())
                        updateRow(13,item.get("slot13").toString())
                        updateRow(14,item.get("slot14").toString())
                        break
                    }
                }
            },
            { error ->
                // TODO: Handle error
            }
        )

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest)

        timeSlot1!!.setOnClickListener {
            openDialog(1,timeSlot1!!.text.toString(),timeSlotsArray)
        }
        timeSlot2!!.setOnClickListener {
            openDialog(2,timeSlot2!!.text.toString(),timeSlotsArray)
        }
        timeSlot3!!.setOnClickListener {
            openDialog(3,timeSlot3!!.text.toString(),timeSlotsArray)
        }
        timeSlot4!!.setOnClickListener {
            openDialog(4,timeSlot4!!.text.toString(),timeSlotsArray)
        }
        timeSlot5!!.setOnClickListener {
            openDialog(5,timeSlot5!!.text.toString(),timeSlotsArray)
        }
        timeSlot6!!.setOnClickListener {
            openDialog(6,timeSlot6!!.text.toString(),timeSlotsArray)
        }
        timeSlot7!!.setOnClickListener {
            openDialog(7,timeSlot7!!.text.toString(),timeSlotsArray)
        }
        timeSlot8!!.setOnClickListener {
            openDialog(8,timeSlot8!!.text.toString(),timeSlotsArray)
        }
        timeSlot9!!.setOnClickListener {
            openDialog(9,timeSlot9!!.text.toString(),timeSlotsArray)
        }
        timeSlot10!!.setOnClickListener {
            openDialog(10,timeSlot10!!.text.toString(),timeSlotsArray)
        }
        timeSlot11!!.setOnClickListener {
            openDialog(11,timeSlot11!!.text.toString(),timeSlotsArray)
        }
        timeSlot12!!.setOnClickListener {
            openDialog(12,timeSlot12!!.text.toString(),timeSlotsArray)
        }
        timeSlot13!!.setOnClickListener {
            openDialog(13,timeSlot13!!.text.toString(),timeSlotsArray)
        }
        timeSlot14!!.setOnClickListener {
            openDialog(14,timeSlot14!!.text.toString(),timeSlotsArray)
        }



        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()

        inflater.inflate(R.menu.fragment_doctor_menu, menu)

        super.onCreateOptionsMenu(menu!!, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //Dottore
            R.id.action_logoutDoctor -> {
                editor.remove("doctorId");
                editor.remove("name");
                editor.remove("surname");
                editor.remove("gender");
                editor.remove("dateOfBirth");
                editor.remove("email");
                editor.remove("phoneNumber");
                editor.remove("passwd");
                editor.remove("headOffice");
                editor.commit();

                val action = DoctorTimeSlotDirections.actionDoctorTimeSlotToNavHome()
                findNavController().navigate(action)
                Toast.makeText(activity,"Logout effettuato",Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.doctorAppointment -> {
                Toast.makeText(activity,"app dottore",Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.doctorTimeSlot -> {
                Toast.makeText(activity,"timeslot",Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.doctorProfile -> {
                Toast.makeText(activity,"dott profilo",Toast.LENGTH_SHORT).show()
                return true
            }

        }
        return false
    }

    fun updateRow(index : Int,value : String){
        when(index) {
            1->{
                timeSlotsArray[1]=value
                if (value=="00:00:00")
                    timeSlot1?.text="NON DISPONIBILE"
                else
                    timeSlot1?.text=value.dropLast(3)
            }
            2->{
                timeSlotsArray[2]=value
                if (value=="00:00:00")
                timeSlot2?.text="NON DISPONIBILE"
            else
                timeSlot2?.text=value.dropLast(3)}
            3->{
                timeSlotsArray[3]=value
                if (value=="00:00:00")
                timeSlot3?.text="NON DISPONIBILE"
            else
                timeSlot3?.text=value.dropLast(3)}
            4->{                timeSlotsArray[4]=value

                if (value=="00:00:00")
                timeSlot4?.text="NON DISPONIBILE"
            else
                timeSlot4?.text=value.dropLast(3)}
            5->{                timeSlotsArray[5]=value
                if (value=="00:00:00")
                timeSlot5?.text="NON DISPONIBILE"
            else
                timeSlot5?.text=value.dropLast(3)}
            6->{                timeSlotsArray[6]=value
                if (value=="00:00:00")
                timeSlot6?.text="NON DISPONIBILE"
            else
                timeSlot6?.text=value.dropLast(3)}
            7->{                timeSlotsArray[7]=value
                if (value=="00:00:00")
                timeSlot7?.text="NON DISPONIBILE"
            else
                timeSlot7?.text=value.dropLast(3)}
            8->{                timeSlotsArray[8]=value
                if (value=="00:00:00")
                timeSlot8?.text="NON DISPONIBILE"
            else
                timeSlot8?.text=value.dropLast(3)}
            9->{                timeSlotsArray[9]=value
                if (value=="00:00:00")
                timeSlot9?.text="NON DISPONIBILE"
            else
                timeSlot9?.text=value.dropLast(3)}
            10->{                timeSlotsArray[10]=value
                if (value=="00:00:00")
                timeSlot10?.text="NON DISPONIBILE"
            else
                timeSlot10?.text=value.dropLast(3)}
            11->{                timeSlotsArray[11]=value
                if (value=="00:00:00")
                timeSlot11?.text="NON DISPONIBILE"
            else
                timeSlot11?.text=value.dropLast(3)}
            12->{                timeSlotsArray[12]=value
                if (value=="00:00:00")
                timeSlot12?.text="NON DISPONIBILE"
            else
                timeSlot12?.text=value.dropLast(3)}
            13->{                timeSlotsArray[13]=value
                if (value=="00:00:00")
                timeSlot13?.text="NON DISPONIBILE"
            else
                timeSlot13?.text=value.dropLast(3)}
            14->{                timeSlotsArray[14]=value
                if (value=="00:00:00")
                timeSlot14?.text="NON DISPONIBILE"
            else
                timeSlot14?.text=value.dropLast(3)}
        }
    }


    fun openDialog(index: Int,value : String,timeSlotsArray1 : Array<String>){

        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        var newTimeSlot1 : Array<String>
        var newTimeSlot : Array<String>
        builder.setTitle("Modifica singola fascia oraria")
        val input = EditText(activity)
        input.inputType = InputType.TYPE_CLASS_DATETIME
        if(value!="NON DISPONIBILE")
        input.setText(value)
        builder.setView(input)
        builder.setMessage("Inserisci qui sotto il nuovo orario desiderato. (HH:MM)")

        builder.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialog, which ->
                m_Text = input.text.toString()
                val pattern = "[0-1][0-9]:[0-5][0-9]".toRegex()
                if (!pattern.matches(m_Text)){
                    Toast.makeText(context,"Formato data non valido. Gli orari di apertura variano dalle 8:00 alle 19:59",Toast.LENGTH_SHORT).show()
                }else{
                     newTimeSlot1=timeSlotsArray1
                    newTimeSlot1[index]=m_Text+":00"
                    val timeSlot = TimeSlot(
                        doctor_id = pref.getString("doctorId","default"),
                        passwd = pref.getString("passwd","default"),
                        slots = newTimeSlot1
                    )
                    val apiService = RestApiService()
                    apiService.updateTimeSlot(timeSlot) {
                        if (it!=null && it.message!=null){
                            if ( it.message.toString() == "Time Slots aggiornato correttamente.") {
                                Toast.makeText(activity,"Modifiche salvate correttamente",Toast.LENGTH_SHORT).show()
                                this.timeSlotsArray=newTimeSlot1
                                val id = findNavController().currentDestination?.id
                                findNavController().popBackStack(id!!,true)
                                findNavController().navigate(id)
                            } else {
                                Toast.makeText(activity,it.message.toString(),Toast.LENGTH_SHORT).show()

                            }
                        }
                    }
                }

            })
        builder.setNegativeButton("Rimuovi",
            DialogInterface.OnClickListener { dialog, which ->
                newTimeSlot=timeSlotsArray1
                newTimeSlot[index]="00:00:00"
                val timeSlot = TimeSlot(
                    doctor_id = pref.getString("doctorId","default"),
                    passwd = pref.getString("passwd","default"),
                    slots = newTimeSlot
                )
                val apiService = RestApiService()
                apiService.updateTimeSlot(timeSlot) {
                    if (it!=null && it.message!=null){
                        if ( it.message.toString() == "Time Slots aggiornato correttamente.") {
                            Toast.makeText(activity,"Modifiche salvate correttamente",Toast.LENGTH_SHORT).show()
                            this.timeSlotsArray=newTimeSlot
                            val id = findNavController().currentDestination?.id
                            findNavController().popBackStack(id!!,true)
                            findNavController().navigate(id)
                        } else {
                            Toast.makeText(activity,it.message.toString(),Toast.LENGTH_SHORT).show()

                        }
                    }
                }
                dialog.cancel()
            })
        builder.setNeutralButton("Annulla",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}