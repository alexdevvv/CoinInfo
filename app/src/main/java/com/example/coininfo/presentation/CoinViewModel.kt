package com.example.coininfo.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.coininfo.data.api.ApiFactory
import com.example.coininfo.data.db.AppDatabase
import com.example.coininfo.model.CoinPriceInfo
import com.example.coininfo.model.CoinPriceInfoRawData
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val disposable = CompositeDisposable()
    val priceListLivedata = db.coinPriceInfoDao().getPriceList()

    init {
        loadData()
    }

    private fun loadData() {
        disposable.add(
            ApiFactory.apiService.getTopCoinsInfo()
                .map { it.data?.map { it.coinInfo?.name }!!.joinToString(",") }
                .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it) }
                .map { getPriceListFromRowData(it) }
                .delaySubscription(10, TimeUnit.SECONDS) // Устанавливаем интервал 10 секунд
                .repeat() // Выполняет циклическую загрузку, если все идкт успешно
                .retry() // Выполняет циклическую загрузку, если происходит ошибка
                .subscribeOn(Schedulers.io())
                .subscribe({
                    db.coinPriceInfoDao().insertPriceList(it)
                    Log.e("loadData", "УСПЕШНО!")
                }, {
                    Log.e("loadData", "НЕ УСПЕШНО!")
                })
        )
    }

    private fun getPriceListFromRowData(
        coinPriceInfoRawData: CoinPriceInfoRawData
    ): List<CoinPriceInfo> {
        val rslList = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject
        if (jsonObject == null) {
            return rslList
        }
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java
                )
                rslList.add(priceInfo)
            }
        }
        return rslList
    }

    fun getDetailInfo(fSym: String): LiveData<CoinPriceInfo> {
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fsym = fSym)
    }


    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }


}