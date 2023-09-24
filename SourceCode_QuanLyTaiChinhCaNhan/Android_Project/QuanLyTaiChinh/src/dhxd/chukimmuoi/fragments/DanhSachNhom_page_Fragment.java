package dhxd.chukimmuoi.fragments;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dhxd.chukimmuoi.adapter.Custom_Dialog_ChucNang;
import dhxd.chukimmuoi.adapter.Custom_Listview_DanhSachNhom;
import dhxd.chukimmuoi.controller.ctr_nhom;
import dhxd.chukimmuoi.model.item_chucnang;
import dhxd.chukimmuoi.model.tbl_nguoidung;
import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.qltc.R;
import dhxd.chukimmuoi.qltc.View_frm_DanhSachNhom_Page_Chon;
import dhxd.chukimmuoi.qltc.View_frm_QuanLyNhom;
import dhxd.chukimmuoi.service.ser_nguoidung;
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
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class DanhSachNhom_page_Fragment extends Fragment {
	ListView danhsachnhom;
	SearchView searchView;

	public static int sotrang;
	public static String chucnang;
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_THONGBAO = "error_msg";

	private static final String KEY_NHOM_ID = "Nhom_Id";
	private static final String KEY_LOAI_ID = "Loai_Id";
	private static final String KEY_TENNHOM = "TenNhom";
	private static final String KEY_ANH = "Anh";
	private static final String KEY_EMAIL = "Email";

	public static Custom_Listview_DanhSachNhom adapterThu, adapterChi;

	public static ArrayList<tbl_nhom> listNhomThu, listNhomChi;
	JSONArray arrJSON = null;

	private static final int REQUEST_CODE = 10;

	private Dialog dialog;
	private int chisochon;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle data = getArguments();
		sotrang = data.getInt("sotrang");
		chucnang = data.getString("chucnang");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.listview_danhsachhienthi,
				container, false);
		danhsachnhom = (ListView) rootView
				.findViewById(R.id.lv_DanhSachHienThi_DSHT);
		if (sotrang == 0) {
			new selectNhomThu().execute();
			/** THỜI GIAN CHỜ ĐỂ THỰC HIỆN LỆNH SELECT NHÓM THU */
//			try {
//				Thread.sleep(30);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		} else {
			if (sotrang == 1) {
				new selectNhomChi().execute();
			}
		}

		danhsachnhom.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				chisochon = arg2;
				if (chucnang == null) {
					if (DanhSachNhomFragment.tranghienthi == 0) {
						if (listNhomThu.get(chisochon).getTenNhom().toString()
								.equals("Nợ")
								|| listNhomThu.get(chisochon).getTenNhom()
										.toString().equals("Khác")
								|| listNhomThu.get(chisochon).getTenNhom()
										.toString().equals("Được tặng")) {
							Toast.makeText(
									getActivity().getApplicationContext(),
									"Đây là nhóm mặc định của ứng dụng, bạn không thể sửa đổi.",
									Toast.LENGTH_SHORT).show();
						} else {
							Intent myIntent = new Intent(
									DanhSachNhom_page_Fragment.this.getActivity(),
									View_frm_QuanLyNhom.class);

							myIntent.putExtra("chucnang", "sua");
							myIntent.putExtra("tieude", "SỬA NHÓM");
							if (DanhSachNhomFragment.tranghienthi == 0) {
								myIntent.putExtra("objNhomSua",
										listNhomThu.get(chisochon));
							}
							if (DanhSachNhomFragment.tranghienthi == 1) {
								myIntent.putExtra("objNhomSua",
										listNhomChi.get(chisochon));
							}
							startActivityForResult(myIntent, REQUEST_CODE);
						}
					}

					if (DanhSachNhomFragment.tranghienthi == 1) {
						if (listNhomChi.get(chisochon).getTenNhom().toString()
								.equals("Cho vay")
								|| listNhomChi.get(chisochon).getTenNhom()
										.toString().equals("Khác")
								|| listNhomChi.get(chisochon).getTenNhom()
										.toString().equals("Chuyển khoản")) {
							Toast.makeText(
									getActivity().getApplicationContext(),
									"Đây là nhóm mặc định của ứng dụng, bạn không thể sửa đổi.",
									Toast.LENGTH_SHORT).show();
						} else {
							Intent myIntent = new Intent(
									DanhSachNhom_page_Fragment.this.getActivity(),
									View_frm_QuanLyNhom.class);

							myIntent.putExtra("chucnang", "sua");
							myIntent.putExtra("tieude", "SỬA NHÓM");
							if (DanhSachNhomFragment.tranghienthi == 0) {
								myIntent.putExtra("objNhomSua",
										listNhomThu.get(chisochon));
							}
							if (DanhSachNhomFragment.tranghienthi == 1) {
								myIntent.putExtra("objNhomSua",
										listNhomChi.get(chisochon));
							}
							startActivityForResult(myIntent, REQUEST_CODE);
						}
					}
				}

				if (chucnang == "chonnhom") {
					if (View_frm_DanhSachNhom_Page_Chon.tranghienthi == 0) {

						Intent myIntent = new Intent();
						myIntent.putExtra("chucnang", "chonnhom");
						myIntent.putExtra("objNhomChon",
								listNhomThu.get(chisochon));

						getActivity().setResult(-1, myIntent);
						getActivity().finish();
					}
					if (View_frm_DanhSachNhom_Page_Chon.tranghienthi == 1) {

						Intent myIntent = new Intent();
						myIntent.putExtra("chucnang", "chonnhom");
						myIntent.putExtra("objNhomChon",
								listNhomChi.get(chisochon));

						getActivity().setResult(-1, myIntent);
						getActivity().finish();
					}
				}
			}
		});

		danhsachnhom.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				chisochon = arg2;
				if (chucnang == null) {
					if (DanhSachNhomFragment.tranghienthi == 0) {
						if (listNhomThu.get(chisochon).getTenNhom().toString()
								.equals("Nợ")
								|| listNhomThu.get(chisochon).getTenNhom()
										.toString().equals("Khác")
								|| listNhomThu.get(chisochon).getTenNhom()
										.toString().equals("Được tặng")) {
							Toast.makeText(
									getActivity().getApplicationContext(),
									"Đây là nhóm mặc định của ứng dụng, bạn không thể sửa đổi.",
									Toast.LENGTH_SHORT).show();
						} else {
							DialogChucNang();
						}
					}

					if (DanhSachNhomFragment.tranghienthi == 1) {
						if (listNhomChi.get(chisochon).getTenNhom().toString()
								.equals("Cho vay")
								|| listNhomChi.get(chisochon).getTenNhom()
										.toString().equals("Khác")
								|| listNhomChi.get(chisochon).getTenNhom()
										.toString().equals("Chuyển khoản")) {
							Toast.makeText(
									getActivity().getApplicationContext(),
									"Đây là nhóm mặc định của ứng dụng, bạn không thể sửa đổi.",
									Toast.LENGTH_SHORT).show();
						} else {
							DialogChucNang();
						}
					}
				}

				if (chucnang == "chonnhom") {
					if (View_frm_DanhSachNhom_Page_Chon.tranghienthi == 0) {

						Intent myIntent = new Intent();
						myIntent.putExtra("chucnang", "chonnhom");
						myIntent.putExtra("objNhomChon",
								listNhomThu.get(chisochon));

						getActivity().setResult(-1, myIntent);
						getActivity().finish();
					}
					if (View_frm_DanhSachNhom_Page_Chon.tranghienthi == 1) {

						Intent myIntent = new Intent();
						myIntent.putExtra("chucnang", "chonnhom");
						myIntent.putExtra("objNhomChon",
								listNhomChi.get(chisochon));

						getActivity().setResult(-1, myIntent);
						getActivity().finish();
					}
				}
				return true;
			}
		});
		return rootView;
	}

	private class selectNhomThu extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DanhSachNhom_page_Fragment.this.getActivity());
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

						ctr_nhom ctrN = new ctr_nhom();
						tbl_nhom tblN = new tbl_nhom();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(getActivity()
								.getApplicationContext());

						tblND = serND.selectNguoiDung();

						tblN.setLoai_Id("0");
						tblN.setEmail(tblND.getEmail());

						JSONObject objJSON = ctrN.selectNhomLoai(tblN);
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

							listNhomThu = new ArrayList<tbl_nhom>();

							arrJSON = objJSON.getJSONArray("tbl_nhom");
							for (int i = 0; i < arrJSON.length(); i++) {
								JSONObject c = arrJSON.getJSONObject(i);
								tbl_nhom tblN = new tbl_nhom();

								tblN.setNhom_Id(Integer.parseInt(c
										.getString(KEY_NHOM_ID)));
								tblN.setTenNhom(c.getString(KEY_TENNHOM));
								tblN.setLoai_Id(c.getString(KEY_LOAI_ID));
								tblN.setAnh(c.getString(KEY_ANH));
								tblN.setEmail(c.getString(KEY_EMAIL));

								listNhomThu.add(tblN);
							}

							adapterThu = new Custom_Listview_DanhSachNhom(
									DanhSachNhom_page_Fragment.this
											.getActivity(),
									R.layout.custom_listview_danhsachnhom,
									listNhomThu);
							danhsachnhom.setAdapter(adapterThu);
							pDialog.dismiss();
						} else {
							if (Integer.parseInt(err) == 1) {
								pDialog.dismiss();
								// new selectNhomThu().execute();
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
					// new selectNhomThu().execute();
					e.printStackTrace();
				}
			} else {
				pDialog.dismiss();
				Toast.makeText(getActivity().getApplicationContext(),
						"Không có kết nối internet", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private class selectNhomChi extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DanhSachNhom_page_Fragment.this.getActivity());
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

						ctr_nhom ctrN = new ctr_nhom();
						tbl_nhom tblN = new tbl_nhom();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(getActivity()
								.getApplicationContext());

						tblND = serND.selectNguoiDung();

						tblN.setLoai_Id("1");
						tblN.setEmail(tblND.getEmail());

						JSONObject objJSON = ctrN.selectNhomLoai(tblN);
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

							listNhomChi = new ArrayList<tbl_nhom>();

							arrJSON = objJSON.getJSONArray("tbl_nhom");
							for (int i = 0; i < arrJSON.length(); i++) {
								JSONObject c = arrJSON.getJSONObject(i);
								tbl_nhom tblN = new tbl_nhom();

								tblN.setNhom_Id(Integer.parseInt(c
										.getString(KEY_NHOM_ID)));
								tblN.setTenNhom(c.getString(KEY_TENNHOM));
								tblN.setLoai_Id(c.getString(KEY_LOAI_ID));
								tblN.setAnh(c.getString(KEY_ANH));
								tblN.setEmail(c.getString(KEY_EMAIL));

								listNhomChi.add(tblN);
							}

							adapterChi = new Custom_Listview_DanhSachNhom(
									DanhSachNhom_page_Fragment.this
											.getActivity(),
									R.layout.custom_listview_danhsachnhom,
									listNhomChi);
							danhsachnhom.setAdapter(adapterChi);
							pDialog.dismiss();
						} else {
							if (Integer.parseInt(err) == 1) {
								pDialog.dismiss();
								// new selectNhomChi().execute();
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
					// new selectNhomChi().execute();
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

			if (data.getExtras().getString("chucnang").equals("sua")) {
				tbl_nhom tblN = new tbl_nhom();
				tblN = (tbl_nhom) data.getSerializableExtra("objNhom");
				if (DanhSachNhomFragment.tranghienthi == 0) {
					listNhomThu.remove(chisochon);
				}
				if (DanhSachNhomFragment.tranghienthi == 1) {
					listNhomChi.remove(chisochon);
				}
				if (tblN.getLoai_Id().toString().equals("0")) {
					listNhomThu.add(chisochon, tblN);
					adapterThu.notifyDataSetChanged();
					adapterChi.notifyDataSetChanged();
				}
				if (tblN.getLoai_Id().toString().equals("1")) {
					listNhomChi.add(chisochon, tblN);
					adapterChi.notifyDataSetChanged();
					adapterThu.notifyDataSetChanged();
				}
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
		String[] chucnang = new String[] { "Sửa nhóm thu - chi này.",
				"Xóa nhóm thu - chi này.", "Hoàn tác." };
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
				// SỬA
				if (arg2 == 0) {
					Intent myIntent = new Intent(
							DanhSachNhom_page_Fragment.this.getActivity(),
							View_frm_QuanLyNhom.class);

					myIntent.putExtra("chucnang", "sua");
					myIntent.putExtra("tieude", "SỬA NHÓM");
					if (DanhSachNhomFragment.tranghienthi == 0) {
						myIntent.putExtra("objNhomSua",
								listNhomThu.get(chisochon));
					}
					if (DanhSachNhomFragment.tranghienthi == 1) {
						myIntent.putExtra("objNhomSua",
								listNhomChi.get(chisochon));
					}
					startActivityForResult(myIntent, REQUEST_CODE);
					dialog.dismiss();
				}
				// XÓA
				if (arg2 == 1) {
					dialog.dismiss();
					DialogXacNhanXoa();
				}
				// THOÁT DIALOG
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
				new deleteNhom().execute();
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

	private class deleteNhom extends AsyncTask<String, String, JSONObject> {
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
						ctr_nhom ctrN = new ctr_nhom();
						tbl_nhom tblN = new tbl_nhom();

						if (DanhSachNhomFragment.tranghienthi == 0) {
							tblN.setNhom_Id(listNhomThu.get(chisochon)
									.getNhom_Id());
						}
						if (DanhSachNhomFragment.tranghienthi == 1) {
							tblN.setNhom_Id(listNhomChi.get(chisochon)
									.getNhom_Id());
						}

						JSONObject objJSON = ctrN.deleteNhom(tblN);
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

							if (DanhSachNhomFragment.tranghienthi == 0) {
								listNhomThu.remove(chisochon);
								adapterThu.notifyDataSetChanged();
							}

							if (DanhSachNhomFragment.tranghienthi == 1) {
								listNhomChi.remove(chisochon);
								adapterChi.notifyDataSetChanged();
							}

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