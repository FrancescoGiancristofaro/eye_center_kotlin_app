package com.example.centro_oculistico.ui.slideshow

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.centro_oculistico.R
import com.example.centro_oculistico.adapter.Branch
import com.example.centro_oculistico.adapter.BranchItemAdapter
import com.example.centro_oculistico.adapter.Doctor
import com.example.centro_oculistico.adapter.DoctorItemAdapter
import com.example.centro_oculistico.databinding.FragmentSlideshowBinding
import com.example.centro_oculistico.ui.login.LoginFragmentDirections
import org.json.JSONObject
import kotlin.properties.Delegates

class SlideshowFragment : Fragment() {
    private var isDoctor =false
    private var isCliente =false
    private lateinit var recyclerView: RecyclerView
    private var _binding: FragmentSlideshowBinding? = null
private lateinit var editor : SharedPreferences.Editor
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true);

        val pref = requireActivity().getSharedPreferences("userDataPreferences", Context.MODE_PRIVATE)
        editor=pref.edit()
        if (pref.getString("cdf","default")!= "default"){
            isCliente=true
            /*val view: View = requireActivity().findViewById(R.id.drawer_layout)
            val myIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_user)
            view.findViewById<Toolbar>(R.id.toolbar).overflowIcon=myIcon*/

        }else if (pref.getString("doctorId","default")!= "default"){
            isDoctor=true

        }
        setHasOptionsMenu(true);


        var branches: MutableList<Branch> = mutableListOf()
        //http call to get the resources
        val queue = Volley.newRequestQueue(activity)
        val url = "http://centrooculistico.hostinggratis.it/centro_oculisticoREST/branch/read.php"
        // Request a json response from the provided URL.

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                for (i in 0 until response.length()) {
                    val item = response.getJSONObject(i) as JSONObject
                    val itemBranch = Branch(
                        item.get("idBranch").toString(),
                        item.get("address").toString(),
                        item.get("city").toString(),
                        item.get("switchboardNumber").toString(),
                    )
                    branches.add(itemBranch)
                }
                recyclerView = binding.recyclerViewBranches
                recyclerView.adapter = BranchItemAdapter(context,branches)
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
                editor.remove("name");
                editor.remove("messageUpdateAppointmentDialog");
                editor.remove("surname");
                editor.remove("gender");
                editor.remove("dateOfBirth");
                editor.remove("email");
                editor.remove("phoneNumber");
                editor.remove("passwd");
                editor.commit();
                val action= SlideshowFragmentDirections.actionNavSlideshowToNavHome()
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

                val action= SlideshowFragmentDirections.actionNavSlideshowToNavHome()
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