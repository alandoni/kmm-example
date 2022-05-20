package com.example.kmmexample.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kmmexample.data.models.RocketLaunch

class RocketLaunchesAdapter: RecyclerView.Adapter<RocketLaunchesAdapter.RocketViewHolder>() {

    var items = emptyList<RocketLaunch>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item, parent, false)
        return RocketViewHolder(view)
    }

    override fun onBindViewHolder(holder: RocketViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    class RocketViewHolder(private val parent: View): RecyclerView.ViewHolder(parent) {
        fun bind(item: RocketLaunch) {
            val name = parent.findViewById<TextView>(R.id.name)
            name.text = item.missionName
        }
    }
}
