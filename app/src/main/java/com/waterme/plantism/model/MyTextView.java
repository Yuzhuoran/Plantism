package com.waterme.plantism.model;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

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
