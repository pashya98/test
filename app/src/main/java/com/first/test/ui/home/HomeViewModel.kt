package com.first.test.ui.home

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.first.test.network.ApiClient
import io.reactivex.disposables.CompositeDisposable
import com.first.test.network.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava2.HttpException
import java.io.IOException
import com.first.test.data.localdb.AppDatabase
import com.first.test.data.localdb.entity.TimeInfo
import io.reactivex.Completable
import io.reactivex.disposables.Disposable
import io.reactivex.CompletableObserver


class HomeViewModel: ViewModel() {

    private  var mCompositeDisposable: CompositeDisposable
    var mTimeDetailsList = MutableLiveData<List<TimeInfo>>()
    private var mApiService :ApiInterface
    private val mApiKey:String="rECvXat5JIMkG59TNS8J4Ri2ZVHh8pYH"
    private lateinit var mCallBack:HomeNavigator
    private lateinit var mAppDatabase: AppDatabase

    init {
        this.mCompositeDisposable = CompositeDisposable()
        mApiService = ApiClient.getClient()!!.create(ApiInterface::class.java)

    }

    fun getDBInstanse(context: Context){
        mAppDatabase= AppDatabase.getDB(context)!!
    }


    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }

    fun getTimeList(callBack:HomeNavigator): LiveData<List<TimeInfo>> {
        mTimeDetailsList = MutableLiveData()
        mCallBack=callBack
        mCallBack.showLoading()
        getLocalDBTimeList()
        getTimeDetails()
        return mTimeDetailsList
    }

    fun getLocalDBTimeList() {
        mCompositeDisposable.add(
        mAppDatabase.timeDao().getTimeList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ timelist ->
                mTimeDetailsList.setValue(timelist)
                if (timelist.isNotEmpty()){
                    mCallBack.hideLoading()
                }
            })
        )
    }


    @SuppressLint("CheckResult")
    fun getTimeDetails() {
        mCompositeDisposable.add(mApiService.getTimeDetails(mApiKey)
            .subscribeOn(Schedulers.io())
            .observeOn( AndroidSchedulers.mainThread())
            .subscribe({ commonResponse ->
                mCallBack.hideLoading()
                if (commonResponse != null && commonResponse.results != null) {
                    //mTimeDetailsList.setValue(commonResponse.results)
                   var  timeList=ArrayList<TimeInfo>()
                    for (item in commonResponse.results) {
                        var timeInfo=TimeInfo(item.id,
                                              item.type,
                                              item.title,
                                              item.abstract,
                                              item.published_date,
                                              item.media.get(0).mediametadata.get(0).url);
                        timeList.add(timeInfo)
//                        Observable.fromCallable {
//                            // mAppDatabase!!.timeDao().insertAll(timeList)
//                          var l=  mAppDatabase!!.timeDao().insert(timeInfo)
//                            print("Insert Data: "+l)
//                            //true
//                        };
                      //  Completable.fromAction {mAppDatabase!!.timeDao().insert(timeInfo)}

                    }
                    print("Server Data"+timeList)
                    addTime(timeList);
//                    Observable.fromCallable {
//                       // mAppDatabase!!.timeDao().insertAll(timeList)
//                        mAppDatabase!!.timeDao().insertAll(timeList)
//                        true
//                    };
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

    fun addTime(list: List<TimeInfo>) {
//        mCompositeDisposable.add(
//            mAppDatabase!!.timeDao().insertAll(list).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ users ->
//                    // databaseCallback.onUsersLoaded(users)
//                  //  mTimeDetailsList.setValue(users)
//                    println("Inserted")
//                })
//        )

        Completable.fromAction {
            mAppDatabase!!.timeDao().insertAll(list)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}

                override fun onComplete() {
                    println("Completed")
                    getLocalDBTimeList()
                   // databaseCallback.onUserAdded()
                }

                override fun onError(e: Throwable) {
                    println("Error:"+e)
                   // databaseCallback.onDataNotAvailable()
                }
            })
    }
}