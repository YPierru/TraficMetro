package com.yanp.traficmetro.customview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yanp.traficmetro.AnimationManager;
import com.yanp.traficmetro.Constants;
import com.yanp.traficmetro.IPanel;
import com.yanp.traficmetro.R;
import com.yanp.traficmetro.data.Struct_AllComments;
import com.yanp.traficmetro.data.TL_Comment;

/**
 * Class which creates the little "+" button, right-bottom corner
 */
public class PanelButtonAddComment extends Button implements IPanel {

	private AnimationManager animationManager;
    private Context context;
	private int widthScreen;
    private int heightScreen;
    private CharSequence[] listTL = {"Fluide","Dense", "Très dense", "Interrompu"};
    private int idStation;


	public PanelButtonAddComment(Context context, int widthScreen, int heightScreen, AnimationManager animationManager) {
		super(context);
		this.animationManager=animationManager;
        this.context=context;
		this.widthScreen=widthScreen;
        this.heightScreen=heightScreen;

        initButton();

	}

    public void initButton(){
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

    public void setData(int idStation){
        this.idStation =idStation;
    }
	
	public void appear(){
		this.setVisibility(View.VISIBLE);
		this.startAnimation(this.animationManager.getAnimation(R.anim.animationbtnaddappear));
	}
	
	public void disappear(){
		this.startAnimation(this.animationManager.getAnimation(R.anim.animationbtnadddisappear));
	}

    private int getColorFromLabel(CharSequence label){
        if(label.equals("Fluide")){
            return Constants.COLOR_TRAFICLIGHT_GREEN;
        }else if(label.equals("Dense")){
            return Constants.COLOR_TRAFICLIGHT_ORANGE;
        }else if(label.equals("Très dense")){
            return Constants.COLOR_TRAFICLIGHT_RED;
        }else if(label.equals("Interrompu")){
            return Constants.COLOR_TRAFICLIGHT_BLACK;
        }else{
            return 0;
        }
    }
	
	private class ButtonAddClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {

            new AlertDialog.Builder(context)
                    .setSingleChoiceItems(listTL, 0, null)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                            int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();

                            Struct_AllComments singleton = Struct_AllComments.getInstance();

                            singleton.getListTLCForStation(idStation).add(0,new TL_Comment(getColorFromLabel(listTL[selectedPosition]),"Ajd"));

                            //Toast.makeText(context, listTL[selectedPosition], Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("Annuler", null)
                    .show();

        }
		
	}

}
