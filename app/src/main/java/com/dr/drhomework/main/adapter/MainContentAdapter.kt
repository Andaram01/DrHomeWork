
package com.dr.drhomework.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dr.drhomework.R
import com.dr.drhomework.base.recyclerview.BasePagingRecyclerAdapter
import com.dr.drhomework.base.recyclerview.BaseViewHolder
import com.dr.drhomework.databinding.LayoutMainContentItemBinding
import com.dr.drhomework.util.extension.setBackgroundDrawableResource
import com.dr.drhomework.util.extension.setClickAnimation
import com.dr.drhomework.util.extension.setImageUrlCenterCrop
import com.dr.drhomework.util.extension.setTextHtml
import com.dr.drhomework.network.model.RpSearchResult.Document as SearchModel


class MainContentAdapter : BasePagingRecyclerAdapter<SearchModel>() {
    init {
        setUseHeader()
    }

    interface OnMainContentListener {
        fun onCallSearchDetail(searchModel: SearchModel)
    }

    private var onMainContentListener: OnMainContentListener? = null

    fun setOnMainContentListener(onMainContentListener: OnMainContentListener) {
        this.onMainContentListener = onMainContentListener
    }


    override fun addDataList(modelArrayList: java.util.ArrayList<SearchModel>?) {
        super.addDataList(modelArrayList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            POSTTYPE_HEADERVIEWHOLDER -> ContentViewHolder(parent)
            else -> ContentViewHolder(parent)
        }
    }

    override fun onBindViewHolder(defaultViewHolder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(defaultViewHolder, position)

        when (defaultViewHolder) {
            is ContentViewHolder -> {
                getItem(position)?.let {
                    defaultViewHolder.bindData(it)
                }
            }
        }
    }


    override fun getNextData(position: Int) {
        if (position == mPaginationLastPosition) {
            return
        } else {
            mOnAddDataListener?.invoke()
            mPaginationLastPosition = position
        }
    }



    inner class ContentViewHolder(
        private val parent: ViewGroup,
        private val binding: LayoutMainContentItemBinding =
            LayoutMainContentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ) : BaseViewHolder(binding.root) {

        init {
            binding.contentViewHolder = this
        }

        fun bindData(searchModel: SearchModel) {
            data = searchModel
            with(binding) {
                nameTv.text = searchModel.getName()
                labelTv.text = searchModel.searchType?.value
                titleTv.setTextHtml(searchModel.title)
                dateTimeTv.text = searchModel.getShortDateTime()
                thumbIv.setImageUrlCenterCrop(
                    url = searchModel.thumbnail,
                    placeholder = R.drawable.placeholder
                )
            }
            updateBackground(searchModel.isOpened)
        }

        private fun updateBackground(isOpened: Boolean) {
            binding.contentLayout.setBackgroundDrawableResource(if (isOpened) R.color.gray_5 else R.color.white)
        }

        fun onAnimClick(view: View) {
            view.setClickAnimation {
                when (view.id) {
                    R.id.contentCv -> {
                        (data as? SearchModel)?.let { searchModel ->
                            searchModel.isOpened = true
                            onMainContentListener?.onCallSearchDetail(searchModel)
                            updateBackground(searchModel.isOpened)
                        }
                    }
                }
            }
        }
    }
}


@BindingAdapter("setMainContentAdapterData")
fun bindMainContentAdapterData(recyclerView: RecyclerView, items: ArrayList<SearchModel>) {
    println("bindMainContentAdapterData 001")
    (recyclerView.adapter as MainContentAdapter?)?.run {
        println("bindMainContentAdapterData 002")
        clearAdapter()
        if (!items.isNullOrEmpty()) {

            addDataList(items)
        }
    }
}



