package com.cmdv.data.entity

import com.google.gson.annotations.SerializedName

data class GetCharactersResponseEntity(
    @SerializedName("code") val code: Int?,
    @SerializedName("status") val status: String?,
    @SerializedName("etag") val etag: String?,
    @SerializedName("data") val data: DataEntity?
) {
    data class DataEntity(
        @SerializedName("offset") val offset: Int?,
        @SerializedName("limit") val limit: Int?,
        @SerializedName("total") val total: Int?,
        @SerializedName("count") val count: Int?,
        @SerializedName("results") val results: ArrayList<CharacterEntity>?
    )
}