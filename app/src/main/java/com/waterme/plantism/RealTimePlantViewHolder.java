package com.waterme.plantism;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.waterme.plantism.model.MyTextView;

/**
 * Created by zhuoran on 3/28/18.
 */

public class RealTimePlantViewHolder extends RealTimeViewHolder {

    public RealTimePlantViewHolder(View view) {
        super(view);
        plantCategory = (MyTextView) itemView.findViewById(R.id.tv_plant_category);
        plantCategory.setStyle("light");
        plantMyName = (MyTextView) itemView.findViewById(R.id.tv_plant_myName);
        hmText = (MyTextView) itemView.findViewById(R.id.tv_plant_hm);
        hmText.setStyle("light");
        tpText = (MyTextView) itemView.findViewById(R.id.tv_plant_tp);
        tpText.setStyle("light");
        plantImg = (ImageView) itemView.findViewById(R.id.im_plant_home);
        hmImg = (ImageView) itemView.findViewById(R.id.im_hm_indicator);
        tpImg = (ImageView) itemView.findViewById(R.id.im_tp_indicator);

    }
}
