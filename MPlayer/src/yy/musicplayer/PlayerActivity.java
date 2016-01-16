package yy.musicplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import yy.fileio.FileUtils;
import yy.fragment.Local;
import yy.fragment.Remote;
import yy.songmodel.SongInfo;
import android.app.ActionBar.Tab;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class PlayerActivity extends FragmentActivity implements
		ActionBar.TabListener {
	private static List<SongInfo> songInfos;
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.player, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			settingDialog();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			switch (position) {
			case 0:
				return new PlaceholderFragment();
			case 1:
				return new Remote();
			case 2:
				return new Local();
			}
			return null;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	public static class PlaceholderFragment extends android.app.ListFragment {
//		private static final String ARG_SECTION_NUMBER = "section_number";
//
//		public static PlaceholderFragment newInstance(int sectionNumber) {
//			PlaceholderFragment fragment = new PlaceholderFragment();
//			Bundle args = new Bundle();
//			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//			fragment.setArguments(args);
//			return fragment;
//		}
//
//		public PlaceholderFragment() {
//		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {

			if (songInfos != null) {
				SongInfo songInfo = songInfos.get(position);
				Intent in = new Intent();
				in.putExtra("songInfo", songInfo);
				in.setClass(getActivity(), PlayActivity.class);
				startActivity(in);
			}

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_list,
					container, false);

			FileUtils files = new FileUtils();
			songInfos = files.getSongFiles("yymp3/");
			List<HashMap<String, String>> listmap = new ArrayList<HashMap<String, String>>();
			for (Iterator iterator = songInfos.iterator(); iterator.hasNext();) {
				SongInfo info = (SongInfo) iterator.next();
				System.out.println(info);
				HashMap<String, String> map = new HashMap<>();
				map.put("name", info.getName());
				map.put("size", info.getSize());
				listmap.add(map);
			}
			SimpleAdapter sAdapter = new SimpleAdapter(getActivity(), listmap,
					R.layout.local_list, new String[] { "name", "size" },
					new int[] { R.id.lname, R.id.lsize });
			setListAdapter(sAdapter);
			// Toast.makeText(getActivity(), "success update !",
			// Toast.LENGTH_SHORT).show();
			return rootView;
		}
	}

	private void settingDialog() {
		LayoutInflater in = PlayerActivity.this.getLayoutInflater();
		final View datil = in.inflate(R.layout.datil, null);

		new AlertDialog.Builder(this).setTitle("武汉轻工大学")
				.setIcon(R.drawable.schoolicon).setView(datil)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(PlayerActivity.this, "阅览完毕，感谢您对本软件的使用！",
								Toast.LENGTH_SHORT).show();
					}

				}).setNegativeButton("取消", null).show();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}
