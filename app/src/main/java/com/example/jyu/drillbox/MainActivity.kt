package com.example.jyu.drillbox

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    val FRAGMENT_STACK = "fragment_class"

    /** Called when the activity is first created.  */
    public override fun onCreate(savedInstanceState: Bundle?) {
        decorateIntent4DefaultFragment()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.experiment_main)

        val btnLoad = findViewById<View>(R.id.btn_load) as Button

        openStackFromIntent()

        btnLoad.setOnClickListener {
            val hello = HelloFragment()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, hello, "HELLO")
                    .addToBackStack(null)
                    .commit()
        }
    }

    private fun openStackFromIntent() {
        val mScreenBuilder = intent.extras[FRAGMENT_STACK] as? ScreenBuilder
        val newFragment = mScreenBuilder?.createFragment()

        if (newFragment != null) {
            //TODO: A. should call sb.replace, but ?? context ?
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, newFragment, "HELLO")
                    .commit()
        }
    }

    private fun decorateIntent4DefaultFragment() {
        intent.putExtra(FRAGMENT_STACK, defaultScreen())
    }

    private fun defaultScreen(): ScreenBuilder {
//        return DefaultFragment.screenBuilder()
        return LoginFragment.screenBuilder()
    }


}


