package com.example.jyu.drillbox

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DefaultFragment() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.default_fragment, container, false)
    }

    companion object {

        fun screenBuilder(): ScreenBuilder{
            return ScreenBuilder( DefaultFragment::class.java )
        }
    }

}
