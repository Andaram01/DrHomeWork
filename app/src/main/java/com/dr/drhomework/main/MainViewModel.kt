package com.dr.drhomework.main


import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dr.drhomework.R
import com.dr.drhomework.base.BaseActivity
import com.dr.drhomework.base.PCViewModel
import com.dr.drhomework.main.repository.MainRepository
import com.dr.drhomework.main.repository.MainRepositoryImpl
import com.dr.drhomework.network.model.RpSearchResult
import com.dr.drhomework.util.SingleLiveData
import com.dr.drhomework.util.extension.default
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.view.*


class MainViewModel(private val mainRepository: MainRepository =
                        MainRepositoryImpl(),
val int: Int = 0) : PCViewModel() {

    companion object {
        private const val PAGING_DEFAULT = 1
    }


    //최근 검색어
    var searchWord: String? = null

    //검색 될 페이지
    var searchPaging: Int = PAGING_DEFAULT

    //상품 리스트
    private val _contentListLiveData =
        MutableLiveData<ArrayList<RpSearchResult.Document>>().default(
            ArrayList()
        )
    val contentListLiveData: LiveData<ArrayList<RpSearchResult.Document>> get() = _contentListLiveData

    //로딩 다이얼로그 호출
    private val _callShowLoadingLiveData = SingleLiveData<Unit>()
    val callShowLoadingLiveData: LiveData<Unit> get() = _callShowLoadingLiveData

    //변수
    var _stringTemp = MutableLiveData<String>()
//    var stringTemp: LiveData<String> get() = _stringTemp

    var _stringTemp2 = ""

    override fun onClicking(view: View) {
        when (view.id){
            R.id.searchIv -> {
                searchWord = "daram"
                getBlogSearch()
            }
        }
    }

    /**
     * 블로그 데이터 호출
     */
    private fun getBlogSearch() {
        _callShowLoadingLiveData.call()
        //로딩바 호출
//        activity.showLoadingPopup()
        if (searchWord.isNullOrEmpty()) return
        mainRepository.getBlogSearch(
            query = searchWord!!,
            page = searchPaging
        ).flatMap { responseSearch ->
            responseSearch.documents.forEach {
                it.searchType = RpSearchResult.SearchType.BLOG
            }
            return@flatMap Single.just(responseSearch.documents)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<RpSearchResult.Document>>() {
                override fun onSuccess(result: List<RpSearchResult.Document>) {
                    if (searchPaging == 1) {
                        println("bindMainContentAdapterData 003")
                        ArrayList(result).get(0).run {
                            println("bindMainContentAdapterData 003")
//                            _stringTemp.postValue(title!!)
                        }
                        _contentListLiveData.postValue(ArrayList(result))
                    } else {
                        println("bindMainContentAdapterData 0031")
                        ArrayList(result).get(0).run {
                            println("bindMainContentAdapterData 0031")
//                            _stringTemp.postValue(title!!)
                        }
                        println("bindMainContentAdapterData 004")
                        _contentListLiveData.postValue(ArrayList(result))
//                        _contentPageListLiveData.postValue(ArrayList(result))
                    }
                    searchPaging++
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }
}