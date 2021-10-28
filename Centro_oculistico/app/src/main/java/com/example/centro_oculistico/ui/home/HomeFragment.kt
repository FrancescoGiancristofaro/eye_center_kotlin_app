package com.example.centro_oculistico.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.centro_oculistico.R
import com.example.centro_oculistico.databinding.FragmentHomeBinding
import com.example.centro_oculistico.ui.gallery.GalleryFragmentDirections
import kotlin.properties.Delegates


class HomeFragment : Fragment() {
    private var isDoctor = false
    private var isCliente =false
    private var _binding: FragmentHomeBinding? = null
    private lateinit var editor : SharedPreferences.Editor
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val pref = requireActivity().getSharedPreferences("userDataPreferences", Context.MODE_PRIVATE)
        editor=pref.edit()

        if (pref.getString("cdf","default")!= "default"){
            isCliente=true
            binding.buttonBook.visibility=View.VISIBLE
            binding.textHome.text="Benvenuto "+ pref.getString("name","default")+" "+pref.getString("surname","default")




        }else if (pref.getString("doctorId","default")!= "default"){
            isDoctor=true
            binding.textHome.text="Benvenuto Dr. "+ pref.getString("name","default")+" "+pref.getString("surname","default")

        }
        setHasOptionsMenu(true);

        binding.buttonBook.setOnClickListener{
            val action= HomeFragmentDirections.actionNavHomeToBookAppointmentFragment()
            it.findNavController().navigate(action)
        }

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

                val id = findNavController().currentDestination?.id
                findNavController().popBackStack(id!!,true)
                findNavController().navigate(id)
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

                val id = findNavController().currentDestination?.id
                findNavController().popBackStack(id!!,true)
                findNavController().navigate(id)
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