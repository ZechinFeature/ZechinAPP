package com.zex.theme;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
//import android.util.ThemeUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
//import android.widget.BounceCoverFlow;
import android.widget.Button;
import android.widget.TextView;
//import com.mediatek.widget.BookmarkAdapter;
//import com.mediatek.widget.BookmarkItem;
import java.io.IOException;
import java.util.ArrayList;

public class ThemeDetail extends Activity
{
 // private BookmarkAdapter mAdapter;
 // private ArrayList<BookmarkItem> mBookmarkItems;
  //private BounceCoverFlow mCoverflow;
  boolean mIsCurrent;
  private ProgressDialog mProgressDialog;
  String mThemeName;
  private String mThemePackageName;
  BitmapDrawable mThumbnailIcon;
  BitmapDrawable mThumbnailKeyguard;
  BitmapDrawable mThumbnailWidget;
  Drawable mWallpaper;
  int mWallpaperResId;
  private TextView ThemeName;
  private Button Apply;
  
  private void doSwitch()
  {
    //ThemeUtils.setCurrentThemePackageName(this.mThemePackageName);
    setWallpaper();
    Intent localIntent = new Intent("com.dewav.theme.action.CHANGED");
    localIntent.putExtra("theme_pkgname", this.mThemePackageName);
    sendBroadcast(localIntent);
    finish();
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
  
  private void initBounceCoverFlow()
  {
    Resources localResources = getResources();
   // this.mCoverflow = ((BounceCoverFlow)findViewById(2131099650));
    //this.mCoverflow.setGravity(16);
  //  this.mCoverflow.setSpacing(localResources.getDimensionPixelSize(2130968578));
    //this.mBookmarkItems = new ArrayList();
   // BookmarkItem localBookmarkItem = new BookmarkItem(this.mThumbnailKeyguard.getBitmap(), this.mThemeName, null);
   // this.mBookmarkItems.add(localBookmarkItem);
    //localBookmarkItem = new BookmarkItem(this.mThumbnailWidget.getBitmap(), this.mThemeName, null);
   // this.mBookmarkItems.add(localBookmarkItem);
   // localBookmarkItem = new BookmarkItem(this.mThumbnailIcon.getBitmap(), this.mThemeName, null);
   // this.mBookmarkItems.add(localBookmarkItem);
   // this.mAdapter = new BookmarkAdapter(this, this.mBookmarkItems);
   // this.mAdapter.setImageDispSize(localResources.getDimensionPixelSize(2130968579), localResources.getDimensionPixelSize(2130968580));
   // this.mAdapter.setImageReflection(0.18F);
    //this.mCoverflow.setAdapter(this.mAdapter);
   // this.mCoverflow.setSelection(1);
   // this.mCoverflow.setEmptyView(null);
    //this.mCoverflow.setMaxZoomOut(120.0F);
  }
  
  private void loadDefultThemeDetail()
  {
    Resources localResources = getResources();
    this.mThemeName = getString(R.string.default_theme_name);
    this.mThumbnailKeyguard = ((BitmapDrawable)localResources.getDrawable(R.drawable.default_thumbnails_keyguard,null));
    this.mThumbnailWidget = ((BitmapDrawable)localResources.getDrawable(R.drawable.default_thumbnails_widget,null));
    this.mThumbnailIcon = ((BitmapDrawable)localResources.getDrawable(R.drawable.default_thumbnails_icon,null));
    this.mWallpaper = localResources.getDrawable(R.drawable.default_wallpaper,null);
    this.mWallpaperResId = 17302163;
  }
  
  private void loadThemeDetail()
  {
    if (/*TextUtils.isEmpty(this.mThemePackageName)*/true)
    {
      loadDefultThemeDetail();
      this.mIsCurrent = false;//TextUtils.equals(ThemeUtils.getCurrentThemePackageName(), this.mThemePackageName);
      if (this.mIsCurrent) {}
      return;
    }
    /*for (;;)
    {
      try
      {
    	PackageManager localPackageManager = getPackageManager();
        String localObject = ((PackageManager)localPackageManager).getApplicationLabel(((PackageManager)localPackageManager).getApplicationInfo(this.mThemePackageName, 0)).toString();
        if (TextUtils.isEmpty(localObject)) {
          continue;
        }
        //localObject = ((CharSequence)localObject).toString();
        this.mThemeName = localObject;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        Object localObject;
        continue;
      }
      this.mThumbnailKeyguard = ((BitmapDrawable)ThemeUtils.getTargetThemeDrawable(this, this.mThemePackageName, "thumbnails_keyguard"));
      this.mThumbnailWidget = ((BitmapDrawable)ThemeUtils.getTargetThemeDrawable(this, this.mThemePackageName, "thumbnails_widget"));
      this.mThumbnailIcon = ((BitmapDrawable)ThemeUtils.getTargetThemeDrawable(this, this.mThemePackageName, "thumbnails_icon"));
      this.mWallpaper = ThemeUtils.getTargetThemeDrawable(this, this.mThemePackageName, "theme_wallpaper");
      this.mWallpaperResId = ThemeUtils.getResourceId(this, this.mThemePackageName, "drawable", "theme_wallpaper");
      break;
    }*/
  }
  
  private void setWallpaper()
  {
    WallpaperManager localWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
    try
    {
      localWallpaperManager.setBitmap(((BitmapDrawable)this.mWallpaper).getBitmap());
      return;
    }
    catch (IOException localIOException) {}
  }
  
  private void showSetThemeDialog()
  {
    if (this.mProgressDialog == null) {
      this.mProgressDialog = ProgressDialog.show(this, null, getString(2131034119), true, false);
    }
  }
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.theme_detail);
    this.mThemePackageName = getIntent().getStringExtra("theme_pkgname");
    
    loadThemeDetail();
    ThemeName = (TextView)findViewById(R.id.theme_name);
    Apply = (Button)findViewById(R.id.apply); 
    if (this.mIsCurrent)
    {
    	ThemeName.setText(this.mThemeName + "(" + getResources().getString(R.string.current) + ")");
    	  
    	Apply.setEnabled(false);
    }else{
    	Apply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switchTheme();
			}
		});
    }
   /* for (boolean bool = true;; bool = false)
    {
      Apply.setEnabled(bool);
      //initBounceCoverFlow();
      return;
      ThemeName.setText(this.mThemeName);
      break;
    }*/
  }
  
  protected void onDestroy()
  {
    finishSetThemeDialog();
    super.onDestroy();
  }
  
  /*public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    }
    for (;;)
    {
      return true;
      finish();
    }
  }
  */
  protected void onStart()
  {
    super.onStart();
    getActionBar().setDisplayHomeAsUpEnabled(true);
  }
  
  public void switchTheme()
  {
    showSetThemeDialog();
    new SetThemeTask().execute(new Void[0]);
  }
  
  private class SetThemeTask extends AsyncTask<Void, Void, Integer>
  {
    private SetThemeTask() {}
    
    protected Integer doInBackground(Void... paramVarArgs)
    {
      ThemeDetail.this.doSwitch();
      return null;
    }
    
    protected void onPostExecute(Integer paramInteger)
    {
      ThemeDetail.this.finishSetThemeDialog();
      ThemeDetail.this.finish();
    }
  }
}
