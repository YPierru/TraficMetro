package com.yanp.traficmetro;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.yanp.traficmetro.data.Line;
import com.yanp.traficmetro.data.MapMetro;

/**
 * First activity of the app. Display the map.
 * @author YPierru
 *
 */
public class MainActivity extends Activity {

	private MapMetro mapMetro;
	private AnimationManager animationManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			
		hideActionBar();
	
		super.onCreate(savedInstanceState);
		
		this.animationManager = new AnimationManager(this);
		this.mapMetro = new MapMetro(this,getStatusBarHeight(), this.animationManager);

		try {
			createLayout();
		} catch (XmlPullParserException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setContentView(this.mapMetro);
	}
	
	/**
	 * Hide the action bar, according to the SDK version
	 */
	private void hideActionBar(){
		if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
        	getActionBar().hide();
        }
	}
	
	private int getStatusBarHeight() { 
	      int result = 0;
	      int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
	      if (resourceId > 0) {
	          result = getResources().getDimensionPixelSize(resourceId);
	      } 
	      return result;
	} 
	
	
	/**
	 * Fetch data from the xml file and fill the view with the drawing
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public void createLayout() throws XmlPullParserException, IOException{
		
		/**
		 * We get the xml file, skip the node.
		 */
		XmlResourceParser parserXML= getResources().getXml(R.xml.stationdatacopie);
		parserXML.next();
		
		/**
		 * Default value for the eventType var, then actual value
		 */
		int eventType=XmlPullParser.END_DOCUMENT;
		eventType = parserXML.getEventType();
		
		/**
		 * The line which will be filled with the xml's data
		 */
		Line currentLine=null;
		
		while(eventType!=XmlPullParser.END_DOCUMENT){

			/**
			 * According to the differents nodes...
			 */
			if(eventType==XmlPullParser.START_TAG && parserXML.getName().equalsIgnoreCase("Line")){
				/**
				 * Creation a new line
				 */
				currentLine = new Line(this,
									   parserXML.getAttributeIntValue(null, "redCode", 0), 
									   parserXML.getAttributeIntValue(null, "greenCode", 0), 
									   parserXML.getAttributeIntValue(null, "blueCode", 0), 
									   parserXML.getAttributeValue(null, "name"),
									   this.mapMetro);
			}
			
			if(eventType==XmlPullParser.START_TAG && parserXML.getName().equalsIgnoreCase("Station")){
				/**
				 * Creation of a new station in this line
				 */
				currentLine.createThenAddStation(parserXML.getAttributeValue(null, "name"), 
 					   							 parserXML.getAttributeIntValue(null, "x", 0), 
 					   							 parserXML.getAttributeIntValue(null, "y", 0), 
 					   							 parserXML.getAttributeBooleanValue(null, "terminus", false));
			}
			
			if(eventType==XmlPullParser.START_TAG && parserXML.getName().equalsIgnoreCase("IntermediaryPoint")){
				/**
				 * Creation of an intermediary point in this line (this point represents only a new direction, it's not a station)
				 */
				currentLine.addLinePoint(parserXML.getAttributeIntValue(null, "x", 0), 
 					   					 parserXML.getAttributeIntValue(null, "y", 0));
			}
			
			if(eventType==XmlPullParser.END_TAG && parserXML.getName().equalsIgnoreCase("Line")){
				/**
				 * Line is complete, add to the custom relativeLayout
				 */
				this.mapMetro.addLine(currentLine);
			}
			
			eventType=parserXML.next();
			
		}
		
		/**
		 * The customs views are added to the relativeLayout.
		 */
		this.mapMetro.buildLayout();
	}	
	
	/**
	 * Override the back button.
	 * If the panel is display, remove it.
	 * Else, natural behavior
	 */
	@Override
	public void onBackPressed() {
		if(this.mapMetro.isPanelInfoDisplay()){
			this.mapMetro.removePanelInfo();
		}else{
			super.onBackPressed();
		}
	}
	
}
