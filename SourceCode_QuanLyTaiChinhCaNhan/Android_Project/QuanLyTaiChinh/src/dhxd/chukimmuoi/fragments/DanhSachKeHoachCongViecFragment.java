package dhxd.chukimmuoi.fragments;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dhxd.chukimmuoi.adapter.Custom_Dialog_ChucNang_KeHoachCongViec;
import dhxd.chukimmuoi.adapter.Custom_Listview_DanhSachKeHoachCongViec;
import dhxd.chukimmuoi.controller.ctr_kehoach;
import dhxd.chukimmuoi.model.intent_kehoach;
import dhxd.chukimmuoi.model.intent_nhom;
import dhxd.chukimmuoi.model.intent_quy;
import dhxd.chukimmuoi.model.item_chucnang;
import dhxd.chukimmuoi.model.tbl_kehoach;
import dhxd.chukimmuoi.model.tbl_nguoidung;
import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.model.tbl_quy;
import dhxd.chukimmuoi.qltc.R;
import dhxd.chukimmuoi.qltc.View_frm_QuanLyGiaoDich;
import dhxd.chukimmuoi.qltc.View_frm_QuanLyKeHoachCongViec;
import dhxd.chukimmuoi.service.ser_nguoidung;
import dhxd.chukimmuoi.utils.Constant;
import dhxd.chukimmuoi.utils.Menu_Phai;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class DanhSachKeHoachCongViecFragment extends Fragment {
	SearchView searchView;
	boolean chontimkiem;
	ListView danhsachkehoachcongviec;
	private static final int REQUEST_CODE = 10;

	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_THONGBAO = "error_msg";

	private String KEY_KEHOACH_ID = "KeHoach_Id";
	private String KEY_NHOM_ID = "Nhom_Id";
	private String KEY_LOAI_ID = "Loai_Id";
	private String KEY_VI_ID = "Vi_Id";
	private String KEY_EMAIL = "Email";
	private String KEY_NGAYBATDAU = "NgayBatDau";
	private String KEY_DIENTA = "DienTa";
	private String KEY_SOTIEN = "SoTien";

	private String KEY_TENNHOM = "TenNhom";
	private String KEY_TENQUY = "TenQuy";
	private String KEY_ANH = "Anh";

	public static Custom_Listview_DanhSachKeHoachCongViec adapter;
	public static ArrayList<tbl_kehoach> listKeHoach;
	public static ArrayList<tbl_nhom> listNhom;
	public static ArrayList<tbl_quy> listQuy;
	JSONArray arrJSON = null;

	private Dialog dialog;
	private int chisochon;

	public DanhSachKeHoachCongViecFragment newInstance(String text) {
		DanhSachKeHoachCongViecFragment mFragment = new DanhSachKeHoachCongViecFragment();
		Bundle mBundle = new Bundle();
		mBundle.putString(Constant.TEXT_FRAGMENT, text);
		mFragment.setArguments(mBundle);
		return mFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.listview_danhsachhienthi,
				container, false);
		danhsachkehoachcongviec = (ListView) rootView
				.findViewById(R.id.lv_DanhSachHienThi_DSHT);

		new selectKeHoachCongViec().execute();

		danhsachkehoachcongviec
				.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						chisochon = arg2;
						Intent myIntent = new Intent(
								DanhSachKeHoachCongViecFragment.this.getActivity(),
								View_frm_QuanLyKeHoachCongViec.class);
						myIntent.putExtra("chucnang", "sua");
						myIntent.putExtra("tieude", "SỬA KẾ HOẠCH");
						myIntent.putExtra("objKeHoachCongViecSua",
								listKeHoach.get(chisochon));
						myIntent.putExtra("objNhomSua", listNhom.get(chisochon));
						myIntent.putExtra("objQuySua", listQuy.get(chisochon));
						startActivityForResult(myIntent, 9);
					}
				});

		danhsachkehoachcongviec
				.setOnItemLongClickListener(new OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						chisochon = arg2;
						DialogChucNang();
						return true;
					}

				});
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu, menu);

		searchView = (SearchView) MenuItemCompat.getActionView(menu
				.findItem(Menu_Phai.TIMKIEM));
		searchView.setQueryHint(this.getString(R.string.search));
		((EditText) searchView
				.findViewById(android.support.v7.appcompat.R.id.search_src_text))
				.setHintTextColor(getResources().getColor(R.color.trang));
		searchView.setOnQueryTextListener(HienThiOTimKiem);

		menu.findItem(Menu_Phai.THEMMOI).setVisible(true);
		menu.findItem(Menu_Phai.TIMKIEM).setVisible(true);

		chontimkiem = false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case Menu_Phai.THEMMOI:
			Intent myIntent = new Intent(
					DanhSachKeHoachCongViecFragment.this.getActivity(),
					View_frm_QuanLyKeHoachCongViec.class);
			myIntent.putExtra("chucnang", "them");
			myIntent.putExtra("tieude", "THÊM CÔNG VIỆC");
			startActivityForResult(myIntent, REQUEST_CODE);
			break;

		case Menu_Phai.TIMKIEM:
			chontimkiem = true;
			break;
		}
		return true;
	}

	private OnQueryTextListener HienThiOTimKiem = new OnQueryTextListener() {

		@Override
		public boolean onQueryTextSubmit(String arg0) {
			return false;
		}

		@Override
		public boolean onQueryTextChange(String arg0) {
			if (chontimkiem) {
				String text = arg0.toString().toLowerCase(Locale.getDefault());
				adapter.TimKiem(text);
			}
			return true;
		}
	};

	private class selectKeHoachCongViec extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DanhSachKeHoachCongViecFragment.this.getActivity());
			pDialog.setTitle("Kết nối đến máy chủ");
			pDialog.setMessage("Đang kết nối đến máy chủ ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... params) {
			ConnectivityManager cm = (ConnectivityManager) getActivity()
					.getSystemService(Context.CONNECTIVITY_SERVICE);
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
						ser_nguoidung serND = new ser_nguoidung(getActivity()
								.getApplicationContext());
						tblND = serND.selectNguoiDung();

						tblKH.setEmail(tblND.getEmail());
						JSONObject objJSON = ctrKH.selectKeHoachCongViec(tblKH);
						return objJSON;
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(JSONObject objJSON) {
			if (objJSON != null) {
				try {
					if (objJSON.getString(KEY_SUCCESS) != null) {

						String success = objJSON.getString(KEY_SUCCESS);
						String err = objJSON.getString(KEY_ERROR);
						String thongbao = objJSON.getString(KEY_THONGBAO);

						if (Integer.parseInt(success) == 1) {
							pDialog.setTitle("Tải dữ liệu");
							pDialog.setMessage("Đang tải..");
							Toast.makeText(
									getActivity().getApplicationContext(),
									thongbao, Toast.LENGTH_SHORT).show();

							listKeHoach = new ArrayList<tbl_kehoach>();
							listNhom = new ArrayList<tbl_nhom>();
							listQuy = new ArrayList<tbl_quy>();

							arrJSON = objJSON.getJSONArray("tbl_kehoach");
							for (int i = 0; i < arrJSON.length(); i++) {
								JSONObject c = arrJSON.getJSONObject(i);
								tbl_nhom tblN = new tbl_nhom();
								tbl_quy tblQ = new tbl_quy();
								tbl_kehoach tblKH = new tbl_kehoach();

								tblKH.setKeHoach_Id(c.getInt(KEY_KEHOACH_ID));
								tblKH.setNhom_Id(c.getInt(KEY_NHOM_ID));
								tblKH.setLoai_Id(c.getInt(KEY_LOAI_ID));
								tblKH.setSoTien(c.getDouble(KEY_SOTIEN));
								tblKH.setDienTa(c.getString(KEY_DIENTA));
								final SimpleDateFormat formatngay = new SimpleDateFormat(
										"yyyy-MM-dd");
								try {
									tblKH.setNgayBatDau(formatngay.parse(c
											.getString(KEY_NGAYBATDAU)));
								} catch (Exception e) {
								}
								tblKH.setVi_Id(c.getInt(KEY_VI_ID));
								tblKH.setEmail(c.getString(KEY_EMAIL));
								listKeHoach.add(tblKH);

								tblQ.setQuy_Id(c.getInt(KEY_VI_ID));
								tblQ.setTenQuy(c.getString(KEY_TENQUY));
								tblQ.setSoTien(c.getDouble("SoTienQuy"));
								tblQ.setAnh(c.getString("AnhQuy"));
								listQuy.add(tblQ);

								tblN.setNhom_Id(c.getInt(KEY_NHOM_ID));
								tblN.setTenNhom(c.getString(KEY_TENNHOM));
								tblN.setLoai_Id(c.getString(KEY_LOAI_ID));
								tblN.setAnh(c.getString(KEY_ANH));
								listNhom.add(tblN);

							}
							adapter = new Custom_Listview_DanhSachKeHoachCongViec(
									DanhSachKeHoachCongViecFragment.this
											.getActivity(),
									R.layout.custom_listview_giaodich_ngaythang,
									listKeHoach, listNhom);
							danhsachkehoachcongviec.setAdapter(adapter);

							pDialog.dismiss();
						} else {
							if (Integer.parseInt(err) == 1) {
								pDialog.dismiss();
								// new selectKeHoachCongViec().execute();
								Toast.makeText(
										getActivity().getApplicationContext(),
										thongbao, Toast.LENGTH_SHORT).show();
							}
						}
					} else {
						pDialog.dismiss();
						Toast.makeText(getActivity().getApplicationContext(),
								"Không có giá trị success trả về.",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// new selectKeHoachCongViec().execute();
					e.printStackTrace();
				}
			} else {
				pDialog.dismiss();
				Toast.makeText(getActivity().getApplicationContext(),
						"Không có kết nối internet", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == FragmentActivity.RESULT_OK
				&& requestCode == REQUEST_CODE) {
			if (data.getExtras().getString("chucnang").equals("them")) {
				intent_kehoach arrKeHoachCongViec_Them = (intent_kehoach) data
						.getSerializableExtra("arrKeHoachCongViec_Them");
				intent_nhom arrNhom_Them = (intent_nhom) data
						.getSerializableExtra("arrNhom_Them");
				intent_quy arrQuy_Them = (intent_quy) data
						.getSerializableExtra("arrQuy_Them");

				ArrayList<tbl_kehoach> arrKeHoachCongViec = arrKeHoachCongViec_Them
						.getArrKeHoach();
				ArrayList<tbl_nhom> arrNhom = arrNhom_Them.getArrNhom();
				ArrayList<tbl_quy> arrQuy = arrQuy_Them.getArrQuy();

				for (int i = 0; i < arrKeHoachCongViec.size(); i++) {
					listKeHoach.add(arrKeHoachCongViec.get(i));
					listNhom.add(arrNhom.get(i));
					listQuy.add(arrQuy.get(i));
				}

				adapter.notifyDataSetChanged();
			}
		}

		if (resultCode == FragmentActivity.RESULT_OK && requestCode == 9) {
			if (data.getExtras().getString("chucnang").equals("sua")) {
				tbl_kehoach tblKH = new tbl_kehoach();
				tbl_nhom tblN = new tbl_nhom();
				tbl_quy tblQ = new tbl_quy();

				tblKH = (tbl_kehoach) data
						.getSerializableExtra("objKeHoachCongViec");
				tblN = (tbl_nhom) data.getSerializableExtra("objNhom");
				tblQ = (tbl_quy) data.getSerializableExtra("objQuy");

				listKeHoach.remove(chisochon);
				listKeHoach.add(chisochon, tblKH);
				listNhom.remove(chisochon);
				listNhom.add(chisochon, tblN);
				listQuy.remove(chisochon);
				listQuy.add(chisochon, tblQ);
				adapter.notifyDataSetChanged();
			}
		}

		if (resultCode == FragmentActivity.RESULT_OK && requestCode == 8) {
			if (data.getExtras().getString("chucnang")
					.equals("thuchienkehoach")) {
				new deleteKeHoachCongViecNgam().execute();
			}
		}
	}

	public void DialogChucNang() {
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
		dialog.setContentView(R.layout.custom_dialog_listview);
		dialog.setTitle("Chức năng");

		ListView dsChucNang = (ListView) dialog
				.findViewById(R.id.lvDSChucNang_CD_Lv);
		ArrayList<item_chucnang> arrChucNang = new ArrayList<item_chucnang>();
		String[] anh = new String[] { "0", "1", "2", "3" };
		String[] chucnang = new String[] { "Thực hiện kế hoạch.",
				"Sửa kế hoạch.", "Xóa kế hoạch.", "Hoàn tác." };
		for (int i = 0; i < anh.length; i++) {
			item_chucnang itemCN = new item_chucnang();
			itemCN.setAnh(anh[i]);
			itemCN.setTenChucNang(chucnang[i]);
			arrChucNang.add(itemCN);
		}

		Custom_Dialog_ChucNang_KeHoachCongViec adapterCN = new Custom_Dialog_ChucNang_KeHoachCongViec(
				getActivity(), R.layout.custom_dialog_chucnang, arrChucNang);
		dsChucNang.setAdapter(adapterCN);

		dialog.show();
		dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.ic_chucnang);

		dsChucNang.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// THỰC HIỆN KẾ HOẠCH
				if (arg2 == 0) {
					Intent myIntent = new Intent(
							DanhSachKeHoachCongViecFragment.this.getActivity(),
							View_frm_QuanLyGiaoDich.class);
					myIntent.putExtra("chucnang", "thuchienkehoach");
					myIntent.putExtra("tieude", "THÊM GIAO DỊCH");
					myIntent.putExtra("objKeHoachCongViecSua",
							listKeHoach.get(chisochon));
					myIntent.putExtra("objNhomSua", listNhom.get(chisochon));
					myIntent.putExtra("objQuySua", listQuy.get(chisochon));
					startActivityForResult(myIntent, 8);
					dialog.dismiss();
				}
				// SỬA
				if (arg2 == 1) {
					Intent myIntent = new Intent(
							DanhSachKeHoachCongViecFragment.this.getActivity(),
							View_frm_QuanLyKeHoachCongViec.class);
					myIntent.putExtra("chucnang", "sua");
					myIntent.putExtra("tieude", "SỬA KẾ HOẠCH");
					myIntent.putExtra("objKeHoachCongViecSua",
							listKeHoach.get(chisochon));
					myIntent.putExtra("objNhomSua", listNhom.get(chisochon));
					myIntent.putExtra("objQuySua", listQuy.get(chisochon));
					startActivityForResult(myIntent, 9);
					dialog.dismiss();
				}
				// XÓA
				if (arg2 == 2) {
					dialog.dismiss();
					DialogXacNhanXoa();
				}
				// HOÀN TÁC
				if (arg2 == 3) {
					dialog.dismiss();
				}
			}
		});
	}

	public void DialogXacNhanXoa() {
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
		dialog.setContentView(R.layout.custom_dialog_xacnhanxoa);
		dialog.setTitle("Xác nhận xóa");

		ImageButton xoa = (ImageButton) dialog
				.findViewById(R.id.imgbtnXoa_CD_XNX);
		ImageButton hoantac = (ImageButton) dialog
				.findViewById(R.id.imgbtnHoanTac_CD_XNX);

		xoa.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				new deleteKeHoachCongViec().execute();
			}
		});

		hoantac.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});

		dialog.show();
		dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.ic_xacnhanxoa);
	}

	private class deleteKeHoachCongViec extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DanhSachKeHoachCongViecFragment.this.getActivity());
			pDialog.setTitle("Kết nối đến máy chủ");
			pDialog.setMessage("Đang kết nối đến máy chủ ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... params) {
			ConnectivityManager cm = (ConnectivityManager) getActivity()
					.getSystemService(Context.CONNECTIVITY_SERVICE);
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
						ser_nguoidung serND = new ser_nguoidung(getActivity()
								.getApplicationContext());
						tblND = serND.selectNguoiDung();

						tblKH.setKeHoach_Id(listKeHoach.get(chisochon)
								.getKeHoach_Id());
						tblKH.setEmail(tblND.getEmail());
						JSONObject objJSON = ctrKH.deleteKeHoach(tblKH);
						return objJSON;
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(JSONObject objJSON) {
			if (objJSON != null) {
				try {
					if (objJSON.getString(KEY_SUCCESS) != null) {

						String success = objJSON.getString(KEY_SUCCESS);
						String err = objJSON.getString(KEY_ERROR);
						String thongbao = objJSON.getString(KEY_THONGBAO);

						if (Integer.parseInt(success) == 1) {
							pDialog.setTitle("Tải dữ liệu");
							pDialog.setMessage("Đang tải..");
							Toast.makeText(
									getActivity().getApplicationContext(),
									thongbao, Toast.LENGTH_SHORT).show();

							pDialog.dismiss();
							listKeHoach.remove(chisochon);
							listNhom.remove(chisochon);
							listQuy.remove(chisochon);
							adapter.notifyDataSetChanged();
						} else {
							if (Integer.parseInt(err) == 1) {
								pDialog.dismiss();
								Toast.makeText(
										getActivity().getApplicationContext(),
										thongbao, Toast.LENGTH_SHORT).show();
							}
						}
					} else {
						pDialog.dismiss();
						Toast.makeText(getActivity().getApplicationContext(),
								"Không có giá trị success trả về.",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				pDialog.dismiss();
				Toast.makeText(getActivity().getApplicationContext(),
						"Không có kết nối internet", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private class deleteKeHoachCongViecNgam extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(String... params) {
			ConnectivityManager cm = (ConnectivityManager) getActivity()
					.getSystemService(Context.CONNECTIVITY_SERVICE);
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
						ser_nguoidung serND = new ser_nguoidung(getActivity()
								.getApplicationContext());
						tblND = serND.selectNguoiDung();

						tblKH.setKeHoach_Id(listKeHoach.get(chisochon)
								.getKeHoach_Id());
						tblKH.setEmail(tblND.getEmail());
						JSONObject objJSON = ctrKH.deleteKeHoach(tblKH);
						return objJSON;
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(JSONObject objJSON) {
			if (objJSON != null) {
				try {
					if (objJSON.getString(KEY_SUCCESS) != null) {

						String success = objJSON.getString(KEY_SUCCESS);
						String err = objJSON.getString(KEY_ERROR);
						String thongbao = objJSON.getString(KEY_THONGBAO);

						if (Integer.parseInt(success) == 1) {
							Toast.makeText(
									getActivity().getApplicationContext(),
									"Đã xóa kế hoạch công việc vừa thực thi.",
									Toast.LENGTH_SHORT).show();

							listKeHoach.remove(chisochon);
							listNhom.remove(chisochon);
							listQuy.remove(chisochon);
							adapter.notifyDataSetChanged();
						} else {
							if (Integer.parseInt(err) == 1) {
								pDialog.dismiss();
								Toast.makeText(
										getActivity().getApplicationContext(),
										thongbao, Toast.LENGTH_SHORT).show();
							}
						}
					} else {

						Toast.makeText(getActivity().getApplicationContext(),
								"Không có giá trị success trả về.",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Toast.makeText(getActivity().getApplicationContext(),
						"Không có kết nối internet", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
