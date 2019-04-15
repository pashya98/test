package com.first.test.ui.time_details

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.first.test.R
import com.first.test.data.localdb.entity.TimeInfo
import android.arch.lifecycle.Observer
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_time_details.*


class TimeDetailsActivity : AppCompatActivity() {

    private lateinit var mTimeDetailsViewModel: TimeDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_details)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
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


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
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
