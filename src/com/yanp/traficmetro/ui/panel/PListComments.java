package com.yanp.traficmetro.ui.panel;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.yanp.traficmetro.Constants;
import com.yanp.traficmetro.R;
import com.yanp.traficmetro.core.AnimationManager;
import com.yanp.traficmetro.core.comments.Struct_AllComments;
import com.yanp.traficmetro.core.comments.TL_Comment;
import com.yanp.traficmetro.core.comments.TL_CommentAdapter;

import java.util.ArrayList;


/**
 * Create the panel of the last "comment".
 * IN CONSTRUCTION
 */
public class PListComments extends ListView implements IPanel {

	private AnimationManager animationManager;
    private ArrayList<TL_Comment> listCurrentTLC = new ArrayList<TL_Comment>();
    private Context context;
	
	public PListComments(Context context, int widthScreen, int heightScreen, AnimationManager animationManager) {
		super(context);
        this.context=context;
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
		//this.setBackgroundColor(Color.WHITE);

	    this.setVisibility(View.GONE);
	}

    public void setData(String idStation){
        Struct_AllComments singleton= Struct_AllComments.getInstance();
        this.listCurrentTLC = singleton.getListTLCForStation(idStation);
        TL_CommentAdapter adapter = new TL_CommentAdapter(context,this.listCurrentTLC);
        this.setAdapter(adapter);
    }
	
	public void appear(){
		this.setVisibility(View.VISIBLE);
		this.startAnimation(this.animationManager.getAnimation(R.anim.animationlistappear));
	}
	
	public void disappear(){
		this.startAnimation(this.animationManager.getAnimation(R.anim.animationlistdisappear));
	}

}
