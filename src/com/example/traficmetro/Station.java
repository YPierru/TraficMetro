package com.example.traficmetro;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.example.traficmetro.customview.StationView;

public class Station {

	private String name;
	private StationView stationView;
	private boolean terminus;
	private String line;
	private ArrayList<Line> mListMembershipLines;
	
	public Station(Context context, String line, String name, float x, float y, boolean terminus, ArrayList<Line> listMembershipLines){
		
		this.name=name;
		this.line=line;
		this.terminus=terminus;
		this.mListMembershipLines=listMembershipLines;
		
		if(this.mListMembershipLines.size()>1){
			this.stationView = new StationView(context, x, y, Color.argb(255,255, 255, 255));
		}else{
			this.stationView = new StationView(context, x, y, this.mListMembershipLines.get(0).getColor());
		}
		
		
		this.stationView.setOnTouchListener(new OnTouchStationListener(context, this.name, this.line));
	}

	public String getName() {
		return name;
	}

	public StationView getStationView() {
		return stationView;
	}

	public boolean isTerminus() {
		return terminus;
	}

	public String getLine() {
		return line;
	}
	

	
	
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
