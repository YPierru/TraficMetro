package com.example.traficmetro.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class StationView extends View {
	
	private static final int DIAMETER=15;
	private float xCenterCircle;
	private float x,y;
	private float yCenterCircle;
	private Paint paint;
	private int color;
	
	public StationView(Context ct, float x, float y, int color){
		super(ct);

		this.color=color;

		this.xCenterCircle=x;
		this.x=x-(DIAMETER/2);
		//this.setX(this.x);
		
		this.yCenterCircle=y;
		this.y=y-(DIAMETER/2);
		//sthis.setY(this.y);

		
		this.paint=new Paint();
		setWillNotDraw(true);
		/*GradientDrawable gd = new GradientDrawable();
	    gd.setCornerRadius(270);
	    gd.setColor(this.color);
		this.setLayoutParams(new ViewGroup.LayoutParams(DIAMETER, DIAMETER));
		this.setBackground(gd);*/
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		this.paint.setColor(this.color);
		this.paint.setStrokeWidth(DIAMETER);

		canvas.drawCircle(this.xCenterCircle, this.yCenterCircle, DIAMETER/2, this.paint);
		
	}
	
	
	
		
	public static int getDiameter(){
		return DIAMETER;
	}

	public float getXCenterCircle() {
		return this.xCenterCircle;
	}

	public float getYCenterCircle() {
		return this.yCenterCircle;
	}
	
}
