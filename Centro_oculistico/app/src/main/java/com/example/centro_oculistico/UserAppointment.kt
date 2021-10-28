package com.example.centro_oculistico.ui.slideshow

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.centro_oculistico.R
import com.example.centro_oculistico.adapter.*
import com.example.centro_oculistico.databinding.FragmentUserAppointmentBinding
import org.json.JSONObject


class UserAppointment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private var _binding: FragmentUserAppointmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var  editor : SharedPreferences.Editor
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val pref = requireActivity().getSharedPreferences("userDataPreferences", Context.MODE_PRIVATE)
         editor=pref.edit()
        _binding = FragmentUserAppointmentBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true);


        var userAppointment : MutableList<AppointmentVolley> = mutableListOf()
        //http call to get the resources
        val queue = Volley.newRequestQueue(activity)
        val url = "http://centrooculistico.hostinggratis.it/centro_oculisticoREST/appointment/read.php"
        // Request a json response from the provided URL.

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                for (i in 0 until response.length()) {
                    val item = response.getJSONObject(i) as JSONObject
                    if (item.get("cdf").toString()==pref.getString("cdf","default123pp")){
                        val itemAppointment= AppointmentVolley(
                            item.get("cdf").toString(),
                            item.get("doctorId").toString(),
                            item.get("timeSlot").toString(),
                            item.get("branchId").toString(),
                            item.get("day").toString(),
                            item.get("appointmentId").toString()
                        )
                        userAppointment.add(itemAppointment)
                    }
                }

                recyclerView = binding.recyclerViewUserAppointment
                recyclerView.adapter = AppointmentItemAdapter(context,userAppointment)
                binding.textView10.isVisible = userAppointment.size<1
                binding.textView8.isVisible = userAppointment.size>=1

            },
            { error ->
            }
        )
        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest)




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
                editor.remove("messageUpdateAppointmentDialog");
                editor.remove("phoneNumber");
                editor.remove("passwd");
                editor.commit();
                val action= UserAppointmentDirections.actionUserAppointmentToNavHome()
                findNavController().navigate(action)

                Toast.makeText(activity,"Logout effettuato",Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.userProfile -> {
                Toast.makeText(activity,"profilo utente",Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.userAppointment -> {
                Toast.makeText(activity,"app utente",Toast.LENGTH_SHORT).show()
                return true
            }

        }
        return false
    }

    fun reload(){
        val id = findNavController().currentDestination?.id
        findNavController().popBackStack(id!!,true)
        findNavController().navigate(id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}