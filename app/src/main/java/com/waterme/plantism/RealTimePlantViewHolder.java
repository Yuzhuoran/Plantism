package com.waterme.plantism;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zhuoran on 3/28/18.
 */

public class RealTimePlantViewHolder extends RealTimeViewHolder {

    public RealTimePlantViewHolder(View view) {
        super(view);
        plantCategory = (TextView) itemView.findViewById(R.id.tv_plant_category);
        plantMyName = (TextView) itemView.findViewById(R.id.tv_plant_myName);
        hmText = (TextView) itemView.findViewById(R.id.tv_plant_hm);
        tpText = (TextView) itemView.findViewById(R.id.tv_plant_tp);
        plantImg = (ImageView) itemView.findViewById(R.id.im_plant_home);
        hmImg = (ImageView) itemView.findViewById(R.id.im_hm_indicator);
        tpImg = (ImageView) itemView.findViewById(R.id.im_tp_indicator);
    }
}
