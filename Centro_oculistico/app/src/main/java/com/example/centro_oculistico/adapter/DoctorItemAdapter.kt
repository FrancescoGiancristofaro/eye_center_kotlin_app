package com.example.centro_oculistico.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.centro_oculistico.R
import com.example.centro_oculistico.ui.gallery.GalleryFragmentDirections


class DoctorItemAdapter (private val context: Context?, private val doctorData : MutableList<Doctor>, private val fragmentManager: FragmentManager): RecyclerView.Adapter<DoctorItemAdapter.DoctorItemViewHolder>(){

        class DoctorItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
            val textViewName: TextView = view.findViewById(R.id.doctorName)
            val textViewSurname: TextView = view.findViewById(R.id.doctorSurname)
            val textViewEmail: TextView = view.findViewById(R.id.doctorEmail)
            val textViewPhone: TextView = view.findViewById(R.id.doctorPhone)
            val textViewBranch: TextView = view.findViewById(R.id.doctorBranch)

            val button : ImageButton = view.findViewById(R.id.imageButton4)




        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_doctor, parent, false)
        return DoctorItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: DoctorItemViewHolder, position: Int) {
        val item = doctorData[position]
        holder.textViewName.text= item.name
        holder.textViewSurname.text = item.surname
        holder.textViewEmail.text = item.email
        holder.textViewPhone.text = item.phoneNumber
        holder.textViewBranch.text = item.headOffice
        holder.button.setOnClickListener{
            val action=GalleryFragmentDirections.actionNavGalleryToDoctorCalendarFragment()
            action.doctorId=item.doctorId
            action.doctorName=item.name+" "+item.surname
            it.findNavController().navigate(action)

        }

    }

    override fun getItemCount(): Int {
        return doctorData.size
    }
}