package com.waterme.plantism.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.waterme.plantism.R;

/**
 * Created by zhuoran on 3/25/18.
 */

public class SearchViewHolder extends RecyclerView.ViewHolder {

    public ImageView searchImg;
    public TextView genusText;
    public TextView speciesText;

    private SearchViewHolder.ClickListener mClickListener;

    public SearchViewHolder(View view) {
        super(view);
        searchImg = (ImageView) itemView.findViewById(R.id.im_plant_search);
        genusText = (TextView) itemView.findViewById(R.id.tv_genus);
        speciesText = (TextView) itemView.findViewById(R.id.tv_species);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });

    }

    public void setmClickListener(SearchViewHolder.ClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    public interface ClickListener {
         void onItemClick(View view, int position);
    }
}
