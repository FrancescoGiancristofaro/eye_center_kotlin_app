package com.example.centro_oculistico.ui.slideshow

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
import com.example.centro_oculistico.R
import com.example.centro_oculistico.adapter.*
import com.example.centro_oculistico.databinding.FragmentUserProfileBinding


class UserProfile : Fragment() {



    private var _binding: FragmentUserProfileBinding? = null
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
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true);

        binding.cdfProfile.isEnabled=false
        binding.cdfProfile.setText(pref.getString("cdf","default").toString())
        binding.nameProfile.isEnabled=false
        binding.nameProfile.setText(pref.getString("name","default").toString())
        binding.surnameProfile.isEnabled=false
        binding.surnameProfile.setText(pref.getString("surname","default").toString())
        binding.dateOfBirthProfile.isEnabled=false
        binding.dateOfBirthProfile.setText(pref.getString("dateOfBirth","default").toString())
        binding.genderProfile.isEnabled=false
        binding.genderProfile.setText(pref.getString("gender","default").toString())
        binding.regPasswordProfile.setText(pref.getString("passwd","default").toString())
        binding.emailProfile.setText(pref.getString("email","default").toString())
        binding.phoneNumberProfile.setText(pref.getString("phoneNumber","default").toString())



        binding.UpdateProfile.setOnClickListener {
            if (
                binding.emailProfile.text.toString()==pref.getString("email","default").toString() &&
                binding.regPasswordProfile.text.toString() ==  pref.getString("passwd","default").toString() &&
                binding.phoneNumberProfile.text.toString() ==  pref.getString("phoneNumber","default").toString()
                    ){
                Toast.makeText(context,"Modificare prima i dati",Toast.LENGTH_SHORT).show()
            }else{

                context?.let { it1 ->
                    AlertDialog.Builder(it1)
                        .setTitle("Modifica dati")
                        .setMessage("Sei sicuro di voler aggiornare i tuoi dati?") // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes,
                            DialogInterface.OnClickListener { dialog, which ->
                                // Continue with delete operation
                                val userToUpdate = UserToUpdate(
                                    cdf = pref.getString("cdf","default").toString(),
                                    passwd = pref.getString("passwd","default").toString(),
                                    new_passwd = binding.regPasswordProfile.text.toString(),
                                    email = binding.emailProfile.text.toString(),
                                    phoneNumber = binding.phoneNumberProfile.text.toString()
                                )
                                val apiService = RestApiService()
                                apiService.updateUser(userToUpdate) {
                                    if (it!=null && it.message!=null){
                                        if ( it.message.toString() == "Utente aggiornato correttamente.") {
                                            editor.putString("email", userToUpdate.email)
                                            editor.commit()
                                            editor.putString("phoneNumber", userToUpdate.phoneNumber)
                                            editor.commit()
                                            editor.putString("passwd", userToUpdate.new_passwd)
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
                            }) // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                }


            }
        }


        binding.deleteUser.setOnClickListener {
            val view =it
            context?.let { it1 ->
                var dialog=AlertDialog.Builder(it1)
                    .setTitle("Cancellazione profilo")
                    .setMessage("Sei sicuro di voler cancellare il tuo profilo? L'operazione è irreversibile") // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("Ok",
                        DialogInterface.OnClickListener { dialog, which ->
                            val userToDelete = UserToDelete(
                                cdf = pref.getString("cdf","default").toString(),
                                passwd = pref.getString("passwd","default").toString()
                            )
                            val apiService = RestApiService()
                            apiService.deleteUser(userToDelete) {
                                if (it!=null && it.message!=null){
                                    if ( it.message.toString() == "Utente cancellato correttamente.") {
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
                                        val action= UserProfileDirections.actionUserProfileToNavHome()
                                        view.findNavController().navigate(action)
                                        Toast.makeText(activity,"Utente cancellato correttamente.",Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(activity,it.message.toString(),Toast.LENGTH_SHORT).show()

                                    }
                                }
                            }
                        }) // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("Annulla", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
                    dialog.setOnShowListener {
                        dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.red,null))
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
                editor.remove("messageUpdateAppointmentDialog");
                editor.remove("phoneNumber");
                editor.remove("passwd");
                editor.commit();
                val action= UserProfileDirections.actionUserProfileToNavHome()
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}