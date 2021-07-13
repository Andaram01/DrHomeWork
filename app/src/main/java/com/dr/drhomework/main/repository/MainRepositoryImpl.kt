package com.dr.drhomework.main.repository


import com.dr.drhomework.network.model.RpSearchResult
import com.dr.drhomework.network.retrofit.NetworkManager
import io.reactivex.Single

class MainRepositoryImpl : MainRepository {

    override fun getBlogSearch(query: String, page: Int): Single<RpSearchResult> =
        NetworkManager.getInstance().getBlogSearch(query, page)

    override fun getCafeSearch(query: String, page: Int): Single<RpSearchResult> =
        NetworkManager.getInstance().getCafeSearch(query, page)


}