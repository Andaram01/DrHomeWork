package com.dr.drhomework.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

@SuppressLint("SimpleDateFormat")
object TimeUtils {

    const val FORMAT_YEAR_MONTH_DAY: String = "yyyy년 MM월 dd일"
    const val FORMAT_DATE: String = "yyyy년 MM월 dd일 a hh시 mm분"


    fun getCurrentCalendar(): Calendar = Calendar.getInstance(TimeZone.getDefault())
    fun getCurrentTime(): Long = getCurrentCalendar().timeInMillis

    fun getTimeDayDiff(
        fromDate: Long,
        toDate: Long = getCurrentTime()
    ): Int {
        return (toDate - fromDate)
            .div(24 * 60 * 60 * 1000)
            .toInt()
    }

    /**
     * convertTime = 2019년 03월 14일 오전 11시 48분
     */
    fun convertTime(time: String?): String {
        if (time.isNullOrEmpty()) return ""
        return try {
            val fromDate: Date = parseDate(time) ?: return ""
            SimpleDateFormat(FORMAT_DATE, Locale.KOREA).format(fromDate)
        } catch (e: IllegalArgumentException) {
            ""
        }
    }

    /**
     * convertTime = 2020년 06월 17일
     */
    fun convertTimeYearMonthDay(time: String?): String {
        if (time.isNullOrEmpty()) return ""
        return try {
            val fromDate: Date = parseDate(time) ?: return ""


            when (getTimeDayDiff(fromDate = fromDate.time)) {
                0 -> "오늘"
                1 -> "어제"
                else -> SimpleDateFormat(FORMAT_YEAR_MONTH_DAY).format(fromDate)
            }
        } catch (e: IllegalArgumentException) {
            ""
        }
    }


    /**
     *
     * 문자열 (날짜) => SimpleDateFormat
     * yyyy-MM-dd HH:mm:ss => yyyy-MM-dd HH:mm:ss
     * yyyy-MM-dd HH:mm:ss.SSS => yyyy-MM-dd HH:mm:ss.SSS
     *
     * yyyy-MM-dd HH:mm:ssZ => yyyy-MM-dd HH:mm:ssX
     * yyyy-MM-dd HH:mm:ss+09 => yyyy-MM-dd HH:mm:ssX
     * yyyy-MM-dd HH:mm:ss+0900 => yyyy-MM-dd HH:mm:ssX
     * yyyy-MM-dd HH:mm:ss+09:00 => yyyy-MM-dd HH:mm:ssXXX
     * yyyy-MM-dd HH:mm:ssKST => yyyy-MM-dd HH:mm:ssZ
     *
     * yyyy-MM-dd HH:mm:ss.SSSZ => yyyy-MM-dd HH:mm:ss.SSSX
     * yyyy-MM-dd HH:mm:ss.SSS+09 => yyyy-MM-dd HH:mm:ss.SSSX
     * yyyy-MM-dd HH:mm:ss.SSS+0900 => yyyy-MM-dd HH:mm:ss.SSSX
     * yyyy-MM-dd HH:mm:ss.SSS+09:00 => yyyy-MM-dd HH:mm:ss.SSSXXX
     * yyyy-MM-dd HH:mm:ss.SSSKST => yyyy-MM-dd HH:mm:ss.SSSZ
     *
     * yyyy-MM-ddTHH:mm:ssZ => yyyy-MM-dd'T'HH:mm:ssX
     * yyyy-MM-ddTHH:mm:ss+09 => yyyy-MM-dd'T'HH:mm:ssX
     * yyyy-MM-ddTHH:mm:ss+0900 => yyyy-MM-dd'T'HH:mm:ssX
     * yyyy-MM-ddTHH:mm:ss+09:00 => yyyy-MM-dd'T'HH:mm:ssX
     * yyyy-MM-ddTHH:mm:ssKST => yyyy-MM-dd'T'HH:mm:ssZ
     *
     * yyyy-MM-ddTHH:mm:ss.SSSZ => yyyy-MM-dd'T'HH:mm:ss.SSSX
     * yyyy-MM-ddTHH:mm:ss.SSS+09 => yyyy-MM-dd'T'HH:mm:ss.SSSX
     * yyyy-MM-ddTHH:mm:ss.SSS+0900 => yyyy-MM-dd'T'HH:mm:ss.SSSX
     * yyyy-MM-ddTHH:mm:ss.SSS+09:00 => yyyy-MM-dd'T'HH:mm:ss.SSSXXX
     * yyyy-MM-ddTHH:mm:ss.SSSKST => yyyy-MM-dd'T'HH:mm:ss.SSSZ
     */
    fun parseDate(dateString: String?): Date? {
        if (dateString.isNullOrEmpty()) {
            return null
        }
        val dateStringBuilder = StringBuilder()
        dateString.forEachIndexed { index, c ->
            when (index) {
                in 0..3 -> {
                    dateStringBuilder.append("y")
                }
                in 5..6 -> {
                    dateStringBuilder.append("M")
                }
                in 8..9 -> {
                    dateStringBuilder.append("d")
                }
                10 -> {
                    when (c) {
                        'T' -> {
                            dateStringBuilder.append("'T'")
                        }
                        ' ' -> {
                            dateStringBuilder.append(c)
                        }
                        else -> {
                            return null
                        }
                    }
                }
                in 11..12 -> {
                    dateStringBuilder.append("H")
                }
                in 14..15 -> {
                    dateStringBuilder.append("m")
                }
                in 17..18 -> {
                    dateStringBuilder.append("s")
                }
                4, 7, 13, 16 -> {
                    dateStringBuilder.append(c)
                }
            }
        }


        if (dateString.length >= 19) {
            // .SSS는 있을 수도 있고 없을 수도 있음, 없는 경우에는 19번째부터 timezone이고 있는 경우는 23번째부터 timezone
            val timezoneIndex = if (
                dateString.substring(19).length >= 4
                && Pattern.matches("[.]\\d{3}", dateString.substring(19, 23))
            ) {
                dateStringBuilder.append(".SSS")
                23
            } else {
                19
            }

            // Timezone을 요약해보면 Z, +09, +0900은 X로, +09:00은 XXX로, KST는 Z로
            val timezone = dateString.substring(timezoneIndex)
            when {
                timezone == "" -> {
                }
                timezone == "Z" -> {
                    dateStringBuilder.append("X")
                }
                Pattern.matches("[+|-]\\d{2}", timezone) -> {
                    dateStringBuilder.append("X")
                }
                Pattern.matches("[+|-]\\d{4}", timezone) -> {
                    dateStringBuilder.append("X")
                }
                Pattern.matches("[+|-]\\d{2}[:]\\d{2}", timezone) -> {
                    dateStringBuilder.append("XXX")
                }
                Pattern.matches("[A-Z]{3}", timezone) -> {
                    dateStringBuilder.append("Z")
                }
                else -> {
//                Wrong timezone
                    return null
                }
            }
        }
        val pattern = dateStringBuilder.toString()
        return try {
            SimpleDateFormat(pattern).parse(dateString)
        } catch (e: IllegalArgumentException) {
            null
        }
    }

}
