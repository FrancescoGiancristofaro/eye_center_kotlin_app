package com.example.centro_oculistico.ui.login

import RestApiService
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.centro_oculistico.adapter.DoctorLogin
import com.example.centro_oculistico.adapter.UserLogin
import com.example.centro_oculistico.databinding.FragmentLoginBinding
import java.util.*
import kotlin.concurrent.schedule


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private lateinit var editor : SharedPreferences.Editor

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val pref = requireActivity().getSharedPreferences("userDataPreferences",Context.MODE_PRIVATE)
        editor = pref.edit()

        _binding  = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameEditText = binding.username
        val passwordEditText = binding.password
        val loginButton = binding.login
        val loadingProgressBar = binding.loading


        loginButton.setOnClickListener {
            val view : View = it
            loadingProgressBar.visibility = View.VISIBLE
            val apiService = RestApiService()
            val radioCliente = binding.radioCliente
            val radioDottore = binding.radioDoctor
            if (radioCliente.isChecked){
                val userLogin = UserLogin(  cdf = usernameEditText.text.toString(),passwd = passwordEditText.text.toString() )
                apiService.loginUser(userLogin) {
                    if (it?.get(0)?.cdf != null) {
                        editor.putString("cdf", it[0].cdf)
                        editor.commit()
                        editor.putString("passwd", it[0].passwd)
                        editor.commit()
                        editor.putString("name", it[0].name)
                        editor.commit()
                        editor.putString("surname", it[0].surname)
                        editor.commit()
                        editor.putString("email", it[0].email)
                        editor.commit()
                        editor.putString("phoneNumber", it[0].phoneNumber)
                        editor.commit()
                        editor.putString("gender", it[0].gender)
                        editor.commit()
                        editor.putString("dateOfBirth", it[0].dateOfBirth)
                        editor.commit()

                        Toast.makeText(activity,"Benvenuto "+it[0].name+" "+it[0].surname,Toast.LENGTH_SHORT).show()
                        Timer("SettingUp", false).schedule(2500){
                            loadingProgressBar.visibility = View.INVISIBLE
                        }
                        val imm: InputMethodManager =
                            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(view.windowToken, 0)
                        val action= LoginFragmentDirections.actionLoginFragmentToNavHome()
                        view.findNavController().navigate(action)

                    } else {
                        Toast.makeText(activity,"Errore login: Codice fiscale o password errati.",Toast.LENGTH_SHORT).show()
                        loadingProgressBar.visibility = View.INVISIBLE

                    }
                }

            }else if (radioDottore.isChecked){
                val doctorLogin = DoctorLogin(  doctorId = usernameEditText.text.toString(),passwd = passwordEditText.text.toString() )
                apiService.loginDoctor(doctorLogin) {
                    var find=false
                    if (it != null) {
                        for (i in 0..it.size-1){
                            if (doctorLogin.doctorId==it[i].doctorId && doctorLogin.passwd==it[i].passwd){
                                editor.putString("doctorId", it[i].doctorId)
                                editor.commit()
                                editor.putString("passwd", it[i].passwd)
                                editor.commit()
                                editor.putString("name", it[i].name)
                                editor.commit()
                                editor.putString("surname", it[i].surname)
                                editor.commit()
                                editor.putString("email", it[i].email)
                                editor.commit()
                                editor.putString("phoneNumber", it[i].phoneNumber)
                                editor.commit()
                                editor.putString("gender", it[i].gender)
                                editor.commit()
                                editor.putString("dateOfBirth", it[i].dateOfBirth)
                                editor.commit()
                                editor.putString("headOffice", it[i].headOffice)
                                editor.commit()
                                Toast.makeText(activity,"Benvenuto "+it[i].name+" "+it[i].surname,Toast.LENGTH_SHORT).show()
                                Timer("SettingUp", false).schedule(2500){
                                    loadingProgressBar.visibility = View.INVISIBLE
                                }
                                val imm: InputMethodManager =
                                    requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                                imm.hideSoftInputFromWindow(view.windowToken, 0)
                                val action= LoginFragmentDirections.actionLoginFragmentToNavHome()
                                view.findNavController().navigate(action)
                                find=true
                                break
                            }
                        }
                    }
                    if (!find){
                        Toast.makeText(activity,"Errore login: ID dottore o password errati.",Toast.LENGTH_SHORT).show()
                        loadingProgressBar.visibility = View.INVISIBLE
                    }
                }
            }else
                Toast.makeText(activity,"Errore radio",Toast.LENGTH_SHORT).show()

        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}