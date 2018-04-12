package com.waterme.plantism.model;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.waterme.plantism.R;

public class MyTextView extends android.support.v7.widget.AppCompatTextView {
    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "avenir_black.ttf");
            setTypeface(tf);
        }
    }
    public void setStyle(String style){
        String path="avenir_" + style + ".ttf";
        if(!isInEditMode()){
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), path);
            setTypeface(tf);
        }
    }
}
