package com.yanp.traficmetro.customview;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.yanp.traficmetro.AnimationManager;
import com.yanp.traficmetro.Constants;
import com.yanp.traficmetro.R;

public class PanelListComments extends ListView {

	private AnimationManager animationManager;
	
	public PanelListComments(Context context, int widthScreen, int heightScreen, AnimationManager animationManager) {
		super(context);		
		this.animationManager=animationManager;
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,      
				RelativeLayout.LayoutParams.MATCH_PARENT
		);
		
		params.setMargins(	(int)(widthScreen*Constants.MARGIN_PURCENTAGE_LEFT_LV),
				(int)(heightScreen*Constants.MARGIN_PURCENTAGE_TOP_LV),
				(int)(widthScreen*Constants.MARGIN_PURCENTAGE_RIGHT_LV),
				(int)(heightScreen*Constants.MARGIN_PURCENTAGE_BOTTOM_LV));
		
		this.setLayoutParams(params);
		this.setBackgroundColor(Color.WHITE);
	    String[] values = new String[50];
	    for(int i=0;i<50;i++){
	        values[i] = ""+i;
	    }
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.lv_item, values);
	    this.setAdapter(adapter);
	    this.setOnItemClickListener(new OnItemClickListener(){

	        @Override
	        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	                long arg3) {
	            //Toast.makeText(getBaseContext(), ""+arg2,     Toast.LENGTH_SHORT).show();
	            Log.d("DEBUG", ""+arg2);

	        }

	    });
	    this.setVisibility(View.GONE);
	}
	
	public void appear(){
		this.setVisibility(View.VISIBLE);
		this.startAnimation(this.animationManager.getAnimation(R.anim.animationlistappear));
	}
	
	public void disappear(){
		this.startAnimation(this.animationManager.getAnimation(R.anim.animationlistdisappear));
	}

}