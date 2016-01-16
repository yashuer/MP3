package yy.musicplayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Queue;

import yy.lrc.LrcProcessor;
import yy.musicplayer.service.PlayerService;
import yy.songmodel.AppConstant;
import yy.songmodel.SongInfo;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Build;

public class PlayActivity extends Activity implements OnClickListener{

	ImageButton beginButton = null;
	ImageButton pauseButton = null;
	ImageButton stopButton = null;
	MediaPlayer mediaPlayer = null;

	private ArrayList<Queue> queues = null;
	private TextView lrcTextView = null;
	private SongInfo songInfo = null;
	private Handler handler = new Handler();
	private UpdateTimeCallback updateTimeCallback = null;
	private long begin = 0;
	private long nextTimeMill = 0;
	private long currentTimeMill = 0;
	private String message = null;
	private long pauseTimeMills = 0;
	private boolean isPlaying = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		
		Intent in = getIntent();
		songInfo = (SongInfo) in.getSerializableExtra("songInfo");
		beginButton = (ImageButton) findViewById(R.id.begin);
		pauseButton = (ImageButton) findViewById(R.id.pause);
		stopButton = (ImageButton) findViewById(R.id.stop);
		beginButton.setOnClickListener(this);
		pauseButton.setOnClickListener(this);
		stopButton.setOnClickListener(this);
		lrcTextView = (TextView) findViewById(R.id.lrcname);
		}
	
	private void prepareLrc(String lrcName){
		try {
			System.out.println("------------>prepare  lrc");
			InputStream inputStream = new FileInputStream(Environment.getExternalStorageDirectory().getAbsoluteFile() +File.separator + "yymp3"+File.separator + lrcName);
			LrcProcessor lrcProcessor = new LrcProcessor();
			queues = lrcProcessor.process(inputStream);
			//创建一个UpdateTimeCallback对象
			//System.out.println("队列－－－－＞" + queues);
			updateTimeCallback = new UpdateTimeCallback(queues);
			begin = 0 ;
			currentTimeMill = 0 ;
			nextTimeMill = 0 ;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("--->prepare异常");
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.begin:
			if(!isPlaying){
			Intent bintent = new Intent();
			bintent.setClass(PlayActivity.this, PlayerService.class);
			bintent.putExtra("songInfo", songInfo);
			bintent.putExtra("MSG", AppConstant.PlayerMsg.PLAY_MSG);
			//读取LRC文件
			//System.out.println("btn --->begin" + songInfo.getLrcName());
			prepareLrc(songInfo.getLrcName());
			//启动Service
			startService(bintent);
			System.out.println("---->service  complete!!");
			//将begin的值置为当前毫秒数
			begin = System.currentTimeMillis();
			//执行updateTimeCallback
			handler.postDelayed(updateTimeCallback, 5);
			isPlaying = true;
	}
			break;
			
		case R.id.pause:
			//通知Service暂停播放MP3
			Intent pintent = new Intent();
			pintent.setClass(PlayActivity.this, PlayerService.class);
			pintent.putExtra("MSG", AppConstant.PlayerMsg.PAUSE_MSG);
			startService(pintent);
			//
			if(isPlaying){
				handler.removeCallbacks(updateTimeCallback);
				pauseTimeMills = System.currentTimeMillis();
			}
			else{
				handler.postDelayed(updateTimeCallback, 5);
				begin = System.currentTimeMillis() - pauseTimeMills + begin;
			}
			isPlaying = isPlaying ? false : true;
			break;
			
		case R.id.stop:
			//通知Service停止播放MP3文件
			Intent sintent = new Intent();
			sintent.setClass(PlayActivity.this, PlayerService.class);
			sintent.putExtra("MSG", AppConstant.PlayerMsg.STOP_MSG);
			startService(sintent);
			//从Handler当中移除updateTimeCallback
			handler.removeCallbacks(updateTimeCallback);
			
			break;

		default:
			break;
		}
		
	}

	
	class UpdateTimeCallback implements Runnable{
		Queue times = null;
		Queue messages = null;
		public UpdateTimeCallback(ArrayList<Queue> queues) {
			//从ArrayList当中取出相应的对象对象
			times = queues.get(0);
			messages = queues.get(1);
		}
		@Override
		public void run() {
			//计算偏移量，也就是说从开始播放MP3到现在为止，共消耗了多少时间，以毫秒为单位
			long offset = System.currentTimeMillis() - begin;
			System.out.println("--------->msg" + messages);
			if(currentTimeMill == 0){
				nextTimeMill = (Long)times.poll();
				message = (String)messages.poll();
			}
			if(offset >= nextTimeMill){
				lrcTextView.setText(message);
				System.out.println(message);
				message = (String)messages.poll();
				nextTimeMill = (Long)times.poll();
			}
			currentTimeMill = currentTimeMill + 10;
			handler.postDelayed(updateTimeCallback, 10);
		}
		
	}
	}

