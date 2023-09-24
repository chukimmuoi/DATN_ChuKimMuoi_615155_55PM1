package dhxd.chukimmuoi.fragments;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dhxd.chukimmuoi.adapter.Custom_Dialog_XacNhanBanBe;
import dhxd.chukimmuoi.adapter.Custom_Listview_DanhSachThongBaoBanBe;
import dhxd.chukimmuoi.adapter.Custom_Listview_DanhSachThongBaoGiaoDich;
import dhxd.chukimmuoi.controller.ctr_doitac;
import dhxd.chukimmuoi.controller.ctr_kehoach;
import dhxd.chukimmuoi.model.item_chucnang;
import dhxd.chukimmuoi.model.tbl_doitac;
import dhxd.chukimmuoi.model.tbl_giaodich;
import dhxd.chukimmuoi.model.tbl_kehoach;
import dhxd.chukimmuoi.model.tbl_nguoidung;
import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.qltc.R;
import dhxd.chukimmuoi.qltc.View_frm_QuanLyDoiTac;
import dhxd.chukimmuoi.qltc.View_frm_QuanLyGiaoDich;
import dhxd.chukimmuoi.service.ser_nguoidung;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class DanhSachThongBao_page_Fragment extends Fragment {
	ListView danhsachthongbao;
	public static int sotrang;

	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_THONGBAO = "error_msg";

	private String KEY_KEHOACH_ID = "KeHoach_Id";
	private String KEY_NHOM_ID = "Nhom_Id";
	private String KEY_LOAI_ID = "Loai_Id";
	private String KEY_EMAILDOITAC = "EmailDoiTac";
	private String KEY_EMAIL = "Email";
	private String KEY_DIENTA = "DienTa";
	private String KEY_SOTIEN = "SoTien";
	private String KEY_NGAYBATDAU = "NgayBatDau";

	private static final String KEY_TENNHOM = "TenNhom";
	private static final String KEY_ANH = "Anh";

	private static final String KEY_QUANHE = "QuanHe";
	private static final String KEY_LOAIKEHOACH = "LoaiKeHoach";

	public static ArrayList<tbl_kehoach> listKeHoach;
	public static ArrayList<tbl_nhom> listNhom;
	public static ArrayList<tbl_giaodich> listGiaoDich;

	ArrayList<tbl_doitac> list_DoiTac;
	JSONArray arrJSON = null;

	public static Custom_Listview_DanhSachThongBaoGiaoDich adapter;
	public static Custom_Listview_DanhSachThongBaoBanBe adapterBanBe;

	private Dialog dialog;
	private int chisochon;
	private static final int REQUEST_CODE = 10;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle data = getArguments();
		sotrang = data.getInt("sotrang");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.listview_danhsachhienthi,
				container, false);
		danhsachthongbao = (ListView) rootView
				.findViewById(R.id.lv_DanhSachHienThi_DSHT);

		if (sotrang == 0) {
			new selectThongBaoGiaoDich().execute();
		} else {
			if (sotrang == 1) {
				new selectDoiTacThongBao().execute();
			}
		}

		danhsachthongbao.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				chisochon = arg2;
				DialogChucNang();
			}

		});
		return rootView;
	}

	private class selectThongBaoGiaoDich extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DanhSachThongBao_page_Fragment.this.getActivity());
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
						JSONObject objJSON = ctrKH
								.selectThongBaoGiaoDich(tblKH);
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
							listGiaoDich = new ArrayList<tbl_giaodich>();

							arrJSON = objJSON.getJSONArray("tbl_kehoach");
							for (int i = 0; i < arrJSON.length(); i++) {
								JSONObject c = arrJSON.getJSONObject(i);
								tbl_nhom tblN = new tbl_nhom();
								tbl_kehoach tblKH = new tbl_kehoach();
								tbl_giaodich tblGD = new tbl_giaodich();

								tblKH.setKeHoach_Id(c.getInt(KEY_KEHOACH_ID));
								tblKH.setLoai_Id(c.getInt(KEY_LOAI_ID));
								tblKH.setSoTien(c.getDouble(KEY_SOTIEN));
								tblKH.setDienTa(c.getString(KEY_DIENTA));
								tblKH.setEmail(c.getString(KEY_EMAIL));
								tblKH.setEmailDoiTac(c
										.getString(KEY_EMAILDOITAC));
								final SimpleDateFormat formatngay = new SimpleDateFormat(
										"yyyy-MM-dd");
								try {
									tblKH.setNgayBatDau(formatngay.parse(c
											.getString(KEY_NGAYBATDAU)));
								} catch (Exception e) {
								}
								tblKH.setVi_Id(c.getInt("GiaoDich_Id"));
								tblKH.setLoaiKeHoach(c.getInt(KEY_LOAIKEHOACH));
								tblKH.setNhom_Id(-1);
								if (!c.getString(KEY_NHOM_ID).toString()
										.equals("null")) {
									tblKH.setNhom_Id(c.getInt(KEY_NHOM_ID));

									tblN.setNhom_Id(c.getInt(KEY_NHOM_ID));
									tblN.setTenNhom(c.getString(KEY_TENNHOM));
									tblN.setLoai_Id(c.getString(KEY_LOAI_ID));
									tblN.setAnh(c.getString(KEY_ANH));

								}
								if (!c.getString("GiaoDich_Id").toString()
										.equals("null")
										|| !c.getString("GiaoDich_Id")
												.toString().equals("0")) {
									try {
										tblGD.setNgayTra(formatngay.parse(c
												.getString("NgayTra")));
									} catch (Exception e) {
									}
									tblGD.setTienLai(c.getDouble("TienLai"));
									tblGD.setLoaiThuLai(c.getInt("LoaiThuLai"));
								}
								listKeHoach.add(tblKH);
								listNhom.add(tblN);
								listGiaoDich.add(tblGD);
							}
							adapter = new Custom_Listview_DanhSachThongBaoGiaoDich(
									DanhSachThongBao_page_Fragment.this
											.getActivity(),
									R.layout.custom_listview_danhsachthongbaogiaodich,
									listKeHoach, listNhom);
							danhsachthongbao.setAdapter(adapter);

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

	private class selectDoiTacThongBao extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DanhSachThongBao_page_Fragment.this.getActivity());
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
						ser_nguoidung ser_ND = new ser_nguoidung(getActivity()
								.getApplicationContext());

						tblND = ser_ND.selectNguoiDung();
						tblDT.setEmail(tblND.getEmail());

						JSONObject objJSON = ctrDT.selectDoiTacThongBao(tblDT);
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
								tblDT.setDoiTac_Id(c.getInt("DoiTac_Id"));
								tblDT.setEmail(c.getString(KEY_EMAIL));
								tblDT.setEmailDoiTac(c
										.getString(KEY_EMAILDOITAC));
								tblDT.setQuanHe(c.getString(KEY_QUANHE));

								list_DoiTac.add(tblDT);
							}
							adapterBanBe = new Custom_Listview_DanhSachThongBaoBanBe(
									DanhSachThongBao_page_Fragment.this
											.getActivity(),
									R.layout.custom_listview_danhsachthongbaobanbe,
									list_DoiTac);
							danhsachthongbao.setAdapter(adapterBanBe);

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

	public void DialogChucNang() {
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
		dialog.setContentView(R.layout.custom_dialog_listview);
		dialog.setTitle("Xác nhận?");

		ListView dsChucNang = (ListView) dialog
				.findViewById(R.id.lvDSChucNang_CD_Lv);
		ArrayList<item_chucnang> arrChucNang = new ArrayList<item_chucnang>();
		String[] anh = new String[] { "0", "1", "2" };
		String[] chucnang = new String[] { "Xác nhận.", "Xóa thông báo.",
				"Hoàn tác." };
		for (int i = 0; i < anh.length; i++) {
			item_chucnang itemCN = new item_chucnang();
			itemCN.setAnh(anh[i]);
			itemCN.setTenChucNang(chucnang[i]);
			arrChucNang.add(itemCN);
		}

		Custom_Dialog_XacNhanBanBe adapterCN = new Custom_Dialog_XacNhanBanBe(
				getActivity(), R.layout.custom_dialog_chucnang, arrChucNang);
		dsChucNang.setAdapter(adapterCN);

		dialog.show();
		dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.ic_xacnhanxoa);

		dsChucNang.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// ĐỒNG Ý
				if (arg2 == 0) {
					if (DanhSachThongBaoFragment.tranghienthi == 0) {
						Intent myIntent = new Intent(
								DanhSachThongBao_page_Fragment.this
										.getActivity(),
								View_frm_QuanLyGiaoDich.class);
						myIntent.putExtra("chucnang", "xacnhangiaodich");
						myIntent.putExtra("tieude", "THÊM GIAO DỊCH");
						myIntent.putExtra("objGiaoDichThem",
								listGiaoDich.get(chisochon));
						myIntent.putExtra("objNhomThem",
								listNhom.get(chisochon));
						myIntent.putExtra("objKeHoachThem",
								listKeHoach.get(chisochon));
						startActivityForResult(myIntent, REQUEST_CODE);
					} else {
						if (DanhSachThongBaoFragment.tranghienthi == 1) {
							Intent myIntent = new Intent(
									DanhSachThongBao_page_Fragment.this
											.getActivity(),
									View_frm_QuanLyDoiTac.class);

							myIntent.putExtra("chucnang", "dongyketban");
							myIntent.putExtra("tieude", "THÊM ĐỐI TÁC");
							myIntent.putExtra("objDoiTac_DongY",
									list_DoiTac.get(chisochon));

							startActivityForResult(myIntent, REQUEST_CODE);
						}
					}

					dialog.dismiss();
				}
				// KHÔNG ĐỒNG Ý
				if (arg2 == 1) {
					dialog.dismiss();
					if (DanhSachThongBaoFragment.tranghienthi == 0) {
						if (listKeHoach.get(chisochon).getVi_Id() == 0) {
							new deleteKeHoachCongViec().execute();
						} else {
							if (listKeHoach.get(chisochon).getVi_Id() != 0) {
								if (listKeHoach.get(chisochon).getLoaiKeHoach() == 0) {
									new deleteKeHoachGiaoDich().execute();
								} else {
									if (listKeHoach.get(chisochon)
											.getLoaiKeHoach() != 0) {
										new deleteKeHoachCongViec().execute();
									}
								}

							}
						}
					} else {
						if (DanhSachThongBaoFragment.tranghienthi == 1) {
							new deleteDoiTacThat().execute();
						}
					}
				}
				// THOÁT DIALOG
				if (arg2 == 2) {
					dialog.dismiss();
				}
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == FragmentActivity.RESULT_OK
				&& requestCode == REQUEST_CODE) {
			if (data.getExtras().getString("chucnang").equals("dongyketban")) {
				list_DoiTac.remove(chisochon);
				adapterBanBe.notifyDataSetChanged();
			}
			if (data.getExtras().getString("chucnang")
					.equals("xacnhangiaodich")) {
				if (listKeHoach.get(chisochon).getVi_Id() == 0) {
					new deleteKeHoachCongViec().execute();
				} else {
					if (listKeHoach.get(chisochon).getVi_Id() != 0) {
						if (listKeHoach.get(chisochon).getLoaiKeHoach() == 0) {
							new deleteKeHoachGiaoDich().execute();
						} else {
							if (listKeHoach.get(chisochon).getLoaiKeHoach() != 0) {
								new deleteKeHoachCongViec().execute();
							}
						}

					}
				}
			}
		}
	}

	private class deleteDoiTacThat extends
			AsyncTask<String, String, JSONObject> {
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
						tblDT.setDoiTac_Id(list_DoiTac.get(chisochon)
								.getDoiTac_Id());

						JSONObject objJSON = ctrDT.deleteDoiTacThat(tblDT);
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
							adapterBanBe.notifyDataSetChanged();
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

	private class deleteKeHoachGiaoDich extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DanhSachThongBao_page_Fragment.this.getActivity());
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

						tblKH.setVi_Id(listKeHoach.get(chisochon).getVi_Id());

						tblKH.setEmail(tblND.getEmail());
						JSONObject objJSON = ctrKH.deleteKeHoachGiaoDich(tblKH);
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
							listGiaoDich.remove(chisochon);

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

	private class deleteKeHoachCongViec extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DanhSachThongBao_page_Fragment.this.getActivity());
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
							listGiaoDich.remove(chisochon);

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
}
