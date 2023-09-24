package dhxd.chukimmuoi.fragments;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dhxd.chukimmuoi.adapter.Custom_Dialog_ChucNang_GiaoDich;
import dhxd.chukimmuoi.adapter.Custom_Listview_DanhSachGiaoDich;
import dhxd.chukimmuoi.controller.ctr_giaodich;
import dhxd.chukimmuoi.model.intent_giaodich;
import dhxd.chukimmuoi.model.intent_nhom;
import dhxd.chukimmuoi.model.intent_quy;
import dhxd.chukimmuoi.model.item_chucnang;
import dhxd.chukimmuoi.model.tbl_giaodich;
import dhxd.chukimmuoi.model.tbl_nguoidung;
import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.model.tbl_quy;
import dhxd.chukimmuoi.qltc.R;
import dhxd.chukimmuoi.qltc.View_frm_BaoCao_BaoCaoThang;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("SimpleDateFormat")
public class DanhSachGiaoDich_page_Fragment extends Fragment {
	public static int sotrang;
	ListView danhsachgiaodich;

	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_THONGBAO = "error_msg";

	private String KEY_GIAODICH_ID = "GiaoDich_Id";
	private String KEY_NHOM_ID = "Nhom_Id";
	private String KEY_SOTIEN = "SoTien";
	private String KEY_GHICHU = "GhiChu";
	private String KEY_EMAILDOITAC = "EmailDoiTac";
	private String KEY_NGAYTHANG = "NgayThang";
	private String KEY_QUY_ID = "Quy_Id";
	private String KEY_EMAIL = "Email";
	private String KEY_NGAYTRA = "NgayTra";
	private String KEY_TIENLAI = "TienLai";
	private String KEY_LOAITHULAI = "LoaiThuLai";

	private String KEY_TENNHOM = "TenNhom";
	private String KEY_TENQUY = "TenQuy";
	private String KEY_LOAI_ID = "Loai_Id";
	private String KEY_ANH = "Anh";

	public static Custom_Listview_DanhSachGiaoDich adapterdu1, adapterdu2,
			adapterdu3;
	public static ArrayList<tbl_giaodich> listGiaoDich, listGiaoDichDu1,
			listGiaoDichDu2, listGiaoDichDu3;
	public static ArrayList<tbl_nhom> listNhom, listNhomDu1, listNhomDu2,
			listNhomDu3;
	public static ArrayList<tbl_quy> listQuy, listQuyDu1, listQuyDu2,
			listQuyDu3;
	JSONArray arrJSON = null;

	private Calendar lich;
	private int year;
	private int month;

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
		danhsachgiaodich = (ListView) rootView
				.findViewById(R.id.lv_DanhSachHienThi_DSHT);

		if (sotrang == 49) {
			new selectGiaoDich49().execute();
		} else {
			if (sotrang == 50) {
				new selectGiaoDich50().execute();
			} else {
				new selectGiaoDich().execute();
			}
		}

		danhsachgiaodich.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				chisochon = arg2;
				if (arg2 == 0) {
					Intent myIntent = new Intent(
							DanhSachGiaoDich_page_Fragment.this.getActivity(),
							View_frm_BaoCao_BaoCaoThang.class);

					lich = Calendar.getInstance();
					lich.add(Calendar.MONTH,
							(DanhSachGiaoDichFragment.tranghienthi - 50));
					month = lich.get(Calendar.MONTH) + 1;
					year = lich.get(Calendar.YEAR);

					myIntent.putExtra("chucnang", "xembaocao");
					myIntent.putExtra("tieude", "Báo cáo\ntháng " + month
							+ " năm " + year + "");
					if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 1) {
						myIntent.putExtra("GiaoDich", new intent_giaodich(
								listGiaoDichDu1));
						myIntent.putExtra("Nhom", new intent_nhom(listNhomDu1));
						myIntent.putExtra("Quy", new intent_quy(listQuyDu1));
					}
					if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 2) {
						myIntent.putExtra("GiaoDich", new intent_giaodich(
								listGiaoDichDu2));
						myIntent.putExtra("Nhom", new intent_nhom(listNhomDu2));
						myIntent.putExtra("Quy", new intent_quy(listQuyDu2));
					}
					if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 0) {
						myIntent.putExtra("GiaoDich", new intent_giaodich(
								listGiaoDichDu3));
						myIntent.putExtra("Nhom", new intent_nhom(listNhomDu3));
						myIntent.putExtra("Quy", new intent_quy(listQuyDu3));
					}
					startActivityForResult(myIntent, REQUEST_CODE);

				} else {
					if (DanhSachGiaoDichFragment.tranghienthi % 3 == 1) {
						Toast.makeText(
								getActivity().getApplicationContext(),
								"" + listGiaoDichDu1.get(arg2).getGhiChu() + "",
								Toast.LENGTH_SHORT).show();
						/**===================================*/
						Intent myIntent = new Intent(
								DanhSachGiaoDich_page_Fragment.this.getActivity(),
								View_frm_QuanLyGiaoDich.class);
						myIntent.putExtra("chucnang", "xemchitiet");
						myIntent.putExtra("tieude", "XEM CHI TIẾT      ");
						if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 1) {
							myIntent.putExtra("objGiaoDichSua",
									listGiaoDichDu1.get(chisochon));
							myIntent.putExtra("objNhomSua",
									listNhomDu1.get(chisochon));
							myIntent.putExtra("objQuySua",
									listQuyDu1.get(chisochon));
						}
						if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 2) {
							myIntent.putExtra("objGiaoDichSua",
									listGiaoDichDu2.get(chisochon));
							myIntent.putExtra("objNhomSua",
									listNhomDu2.get(chisochon));
							myIntent.putExtra("objQuySua",
									listQuyDu2.get(chisochon));
						}
						if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 0) {
							myIntent.putExtra("objGiaoDichSua",
									listGiaoDichDu3.get(chisochon));
							myIntent.putExtra("objNhomSua",
									listNhomDu3.get(chisochon));
							myIntent.putExtra("objQuySua",
									listQuyDu3.get(chisochon));
						}
						startActivityForResult(myIntent, REQUEST_CODE);
						/**===================================*/
					}
					if (DanhSachGiaoDichFragment.tranghienthi % 3 == 2) {
						Toast.makeText(
								getActivity().getApplicationContext(),
								"" + listGiaoDichDu2.get(arg2).getGhiChu() + "",
								Toast.LENGTH_SHORT).show();
						/**===================================*/
						Intent myIntent = new Intent(
								DanhSachGiaoDich_page_Fragment.this.getActivity(),
								View_frm_QuanLyGiaoDich.class);
						myIntent.putExtra("chucnang", "xemchitiet");
						myIntent.putExtra("tieude", "XEM CHI TIẾT      ");
						if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 1) {
							myIntent.putExtra("objGiaoDichSua",
									listGiaoDichDu1.get(chisochon));
							myIntent.putExtra("objNhomSua",
									listNhomDu1.get(chisochon));
							myIntent.putExtra("objQuySua",
									listQuyDu1.get(chisochon));
						}
						if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 2) {
							myIntent.putExtra("objGiaoDichSua",
									listGiaoDichDu2.get(chisochon));
							myIntent.putExtra("objNhomSua",
									listNhomDu2.get(chisochon));
							myIntent.putExtra("objQuySua",
									listQuyDu2.get(chisochon));
						}
						if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 0) {
							myIntent.putExtra("objGiaoDichSua",
									listGiaoDichDu3.get(chisochon));
							myIntent.putExtra("objNhomSua",
									listNhomDu3.get(chisochon));
							myIntent.putExtra("objQuySua",
									listQuyDu3.get(chisochon));
						}
						startActivityForResult(myIntent, REQUEST_CODE);
						/**===================================*/
					}
					if (DanhSachGiaoDichFragment.tranghienthi % 3 == 0) {
						Toast.makeText(
								getActivity().getApplicationContext(),
								"" + listGiaoDichDu3.get(arg2).getGhiChu() + "",
								Toast.LENGTH_SHORT).show();
						/**===================================*/
						Intent myIntent = new Intent(
								DanhSachGiaoDich_page_Fragment.this.getActivity(),
								View_frm_QuanLyGiaoDich.class);
						myIntent.putExtra("chucnang", "xemchitiet");
						myIntent.putExtra("tieude", "XEM CHI TIẾT      ");
						if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 1) {
							myIntent.putExtra("objGiaoDichSua",
									listGiaoDichDu1.get(chisochon));
							myIntent.putExtra("objNhomSua",
									listNhomDu1.get(chisochon));
							myIntent.putExtra("objQuySua",
									listQuyDu1.get(chisochon));
						}
						if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 2) {
							myIntent.putExtra("objGiaoDichSua",
									listGiaoDichDu2.get(chisochon));
							myIntent.putExtra("objNhomSua",
									listNhomDu2.get(chisochon));
							myIntent.putExtra("objQuySua",
									listQuyDu2.get(chisochon));
						}
						if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 0) {
							myIntent.putExtra("objGiaoDichSua",
									listGiaoDichDu3.get(chisochon));
							myIntent.putExtra("objNhomSua",
									listNhomDu3.get(chisochon));
							myIntent.putExtra("objQuySua",
									listQuyDu3.get(chisochon));
						}
						startActivityForResult(myIntent, REQUEST_CODE);
						/**===================================*/
					}
				}
			}

		});
		
		danhsachgiaodich.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				chisochon = arg2;
				if (arg2 == 0) {
					Intent myIntent = new Intent(
							DanhSachGiaoDich_page_Fragment.this.getActivity(),
							View_frm_BaoCao_BaoCaoThang.class);

					lich = Calendar.getInstance();
					lich.add(Calendar.MONTH,
							(DanhSachGiaoDichFragment.tranghienthi - 50));
					month = lich.get(Calendar.MONTH) + 1;
					year = lich.get(Calendar.YEAR);

					myIntent.putExtra("chucnang", "xembaocao");
					myIntent.putExtra("tieude", "Báo cáo\ntháng " + month
							+ " năm " + year + "");
					if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 1) {
						myIntent.putExtra("GiaoDich", new intent_giaodich(
								listGiaoDichDu1));
						myIntent.putExtra("Nhom", new intent_nhom(listNhomDu1));
						myIntent.putExtra("Quy", new intent_quy(listQuyDu1));
					}
					if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 2) {
						myIntent.putExtra("GiaoDich", new intent_giaodich(
								listGiaoDichDu2));
						myIntent.putExtra("Nhom", new intent_nhom(listNhomDu2));
						myIntent.putExtra("Quy", new intent_quy(listQuyDu2));
					}
					if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 0) {
						myIntent.putExtra("GiaoDich", new intent_giaodich(
								listGiaoDichDu3));
						myIntent.putExtra("Nhom", new intent_nhom(listNhomDu3));
						myIntent.putExtra("Quy", new intent_quy(listQuyDu3));
					}
					startActivityForResult(myIntent, REQUEST_CODE);

				} else {
					if (DanhSachGiaoDichFragment.tranghienthi % 3 == 1) {
						Toast.makeText(
								getActivity().getApplicationContext(),
								"" + listGiaoDichDu1.get(arg2).getGhiChu() + "",
								Toast.LENGTH_SHORT).show();
						DialogChucNang();
					}
					if (DanhSachGiaoDichFragment.tranghienthi % 3 == 2) {
						Toast.makeText(
								getActivity().getApplicationContext(),
								"" + listGiaoDichDu2.get(arg2).getGhiChu() + "",
								Toast.LENGTH_SHORT).show();
						DialogChucNang();
					}
					if (DanhSachGiaoDichFragment.tranghienthi % 3 == 0) {
						Toast.makeText(
								getActivity().getApplicationContext(),
								"" + listGiaoDichDu3.get(arg2).getGhiChu() + "",
								Toast.LENGTH_SHORT).show();
						DialogChucNang();
					}
				}
				return true;
			}
		});
		return rootView;
	}

	private class selectGiaoDich extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DanhSachGiaoDich_page_Fragment.this.getActivity());
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
						ctr_giaodich ctrGD = new ctr_giaodich();
						tbl_giaodich tblGD = new tbl_giaodich();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(getActivity()
								.getApplicationContext());
						tblND = serND.selectNguoiDung();
						lich = Calendar.getInstance();
						lich.add(Calendar.MONTH, (sotrang - 50));
						month = lich.get(Calendar.MONTH) + 1;
						year = lich.get(Calendar.YEAR);
						tblGD.setQuy_Id(month);
						tblGD.setNhom_Id(year);
						tblGD.setEmail(tblND.getEmail());
						JSONObject objJSON = ctrGD.selectGiaoDich(tblGD);
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

							listGiaoDich = new ArrayList<tbl_giaodich>();
							listNhom = new ArrayList<tbl_nhom>();
							listQuy = new ArrayList<tbl_quy>();

							tbl_nhom tblN0 = new tbl_nhom();
							tbl_quy tblQ0 = new tbl_quy();
							tbl_giaodich tblGD0 = new tbl_giaodich();

							listGiaoDich.add(tblGD0);
							listNhom.add(tblN0);
							listQuy.add(tblQ0);

							arrJSON = objJSON.getJSONArray("tbl_giaodich");
							for (int i = 0; i < arrJSON.length(); i++) {
								JSONObject c = arrJSON.getJSONObject(i);
								tbl_nhom tblN = new tbl_nhom();
								tbl_quy tblQ = new tbl_quy();
								tbl_giaodich tblGD = new tbl_giaodich();

								tblGD.setGiaoDich_Id(Integer.parseInt(c
										.getString(KEY_GIAODICH_ID)));
								tblGD.setNhom_Id(Integer.parseInt(c
										.getString(KEY_NHOM_ID)));
								tblGD.setSoTien(c.getDouble(KEY_SOTIEN));
								tblGD.setGhiChu(c.getString(KEY_GHICHU));
								tblGD.setEmailDoiTac(c
										.getString(KEY_EMAILDOITAC));
								final SimpleDateFormat formatngay = new SimpleDateFormat(
										"yyyy-MM-dd");
								try {
									tblGD.setNgayThang(formatngay.parse(c
											.getString(KEY_NGAYTHANG)));
								} catch (Exception e) {
								}
								tblGD.setQuy_Id(c.getInt(KEY_QUY_ID));
								tblGD.setEmail(c.getString(KEY_EMAIL));
								try {
									tblGD.setNgayTra(formatngay.parse(c
											.getString(KEY_NGAYTRA)));
								} catch (Exception e) {
								}
								tblGD.setTienLai(c.getDouble(KEY_TIENLAI));
								tblGD.setLoaiThuLai(c.getInt(KEY_LOAITHULAI));
								listGiaoDich.add(tblGD);

								tblQ.setQuy_Id(c.getInt(KEY_QUY_ID));
								tblQ.setTenQuy(c.getString(KEY_TENQUY));
								tblQ.setSoTien(c.getDouble("SoTienQuy"));
								listQuy.add(tblQ);

								tblN.setNhom_Id(c.getInt(KEY_NHOM_ID));
								tblN.setTenNhom(c.getString(KEY_TENNHOM));
								tblN.setLoai_Id(c.getString(KEY_LOAI_ID));
								tblN.setAnh(c.getString(KEY_ANH));
								listNhom.add(tblN);
							}
							if ((sotrang % 3) == 1) {
								listGiaoDichDu1 = new ArrayList<tbl_giaodich>();
								listNhomDu1 = new ArrayList<tbl_nhom>();
								listQuyDu1 = new ArrayList<tbl_quy>();

								listGiaoDichDu1.addAll(listGiaoDich);
								listNhomDu1.addAll(listNhom);
								listQuyDu1.addAll(listQuy);

								adapterdu1 = new Custom_Listview_DanhSachGiaoDich(
										DanhSachGiaoDich_page_Fragment.this
												.getActivity(),
										R.layout.custom_listview_giaodich_tongquan,
										listGiaoDichDu1, listNhomDu1);
								danhsachgiaodich.setAdapter(adapterdu1);

							}
							if ((sotrang % 3) == 2) {
								listGiaoDichDu2 = new ArrayList<tbl_giaodich>();
								listNhomDu2 = new ArrayList<tbl_nhom>();
								listQuyDu2 = new ArrayList<tbl_quy>();

								listGiaoDichDu2.addAll(listGiaoDich);
								listNhomDu2.addAll(listNhom);
								listQuyDu2.addAll(listQuy);

								adapterdu2 = new Custom_Listview_DanhSachGiaoDich(
										DanhSachGiaoDich_page_Fragment.this
												.getActivity(),
										R.layout.custom_listview_giaodich_tongquan,
										listGiaoDichDu2, listNhomDu2);
								danhsachgiaodich.setAdapter(adapterdu2);

							}
							if ((sotrang % 3) == 0) {
								listGiaoDichDu3 = new ArrayList<tbl_giaodich>();
								listNhomDu3 = new ArrayList<tbl_nhom>();
								listQuyDu3 = new ArrayList<tbl_quy>();

								listGiaoDichDu3.addAll(listGiaoDich);
								listNhomDu3.addAll(listNhom);
								listQuyDu3.addAll(listQuy);

								adapterdu3 = new Custom_Listview_DanhSachGiaoDich(
										DanhSachGiaoDich_page_Fragment.this
												.getActivity(),
										R.layout.custom_listview_giaodich_tongquan,
										listGiaoDichDu3, listNhomDu3);
								danhsachgiaodich.setAdapter(adapterdu3);

							}

							pDialog.dismiss();
						} else {
							if (Integer.parseInt(err) == 1) {
								pDialog.dismiss();
								// new selectGiaoDich().execute();
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
					// new selectGiaoDich().execute();
					e.printStackTrace();
				}
			} else {
				pDialog.dismiss();
				// new selectGiaoDich().execute();
				Toast.makeText(getActivity().getApplicationContext(),
						"Không có kết nối internet", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private class selectGiaoDich49 extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DanhSachGiaoDich_page_Fragment.this.getActivity());
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
						ctr_giaodich ctrGD = new ctr_giaodich();
						tbl_giaodich tblGD = new tbl_giaodich();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(getActivity()
								.getApplicationContext());
						tblND = serND.selectNguoiDung();
						lich = Calendar.getInstance();
						lich.add(Calendar.MONTH, (49 - 50));
						month = lich.get(Calendar.MONTH) + 1;
						year = lich.get(Calendar.YEAR);
						tblGD.setQuy_Id(month);
						tblGD.setNhom_Id(year);
						tblGD.setEmail(tblND.getEmail());
						JSONObject objJSON = ctrGD.selectGiaoDich(tblGD);
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

							listGiaoDich = new ArrayList<tbl_giaodich>();
							listNhom = new ArrayList<tbl_nhom>();
							listQuy = new ArrayList<tbl_quy>();

							tbl_nhom tblN0 = new tbl_nhom();
							tbl_quy tblQ0 = new tbl_quy();
							tbl_giaodich tblGD0 = new tbl_giaodich();

							listGiaoDich.add(tblGD0);
							listNhom.add(tblN0);
							listQuy.add(tblQ0);

							arrJSON = objJSON.getJSONArray("tbl_giaodich");
							for (int i = 0; i < arrJSON.length(); i++) {
								JSONObject c = arrJSON.getJSONObject(i);
								tbl_nhom tblN = new tbl_nhom();
								tbl_quy tblQ = new tbl_quy();
								tbl_giaodich tblGD = new tbl_giaodich();

								tblGD.setGiaoDich_Id(Integer.parseInt(c
										.getString(KEY_GIAODICH_ID)));
								tblGD.setNhom_Id(Integer.parseInt(c
										.getString(KEY_NHOM_ID)));
								tblGD.setSoTien(c.getDouble(KEY_SOTIEN));
								tblGD.setGhiChu(c.getString(KEY_GHICHU));
								tblGD.setEmailDoiTac(c
										.getString(KEY_EMAILDOITAC));
								final SimpleDateFormat formatngay = new SimpleDateFormat(
										"yyyy-MM-dd");
								try {
									tblGD.setNgayThang(formatngay.parse(c
											.getString(KEY_NGAYTHANG)));
								} catch (Exception e) {
								}
								tblGD.setQuy_Id(c.getInt(KEY_QUY_ID));
								tblGD.setEmail(c.getString(KEY_EMAIL));
								try {
									tblGD.setNgayTra(formatngay.parse(c
											.getString(KEY_NGAYTRA)));
								} catch (Exception e) {
								}
								tblGD.setTienLai(c.getDouble(KEY_TIENLAI));
								tblGD.setLoaiThuLai(c.getInt(KEY_LOAITHULAI));
								listGiaoDich.add(tblGD);

								tblQ.setQuy_Id(c.getInt(KEY_QUY_ID));
								tblQ.setTenQuy(c.getString(KEY_TENQUY));
								tblQ.setSoTien(c.getDouble("SoTienQuy"));
								listQuy.add(tblQ);

								tblN.setNhom_Id(c.getInt(KEY_NHOM_ID));
								tblN.setTenNhom(c.getString(KEY_TENNHOM));
								tblN.setLoai_Id(c.getString(KEY_LOAI_ID));
								tblN.setAnh(c.getString(KEY_ANH));
								listNhom.add(tblN);
							}

							listGiaoDichDu1 = new ArrayList<tbl_giaodich>();
							listNhomDu1 = new ArrayList<tbl_nhom>();
							listQuyDu1 = new ArrayList<tbl_quy>();

							listGiaoDichDu1.addAll(listGiaoDich);
							listNhomDu1.addAll(listNhom);
							listQuyDu1.addAll(listQuy);

							adapterdu1 = new Custom_Listview_DanhSachGiaoDich(
									DanhSachGiaoDich_page_Fragment.this
											.getActivity(),
									R.layout.custom_listview_giaodich_tongquan,
									listGiaoDichDu1, listNhomDu1);
							danhsachgiaodich.setAdapter(adapterdu1);

							pDialog.dismiss();
						} else {
							if (Integer.parseInt(err) == 1) {
								pDialog.dismiss();
								// new selectGiaoDich49().execute();
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
					// new selectGiaoDich49().execute();
					e.printStackTrace();
				}
			} else {
				pDialog.dismiss();
				// new selectGiaoDich49().execute();
				Toast.makeText(getActivity().getApplicationContext(),
						"Không có kết nối internet", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private class selectGiaoDich50 extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DanhSachGiaoDich_page_Fragment.this.getActivity());
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
						ctr_giaodich ctrGD = new ctr_giaodich();
						tbl_giaodich tblGD = new tbl_giaodich();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(getActivity()
								.getApplicationContext());
						tblND = serND.selectNguoiDung();
						lich = Calendar.getInstance();
						lich.add(Calendar.MONTH, (50 - 50));
						month = lich.get(Calendar.MONTH) + 1;
						year = lich.get(Calendar.YEAR);
						tblGD.setQuy_Id(month);
						tblGD.setNhom_Id(year);
						tblGD.setEmail(tblND.getEmail());
						JSONObject objJSON = ctrGD.selectGiaoDich(tblGD);
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

							listGiaoDich = new ArrayList<tbl_giaodich>();
							listNhom = new ArrayList<tbl_nhom>();
							listQuy = new ArrayList<tbl_quy>();

							tbl_nhom tblN0 = new tbl_nhom();
							tbl_quy tblQ0 = new tbl_quy();
							tbl_giaodich tblGD0 = new tbl_giaodich();

							listGiaoDich.add(tblGD0);
							listNhom.add(tblN0);
							listQuy.add(tblQ0);

							arrJSON = objJSON.getJSONArray("tbl_giaodich");
							for (int i = 0; i < arrJSON.length(); i++) {
								JSONObject c = arrJSON.getJSONObject(i);
								tbl_nhom tblN = new tbl_nhom();
								tbl_quy tblQ = new tbl_quy();
								tbl_giaodich tblGD = new tbl_giaodich();

								tblGD.setGiaoDich_Id(Integer.parseInt(c
										.getString(KEY_GIAODICH_ID)));
								tblGD.setNhom_Id(Integer.parseInt(c
										.getString(KEY_NHOM_ID)));
								tblGD.setSoTien(c.getDouble(KEY_SOTIEN));
								tblGD.setGhiChu(c.getString(KEY_GHICHU));
								tblGD.setEmailDoiTac(c
										.getString(KEY_EMAILDOITAC));
								final SimpleDateFormat formatngay = new SimpleDateFormat(
										"yyyy-MM-dd");
								try {
									tblGD.setNgayThang(formatngay.parse(c
											.getString(KEY_NGAYTHANG)));
								} catch (Exception e) {
								}
								tblGD.setQuy_Id(c.getInt(KEY_QUY_ID));
								tblGD.setEmail(c.getString(KEY_EMAIL));
								try {
									tblGD.setNgayTra(formatngay.parse(c
											.getString(KEY_NGAYTRA)));
								} catch (Exception e) {
								}
								tblGD.setTienLai(c.getDouble(KEY_TIENLAI));
								tblGD.setLoaiThuLai(c.getInt(KEY_LOAITHULAI));
								listGiaoDich.add(tblGD);

								tblQ.setQuy_Id(c.getInt(KEY_QUY_ID));
								tblQ.setTenQuy(c.getString(KEY_TENQUY));
								tblQ.setSoTien(c.getDouble("SoTienQuy"));
								listQuy.add(tblQ);

								tblN.setNhom_Id(c.getInt(KEY_NHOM_ID));
								tblN.setTenNhom(c.getString(KEY_TENNHOM));
								tblN.setLoai_Id(c.getString(KEY_LOAI_ID));
								tblN.setAnh(c.getString(KEY_ANH));
								listNhom.add(tblN);
							}

							listGiaoDichDu2 = new ArrayList<tbl_giaodich>();
							listNhomDu2 = new ArrayList<tbl_nhom>();
							listQuyDu2 = new ArrayList<tbl_quy>();

							listGiaoDichDu2.addAll(listGiaoDich);
							listNhomDu2.addAll(listNhom);
							listQuyDu2.addAll(listQuy);

							adapterdu2 = new Custom_Listview_DanhSachGiaoDich(
									DanhSachGiaoDich_page_Fragment.this
											.getActivity(),
									R.layout.custom_listview_giaodich_tongquan,
									listGiaoDichDu2, listNhomDu2);
							danhsachgiaodich.setAdapter(adapterdu2);

							pDialog.dismiss();
						} else {
							if (Integer.parseInt(err) == 1) {
								pDialog.dismiss();
								// new selectGiaoDich50().execute();
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
					// new selectGiaoDich50().execute();
					e.printStackTrace();
				}
			} else {
				pDialog.dismiss();
				// new selectGiaoDich50().execute();
				Toast.makeText(getActivity().getApplicationContext(),
						"Không có kết nối internet", Toast.LENGTH_SHORT).show();
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
		String[] chucnang = new String[] { "Xem chi tiết.", "Sửa giao dịch.",
				"Xóa giao dịch.", "Hoàn tác." };
		for (int i = 0; i < anh.length; i++) {
			item_chucnang itemCN = new item_chucnang();
			itemCN.setAnh(anh[i]);
			itemCN.setTenChucNang(chucnang[i]);
			arrChucNang.add(itemCN);
		}

		Custom_Dialog_ChucNang_GiaoDich adapterCN = new Custom_Dialog_ChucNang_GiaoDich(
				getActivity(), R.layout.custom_dialog_chucnang, arrChucNang);
		dsChucNang.setAdapter(adapterCN);

		dialog.show();
		dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.ic_chucnang);

		dsChucNang.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// XEM CHI TIẾT
				if (arg2 == 0) {
					Intent myIntent = new Intent(
							DanhSachGiaoDich_page_Fragment.this.getActivity(),
							View_frm_QuanLyGiaoDich.class);
					myIntent.putExtra("chucnang", "xemchitiet");
					myIntent.putExtra("tieude", "XEM CHI TIẾT      ");
					if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 1) {
						myIntent.putExtra("objGiaoDichSua",
								listGiaoDichDu1.get(chisochon));
						myIntent.putExtra("objNhomSua",
								listNhomDu1.get(chisochon));
						myIntent.putExtra("objQuySua",
								listQuyDu1.get(chisochon));
					}
					if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 2) {
						myIntent.putExtra("objGiaoDichSua",
								listGiaoDichDu2.get(chisochon));
						myIntent.putExtra("objNhomSua",
								listNhomDu2.get(chisochon));
						myIntent.putExtra("objQuySua",
								listQuyDu2.get(chisochon));
					}
					if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 0) {
						myIntent.putExtra("objGiaoDichSua",
								listGiaoDichDu3.get(chisochon));
						myIntent.putExtra("objNhomSua",
								listNhomDu3.get(chisochon));
						myIntent.putExtra("objQuySua",
								listQuyDu3.get(chisochon));
					}
					startActivityForResult(myIntent, REQUEST_CODE);
					dialog.dismiss();
				}
				// SỬA
				if (arg2 == 1) {
					Intent myIntent = new Intent(
							DanhSachGiaoDich_page_Fragment.this.getActivity(),
							View_frm_QuanLyGiaoDich.class);
					myIntent.putExtra("chucnang", "sua");
					myIntent.putExtra("tieude", "SỬA GIAO DỊCH");
					if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 1) {
						myIntent.putExtra("objGiaoDichSua",
								listGiaoDichDu1.get(chisochon));
						myIntent.putExtra("objNhomSua",
								listNhomDu1.get(chisochon));
						myIntent.putExtra("objQuySua",
								listQuyDu1.get(chisochon));
					}
					if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 2) {
						myIntent.putExtra("objGiaoDichSua",
								listGiaoDichDu2.get(chisochon));
						myIntent.putExtra("objNhomSua",
								listNhomDu2.get(chisochon));
						myIntent.putExtra("objQuySua",
								listQuyDu2.get(chisochon));
					}
					if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 0) {
						myIntent.putExtra("objGiaoDichSua",
								listGiaoDichDu3.get(chisochon));
						myIntent.putExtra("objNhomSua",
								listNhomDu3.get(chisochon));
						myIntent.putExtra("objQuySua",
								listQuyDu3.get(chisochon));
					}
					startActivityForResult(myIntent, REQUEST_CODE);
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
				new deleteGiaoDich().execute();
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

	private class deleteGiaoDich extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DanhSachGiaoDich_page_Fragment.this.getActivity());
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
						ctr_giaodich ctrGD = new ctr_giaodich();
						tbl_giaodich tblGD = new tbl_giaodich();
						tbl_nhom tblN = new tbl_nhom();
						tbl_quy tblQ = new tbl_quy();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(getActivity()
								.getApplicationContext());
						tblND = serND.selectNguoiDung();
						if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 1) {
							tblGD.setGiaoDich_Id(listGiaoDichDu1.get(chisochon)
									.getGiaoDich_Id());
							tblGD.setSoTien(listGiaoDichDu1.get(chisochon)
									.getSoTien());
							tblGD.setQuy_Id(listGiaoDichDu1.get(chisochon)
									.getQuy_Id());
							tblN.setLoai_Id(listNhomDu1.get(chisochon)
									.getLoai_Id());
							tblQ.setSoTien(listQuyDu1.get(chisochon)
									.getSoTien());
						}
						if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 2) {
							tblGD.setGiaoDich_Id(listGiaoDichDu2.get(chisochon)
									.getGiaoDich_Id());
							tblGD.setSoTien(listGiaoDichDu2.get(chisochon)
									.getSoTien());
							tblGD.setQuy_Id(listGiaoDichDu2.get(chisochon)
									.getQuy_Id());
							tblN.setLoai_Id(listNhomDu2.get(chisochon)
									.getLoai_Id());
							tblQ.setSoTien(listQuyDu2.get(chisochon)
									.getSoTien());
						}
						if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 0) {
							tblGD.setGiaoDich_Id(listGiaoDichDu3.get(chisochon)
									.getGiaoDich_Id());
							tblGD.setSoTien(listGiaoDichDu3.get(chisochon)
									.getSoTien());
							tblGD.setQuy_Id(listGiaoDichDu3.get(chisochon)
									.getQuy_Id());
							tblN.setLoai_Id(listNhomDu3.get(chisochon)
									.getLoai_Id());
							tblQ.setSoTien(listQuyDu3.get(chisochon)
									.getSoTien());
						}
						tblGD.setEmail(tblND.getEmail());
						JSONObject objJSON = ctrGD.deleteGiaoDich(tblGD, tblN,
								tblQ);
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
							for (int i = 1; i < listQuyDu1.size(); i++) {
								if (listQuyDu1.get(i).getQuy_Id() == listQuyDu1
										.get(chisochon).getQuy_Id()) {
									listQuyDu1.get(i).setSoTien(
											objJSON.getDouble("SoTienQuy"));
								}
							}
							for (int i = 1; i < listQuyDu2.size(); i++) {
								if (listQuyDu2.get(i).getQuy_Id() == listQuyDu2
										.get(chisochon).getQuy_Id()) {
									listQuyDu2.get(i).setSoTien(
											objJSON.getDouble("SoTienQuy"));
								}
							}
							for (int i = 1; i < listQuyDu3.size(); i++) {
								if (listQuyDu3.get(i).getQuy_Id() == listQuyDu3
										.get(chisochon).getQuy_Id()) {
									listQuyDu3.get(i).setSoTien(
											objJSON.getDouble("SoTienQuy"));
								}
							}
							if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 1) {
								listGiaoDichDu1.remove(chisochon);
								listNhomDu1.remove(chisochon);
								listQuyDu1.remove(chisochon);
								// adapterdu1.notifyDataSetChanged();
							}
							if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 2) {
								listGiaoDichDu2.remove(chisochon);
								listNhomDu2.remove(chisochon);
								listQuyDu2.remove(chisochon);
								// adapterdu2.notifyDataSetChanged();
							}
							if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 0) {
								listGiaoDichDu3.remove(chisochon);
								listNhomDu3.remove(chisochon);
								listQuyDu3.remove(chisochon);
								// adapterdu3.notifyDataSetChanged();
							}
							adapterdu1.notifyDataSetChanged();
							adapterdu2.notifyDataSetChanged();
							adapterdu3.notifyDataSetChanged();
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
			if (data.getExtras().getString("chucnang").equals("sua")) {
				tbl_giaodich tblGD = new tbl_giaodich();
				tbl_nhom tblN = new tbl_nhom();
				tbl_quy tblQ = new tbl_quy();
				tblGD = (tbl_giaodich) data.getSerializableExtra("objGiaoDich");
				tblN = (tbl_nhom) data.getSerializableExtra("objNhom");
				tblQ = (tbl_quy) data.getSerializableExtra("objQuy");
				if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 1) {
					listGiaoDichDu1.remove(chisochon);
					listGiaoDichDu1.add(chisochon, tblGD);
					listNhomDu1.remove(chisochon);
					listNhomDu1.add(chisochon, tblN);
					listQuyDu1.remove(chisochon);
					listQuyDu1.add(chisochon, tblQ);
					// adapterdu1.notifyDataSetChanged();
				}
				if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 2) {
					listGiaoDichDu2.remove(chisochon);
					listGiaoDichDu2.add(chisochon, tblGD);
					listNhomDu2.remove(chisochon);
					listNhomDu2.add(chisochon, tblN);
					listQuyDu2.remove(chisochon);
					listQuyDu2.add(chisochon, tblQ);
					// adapterdu2.notifyDataSetChanged();
				}
				if ((DanhSachGiaoDichFragment.tranghienthi % 3) == 0) {
					listGiaoDichDu3.remove(chisochon);
					listGiaoDichDu3.add(chisochon, tblGD);
					listNhomDu3.remove(chisochon);
					listNhomDu3.add(chisochon, tblN);
					listQuyDu3.remove(chisochon);
					listQuyDu3.add(chisochon, tblQ);
					// adapterdu3.notifyDataSetChanged();
				}

				for (int i = 1; i < listQuyDu1.size(); i++) {
					if (listQuyDu1.get(i).getQuy_Id() == tblQ.getQuy_Id()) {
						listQuyDu1.get(i).setSoTien(tblQ.getSoTien());
					}
				}
				for (int i = 1; i < listQuyDu2.size(); i++) {
					if (listQuyDu2.get(i).getQuy_Id() == tblQ.getQuy_Id()) {
						listQuyDu2.get(i).setSoTien(tblQ.getSoTien());
					}
				}
				for (int i = 1; i < listQuyDu3.size(); i++) {
					if (listQuyDu3.get(i).getQuy_Id() == tblQ.getQuy_Id()) {
						listQuyDu3.get(i).setSoTien(tblQ.getSoTien());
					}
				}
				adapterdu1.notifyDataSetChanged();
				adapterdu2.notifyDataSetChanged();
				adapterdu3.notifyDataSetChanged();
			}

			if (data.getExtras().getString("chucnang").equals("xembaocao")) {

			}
		}
	}

}
