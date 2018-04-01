package com.waterme.plantism.model;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class MyEditText extends android.support.v7.widget.AppCompatEditText{
    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyEditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/avenir_black.ttf");
            setTypeface(tf);
        }
    }
    public void setStyle(String style){
        String path="fonts/avenir_"+style+".ttf";
        if(!isInEditMode()){
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), path);
            setTypeface(tf);
        }
    }
}
