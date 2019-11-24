package com.unicorn.tickets.ui.base

import android.content.Context
import android.view.View
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.extensions.LayoutContainer

// means kotlinViewHolder
class KVHolder(view: View) : BaseViewHolder(view), LayoutContainer {

    override val containerView: View = view

    val context: Context = containerView.context

}

