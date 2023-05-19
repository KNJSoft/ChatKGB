package com.example.chatkgb.model

data class Amis(
    var uuid: String,
    val nom:String,
    val lastmeg:String,
    val heure: Long,
    val image:String,
) {
    constructor(): this("","","",0,"")
}
