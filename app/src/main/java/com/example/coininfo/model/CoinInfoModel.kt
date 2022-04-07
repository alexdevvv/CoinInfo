package com.example.coininfo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinInfoModel(
    @SerializedName("Name")
    @Expose
    val name: String? = null
)