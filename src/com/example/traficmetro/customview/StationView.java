package com.example.traficmetro.customview;

import com.example.mapmetro.R.drawable;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class StationView extends View {

	public StationView(Context ct, float x, float y, int diameter){
		super(ct);
		this.setX(x);
		this.setY(y);
		this.setLayoutParams(new ViewGroup.LayoutParams(diameter, diameter));
		this.setBackground(getResources().getDrawable(drawable.station_shape));
	}
	
}
