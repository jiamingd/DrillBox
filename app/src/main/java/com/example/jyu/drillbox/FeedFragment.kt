package com.example.jyu.drillbox

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class FeedFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater!!.inflate(R.layout.feed_fragment, null)
        return view
    }

    companion object {
        @JvmStatic
        fun screenBuilder(foo: String) = FeedFragment().apply {
            arguments = Bundle().apply {
                putString("foo", foo)
            }
        }
    }
}
