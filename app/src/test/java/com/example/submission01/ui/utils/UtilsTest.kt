package com.example.submission01.ui.utils

import junit.framework.TestCase
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class UtilsTest : TestCase() {

    fun testToSimpleString() {
        val dateFormat: DateFormat = SimpleDateFormat("MM/dd/yyyy")
        var date: Date? = null
        try {
            date = dateFormat.parse("02/28/2018")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        date?.let {
            assertEquals("Wed, 28 Feb 2018", Utils.toSimpleString(date))
        }
    }
}