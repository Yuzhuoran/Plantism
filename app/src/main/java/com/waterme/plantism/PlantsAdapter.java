package com.waterme.plantism;

import android.content.Context;
import android.database.Cursor;
import android.media.Image;
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
    private List<Plant> mPlants;
    private PlantsAdapterOnClickHandler mClickHandler;

    public interface PlantsAdapterOnClickHandler {
         void onClick(Plant plant);
    }


    /**
     * Constructor for plants adapter
     * @param context
     * @param clickHandler
     */
    public PlantsAdapter(Context context, PlantsAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }


    /**
     * calls each viewholder is created
     * @param viewGroup the viewGroup this holder contains, here we have two figure view?
     * @param viewType the type of view
     * @return A new PlantsAdapterViewHolder
     */
    @Override
    public PlantsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.plant_list_item, viewGroup, false);
        view.setFocusable(true);
        return new PlantsAdapterViewHolder(view);
    }

    /**
     * display data at specified position, here we update the contetn of viewholder
     * @param plantsAdapterViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(PlantsAdapterViewHolder plantsAdapterViewHolder, int position) {

        plantsAdapterViewHolder.plantSummary.setText("Hello World");
    }

    /**
     * the number of items to display
     * @return
     */
    @Override
    public int getItemCount() {
        return mPlants.size();
    }

    /**
     * update the plants data
     * @param plants
     */

    void updateData(List<Plant> plants) {
        mPlants = plants;
    }

    /**
     *
     * @param plant update one plant information
     * @param position position of the plant in the list
     */
    void updateOneData(Plant plant, int position) {
        mPlants.set(position, plant);
    }
    /**
     * viewholder to plants
     * each viewholder has a figure for humudity and temperature
     */
    public class PlantsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView plantSummary;

        PlantsAdapterViewHolder(View view) {
            super(view);

            plantSummary = (TextView) view.findViewById(R.id.tv_plant_data);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mPlants.get(adapterPosition));
        }
    }
}
