package com.gosemathraj.railsofindia.utility;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by iamsparsh on 19/2/17.
 */

public class CustomLightTextView extends TextView {
    public CustomLightTextView(Context context) {
        super(context);
        setFont(context);
    }

    public CustomLightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public CustomLightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomLightTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setFont(context);
    }

    public void setFont(Context context){
        setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/QuicksandLight.otf"));
    }
}
