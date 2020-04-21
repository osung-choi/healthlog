package com.example.healthlog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.healthlog.model.database.entitiy.OneSet
import com.example.healthlog.databinding.AdapterSetBinding

class SetAdapter(setList: ArrayList<OneSet>): RecyclerView.Adapter<SetAdapter.ViewHolder>() {
    private val setList = setList

    private fun defaultSetItem(weight: Int = 0, count: Int = 0)
            = OneSet(0, 0, setList.size + 1, weight, count)

    fun addItem() {
        if(setList.size > 0) {
            val lastWeight = setList.last().weight
            val lastCount = setList.last().count

            setList.add(defaultSetItem(lastWeight, lastCount))
        } else {
            setList.add(defaultSetItem())
        }

        notifyItemInserted(setList.size - 1)
    }

    fun removeLastItem() {
        val index = setList.size-1
        setList.removeAt(index)
        notifyItemRemoved(index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ViewHolder(
        AdapterSetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
    override fun getItemCount() = setList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(setList[position])


    class ViewHolder(binding: AdapterSetBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding

        fun bind(item: OneSet) {
            binding.setCount.text = "${adapterPosition + 1} 세트"
            binding.editSetWeight.setText(item.weight.toString())
            binding.editSetCount.setText(item.count.toString())

            binding.editSetWeight.addTextChangedListener {
                it?.let {
                    if(!it.isEmpty()) item.weight = it.toString().toInt()
                }
            }

            binding.editSetCount.addTextChangedListener {
                it?.let {
                    if(!it.isEmpty()) item.count = it.toString().toInt()
                }
            }
        }
    }

}