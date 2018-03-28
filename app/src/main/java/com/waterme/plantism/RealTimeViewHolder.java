package com.waterme.plantism;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zhuoran on 3/22/18.
 */

public class RealTimeViewHolder extends RecyclerView.ViewHolder {

    public TextView plantCategory;
    public TextView plantMyName;
    public TextView hmText;
    public TextView tpText;
    public ImageView plantImg;
    public ImageView hmImg;
    public ImageView tpImg;

    private RealTimeViewHolder.ClickListener mClickListener;

    public RealTimeViewHolder(View view) {
        super(view);
        plantCategory = (TextView) itemView.findViewById(R.id.tv_plant_category);
        plantMyName = (TextView) itemView.findViewById(R.id.tv_plant_myName);
        hmText = (TextView) itemView.findViewById(R.id.tv_plant_hm);
        tpText = (TextView) itemView.findViewById(R.id.tv_plant_tp);
        plantImg = (ImageView) itemView.findViewById(R.id.im_plant_home);
        hmImg = (ImageView) itemView.findViewById(R.id.im_hm_indicator);
        tpImg = (ImageView) itemView.findViewById(R.id.im_tp_indicator);

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