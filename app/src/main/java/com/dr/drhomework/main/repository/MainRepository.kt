package com.dr.drhomework.main.repository

import com.dr.drhomework.network.model.RpSearchResult

import io.reactivex.Single

interface MainRepository {

    /**
     * 블로그 검색
     */
    fun getBlogSearch(query: String, page: Int = 1): Single<RpSearchResult>

    /**
     * 카페 검색
     */
    fun getCafeSearch(query: String, page: Int = 1): Single<RpSearchResult>

}