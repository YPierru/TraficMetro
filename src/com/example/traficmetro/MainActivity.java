package com.example.traficmetro;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.traficmetro.data.Line;
import com.example.traficmetro.data.MapMetro;

public class MainActivity extends Activity {

	private MapMetro mapMetro;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			
		if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
        	getActionBar().hide();
        }
	
		super.onCreate(savedInstanceState);
		
		this.mapMetro = new MapMetro(this);

		try {
			createLayout();
		} catch (XmlPullParserException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setContentView(this.mapMetro);
	}
	
	public void createLayout() throws XmlPullParserException, IOException{
		
		/*
		 * Etape 1 : construire les stations à partir du fichier XML
		 */
		
		XmlResourceParser parserXML= getResources().getXml(R.xml.stationdata);
		parserXML.next();
		
		int eventType=XmlPullParser.END_DOCUMENT;
		eventType = parserXML.getEventType();
		
		Line currentLine=null;
		while(eventType!=XmlPullParser.END_DOCUMENT){

			if(eventType==XmlPullParser.START_TAG && parserXML.getName().equalsIgnoreCase("Line")){
				
				currentLine = new Line(this,
									   parserXML.getAttributeIntValue(null, "redCode", 0), 
									   parserXML.getAttributeIntValue(null, "greenCode", 0), 
									   parserXML.getAttributeIntValue(null, "blueCode", 0), 
									   parserXML.getAttributeValue(null, "name"));
			}
			
			if(eventType==XmlPullParser.START_TAG && parserXML.getName().equalsIgnoreCase("Station")){
				currentLine.createThenAddStation(currentLine.getName(), 
 					   							 parserXML.getAttributeValue(null, "name"), 
 					   							 parserXML.getAttributeIntValue(null, "x", 0), 
 					   							 parserXML.getAttributeIntValue(null, "y", 0), 
 					   							 parserXML.getAttributeBooleanValue(null, "terminus", false));
			}
			
			if(eventType==XmlPullParser.START_TAG && parserXML.getName().equalsIgnoreCase("IntermediaryPoint")){
				currentLine.addLinePoint(parserXML.getAttributeIntValue(null, "x", 0), 
 					   					 parserXML.getAttributeIntValue(null, "y", 0));
			}
			
			if(eventType==XmlPullParser.END_TAG && parserXML.getName().equalsIgnoreCase("Line")){
				this.mapMetro.addLine(currentLine);
			}
			
			eventType=parserXML.next();
			
		}
		
		
		/*
		 * Etape 2 : construire les lignes joignant les stations entre elles
		 */
		
		this.mapMetro.buildLayout();
	}	
	
}
