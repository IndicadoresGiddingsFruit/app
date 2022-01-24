package com.example.indicadoresapp.ApiService

import java.text.SimpleDateFormat

class Constants {
    companion object{
        const val BaseUrl="https://giddingsfruit.mx/ApiIndicadores/api/"
        val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("yyyy-MM-dd")
    }
}