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
import android.widget.Filter;
import android.widget.Filterable;

import com.waterme.plantism.data.PlantContract;
import com.waterme.plantism.data.PlantImageContract;
import com.waterme.plantism.model.Plant;
import com.waterme.plantism.model.PlantIntro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuoran on 3/28/18.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> implements
        Filterable{

    private Context mContext;
    private Cursor mCursor;
    private List<PlantIntro> items;
    private List<PlantIntro> originalItems;


    public SearchAdapter(Context context, Cursor cursor) {
        super();
        mCursor = cursor;
        mContext = context;
        items = new ArrayList<>();
        originalItems = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                originalItems.add(new PlantIntro(
                        cursor.getString(cursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_SPECIES)),
                        cursor.getString(cursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_GENUES)),
                        cursor.getString(cursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_FULL_INTRO)),
                        cursor.getString(cursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_SHORT_INTRO)),
                        cursor.getString(cursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_TIP)),
                        cursor.getString(cursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_IMAGE)),
                        cursor.getInt(cursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_HUMIDITY_THREAD)),
                        cursor.getInt(cursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_TEMPERATURE_THREAD))

                ));
            }
        } finally {
            cursor.close();
        }
        items.addAll(originalItems);

    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        //mCursor.moveToPosition(position);
        final PlantIntro intro = items.get(position);
        holder.genusText.setText(intro.getGenus());
        holder.speciesText.setText(intro.getSpecies());
        String imageId = intro.getImage();
        int id = mContext.getResources().getIdentifier("@drawable/" + imageId,
                null,
                HomeActivity.PACKAGE_NAME);
        //Drawable drawable = mContext.getResources().getDrawable(id);
        //ResourcesCompat.getDrawable(mContext.getResources(), id, null);
        /* not test yet */
        //holder.searchImg.setImageResource(id);
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_plant, parent, false));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void swapIntroCursor(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<PlantIntro> filteredResult = null;
                if (constraint.length() == 0) {
                    filteredResult = originalItems;
                } else {
                    filteredResult = getFilteredResult(constraint.toString().toLowerCase());
                }
                FilterResults results = new FilterResults();
                results.values = filteredResult;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                items = (List<PlantIntro>) results.values;
                notifyDataSetChanged();

            }
        };

    }
    private List<PlantIntro> getFilteredResult(String constraint) {
        List<PlantIntro> res = new ArrayList<>();

        for (PlantIntro intro : originalItems) {
            if (intro.getSpecies().toLowerCase().contains(constraint)
                    || intro.getGenus().toLowerCase().contains(constraint)) {
                res.add(intro);
            }
        }
        return res;
    }
}
