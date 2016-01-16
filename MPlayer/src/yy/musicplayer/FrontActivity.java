package yy.musicplayer;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import yy.download.Downloader;
import yy.musicplayer.service.DownloadService;
import yy.songmodel.SongInfo;
import yy.xml.SongsHandler;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class FrontActivity extends ListActivity implements OnClickListener {
	private static final int UPDATE = 1;
	private static final int ABOUT = 2;
	private static final String SERVERURL = "http://192.168.191.3:8080/MP3/Mp3List.xml";
	private MyHandler2 myHandler2;
	private String downxml;
	private List<SongInfo> songInfos = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_front);
		updateView(downxml);
	}

	class MyHandler2 extends Handler {
		public MyHandler2() {

		}

		public MyHandler2(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			downxml = (String) msg.obj;
			System.out.println("xml" + SERVERURL + downxml);

		}
	}

	class MyHandler extends Handler {
		// public MyHandler(){
		//
		// }
		// public MyHandler(Looper looper){
		// super(looper);
		// }
		// @Override
		// public void handleMessage(Message msg) {
		// System.out.println("Handler--->" + Thread.currentThread().getId());
		// System.out.println("handlerMessage");
		// }
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, UPDATE, 1, R.string.update);
		menu.add(0, ABOUT, 2, R.string.about);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == UPDATE) {
			updateView(downxml);
		} else if (item.getItemId() == ABOUT) {
			about();
		}
		return super.onOptionsItemSelected(item);
	}

	private void about() {
		
		
	}

	private void updateView(String xml) {
		DownThread();
		songInfos = parse(xml);
		List<HashMap<String, String>> listmap = new ArrayList<HashMap<String, String>>();
		for (Iterator iterator = songInfos.iterator(); iterator.hasNext();) {
			SongInfo info = (SongInfo) iterator.next();
			HashMap<String, String> map = new HashMap<>();
			map.put("name", info.getName());
			map.put("singer", info.getSinger());
			listmap.add(map);
			SimpleAdapter sAdapter = new SimpleAdapter(getApplicationContext(),
					listmap, R.layout.update_item, new String[] { "name",
							"singer" }, new int[] { R.id.name, R.id.singer });
			setListAdapter(sAdapter);
			Toast.makeText(getApplicationContext(), "success update !",
					Toast.LENGTH_SHORT).show();
		}
	}

	private String downloadXml(String url) {
		Downloader hd = new Downloader();
		String result = hd.download(url);
		return result;
	}

	private List<SongInfo> parse(String xmlStr) {
		SAXParserFactory sax = SAXParserFactory.newInstance();
		List<SongInfo> infos = new ArrayList<SongInfo>();
		try {
			XMLReader xReader = sax.newSAXParser().getXMLReader();
			SongsHandler hander = new SongsHandler(infos);
			xReader.setContentHandler(hander);
			xReader.parse(new InputSource(new StringReader(xmlStr)));
			for (Iterator iterator = infos.iterator(); iterator.hasNext();) {
				SongInfo songInfo = (SongInfo) iterator.next();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infos;

	}

	private void DownThread() {
		new Thread() {

			@Override
			public void run() {
				Looper.prepare();
				String xml = downloadXml(SERVERURL);
				myHandler2 = new MyHandler2(getMainLooper());
				Message msg1 = new Message();
				msg1.obj = xml;
				myHandler2.sendMessage(msg1);
				Looper.loop();
				super.run();
			}
		}.start();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		SongInfo songInfo = songInfos.get(position);
		Intent intent = new Intent();
		intent.putExtra("song", songInfo);
		intent.setClass(getApplicationContext(), DownloadService.class);
		startService(intent);
	}

}
