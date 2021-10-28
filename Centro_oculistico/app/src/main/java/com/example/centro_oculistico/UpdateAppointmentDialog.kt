package com.example.centro_oculistico

import RestApiService
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.centro_oculistico.adapter.Appointment
import com.example.centro_oculistico.adapter.AppointmentToDelete
import com.example.centro_oculistico.databinding.UpdateAppointmentDialogBinding
import org.json.JSONObject


class UpdateAppointmentDialog() : DialogFragment() {
    private var _binding: UpdateAppointmentDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var editor : SharedPreferences.Editor
    private var message : String? =null
     var context1 : Context? =null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = UpdateAppointmentDialogBinding.inflate(LayoutInflater.from(context))
        val arguments = object {
            val cdf= arguments?.get("cdf").toString()
            val doctorId= arguments?.get("doctorId").toString()
            val branchId= arguments?.get("branchId").toString()
            val day= arguments?.get("day").toString()
            val timeSlot= arguments?.get("timeSlot").toString()
            val appointmentId= arguments?.get("appointmentId").toString()
        }
        val pref = requireActivity().getSharedPreferences("userDataPreferences", Context.MODE_PRIVATE)
        editor=pref.edit()
        binding.timeSlotUpdate.setIs24HourView(true)
        binding.timeSlotUpdate.hour=arguments.timeSlot.substring(0,2).toInt()
        binding.timeSlotUpdate.minute=arguments.timeSlot.substring(3,5).toInt()
        binding.datePickerUpdate1.updateDate(arguments.day.substring(0,4).toInt(),arguments.day.substring(5,7).toInt(),arguments.day.substring(8,10).toInt())
        val doctorsList = binding.spinnerUpdateDoctor
        val branchesList = binding.spinnerUpdateBranches
        var branches: MutableList<String> = mutableListOf()
        val doctors: MutableList<String> = mutableListOf()
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
                val adapter1 = context?.let { ArrayAdapter(it, R.layout.spinner_item_small, branches) }
                if (adapter1 != null) {
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    branchesList.adapter = adapter1
                    adapter1.notifyDataSetChanged();
                    branchesList.setSelection(0)

                    val sizeBranch =adapter1.count
                    for (j in 0..sizeBranch){
                        if (adapter1.getItem(j).toString().contains(arguments.branchId)){
                            binding.spinnerUpdateBranches.setSelection(j)
                            break
                        }
                    }
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

        val jsonArrayRequest1 = JsonArrayRequest(
            Request.Method.GET, url1, null,
            { response ->
                for (i in 0 until response.length()) {
                    val item = response.getJSONObject(i) as JSONObject
                    val itemDoctor =
                        "Dr. " + item.get("name").toString() + " " + item.get("surname").toString()+" ["+item.get("doctorId")+"]"
                    doctors.add(itemDoctor)
                    // Your code here
                }
                val adapter = context?.let {
                    ArrayAdapter(it, R.layout.spinner_item_small, doctors)
                }
                if (adapter != null) {
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    doctorsList.adapter = adapter
                    adapter.notifyDataSetChanged();
                    doctorsList.setSelection(0)

                val sizeDoc =adapter.count
                for (i in 0..sizeDoc){
                    if (adapter.getItem(i).toString().contains(arguments.doctorId)){
                        binding.spinnerUpdateDoctor.setSelection(i)
                        break
                    }
                }}

            },
            { error ->
                // TODO: Handle error
            }
        )
        // Add the request to the RequestQueue.
        queue1.add(jsonArrayRequest1)

        val dialog=AlertDialog.Builder(requireActivity())
            // Add action buttons
            .setView(binding.root)
            .setPositiveButton("Modifica",
                DialogInterface.OnClickListener { dialog, id ->
                    var date = binding.datePickerUpdate1.year.toString() + "-"
                    if (binding.datePickerUpdate1.month.toString().length == 1)
                        date += "0" + binding.datePickerUpdate1.month.toString() + "-"
                    else
                        date += binding.datePickerUpdate1.month.toString() + "-"
                    if (binding.datePickerUpdate1.dayOfMonth.toString().length == 1) {
                        date += "0" + binding.datePickerUpdate1.dayOfMonth.toString()
                    } else {
                        date += binding.datePickerUpdate1.dayOfMonth.toString()
                    }
                    var timeSlot: String
                    if (binding.timeSlotUpdate.hour.toString().length == 1)
                        timeSlot = "0" + binding.timeSlotUpdate.hour.toString() + ":"
                    else
                        timeSlot = binding.timeSlotUpdate.hour.toString() + ":"
                    if (binding.timeSlotUpdate.minute.toString().length == 1)
                        timeSlot += "0" + binding.timeSlotUpdate.minute.toString() + ":00"
                    else
                        timeSlot +=binding.timeSlotUpdate.minute.toString() + ":00"
                    val appointmentToUpdate = Appointment(
                        cdf = arguments.cdf,
                        doctorId = doctorsList.selectedItem.toString().takeLast(8).replace("[","").replace("]",""),
                        branchId = branchesList.selectedItem.toString().takeLast(3).replace("[","").replace("]",""),
                        day = date,
                        timeSlot = timeSlot,
                        appointmentId = arguments.appointmentId
                    )
                    val apiService = RestApiService()
                    apiService.updateAppointment(appointmentToUpdate) {
                        if (it!=null && it.message!=null){
                            if ( it.message.toString() != "") {
                                this.message=it.message.toString()
                                Toast.makeText(context1,message,Toast.LENGTH_SHORT).show()
                            } else {
                                this.message=it.message.toString()
                                Toast.makeText(context1,message,Toast.LENGTH_SHORT).show()

                            }
                        }
                    }
                })
            .setNeutralButton("Cancella",
                DialogInterface.OnClickListener { dialog, id ->
                    val apiService = RestApiService()
                    val appointmentToDelete = AppointmentToDelete(
                        cdf = arguments.cdf,
                        appointmentId = arguments.appointmentId
                    )
                    apiService.deleteAppointment(appointmentToDelete) {
                        if (it?.message != null){
                            if ( it.message.toString() != "") {
                                this.message=it.message.toString()
                                Toast.makeText(context1,message,Toast.LENGTH_SHORT).show()

                            } else {
                                this.message=it.message.toString()
                                Toast.makeText(context1,message,Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                })
            .setNegativeButton("Annulla",
                DialogInterface.OnClickListener { dialog, id ->
                    getDialog()?.cancel()
                })
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(resources.getColor(R.color.red,null))
        }
        return dialog
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        (activity as MainActivity?)?.updateUserProfile("this.message!!")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}