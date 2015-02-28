package com.yanp.traficmetro.ui.panel;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.yanp.traficmetro.Constants;
import com.yanp.traficmetro.R;
import com.yanp.traficmetro.core.AnimationManager;
import com.yanp.traficmetro.core.comments.Struct_AllComments;

/**
 * This class create the panel displayed when the user click on a station.
 * Construction :
 * linearLayoutHorizontal(traficLight,linearLayoutVertical(stationName,lineName))
 */
public class PInfoStations implements IPanel{

    private LinearLayout linearLayoutHorizontal;
    private LinearLayout linearLayoutVertical;
    private TraficLightView traficLight;
    private TextView stationName;
    private TextView lineName;

    private Context context;
    private AnimationManager animationManager;

    private int widthScreen;
    private int heightScreen;

	public PInfoStations(Context context, int widthScreen, int heightScreen,AnimationManager animationManager) {
        this.context=context;
        this.animationManager=animationManager;
        this.widthScreen=widthScreen;
        this.heightScreen=heightScreen;


        this.stationName=new TextView(this.context);
        this.lineName=new TextView(this.context);
        this.traficLight = new TraficLightView(this.context);

        this.initLinearLayoutHorizontal();
        this.initLinearLayoutVertical();
        this.initTraficLightView();

        this.linearLayoutVertical.addView(this.stationName);
        this.linearLayoutVertical.addView(this.lineName);

        this.linearLayoutHorizontal.addView(this.traficLight);
        this.linearLayoutHorizontal.addView(this.linearLayoutVertical);

	}

    public void initTraficLightView(){
        LinearLayout.LayoutParams params_TLV = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
        params_TLV.weight = 3f;
        this.traficLight.setLayoutParams(params_TLV);
    }

    /**
     * Initialise the linear layout horiztonal, with params, marges etc.
     */
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

    /**
     * Initiliase the linear layout vertical (params, weight)
     */
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

    /**
     *
     * @return the panel
     */
    public LinearLayout getPanelInfoStations(){
        return this.linearLayoutHorizontal;
    }

    /**
     * On construction
     * @param stationName
     * @param lineName
     */
    public void setData(String stationName,String lineName){

        this.stationName.setText(stationName);
        this.lineName.setText(lineName);
        Struct_AllComments singleton = Struct_AllComments.getInstance();
        this.traficLight.setColor(singleton.getLastColorById(stationName));
    }

    /**
     * Show the panel with the animation
     */
    public void appear(){
        this.linearLayoutHorizontal.setVisibility(View.VISIBLE);
        this.linearLayoutHorizontal.startAnimation(this.animationManager.getAnimation(R.anim.animationinfostationappear));
    }

    /**
     * Hide the panel with the animation
     */
    public void disappear(){
        this.linearLayoutHorizontal.setVisibility(View.GONE);
        this.linearLayoutHorizontal.startAnimation(this.animationManager.getAnimation(R.anim.animationinfostationdisappear));
    }

}
