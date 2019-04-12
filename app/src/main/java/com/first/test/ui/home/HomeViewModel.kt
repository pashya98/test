package com.first.test.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.first.test.model.TimeDetails
import com.first.test.network.ApiClient
import io.reactivex.disposables.CompositeDisposable
import com.first.test.network.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava2.HttpException
import java.io.IOException

class HomeViewModel: ViewModel() {
    private  var mCompositeDisposable: CompositeDisposable
    var mTimeDetailsList = MutableLiveData<List<TimeDetails>>()
    private  var mApiService :ApiInterface
    private val mApiKey:String="rECvXat5JIMkG59TNS8J4Ri2ZVHh8pYH"
    private lateinit var mCallBack:HomeNavigator

    init {
        this.mCompositeDisposable = CompositeDisposable()
        mApiService = ApiClient.getClient()!!.create(ApiInterface::class.java)
    }


    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }

    fun getTimeList(callBack:HomeNavigator): LiveData<List<TimeDetails>> {
        mTimeDetailsList = MutableLiveData()
        mCallBack=callBack
        getTimeDetails()
        return mTimeDetailsList
    }


    fun getTimeDetails() {
        mCallBack.showLoading()
        mCompositeDisposable.add(mApiService.getTimeDetails(mApiKey)
            .subscribeOn(Schedulers.io())
            .observeOn( AndroidSchedulers.mainThread())
            .subscribe({ commonResponse ->
                mCallBack.hideLoading()
                if (commonResponse != null && commonResponse.results != null) {
                    mTimeDetailsList.setValue(commonResponse.results)
                }
            }, { throwable ->
                 print(throwable)
                try {
                    mCallBack.hideLoading()
                    if (throwable is HttpException) {
                        val httpException = throwable
                        println("Http Error: " + throwable.message)
                        mCallBack.showMessage(httpException.message())
                    } else if (throwable is IOException) {
                        println("IO Error: " + throwable.message)
                    } else {
                        println("Error: " + throwable.message)
                    }
                } catch (exception: Exception) {
                    mCallBack.hideLoading()
                    println("Error: " + exception.message)
                }

            })
        )
    }

}