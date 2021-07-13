
package com.dr.drhomework.network.retrofit

import com.dr.drhomework.network.model.RpSearchResult
import io.reactivex.Single

class NetworkManager : PreHomeworkRetrofit() {

    companion object {
        private var instance: NetworkManager? = null

        @Synchronized
        fun getInstance(): NetworkManager {
            if (instance == null) {
                instance = NetworkManager()
            }

            return instance!!
        }
    }


    /**
     * 블로그 검색
     * */
    fun getBlogSearch(query: String, page: Int = 1): Single<RpSearchResult> =
        getRequestRetrofit(NetworkAPI::class.java).getBlog(
            query = query,
            page = page
        )

    /**
     * 카페 검색
     * */
    fun getCafeSearch(query: String, page: Int = 1): Single<RpSearchResult> =
        getRequestRetrofit(NetworkAPI::class.java).getCafe(
            query = query,
            page = page
        )
}