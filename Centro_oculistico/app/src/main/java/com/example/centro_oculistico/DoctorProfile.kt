package com.example.centro_oculistico

import RestApiService
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.centro_oculistico.adapter.*
import com.example.centro_oculistico.databinding.FragmentDoctorProfileBinding
import com.example.centro_oculistico.databinding.FragmentUserProfileBinding
import com.example.centro_oculistico.ui.slideshow.UserProfileDirections


class DoctorProfile : Fragment() {



    private var _binding: FragmentDoctorProfileBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var  editor : SharedPreferences.Editor
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val pref = requireActivity().getSharedPreferences("userDataPreferences", Context.MODE_PRIVATE)
        editor=pref.edit()
        _binding = FragmentDoctorProfileBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true);

        binding.doctorIdProfile.isEnabled=false
        binding.doctorIdProfile.setText(pref.getString("doctorId","default").toString())
        binding.nameDoctorProfile.isEnabled=false
        binding.nameDoctorProfile.setText(pref.getString("name","default").toString())
        binding.surnameDoctorProfile.isEnabled=false
        binding.surnameDoctorProfile.setText(pref.getString("surname","default").toString())
        binding.dateOfBirthDoctorProfile.isEnabled=false
        binding.dateOfBirthDoctorProfile.setText(pref.getString("dateOfBirth","default").toString())
        binding.genderDoctorProfile.isEnabled=false
        binding.headOfficeDoctorProfile.setText(pref.getString("headOffice","default").toString())
        binding.headOfficeDoctorProfile.isEnabled=false
        binding.genderDoctorProfile.setText(pref.getString("gender","default").toString())
        binding.regPasswordDoctorProfile.setText(pref.getString("passwd","default").toString())
        binding.emailDoctorProfile.setText(pref.getString("email","default").toString())
        binding.phoneNumberDoctorProfile.setText(pref.getString("phoneNumber","default").toString())



        binding.UpdateDoctorProfile.setOnClickListener {
            if (
                binding.emailDoctorProfile.text.toString()==pref.getString("email","default").toString() &&
                binding.regPasswordDoctorProfile.text.toString() ==  pref.getString("passwd","default").toString() &&
                binding.phoneNumberDoctorProfile.text.toString() ==  pref.getString("phoneNumber","default").toString()
            ){
                Toast.makeText(context,"Modificare prima i dati",Toast.LENGTH_SHORT).show()
            }else{

                context?.let { it1 ->
                    AlertDialog.Builder(it1)
                        .setTitle("Modifica dati")
                        .setMessage("Sei sicuro di voler aggiornare i tuoi dati?") // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Ok",
                            DialogInterface.OnClickListener { dialog, which ->
                                // Continue with delete operation
                                val doctorToUpdate = DoctorToUpdate(
                                    doctorId = pref.getString("doctorId","default").toString(),
                                    passwd = pref.getString("passwd","default").toString(),
                                    new_passwd = binding.regPasswordDoctorProfile.text.toString(),
                                    email = binding.emailDoctorProfile.text.toString(),
                                    phoneNumber = binding.phoneNumberDoctorProfile.text.toString()
                                )
                                val apiService = RestApiService()
                                apiService.updateDoctor(doctorToUpdate) {
                                    if (it?.message != null){
                                        if ( it.message.toString() == "Dati Dottore aggiornati correttamente.") {
                                            editor.putString("email", doctorToUpdate.email)
                                            editor.commit()
                                            editor.putString("phoneNumber", doctorToUpdate.phoneNumber)
                                            editor.commit()
                                            editor.putString("passwd", doctorToUpdate.new_passwd)
                                            editor.commit()
                                            val id = findNavController().currentDestination?.id
                                            findNavController().popBackStack(id!!,true)
                                            findNavController().navigate(id)
                                            Toast.makeText(activity,"Modifiche salvate correttamente",Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(activity,it.message.toString(),Toast.LENGTH_SHORT).show()

                                        }
                                    }
                                }
                            })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                }


            }
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

                val action = DoctorProfileDirections.actionDoctorProfileToNavHome()
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