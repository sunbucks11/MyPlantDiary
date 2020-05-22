package edu.uc.jonesbr.myplantdiary.dto

class Plant(var genus: String, var species : String, var common :String) {
    // Ref: https://github.com/discospiff/MyPlantDiaryQ/blob/master/app/src/main/java/edu/uc/jonesbr/myplantdiary/dto/Plant.kt
    override fun toString(): String {
        return common
    }
}
