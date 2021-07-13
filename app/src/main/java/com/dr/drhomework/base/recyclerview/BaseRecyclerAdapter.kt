package com.dr.drhomework.base.recyclerview

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*


abstract class BaseRecyclerAdapter<M> : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        private const val TAG = "BaseRecyclerAdapter"

        const val POSTTYPE_HEADERVIEWHOLDER = 99 //기타
        const val POSTTYPE_FOOTERVIEWHOLDER = 98 //기타
        const val POSTTYPE_DEFAULTVIEWHOLDER = 97 //기타
        const val DEFAULT_PAGING_INDEX = -1 //기타
    }

    protected var mHeaderViewHolder: BaseViewHolder? = null
    protected var mFooterViewHolder: BaseViewHolder? = null

    var model: ArrayList<M> = ArrayList()
    protected var mPaginationId: String? = null
    protected var mPaginationLastPosition: Int = DEFAULT_PAGING_INDEX

    private var mOnItemClickListener: BaseViewHolder.OnItemClickListener? = null

    /**
     * 헤더뷰 사용여부 확인
     *
     * @return
     */
    var useHeader = false
        private set
    var useFooter = false
        private set

    /**
     * 데이터 입력 (추후 추가되는데이터도 동일시 입력)
     *
     * @param modelArrayList 추가될 데이터
     */
    open fun addDataList(modelArrayList: ArrayList<M>?) {
        var insertPosition = model.size
        model.addAll(modelArrayList!!)
        if (insertPosition != 0) insertPosition++
        notifyItemRangeInserted(insertPosition, modelArrayList.size)
    }

    open fun addData(model: M?) {
        if (model != null) {
            val insertPosition = this.model.size
            this.model.add(model)
            notifyItemInserted(insertPosition)
        }
    }

    open fun addFirstNotNotified(model: M?) {
        if (model != null) {
            this.model.add(model)
        }
    }

    /**
     * 뷰타입에 따라 보이는 화면 설정(다른 타입이 있을시 이부분 오버라이딩해서 씀)
     *
     * @param position 요청할 뷰 타입 번호
     * @return
     */
    override fun getItemViewType(position: Int): Int {
        return POSTTYPE_DEFAULTVIEWHOLDER
        return when {
            position == 0 && useHeader -> POSTTYPE_HEADERVIEWHOLDER
            position == itemCount - 1 && useFooter -> POSTTYPE_FOOTERVIEWHOLDER
            else -> POSTTYPE_DEFAULTVIEWHOLDER
        }
    }

    /**
     * 아이템 갯수
     *
     * @return
     */
    override fun getItemCount(): Int {
        var returnCount = model.size
        if (useHeader) returnCount++
        if (useFooter) returnCount++
        return returnCount
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder

    /**
     * 데이터 세팅
     * 데이터값이 없을경우 해당 아이템 삭제
     *
     * @param defaultViewHolder
     * @param position          해당 아이템의 포지션
     */
    override fun onBindViewHolder(defaultViewHolder: BaseViewHolder, position: Int) {
        if (mOnItemClickListener != null) {
            defaultViewHolder.setOnItemClickListener(mOnItemClickListener)
        }
        //헤더일경우
        if (position == 0 && useHeader) {
            //푸터일경우
        } else if (position == itemCount - 1 && useFooter) {
        } else {
            try {
                defaultViewHolder.data = getItem(position)
            } catch (e: IndexOutOfBoundsException) {
                e.printStackTrace()
            } catch (e: NullPointerException) {
                Log.e(TAG, "AdapterItemNull")
                errorDeleteItem(position - if (useHeader) 1 else 0)
            }
        }
    }

    /**
     * 데이터 없는 아이템 삭제
     *
     * @param itemPosition 삭제될 아이템 번호
     */
    private fun errorDeleteItem(itemPosition: Int) {
        Handler(Looper.getMainLooper()).postDelayed({
            model.removeAt(itemPosition)
            notifyDataSetChanged()
        }, 200)
    }

    /**
     * 아이템 가져오기
     *
     * @param position 가져올 아이템 위치
     * @return
     */
    fun getItem(position: Int): M? {
        var itemPosition = position
        if (itemPosition != 0) itemPosition -= if (useHeader) 1 else 0
        if (model.size == 0 || model.size <= itemPosition) {
            return null
        }
        return try {
            model[itemPosition]
        } catch (e: ArrayIndexOutOfBoundsException) {
            null
        }
    }

    /**
     * 헤더뷰 사용
     *
     * @param headerVIewHolder 상단에 보일 헤더뷰
     */
    fun setUseHeader(headerVIewHolder: BaseViewHolder? = null) {
        mHeaderViewHolder = headerVIewHolder
        useHeader = true
        notifyDataSetChanged()
    }

    /**
     * 헤더뷰 사용 취소
     */
    fun setUnUseHeader() {
        mHeaderViewHolder = null
        useHeader = false
    }

    fun setUnUseFooter() {
        mFooterViewHolder = null
        useFooter = false
    }

    fun setUseFooter(footerViewHolder: BaseViewHolder?) {
        mFooterViewHolder = footerViewHolder
        useFooter = true
    }

    fun setOnItemClickListener(onItemClickListener: BaseViewHolder.OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    open fun clearAdapter() {
        mPaginationId = ""
        mPaginationLastPosition = DEFAULT_PAGING_INDEX
        model.clear()
        notifyDataSetChanged()
    }


}
