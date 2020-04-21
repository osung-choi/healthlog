package com.example.healthlog.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.example.healthlog.R

@Deprecated("모든 단어 검색 사용 중지")
class SearchAdapter(val mContext: Context, val resource: Int, val list: MutableList<String>) : ArrayAdapter<String>(mContext, resource, list), Filterable {
//    interface SearchClickListener {
//        fun onClick(name: String)
//    }

    var callback : ((String) -> Unit)? = null
    override fun getCount(): Int = list.size
    override fun getItem(position: Int): String? = list[position]
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_search_auto_complete, null, false)
        val textView: TextView = view.findViewById(R.id.autoCompleteItem)
        textView.text = list[position]
//
//        view.setOnClickListener {
//            callback?.let { it(list[position]) }
//        }
        return view
    }
    override fun getFilter() = mfilter

    private var mfilter: Filter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            val results = FilterResults()

            val query = if (constraint != null && constraint.isNotEmpty()) autocomplete(constraint.toString())
            else arrayListOf()

            results.values = query
            results.count = query.size

            return results
        }


        private fun autocomplete(input: String): MutableList<String> {
            val results = arrayListOf<String>()

            for (i in list) {
                results.add(i)
            }

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults) {
            if (results.count > 0) notifyDataSetChanged()
            else notifyDataSetInvalidated()
        }
    }


    fun getObject(position: Int) = list[position]

    fun setData(changedList: List<String>) {
        list.clear()
        list.addAll(changedList)
    }
//
//    fun setOnSearchClickListener(callback: SearchClickListener) {
//        this.callback = callback
//    }
}