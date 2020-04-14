package com.example.healthlog.log

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.healthlog.database.entitiy.OneSet
import com.example.healthlog.databinding.AdapterSetBinding

class SetAdapter(setList: ArrayList<OneSet>): RecyclerView.Adapter<SetAdapter.ViewHolder>() {
    private val setList = setList

    private fun defaultSetItem() = OneSet(0, 0, setList.size + 1, 0, 0)

    fun addItem() {
        if(setList.size > 0) setList.add(setList.get(setList.lastIndex)) else setList.add(defaultSetItem())
        notifyItemInserted(setList.size - 1)
    }

    fun removeLastItem() {
        val index = setList.size-1
        setList.removeAt(index)
        notifyItemRemoved(index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ViewHolder(AdapterSetBinding.inflate(LayoutInflater.from(parent.context), parent , false))
    override fun getItemCount() = setList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(setList[position])


    class ViewHolder(binding: AdapterSetBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding

        fun bind(item: OneSet) {
            binding.setCount.text = "${adapterPosition + 1} 세트"
        }
    }

}