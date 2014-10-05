package com.example.traficmetro.customview;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.GestureDetector;
import android.view.View;
import android.view.ViewGroup;

public class StationView extends View {
	
	private static final int DIAMETER=15;
	private float xCenterCircle;
	private float yCenterCircle;
	private GestureDetector gestureDetector;
	private View.OnTouchListener gestureListener;
	
	public StationView(Context ct, float x, float y, int color){
		super(ct);

		this.xCenterCircle=x+(DIAMETER/2);
		this.setX(x);
		
		this.yCenterCircle=y+(DIAMETER/2);
		this.setY(y);
		
		GradientDrawable gd = new GradientDrawable();
	    gd.setCornerRadius(270);
	    gd.setColor(color);
		this.setLayoutParams(new ViewGroup.LayoutParams(DIAMETER, DIAMETER));
		this.setBackground(gd);
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
