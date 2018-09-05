package com.imaginecurve.curvecontactsapp.view.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.imaginecurve.curvecontactsapp.R

class ItemListAdapter(
    val context: Context,
    var list: List<ItemUIModel>,
    private val onDetailSelected: (ItemUIModel) -> Unit
) : RecyclerView.Adapter<ItemListAdapter.ItemUIHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemUIHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ItemUIHolder(view)
    }

    override fun onBindViewHolder(holder: ItemUIHolder, position: Int) {
        holder.display(list[position], context, onDetailSelected)
    }

    override fun getItemCount() = list.size

    inner class ItemUIHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val layout = item.findViewById<LinearLayout>(R.id.item)
        private val name = item.findViewById<TextView>(R.id.item_name)

        fun display(
            item: ItemUIModel,
            context: Context,
            onClick: (ItemUIModel) -> Unit
        ) {
            layout.setOnClickListener { onClick(item) }
            //TODO color background
            name.text = item.name

//            weatherItemLayout.setOnClickListener { onClick(dailyForecastModel) }
//            weatherItemDay.text = dailyForecastModel.day
//            weatherItemIcon.text = dailyForecastModel.icon
//            val color = context.getColorFromCode(dailyForecastModel)
//            weatherItemDay.setTextColor(color)
//            weatherItemIcon.setTextColor(color)
        }

    }
}