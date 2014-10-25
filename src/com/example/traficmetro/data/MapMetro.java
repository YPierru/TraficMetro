package com.example.traficmetro.data;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.traficmetro.Constants;
import com.example.traficmetro.customview.StationView;

/**
 * Represents the map drawn in the first view
 * @author YPierru
 *
 */
public class MapMetro extends RelativeLayout{
		
	private final float MINIMUM_SCALE_FACTOR=0.81f;
	private final float MAXIMUM_SCALE_FACTOR=3.1f;
	private final float SIZE_DIVISOR=1.2f;

	private ArrayList<Line> listLines;

	//X coordinate most to the right
    private int xMaxRight;
    //X coordinate most to the left
    private int xMaxLeft;
    //Y coordinate most to the top
    private int yMaxTop;
    //Y coordinate most to the bottom 
    private int yMaxBottom;
    
    private int statusBarHeight;
    private int heightScreen;
    private int widthScreen;
    
    /**
     * Var used for the pinch-to-zoom
     */
    private ScaleGestureDetector scaleDetector;
	private float scaleFactor = 1.f;
	
	/**
	 * Var used for moving the map
	 */
    private float lastTouchX;
    private float lastTouchY;
    private float posX;
    private float posY;
    private static final int INVALID_POINTER_ID = -1;
    private int activePointerId = INVALID_POINTER_ID;
	
    
    
	public MapMetro(Context context, int statusBarHeight){
		super(context);
		this.setBackgroundColor(Color.WHITE);
		this.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

        setHeightWidthScreen();
        initMaxCooValue();
        
		this.statusBarHeight=statusBarHeight;
	    
		this.listLines = new ArrayList<>();
		scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
	}
	
	/**
	 * Set the H/W var
	 */
	private void setHeightWidthScreen(){
		this.widthScreen = getResources().getDisplayMetrics().widthPixels;
        this.heightScreen = getResources().getDisplayMetrics().heightPixels;
	}
	
	/**
	 * Init the max coo value.
	 */
	private void initMaxCooValue(){
        this.xMaxLeft=this.widthScreen;
        this.xMaxRight=0;
		this.yMaxTop=this.heightScreen;
		this.yMaxBottom=0;
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        scaleDetector.onTouchEvent(ev);
        
        final int action = ev.getAction();
        
        /**
         * Define the new coordinate (posX/pos) of the map
         */
        switch (action & MotionEvent.ACTION_MASK) {
        
	        case MotionEvent.ACTION_DOWN: {
	            final float x = ev.getX();
	            final float y = ev.getY();
	            
	            lastTouchX = x;
	            lastTouchY = y;
	            activePointerId = ev.getPointerId(0);
	            break;
	        }
	            
	        case MotionEvent.ACTION_MOVE: {
	            final int pointerIndex = ev.findPointerIndex(activePointerId);
	            final float x = ev.getX(pointerIndex);
	            final float y = ev.getY(pointerIndex);
	
	            // Only move if the ScaleGestureDetector isn't processing a gesture.
	            if (!scaleDetector.isInProgress()) {
	                final float dx = x - lastTouchX;
	                final float dy = y - lastTouchY;
	
	                posX += dx;
	                posY += dy;
	
	                invalidate();
	            }
	            
	            /**
	             * Replace the map when it's out of the screen
	             */
	            
	            if(posX<=0-xMaxRight*scaleFactor/SIZE_DIVISOR){
	            	posX=-xMaxRight*scaleFactor/SIZE_DIVISOR;
	            }
	            
	            if(posX>=(widthScreen-xMaxLeft*scaleFactor)/SIZE_DIVISOR){
	            	posX=(widthScreen-xMaxLeft*scaleFactor)/SIZE_DIVISOR;
	            }
	            
	            if(posY<=-yMaxBottom*scaleFactor/SIZE_DIVISOR){
	            	posY=-yMaxBottom*scaleFactor/SIZE_DIVISOR;
	            }
	            
	            if(posY>=(heightScreen-statusBarHeight-yMaxTop*scaleFactor)/SIZE_DIVISOR){
	            	posY=(heightScreen-statusBarHeight-yMaxTop*scaleFactor)/SIZE_DIVISOR;
	            }
	
	            lastTouchX = x;
	            lastTouchY = y;
	
	            break;
	        }
	            
	        case MotionEvent.ACTION_UP: {
	            activePointerId = INVALID_POINTER_ID;
	            break;
	        }
	            
	        case MotionEvent.ACTION_CANCEL: {
	            activePointerId = INVALID_POINTER_ID;
	            break;
	        }
	        
	        case MotionEvent.ACTION_POINTER_UP: {
	            final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) 
	                    >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
	            final int pointerId = ev.getPointerId(pointerIndex);
	            if (pointerId == activePointerId) {
	                // This was our active pointer going up. Choose a new
	                // active pointer and adjust accordingly.
	                final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
	                lastTouchX = ev.getX(newPointerIndex);
	                lastTouchY = ev.getY(newPointerIndex);
	                activePointerId = ev.getPointerId(newPointerIndex);
	            }
	            break;
	        }
        }
        
        return true;
    }
	
	@Override
	public void onDraw(Canvas canvas) {
	    super.onDraw(canvas);
	    
	    canvas.save();
	    
	    //Set the new coordinate
        canvas.translate(posX, posY);
        
        //Multiply the coordinate with the scale factor
	    canvas.scale(scaleFactor, scaleFactor);

	    //Each lines are drawn
	    for(int i=0;i<this.listLines.size();i++){
	    	
	    	this.listLines.get(i).getLineView().draw(canvas);
	    	
	    	//Each stations are drawn
	    	for(int j=0;j<this.listLines.get(i).getListStations().size();j++){
	    		this.listLines.get(i).getListStations().get(j).getStationView().mydraw(canvas,posX,posY,scaleFactor);
	    	}
	    	
	    }
        
	    canvas.restore();
	}
	
	/**
	 * Add a new line to the map.
	 * @param line
	 */
	public void addLine(Line line){
		searchMaxCoo(line);
		this.listLines.add(line);
	}
	
	/**
	 * Search and set values of the max (x left/right, y top/bottom) coo
	 * @param line
	 */
	private void searchMaxCoo(Line line){
		ArrayList<Station> ls = line.getListStations();
		
		for(int i=0;i<ls.size();i++){
			if(xMaxLeft>ls.get(i).getStationView().getXCenterCircle()){
				xMaxLeft=(int)ls.get(i).getStationView().getXCenterCircle();
			}
			if(xMaxRight<ls.get(i).getStationView().getXCenterCircle()){
				xMaxRight=(int)ls.get(i).getStationView().getXCenterCircle();
			}
			if(yMaxTop>ls.get(i).getStationView().getYCenterCircle()){
				yMaxTop=(int)ls.get(i).getStationView().getYCenterCircle();
			}
			if(yMaxBottom<ls.get(i).getStationView().getYCenterCircle()){
				yMaxBottom=(int)ls.get(i).getStationView().getYCenterCircle();
			}
		}
		
	}
	
	/**
	 * Add the view (lineView and stationView) to the relativeLayout.
	 * Is used for the touch event on the map
	 */
	public void buildLayout(){
		ArrayList<Station> listStationsOnCurrentLine;
		
		for(int i=0;i<this.listLines.size();i++){
			
			listStationsOnCurrentLine = this.listLines.get(i).getListStations();
			
			for(int j=0;j<listStationsOnCurrentLine.size();j++){
				this.addView(listStationsOnCurrentLine.get(j).getStationView());
			}
			
			this.addView(this.listLines.get(i).getLineView());
		}
		
	}

	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
	    @Override
	    public boolean onScale(ScaleGestureDetector detector) {
	        scaleFactor *= detector.getScaleFactor();

	        // Don't let the object get too small or too large.
	        scaleFactor = Math.max(MINIMUM_SCALE_FACTOR, Math.min(scaleFactor,MAXIMUM_SCALE_FACTOR));
	        invalidate();
	        return true;
	    }
	}
	
}
