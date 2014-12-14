package com.yanp.traficmetro.data;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yanp.traficmetro.AnimationManager;
import com.yanp.traficmetro.Constants;
import com.yanp.traficmetro.R;
import com.yanp.traficmetro.customview.GreyPanel;
import com.yanp.traficmetro.customview.PanelButtonAddComment;
import com.yanp.traficmetro.customview.PanelInfoStations;
import com.yanp.traficmetro.customview.PanelListComments;

/**
 * Represents the map drawn in the first view
 * @author YPierru
 *
 */
public class MapMetro extends RelativeLayout{
		
	private final float MINIMUM_SCALE_FACTOR=1.50f;//0.81
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
    
    private boolean touchEnable=true;
    
    /**
     * Var used for the pinch-to-zoom
     */
    private ScaleGestureDetector scaleDetector;
	private float scaleFactor = 1.50f;
	
	/**
	 * Var used for moving the map
	 */
    private float lastTouchX;
    private float lastTouchY;
    private float posX;
    private float posY;
    private static final int INVALID_POINTER_ID = -1;
    private int activePointerId = INVALID_POINTER_ID;    
    
    /**
     * Graphics elements
     */
    
    private PanelButtonAddComment buttonAddComment;
    private GreyPanel greyPanel;
    private PanelInfoStations panelInfoStations;
    private PanelListComments panelListComments;
    
    private boolean panelInfoDisplay=false;

	private AnimationManager animationManager;
    
	public MapMetro(Context context, int statusBarHeight, AnimationManager animationManager){
		super(context);
		this.setBackgroundColor(Color.WHITE);
		this.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		

        setHeightWidthScreen();
        initMaxCooValue();
		this.statusBarHeight=statusBarHeight;
		this.animationManager=animationManager;
		
		
		requestLayout();
		
		this.greyPanel=new GreyPanel(getContext(), this.animationManager, this);
		this.panelInfoStations=new PanelInfoStations(getContext(), this.widthScreen, this.heightScreen, this.animationManager);
	    this.buttonAddComment = new PanelButtonAddComment(getContext(), this.widthScreen, this.heightScreen, this.animationManager);
		this.panelListComments = new PanelListComments(getContext(), this.widthScreen, this.heightScreen, this.animationManager);
	    
		this.listLines = new ArrayList<>();
		scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
	}	
	
	/**
	 * Display the informations panel on the screen
	 */
	public void addInformationsPanel(){
		this.panelInfoDisplay=true;
		this.touchEnable=false;
		
		
		
		addGreyPanel();
		addListViewComments();
		addTextViewInfoStation();
		addButtonAddComment();
		
	}
	
	private void addGreyPanel(){
	    this.addView(this.greyPanel);
		this.greyPanel.appear();
	}
	
	/**
	 * Add and display the list of the comments for the station selected
	 */
	private void addListViewComments(){
	    this.addView(this.panelListComments);
	    this.panelListComments.appear();
	}
	
	/**
	 * Add and display informations about the station selected
	 */
	private void addTextViewInfoStation(){
		this.addView(this.panelInfoStations);
		this.panelInfoStations.appear();
	}
	
	/**
	 * Add and display the button for adding a new comment
	 */
	private void addButtonAddComment(){
		this.addView(this.buttonAddComment);
		this.buttonAddComment.appear();
	}
	
	
	/**
	 * Remove the panels
	 */
	public void removePanelInfo(){
		
		
		this.greyPanel.disappear();
		this.panelInfoStations.disappear();
		this.buttonAddComment.disappear();
		this.panelListComments.disappear();
		
		this.removeView(this.panelInfoStations);
		this.removeView(this.panelListComments);
		this.removeView(this.buttonAddComment);
		this.removeView(this.greyPanel);

		this.panelInfoDisplay = false;
		this.touchEnable=true;
	}
	
	
	public boolean isPanelInfoDisplay(){
		return this.panelInfoDisplay;
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
		
		if(this.touchEnable){
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
