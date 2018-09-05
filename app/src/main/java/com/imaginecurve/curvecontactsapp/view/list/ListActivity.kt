package com.imaginecurve.curvecontactsapp.view.list

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.imaginecurve.curvecontactsapp.APP_TAG
import com.imaginecurve.curvecontactsapp.R
import com.imaginecurve.curvecontactsapp.util.mvvm.ErrorState
import com.imaginecurve.curvecontactsapp.view.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_list.*
import org.jetbrains.anko.startActivity
import org.koin.android.viewmodel.ext.android.viewModel

class ListActivity : AppCompatActivity() {

    val viewModel: ListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        prepareUIList()

        viewModel.states.observe(this, Observer { state ->
            when (state) {
                // skip loading state
                is ListViewModel.LoadedState -> showItems(state.list)
                is ErrorState -> showError(state.error)
            }
        })
        viewModel.getContacts()
    }

    private fun prepareUIList() {
        list_recycler.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        list_recycler.adapter = ItemListAdapter(emptyList(), this, ::onItemSelected)
    }

    private fun onItemSelected(item: ItemUIModel, color : Int) {
        println("color -> $color / $item ")
        startActivity<DetailActivity>(
            ARG_ITEM_ID to item.id,
            ARG_COLOR to color
        )
    }

    private fun showItems(newList: List<ItemUIModel>) {
        (list_recycler.adapter as ItemListAdapter).apply {
            list = newList
            notifyDataSetChanged()
        }
    }

    private fun showError(error: Throwable) {
        Log.e(APP_TAG, "ListViewModel loading error : $error")
        Toast.makeText(this,getString(R.string.list_failed),Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val ARG_ITEM_ID = "ITEM_ID"
        const val ARG_COLOR = "COLOR"
    }
}