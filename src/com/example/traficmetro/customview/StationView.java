package com.example.traficmetro.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.traficmetro.Constants;
import com.example.traficmetro.data.Station;

/**
 * Custom view which represents the station. Draw a little circle on canvas.
 * @author YPierru
 *
 */
public class StationView extends View {
	
	/**
	 * Coordinate which are from the xml
	 */
	private float xStatic;
	private float yStatic;
	
	/**
	 * Coordinate calculate when user use pinch to zoom/deplacement
	 */
	private float xDynamic;
	private float yDynamic;
	
	private Paint paint;
	private int color;
	private Station station;
	private float scaleFactor;
	
	/**
	 * Distance between the user XY and the station view. See onTouchEvent.
	 */
	private double distance;
	
	/**
	 * Coordinate of the user's finger on the screen
	 */
	private float xUser;
	private float yUser;
	
	public StationView(Context ct, float x, float y, int color, Station station){
		super(ct);
		
		this.color=color;
		
		this.xStatic=x;
		this.yStatic=y;
		
		this.station=station;
		
		this.paint=new Paint();
		
		setWillNotDraw(true);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		this.paint.setColor(this.color);
		this.paint.setStrokeWidth(Constants.STATIONVIEW_DIAMETER);

		canvas.drawCircle(this.xStatic, this.yStatic, Constants.STATIONVIEW_RADIUS, this.paint);
	}
	
	/**
	 * Draw the view and calculate dynamicaly the new coordinate
	 * @param canvas - the canvas receive from MapMetro
	 * @param posX - translation in X
	 * @param posY - translation in Y
	 * @param scaleFactor - re-scale the coordinate
	 */
	public void mydraw(Canvas canvas, float posX, float posY, float scaleFactor){
		this.scaleFactor=scaleFactor;
		this.xDynamic=(this.xStatic*this.scaleFactor+posX);
		this.yDynamic=(this.yStatic*this.scaleFactor+posY);
		this.draw(canvas);
	}
	
	 @Override
    public boolean onTouchEvent(MotionEvent event) {

		 switch(event.getAction()){
		 
		 case MotionEvent.ACTION_DOWN:{
			 
			 this.xUser=event.getX();
			 this.yUser=event.getY();
			 
			 this.distance = Math.sqrt(Math.pow((this.xUser-this.xDynamic), 2)+ Math.pow((this.yUser-this.yDynamic),2));
			 
			 if(this.distance < Constants.STATIONVIEW_RADIUS*this.scaleFactor){
				 Toast.makeText(getContext(), this.station.getName()+"\n"+this.station.getLineName(), Toast.LENGTH_SHORT).show();
			 }
			 
			 break;
		 }
		 
		 }
		 
        return false;
    }

	/**
	 * Returns the X coordinate of the station (the center of the circle)
	 * @return X coo center circle
	 */
	public float getXCenterCircle() {
		return this.xStatic;
	}

	/**
	 * Returns the Y coordinate of the station (the center of the circle)
	 * @return Y coo center circle
	 */
	public float getYCenterCircle() {
		return this.yStatic;
	}
	
}
