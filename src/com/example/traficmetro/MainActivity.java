package com.example.traficmetro;

import java.util.ArrayList;

import com.example.traficmetro.customview.LineView;
import com.example.traficmetro.customview.StationView;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	private int mMaxX, mMaxY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		this.mMaxX = size.x;
		this.mMaxY = size.y;

		// station.setOnTouchListener(new onTouchStationListener());
		
		
		//On créer le layout
		RelativeLayout mLayout = new RelativeLayout(this);
		mLayout.setBackgroundColor(Color.WHITE);
		
		
		
		//Points au pif
		double[] xy0={68*1.5,182*1.5};
		double[] xy1={90*1.5,198*1.5};
		double[] xy2={114*1.5,215*1.5};
		double[] xy3={138*1.5,232*1.5};
		double[] xy4={165*1.5,252*1.5};
		
		double[] xy5={204*1.5,279*1.5};
		double[] xy6={233*1.5,301*1.5};
		double[] xy7={270*1.5,326*1.5};
		double[] xy8={306*1.5,352*1.5};
		
		ArrayList<double[]> listTarget= new ArrayList<>();
		
		listTarget.add(xy0);
		listTarget.add(xy1);
		listTarget.add(xy2);
		listTarget.add(xy3);
		listTarget.add(xy4);
		listTarget.add(xy5);
		listTarget.add(xy6);
		listTarget.add(xy7);
		listTarget.add(xy8);
		
		int diameter=30;
		
		for(int i=0;i<listTarget.size();i++){
			mLayout.addView(new StationView(getApplicationContext(), (int)listTarget.get(i)[0],(int)listTarget.get(i)[1], diameter));
		}
	
		double xTarget;
		double yTarget;
		double width;
		double xLine;
		double yLine;
		float rotation;
		
		for(int i=0;i<listTarget.size()-1;i++){
			
			xLine=calculateXLine(listTarget.get(i)[0], diameter);
			yLine=calculateYLine(listTarget.get(i)[1], diameter);
			
			xTarget=calculateXLine(listTarget.get(i+1)[0], diameter);
			yTarget=calculateYLine(listTarget.get(i+1)[1], diameter);
			
			//Formule pour avoir la longueur de la line
			width = calculateLineWidth(xLine, yLine, xTarget, yTarget);
			
			//Formule pour avoir l'angle de rotation
			rotation = calculateLineRotation(xLine, yLine, xTarget, yTarget, width);
			
			//On ajoute la ligne sur le layout
			mLayout.addView(new LineView(this, (int)xLine, (int)yLine, (int)width, rotation));
			
		}
	
		setContentView(mLayout);
	}

	public double calculateXLine(double x, int diam){
		return x+diam/2;
	}
	
	public double calculateYLine(double y, int diam){
		return y+diam+2-LineView.getLineHeight()/2;
	}
	
	public double calculateLineWidth(double xL, double yL, double xT, double yT){
		return Math.sqrt(Math.pow((xL-xT),2)+Math.pow(yL-yT, 2));
	}
	
	public float calculateLineRotation(double xL, double yL, double xT, double yT, double width){
		float rot= (float)Math.toDegrees(Math.asin(Math.abs(xL-xT)/width)); 
		/*if(xT>=xL && yT>=yL){
			rot-=90;
		}else if(xT<xL && yT>yL){
			rot+=90;
		}else if(xT<=xL && yT<yL){
			rot+=180;
		}*/
		
		return rot;
	}
	
	public class onTouchStationListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Toast.makeText(getApplicationContext(), "Yep !", Toast.LENGTH_SHORT)
					.show();
			;
			return false;
		}

	}
}
