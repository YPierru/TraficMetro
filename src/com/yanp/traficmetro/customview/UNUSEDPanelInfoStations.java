package com.yanp.traficmetro.customview;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.yanp.traficmetro.AnimationManager;
import com.yanp.traficmetro.Constants;
import com.yanp.traficmetro.R;

public class UNUSEDPanelInfoStations extends TextView {
	
	private AnimationManager animationManager;

	public UNUSEDPanelInfoStations(Context context, int widthScreen, int heightScreen, AnimationManager animationManager) {
		super(context);
		this.animationManager=animationManager;
		LayoutParams params = new LayoutParams(
		        LayoutParams.MATCH_PARENT,      
		        LayoutParams.MATCH_PARENT
		);
		params.setMargins(	(int)(widthScreen*Constants.MARGIN_PURCENTAGE_LEFT_TV),
							(int)(heightScreen*Constants.MARGIN_PURCENTAGE_TOP_TV),
							(int)(widthScreen*Constants.MARGIN_PURCENTAGE_RIGHT_TV),
							(int)(heightScreen*Constants.MARGIN_PURCENTAGE_BOTTOM_TV));
		
		this.setLayoutParams(params);
		this.setBackgroundColor(Color.LTGRAY);
		
		this.setText("BLABLABLA JE SUIS UNE STATION :3");
		this.setVisibility(View.GONE);
	}
	
	public void appear(){
		this.setVisibility(View.VISIBLE);
		this.startAnimation(this.animationManager.getAnimation(R.anim.animationinfostationappear));
	}
	
	public void disappear(){
		this.startAnimation(this.animationManager.getAnimation(R.anim.animationinfostationdisappear));
		this.setVisibility(View.GONE);
	}

}
