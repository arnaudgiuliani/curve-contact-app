package com.imaginecurve.curvecontactsapp.view.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.imaginecurve.curvecontactsapp.R
import com.imaginecurve.curvecontactsapp.util.android.getColor

class ItemListAdapter(
    var list: List<ItemUIModel>,
    val context: Context,
    private val onDetailSelected: (ItemUIModel, Int) -> Unit
) : RecyclerView.Adapter<ItemListAdapter.ItemUIHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemUIHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ItemUIHolder(view)
    }

    override fun onBindViewHolder(holder: ItemUIHolder, position: Int) {
        holder.display(list[position], position)
    }

    override fun getItemCount() = list.size

    inner class ItemUIHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val layout = item.findViewById<LinearLayout>(R.id.item)
        private val name = item.findViewById<TextView>(R.id.item_name)

        fun display(
            item: ItemUIModel,
            index: Int
        ) {
            name.text = item.name

            // Color background
            val color = getColor(item.getColorIndex(index), context)
            layout.setBackgroundColor(color)
            layout.setOnClickListener { onDetailSelected(item, color) }
        }

    }
}