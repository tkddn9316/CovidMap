package app.map.covid.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CentersApi(
    @SerializedName("page")
    @Expose
    val page: Int,
    @SerializedName("perPage")
    @Expose
    val perPage: Int,
    @SerializedName("totalCount")
    @Expose
    val totalCount: Int,
    @SerializedName("currentCount")
    @Expose
    val currentCount: Int,
    @SerializedName("data")
    @Expose
    val centersModel: List<CentersModel>
)
