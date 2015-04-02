package com.fstrise.ilovekara.page;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.fstrise.ilovekara.FirstPage;
import com.fstrise.ilovekara.MainActivity;
import com.fstrise.ilovekara.PlayerActivity;
import com.fstrise.ilovekara.PlayerViewDemoActivity;
import com.fstrise.ilovekara.R;
import com.fstrise.ilovekara.adapter.VideoPagingAdaper;
import com.fstrise.ilovekara.classinfo.video;
import com.fstrise.ilovekara.custom.SafeAsyncTask;
import com.fstrise.ilovekara.paginglistview.PagingListView;
import com.fstrise.ilovekara.quickaction.ActionItem;
import com.fstrise.ilovekara.quickaction.QuickAction;
import com.fstrise.ilovekara.utils.Utils;

@SuppressLint("ValidFragment")
public class PageHot extends Fragment {
	public PageHot(Context context, MainActivity ma) {
		mConetxt = context;
		mActivity = ma;
	}

	private MainActivity mActivity;
	private PagingListView listView;
	private VideoPagingAdaper adapter;
	private int pager = 0;
	private List<String> firstList;
	private List<String> secondList;
	private List<String> thirdList;
	private List<video> listVideo;
	private static final int ID_ADD = 1;
	private static final int ID_ACCEPT = 2;
	private static final int ID_UPLOAD = 3;
	private int mSelectedRow = 0;
	private String itemSelectedID;
	private String itemSelectedURL;
	private String itemSelectedTitle;
	private Context mConetxt;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.pager_songhot, container,
				false);
		listView = (PagingListView) rootView
				.findViewById(R.id.paging_list_view_hot);
		adapter = new VideoPagingAdaper();
		// initData();
		//
		listView.setAdapter(adapter);
		listView.setHasMoreItems(true);
		listView.setPagingableListener(new PagingListView.Pagingable() {
			@Override
			public void onLoadMoreItems() {
				if (pager < 20) {
					new loadVideoAsyncTask().execute();
				} else {
					listView.onFinishLoading(false, null);
				}
			}
		});
		ActionItem addItem = new ActionItem(ID_ADD, "Xem & Thu Âm",
				getResources().getDrawable(R.drawable.ic_add));
		ActionItem acceptItem = new ActionItem(ID_ACCEPT, "Thích",
				getResources().getDrawable(R.drawable.ic_accept));
		ActionItem uploadItem = new ActionItem(ID_UPLOAD, "Tải về máy",
				getResources().getDrawable(R.drawable.ic_up));

		final QuickAction mQuickAction = new QuickAction(getActivity()
				.getBaseContext());

		mQuickAction.addActionItem(addItem);
		mQuickAction.addActionItem(acceptItem);
		mQuickAction.addActionItem(uploadItem);

		// setup the action item click listener
		mQuickAction
				.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
					@Override
					public void onItemClick(QuickAction quickAction, int pos,
							int actionId) {
						ActionItem actionItem = quickAction.getActionItem(pos);

						if (actionId == ID_ADD) { // Add item selected
							// Intent it = new Intent(mActivity,
							// PlayerActivity.class);
							// mActivity.startActivity(it);
							// mActivity.overridePendingTransition(
							// R.anim.open_next, R.anim.close_main);
							Intent it = new Intent(mActivity,
									PlayerViewDemoActivity.class);
							it.putExtra("id", itemSelectedID);
							it.putExtra("url", itemSelectedURL);
							it.putExtra("title", itemSelectedTitle);							
							mActivity.startActivity(it);
							mActivity.overridePendingTransition(
									R.anim.open_next, R.anim.close_main);
							Toast.makeText(
									getActivity().getBaseContext(),
									"Add item selected on row " + mSelectedRow
											+ " T: " + itemSelectedID,
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(
									getActivity().getBaseContext(),
									actionItem.getTitle()
											+ " item selected on row "
											+ mSelectedRow, Toast.LENGTH_SHORT)
									.show();
						}
					}
				});

		// setup on dismiss listener, set the icon back to normal
		mQuickAction.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mSelectedRow = position; // set the selected row
				TextView txtID = (TextView) view.findViewById(R.id.txtID);
				itemSelectedID = txtID.getText().toString();
				TextView txtUrl = (TextView) view.findViewById(R.id.txtUrl);
				itemSelectedURL = txtUrl.getText().toString();
				TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
				itemSelectedTitle = txtTitle.getText().toString();
				mQuickAction.show(view);

				// change the right arrow icon to selected state
			}
		});
		return rootView;
	}

	// private void initData() {
	// firstList = new ArrayList<String>();
	// firstList.add("Afghanistan");
	// firstList.add("Albania");
	// firstList.add("Algeria");
	// firstList.add("Andorra");
	// firstList.add("Angola");
	// firstList.add("Antigua and Barbuda");
	// firstList.add("Argentina");
	// firstList.add("Armenia");
	// firstList.add("Aruba");
	// firstList.add("Australia");
	// firstList.add("Austria");
	// firstList.add("Azerbaijan");
	//
	// secondList = new ArrayList<String>();
	// secondList.add("Bahamas, The");
	// secondList.add("Bahrain");
	// secondList.add("Bangladesh");
	// secondList.add("Barbados");
	// secondList.add("Belarus");
	// secondList.add("Belgium");
	// secondList.add("Belize");
	// secondList.add("Benin");
	// secondList.add("Bhutan");
	// secondList.add("Bolivia");
	// secondList.add("Bosnia and Herzegovina");
	// secondList.add("Botswana");
	// secondList.add("Brazil");
	// secondList.add("Brunei");
	// secondList.add("Bulgaria");
	// secondList.add("Burkina Faso");
	// secondList.add("Burma");
	// secondList.add("Burundi");
	//
	// thirdList = new ArrayList<String>();
	// thirdList.add("Denmark");
	// thirdList.add("Djibouti");
	// thirdList.add("Dominica");
	// thirdList.add("Dominican Republic");
	// }

	private class loadVideoAsyncTask extends SafeAsyncTask<String> {

		@Override
		public String call() throws Exception {
			String result = Utils.getData("http://fstrise.com/api.aspx?page="
					+ (pager * 20));
			Thread.sleep(3000);
			return result;
		}

		@Override
		protected void onSuccess(String newItems) throws Exception {
			super.onSuccess(newItems);
			pager++;
			listVideo = new ArrayList<video>();
			try {
				JSONObject obj = new JSONObject(newItems);
				JSONArray objArr = new JSONArray(obj.getString("rows"));
				JSONObject e3 = null;
				for (int i = 0; i < objArr.length(); i++) {
					e3 = objArr.getJSONObject(i);
					video objvideo = new video();
					objvideo.setTitle(e3.getString("title"));
					objvideo.setLyrics(e3.getString("lyrics"));
					objvideo.setVideo_code(e3.getString("video_code"));
					objvideo.setUrl(e3.getString("url"));
					objvideo.setView(e3.getInt("view"));
					objvideo.setSinger(e3.getString("singer"));
					objvideo.setTotal_dl(e3.getInt("total_dl"));
					listVideo.add(objvideo);
				}

			} catch (JSONException e3) {
				e3.printStackTrace();
			}
			listView.onFinishLoading(true, listVideo);
		}
	}

}
