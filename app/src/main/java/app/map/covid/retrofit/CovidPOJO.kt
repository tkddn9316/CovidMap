package app.map.covid.retrofit

import androidx.room.Entity
import androidx.room.PrimaryKey
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

@Entity(tableName = "CovidCenterTable")
data class CentersModel(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("centerName")
    @Expose
    val centerName: String,

    @SerializedName("sido")
    @Expose
    val sido: String,

    @SerializedName("sigungu")
    @Expose
    val sigungu: String,

    @SerializedName("facilityName")
    @Expose
    val facilityName: String,

    @SerializedName("zipCode")
    @Expose
    val zipCode: String,

    @SerializedName("address")
    @Expose
    val address: String,

    @SerializedName("lat")
    @Expose
    val lat: String,

    @SerializedName("lng")
    @Expose
    val lng: String,

    @SerializedName("createdAt")
    @Expose
    val createdAt: String,

    @SerializedName("updatedAt")
    @Expose
    val updatedAt: String,

    @SerializedName("centerType")
    @Expose
    val centerType: String,

    @SerializedName("org")
    @Expose
    val org: String,

    @SerializedName("phoneNumber")
    @Expose
    val phoneNumber: String
)