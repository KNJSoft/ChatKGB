package com.example.chatkgb.model

data class User(
    var uuid: String,
    val email: String,
    val nom: String,
    val image: String
) {
    constructor(): this("","","","")
}
