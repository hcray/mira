package com.mira;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MRGuideActivity extends Activity {
	private ViewPager myViewPager;
	 
    private MyPagerAdapter myAdapter;
     
    private LayoutInflater mInflater;
    private List<View> mListViews;
    private View layout1 = null;
    private View layout2 = null;
    private View layout3 = null;
    
    private ImageView ivDot0;
    
    private ImageView ivDot1;
    
    private ImageView ivDot2;
    
    private Button btnLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_guide);
		
		ivDot0 = (ImageView) this.findViewById(R.id.guide_activity_dot0);
		
		ivDot1 = (ImageView) this.findViewById(R.id.guide_activity_dot1);
		
		ivDot2 = (ImageView) this.findViewById(R.id.guide_activity_dot2);
		
		
        myAdapter = new MyPagerAdapter();
        myViewPager = (ViewPager) findViewById(R.id.guide_activity_viewpager);
         
        mListViews = new ArrayList<View>();
        mInflater = getLayoutInflater();
        layout1 = mInflater.inflate(R.layout.guide1, null);
        layout2 = mInflater.inflate(R.layout.guide2, null);
        layout3 = mInflater.inflate(R.layout.guide3, null);
        
        btnLogin = (Button) layout3.findViewById(R.id.guide3_btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Intent intent = new Intent(v.getContext(), MRLoginActivity.class);
        		startActivity(intent);
        	}
        });
        mListViews.add(layout1);
        mListViews.add(layout2);
        mListViews.add(layout3);
         
        myViewPager.setAdapter(myAdapter);
        //初始化当前显示的view
        myViewPager.setCurrentItem(0);
        
        myViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            
            @Override
            public void onPageSelected(int arg0) {
                Log.d("k", "onPageSelected - " + arg0);
                //activity从1到2滑动，2被加载后掉用此方法
                View v = mListViews.get(arg0);
                switchTab(arg0);
//                EditText editText = (EditText)v.findViewById(R.id.editText1);
//                editText.setText("动态设置#"+arg0+"edittext控件的值");
            }
             
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                Log.d("k", "onPageScrolled - " + arg0);
                //从1到2滑动，在1滑动前调用
            }
             
            @Override
            public void onPageScrollStateChanged(int arg0) {
                Log.d("k", "onPageScrollStateChanged - " + arg0);
                //状态有三个0空闲，1是增在滑行中，2目标加载完毕
                /**
                 * Indicates that the pager is in an idle, settled state. The current page
                 * is fully in view and no animation is in progress.
                 */
                //public static final int SCROLL_STATE_IDLE = 0;
                /**
                 * Indicates that the pager is currently being dragged by the user.
                 */
                //public static final int SCROLL_STATE_DRAGGING = 1;
                /**
                 * Indicates that the pager is in the process of settling to a final position.
                 */
                //public static final int SCROLL_STATE_SETTLING = 2;
 
            }
        });
	}
	
	private class MyPagerAdapter extends PagerAdapter{
		 
        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            Log.d("k", "destroyItem");
            ((ViewPager) arg0).removeView(mListViews.get(arg1));
        }
 
        @Override
        public void finishUpdate(View arg0) {
            Log.d("k", "finishUpdate");
        }
 
        @Override
        public int getCount() {
            Log.d("k", "getCount");
            return mListViews.size();
        }
 
        @Override
        public Object instantiateItem(View arg0, int arg1) {
            Log.d("k", "instantiateItem");
            ((ViewPager) arg0).addView(mListViews.get(arg1),0);
            return mListViews.get(arg1);
        }
 
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            Log.d("k", "isViewFromObject");
            return arg0==(arg1);
        }
 
        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            Log.d("k", "restoreState");
        }
 
        @Override
        public Parcelable saveState() {
            Log.d("k", "saveState");
            return null;
        }
 
        @Override
        public void startUpdate(View arg0) {
            Log.d("k", "startUpdate");
        }
         
    }
	
	private void switchTab(int index) {
		Drawable dotOn = getResources().getDrawable(R.drawable.dot_on);
		Drawable dotOff = getResources().getDrawable(R.drawable.dot_off);
		switch (index) {
		case 0:
			ivDot0.setImageDrawable(dotOn);
			ivDot1.setImageDrawable(dotOff);
			ivDot2.setImageDrawable(dotOff);
			break;
		case 1:
			ivDot0.setImageDrawable(dotOff);
			ivDot1.setImageDrawable(dotOn);
			ivDot2.setImageDrawable(dotOff);
			break;
		case 2:
			ivDot0.setImageDrawable(dotOff);
			ivDot1.setImageDrawable(dotOff);
			ivDot2.setImageDrawable(dotOn);
			break;
		default:
			break;
		}
	}
}
