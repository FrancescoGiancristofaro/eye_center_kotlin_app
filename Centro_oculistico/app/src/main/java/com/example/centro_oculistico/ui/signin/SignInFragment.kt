package com.example.centro_oculistico.ui.signin

import RestApiService
import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.centro_oculistico.R
import com.example.centro_oculistico.adapter.User
import com.example.centro_oculistico.adapter.UserLogin
import com.example.centro_oculistico.databinding.FragmentSignInBinding
import com.example.centro_oculistico.ui.login.LoginFragmentDirections
import java.util.*
import kotlin.concurrent.schedule


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding  = FragmentSignInBinding.inflate(inflater, container, false)
        val spinner: Spinner = binding.spinner
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.genders,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner.adapter = adapter
            }
        }

        binding.regRegister.setOnClickListener {
            val pattern = "[1-9][0-9][0-9][0-9]/[0-9][0-9]/[0-9][0-9]".toRegex()
            var cdf = binding.cdf.text.toString()
            var name = binding.name.text.toString()
            var surname = binding.surname.text.toString()
            var email = binding.email.text.toString()
            var passwd = binding.regPassword.text.toString()
            var phoneNumber = binding.phoneNumber.text.toString()
            var dateOfBirth = binding.dateOfBirth.text.toString()
            var gender= binding.spinner.selectedItem.toString()
            if (cdf!=""&&name!=""&&surname!=""&&email!=""&&passwd!=""&&phoneNumber!=""&&dateOfBirth!=""&&gender!=""){
                if (!pattern.containsMatchIn(dateOfBirth)){
                    Toast.makeText(context,"Formato data di nascita non valido",Toast.LENGTH_SHORT).show()
                }else{
                    cdf=cdf.uppercase()
                    val helper =dateOfBirth.replace("/","-")
                    dateOfBirth= helper
                    val apiService = RestApiService()
                    val user = User(  cdf = cdf,passwd = passwd,name=name,surname = surname,email = email,phoneNumber = phoneNumber,dateOfBirth = dateOfBirth,gender = gender )
                    apiService.createUser(user) {
                        if (it!=null){
                            if ( it.message.toString() == "Utente creato correttamente.") {

                                Toast.makeText(activity, it.message.toString(), Toast.LENGTH_SHORT)
                                    .show()

                                val imm: InputMethodManager =
                                    requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                                imm.hideSoftInputFromWindow(view?.windowToken, 0)
                                val action =
                                    SignInFragmentDirections.actionSignInFragmentToLoginFragment()
                                view?.findNavController()?.navigate(action)

                            } else {
                                Toast.makeText(activity, it.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                }
            }else{
                Toast.makeText(context,"Dati incompleti. Ricontrollare",Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}