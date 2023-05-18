package com.example.chatkgb.model

data class Message(
    val sender:String,
    val rec:String,
    val text:String,
    val hr:Long,
    var isrec:Boolean=true
){
    constructor(): this("","","",0)
}
