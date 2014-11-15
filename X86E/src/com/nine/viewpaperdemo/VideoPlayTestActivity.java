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
                        R.style.MyDialog);//�½��Ի���
        		dialog.setCanceledOnTouchOutside(true);
        		//��ȡdialog�еĿؼ�
        		View view = dialog.getDialogView();
        		surfaceView = (SurfaceView) view.findViewById(R.id.surfaceView);
        		
        		playVideo("/sdcard/VID.mp4");

        		dialog.show();
            }
        });
		

	}

	private void playVideo(String videoFileLocation)
	{
		// ����MediaPlayer
		mPlayer = new MediaPlayer();

		surfaceHolder = surfaceView.getHolder();
		// ���ò���ʱ����Ļ
		surfaceHolder.setKeepScreenOn(true);
		surfaceHolder.addCallback(new SurfaceListener());

		mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		
		// ������Ҫ���ŵ���Ƶ
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

		//�ȴ�surfaceHolder��ʼ����ɲ���ִ��mPlayer.setDisplay(surfaceHolder)
		mPlayer.setOnPreparedListener(new OnPreparedListener() {       
           
            public void onPrepared(MediaPlayer mp) {
            	// ����Ƶ���������SurfaceView
        	
        		
            	mPlayer.start();
            	
            	
            }
        });
		
		//��Ƶ������ɺ�Ĳ���
		mPlayer.setOnCompletionListener(new OnCompletionListener() {
		
			public void onCompletion(MediaPlayer mp){
				if(mPlayer!=null)
					mPlayer.reset();//����mediaplayer�ȴ���һ�β���
				if(dialog.isShowing())
					dialog.dismiss(); //�رնԻ���
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
		// ֹͣ����
		if(mPlayer!=null)
		{
			if (mPlayer.isPlaying()) 
				mPlayer.stop();
			// �ͷ���Դ
			mPlayer.release();
		}
		super.onDestroy();
		finish();
	}
	
	/**

     * ����View��˸Ч��

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

     * ȡ��View��˸Ч��

     * 

     * */

    private void stopFlick( View view ){

        if( null == view ){

            return;

        }

        view.clearAnimation( );

    }

}
