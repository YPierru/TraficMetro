package com.yanp.traficmetro.data;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;

import com.yanp.traficmetro.customview.StationView;

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
	private MapMetro mapMetro;
	
	public Station(Context context, String line, String name, float x, float y, boolean terminus, ArrayList<Line> listMembershipLines, MapMetro mapMetro){

		this.name=name;
		this.lineName=line;
		this.terminus=terminus;
		this.mListMembershipLines=listMembershipLines;
		
		this.mapMetro=mapMetro;
		
		if(this.mListMembershipLines.size()>1){
			this.stationView = new StationView(context, x, y, Color.argb(255,255, 255, 255), this);
		}else{
			this.stationView = new StationView(context, x, y, this.mListMembershipLines.get(0).getColor(), this);
		}
		
		/**
		 * Display informations when the user touch the station
		 */
		//this.stationView.setOnTouchListener(new OnTouchStationListener(context, this.name, this.lineName));
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
	
	public void popPanelComments(){
		this.mapMetro.addInformationsPanel();
	}
	
}
