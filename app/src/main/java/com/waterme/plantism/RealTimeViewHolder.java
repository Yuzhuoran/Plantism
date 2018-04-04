package com.waterme.plantism;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by zhuoran on 3/22/18.
 */

abstract class RealTimeViewHolder extends RecyclerView.ViewHolder {

    // TODO check the view item
    public TextView plantCategory;
    public TextView plantMyName;
    public TextView hmText;
    public TextView tpText;
    public ImageView plantImg;
    public ImageView hmImg;
    public ImageView tpImg;

    /* add plant item view */
    public TextView sensorAddId;

    /* sensor list */
    public TextView sensorId;
    public TextView sensorPlantName;
    public TextView sensorPlantSpecies;

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