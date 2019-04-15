package com.first.test.ui.time_details

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.first.test.R
import com.first.test.data.localdb.entity.TimeInfo
import com.first.test.ui.home.HomeViewModel
import com.first.test.ui.home.TimeAdapter
import android.arch.lifecycle.Observer
import android.support.annotation.Nullable
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_time_details.*


class TimeDetailsActivity : AppCompatActivity() {

    private lateinit var mTimeDetailsViewModel: TimeDetailsViewModel
    private lateinit var mTimeDetails:TimeInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_details)
        title="Time Details"
        mTimeDetailsViewModel = ViewModelProviders.of(this).get(TimeDetailsViewModel::class.java)
        mTimeDetailsViewModel.getDBInstanse(this)
       if(intent.hasExtra("Id")) {
           var id=intent.getLongExtra("Id",0)
           if(id>0){
               getTime(id)
           }
       }
    }

    fun getTime(id:Long){
        mTimeDetailsViewModel.getTimeDetails(id).observe(this, Observer<TimeInfo> {
            tv_title.text=it!!.title
            tv_sub_title.text=it.sub_title
            tv_publish_date.text=it.published_date
            tv_type.text= getString(R.string.type)+it.type
            Glide
                .with(this)
                .load(it.url)
                .into(iv_thumbnail)
            println(it.toString())
        })
    }
}
