package edu.uc.jonesbr.myplantdiary.dto

import com.google.gson.annotations.SerializedName

data class Plant(var genus: String, var species: String, var common: String, @SerializedName("id") var plantId: Int = 0) {
    // Ref: https://github.com/discospiff/MyPlantDiaryQ/blob/master/app/src/main/java/edu/uc/jonesbr/myplantdiary/dto/Plant.kt
    override fun toString(): String {
        return common
    }
}
