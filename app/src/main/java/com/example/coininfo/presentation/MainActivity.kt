package com.example.coininfo.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.coininfo.R


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(CoinViewModel::class.java)
//        bindLiveData()
        getInfoOneCoin()

    }


    private fun getInfoOneCoin(){
        viewModel.getDetailInfo("BTC").observe(this, Observer {
            Log.e("Info to BTC", it.lastVolume.toString())
        })
    }

    private fun bindLiveData(){
        viewModel.priceListLivedata.observe(this, Observer {
            Log.e("!!!!XXXXX!!!!", "very well")
        })
    }



}