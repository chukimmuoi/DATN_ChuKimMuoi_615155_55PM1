package dhxd.chukimmuoi.fragments;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dhxd.chukimmuoi.adapter.Custom_Dialog_ChucNang;
import dhxd.chukimmuoi.adapter.Custom_Listview_DanhSachDoiTac;
import dhxd.chukimmuoi.controller.ctr_doitac;
import dhxd.chukimmuoi.model.item_chucnang;
import dhxd.chukimmuoi.model.tbl_doitac;
import dhxd.chukimmuoi.model.tbl_nguoidung;
import dhxd.chukimmuoi.qltc.R;
import dhxd.chukimmuoi.qltc.View_frm_QuanLyDoiTac;
import dhxd.chukimmuoi.service.ser_nguoidung;
import dhxd.chukimmuoi.utils.Constant;
import dhxd.chukimmuoi.utils.Menu_Phai;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DanhSachDoiTacFragment extends Fragment {
	ListView danhsachdoitac;
	SearchView searchView;

	boolean chontimkiem;

	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_THONGBAO = "error_msg";

	private String KEY_DOITAC_ID = "DoiTac_Id";
	private String KEY_EMAIL = "Email";
	private String KEY_EMAILDOITAC = "EmailDoiTac";
	private String KEY_QUANHE = "QuanHe";
	private String KEY_XACNHAN = "XacNhan";

	Custom_Listview_DanhSachDoiTac adapter;
	ArrayList<tbl_doitac> list_DoiTac;

	private static final int REQUEST_CODE = 10;
	private Dialog dialog;
	private int chisochon;

	public DanhSachDoiTacFragment newInstance(String text) {
		DanhSachDoiTacFragment mFragment = new DanhSachDoiTacFragment();
		Bundle mBundle = new Bundle();
		mBundle.putString(Constant.TEXT_FRAGMENT, text);
		mFragment.setArguments(mBundle);
		return mFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.view_frm_danhsachdoitac,
				container, false);
		danhsachdoitac = (ListView) rootView
				.findViewById(R.id.lvDanhSachDoiDT_DSDT);
		new selectDoiTac().execute();

		danhsachdoitac.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				chisochon = arg2;
				Intent myIntent = new Intent(DanhSachDoiTacFragment.this
						.getActivity(), View_frm_QuanLyDoiTac.class);

				myIntent.putExtra("chucnang", "sua");
				myIntent.putExtra("tieude", "SỬA QUAN HỆ");
				myIntent.putExtra("objDoiTac_Sua",
						list_DoiTac.get(chisochon));

				startActivityForResult(myIntent, REQUEST_CODE);
			}
		});
		
		danhsachdoitac.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				chisochon = arg2;
				DialogChucNang();
				return true;
			}
		
		});
		return rootView;
	}

	private class selectDoiTac extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DanhSachDoiTacFragment.this.getActivity());
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
						tbl_doitac tblDT = new tbl_doitac();
						ctr_doitac ctrDT = new ctr_doitac();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung ser_ND = new ser_nguoidung(
								DanhSachDoiTacFragment.this.getActivity());

						tblND = ser_ND.selectNguoiDung();
						tblDT.setEmail(tblND.getEmail());

						JSONObject objJSON = ctrDT.selectDoiTac(tblDT);
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

							list_DoiTac = new ArrayList<tbl_doitac>();

							JSONArray arrJSON = objJSON
									.getJSONArray("tbl_doitac");
							for (int i = 0; i < arrJSON.length(); i++) {
								JSONObject c = arrJSON.getJSONObject(i);
								tbl_doitac tblDT = new tbl_doitac();
								tblDT.setDoiTac_Id(Integer.parseInt(c
										.getString(KEY_DOITAC_ID)));
								tblDT.setEmail(c.getString(KEY_EMAIL));
								tblDT.setEmailDoiTac(c
										.getString(KEY_EMAILDOITAC));
								tblDT.setQuanHe(c.getString(KEY_QUANHE));
								tblDT.setXacNhan(Integer.parseInt(c
										.getString(KEY_XACNHAN)));

								list_DoiTac.add(tblDT);
							}

							adapter = new Custom_Listview_DanhSachDoiTac(
									DanhSachDoiTacFragment.this.getActivity(),
									R.layout.custom_listview_danhsachdoitac,
									list_DoiTac);

							danhsachdoitac.setAdapter(adapter);
							pDialog.dismiss();
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
					DanhSachDoiTacFragment.this.getActivity(),
					View_frm_QuanLyDoiTac.class);
			myIntent.putExtra("chucnang", "them");
			myIntent.putExtra("tieude", "GỬI LỜI MỜI");
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == FragmentActivity.RESULT_OK
				&& requestCode == REQUEST_CODE) {
			if (data.getExtras().getString("chucnang").equals("them")) {
			}
			if (data.getExtras().getString("chucnang").equals("sua")) {
				tbl_doitac tblDT = new tbl_doitac();
				tblDT = (tbl_doitac) data.getSerializableExtra("objDoiTac");
				list_DoiTac.remove(chisochon);
				list_DoiTac.add(chisochon, tblDT);
				adapter.notifyDataSetChanged();
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
		String[] anh = new String[] { "0", "1", "2" };
		String[] chucnang = new String[] { "Sửa quan hệ.", "Hủy kết bạn.",
				"Hoàn tác." };
		for (int i = 0; i < anh.length; i++) {
			item_chucnang itemCN = new item_chucnang();
			itemCN.setAnh(anh[i]);
			itemCN.setTenChucNang(chucnang[i]);
			arrChucNang.add(itemCN);
		}

		Custom_Dialog_ChucNang adapterCN = new Custom_Dialog_ChucNang(
				getActivity(), R.layout.custom_dialog_chucnang, arrChucNang);
		dsChucNang.setAdapter(adapterCN);

		dialog.show();
		dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.ic_chucnang);

		dsChucNang.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 0) {
					Intent myIntent = new Intent(DanhSachDoiTacFragment.this
							.getActivity(), View_frm_QuanLyDoiTac.class);

					myIntent.putExtra("chucnang", "sua");
					myIntent.putExtra("tieude", "SỬA QUAN HỆ");
					myIntent.putExtra("objDoiTac_Sua",
							list_DoiTac.get(chisochon));

					startActivityForResult(myIntent, REQUEST_CODE);
					dialog.dismiss();
				}
				if (arg2 == 1) {
					dialog.dismiss();
					DialogXacNhanXoa();
				}
				if (arg2 == 2) {
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

				new deleteDoiTac().execute();
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

	private class deleteDoiTac extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
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
						ctr_doitac ctrDT = new ctr_doitac();
						tbl_doitac tblDT = new tbl_doitac();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(getActivity()
								.getApplicationContext());

						tblND = serND.selectNguoiDung();
						tblDT.setDoiTac_Id(list_DoiTac.get(chisochon)
								.getDoiTac_Id());
						tblDT.setEmail(tblND.getEmail());
						tblDT.setEmailDoiTac(list_DoiTac.get(chisochon).getEmailDoiTac());

						JSONObject objJSON = ctrDT.deleteDoiTac(tblDT);
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

							Toast.makeText(getActivity(), thongbao,
									Toast.LENGTH_SHORT).show();

							pDialog.dismiss();

							list_DoiTac.remove(chisochon);
							adapter.notifyDataSetChanged();
						} else {
							if (Integer.parseInt(err) == 1) {
								pDialog.dismiss();
								Toast.makeText(getActivity(), thongbao,
										Toast.LENGTH_SHORT).show();
							}
						}
					} else {
						pDialog.dismiss();
						Toast.makeText(getActivity(),
								"Không có giá trị success trả về.",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				pDialog.dismiss();
				Toast.makeText(getActivity(), "Không có kết nối internet",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
