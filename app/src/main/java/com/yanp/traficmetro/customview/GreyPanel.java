package com.yanp.traficmetro.customview;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;

import com.yanp.traficmetro.AnimationManager;
import com.yanp.traficmetro.IPanel;
import com.yanp.traficmetro.R;
import com.yanp.traficmetro.data.MapMetro;

public class GreyPanel extends View implements IPanel {

	private AnimationManager animationManager;
	private MapMetro mapMetroRef;
	
	public GreyPanel(Context context, AnimationManager animationManager, MapMetro mapMetroRef) {
		super(context);
		this.mapMetroRef=mapMetroRef;
		this.animationManager=animationManager;
		LayoutParams params = new LayoutParams(
		        LayoutParams.MATCH_PARENT,      
		        LayoutParams.MATCH_PARENT
		);

		this.setOnClickListener(new GreyPanelClickListener());
		this.setLayoutParams(params);
		this.setBackgroundColor(Color.argb(50, 0, 0, 0));
		
	    
	    this.setVisibility(View.GONE);
	}
	
	public void appear(){
		this.setVisibility(View.VISIBLE);
		this.startAnimation(this.animationManager.getAnimation(R.anim.animationpanelappear));
	}
	
	public void disappear(){
		this.startAnimation(this.animationManager.getAnimation(R.anim.animationpaneldisappear));
	}
	
	private class GreyPanelClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(getVisibility()==View.VISIBLE){
				mapMetroRef.removePanelInfo();
			}
		}
		
	}

}
