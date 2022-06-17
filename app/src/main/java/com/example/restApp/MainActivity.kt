package com.example.restApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL="https://jsonplaceholder.typicode.com/"
class MainActivity : AppCompatActivity() {
    lateinit var myAdapter: MyAdapter
    lateinit var linerLayoutManager:LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview_users.setHasFixedSize(true)
        linerLayoutManager= LinearLayoutManager(this)
        recyclerview_users.layoutManager = linerLayoutManager

        getMyData();


    }

    private fun getMyData() {

        val retrofitBuilder= Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
            BASE_URL).build().create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()
        retrofitData.enqueue(object : Callback<List<myData>?> {
            override fun onResponse(
                call: Call<List<myData>?>,
                response: Response<List<myData>?>
            ) {
                val responseBody = response.body()!!

               myAdapter = MyAdapter(baseContext,responseBody)
                myAdapter.notifyDataSetChanged()
                recyclerview_users.adapter=myAdapter
            }

            override fun onFailure(call: Call<List<myData>?>, t: Throwable) {
                d("MainActivity", "onFailure: "+t.message)

            }
        })
    }
}