package com.nine.viewpaperdemo;
import java.io.IOException;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Description:
 * <br/>Program Name:VideoPlayTestActivity
 * <br/>Date:2014/7/31
 * @author  colourfulcloud 
 */
public class VideoPlayTestActivity extends Activity{
	
	private ImageButton button_show;
	private SurfaceView surfaceView;
	private MediaPlayer mPlayer;
	private SurfaceHolder surfaceHolder;
	private VideoPlayDialog dialog;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videoplaytest);

		button_show = (ImageButton)findViewById(R.id.showVideo);
		startFlick(button_show); 
		button_show.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) { 
        		dialog = new VideoPlayDialog(VideoPlayTestActivity.this,
                        R.style.MyDialog);//新建对话框
        		dialog.setCanceledOnTouchOutside(true);
        		//获取dialog中的控件
        		View view = dialog.getDialogView();
        		surfaceView = (SurfaceView) view.findViewById(R.id.surfaceView);
        		
        		playVideo("/sdcard/VID.mp4");

        		dialog.show();
            }
        });
		

	}

	private void playVideo(String videoFileLocation)
	{
		// 创建MediaPlayer
		mPlayer = new MediaPlayer();

		surfaceHolder = surfaceView.getHolder();
		// 设置播放时打开屏幕
		surfaceHolder.setKeepScreenOn(true);
		surfaceHolder.addCallback(new SurfaceListener());

		mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		
		// 设置需要播放的视频
		try
		{
			mPlayer.setDataSource(getApplicationContext(), Uri.parse("android.resource://com.nine.viewpaperdemo/"+R.raw.laserdemo));
			//mPlayer.setDataSource(videoFileLocation);
			
		}
		catch (IllegalArgumentException e1)
		{
			e1.printStackTrace();
		}
		catch (SecurityException e1)
		{
			e1.printStackTrace();
		}
		catch (IllegalStateException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		
		try
		{
			mPlayer.prepare();
		}
		catch (IllegalStateException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}

		//等待surfaceHolder初始化完成才能执行mPlayer.setDisplay(surfaceHolder)
		mPlayer.setOnPreparedListener(new OnPreparedListener() {       
           
            public void onPrepared(MediaPlayer mp) {
            	// 把视频画面输出到SurfaceView
        	
        		
            	mPlayer.start();
            	
            	
            }
        });
		
		//视频播放完成后的操作
		mPlayer.setOnCompletionListener(new OnCompletionListener() {
		
			public void onCompletion(MediaPlayer mp){
				if(mPlayer!=null)
					mPlayer.reset();//重置mediaplayer等待下一次播放
				if(dialog.isShowing())
					dialog.dismiss(); //关闭对话框
			}
		});
	}

	private class SurfaceListener implements SurfaceHolder.Callback
	{
		
		public void surfaceChanged(SurfaceHolder holder, int format,
			int width, int height)
		{
		}

		
		public void surfaceCreated(SurfaceHolder holder)
		{
			mPlayer.setDisplay(surfaceHolder);
		}

		
		public void surfaceDestroyed(SurfaceHolder holder)
		{
		}
	}
	
	@Override
	protected void onDestroy()
	{
		// 停止播放
		if(mPlayer!=null)
		{
			if (mPlayer.isPlaying()) 
				mPlayer.stop();
			// 释放资源
			mPlayer.release();
		}
		super.onDestroy();
		finish();
	}
	
	/**

     * 开启View闪烁效果

     * 

     * */

    private void startFlick( View view ){

        if( null == view ){

            return;

        }

        Animation alphaAnimation = new AlphaAnimation( 1, 0.3f );

        alphaAnimation.setDuration( 2000 );

        alphaAnimation.setInterpolator( new LinearInterpolator( ) );

        alphaAnimation.setRepeatCount( Animation.INFINITE );

        alphaAnimation.setRepeatMode( Animation.REVERSE );

        view.startAnimation( alphaAnimation );

    }

    /**

     * 取消View闪烁效果

     * 

     * */

    private void stopFlick( View view ){

        if( null == view ){

            return;

        }

        view.clearAnimation( );

    }

}
