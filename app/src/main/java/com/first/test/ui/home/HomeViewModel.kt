package com.first.test.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.first.test.data.local.db.AppDatabase
import com.first.test.data.local.db.entity.TImeInfoEntity
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
    var mTimeDetailsList = MutableLiveData<List<TImeInfoEntity>>()
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

    fun getTimeList(context: Context, callBack:HomeNavigator): LiveData<List<TImeInfoEntity>> {
        mTimeDetailsList = MutableLiveData()
        mCallBack=callBack
        mTimeDetailsList.value=AppDatabase.getAppDatabase(context).timeDao().getTimeInfoDB()
        getTimeDetails(context)
        return mTimeDetailsList
    }


    fun getTimeDetails(context: Context) {
       // mCallBack.showLoading()
        mCompositeDisposable.add(mApiService.getTimeDetails(mApiKey)
            .subscribeOn(Schedulers.io())
            .observeOn( AndroidSchedulers.mainThread())
            .subscribe({ commonResponse ->
                mCallBack.hideLoading()
                if (commonResponse != null && commonResponse.results != null) {
                    var timeList=ArrayList<TImeInfoEntity>()
//                    for (item in commonResponse.results){
//                     var time=TImeInfoEntity(
//                         item.id,item.title,item.abstract,item.published_date,item.url
//                     )
//                        timeList.add(time)
//                    }
                    if(!timeList.isEmpty()){
                        AppDatabase.getAppDatabase(context).timeDao().insertTimeDB(timeList)
                    }

                   // mTimeDetailsList.setValue(commonResponse.results)
                }
            }, { throwable ->
                 print(throwable)
                try {
                  //  mCallBack.hideLoading()
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
                   // mCallBack.hideLoading()
                    println("Error: " + exception.message)
                }

            })
        )
    }

}