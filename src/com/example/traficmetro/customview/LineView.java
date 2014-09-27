package com.example.traficmetro.customview;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

public class LineView extends View {

	private static int height=10;
	
	public LineView(Context ct, int x, int y, int width, float rotation) {
		super(ct);
		this.setBackgroundColor(Color.rgb(255, 186, 17));
		this.setLayoutParams(new ViewGroup.LayoutParams((int)width,(int)height));
		this.setX(x);
		this.setY(y);
		this.setPivotX(0f);
		this.setPivotY((int)height/2);
		this.setRotation(rotation);
	}
	
	public static int getLineHeight(){
		return height;
	}
}
