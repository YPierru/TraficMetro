package com.example.traficmetro.data;

import java.util.ArrayList;

import com.example.traficmetro.customview.StationView;

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

public class MapMetro extends RelativeLayout{

	private ArrayList<Line> listLines;
	private ScaleGestureDetector mScaleDetector;
	private float mScaleFactor = 1.f;
    private float mPosX;
    private float mPosY;
    
    private Paint p= new Paint();
    private Rect rect;
    private float mLastTouchX;
    private float mLastTouchY;
    int[] xyLoc = new int[2];
    private static final int INVALID_POINTER_ID = -1;

    // The active pointer is the one currently moving our object.
    private int mActivePointerId = INVALID_POINTER_ID;
	
	public MapMetro(Context context){
		super(context);
		this.setBackgroundColor(Color.WHITE);
		this.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		/*this.setHorizontalScrollBarEnabled(true);
		this.setVerticalScrollBarEnabled(true);
		TypedArray a = context.obtainStyledAttributes(R.styleable.View);
	    a.recycle();*/
	    setWillNotDraw(false);
		this.listLines = new ArrayList<>();
		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(ev);
        
        final int action = ev.getAction();
        
        switch (action & MotionEvent.ACTION_MASK) {
	        case MotionEvent.ACTION_DOWN: {
	            final float x = ev.getX();
	            final float y = ev.getY();
	            
	            mLastTouchX = x;
	            mLastTouchY = y;
	            mActivePointerId = ev.getPointerId(0);
	            break;
	        }
	            
	        case MotionEvent.ACTION_MOVE: {
	            final int pointerIndex = ev.findPointerIndex(mActivePointerId);
	            final float x = ev.getX(pointerIndex);
	            final float y = ev.getY(pointerIndex);
	
	            // Only move if the ScaleGestureDetector isn't processing a gesture.
	            if (!mScaleDetector.isInProgress()) {
	                final float dx = x - mLastTouchX;
	                final float dy = y - mLastTouchY;
	
	                mPosX += dx;
	                mPosY += dy;
	
	                invalidate();
	            }
	            
	            if(mPosX<-68+StationView.getDiameter()/2){
	            	Log.d("DEBUUUUUG", "MPOSX="+mPosX);
	            	mPosX=-68+StationView.getDiameter()/2;
	            }
	            
	            if(mPosY<-182+StationView.getDiameter()/2){
	            	Log.d("DEBUUUUUG", "MPOSY="+mPosY);
	            	mPosY=-182+StationView.getDiameter()/2;
	            }
	
	            mLastTouchX = x;
	            mLastTouchY = y;
	
	            break;
	        }
	            
	        case MotionEvent.ACTION_UP: {
	            mActivePointerId = INVALID_POINTER_ID;
	            break;
	        }
	            
	        case MotionEvent.ACTION_CANCEL: {
	            mActivePointerId = INVALID_POINTER_ID;
	            break;
	        }
	        
	        case MotionEvent.ACTION_POINTER_UP: {
	            final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) 
	                    >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
	            final int pointerId = ev.getPointerId(pointerIndex);
	            if (pointerId == mActivePointerId) {
	                // This was our active pointer going up. Choose a new
	                // active pointer and adjust accordingly.
	                final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
	                mLastTouchX = ev.getX(newPointerIndex);
	                mLastTouchY = ev.getY(newPointerIndex);
	                mActivePointerId = ev.getPointerId(newPointerIndex);
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
        canvas.translate(mPosX, mPosY);
	    canvas.scale(mScaleFactor, mScaleFactor);

	    for(int i=0;i<this.listLines.size();i++){
	    	
	    	this.listLines.get(i).getLineView().draw(canvas);
	    	
	    	for(int j=0;j<this.listLines.get(i).getListStations().size();j++){
	    		this.listLines.get(i).getListStations().get(j).getStationView().draw(canvas);
	    	}
	    	
	    }

	   // p.setColor(Color.RED);
	    
	    //canvas.drawRect(rect,p);
        
	    canvas.restore();
	}
	
	public void addLine(Line line){
		this.listLines.add(line);
	}
	
	
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
	        mScaleFactor *= detector.getScaleFactor();

	        // Don't let the object get too small or too large.
	        mScaleFactor = Math.max(0.81f, Math.min(mScaleFactor, 3.0f));
	        invalidate();
	        return true;
	    }
	}
	
}
