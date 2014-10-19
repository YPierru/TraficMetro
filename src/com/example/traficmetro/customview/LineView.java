package com.example.traficmetro.customview;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.View;

public class LineView extends View {

	private static final int HEIGHT = 5;
	private Paint paint;
	private Path path;
	private CornerPathEffect cpe;
	private int color;
	private ArrayList<PointF> listLinePoints;

	public LineView(Context ct, ArrayList<PointF>listLinePoints, int color) {
		super(ct);
		this.listLinePoints=listLinePoints;
		this.color=color;
		
		this.paint=new Paint();
		this.path=new Path();
		this.cpe=new CornerPathEffect(5);
		setWillNotDraw(true);
		//Log.d("debuuuug", "coucou");
		
	}

	@Override
	public void onDraw(Canvas canvas) {
		this.paint.setColor(this.color);
		this.paint.setStrokeWidth(HEIGHT);
		this.paint.setStyle(Paint.Style.STROKE);
		this.paint.setDither(true);
		this.paint.setStrokeJoin(Paint.Join.ROUND);
		this.paint.setStrokeCap(Paint.Cap.ROUND);
		this.paint.setPathEffect(this.cpe);
		this.paint.setAntiAlias(true);

		this.path.moveTo(this.listLinePoints.get(0).x, this.listLinePoints.get(0).y);
		
		for(int i=1;i<this.listLinePoints.size();i++){
			this.path.lineTo(this.listLinePoints.get(i).x, this.listLinePoints.get(i).y);
		}
		
		canvas.drawPath(path,paint);
		
		paint.setColor(Color.RED);
		//canvas.drawRect(this.getC, paint);
		
	}

	private Rect getBounds(){
		int[] l = new int[2];
	    getLocationOnScreen(l);
	    return new Rect(l[0], l[1], l[0] + getWidth(), l[1] + getHeight());
	}
	
	public static int getLineHeight() {
		return HEIGHT;
	}

}
