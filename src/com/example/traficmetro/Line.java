package com.example.traficmetro;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;

public class Line {

	private String name;
	private int rgbColor;
	private ArrayList<Station> listStations;
	private int numberOfStations;
	
	public Line(int r, int g, int b, String name){
		this.name=name;
		this.rgbColor = Color.argb(255, r, g, b);
		this.listStations=new ArrayList<>();
	}
	
	public void createThenAddStation(Context context, String line, String name, float x, float y, boolean terminus){
		
		ArrayList<Line> l = new ArrayList<>();
		l.add(this);
		this.listStations.add(new Station(context, line, name, x, y, terminus, l));
	}
	
	public int getColor(){
		return rgbColor;
	}
	
	public ArrayList<Station> getListStations(){
		return this.listStations;
	}
	
	public String getName(){
		return this.name;
	}
}
