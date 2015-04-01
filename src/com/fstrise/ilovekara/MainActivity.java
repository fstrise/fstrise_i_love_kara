package com.fstrise.ilovekara;

import java.io.File;
import java.io.IOException;

import com.androidquery.AQuery;
import com.androidquery.util.AQUtility;
import com.fstrise.ilovekara.data.SqlLiteDbHelper;
import com.fstrise.ilovekara.pagerSlidingTabstrip.PageSlidingTabStripFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

public class MainActivity extends MaterialNavigationDrawer<Object> {

	MaterialNavigationDrawer<?> drawer = null;
	SqlLiteDbHelper dbHelper = new SqlLiteDbHelper(this);
	private File cacheDir;
	public static AQuery aq;

	@Override
	public int headerType() {
		// set type. you get the available constant from
		// MaterialNavigationDrawer class
		return MaterialNavigationDrawer.DRAWERHEADER_HEADITEMS;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init(Bundle savedInstanceState) {

		drawer = this;
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String hasdb = preferences.getString("hasdb", null);
		if (hasdb == null) {
			dbHelper = new SqlLiteDbHelper(this);
			try {
				dbHelper.CopyDataBaseFromAsset(this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"iLoveKara");
		else
			cacheDir = this.getCacheDir();
		if (!cacheDir.exists())
			cacheDir.mkdirs();
		AQUtility.setCacheDir(cacheDir);
		aq = new AQuery(this);

		MaterialMenu menu = new MaterialMenu();

		// create intent for settings activity
		Intent i = new Intent(this, Settings.class);

		this.newSection(getResources().getString(R.string.home), this
				.getResources().getDrawable(R.drawable.ic_myhome),
				new FragmentHome(MainActivity.this), false, menu);
		this.newDevisor(menu);
		// section with own in click listener
		this.newLabel(getResources().getString(R.string.ikara), false, menu);
		MaterialSection<?> section7 = this.newSection(
				getResources().getString(R.string.cakhuccuatoi), this
						.getResources().getDrawable(R.drawable.ic_mymusic),
				new FragmentIndex(), false, menu);
		section7.setNotifications(0);
		//
		MaterialSection<?> section8 = this.newSection(
				getResources().getString(R.string.myfavorite), this
						.getResources().getDrawable(R.drawable.ic_love),
				new FragmentIndex(), false, menu);
		section8.setNotifications(0);
		MaterialSection<?> section9 = this.newSection(
				getResources().getString(R.string.singdownload), this
						.getResources().getDrawable(R.drawable.ic_dl),
				new FragmentIndex(), false, menu);
		section9.setNotifications(0);
		this.newDevisor(menu);
		// section with own in click listener
		this.newLabel(getResources().getString(R.string.karaquan), false, menu);
		this.newSection(getResources().getString(R.string.karaquan5so), this
				.getResources().getDrawable(R.drawable.ic_5so),
				new PageSlidingTabStripFragment(), false, menu);
		this.newSection(getResources().getString(R.string.karaquan6so), this
				.getResources().getDrawable(R.drawable.ic_6so),
				new FragmentIndex(), false, menu);
		// use bitmap and make a circle photo
		final Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.logo_small);
		final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(
				getResources(), bitmap);

		// create Head Item
		MaterialHeadItem headItem = new MaterialHeadItem(this, getResources()
				.getString(R.string.ilovekara), getResources().getString(
				R.string.mysing), drawableAppIcon, R.drawable.karaoke, menu, 0);

		// add head Item (menu will be loaded automatically)
		this.addHeadItem(headItem);
	}

	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(
						this.getApplicationContext().getString(
								R.string.msgcloseapp1))
				.setMessage(
						this.getApplicationContext().getString(
								R.string.msgcloseapp))
				.setPositiveButton(
						this.getApplicationContext().getString(R.string.co),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}

						})
				.setNegativeButton(
						this.getApplicationContext().getString(R.string.khong),
						null).show();
	}
}
