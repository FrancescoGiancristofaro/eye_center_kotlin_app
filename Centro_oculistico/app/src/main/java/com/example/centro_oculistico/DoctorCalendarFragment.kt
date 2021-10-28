package com.example.centro_oculistico

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color.*
import android.graphics.Typeface
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.centro_oculistico.databinding.FragmentDoctorCalendarBinding
import org.json.JSONObject


/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoctorCalendarFragment() : Fragment() {
    private var isDoctor = false
    private var isCliente =false
    private lateinit var editor : SharedPreferences.Editor

    private var _binding: FragmentDoctorCalendarBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoctorCalendarBinding.inflate(inflater, container, false)
        val pref = requireActivity().getSharedPreferences("userDataPreferences", Context.MODE_PRIVATE)
        editor=pref.edit()

        if (pref.getString("cdf","default")!= "default"){
            isCliente=true
        }else if (pref.getString("doctorId","default")!= "default"){
            isDoctor=true
        }

        setHasOptionsMenu(true);

        val queue = Volley.newRequestQueue(activity)
        val url = "http://centrooculistico.hostinggratis.it/centro_oculisticoREST/timeSlot/read.php"
        // Request a json response from the provided URL.
        val param = arguments?.getString("doctorId")
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                for (i in 0 until response.length()) {
                    val item = response.getJSONObject(i) as JSONObject
                    if(param==item.get("doctor_id").toString()){
                        for (j in 0..13){
                            addRow(j+1,item.getString("slot"+(j+1)))
                        }
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


        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        if (isCliente)
            inflater.inflate(R.menu.fragment_home_menu, menu)
        else if (isDoctor)
            inflater.inflate(R.menu.fragment_doctor_menu, menu)
        else
            inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu!!, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //cliente
            R.id.action_logout -> {
                editor.remove("cdf");
                editor.remove("messageUpdateAppointmentDialog");
                editor.remove("name");
                editor.remove("surname");
                editor.remove("gender");
                editor.remove("dateOfBirth");
                editor.remove("email");
                editor.remove("phoneNumber");
                editor.remove("passwd");
                editor.commit();

                val action= DoctorCalendarFragmentDirections.actionDoctorCalendarFragmentToNavHome()
                findNavController().navigate(action)
                Toast.makeText(activity,"Logout effettuato", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.userProfile -> {
                Toast.makeText(activity,"profilo utente", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.userAppointment -> {
                Toast.makeText(activity,"app utente", Toast.LENGTH_SHORT).show()
                return true
            }
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

                val action= DoctorCalendarFragmentDirections.actionDoctorCalendarFragmentToNavHome()
                findNavController().navigate(action)
                Toast.makeText(activity,"Logout effettuato", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.doctorAppointment -> {
                Toast.makeText(activity,"app dottore", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.doctorTimeSlot -> {
                Toast.makeText(activity,"timeslot", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.doctorProfile -> {
                Toast.makeText(activity,"dott profilo", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addRow(index : Int, value : String){
        var table : TableLayout = binding.calendarTable
        val row = TableRow(activity)
        val lp: TableRow.LayoutParams =
            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,30, 1F)
        row.layoutParams = lp

        val textView = TextView(activity)
        if (value=="00:00:00" || value=="null" || value=="")
        textView.text = "NON DISPONIBILE"
        else
            textView.text=value
        textView.height=150
        textView.gravity=17
        textView.setTextColor(BLACK)
        textView.typeface = Typeface.DEFAULT_BOLD;
        if (index!=1)
            textView.setPadding(0,40,0,0)

        row.addView(textView)
        row.gravity=17
        table.addView(row)

        if (index!=14){
            val border = TableRow(activity)
            val lp1: TableRow.LayoutParams =
                TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 5, 1F)
            val textView1 = TextView(activity)
            textView1.height = 5
            textView1.setBackgroundColor(LTGRAY)
            border.addView(textView1)
            border.layoutParams = lp1
            border.gravity = 17
            border.setBackgroundColor(LTGRAY)
            table.addView(border)
        }

    }

}