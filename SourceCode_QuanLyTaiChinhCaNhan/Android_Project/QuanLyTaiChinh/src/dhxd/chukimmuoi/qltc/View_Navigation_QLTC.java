package dhxd.chukimmuoi.qltc;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
//import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import dhxd.chukimmuoi.adapter.NavigationAdapter;
import dhxd.chukimmuoi.controller.ctr_doitac;
import dhxd.chukimmuoi.controller.ctr_kehoach;
import dhxd.chukimmuoi.fragments.DanhSachDoiTacFragment;
import dhxd.chukimmuoi.fragments.DanhSachGiaoDichFragment;
import dhxd.chukimmuoi.fragments.DanhSachKeHoachCongViecFragment;
import dhxd.chukimmuoi.fragments.DanhSachKeHoachNganSachFragment;
import dhxd.chukimmuoi.fragments.DanhSachNhomFragment;
import dhxd.chukimmuoi.fragments.DanhSachQuyFragment;
import dhxd.chukimmuoi.fragments.DanhSachSoNoFragment;
import dhxd.chukimmuoi.fragments.DanhSachThongBaoFragment;
import dhxd.chukimmuoi.model.tbl_doitac;
import dhxd.chukimmuoi.model.tbl_kehoach;
import dhxd.chukimmuoi.model.tbl_nguoidung;
import dhxd.chukimmuoi.service.ser_nguoidung;
import dhxd.chukimmuoi.utils.Constant;
import dhxd.chukimmuoi.utils.Menu_Phai;
import dhxd.chukimmuoi.utils.Icon_MenuItem;

@SuppressWarnings("deprecation")
public class View_Navigation_QLTC extends ActionBarActivity {

	private int mLastPosition = 1;
	private ListView mListDrawer;

	private DrawerLayout mLayoutDrawer;
	private RelativeLayout mRelativeDrawer;

	private FragmentManager mFragmentManager;
	private NavigationAdapter mNavigationAdapter;
	private ActionBarDrawerToggleCompat mDrawerToggle;

	private static String KEY_SUCCESS = "success";

//	private int ThongBao = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ser_nguoidung serND = new ser_nguoidung(getApplicationContext());
		if (serND.getSoDong() > 0) {
			getSupportActionBar().setIcon(R.drawable.ic_launcher);

			setContentView(R.layout.view_navigation_qltc);

			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setHomeButtonEnabled(true);

			mListDrawer = (ListView) findViewById(R.id.listDrawer);
			mRelativeDrawer = (RelativeLayout) findViewById(R.id.relativeDrawer);
			mLayoutDrawer = (DrawerLayout) findViewById(R.id.layoutDrawer);

			if (mListDrawer != null) {

				// All header menus should be informed here
				// listHeader.add(MENU POSITION)
				List<Integer> mListHeader = new ArrayList<Integer>();
				mListHeader.add(0);
				mListHeader.add(4);
				mListHeader.add(8);
				mListHeader.add(12);

				// All menus which will contain an accountant should be informed
				// here
				// Counter.put ("POSITION MENU", "VALUE COUNTER");
				SparseIntArray mCounter = new SparseIntArray();
				// SET GIÁ TRỊ CHO Ô THÔNG BÁO
//				new selectGiaoDich().execute();
//				new selectDoiTac().execute();
				
				mCounter.put(Constant.MENU_THONGBAO,
						(ThongBaoGiaoDich() + ThongBaoDoiTac()));
//						(ThongBao));

				mNavigationAdapter = new NavigationAdapter(this,
						NavigationList.getNavigationAdapter(this, mListHeader,
								mCounter, null));
			}

			mListDrawer.setAdapter(mNavigationAdapter);
			mListDrawer.setOnItemClickListener(new DrawerItemClickListener());

			mDrawerToggle = new ActionBarDrawerToggleCompat(this, mLayoutDrawer);
			mLayoutDrawer.setDrawerListener(mDrawerToggle);

			if (savedInstanceState != null) {
				setLastPosition(savedInstanceState
						.getInt(Constant.LAST_POSITION));

				setTitleFragments(mLastPosition);
				mNavigationAdapter.resetarCheck();
				mNavigationAdapter.setChecked(mLastPosition, true);
			} else {
				setLastPosition(mLastPosition);
				setFragmentList(mLastPosition);
			}
		} else {
			Intent intent_DN = new Intent(View_Navigation_QLTC.this,
					View_frm_DangNhap.class);
			intent_DN.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent_DN);
			finish();
		}
	}

	public int ThongBaoGiaoDich() {
		int tong = 0;
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			try {
				URL url = new URL("http://www.google.com");
				HttpURLConnection httpUrlConn = (HttpURLConnection) url
						.openConnection();
				httpUrlConn.setConnectTimeout(3000);
				httpUrlConn.connect();
				if (httpUrlConn.getResponseCode() == 200) {
					ctr_kehoach ctrKH = new ctr_kehoach();
					tbl_kehoach tblKH = new tbl_kehoach();
					tbl_nguoidung tblND = new tbl_nguoidung();
					ser_nguoidung serND = new ser_nguoidung(
							getApplicationContext());
					tblND = serND.selectNguoiDung();

					tblKH.setEmail(tblND.getEmail());
					JSONObject objJSON = ctrKH.selectThongBaoGiaoDich(tblKH);
					if (objJSON != null) {
						try {
							if (objJSON.getString(KEY_SUCCESS) != null) {

								String success = objJSON.getString(KEY_SUCCESS);

								if (Integer.parseInt(success) == 1) {

									JSONArray arrJSON = objJSON
											.getJSONArray("tbl_kehoach");
									for (int i = 0; i < arrJSON.length(); i++) {
										JSONObject c = arrJSON.getJSONObject(i);
										if (c.getInt("TrangThai") == 0) {
											tong = tong + 1;
										}
									}
									if (tong > 0) {
										Toast.makeText(
												getApplicationContext(),
												"Có thông báo giao dịch mới được gửi đến.",
												Toast.LENGTH_SHORT).show();
									}
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tong;
	}

//	private class selectGiaoDich extends AsyncTask<String, String, JSONObject> {
//		private int tong = 0;
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//		}
//
//		@Override
//		protected JSONObject doInBackground(String... params) {
//			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//			NetworkInfo netInfo = cm.getActiveNetworkInfo();
//			if (netInfo != null && netInfo.isConnected()) {
//				try {
//					URL url = new URL("http://www.google.com");
//					HttpURLConnection httpUrlConn = (HttpURLConnection) url
//							.openConnection();
//					httpUrlConn.setConnectTimeout(3000);
//					httpUrlConn.connect();
//					if (httpUrlConn.getResponseCode() == 200) {
//						ctr_kehoach ctrKH = new ctr_kehoach();
//						tbl_kehoach tblKH = new tbl_kehoach();
//						tbl_nguoidung tblND = new tbl_nguoidung();
//						ser_nguoidung serND = new ser_nguoidung(
//								getApplicationContext());
//						tblND = serND.selectNguoiDung();
//
//						tblKH.setEmail(tblND.getEmail());
//						JSONObject objJSON = ctrKH
//								.selectThongBaoGiaoDich(tblKH);
//						return objJSON;
//					}
//				} catch (MalformedURLException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(JSONObject objJSON) {
//			if (objJSON != null) {
//				try {
//					if (objJSON.getString(KEY_SUCCESS) != null) {
//
//						String success = objJSON.getString(KEY_SUCCESS);
//
//						if (Integer.parseInt(success) == 1) {
//
//							JSONArray arrJSON = objJSON
//									.getJSONArray("tbl_kehoach");
//							for (int i = 0; i < arrJSON.length(); i++) {
//								JSONObject c = arrJSON.getJSONObject(i);
//								if (c.getInt("TrangThai") == 0) {
//									tong = tong + 1;
//								}
//							}
//							if (tong > 0) {
//								ThongBao = ThongBao + tong;
//								Toast.makeText(
//										getApplicationContext(),
//										"Có thông báo giao dịch mới được gửi đến.",
//										Toast.LENGTH_SHORT).show();
//							}
//						}
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}

	public int ThongBaoDoiTac() {
		int tong = 0;
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			try {
				URL url = new URL("http://www.google.com");
				HttpURLConnection httpUrlConn = (HttpURLConnection) url
						.openConnection();
				httpUrlConn.setConnectTimeout(3000);
				httpUrlConn.connect();
				if (httpUrlConn.getResponseCode() == 200) {
					tbl_doitac tblDT = new tbl_doitac();
					ctr_doitac ctrDT = new ctr_doitac();
					tbl_nguoidung tblND = new tbl_nguoidung();
					ser_nguoidung ser_ND = new ser_nguoidung(
							getApplicationContext());

					tblND = ser_ND.selectNguoiDung();
					tblDT.setEmail(tblND.getEmail());

					JSONObject objJSON = ctrDT.selectDoiTacThongBao(tblDT);
					if (objJSON != null) {
						try {
							if (objJSON.getString(KEY_SUCCESS) != null) {

								String success = objJSON.getString(KEY_SUCCESS);

								if (Integer.parseInt(success) == 1) {
									JSONArray arrJSON = objJSON
											.getJSONArray("tbl_doitac");
									for (int i = 0; i < arrJSON.length(); i++) {
										JSONObject c = arrJSON.getJSONObject(i);
										if (c.getInt("XacNhan") == 0) {
											tong = tong + 1;
										}
									}
									if (tong > 0) {
										Toast.makeText(
												getApplicationContext(),
												"Có lời mời kết bạn được gửi cho bạn.",
												Toast.LENGTH_SHORT).show();
									}
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tong;
	}

//	private class selectDoiTac extends AsyncTask<String, String, JSONObject> {
//		private int tong = 0;
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//		}
//
//		@Override
//		protected JSONObject doInBackground(String... params) {
//			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//			NetworkInfo netInfo = cm.getActiveNetworkInfo();
//			if (netInfo != null && netInfo.isConnected()) {
//				try {
//					URL url = new URL("http://www.google.com");
//					HttpURLConnection httpUrlConn = (HttpURLConnection) url
//							.openConnection();
//					httpUrlConn.setConnectTimeout(3000);
//					httpUrlConn.connect();
//					if (httpUrlConn.getResponseCode() == 200) {
//						tbl_doitac tblDT = new tbl_doitac();
//						ctr_doitac ctrDT = new ctr_doitac();
//						tbl_nguoidung tblND = new tbl_nguoidung();
//						ser_nguoidung ser_ND = new ser_nguoidung(
//								getApplicationContext());
//
//						tblND = ser_ND.selectNguoiDung();
//						tblDT.setEmail(tblND.getEmail());
//
//						JSONObject objJSON = ctrDT.selectDoiTacThongBao(tblDT);
//						return objJSON;
//					}
//				} catch (MalformedURLException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(JSONObject objJSON) {
//			if (objJSON != null) {
//				try {
//					if (objJSON.getString(KEY_SUCCESS) != null) {
//
//						String success = objJSON.getString(KEY_SUCCESS);
//
//						if (Integer.parseInt(success) == 1) {
//							JSONArray arrJSON = objJSON
//									.getJSONArray("tbl_doitac");
//							for (int i = 0; i < arrJSON.length(); i++) {
//								JSONObject c = arrJSON.getJSONObject(i);
//								if (c.getInt("XacNhan") == 0) {
//									tong = tong + 1;
//								}
//							}
//							if (tong > 0) {
//								ThongBao = ThongBao + tong;
//								Toast.makeText(getApplicationContext(),
//										"Có lời mời kết bạn được gửi cho bạn.",
//										Toast.LENGTH_SHORT).show();
//							}
//						}
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}

	private void setFragmentList(int posicao) {

		Fragment mFragment = null;
		mFragmentManager = getSupportFragmentManager();

		switch (posicao) {
		case Constant.MENU_DANHSACHGIAODICH:
			mFragment = new DanhSachGiaoDichFragment()
					.newInstance(Icon_MenuItem.getTitleItem(
							View_Navigation_QLTC.this,
							Constant.MENU_DANHSACHGIAODICH));
			break;

		case Constant.MENU_BAOCAO:
			Intent Intent_BC = new Intent(View_Navigation_QLTC.this,
					View_frm_BaoCao_TongQuatNam.class);
			Intent_BC.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(Intent_BC);
			break;

		case Constant.MENU_SONO:
			mFragment = new DanhSachSoNoFragment()
					.newInstance(Icon_MenuItem.getTitleItem(
							View_Navigation_QLTC.this, Constant.MENU_SONO));
			break;

		case Constant.MENU_THONGBAO:
			mFragment = new DanhSachThongBaoFragment()
					.newInstance(Icon_MenuItem.getTitleItem(
							View_Navigation_QLTC.this, Constant.MENU_THONGBAO));
			break;

		case Constant.MENU_DOIMATKHAU:
			Intent Intent_DMK = new Intent(View_Navigation_QLTC.this,
					View_frm_DoiMatKhau.class);
			Intent_DMK.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(Intent_DMK);
			break;

		case Constant.MENU_DANGXUAT:
			ser_nguoidung serND = new ser_nguoidung(getApplicationContext());
			serND.deleteNguoiDung();

			Intent Intent_DX = new Intent(View_Navigation_QLTC.this,
					View_frm_DangNhap.class);
			Intent_DX.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(Intent_DX);
			finish();
			break;

		case Constant.MENU_VI:
			mFragment = new DanhSachQuyFragment().newInstance(Icon_MenuItem
					.getTitleItem(View_Navigation_QLTC.this, Constant.MENU_VI));
			break;

		case Constant.MENU_NHOMCHITIEU:
			mFragment = new DanhSachNhomFragment().newInstance(Icon_MenuItem
					.getTitleItem(View_Navigation_QLTC.this,
							Constant.MENU_NHOMCHITIEU));
			break;

		case Constant.MENU_DOITAC:
			mFragment = new DanhSachDoiTacFragment().newInstance(Icon_MenuItem
					.getTitleItem(View_Navigation_QLTC.this,
							Constant.MENU_DOITAC));
			break;

		case Constant.MENU_NGANSACH:
			mFragment = new DanhSachKeHoachNganSachFragment()
					.newInstance(Icon_MenuItem.getTitleItem(
							View_Navigation_QLTC.this, Constant.MENU_NGANSACH));
			break;

		case Constant.MENU_CONGVIEC:
			mFragment = new DanhSachKeHoachCongViecFragment()
					.newInstance(Icon_MenuItem.getTitleItem(
							View_Navigation_QLTC.this, Constant.MENU_CONGVIEC));
			break;
		}

		if (mFragment != null) {
			setTitleFragments(mLastPosition);
			mNavigationAdapter.resetarCheck();
			mNavigationAdapter.setChecked(posicao, true);
			mFragmentManager.beginTransaction()
					.replace(R.id.content_frame, mFragment).commit();
		}
	}

	// ẨN HIỆN CÁC MENU
	private void hideMenus(Menu menu, int posicao) {

		boolean drawerOpen = mLayoutDrawer.isDrawerOpen(mRelativeDrawer);

		switch (posicao) {
		case Constant.MENU_DANHSACHGIAODICH:
			menu.findItem(Menu_Phai.THEMMOI).setVisible(!drawerOpen);
			menu.findItem(Menu_Phai.TIMKIEM).setVisible(!drawerOpen);
			break;

		case Constant.MENU_BAOCAO:
			menu.findItem(Menu_Phai.THEMMOI).setVisible(!drawerOpen);
			menu.findItem(Menu_Phai.TIMKIEM).setVisible(!drawerOpen);
			break;

		case Constant.MENU_NHOMCHITIEU:
			menu.findItem(Menu_Phai.THEMMOI).setVisible(!drawerOpen);
			menu.findItem(Menu_Phai.TIMKIEM).setVisible(!drawerOpen);
			break;

		case Constant.MENU_DOITAC:
			menu.findItem(Menu_Phai.THEMMOI).setVisible(!drawerOpen);
			menu.findItem(Menu_Phai.TIMKIEM).setVisible(!drawerOpen);
			break;

		case Constant.MENU_VI:
			menu.findItem(Menu_Phai.THEMMOI).setVisible(!drawerOpen);
			menu.findItem(Menu_Phai.TIMKIEM).setVisible(!drawerOpen);
			break;

		case Constant.MENU_NGANSACH:
			menu.findItem(Menu_Phai.THEMMOI).setVisible(!drawerOpen);
			menu.findItem(Menu_Phai.TIMKIEM).setVisible(!drawerOpen);
			break;

		case Constant.MENU_THONGBAO:
			// menu.findItem(Menu_Phai.THEMMOI).setVisible(!drawerOpen);
			menu.findItem(Menu_Phai.TIMKIEM).setVisible(!drawerOpen);
			break;

		case Constant.MENU_SONO:
			// menu.findItem(Menu_Phai.THEMMOI).setVisible(!drawerOpen);
			menu.findItem(Menu_Phai.TIMKIEM).setVisible(!drawerOpen);
			break;
		}
	}

	private void setTitleFragments(int position) {
		setIconActionBar(Icon_MenuItem.iconNavigation[position]);
		setSubtitleActionBar(Icon_MenuItem.getTitleItem(
				View_Navigation_QLTC.this, position));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt(Constant.LAST_POSITION, mLastPosition);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case Menu_Phai.HOME:
			if (mLayoutDrawer.isDrawerOpen(mRelativeDrawer)) {
				mLayoutDrawer.closeDrawer(mRelativeDrawer);
			} else {
				mLayoutDrawer.openDrawer(mRelativeDrawer);
			}
			return true;
		default:

			if (mDrawerToggle.onOptionsItemSelected(item)) {
				return true;
			}

			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		hideMenus(menu, mLastPosition);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	public void setTitleActionBar(CharSequence informacao) {
		getSupportActionBar().setTitle(informacao);
	}

	public void setSubtitleActionBar(CharSequence informacao) {
		getSupportActionBar().setSubtitle(informacao);
	}

	public void setIconActionBar(int icon) {
		getSupportActionBar().setIcon(icon);
	}

	public void setLastPosition(int posicao) {
		this.mLastPosition = posicao;
	}

	private class ActionBarDrawerToggleCompat extends ActionBarDrawerToggle {

		public ActionBarDrawerToggleCompat(Activity mActivity,
				DrawerLayout mDrawerLayout) {
			super(mActivity, mDrawerLayout,
					R.drawable.ic_action_navigation_drawer,
					R.string.drawer_open, R.string.drawer_close);
		}

		@Override
		public void onDrawerClosed(View view) {
			supportInvalidateOptionsMenu();
		}

		@Override
		public void onDrawerOpened(View drawerView) {
			mNavigationAdapter.notifyDataSetChanged();
			supportInvalidateOptionsMenu();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int posicao,
				long id) {
			setLastPosition(posicao);
			setFragmentList(mLastPosition);
			mLayoutDrawer.closeDrawer(mRelativeDrawer);
		}
	}
}
