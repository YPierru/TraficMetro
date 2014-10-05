package com.example.traficmetro.data;

import java.util.ArrayList;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.traficmetro.R;
import com.example.traficmetro.customview.LineView;

public class MapMetro{

	private ArrayList<Line> listLines;
	private RelativeLayout mLayout;
	private Context context;
	
	public MapMetro(Context context){
		this.context=context;
		this.mLayout =new RelativeLayout(this.context);
		this.mLayout.setBackgroundColor(Color.WHITE);
		this.mLayout.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		/*this.mLayout.setHorizontalScrollBarEnabled(true);
		this.mLayout.setVerticalScrollBarEnabled(true);
		TypedArray a = context.obtainStyledAttributes(R.styleable.View);
	    a.recycle();*/
		this.listLines = new ArrayList<>();
	}
	
	public void addLine(Line line){
		this.listLines.add(line);
	}
	
	
	public RelativeLayout buildLayout(){
		ArrayList<Station> listStationsOnCurrentLine;
		for(int i=0;i<this.listLines.size();i++){
			listStationsOnCurrentLine = this.listLines.get(i).getListStations();
			/*for(int j=0;j<listStationsOnCurrentLine.size();j++){
				this.mLayout.addView(listStationsOnCurrentLine.get(j).getStationView());
			}*/
			this.mLayout.addView(this.listLines.get(i).getLineView());
		}
		return this.mLayout;
	}

	
}
