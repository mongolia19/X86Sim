package com.nine.viewpaperdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class GuideActivity extends Activity implements OnTouchListener{
	
	public static final int TotoalNum = 4;
	private ViewPager viewPager;
	private List<View> listView;
	private List<View> listDots;
	
	private int thePos = 0; //当前的View的索引
	private int oldPosition;
	private int count = 0;  //点击屏幕的次数
	
	private long firstTime = 0;//第一次点击的时间;
	private long secondTime = 0;//第二次点击的时间
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
      
        	setTarget();
        	setContentView(R.layout.guide);
    		initViewPager();
    		initDots();
      
    		//Cpu segment
    		
    		CPU_STATE_S cpu86=new CPU_STATE_S();
    		retro86.startCPU(cpu86);
    		
		
    }
    
 
    /**第一次启动后设置标志*/
    private void setTarget() {
    	SharedPreferences share = getSharedPreferences("fs", MODE_PRIVATE);
    	Editor editor = share.edit();
    	editor.putString("isfs", "no");
    	editor.commit();
    }
    
    private void initViewPager() {
    	viewPager = (ViewPager) findViewById(R.id.viewpager);
    	listView = new ArrayList<View>();
    	LayoutInflater inflater = getLayoutInflater();
    	listView.add(inflater.inflate(R.layout.resume, null));
    	listView.add(inflater.inflate(R.layout.inforlinearprj1, null));
    	listView.add(inflater.inflate(R.layout.inforlinear, null));
    	listView.add(inflater.inflate(R.layout.inforlinearprj2, null));
    	listView.add(inflater.inflate(R.layout.selfeval, null));
    	
    	for (int i = 0; i < listView.size(); i++) {
    		View view = (View) listView.get(i);
    		view.setOnTouchListener(this);
    	}
    	
    	viewPager.setAdapter(new MyPagerAdapter(listView));
    	viewPager.setOnPageChangeListener(new MyPagerChangeListener());
    	
    }
    
    private void initDots() {
    	listDots = new ArrayList<View>();
    	listDots.add(findViewById(R.id.dot01));
    	listDots.add(findViewById(R.id.dot02));
    	listDots.add(findViewById(R.id.dot03));
    	listDots.add(findViewById(R.id.dot04));
    }
    
    
    public class MyPagerChangeListener implements OnPageChangeListener {

		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		public void onPageSelected(int position) {
			((View)listDots.get(position%TotoalNum)).setBackgroundResource(R.drawable.dot_focused);
			((View)listDots.get(oldPosition%TotoalNum)).setBackgroundResource(R.drawable.dot_normal);
			oldPosition = position;
			thePos = position;
			System.out.println(thePos);
		}
    	
    }
    
    public class MyPagerAdapter extends PagerAdapter {
    	
    	private List<View> list;
    	
    	public MyPagerAdapter(List<View> list) {
    		this.list = list;
		}
    	
		@Override
		public void destroyItem(View view, int index, Object arg2) {
			// TODO Auto-generated method stub
			((ViewPager)view).removeView(list.get(index));
		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object instantiateItem(View view, int index) {
			((ViewPager)view).addView(list.get(index % list.size()), 0);
			return list.get(index % list.size());
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == (object);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub
			
		}
    	
    }

	public boolean onTouch(View arg0, MotionEvent event) {
		if (MotionEvent.ACTION_DOWN == event.getAction() && thePos == 3) {
    		count++;
    		if (count ==1) {
    			firstTime = System.currentTimeMillis();
    		} else {
    			secondTime = System.currentTimeMillis();
    			if (secondTime - firstTime < 1000) {
    				Intent it = new Intent();
    	    		it.setClass(this, VideoPlayTestActivity.class);
    	    		startActivity(it);
    	    		
    			}
    			count = 0;
    			firstTime = 0;
    			secondTime = 0;
    		}
    		
    	}
    	return true;
	}

}