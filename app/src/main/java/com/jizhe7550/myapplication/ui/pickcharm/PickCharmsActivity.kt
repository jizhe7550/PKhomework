package com.jizhe7550.myapplication.ui.pickcharm

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jizhe7550.myapplication.R
import com.jizhe7550.myapplication.constant.IntentKey
import com.jizhe7550.myapplication.ui.widget.GravitySnapHelper
import com.jizhe7550.myapplication.util.screenWidth
import com.jizhe7550.myapplication.ui.pickcharm.charmsgallery.adapter.CircleViewAdapter
import com.jizhe7550.myapplication.ui.pickcharm.charmsgallery.LinearEdgeDecoration
import com.jizhe7550.myapplication.ui.pickcharm.charmsgallery.adapter.RectNumViewAdapter
import com.jizhe7550.myapplication.ui.pickcharm.charmsgallery.anim.AnimManager
import com.jizhe7550.myapplication.model.CharmModel
import com.jizhe7550.myapplication.ui.confirm.ConfirmActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class PickCharmsActivity : AppCompatActivity() {

    private val mViewModel: PickCharmsViewModel by viewModel()
    private val mCharmList = mutableListOf<CharmModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startObserve()
        setContentView(R.layout.activity_main)
        setToolBar()
        initData()
        setView()
    }

    private fun initData() {
        mViewModel.getCharmList()
    }

    private fun startObserve() {
        mViewModel.charms.observe(this, Observer {
            it?.run {
                setCharmsViewData(it)
            }
        })
    }

    private fun setCharmsViewData(charms: List<CharmModel>) {
        mCharmList.clear()
        mCharmList.addAll(charms)
        refresh()
    }

    private fun refresh() {
        recyclerView.adapter!!.notifyDataSetChanged()
        recyclerView2.adapter!!.notifyDataSetChanged()
        recyclerView3.adapter!!.notifyDataSetChanged()

        setInitAnim(recyclerView)
        setInitAnim(recyclerView2)
        setInitAnim(recyclerView3)
    }

    private fun setView() {
        setRecyclerView1()
        setRecyclerView2()
        setRecyclerView3()

        confirmButton.setOnClickListener {
            val intent = Intent(this, ConfirmActivity::class.java)
            intent.putExtra(
                IntentKey.ChoiceCharm,
                mCharmList[recyclerView2.snapHelper.currentSnappedPosition])
            intent.putExtra(IntentKey.ChoiceCharmNum1,recyclerView.snapHelper.currentSnappedPosition)
            intent.putExtra(IntentKey.ChoiceCharmNum2,recyclerView3.snapHelper.currentSnappedPosition)
            startActivity(intent)
        }
    }

    private fun setRecyclerView1() {
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        setItemDecoration(recyclerView)
        setAdapter1()
        recyclerView.setItemViewAnimListener(MyItemViewAnimListener())
    }

    private fun setRecyclerView2() {
        recyclerView2.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        setItemDecoration(recyclerView2)
        setAdapter2()
        recyclerView2.setItemViewAnimListener(MyItemViewAnimListener())
    }

    private fun setRecyclerView3() {
        recyclerView3.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        setItemDecoration(recyclerView3)
        setAdapter3()
        recyclerView3.setItemViewAnimListener(MyItemViewAnimListener())
    }

    private fun setItemDecoration(recyclerView: RecyclerView) {
        recyclerView.addItemDecoration(
            LinearEdgeDecoration(
                startPadding = screenWidth() / 2 - resources.getDimension(R.dimen.circle_width).toInt() / 2,
                orientation = RecyclerView.HORIZONTAL
            )
        )
    }

    private fun setToolBar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    private fun setInitAnim(rLm: RecyclerView) {
        val lm = rLm.layoutManager as LinearLayoutManager
        if (lm.itemCount > 0) {
            Handler().postDelayed({
                AnimManager.handleSnapViewAnim(0, lm)
                AnimManager.handleLastSnapViewsAnim(0, lm)
            }, 100)
        }
    }

    private fun setAdapter1() {
        val adapter1 = CircleViewAdapter(CircleViewAdapter.CircleViewType.TEXT)
        adapter1.setItems(mCharmList)
        recyclerView.adapter = adapter1
    }

    private fun setAdapter2() {
        val adapter2 = CircleViewAdapter(CircleViewAdapter.CircleViewType.IMAGE)
        adapter2.setItems(mCharmList)
        recyclerView2.adapter = adapter2
    }

    private fun setAdapter3() {
        val adapter3 = RectNumViewAdapter()
        adapter3.setItems(mCharmList)
        recyclerView3.adapter = adapter3
    }


    class MyItemViewAnimListener : GravitySnapHelper.ItemViewAnimListener {

        override fun onItemViewAnim(recyclerView: RecyclerView, position: Int, newState: Int) {
            if (recyclerView.childCount > 0) {

                val lm = recyclerView.layoutManager as LinearLayoutManager

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    AnimManager.handleSnapViewAnim(position, lm)

                    if (position > 0) {
                        AnimManager.handleFrontSnapViewsAnim(position, lm)
                    }

                    if (position < recyclerView.adapter!!.itemCount - 1) {
                        AnimManager.handleLastSnapViewsAnim(position, lm)
                    }

                } else {
                    AnimManager.handleScrollItemAnim(lm)
                }
            }
        }

    }
}

