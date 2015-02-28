package com.yanp.traficmetro.ui.map;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.view.View;

import com.yanp.traficmetro.Constants;


/**
 * Custom view which represent the line. Draw a line on a canvas.
 * @author YPierru
 *
 */
public class LineView extends View {

	private Paint paint;
	private Path path;
	private CornerPathEffect cornerPathEffect;
	private int color;
	private ArrayList<PointF> listLinePoints;

	public LineView(Context ct, ArrayList<PointF>listLinePoints, int color) {
		super(ct);
		this.listLinePoints=listLinePoints;
		this.color=color;
		this.paint=new Paint();
		this.path=new Path();
		this.cornerPathEffect=new CornerPathEffect(5);
		
		setWillNotDraw(true);
	}

	@Override
	public void onDraw(Canvas canvas) {
		/**
		 * Settings some graphic parameters
		 */
		this.paint.setColor(this.color);
		this.paint.setStrokeWidth(Constants.LINEVIEW_HEIGHT);
		this.paint.setStyle(Paint.Style.STROKE);
		this.paint.setDither(true);
		this.paint.setStrokeJoin(Paint.Join.ROUND);
		this.paint.setStrokeCap(Paint.Cap.ROUND);
		this.paint.setPathEffect(this.cornerPathEffect);
		this.paint.setAntiAlias(true);
		
		/**
		 * Draw the line
		 */
		this.path.moveTo(this.listLinePoints.get(0).x, this.listLinePoints.get(0).y);
		
		for(int i=1;i<this.listLinePoints.size();i++){
			this.path.lineTo(this.listLinePoints.get(i).x, this.listLinePoints.get(i).y);
		}
		
		canvas.drawPath(path,paint);		
	}

}
