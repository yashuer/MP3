package yy.fragment;

import yy.musicplayer.R;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Remote extends Fragment{
	WebView wv;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_remote, container,false);
		wv = ((WebView) rootView.findViewById(R.id.wv));
		wv.setWebViewClient(new WebViewClient());
		wv.loadUrl("http://www.baidu.com");
		
		return rootView;
	}
	

}
