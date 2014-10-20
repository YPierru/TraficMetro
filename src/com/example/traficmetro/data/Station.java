package com.example.traficmetro.data;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.example.traficmetro.customview.StationView;

/**
 * Contains the data of a station. Which include his custom view.
 * @author YPierru
 *
 */
public class Station {

	private String name;
	private StationView stationView;
	private boolean terminus;
	private String lineName;
	private ArrayList<Line> mListMembershipLines;
	
	public Station(Context context, String line, String name, float x, float y, boolean terminus, ArrayList<Line> listMembershipLines){

		this.name=name;
		this.lineName=line;
		this.terminus=terminus;
		this.mListMembershipLines=listMembershipLines;
		
		if(this.mListMembershipLines.size()>1){
			this.stationView = new StationView(context, x, y, Color.argb(255,255, 255, 255));
		}else{
			this.stationView = new StationView(context, x, y, this.mListMembershipLines.get(0).getColor());
		}
		
		/**
		 * Display informations when the user touch the station
		 */
		//this.stationView.setOnTouchListener(new OnTouchStationListener(context, this.name, this.line));
	}

	/**
	 * Returns the name of the station
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the stationView
	 * @return
	 */
	public StationView getStationView() {
		return stationView;
	}

	/**
	 * Return yes if the station is a terminus of his line, false either
	 * @return
	 */
	public boolean isTerminus() {
		return terminus;
	}

	/**
	 * Return the line's name of the station
	 * @return
	 */
	public String getLineName() {
		return lineName;
	}
	
	
	/**
	 * Manage the touch on the station
	 * @author YPierru
	 *
	 */
	public class OnTouchStationListener implements OnTouchListener {
		
		private Context mCt;
		private String mName;
		private String mLine;
		
		public OnTouchStationListener(Context ct, String name, String line){
			mCt=ct;
			mName=name;
			mLine=line;
		}
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Toast.makeText(mCt, "Station : "+mName+"\nLigne : "+mLine, Toast.LENGTH_SHORT).show();
			;
			return false;
		}

	}
	
}
