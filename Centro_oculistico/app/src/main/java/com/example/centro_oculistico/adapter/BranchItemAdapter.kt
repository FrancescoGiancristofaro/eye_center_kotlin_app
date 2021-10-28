package com.example.centro_oculistico.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.centro_oculistico.R
import com.example.centro_oculistico.ui.gallery.GalleryFragmentDirections

class BranchItemAdapter(private val context: Context?, private val branchData : MutableList<Branch>) : RecyclerView.Adapter<BranchItemAdapter.BranchItemViewHolder>() {
    class BranchItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val textViewCity: TextView = view.findViewById(R.id.branchCity)
        val textViewAddress: TextView = view.findViewById(R.id.branchAddress)
        val textViewSwitchboard: TextView = view.findViewById(R.id.branchSwitchboard)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchItemAdapter.BranchItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_branch, parent, false)
        return BranchItemAdapter.BranchItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: BranchItemAdapter.BranchItemViewHolder, position: Int) {
        val item = branchData[position]
        holder.textViewCity.text= item.city
        holder.textViewAddress.text = item.address
        holder.textViewSwitchboard.text = item.switchboardNumber

    }

    override fun getItemCount(): Int {
        return branchData.size
    }
}