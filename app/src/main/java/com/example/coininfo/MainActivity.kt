package com.example.coininfo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.coininfo.data.api.ApiFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disposable.add(ApiFactory.apiService.getFullPriceList(fSyms = "ETH")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    Log.e("TOP_LIST", it.toString())
            }, {

            })
        )

    }


    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

}