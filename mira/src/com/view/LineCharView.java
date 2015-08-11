package com.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mira.R;

public class LineCharView extends View {
	private int xori;// 圆点x坐标
	private int yori;// 圆点y坐标
	private int xinit;// 第一个点x坐标
	private int minXinit;// 在移动时，第一个点允许最小的x坐标
	private int maxXinit;// 在移动时，第一个点允许允许最大的x坐标
	private int xylinecolor;// xy坐标轴颜色
	private int xylinewidth;// xy坐标轴大小
	private int xytextcolor;// xy坐标轴文字颜色
	private int xytextsize;// xy坐标轴文字大小
	private int linecolor;// 折线的颜色
	private int interval;// 坐标间的间隔
	private int bgColor;// 背景颜色
	private List<String> x_coords;// x坐标点的值
	private List<Integer> x_coord_values;// 每个点状态值

	private int width;// 控件宽度
	private int heigth;// 控件高度
	private int imageWidth;// 表情的宽度
	private float textwidth;// y轴文字的宽度
	float startX = 0;// 滑动时候，上一次手指的x坐标

	public LineCharView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.LineChar);
		xylinecolor = typedArray.getColor(R.styleable.LineChar_xylinecolor,
				Color.GRAY);
		xylinewidth = typedArray.getInt(R.styleable.LineChar_xylinewidth, 1);
		xytextcolor = typedArray.getColor(R.styleable.LineChar_xytextcolor,
				Color.BLACK);
		xytextsize = typedArray.getLayoutDimension(
				R.styleable.LineChar_xytextsize, 20);
		linecolor = typedArray.getColor(R.styleable.LineChar_linecolor,
				Color.parseColor("#81d8cf"));
		interval = typedArray.getLayoutDimension(R.styleable.LineChar_interval,
				100);
		bgColor = typedArray
				.getColor(R.styleable.LineChar_bgcolor, Color.WHITE);
		typedArray.recycle();
		x_coords = new ArrayList<String>();
		x_coord_values = new ArrayList<Integer>();
	}

	public LineCharView(Context context) {
		super(context);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		if (changed) {
			width = getWidth();
			heigth = getHeight();
			Paint paint = new Paint();
			paint.setTextSize(xytextsize);
			textwidth = paint.measureText("A");
			xori = (int) (textwidth + 6 + 2 * xylinewidth);// 6 为与y轴的间隔
			yori = heigth - xytextsize - 2 * xylinewidth - 3;
			xinit = interval / 2 + xori;
			imageWidth = BitmapFactory.decodeResource(getResources(),
					R.drawable.btn_blue).getWidth();
			minXinit = (int) (width - xori - x_coords.size() * interval * 0.5);
			maxXinit = xinit;
			setBackgroundColor(bgColor);
		}
		super.onLayout(changed, left, top, right, bottom);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		drawX(canvas);
		drawXY(canvas);
		drawY(canvas);
	}

	// 画坐标轴
	private void drawXY(Canvas canvas) {
		Paint paint = new Paint();
		// 颜色
		paint.setColor(xylinecolor);
		// 宽度
		paint.setStrokeWidth(xylinewidth);
		// Y
		canvas.drawLine(xori + 20, 0, xori + 20, yori, paint);
		// x
		canvas.drawLine(xori + 20, yori, width, yori, paint);
	}

	// 画Y轴坐标点
	private void drawY(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(xylinecolor);
		paint.setStyle(Paint.Style.FILL);
		for (int i = 1; i < 6; i++) {
			canvas.drawCircle(xori + 20, (float) (yori - (i * 0.4 * interval)),
					xylinewidth * 2, paint);
		}

		paint.setTextSize(xytextsize);
		paint.setColor(xytextcolor);
		canvas.drawText("100", xori - textwidth - 6 - xylinewidth,
				(float) (yori - (2.0 * interval) + xytextsize / 2), paint);
		canvas.drawText("80", xori - textwidth - 6 - xylinewidth, (float) (yori
				- (1.6 * interval) + xytextsize / 2), paint);
		canvas.drawText("60", xori - textwidth - 6 - xylinewidth, (float) (yori
				- (1.2 * interval) + xytextsize / 2), paint);
		canvas.drawText("40", xori - textwidth - 6 - xylinewidth, (float) (yori
				- (0.8 * interval) + xytextsize / 2), paint);
		canvas.drawText("20", xori - textwidth - 6 - xylinewidth, (float) (yori
				- (0.4 * interval) + xytextsize / 2), paint);
	}

	// 画X轴坐标点，折线，表情
	@SuppressLint("ResourceAsColor")
	private void drawX(Canvas canvas) {
		Paint x_coordPaint = new Paint();

		Log.i("xinit", xinit + "");
		x_coordPaint.setTextSize(xytextsize);
		x_coordPaint.setStyle(Paint.Style.FILL);
		Path path = new Path();
		// 画坐标点
		for (int i = 0; i < x_coords.size(); i++) {
			int x = (int) (i * interval * 0.5 + xinit);
			if (i == 0) {
				path.moveTo(x, getYValue(x_coord_values.get(i)));
			} else {
				path.lineTo(x, getYValue(x_coord_values.get(i)));
			}
			x_coordPaint.setColor(xylinecolor);
			canvas.drawCircle(x, yori, xylinewidth * 2, x_coordPaint);
			String text = x_coords.get(i);
			x_coordPaint.setColor(xytextcolor);
			//x轴上的文字
			canvas.drawText(text,  x - x_coordPaint.measureText(text) / 2, yori + xytextsize + xylinewidth * 2, x_coordPaint);
		}
		//折线
		x_coordPaint.setStyle(Paint.Style.STROKE);
		x_coordPaint.setStrokeWidth(xylinewidth*2);
		x_coordPaint.setColor(linecolor);
		// 画折线
		canvas.drawPath(path, x_coordPaint);

		// 画表情
		for (int i = 0; i < x_coords.size(); i++) {
			int x = (int) (i * interval * 0.5 + xinit);
			canvas.drawBitmap(getYBitmap(x_coord_values.get(i)), x - imageWidth
					/ 2, getYValue(x_coord_values.get(i)) - imageWidth / 2,
					x_coordPaint);
		}

		// 将折线超出x轴坐标的部分截取掉
		x_coordPaint.setStyle(Paint.Style.FILL);
		x_coordPaint.setColor(bgColor);
		x_coordPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
		RectF rectF = new RectF(0, 0, xori, heigth);
		canvas.drawRect(rectF, x_coordPaint);

	}

	// 得到y坐标
	private float getYValue(int value) {
		float v = value/20;
		return (float) (yori - interval * v * 0.4);
		/*
		if (value.equalsIgnoreCase("A")) {
			return yori - interval / 2;
		} else if (value.equalsIgnoreCase("B")) {
			return yori - interval;
		} else if (value.equalsIgnoreCase("C")) {
			return (float) (yori - interval * 1.5);
		} else if (value.equalsIgnoreCase("D")) {
			return yori - interval * 2;
		} else {
			return yori;
		}
		*/
		
	}

	// 得到表情图
	private Bitmap getYBitmap(Integer value) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_blue);
		return bitmap;
	}

	/*@Override
	public boolean onTouchEvent(MotionEvent event) {

		// 如果不用滑动就可以展示所有数据，就不让滑动
		if (interval * x_coord_values.size() <= width - xori) {
			return false;
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = event.getX();
			break;

		case MotionEvent.ACTION_MOVE:
			float dis = event.getX() - startX;
			startX = event.getX();
			if (xinit + dis > maxXinit) {
				xinit = maxXinit;
			} else if (xinit + dis < minXinit) {
				xinit = minXinit;
			} else {
				xinit = (int) (xinit + dis);
			}
			invalidate();

			break;
		}
		return true;
	}*/

	public int getXori() {
		return xori;
	}

	public void setXori(int xori) {
		this.xori = xori;
	}

	public int getYori() {
		return yori;
	}

	public void setYori(int yori) {
		this.yori = yori;
	}

	public int getXinit() {
		return xinit;
	}

	public void setXinit(int xinit) {
		this.xinit = xinit;
	}

	public int getMinXinit() {
		return minXinit;
	}

	public void setMinXinit(int minXinit) {
		this.minXinit = minXinit;
	}

	public int getMaxXinit() {
		return maxXinit;
	}

	public void setMaxXinit(int maxXinit) {
		this.maxXinit = maxXinit;
	}

	public int getXylinecolor() {
		return xylinecolor;
	}

	public void setXylinecolor(int xylinecolor) {
		this.xylinecolor = xylinecolor;
	}

	public int getXylinewidth() {
		return xylinewidth;
	}

	public void setXylinewidth(int xylinewidth) {
		this.xylinewidth = xylinewidth;
	}

	public int getXytextcolor() {
		return xytextcolor;
	}

	public void setXytextcolor(int xytextcolor) {
		this.xytextcolor = xytextcolor;
	}

	public int getXytextsize() {
		return xytextsize;
	}

	public void setXytextsize(int xytextsize) {
		this.xytextsize = xytextsize;
	}

	public int getLinecolor() {
		return linecolor;
	}

	public void setLinecolor(int linecolor) {
		this.linecolor = linecolor;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getBgColor() {
		return bgColor;
	}

	public void setBgColor(int bgColor) {
		this.bgColor = bgColor;
	}

	public List<String> getX_coords() {
		return x_coords;
	}

	public List<Integer> getX_coord_values() {
		return x_coord_values;
	}

	/**
	 * 设置坐标折线图值
	 * 
	 * @param x_coords
	 *            横坐标坐标点
	 * @param x_coord_values
	 *            每个点的值
	 */
	public void setValue(List<String> x_coords, List<Integer> x_coord_values) {
		if (x_coord_values.size() != x_coords.size()) {
			throw new IllegalArgumentException("坐标轴点和坐标轴点的值的个数必须一样!");
		}
		this.x_coord_values = x_coord_values;
		this.x_coords = x_coords;
		invalidate();
	}

}
