
package com.dr.drhomework.network.model

import com.dr.drhomework.util.TimeUtils
import java.io.Serializable

data class RpSearchResult(
    val documents: List<Document> = listOf(),
    val meta: Meta? = Meta()
) : Serializable {

    enum class SearchType(val value: String) {
        BLOG("B"),
        CAFE("C")
    }

    data class Document(
        val blogname: String? = "",
        val cafename: String? = "",
        val contents: String? = "",
        val datetime: String? = "",
        val thumbnail: String? = "",
        val title: String? = "",
        val url: String? = "",
        var searchType: SearchType? = null,
        var isOpened: Boolean = false
    ) : Serializable {

        fun getName(): String? =
            when (searchType) {
                SearchType.BLOG -> blogname
                SearchType.CAFE -> cafename
                null -> ""
            }

        /**
         * 오늘
         * 어제
         * 그외 -> 2020년 06월 17일
         */
        fun getShortDateTime(): String? =
            TimeUtils.convertTimeYearMonthDay(datetime)

        /**
         * 2019년 03월 14일 오전 11시 48분
         */
        fun getLongDateTime(): String? =
            TimeUtils.convertTime(datetime)

    }

    data class Meta(
        val is_end: Boolean? = false,
        val pageable_count: Int? = 0,
        val total_count: Int? = 0
    ) : Serializable

}

