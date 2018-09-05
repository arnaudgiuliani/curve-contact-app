package com.imaginecurve.curvecontactsapp.view.splash

import android.Manifest
import android.arch.lifecycle.Observer
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import com.imaginecurve.curvecontactsapp.APP_TAG
import com.imaginecurve.curvecontactsapp.R
import com.imaginecurve.curvecontactsapp.util.mvvm.FailedEvent
import com.imaginecurve.curvecontactsapp.util.mvvm.LoadingEvent
import com.imaginecurve.curvecontactsapp.util.mvvm.SuccessEvent
import com.imaginecurve.curvecontactsapp.view.list.ListActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.events.observe(this, Observer { event ->
            event?.let {
                when (event) {
                    is LoadingEvent -> showIsLoading()
                    is SuccessEvent -> showSucceed()
                    is FailedEvent -> showError(event.error)
                }
            }
        })

        // Ugly block to check permission
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_CONTACTS
                )
            ) {
                // Should Show an explanation to the user *asynchronously*
                splash_text.text = getString(R.string.permission_request)
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    readContactPermission
                )
            }
        } else {
            // Permission has already been granted
            viewModel.loadAllContacts()
        }
    }

    var readContactPermission: Int = 0

    // Intercept permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            readContactPermission -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted
                    viewModel.loadAllContacts()
                } else {
                    // permission denied
                }
                return
            }

            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun showSucceed() {
        splash_text.animation.cancel()
        splash_text.visibility = View.INVISIBLE
        Log.i(APP_TAG, "loading succeed!")

        startActivity(intentFor<ListActivity>().clearTop().clearTask().newTask())
    }

    private fun showIsLoading() {
        val animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.infinite_blinking_animation)
        splash_text.startAnimation(animation)

        splash_text.visibility = View.VISIBLE
        splash_text.text = getString(R.string.currently_loading)
    }

    private fun showError(error: Throwable) {
        splash_text.animation.cancel()
        splash_text.visibility = View.VISIBLE
        splash_text.text = getString(R.string.currently_failed)
        Log.e(APP_TAG, "loading failed: $error")
    }
}