package com.yanp.traficmetro.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.yanp.traficmetro.Constants;
import com.yanp.traficmetro.R;

public class TraficLightView extends View {

    private Paint paint;

    private int green=Color.rgb(0,204,0);
    private int orange=Color.rgb(232,108,25);
    private int red=Color.rgb(0,204,0);
    private int black=Color.rgb(0,0,0);

    private int currentColor;

    public TraficLightView(Context context) {
        super(context);
        this.paint=new Paint();
    }

    public void setColor(){
        /**
         * TODO
         */
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(this.red);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, 500, 500, paint);
    }
}
