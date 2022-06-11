package com.nanamare.starter.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.nanamare.starter.R
import com.nanamare.starter.databinding.EmptyViewBinding
import com.nanamare.starter.ui.PlaceHolderView.State.EMPTY
import com.nanamare.starter.ui.PlaceHolderView.State.ERROR

class PlaceHolderView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    enum class State { ERROR, EMPTY }

    private val binding by lazy {
        EmptyViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private val title get() = binding.title
    private val content get() = binding.content

    val button get() = binding.button

    init {
        initAttr(context, attrs)
    }

    private fun initAttr(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PlaceHolderView)

        val titleResId = typedArray.getResourceId(R.styleable.PlaceHolderView_titleText, 0)
        if (titleResId != 0)
            title.setText(titleResId)

        val contentResId = typedArray.getResourceId(R.styleable.PlaceHolderView_contentText, 0)
        if (contentResId != 0)
            content.setText(contentResId)

        val buttonResId = typedArray.getResourceId(R.styleable.PlaceHolderView_buttonText, 0)
        if (buttonResId != 0)
            button.setText(buttonResId)

        typedArray.recycle()
    }

    fun changeMode(state: State) {
        when (state) {
            ERROR -> setContent(R.string.title_error, R.string.content_error)
            EMPTY -> setContent(R.string.title_item_empty, R.string.content_item_empty)
        }
        isVisible = true
    }

    private fun setContent(@StringRes titleResId: Int, @StringRes contentResId: Int) {
        title.text = context.getString(titleResId)
        content.text = context.getString(contentResId)
    }
}