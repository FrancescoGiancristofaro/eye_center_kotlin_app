package com.example.centro_oculistico.ui.home

import RestApiService
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.centro_oculistico.DoctorCalendarFragmentDirections
import com.example.centro_oculistico.R
import com.example.centro_oculistico.adapter.AppointmentToCreate
import com.example.centro_oculistico.adapter.Branch
import com.example.centro_oculistico.adapter.Doctor
import com.example.centro_oculistico.adapter.UserLogin
import com.example.centro_oculistico.databinding.FragmentBookAppointmentBinding
import com.example.centro_oculistico.ui.login.LoginFragmentDirections
import com.example.centro_oculistico.ui.signin.SignInFragmentDirections

import org.json.JSONObject
import java.util.*
import kotlin.concurrent.schedule


class BookAppointmentFragment : Fragment() {

    private var _binding: FragmentBookAppointmentBinding? = null
    private lateinit var editor: SharedPreferences.Editor

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBookAppointmentBinding.inflate(inflater, container, false)

        val pref = requireActivity().getSharedPreferences(
            "userDataPreferences",
            AppCompatActivity.MODE_PRIVATE
        )
        editor=pref.edit()
        val cdf = binding.cdfBook
        val doctorsList = binding.spinnerDoctor
        val branchesList = binding.spinnerBranches
        var branches: MutableList<String> = mutableListOf()
        val doctors: MutableList<String> = mutableListOf()
        binding.timeSlot.setIs24HourView(true)

        //http call to get the resources
        val queue = Volley.newRequestQueue(activity)
        val url = "http://centrooculistico.hostinggratis.it/centro_oculisticoREST/branch/read.php"
        // Request a json response from the provided URL.
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                for (i in 0 until response.length()) {
                    val item1 = response.getJSONObject(i) as JSONObject
                    val itemBranch =
                        item1.get("address").toString() + " " + item1.get("city").toString()+" ["+item1.get("idBranch")+"]"
                    branches.add(itemBranch)
                    // Your code here
                }
                val adapter1 = context?.let { ArrayAdapter(it, R.layout.spinner_item, branches) }
                if (adapter1 != null) {
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    branchesList.adapter = adapter1
                    adapter1.notifyDataSetChanged();
                    branchesList.setSelection(0)
                }
            },
            { error ->
                // TODO: Handle error

            }
        )
        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest)


        //http call to get the resources
        val queue1 = Volley.newRequestQueue(activity)
        val url1 = "http://centrooculistico.hostinggratis.it/centro_oculisticoREST/doctor/read.php"
        // Request a json response from the provided URL.

        val jsonArrayRequest1 = JsonArrayRequest(Request.Method.GET, url1, null,
            { response ->
                for (i in 0 until response.length()) {
                    val item = response.getJSONObject(i) as JSONObject
                    val itemDoctor =
                        "Dr. " + item.get("name").toString() + " " + item.get("surname").toString()+" ["+item.get("doctorId")+"]"
                    doctors.add(itemDoctor)
                    // Your code here
                }
                val adapter = context?.let {
                    ArrayAdapter(it, R.layout.spinner_item, doctors)
                }
                if (adapter != null) {
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    doctorsList.adapter = adapter
                    adapter.notifyDataSetChanged();
                    doctorsList.setSelection(0)
                }
            },
            { error ->
                // TODO: Handle error
            }
        )
        // Add the request to the RequestQueue.
        queue1.add(jsonArrayRequest1)

        if (pref.getString("cdf", "default") != "default") {
            cdf.setText(pref.getString("cdf", "default").toString())
        } else {
            cdf.isEnabled = true;
        }

        setHasOptionsMenu(true);

        binding.regBook.setOnClickListener {
            val doctor = doctorsList.selectedItem.toString().takeLast(8).replace("[","").replace("]","")
            val branch = branchesList.selectedItem.toString().takeLast(3).replace("[","").replace("]","")
            var date = binding.datePicker1.year.toString() + "-"
            if (binding.datePicker1.month.toString().length == 1)
                date += "0" + binding.datePicker1.month.toString() + "-"
            else
                date += binding.datePicker1.month.toString() + "-"
            if (binding.datePicker1.dayOfMonth.toString().length == 1) {
                date += "0" + binding.datePicker1.dayOfMonth.toString()
            } else {
                date += binding.datePicker1.dayOfMonth.toString()
            }
            var timeSlot: String
            if (binding.timeSlot.hour.toString().length == 1)
                timeSlot = "0" + binding.timeSlot.hour.toString() + ":"
            else
                timeSlot = binding.timeSlot.hour.toString() + ":"
            if (binding.timeSlot.minute.toString().length == 1)
                timeSlot += "0" + binding.timeSlot.minute.toString() + ":00"
            else
                timeSlot +=binding.timeSlot.minute.toString() + ":00"


            val apiService = RestApiService()
            val appointmentToCreate = AppointmentToCreate(  cdf = binding.cdfBook.text.toString(), doctorId = doctor,branchId = branch,day = date,timeSlot = timeSlot )
            apiService.createAppointment(appointmentToCreate) {
                if (it!=null){
                    if ( it.message.toString() != "") {

                        Toast.makeText(activity, it.message.toString(), Toast.LENGTH_LONG)
                            .show()
                        if ( it.message.toString() == "Appuntamento creato correttamente. Il codice è personale e unico. Usalo per modificare o cancellare la prenotazione in qualunque momento.") {
                            val action =
                            BookAppointmentFragmentDirections.actionBookAppointmentFragmentToUserAppointment()
                        view?.findNavController()?.navigate(action)
                        }

                    } else {
                        Toast.makeText(activity, "Errore generico", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.fragment_home_menu, menu)
        super.onCreateOptionsMenu(menu!!, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //cliente
            R.id.action_logout -> {
                editor.remove("cdf");
                editor.remove("name");
                editor.remove("surname");
                editor.remove("gender");
                editor.remove("dateOfBirth");
                editor.remove("email");
                editor.remove("phoneNumber");
                editor.remove("passwd");
                editor.remove("messageUpdateAppointmentDialog");
                editor.commit();

                val action= BookAppointmentFragmentDirections.actionBookAppointmentFragmentToNavHome()
                findNavController().navigate(action)
                Toast.makeText(activity, "Logout effettuato", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.userProfile -> {
                Toast.makeText(activity, "profilo utente", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.userAppointment -> {
                Toast.makeText(activity, "app utente", Toast.LENGTH_SHORT).show()
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