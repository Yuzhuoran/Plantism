package com.waterme.plantism;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waterme.plantism.data.PlantContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuoran on 3/28/18.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private Context mContext;
    private Cursor mIntroCursor;
    private List<String> speciesList;
    private List<String> genusList;
    private List<String> imageList;

    public SearchAdapter(Context context, Cursor IntroCursor) {
        mIntroCursor = IntroCursor;
        mContext = context;

        speciesList = new ArrayList<>();
        genusList = new ArrayList<>();
        imageList = new ArrayList<>();
        while(mIntroCursor.moveToNext()) {
            speciesList.add(mIntroCursor.getString(mIntroCursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_SPECIES)));
            genusList.add(mIntroCursor.getString(mIntroCursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_GENUES)));
            imageList.add(mIntroCursor.getString(mIntroCursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_IMAGE)));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.genusText.setText(genusList.get(position));
        holder.speciesText.setText(speciesList.get(position));
        int id = mContext.getResources().getIdentifier("@drawable/" + imageList.get(position),
                null,
                HomeActivity.PACKAGE_NAME);
        //Drawable drawable = mContext.getResources().getDrawable(id);
        //ResourcesCompat.getDrawable(mContext.getResources(), id, null);
        holder.searchImg.setImageResource(id);
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_plant, parent, false));
    }

    @Override
    public int getItemCount() {
        return mIntroCursor.getCount();
    }

    public void swapCursor(Cursor cursor) {
        mIntroCursor = cursor;
    }


}
