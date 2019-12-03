package com.jizhe7550.myapplication.ui.confirm

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.jizhe7550.myapplication.R
import com.jizhe7550.myapplication.constant.IntentKey
import com.jizhe7550.myapplication.model.CharmModel
import com.jizhe7550.myapplication.ui.pickcharm.PickCharmsActivity
import com.jizhe7550.myapplication.ui.pickcharm.charmsgallery.adapter.CircleViewAdapter
import kotlinx.android.synthetic.main.activity_confirm.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar

class ConfirmActivity : AppCompatActivity() {
    private lateinit var charmModel: CharmModel
    private var num1: Int = 0
    private var num2: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        charmModel = intent.getSerializableExtra(IntentKey.ChoiceCharm) as CharmModel
        num1 = intent.getIntExtra(IntentKey.ChoiceCharmNum1, 0)
        num2 = intent.getIntExtra(IntentKey.ChoiceCharmNum2, 0)
        setToolBar()

        setView()
    }

    private fun setView() {
        Glide.with(imageView.context).load(charmModel.url)
            .placeholder(R.drawable.ic_photos_48dp)
            .into(imageView)

        textView1.text = num1.toString()
        textView2.text = num2.toString()
    }

    private fun setToolBar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeButtonEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}