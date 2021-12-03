package com.app.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.app.demo.model.APIResponse
import com.app.demo.model.Data
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MaimnListActivity : AppCompatActivity() {
    companion object CompanionObject {

        var list = mutableListOf<Data>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        InsertNotification()
    }
    private fun InsertNotification() {
        try{


            var url =  "https://api.mystral.in/tt/mobile/logistics/searchTrucks?auth-company=PCH&companyId=33&deactivated=false&key=g2qb5jvucg7j8skpu5q7ria0mu&q-expand=true&q-include=lastRunningState,lastWaypoint"

            AndroidNetworking.post(url)
                .addJSONObjectBody(null)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        //  progressDialog?.dismiss()

                        val response =
                            Gson().fromJson(response.toString(), APIResponse::class.java)
                        if (response!=null){
                            if (response.responseCode.responseCode== 200 ) {
                                if ( response.data.size>0 ) {
                                    list.addAll(response.data.toMutableList())
                                    rvMain.apply {
                                        layoutManager =
                                            LinearLayoutManager(this@MaimnListActivity, RecyclerView.VERTICAL, false)
                                        adapter = ListAdapter(response.data.toMutableList(),this@MaimnListActivity)
                                    }
                                    rvMain.visibility = View.VISIBLE



                                }

                            }
                        } else {
                            // toast("No Network, Check your connection")
                        }




                    }

                    override fun onError(error: ANError?) {

                       Log.v("Error: ","Error: " + error?.localizedMessage)
                    }
                })
        }catch (e : java.lang.Exception){}

    }
}