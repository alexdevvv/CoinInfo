package com.example.coininfo.data.api

import androidx.room.PrimaryKey
import com.example.coininfo.model.CoinInfoListOfData
import com.example.coininfo.model.CoinInfoModel
import com.example.coininfo.model.CoinPriceInfoRawData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query (API_KEY) apiKey: String = "7d1f5f82a9a8ec0713197f4d646ad75f3819ebc3c3fc032d710b8d3a61d8de8e",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = "USD"
    ): Single<CoinInfoListOfData>

    @GET("pricemultifull")
    fun getFullPriceList(
        @Query (API_KEY) apiKey: String = "7d1f5f82a9a8ec0713197f4d646ad75f3819ebc3c3fc032d710b8d3a61d8de8e",
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = "USD",

    ): Single<CoinPriceInfoRawData>

    companion object{
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL= "tsym"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val API_KEY= "api_key"
    }


}