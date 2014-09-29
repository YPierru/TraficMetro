package com.example.traficmetro.customview;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

public class LineView extends View {

	private static final int HEIGHT=10;
	
	public LineView(Context ct, int x, int y, int width, float rotation) {
		super(ct);
		this.setBackgroundColor(Color.rgb(255, 186, 17));
		this.setBackgroundColor(Color.BLACK);
		
		System.out.println(width);
		this.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,(int)HEIGHT));
		this.setX(x);
		this.setY(y);
		this.setPivotX(0f);
		this.setPivotY((int)HEIGHT/2);
		this.setRotation(rotation);
	}
		
	public static int getLineHeight(){
		return HEIGHT;
	}
}
