package com.first.test.ui.home

import android.support.v7.widget.RecyclerView
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.first.test.R
import com.first.test.model.TimeDetails


class TimeAdapter(var mContext: Context, var mTimeDetailsList:List<TimeDetails>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    fun updateTime(timeDetailsList:List<TimeDetails>){
        mTimeDetailsList=timeDetailsList;
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): RecyclerView.ViewHolder {
       val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_item_time,viewGroup,false);
        return TimeViewHolder(view);
    }

    override fun getItemCount(): Int {
      return mTimeDetailsList.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val timeViewHolder = viewHolder as TimeViewHolder

        timeViewHolder.mTitle.text=mTimeDetailsList[position].title
        timeViewHolder.mSubTitle.text=mTimeDetailsList[position].abstract
        timeViewHolder.mPublishDate.text=mTimeDetailsList[position].published_date
        Glide
            .with(mContext)
            .load(mTimeDetailsList[position].media.get(0).mediametadata.get(0).url)
            .into(timeViewHolder.mProfile)
    }


    class TimeViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var mTitle: TextView
        var mSubTitle: TextView
        var mProfile: ImageView
        var mPublishDate:TextView

        init {
            mTitle = v.findViewById(R.id.tv_title)
            mSubTitle = v.findViewById(R.id.tv_subtitle)
            mProfile = v.findViewById(R.id.iv_profile)
            mPublishDate = v.findViewById(R.id.tv_publish_date)
        }
    }

}
