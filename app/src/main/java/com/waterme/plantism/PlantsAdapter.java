package com.waterme.plantism;

import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhuoran on 3/9/18.
 */

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.PlantsAdapterViewHolder>{

    private Context mContext;
    private List<String> mPlantsId;
    private List<String> mImgUrls;
    private List<RealTimeData> mData;

    private PlantsAdapterOnClickHandler mClickHandler;

    public interface PlantsAdapterOnClickHandler {
         void onClick(String plantId);
    }


    /**
     * Constructor for plants adapter
     * @param context
     * @param clickHandler
     */
    public PlantsAdapter(Context context, PlantsAdapterOnClickHandler clickHandler, List<String> plantsId,
                         List<RealTimeData> data, List<String> imgUrls) {
        mPlantsId = plantsId;
        mImgUrls = imgUrls;
        mData = data;
        mContext = context;
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public PlantsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlantsAdapterViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantsAdapterViewHolder holder, int position) {
        // bind view holder
    }

    /**
     * update the home page
     * @param newData
     */
    public void updateAll(List<RealTimeData> newData) {
        mData.clear();
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mPlantsId.size();
    }


    /**
     * Viewholder for plant home page
     */
    public class PlantsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView plantName;
        public TextView plantMyName;
        public TextView hmText;
        public TextView tpText;
        public ImageView plantImg;
        public ImageView hmImg;
        public ImageView tpImg;

        public PlantsAdapterViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_plant, parent, false));
            plantName = (TextView) itemView.findViewById(R.id.tv_plant_name);
            plantMyName = (TextView) itemView.findViewById(R.id.tv_plant_myName);
            hmText = (TextView) itemView.findViewById(R.id.tv_plant_hm);
            tpText = (TextView) itemView.findViewById(R.id.tv_plant_tp);
            plantImg = (ImageView) itemView.findViewById(R.id.im_plant_home);
            hmImg = (ImageView) itemView.findViewById(R.id.im_hm_indicator);
            tpImg = (ImageView) itemView.findViewById(R.id.im_tp_indicator);
        }
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mPlantsId.get(adapterPosition));
        }
    }
}
