package com.yanp.traficmetro.customview;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.yanp.traficmetro.AnimationManager;
import com.yanp.traficmetro.Constants;
import com.yanp.traficmetro.R;

public class PanelInfoStations {

    private LinearLayout linearLayoutHorizontal;
    private LinearLayout linearLayoutVertical;
    private TraficLightView traficLight;
    private TextView stationName;
    private TextView lineName;

    private Context context;
    private AnimationManager animationManager;

    private int widthScreen;
    private int heightScreen;

	public PanelInfoStations(Context context, int widthScreen, int heightScreen,AnimationManager animationManager) {
        this.context=context;
        this.animationManager=animationManager;
        this.widthScreen=widthScreen;
        this.heightScreen=heightScreen;

        this.initLinearLayoutHorizontal();

        this.initLinearLayoutVertical();

        this.traficLight = new TraficLightView(this.context);
        LinearLayout.LayoutParams params_TLV = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
        params_TLV.weight = 3f;
        this.traficLight.setLayoutParams(params_TLV);

        this.stationName=new TextView(this.context);
        this.stationName.setText("SNdezdezdezdezd");

        this.lineName=new TextView(this.context);
        this.lineName.setText("LdezdezdezdzedzdezeN");

        this.linearLayoutVertical.addView(this.stationName);
        this.linearLayoutVertical.addView(this.lineName);

        this.linearLayoutHorizontal.addView(this.traficLight);
        this.linearLayoutHorizontal.addView(this.linearLayoutVertical);

	}

    private void initLinearLayoutHorizontal(){
        this.linearLayoutHorizontal = new LinearLayout(this.context);
        this.linearLayoutHorizontal.setBackgroundColor(Color.WHITE);
        this.linearLayoutHorizontal.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        );
        params.setMargins((int) (this.widthScreen * Constants.MARGIN_PURCENTAGE_LEFT_TV),
                (int) (this.heightScreen * Constants.MARGIN_PURCENTAGE_TOP_TV),
                (int) (this.widthScreen * Constants.MARGIN_PURCENTAGE_RIGHT_TV),
                (int) (this.heightScreen * Constants.MARGIN_PURCENTAGE_BOTTOM_TV));
        this.linearLayoutHorizontal.setLayoutParams(params);
        this.linearLayoutHorizontal.setWeightSum(10f);
    }

    private void initLinearLayoutVertical(){
        this.linearLayoutVertical = new LinearLayout(this.context);
        this.linearLayoutVertical.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.weight=7f;
        this.linearLayoutVertical.setLayoutParams(params);
    }

    public LinearLayout getPanelInfoStations(){
        return this.linearLayoutHorizontal;
    }

    public void setData(String stationName,String lineName){
        this.stationName.setText(stationName+" "+lineName);
    }

    public void appear(){
        this.linearLayoutHorizontal.setVisibility(View.VISIBLE);
        this.linearLayoutHorizontal.startAnimation(this.animationManager.getAnimation(R.anim.animationinfostationappear));
    }

    public void disappear(){
        this.linearLayoutHorizontal.setVisibility(View.GONE);
        this.linearLayoutHorizontal.startAnimation(this.animationManager.getAnimation(R.anim.animationinfostationdisappear));
    }

}
