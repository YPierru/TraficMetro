package com.yanp.traficmetro.customview;

import com.yanp.traficmetro.AnimationManager;
import com.yanp.traficmetro.Constants;
import com.yanp.traficmetro.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

public class UNUSEDPanelButtonAddComment extends Button {

	private AnimationManager animationManager;
	
	public UNUSEDPanelButtonAddComment(Context context, int widthScreen, int heightScreen, AnimationManager animationManager) {
		super(context);
		LayoutParams params = new LayoutParams(
		        LayoutParams.WRAP_CONTENT,      
		        LayoutParams.WRAP_CONTENT
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
		
		this.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Toast.makeText(getContext(), "Ajout d'un comzz", Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		this.setVisibility(View.GONE);
	}
	
	public void appear(){
		this.setVisibility(View.VISIBLE);
		this.startAnimation(this.animationManager.getAnimation(R.anim.animationbtnaddappear));
	}
	
	public void disappear(){
		this.startAnimation(this.animationManager.getAnimation(R.anim.animationbtnadddisappear));
		this.setVisibility(View.GONE);
	}

}
