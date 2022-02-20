package com.example.rickandmortyapplication.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration(context: Context, @DrawableRes dividerRes: Int) :
    RecyclerView.ItemDecoration() {

    private val itemDivider: Drawable = ContextCompat.getDrawable(context, dividerRes)!!

    override fun onDrawOver(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top: Int = child.bottom + params.bottomMargin
            val bottom = top + itemDivider.intrinsicHeight
            itemDivider.setBounds(left, top, right, bottom)
            itemDivider.draw(c)
        }
    }
}