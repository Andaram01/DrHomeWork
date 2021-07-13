package com.dr.drhomework.base.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dr.drhomework.util.extension.setClickAnimation


abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    init {
        itemView.setOnClickListener { view ->
            if (useClickAnimation) {
                view.setClickAnimation {
                    mOnItemClickListener?.onItemClick(layoutPosition, data)
                }
            } else {
                mOnItemClickListener?.onItemClick(layoutPosition, data)
            }
        }
        itemView.setOnLongClickListener {
            mOnItemClickListener?.onItemLongClick(layoutPosition, data)
            true
        }
    }

    protected var mOnItemClickListener: OnItemClickListener? = null
    private var useClickAnimation = false
    var data: Any? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, model: Any?) { /* explicitly empty */
        }

        fun onItemLongClick(position: Int, model: Any?) { /* explicitly empty */
        }
    }

    fun setUseClickAnimation(useClickAnimation: Boolean) {
        this.useClickAnimation = useClickAnimation
    }


    open fun onAppear() {
    }

    open fun onDisappear() {
    }
}
