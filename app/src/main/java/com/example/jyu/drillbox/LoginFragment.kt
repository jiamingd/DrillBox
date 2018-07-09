package com.example.jyu.drillbox

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatEditText
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AutoCompleteTextView
import android.widget.TextView
import com.example.jyu.drillbox.api.ProfileApi

import retrofit2.Retrofit;

import kotlinx.android.synthetic.main.login.*
class LoginFragment() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email_sign_in_button.setOnClickListener{it ->
            val p = password as AppCompatEditText
            val password = p.text.toString()
            val ev = email as AutoCompleteTextView
            Log.i("eeeeeeeeeeeeeeemail", ev.text.toString())
            val email = ev.text.toString()

            //TODO 10: api client requst/response using retrofit2
            val token = ProfileApi(this.activity!!.applicationContext).login(email, password)


        }

    }

    companion object {

        fun screenBuilder(): ScreenBuilder{
            return ScreenBuilder( LoginFragment::class.java )
        }
    }

    /**
     * Creates an instance of Retrofit Client.
     */
//    fun getRetrofit(context: Context): Retrofit {
//        return Retrofit.Builder()
//                .baseUrl(SharedConfig.getApiHost(context) + "/v3/")
//                .client(HttpClientFactory.getEventbriteApiHttpClient(context))
//                .addConverterFactory(GsonConverterFactory.create(gson()))
//                .addCallAdapterFactory(PaginatedCallAdapter.Factory())
//                .addCallAdapterFactory(SimpleCallAdapter.Factory())
//                .build()
//    }

}
