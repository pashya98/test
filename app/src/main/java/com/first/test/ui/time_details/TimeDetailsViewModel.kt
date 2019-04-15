package com.first.test.ui.time_details

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.first.test.data.localdb.AppDatabase
import com.first.test.data.localdb.entity.TimeInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TimeDetailsViewModel: ViewModel() {
    private  var mCompositeDisposable: CompositeDisposable
    var mTimeDetails = MutableLiveData<TimeInfo>()
    private lateinit var mAppDatabase: AppDatabase

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }

    fun getDBInstanse(context: Context){
        mAppDatabase= AppDatabase.getDB(context)!!
    }

    fun getTimeDetails(id: Long): MutableLiveData<TimeInfo> {
        mTimeDetails = MutableLiveData()
        getDBTimeDetails(id)
        return mTimeDetails
    }

    fun getDBTimeDetails(id:Long) {
        mCompositeDisposable.add(
            mAppDatabase.timeDao().getTimeDetails(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ users ->
                    mTimeDetails.setValue(users)
                })
        )
    }


}