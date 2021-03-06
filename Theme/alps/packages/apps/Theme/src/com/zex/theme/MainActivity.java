package com.zex.theme;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
//import android.util.ThemeUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends Activity implements
		AdapterView.OnItemClickListener {
	ThemeItemAdapter mAdapter;
	private String mCurrentTheme;
	GridView mGridView;
	private ProgressDialog mProgressDialog;
	private ArrayList<ThemeItem> mThemeItems;
	Drawable mWallpaper;

	private void loadThemeItems() {
		mThemeItems = new ArrayList<ThemeItem>();
		Object localObject = new ThemeItem();
		((ThemeItem) localObject).mPkgName = null;
		((ThemeItem) localObject).mThemeName = getString(R.string.default_theme_name);
		((ThemeItem) localObject).mThumbnailAll = getResources().getDrawable(R.drawable.default_thumbnails_all,null);
		((ThemeItem) localObject).mIsCurrent = TextUtils.equals(this.mCurrentTheme, ((ThemeItem) localObject).mPkgName);
		this.mThemeItems.add((ThemeItem)localObject);
		ThemeItem theme2 = new ThemeItem();
		theme2.mPkgName = null;
		theme2.mThemeName = getString(R.string.snow_theme_name);
		theme2.mThumbnailAll = getResources().getDrawable(R.drawable.thumbnails_all,null);
		theme2.mIsCurrent = TextUtils.equals(this.mCurrentTheme, theme2.mPkgName);
		this.mThemeItems.add(theme2);
		/*PackageManager localPackageManager = getPackageManager();
		List<ApplicationInfo> localList = localPackageManager.getInstalledApplications(0);
		int i = 0;
		while (i < localList.size()) {
			ApplicationInfo localApplicationInfo = localList.get(i);
			if (!ThemeUtils.isThemePackage(localApplicationInfo)) {
				i += 1;
			} else {
				Log.d("DWThemeManager", "pkgName = "
						+ localApplicationInfo.packageName);
				localObject = localPackageManager
						.getApplicationLabel(localApplicationInfo);
				ThemeItem localThemeItem = new ThemeItem();
				localThemeItem.mPkgName = localApplicationInfo.packageName;
				if (localObject != null) {
				}
				for (localObject = ((CharSequence) localObject).toString();; localObject = localApplicationInfo.packageName) {
					localThemeItem.mThemeName = ((String) localObject);
					localThemeItem.mThumbnailAll = ThemeUtils
							.getTargetThemeDrawable(this,
									localApplicationInfo.packageName,
									"thumbnails_all");
					localThemeItem.mIsCurrent = TextUtils.equals(
							this.mCurrentTheme,
							localApplicationInfo.packageName);
					this.mThemeItems.add(localThemeItem);
					break;
				}
			}
		}*/
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_main);
		mGridView = ((GridView) findViewById(R.id.grid_view));
		//this.mGridView.setSelector(new ColorDrawable(184483840));
		this.mCurrentTheme = getString(R.string.default_theme_name);//ThemeUtils.getCurrentThemePackageName();
		loadThemeItems();
		mAdapter = new ThemeItemAdapter(this,mThemeItems);
		mGridView.setAdapter(this.mAdapter);
		mGridView.setOnItemClickListener(this);
	}

	 protected void onDestroy()
	  {
	   // finishSetThemeDialog();
	    super.onDestroy();
	  }
	public void onItemClick(AdapterView<?> paramAdapterView, View paramView,
			int paramInt, long paramLong) {
		ThemeItem localThemeItem = (ThemeItem) this.mThemeItems.get(paramInt);
		Log.d("DWThemeManager", "onItemClick: item = "
				+ localThemeItem.mPkgName);
		Settings.System.putInt(this.getContentResolver(), "com.dewav.THEME", paramInt);
		//Intent paramTheme = new Intent(this, ThemeDetail.class);
		//paramTheme.putExtra("theme_pkgname", localThemeItem.mPkgName);
		//startActivity(paramTheme);
		switchTheme();
	}

	public void switchTheme()
	  {
	    showSetThemeDialog();
	    new SetThemeTask().execute(new Void[0]);
	  }
	private void showSetThemeDialog()
	  {
	    if (this.mProgressDialog == null) {
	      this.mProgressDialog = ProgressDialog.show(this, null, getString(2131034119), true, false);
	    }
	  }
	 private class SetThemeTask extends AsyncTask<Void, Void, Integer>
	  {
	    private SetThemeTask() {}
	    
	    protected Integer doInBackground(Void... paramVarArgs)
	    {
	      doSwitch();
	      return null;
	    }
	    
	    protected void onPostExecute(Integer paramInteger)
	    {
	      //finishSetThemeDialog();
	      finish();
	    }
	  }
	 private void finishSetThemeDialog()
	  {
	    Log.d("ThemeDetail", "finishSetThemeDialog()");
	    if (this.mProgressDialog != null)
	    {
	      this.mProgressDialog.dismiss();
	      this.mProgressDialog = null;
	    }
	  }
	 private void doSwitch()
	  {
	    //ThemeUtils.setCurrentThemePackageName(this.mThemePackageName);
	    setWallpaper();
	    Intent localIntent = new Intent("com.dewav.theme.action.CHANGED");
	    localIntent.putExtra("theme_pkgname", "  ");
	    sendBroadcast(localIntent);
	    finish();
	  }
	 private void setWallpaper(){
		Resources localResources = getResources();
	    WallpaperManager localWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
	    this.mWallpaper = localResources.getDrawable(R.drawable.default_wallpaper,null);
	    try
	    {
	      localWallpaperManager.setBitmap(((BitmapDrawable)this.mWallpaper).getBitmap());
	      return;
	    }
	    catch (IOException localIOException) {}
	  }
	protected void onResume() {
		super.onResume();
		String str = getString(R.string.default_theme_name);//ThemeUtils.getCurrentThemePackageName();
		if (!TextUtils.equals(this.mCurrentTheme, str)) {
			this.mCurrentTheme = str;
			Iterator localIterator = this.mThemeItems.iterator();
			while (localIterator.hasNext()) {
				ThemeItem localThemeItem = (ThemeItem) localIterator.next();
				localThemeItem.mIsCurrent = TextUtils.equals(str,
						localThemeItem.mPkgName);
			}
			this.mAdapter.notifyDataSetChanged();
		}
	}

	public class ThemeItem {
		boolean mIsCurrent;
		String mPkgName;
		String mThemeName;
		Drawable mThumbnailAll;

		public ThemeItem() {
		}
	}

	private class ThemeItemAdapter extends BaseAdapter {
		private final LayoutInflater mInflater;
		private ArrayList<MainActivity.ThemeItem> mItems;

		public ThemeItemAdapter(Context context,ArrayList<ThemeItem> paramArrayList) {
			mInflater = ((LayoutInflater) context.getSystemService("layout_inflater"));
			//ArrayList<MainActivity.ThemeItem> localArrayList;
			this.mItems = paramArrayList;
		}

		public int getCount() {
			return this.mItems.size();
		}

		public Object getItem(int paramInt) {
			return this.mItems.get(paramInt);
		}

		public long getItemId(int paramInt) {
			return paramInt;
		}

		public View getView(int paramInt, View paramView,
				ViewGroup paramViewGroup) {
			int i = 0;
			MainActivity.ThemeItem localThemeItem = (MainActivity.ThemeItem) getItem(paramInt);
			ViewHolder holder = null;
			if (paramView == null) {
				paramView = this.mInflater.inflate(R.layout.thumbnails_item,paramViewGroup,false);
				holder = new ViewHolder();
				holder.localtheumbanils = ((ImageView) paramView.findViewById(R.id.theumbnails));
				
				holder.localImageView = (ImageView) paramView.findViewById(R.id.current);
				paramView.setTag(holder);
			}else{
				holder = (ViewHolder)paramView.getTag();
			}
			holder.localtheumbanils.setImageDrawable(localThemeItem.mThumbnailAll);
			return paramView;
			/*for (;;) {
				localImageView.setVisibility(i);
				holder.mCover = paramView.findViewById(R.id.cover);
				holder.position = paramInt;
				paramViewGroup.setTag(paramView);
				paramView.setOnTouchListener(new View.OnTouchListener() {
					public boolean onTouch(View paramAnonymousView,
							MotionEvent paramAnonymousMotionEvent) {
						Log.d("DWThemeManager", "onTouch: view = "
								+ paramAnonymousView + ", action = "
								+ paramAnonymousMotionEvent.getAction());
						MainActivity.ViewHolder localViewHolder = (MainActivity.ViewHolder) paramAnonymousView
								.getTag();
						View localView = localViewHolder.mCover;
						switch (paramAnonymousMotionEvent.getAction()) {
						}
						for (;;) {
							return true;
							localView.setVisibility(0);
							continue;
							MainActivity.this.mGridView
									.performItemClick(
											paramAnonymousView,
											localViewHolder.position,
											MainActivity.this.mGridView
													.getItemIdAtPosition(localViewHolder.position));
							localView.setVisibility(4);
						}
						return true;
					}
				});
				return paramViewGroup;
			}*/
		}
	}

	private class ViewHolder {
		public ImageView localImageView;
		public ImageView localtheumbanils;
		public View mCover;
		public int position;

		private ViewHolder() {
		}
	}
}
