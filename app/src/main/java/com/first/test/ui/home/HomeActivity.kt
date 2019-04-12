package com.first.test.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.first.test.R
import com.first.test.model.TimeDetails
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.first.test.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_main.*


class HomeActivity : AppCompatActivity(),HomeNavigator {

    private lateinit var mHomeViewModel:HomeViewModel
    private lateinit var mTimeAdapter:TimeAdapter
    private lateinit var mTimeList:List<TimeDetails>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv_time.setLayoutManager(LinearLayoutManager(this))
        mTimeList= ArrayList<TimeDetails>()
        mTimeAdapter=TimeAdapter(this, mTimeList);
        rv_time.setAdapter(mTimeAdapter)
        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        getTime()
    }

    fun getTime(){
        if(NetworkUtil.isNetworkConnected(this)){
            mHomeViewModel.getTimeList(this).observe(this, object : Observer<List<TimeDetails>> {
                override fun onChanged(timeDetailsList: List<TimeDetails>?) {
                    if(mTimeAdapter!=null){
                        mTimeAdapter.updateTime(timeDetailsList!!)
                    }
                }
            })
        }else{
            showMessage(getString(R.string.no_internet_connection))
        }

    }

    override fun showMessage(message: String) {
       Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        simpleProgressBar.visibility= View.VISIBLE
    }

    override fun hideLoading() {
        simpleProgressBar.visibility= View.GONE
    }

}
