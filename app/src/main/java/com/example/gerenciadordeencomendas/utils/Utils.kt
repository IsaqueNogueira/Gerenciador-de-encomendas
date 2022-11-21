package com.example.gerenciadordeencomendas.utils

import java.text.SimpleDateFormat
import java.util.*

class Utils {

    fun dataHora(): String{
        val date = Calendar.getInstance().time
        var dateTimeFormat = SimpleDateFormat("d MMM yyyy HH:mm:ss", Locale("PT", "BR"))
        val  data = dateTimeFormat.format(date)
        return data
    }

    fun dataHoraMillisegundos(): Long {
        val cal = Calendar.getInstance()
        val dataMillesegundos =  cal.timeInMillis
        return dataMillesegundos
    }

}