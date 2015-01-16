package com.yanp.traficmetro.customview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yanp.traficmetro.AnimationManager;
import com.yanp.traficmetro.Constants;
import com.yanp.traficmetro.R;

public class PanelButtonAddComment extends Button {

	private AnimationManager animationManager;
    private Context context;
	
	public PanelButtonAddComment(Context context, int widthScreen, int heightScreen, AnimationManager animationManager) {
		super(context);
		this.animationManager=animationManager;
        this.context=context;
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,      
				RelativeLayout.LayoutParams.WRAP_CONTENT
		);
		
		params.setMargins(	(int)(widthScreen*Constants.MARGIN_PURCENTAGE_LEFT_BTN),
							(int)(heightScreen*Constants.MARGIN_PURCENTAGE_TOP_BTN),
							(int)(widthScreen*Constants.MARGIN_PURCENTAGE_RIGHT_BTN),
							(int)(heightScreen*Constants.MARGIN_PURCENTAGE_BOTTOM_BTN));
		
		this.setLayoutParams(params);
		this.setBackgroundResource(R.drawable.round_button_add);
		this.setTextColor(Color.WHITE);
		this.setTextSize(26);
		this.setText("+");
		
		this.setOnClickListener(new ButtonAddClickListener());

		this.setVisibility(View.GONE);
	}
	
	public void appear(){
		this.setVisibility(View.VISIBLE);
		this.startAnimation(this.animationManager.getAnimation(R.anim.animationbtnaddappear));
	}
	
	public void disappear(){
		this.startAnimation(this.animationManager.getAnimation(R.anim.animationbtnadddisappear));
	}
	
	private class ButtonAddClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {

        }
		
	}

}
