package com.imaginecurve.curvecontactsapp.view.detail

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.imaginecurve.curvecontactsapp.APP_TAG
import com.imaginecurve.curvecontactsapp.R
import com.imaginecurve.curvecontactsapp.util.ext.android.argument
import com.imaginecurve.curvecontactsapp.util.mvvm.ErrorState
import com.imaginecurve.curvecontactsapp.view.list.ListActivity.Companion.ARG_COLOR
import com.imaginecurve.curvecontactsapp.view.list.ListActivity.Companion.ARG_ITEM_ID
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val color by argument<Int>(ARG_COLOR)
    private val detailId by argument<String>(ARG_ITEM_ID)

    val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        detail.setBackgroundColor(color)
        detail.setOnClickListener { onBackPressed() }

        viewModel.states.observe(this, Observer { state ->
            when (state) {
                is DetailViewModel.LoadedState -> showDetail(state.detail)
                is ErrorState -> showError(state.error)
            }
        })
        viewModel.getDetail(detailId)
    }

    private fun showError(error: Throwable) {
        Log.e(APP_TAG, "DetailActivity loading error : $error")
        Toast.makeText(this,getString(R.string.detail_failed), Toast.LENGTH_SHORT).show()
    }

    private fun showDetail(detail: DetailUIModel) {
        detail_name_label.text = detail.name
        detail_email_label.text = detail.email
        detail_phone_label.text = detail.phone
    }
}