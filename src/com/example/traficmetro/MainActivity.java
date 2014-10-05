package com.example.traficmetro;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.traficmetro.data.Line;
import com.example.traficmetro.data.MapMetro;

public class MainActivity extends Activity {

	private int mMaxX, mMaxY;
	private RelativeLayout mLayout;
	private MapMetro mMapMetro;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
        	getActionBar().hide();
        }
		super.onCreate(savedInstanceState);

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		this.mMaxX = size.x-30;
		this.mMaxY = size.y-30;
		
		this.mMapMetro = new MapMetro(this);

		try {
			this.mLayout=createLayout();
		} catch (XmlPullParserException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setContentView(this.mLayout);
	}
	
	public RelativeLayout createLayout() throws XmlPullParserException, IOException{
		
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
				this.mMapMetro.addLine(currentLine);
			}
			
			eventType=parserXML.next();
			
		}
		
		
		/*
		 * Etape 2 : construire les lignes joignant les stations entre elles
		 */
		
		
		return this.mMapMetro.buildLayout();
	}
	
/*
	public double calculateXLine(double x, int diam){
		return x+diam/2;
	}
	
	public double calculateYLine(double y, int diam){
		return y+diam+2-SegmentLineView.getLineHeight()/2;
	}
	
	public double calculateLineWidth(double xL, double yL, double xT, double yT){
		
		return Math.sqrt(Math.pow((xL-xT),2)+Math.pow(yL-yT, 2));
	}
	
	public float calculateLineRotation(double xL, double yL, double xT, double yT, double width){
		float rot= (float)Math.toDegrees(Math.asin(Math.abs(xL-xT)/width)); 
		if(xT>=xL && yT>=yL){
			rot-=90;
		}else if(xT<xL && yT>yL){
			rot+=90;
		}else if(xT<=xL && yT<yL){
			rot+=180;
		}
		
		return rot;
	}*/
	
	
}
