package com.gosemathraj.railsofindia.utility;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by iamsparsh on 14/2/17.
 */

public class CustomBoldTextView extends TextView {
    public CustomBoldTextView(Context context) {
        super(context);
        setFont(context);
    }

    public CustomBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public CustomBoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomBoldTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setFont(context);
    }

    public void setFont(Context context){
        setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/QuicksandBold.otf"));
    }
}
