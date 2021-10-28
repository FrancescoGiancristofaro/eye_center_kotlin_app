package com.example.centro_oculistico

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.centro_oculistico.adapter.*
import com.example.centro_oculistico.databinding.FragmentDoctorAppointmentBinding
import com.example.centro_oculistico.databinding.FragmentSlideshowBinding
import com.example.centro_oculistico.databinding.FragmentUserAppointmentBinding
import org.json.JSONObject


class DoctorAppointment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private var _binding: FragmentDoctorAppointmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var  editor : SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var pref=requireActivity().getSharedPreferences("userDataPreferences", AppCompatActivity.MODE_PRIVATE)
        editor = pref.edit()
        _binding = FragmentDoctorAppointmentBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true);


        var appointment: MutableList<AppointmentVolley> = mutableListOf()
        //http call to get the resources
        val queue = Volley.newRequestQueue(activity)
        val url = "http://centrooculistico.hostinggratis.it/centro_oculisticoREST/appointment/read.php"
        // Request a json response from the provided URL.

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val name = pref.getString("name","default")
                val surname = pref.getString("surname","default")
                for (i in 0 until response.length()) {
                    val item = response.getJSONObject(i) as JSONObject
                    if (item.get("doctorId").toString()==name+" "+surname ) {
                        val itemAppointment = AppointmentVolley(
                            item.get("cdf").toString(),
                            item.get("doctorId").toString(),
                            item.get("timeSlot").toString(),
                            item.get("branchId").toString(),
                            item.get("day").toString(),
                            item.get("appointmentId").toString()
                        )
                        appointment.add(itemAppointment)
                    }
                }
                recyclerView = binding.recyclerViewDoctorAppointment
                recyclerView.adapter = AppointmentDoctorItemAdapter(context,appointment)
                if (appointment.size<1)
                    binding.textView101.isVisible=true
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

                val action = DoctorAppointmentDirections.actionDoctorAppointmentToNavHome()
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}