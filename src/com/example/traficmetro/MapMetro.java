package com.example.traficmetro;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.widget.RelativeLayout;

public class MapMetro {

	private ArrayList<Line> listLines;
	private RelativeLayout mLayout;
	
	public MapMetro(Context context){
		this.mLayout =new RelativeLayout(context);
		this.mLayout.setBackgroundColor(Color.WHITE);
		
		this.listLines = new ArrayList<>();
	}
	
	public void addLine(Line line){
		this.listLines.add(line);
	}
	
	public RelativeLayout buildLayout(){
		for(int i=0;i<this.listLines.size();i++){
			ArrayList<Station> listStationsLine = this.listLines.get(i).getListStations();
			for(int j=0;j<listStationsLine.size();j++){
				System.out.println("yolo");
				this.mLayout.addView(listStationsLine.get(j).getStationView());
			}
		}
		
		return this.mLayout;
	}
	
}
