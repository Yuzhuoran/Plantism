package com.waterme.plantism;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhuoran on 3/9/18.
 */

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.PlantsAdapterViewHolder>{

    private Context mContext;
    private Cursor mCursor;
    private PlantsAdapterOnClickHandler mClickHandler;
    public interface PlantsAdapterOnClickHandler {
         void onClick();
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
        return null;
    }

    /**
     * display data at specified position, here we update the contetn of viewholder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(PlantsAdapterViewHolder holder, int position) {

    }

    /**
     * the number of items to display
     * @return
     */
    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * viewholder to plants
     * each viewholder has a figure for humudity and temperature
     */
    public class PlantsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        PlantsAdapterViewHolder(View view) {
            super(view);

            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {

        }
    }
}
