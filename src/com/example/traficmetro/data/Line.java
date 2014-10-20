package com.example.traficmetro.data;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.Log;

import com.example.traficmetro.customview.LineView;


/**
 * Contains the data of a line. Which include his custom view.
 * @author YPierru
 *
 */
public class Line {

	private String name;
	private int rgbColor;
	private ArrayList<Station> listStations;
	private ArrayList<PointF> listLinePoints;
	private int numberOfStations;
	private LineView lineView;
	private Context context;
	
	public Line(Context ct, int r, int g, int b, String name){
		this.context=ct;
		this.name=name;
		this.rgbColor = Color.argb(255, r, g, b);
		this.listStations=new ArrayList<>();
		this.listLinePoints = new ArrayList<>();
	}
	
	/**
	 * Add a new point to the list of the line point
	 * @param x - coordinate
	 * @param y - coordinate
	 */
	public void addLinePoint(float x, float y){
		this.listLinePoints.add(new PointF(x, y));
	}
	
	/**
	 * Create a new station with the parameters, then add this station to the line
	 * @param name of the station
	 * @param x - coordination from XML
	 * @param y - coordinate from XML
	 * @param terminus - true if the station is a terminus, false either
	 */
	public void createThenAddStation(String name, float x, float y, boolean terminus){

		/*
		 * TODO : modifier le passage de la couleur
		 */
		ArrayList<Line> l = new ArrayList<>();
		l.add(this);
		
		Station st = new Station(this.context, this.name, name, x, y, terminus, l);
		
		this.listStations.add(st);
		
		this.addLinePoint(x, y);
	}
	
	/**
	 * Returns the color of the line
	 * @return
	 */
	public int getColor(){
		return rgbColor;
	}
	
	/**
	 * Returns the list of the line's station
	 * @return
	 */
	public ArrayList<Station> getListStations(){
		return this.listStations;
	}
	
	/**
	 * Returns the name of the station
	 * @return
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Create the lineView associate to the line
	 */
	private void createLineView(){
		this.lineView=new LineView(this.context, this.listLinePoints, this.rgbColor);
	}
	
	/**
	 * Returns the lineView associate to the line (create it if needed)
	 * @return
	 */
	public LineView getLineView(){
		createLineView();
		return this.lineView;
	}
}