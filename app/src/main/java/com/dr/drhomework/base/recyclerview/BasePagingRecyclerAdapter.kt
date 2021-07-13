package com.dr.drhomework.base.recyclerview

abstract class BasePagingRecyclerAdapter<M> : BaseRecyclerAdapter<M>() {
    companion object {
        private const val PAGINATION_ADDITIONAL_REQUEST_LIMIT = 2
    }

    private var hasNext = true
    protected var mOnAddDataListener: (() -> Unit)? = null

    fun setOnAddDataListener(onAddDataListener: (() -> Unit)?) {
        mOnAddDataListener = onAddDataListener
    }

    fun setHasNext(hasNext: Boolean) {
        this.hasNext = hasNext
    }

    /**
     * 데이터 세팅
     * 데이터값이 없을경우 해당 아이템 삭제
     *
     * @param defaultViewHolder
     * @param position          해당 아이템의 포지션
     */
    override fun onBindViewHolder(defaultViewHolder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(defaultViewHolder!!, position)
        if (itemCount > 0 && itemCount - position < PAGINATION_ADDITIONAL_REQUEST_LIMIT && hasNext) {
            getNextData(position)
        }
    }

    abstract fun getNextData(position: Int)

}
