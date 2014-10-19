package com.example.traficmetro.data;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.Log;

import com.example.traficmetro.customview.LineView;

public class Line {

	private String name;
	private int rgbColor;
	private ArrayList<Station> listStations;
	private ArrayList<PointF> listLinePoints;
	private int numberOfStations;
	private LineView lineView;
	private Context ct;
	
	public Line(Context ct, int r, int g, int b, String name){
		this.ct=ct;
		this.name=name;
		this.rgbColor = Color.argb(255, r, g, b);
		this.listStations=new ArrayList<>();
		this.listLinePoints = new ArrayList<>();
	}
	
	public void addLinePoint(float x, float y){
		this.listLinePoints.add(new PointF(x, y));
	}
	
	
	public void createThenAddStation(String line, String name, float x, float y, boolean terminus){

		/*
		 * TODO : modifier le passage de la couleur
		 */
		ArrayList<Line> l = new ArrayList<>();
		l.add(this);
		
		Station st = new Station(this.ct, line, name, x, y, terminus, l);
		
		this.listStations.add(st);
		
		this.addLinePoint(x, y);
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
	
	public void createLineView(){
		this.lineView=new LineView(this.ct, this.listLinePoints, this.rgbColor);
	}
	
	public LineView getLineView(){
		this.createLineView();
		return this.lineView;
	}
}