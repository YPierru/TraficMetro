package com.example.traficmetro.customview;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;

import com.example.traficmetro.R.drawable;

public class StationView extends View {
	
	private static final int DIAMETER=40;
	
	public StationView(Context ct, float x, float y, int color){
		super(ct);
		this.setX(x);
		this.setY(y);
		
		GradientDrawable gd = new GradientDrawable(
	            GradientDrawable.Orientation.TOP_BOTTOM,
	            new int[] {0xFFFFBA11,0xFFFFBA11});
	    gd.setCornerRadius(270);

		
		//this.setBackgroundColor(color);
		this.setLayoutParams(new ViewGroup.LayoutParams(DIAMETER, DIAMETER));
		this.setBackground(gd);
	}
	
	public static int getDiameter(){
		return DIAMETER;
	}
}
