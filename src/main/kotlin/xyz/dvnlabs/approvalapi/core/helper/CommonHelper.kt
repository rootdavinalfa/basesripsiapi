/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.core.helper

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream


open class CommonHelper {

    companion object {
        fun getStringSeq(
            lastSeq: String, prefix: String, midfix: String, zeroFill: Int
        ): String {
            val max = lastSeq.substring(lastSeq.length - zeroFill).toLong()

            return prefix + midfix + String.format("%0" + zeroFill + "d", max + 1)
        }

        fun convertDateToStringWithPattern(date: Date?, pattern: String?): String? {
            var pattern = pattern
            if (pattern == null) pattern = "yyyy-MM-dd"
            val sdf: DateFormat = SimpleDateFormat(pattern)
            return sdf.format(date)
        }


        fun monthToAlfabet(date: Date?): String {
            val alfaMonth = Stream.of(
                arrayOf("01", "A"),
                arrayOf("02", "B"),
                arrayOf("03", "C"),
                arrayOf("04", "D"),
                arrayOf("05", "E"),
                arrayOf("06", "F"),
                arrayOf("07", "G"),
                arrayOf("08", "H"),
                arrayOf("09", "I"),
                arrayOf("10", "J"),
                arrayOf("11", "K"),
                arrayOf("12", "L")
            ).collect(
                Collectors.toMap(
                    { strings: Array<String> ->
                        strings[0]
                    },
                    { strings: Array<String> ->
                        strings[1]
                    })
            )
            val formatter = SimpleDateFormat("MM")
            return alfaMonth[formatter.format(date)] ?: "XX"
        }

        fun getStringSeq(
            lastSeq: String, date: Date?, prefix: String, midfix: String, zeroFill: Int,
            withAlfaMonth: Boolean, dateFormat: String?
        ): String {
            var strAlfaMonth = ""
            if (withAlfaMonth) strAlfaMonth = monthToAlfabet(date)
            val max = lastSeq.substring(lastSeq.length - zeroFill).toLong()
            var dateStr = ""
            if (dateFormat != null) {
                val formatter = SimpleDateFormat(dateFormat)
                dateStr = formatter.format(date)
            }
            return prefix + dateStr + strAlfaMonth + midfix + String.format("%0" + zeroFill + "d", max + 1)
        }

        fun getCurrentDate(): Date? {
            val calendar: Calendar = Calendar.getInstance()
            return calendar.time
        }


    }

}