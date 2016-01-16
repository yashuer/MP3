package yy.downloadThread;

import yy.musicplayer.FrontActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class DownThread extends Thread {
	Handler handler;

	@Override
	public void run() {
		System.out.println("come in thread");
		Looper.prepare();
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				String s = "this is the new thread";
				Message message = new Message();
				Bundle b = new Bundle();
				b.putString("ss", s);
				message.setData(b);
				//FrontActivity.getFront().handler.sendMessage(message);
			}

		};
		Looper.loop();
	}

}
