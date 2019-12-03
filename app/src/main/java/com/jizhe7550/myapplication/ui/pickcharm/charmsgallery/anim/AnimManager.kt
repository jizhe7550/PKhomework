package com.jizhe7550.myapplication.ui.pickcharm.charmsgallery.anim

import android.view.animation.AccelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager

object AnimManager {

    fun handleSnapViewAnim(snapPos: Int, lm: LinearLayoutManager) {
        val snapView = lm.findViewByPosition(snapPos)!!
        snapView.animate().setDuration(350).scaleX(1.5f).scaleY(1.5f)
            .alpha(1.0f)
            .setInterpolator(AccelerateInterpolator()).start()
    }

    fun handleFrontSnapViewsAnim(snapPos: Int, lm: LinearLayoutManager) {
        val firstViewPosition = lm.findFirstVisibleItemPosition()
        for (index in snapPos - 1 downTo firstViewPosition) {
            val frontToSnapView = lm.findViewByPosition(index)!!
            frontToSnapView.animate().setDuration(350).scaleX(1f).scaleY(1f)
                .alpha(1.0f - (snapPos - index) * 0.4f)
                .setInterpolator(AccelerateInterpolator()).start()
        }

    }

    fun handleLastSnapViewsAnim(snapPos: Int, lm: LinearLayoutManager) {
        val lastViewPosition = lm.findLastVisibleItemPosition()
        for (index in snapPos + 1..lastViewPosition) {
            val lastViewToSnapView = lm.findViewByPosition(index)!!
            lastViewToSnapView.animate().setDuration(350).scaleX(1f).scaleY(1f)
                .alpha(1.0f - (index - snapPos) * 0.4f)
                .setInterpolator(AccelerateInterpolator()).start()
        }

    }

    fun handleScrollItemAnim(lm: LinearLayoutManager) {
        val firstViewPosition = lm.findFirstVisibleItemPosition()
        val lastViewPosition = lm.findLastVisibleItemPosition()
        for (index in firstViewPosition..lastViewPosition) {
            val view = lm.findViewByPosition(index)!!
            view.animate().setDuration(350).scaleX(1f).scaleY(1f)
                .alpha(1f)
                .setInterpolator(AccelerateInterpolator()).start()
        }
    }
}