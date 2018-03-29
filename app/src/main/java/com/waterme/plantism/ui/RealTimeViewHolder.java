package com.waterme.plantism.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zhuoran on 3/22/18.
 */

abstract class RealTimeViewHolder extends RecyclerView.ViewHolder {

    public TextView plantCategory;
    public TextView plantMyName;
    public TextView hmText;
    public TextView tpText;
    public ImageView plantImg;
    public ImageView hmImg;
    public ImageView tpImg;

    public TextView sensorText;

    private RealTimeViewHolder.ClickListener mClickListener;

    public RealTimeViewHolder(View view) {
        super(view);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });

    }

    public void setmClickListener(ClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    public interface ClickListener {
         void onItemClick(View view, int position);
    }
}