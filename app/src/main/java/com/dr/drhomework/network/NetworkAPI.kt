package com.dr.drhomework.network.retrofit

import com.dr.drhomework.network.model.RpSearchResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkAPI {

    companion object {
        private val SORT_ACCURACY = "accuracy"
        private val PAGING_SIZE_25 = 25
    }

    /**
     * 블로그 검색
     */
    @GET(V2_SEARCH_BLOG)
    fun getBlog(
        @Query(value = "query", encoded = true) query: String?,
        @Query("sort") sort: String = SORT_ACCURACY,
        @Query("page") page: Int?,
        @Query("size") size: Int = PAGING_SIZE_25
    ): Single<RpSearchResult>

    /**
     * 카페 검색
     */
    @GET(V2_SEARCH_CAFE)
    fun getCafe(
        @Query(value = "query", encoded = true) query: String?,
        @Query("sort") sort: String = SORT_ACCURACY,
        @Query("page") page: Int?,
        @Query("size") size: Int = PAGING_SIZE_25
    ): Single<RpSearchResult>

}