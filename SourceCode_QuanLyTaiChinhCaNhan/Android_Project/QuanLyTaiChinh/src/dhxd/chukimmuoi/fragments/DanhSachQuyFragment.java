package dhxd.chukimmuoi.fragments;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dhxd.chukimmuoi.adapter.Custom_Dialog_ChucNang_ChuyenTien;
import dhxd.chukimmuoi.adapter.Custom_Listview_DanhSachQuy;
import dhxd.chukimmuoi.controller.ctr_quy;
import dhxd.chukimmuoi.model.intent_quy;
import dhxd.chukimmuoi.model.item_chucnang;
import dhxd.chukimmuoi.model.tbl_nguoidung;
import dhxd.chukimmuoi.model.tbl_quy;
import dhxd.chukimmuoi.qltc.R;
import dhxd.chukimmuoi.qltc.View_frm_ChuyenTien;
import dhxd.chukimmuoi.qltc.View_frm_QuanLyQuy;
import dhxd.chukimmuoi.service.ser_nguoidung;
import dhxd.chukimmuoi.utils.Constant;
import dhxd.chukimmuoi.utils.Menu_Phai;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class DanhSachQuyFragment extends Fragment {
	ListView danhsachquy;
	TextView tongtien;
	SearchView searchView;

	boolean chontimkiem;
	Custom_Listview_DanhSachQuy adapter;
	ArrayList<tbl_quy> list_Quy;
	JSONArray arrJSON = null;
	private static final int REQUEST_CODE = 10;
	private Dialog dialog;
	private int chisochon;

	private String KEY_SUCCESS = "success";
	private String KEY_ERROR = "error";
	private String KEY_THONGBAO = "error_msg";

	private String KEY_QUY_ID = "Quy_Id";
	private String KEY_TENQUY = "TenQuy";
	private String KEY_ANH = "Anh";
	private String KEY_SOTIEN = "SoTien";
	private String KEY_EMAIL = "Email";
	

	public DanhSachQuyFragment newInstance(String text) {
		DanhSachQuyFragment mFragment = new DanhSachQuyFragment();
		Bundle mBundle = new Bundle();
		mBundle.putString(Constant.TEXT_FRAGMENT, text);
		mFragment.setArguments(mBundle);
		return mFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.view_frm_danhsachquy,
				container, false);
		tongtien = (TextView) rootView.findViewById(R.id.tvTongTien_DSQ);
		tongtien.setText("0 đ");
		danhsachquy = (ListView) rootView.findViewById(R.id.lvDanhSachQuy_DSQ);
		new selectQuy().execute();

		danhsachquy.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				chisochon = arg2;
				
				Intent myIntent = new Intent(DanhSachQuyFragment.this
						.getActivity(), View_frm_QuanLyQuy.class);

				myIntent.putExtra("chucnang", "sua");
				myIntent.putExtra("tieude", "SỬA QUỸ");
				myIntent.putExtra("objQuySua", list_Quy.get(chisochon));

				startActivityForResult(myIntent, REQUEST_CODE);		
			}
		});
		
		danhsachquy.setOnItemLongClickListener(new OnItemLongClickListener() {

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
					DanhSachQuyFragment.this.getActivity(),
					View_frm_QuanLyQuy.class);
			myIntent.putExtra("chucnang", "them");
			myIntent.putExtra("tieude", "THÊM QUỸ");
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

	private class selectQuy extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DanhSachQuyFragment.this.getActivity());
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
						ctr_quy ctrQ = new ctr_quy();
						tbl_quy tblQ = new tbl_quy();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(getActivity());

						tblND = serND.selectNguoiDung();

						tblQ.setEmail(tblND.getEmail());

						JSONObject objJSON = ctrQ.selectQuy(tblQ);
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
							list_Quy = new ArrayList<tbl_quy>();
							arrJSON = objJSON.getJSONArray("tbl_quy");
							for (int i = 0; i < arrJSON.length(); i++) {
								JSONObject c = arrJSON.getJSONObject(i);
								tbl_quy tblQ = new tbl_quy();
								tblQ.setQuy_Id(Integer.parseInt(c
										.getString(KEY_QUY_ID)));
								tblQ.setTenQuy(c.getString(KEY_TENQUY));
								tblQ.setAnh(c.getString(KEY_ANH));
								tblQ.setSoTien(Double.parseDouble(c
										.getString(KEY_SOTIEN)));
								tblQ.setEmail(c.getString(KEY_EMAIL));

								list_Quy.add(tblQ);
							}
							adapter = new Custom_Listview_DanhSachQuy(
									DanhSachQuyFragment.this.getActivity(),
									R.layout.custom_listview_danhsachquy,
									list_Quy);
							danhsachquy.setAdapter(adapter);
							TinhTongTien();
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		getActivity();
		if (resultCode == FragmentActivity.RESULT_OK
				&& requestCode == REQUEST_CODE) {
			if (data.getExtras().getString("chucnang").equals("them")) {
				intent_quy arrQuy_Them = (intent_quy) data
						.getSerializableExtra("arrQuy_Them");
				ArrayList<tbl_quy> arrQuy = arrQuy_Them.getArrQuy();
				list_Quy.addAll(arrQuy);
				adapter.notifyDataSetChanged();
			}

			if (data.getExtras().getString("chucnang").equals("sua")) {
				tbl_quy tblQ = new tbl_quy();
				tblQ = (tbl_quy) data.getSerializableExtra("objQuy");
				list_Quy.remove(chisochon);
				list_Quy.add(chisochon, tblQ);
				adapter.notifyDataSetChanged();
			}
			
			if (data.getExtras().getString("chucnang").equals("chuyentien")) {
				new selectQuy().execute();
			}
			if (data.getExtras().getString("chucnang").equals("quaylai")) {
				
			}
			TinhTongTien();
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
		String[] chucnang = new String[] { "Chuyển tiền.", "Sửa quỹ tiêu dùng.",
				"Xóa qũy tiêu dùng.", "Hoàn tác." };
		for (int i = 0; i < anh.length; i++) {
			item_chucnang itemCN = new item_chucnang();
			itemCN.setAnh(anh[i]);
			itemCN.setTenChucNang(chucnang[i]);
			arrChucNang.add(itemCN);
		}

		Custom_Dialog_ChucNang_ChuyenTien adapterCN = new Custom_Dialog_ChucNang_ChuyenTien(
				getActivity(), R.layout.custom_dialog_chucnang, arrChucNang);
		dsChucNang.setAdapter(adapterCN);

		dialog.show();
		dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.ic_chucnang);

		// Bắt sự kiện chọn chức năng.
		dsChucNang.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(arg2 == 0){
					Intent myIntent = new Intent(DanhSachQuyFragment.this
							.getActivity(), View_frm_ChuyenTien.class);
					myIntent.putExtra("chucnang", "chuyentien");
					myIntent.putExtra("objQuyChuyen", list_Quy.get(chisochon));
					startActivityForResult(myIntent, REQUEST_CODE);
					dialog.dismiss();
				}
				// SỬA
				if (arg2 == 1) {
					Intent myIntent = new Intent(DanhSachQuyFragment.this
							.getActivity(), View_frm_QuanLyQuy.class);

					myIntent.putExtra("chucnang", "sua");
					myIntent.putExtra("tieude", "SỬA QUỸ");
					myIntent.putExtra("objQuySua", list_Quy.get(chisochon));

					startActivityForResult(myIntent, REQUEST_CODE);
					dialog.dismiss();
				}
				// XÓA
				if (arg2 == 2) {
					dialog.dismiss();
					DialogXacNhanXoa();
				}
				// THOÁT DIALOG
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
				new deleteQuy().execute();
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

	private class deleteQuy extends AsyncTask<String, String, JSONObject> {
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
						ctr_quy ctrQ = new ctr_quy();
						tbl_quy tblQ = new tbl_quy();

						tblQ.setQuy_Id(list_Quy.get(chisochon).getQuy_Id());

						JSONObject objJSON = ctrQ.deleteQuy(tblQ);
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

							list_Quy.remove(chisochon);
							adapter.notifyDataSetChanged();
							TinhTongTien();
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

	private void TinhTongTien() {
		DecimalFormat tien = new DecimalFormat("###,###,### đ");
		double tongtien = 0;
		for (int i = 0; i < list_Quy.size(); i++) {
			tongtien = tongtien + list_Quy.get(i).getSoTien();
		}
		if (tongtien > 0) {
			this.tongtien.setTextColor(Color.parseColor("#006600"));
		} else {
			this.tongtien.setTextColor(Color.parseColor("#CC0000"));
		}
		this.tongtien.setText(tien.format(tongtien));
	}
}
