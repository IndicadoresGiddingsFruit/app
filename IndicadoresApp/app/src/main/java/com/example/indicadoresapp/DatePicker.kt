package com.example.indicadoresapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.time.LocalDate
import java.util.*

class DatePicker(val listener:(year:Int,month:Int,day:Int) -> Unit):DialogFragment(),DatePickerDialog.OnDateSetListener
{
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year : Int = c.get(Calendar.YEAR)
        val mont : Int = c.get(Calendar.MONTH)
        val day : Int = c.get(Calendar.DAY_OF_MONTH)

        val picker=DatePickerDialog(activity as Context,this,year,mont,day)//, R.style.datePickerTheme
        return picker
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
       listener(year,month+1,dayOfMonth)
    }
}