package com.example.traficmetro;

import java.util.ArrayList;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.traficmetro.customview.LineView;

public class MapMetro {

	private ArrayList<Line> listLines;
	private RelativeLayout mLayout;
	private Context context;
	
	public MapMetro(Context context){
		this.context=context;
		this.mLayout =new RelativeLayout(this.context);
		this.mLayout.setBackgroundColor(Color.WHITE);
		this.mLayout.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		
		this.listLines = new ArrayList<>();
	}
	
	public void addLine(Line line){
		this.listLines.add(line);
	}
	
	public RelativeLayout buildLayout(){
		
		LineView lv;
		double xLine,yLine,widthLine;
		float rotation;
		for(int i=0;i<this.listLines.size();i++){
			ArrayList<Station> listStationsOnCurrentLine = this.listLines.get(i).getListStations();
			for(int j=0;j<listStationsOnCurrentLine.size()-1;j++){
				
				xLine=listStationsOnCurrentLine.get(j).getStationView().getXCenterCircle();
				yLine=calculateYLine(listStationsOnCurrentLine.get(j).getStationView().getYCenterCircle());
				//yLine=listStationsOnCurrentLine.get(j).getStationView().getYCenterCircle();
				widthLine=calculateLineWidth((double)listStationsOnCurrentLine.get(j).getStationView().getXCenterCircle(), 
						(double)listStationsOnCurrentLine.get(j).getStationView().getYCenterCircle(), 
						(double)listStationsOnCurrentLine.get(j+1).getStationView().getXCenterCircle(), 
						(double)listStationsOnCurrentLine.get(j+1).getStationView().getYCenterCircle());
				
				/*System.out.println("xStation="+listStationsOnCurrentLine.get(j).getStationView().getXCenterCircle());
				System.out.println("xLine="+xLine);
				
				System.out.println("yStation="+listStationsOnCurrentLine.get(j).getStationView().getYCenterCircle());
				System.out.println("yLine="+yLine);
				
				System.out.println("xBase="+listStationsOnCurrentLine.get(j).getStationView().getX());
				System.out.println("yBase="+listStationsOnCurrentLine.get(j).getStationView().getY());*/
				
				rotation = calculateLineRotation(xLine,
												yLine, 
												listStationsOnCurrentLine.get(j+1).getStationView().getXCenterCircle(), 
												listStationsOnCurrentLine.get(j+1).getStationView().getYCenterCircle(), 
												widthLine);
				
				lv = new LineView(this.context, 
								  (int)xLine, 
								  (int)yLine, 
								  (int)widthLine, 
								  rotation);
				

				this.mLayout.addView(listStationsOnCurrentLine.get(j).getStationView());
				this.mLayout.addView(listStationsOnCurrentLine.get(j+1).getStationView());
				this.mLayout.addView(lv);
			}
		}
		
		
		
		return this.mLayout;
	}
	
	private double calculateYLine(double y){
		return y-LineView.getLineHeight()/2;
	}
	
	private double calculateLineWidth(double xL, double yL, double xT, double yT){
		
		System.out.println(Math.sqrt(Math.pow((xL-xT),2)+Math.pow(yL-yT, 2)));
		
		return Math.sqrt(Math.pow((xL-xT),2)+Math.pow(yL-yT, 2));
	}
	
	public float calculateLineRotation(double xL, double yL, double xT, double yT, double width){
		float rot= (float)Math.toDegrees(Math.asin(Math.abs(xL-xT)/width)); 
		
		if(xT>=xL && yT>=yL){
			System.out.println("1");
			rot+=30;
		}else if(xT<xL && yT>yL){
			System.out.println("2");
			rot+=90;
		}else if(xT<=xL && yT<yL){
			System.out.println("3");
			rot+=180;
		}
		
		return rot;
	}
	
}
