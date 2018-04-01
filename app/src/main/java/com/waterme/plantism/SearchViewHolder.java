package com.waterme.plantism;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.waterme.plantism.model.MyTextView;

/**
 * Created by zhuoran on 3/25/18.
 */

public class SearchViewHolder extends RecyclerView.ViewHolder {

    public ImageView searchImg;
    public MyTextView genusText;
    public MyTextView speciesText;

    private SearchViewHolder.ClickListener mClickListener;

    public SearchViewHolder(View view) {
        super(view);
        searchImg = (ImageView) itemView.findViewById(R.id.im_plant_search);
        genusText = (MyTextView) itemView.findViewById(R.id.tv_genus);
        speciesText = (MyTextView) itemView.findViewById(R.id.tv_species);
        genusText.setStyle("light");
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
