package com.example.traficmetro.customview;

import com.example.traficmetro.Constants;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

/**
 * Custom view which represents the station. Draw a little circle on canvas.
 * @author YPierru
 *
 */
public class StationView extends View {
	
	//private static final int DIAMETER=15;
	private float xCenterCircle;
	private float x,y;
	private float yCenterCircle;
	private Paint paint;
	private int color;
	
	public StationView(Context ct, float x, float y, int color){
		super(ct);
		
		this.color=color;
		this.xCenterCircle=x;
		this.yCenterCircle=y;
		
		this.paint=new Paint();
		
		setWillNotDraw(true);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		this.paint.setColor(this.color);
		this.paint.setStrokeWidth(Constants.STATIONVIEW_DIAMETER);

		canvas.drawCircle(this.xCenterCircle, this.yCenterCircle, Constants.STATIONVIEW_RADIUS, this.paint);
	}

	/**
	 * Returns the X coordinate of the station (the center of the circle)
	 * @return X coo center circle
	 */
	public float getXCenterCircle() {
		return this.xCenterCircle;
	}

	/**
	 * Returns the Y coordinate of the station (the center of the circle)
	 * @return Y coo center circle
	 */
	public float getYCenterCircle() {
		return this.yCenterCircle;
	}
	
}
