package com.fstrise.ilovekara.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.callback.ImageOptions;
import com.fstrise.ilovekara.MainActivity;
import com.fstrise.ilovekara.R;

public class Utils {
	public static Typeface Roboto_Thin;
	public static Typeface Roboto_LightItalic;
	public static Typeface Roboto_Regular;
	public static Typeface Roboto_Bold;

	public Utils(Context cont) {
		Roboto_Thin = Typeface.createFromAsset(cont.getAssets(),
				"fonts/Roboto-Thin.ttf");
		Roboto_LightItalic = Typeface.createFromAsset(cont.getAssets(),
				"fonts/Roboto-LightItalic.ttf");
		Roboto_Regular = Typeface.createFromAsset(cont.getAssets(),
				"fonts/Roboto-Regular.ttf");
		Roboto_Bold = Typeface.createFromAsset(cont.getAssets(),
				"fonts/Roboto-Bold.ttf");

	}

	public static String getData(String url) {

		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();
		// Creating HTTP Post
		HttpGet httpGet = new HttpGet(url);
		// Making HTTP Request
		try {
			HttpResponse response = httpClient.execute(httpGet);
			// writing response to log
			HttpEntity entity = response.getEntity();
			InputStream is1 = entity.getContent();
			String programlist = convertStreamToString(is1);
			return programlist;
		} catch (ClientProtocolException e) {
			// writing exception to log
			e.printStackTrace();
		} catch (IOException e) {
			// writing exception to log
			e.printStackTrace();

		}
		return "";
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static void DisplayImage(final String url,
			final ImageView imageView, final int type) {
		MainActivity.aq.id(imageView).image(url, true, true, 0, AQuery.FADE_IN);
		//

	}

	public static String UppercaseFirstLetters(String str) {
		boolean prevWasWhiteSp = true;
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (Character.isLetter(chars[i])) {
				if (prevWasWhiteSp) {
					chars[i] = Character.toUpperCase(chars[i]);
				}
				prevWasWhiteSp = false;
			} else {
				prevWasWhiteSp = Character.isWhitespace(chars[i]);
			}
		}
		return new String(chars);
	}
}
