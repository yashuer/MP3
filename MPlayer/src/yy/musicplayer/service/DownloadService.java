package yy.musicplayer.service;

import yy.download.Downloader;
import yy.songmodel.SongInfo;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloadService extends Service{

	private static String url = "http://192.168.191.3:8080/MP3/";
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		SongInfo song = (SongInfo)intent.getSerializableExtra("song");
		System.out.println("-------->service"+song);
		DownloadThread downloadThread = new DownloadThread(song); 
		Thread th = new Thread(downloadThread);
		th.start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	class DownloadThread implements Runnable{
		private SongInfo song = null;
		
		public DownloadThread(SongInfo song) {
			this.song = song;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String urlStr = url + song.getName();
			Downloader downloader = new Downloader();
			int result = downloader.downFile(urlStr, "yymp3/", song.getName());
			System.out.println(result);
			if(result == 0 ){
				
			}
		}
		
	}

}
