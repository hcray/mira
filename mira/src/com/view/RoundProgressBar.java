package com.view;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.mira.R;

/**
 * ��iphone���ȵĽ�������̰߳�ȫ��View����ֱ�����߳��и��½��
 * @author xiaanming
 *
 */
public class RoundProgressBar extends View {
	/**
	 * ���ʶ��������
	 */
	public Paint paint;
	
	/**
	 * Բ������ɫ
	 */
	public int roundColor;
	
	/**
	 * Բ����ȵ���ɫ
	 */
	public int roundProgressColor;
	
	/**
	 * �м��Ȱٷֱȵ��ַ����ɫ
	 */
	public int textColor;
	
	/**
	 * �м��Ȱٷֱȵ��ַ������
	 */
	public float textSize;
	
	/**
	 * Բ���Ŀ��
	 */
	public float roundWidth;
	
	/**
	 * �����
	 */
	public float max;
	
	/**
	 * ��ǰ���
	 */
	public float progress;
	
	public boolean timeProgress;
	public float tempProgress;
	/**
	 * �Ƿ���ʾ�м�Ľ��
	 */
	public boolean textIsDisplayable;
	
	/**
	 * ��ȵķ��ʵ�Ļ��߿���
	 */
	public int style;
	public Timer timer;
	public static final int STROKE = 0;
	public static final int FILL = 1;
	
	public RoundProgressBar(Context context) {
		this(context, null);
	}

	public RoundProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		paint = new Paint();

		
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.RoundProgressBar);
		
		//��ȡ�Զ������Ժ�Ĭ��ֵ
		roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.RED);
		roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.GREEN);
		textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.GREEN);
		textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 15);
		roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 5);
		max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
		textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
		style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);
		
		mTypedArray.recycle();
	}
	

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		/**
		 * �������Ĵ�Բ��
		 */
		int centre = getWidth()/2; //��ȡԲ�ĵ�x���
		int radius = (int) (centre - roundWidth/2); //Բ���İ뾶
		paint.setColor(roundColor); //����Բ������ɫ
		paint.setStyle(Paint.Style.STROKE); //���ÿ���
		paint.setStrokeWidth(roundWidth); //����Բ���Ŀ��
		paint.setAntiAlias(true);  //����� 
		canvas.drawCircle(centre, centre, radius, paint); //����Բ��
		
//		Log.e("log", centre + "");
		
		/**
		 * ����Ȱٷֱ�
		 */
		paint.setStrokeWidth(0); 
		paint.setColor(textColor);
		paint.setTextSize(textSize);
		paint.setTypeface(Typeface.DEFAULT_BOLD); //��������
		int percent = (int)(((float)progress / (float)max) * 100);  //�м�Ľ�Ȱٷֱȣ���ת����float�ڽ��г����㣬��Ȼ��Ϊ0
		float textWidth = paint.measureText(String.valueOf(percent));   //���������ȣ�������Ҫ�������Ŀ��������Բ���м�
		float textHeight=-12;
		if(textIsDisplayable && percent >= 0 && style == STROKE){
			//去除%的显示
			canvas.drawText(String.valueOf(percent), centre - textWidth/2, centre + (textSize+textHeight)/2, paint); //������Ȱٷֱ�
		}
		
		
		/**
		 * ��Բ�� ����Բ���Ľ��
		 */
		
		//���ý����ʵ�Ļ��ǿ���
		paint.setStrokeWidth(roundWidth); //����Բ���Ŀ��
		paint.setColor(roundProgressColor);  //���ý�ȵ���ɫ
		RectF oval = new RectF(centre - radius, centre - radius, centre
				+ radius, centre + radius);  //���ڶ����Բ������״�ʹ�С�Ľ���
		
		switch (style) {
		case STROKE:{
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawArc(oval, -90, 360 * progress / max, false, paint);  //��ݽ�Ȼ�Բ��
			break;
		}
		case FILL:{
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			if(progress >= 0)
				canvas.drawArc(oval, -90, 360 * progress / max, true, paint);  //��ݽ�Ȼ�Բ��
			break;
		}
		}
		
	}
	
	
	public synchronized float getMax() {
		return max;
	}

	/**
	 * ���ý�ȵ����ֵ
	 * @param max
	 */
	public synchronized void setMax(int max) {
		if(max < 0){
			throw new IllegalArgumentException("max not less than 0");
		}
		this.max = max;
	}

	/**
	 * ��ȡ���.��Ҫͬ��
	 * @return
	 */
	public synchronized float getProgress() {
		return progress;
	}

	/**
	 * ���ý�ȣ���Ϊ�̰߳�ȫ�ؼ������ڿ��Ƕ��ߵ����⣬��Ҫͬ��
	 * ˢ�½������postInvalidate()���ڷ�UI�߳�ˢ��
	 * @param progress
	 */
	public synchronized void setProgress(float progress) {
		tempProgress=progress;
		if(tempProgress < 0){
			tempProgress=0;
		}
		if(tempProgress > max){
			tempProgress = max;
		}
		if(tempProgress <= max){
			if(this.progress<tempProgress)
			{
				timeProgress=true;
			}
			else if(this.progress>tempProgress)
			{
				timeProgress=false;
			}
			else
			{
				return;
			}
			if(timer!=null)
			{
				timer.cancel();
				timer=null;
			}
			timer=new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					if(timeProgress)
					{
						RoundProgressBar.this.progress+=0.3;
						postInvalidate();
						if(RoundProgressBar.this.progress>=tempProgress)
						{
							if(timer!=null)
							{
								timer.cancel();
								timer=null;
							}
						}
					}
					else
					{
						RoundProgressBar.this.progress-=0.3;
						postInvalidate();
						if(RoundProgressBar.this.progress<=tempProgress)
						{
							if(timer!=null)
							{
								timer.cancel();
								timer=null;
							}
						}
					}
				}
			}, 0, 10);
		}
	}
	
	
	public int getCricleColor() {
		return roundColor;
	}

	public void setCricleColor(int cricleColor) {
		this.roundColor = cricleColor;
	}

	public int getCricleProgressColor() {
		return roundProgressColor;
	}

	public void setCricleProgressColor(int cricleProgressColor) {
		this.roundProgressColor = cricleProgressColor;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public float getRoundWidth() {
		return roundWidth;
	}

	public void setRoundWidth(float roundWidth) {
		this.roundWidth = roundWidth;
	}



}
