package com.example.chatkgb.model

data class User(
    var uuid: String,
    val email: String,
    var nom: String,
    var image: String?
) {
    constructor(): this("","","","")
}
