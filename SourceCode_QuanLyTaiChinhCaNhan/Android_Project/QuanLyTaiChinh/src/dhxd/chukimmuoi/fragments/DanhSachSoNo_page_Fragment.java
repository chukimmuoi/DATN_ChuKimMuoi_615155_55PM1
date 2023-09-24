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

import dhxd.chukimmuoi.adapter.Custom_Dialog_ChucNang_SoNo;
import dhxd.chukimmuoi.adapter.Custom_Listview_DanhSachSoNo;
import dhxd.chukimmuoi.controller.ctr_kehoach;
import dhxd.chukimmuoi.model.item_chucnang;
import dhxd.chukimmuoi.model.tbl_giaodich;
import dhxd.chukimmuoi.model.tbl_kehoach;
import dhxd.chukimmuoi.model.tbl_nguoidung;
import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.model.tbl_quy;
import dhxd.chukimmuoi.qltc.R;
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
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("SimpleDateFormat")
public class DanhSachSoNo_page_Fragment extends Fragment {
	ListView danhsachthongbao;
	public static int sotrang;
	private int chisochon;

	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_THONGBAO = "error_msg";

	private String KEY_GIAODICH_ID = "GiaoDich_Id";
	private String KEY_NHOM_ID = "Nhom_Id";
	private String KEY_GHICHU = "GhiChu";
	private String KEY_NGAYTHANG = "NgayThang";
	private String KEY_QUY_ID = "Quy_Id";
	private String KEY_NGAYTRA = "NgayTra";
	private String KEY_TIENLAI = "TienLai";
	private String KEY_LOAITHULAI = "LoaiThuLai";

	private String KEY_TENNHOM = "TenNhom";
	private String KEY_TENQUY = "TenQuy";
	private String KEY_ANH = "Anh";

	private String KEY_KEHOACH_ID = "KeHoach_Id";
	private String KEY_DIENTA = "DienTa";
	private String KEY_NGAYBATDAU = "NgayBatDau";

	public static ArrayList<tbl_giaodich> listGiaoDich0, listGiaoDich1;
	public static ArrayList<tbl_nhom> listNhom0, listNhom1;
	public static ArrayList<tbl_quy> listQuy0, listQuy1;
	public static ArrayList<tbl_kehoach> listKeHoach0, listKeHoach1;
	JSONArray arrJSON = null;

	public static Custom_Listview_DanhSachSoNo adapter0, adapter1;

	private Dialog dialog;
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
			new selectNoCanThu().execute();
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			if (sotrang == 1) {
				new selectNoCanTra().execute();
			}
		}
		danhsachthongbao.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				chisochon = arg2;
				if (chisochon != 0) {
					Intent myIntent = new Intent(
							DanhSachSoNo_page_Fragment.this.getActivity(),
							View_frm_QuanLyGiaoDich.class);
					myIntent.putExtra("chucnang", "xemchitiet");
					myIntent.putExtra("tieude", "XEM CHI TIẾT     ");
					if (DanhSachSoNoFragment.tranghienthi == 0) {
						myIntent.putExtra("objGiaoDichSua",
								listGiaoDich0.get(chisochon));
						myIntent.putExtra("objNhomSua",
								listNhom0.get(chisochon));
						myIntent.putExtra("objQuySua", listQuy0.get(chisochon));
					}
					if (DanhSachSoNoFragment.tranghienthi == 1) {
						myIntent.putExtra("objGiaoDichSua",
								listGiaoDich1.get(chisochon));
						myIntent.putExtra("objNhomSua",
								listNhom1.get(chisochon));
						myIntent.putExtra("objQuySua", listQuy1.get(chisochon));
					}
					startActivityForResult(myIntent, REQUEST_CODE);
				}
			}

		});
		
		danhsachthongbao.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				chisochon = arg2;
				if (chisochon != 0) {
					DialogChucNang();
				}
				return true;
			}
		});
		return rootView;
	}

	private class selectNoCanThu extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DanhSachSoNo_page_Fragment.this.getActivity());
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

						tblKH.setLoai_Id(0);
						tblKH.setEmail(tblND.getEmail());
						JSONObject objJSON = ctrKH.selectSoNo(tblKH);
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

							listGiaoDich0 = new ArrayList<tbl_giaodich>();
							listNhom0 = new ArrayList<tbl_nhom>();
							listQuy0 = new ArrayList<tbl_quy>();
							listKeHoach0 = new ArrayList<tbl_kehoach>();

							tbl_nhom tblN0 = new tbl_nhom();
							tbl_quy tblQ0 = new tbl_quy();
							tbl_giaodich tblGD0 = new tbl_giaodich();
							tbl_kehoach tblKH0 = new tbl_kehoach();
							tblKH0.setLoai_Id(0);

							listGiaoDich0.add(tblGD0);
							listNhom0.add(tblN0);
							listQuy0.add(tblQ0);
							listKeHoach0.add(tblKH0);

							arrJSON = objJSON.getJSONArray("tbl_kehoach");
							for (int i = 0; i < arrJSON.length(); i++) {
								JSONObject c = arrJSON.getJSONObject(i);
								tbl_nhom tblN = new tbl_nhom();
								tbl_quy tblQ = new tbl_quy();
								tbl_giaodich tblGD = new tbl_giaodich();
								tbl_kehoach tblKH = new tbl_kehoach();

								tblGD.setGiaoDich_Id(Integer.parseInt(c
										.getString(KEY_GIAODICH_ID)));
								tblGD.setNhom_Id(Integer.parseInt(c
										.getString(KEY_NHOM_ID)));
								tblGD.setSoTien(c.getDouble("SoTien_tblGD"));
								tblGD.setGhiChu(c.getString(KEY_GHICHU));
								tblGD.setEmailDoiTac(c
										.getString("EmailDoiTac_tblGD"));
								final SimpleDateFormat formatngay = new SimpleDateFormat(
										"yyyy-MM-dd");
								try {
									tblGD.setNgayThang(formatngay.parse(c
											.getString(KEY_NGAYTHANG)));
								} catch (Exception e) {
								}
								tblGD.setQuy_Id(c.getInt(KEY_QUY_ID));
								tblGD.setEmail(c.getString("Email_tblGD"));
								try {
									tblGD.setNgayTra(formatngay.parse(c
											.getString(KEY_NGAYTRA)));
								} catch (Exception e) {
								}
								tblGD.setTienLai(c.getDouble(KEY_TIENLAI));
								tblGD.setLoaiThuLai(c.getInt(KEY_LOAITHULAI));
								listGiaoDich0.add(tblGD);

								tblQ.setQuy_Id(c.getInt(KEY_QUY_ID));
								tblQ.setTenQuy(c.getString(KEY_TENQUY));
								tblQ.setSoTien(c.getDouble("SoTien_tblQ"));
								listQuy0.add(tblQ);

								tblN.setNhom_Id(c.getInt(KEY_NHOM_ID));
								tblN.setTenNhom(c.getString(KEY_TENNHOM));
								tblN.setLoai_Id(c.getString("Loai_Id_tblN"));
								tblN.setAnh(c.getString(KEY_ANH));
								listNhom0.add(tblN);

								tblKH.setKeHoach_Id(c.getInt(KEY_KEHOACH_ID));
								tblKH.setLoai_Id(c.getInt("Loai_Id_tblKH"));
								tblKH.setEmailDoiTac(c
										.getString("EmailDoiTac_tblKH"));
								tblKH.setEmail(c.getString("Email_tblKH"));
								try {
									tblKH.setNgayBatDau(formatngay.parse(c
											.getString(KEY_NGAYBATDAU)));
								} catch (Exception e) {
								}
								tblKH.setDienTa(c.getString(KEY_DIENTA));
								tblKH.setSoTien(c.getDouble("SoTien_tblKH"));
								tblKH.setVi_Id(c.getInt("GiaoDich_Id"));
								tblKH.setLoaiKeHoach(c.getInt("LoaiKeHoach"));
								listKeHoach0.add(tblKH);
							}

							adapter0 = new Custom_Listview_DanhSachSoNo(
									DanhSachSoNo_page_Fragment.this
											.getActivity(),
									R.layout.custom_listview_sono_tongtien,
									listKeHoach0, listNhom0);
							
							danhsachthongbao.setAdapter(adapter0);

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

	private class selectNoCanTra extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DanhSachSoNo_page_Fragment.this.getActivity());
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

						tblKH.setLoai_Id(1);
						tblKH.setEmail(tblND.getEmail());
						JSONObject objJSON = ctrKH.selectSoNo(tblKH);
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

							listGiaoDich1 = new ArrayList<tbl_giaodich>();
							listNhom1 = new ArrayList<tbl_nhom>();
							listQuy1 = new ArrayList<tbl_quy>();
							listKeHoach1 = new ArrayList<tbl_kehoach>();

							tbl_nhom tblN0 = new tbl_nhom();
							tbl_quy tblQ0 = new tbl_quy();
							tbl_giaodich tblGD0 = new tbl_giaodich();
							tbl_kehoach tblKH0 = new tbl_kehoach();
							tblKH0.setLoai_Id(1);
							listGiaoDich1.add(tblGD0);
							listNhom1.add(tblN0);
							listQuy1.add(tblQ0);
							listKeHoach1.add(tblKH0);

							arrJSON = objJSON.getJSONArray("tbl_kehoach");
							for (int i = 0; i < arrJSON.length(); i++) {
								JSONObject c = arrJSON.getJSONObject(i);
								tbl_nhom tblN = new tbl_nhom();
								tbl_quy tblQ = new tbl_quy();
								tbl_giaodich tblGD = new tbl_giaodich();
								tbl_kehoach tblKH = new tbl_kehoach();

								tblGD.setGiaoDich_Id(Integer.parseInt(c
										.getString(KEY_GIAODICH_ID)));
								tblGD.setNhom_Id(Integer.parseInt(c
										.getString(KEY_NHOM_ID)));
								tblGD.setSoTien(c.getDouble("SoTien_tblGD"));
								tblGD.setGhiChu(c.getString(KEY_GHICHU));
								tblGD.setEmailDoiTac(c
										.getString("EmailDoiTac_tblGD"));
								final SimpleDateFormat formatngay = new SimpleDateFormat(
										"yyyy-MM-dd");
								try {
									tblGD.setNgayThang(formatngay.parse(c
											.getString(KEY_NGAYTHANG)));
								} catch (Exception e) {
								}
								tblGD.setQuy_Id(c.getInt(KEY_QUY_ID));
								tblGD.setEmail(c.getString("Email_tblGD"));
								try {
									tblGD.setNgayTra(formatngay.parse(c
											.getString(KEY_NGAYTRA)));
								} catch (Exception e) {
								}
								tblGD.setTienLai(c.getDouble(KEY_TIENLAI));
								tblGD.setLoaiThuLai(c.getInt(KEY_LOAITHULAI));
								listGiaoDich1.add(tblGD);

								tblQ.setQuy_Id(c.getInt(KEY_QUY_ID));
								tblQ.setTenQuy(c.getString(KEY_TENQUY));
								tblQ.setSoTien(c.getDouble("SoTien_tblQ"));
								listQuy1.add(tblQ);

								tblN.setNhom_Id(c.getInt(KEY_NHOM_ID));
								tblN.setTenNhom(c.getString(KEY_TENNHOM));
								tblN.setLoai_Id(c.getString("Loai_Id_tblN"));
								tblN.setAnh(c.getString(KEY_ANH));
								listNhom1.add(tblN);

								tblKH.setKeHoach_Id(c.getInt(KEY_KEHOACH_ID));
								tblKH.setLoai_Id(c.getInt("Loai_Id_tblKH"));
								tblKH.setEmailDoiTac(c
										.getString("EmailDoiTac_tblKH"));
								tblKH.setEmail(c.getString("Email_tblKH"));
								try {
									tblKH.setNgayBatDau(formatngay.parse(c
											.getString(KEY_NGAYBATDAU)));
								} catch (Exception e) {
								}
								tblKH.setDienTa(c.getString(KEY_DIENTA));
								tblKH.setSoTien(c.getDouble("SoTien_tblKH"));
								tblKH.setVi_Id(c.getInt("GiaoDich_Id"));
								tblKH.setLoaiKeHoach(c.getInt("LoaiKeHoach"));
								listKeHoach1.add(tblKH);
							}

							adapter1 = new Custom_Listview_DanhSachSoNo(
									DanhSachSoNo_page_Fragment.this
											.getActivity(),
									R.layout.custom_listview_sono_tongtien,
									listKeHoach1, listNhom1);
							danhsachthongbao.setAdapter(adapter1);

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

	public void DialogChucNang() {
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
		dialog.setContentView(R.layout.custom_dialog_listview);
		dialog.setTitle("Chọn chức năng");

		ListView dsChucNang = (ListView) dialog
				.findViewById(R.id.lvDSChucNang_CD_Lv);
		ArrayList<item_chucnang> arrChucNang = new ArrayList<item_chucnang>();
		String[] anh = new String[] { "0", "1", "2" };
		String[] chucnang = new String[] { "Xem chi tiết giao dịch.",
				"Thực hiện giao dịch.", "Hoàn tác." };
		for (int i = 0; i < anh.length; i++) {
			item_chucnang itemCN = new item_chucnang();
			itemCN.setAnh(anh[i]);
			itemCN.setTenChucNang(chucnang[i]);
			arrChucNang.add(itemCN);
		}

		Custom_Dialog_ChucNang_SoNo adapterCN = new Custom_Dialog_ChucNang_SoNo(
				getActivity(), R.layout.custom_dialog_chucnang, arrChucNang);
		dsChucNang.setAdapter(adapterCN);

		dialog.show();
		dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.ic_chucnang);

		dsChucNang.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// CHI TIẾT GIAO DỊCH
				if (arg2 == 0) {
					Intent myIntent = new Intent(
							DanhSachSoNo_page_Fragment.this.getActivity(),
							View_frm_QuanLyGiaoDich.class);
					myIntent.putExtra("chucnang", "xemchitiet");
					myIntent.putExtra("tieude", "XEM CHI TIẾT     ");
					if (DanhSachSoNoFragment.tranghienthi == 0) {
						myIntent.putExtra("objGiaoDichSua",
								listGiaoDich0.get(chisochon));
						myIntent.putExtra("objNhomSua",
								listNhom0.get(chisochon));
						myIntent.putExtra("objQuySua", listQuy0.get(chisochon));
					}
					if (DanhSachSoNoFragment.tranghienthi == 1) {
						myIntent.putExtra("objGiaoDichSua",
								listGiaoDich1.get(chisochon));
						myIntent.putExtra("objNhomSua",
								listNhom1.get(chisochon));
						myIntent.putExtra("objQuySua", listQuy1.get(chisochon));
					}
					startActivityForResult(myIntent, REQUEST_CODE);
					dialog.dismiss();
				}
				// THỰC HIỆN GIAO DỊCH
				if (arg2 == 1) {
					Intent myIntent = new Intent(
							DanhSachSoNo_page_Fragment.this.getActivity(),
							View_frm_QuanLyGiaoDich.class);
					myIntent.putExtra("chucnang", "thuchiengiaodichthutra");
					myIntent.putExtra("tieude", "THÊM GIAO DỊCH");
					if (DanhSachSoNoFragment.tranghienthi == 0) {
						myIntent.putExtra("objKeHoachThem",
								listKeHoach0.get(chisochon));
					}
					if (DanhSachSoNoFragment.tranghienthi == 1) {
						myIntent.putExtra("objKeHoachThem",
								listKeHoach1.get(chisochon));
					}
					startActivityForResult(myIntent, REQUEST_CODE);
					dialog.dismiss();
				}
				// HOÀN TÁC
				if (arg2 == 2) {
					dialog.dismiss();
				}
			}
		});
	}

	private class deleteSoNoLienQuan extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DanhSachSoNo_page_Fragment.this.getActivity());
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
						if (DanhSachSoNoFragment.tranghienthi == 0) {
							tblKH.setVi_Id(listKeHoach0.get(chisochon)
									.getVi_Id());
						}
						if (DanhSachSoNoFragment.tranghienthi == 1) {
							tblKH.setVi_Id(listKeHoach1.get(chisochon)
									.getVi_Id());
						}
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
							if (DanhSachSoNoFragment.tranghienthi == 0) {
								int giaodich_id = listKeHoach0.get(
										chisochon).getVi_Id();
								for (int i = 1; i < listKeHoach0.size(); i++) {
									if (listKeHoach0.get(i).getVi_Id() == giaodich_id) {
										listKeHoach0.remove(i);
										listGiaoDich0.remove(i);
										listNhom0.remove(i);
										listQuy0.remove(i);
									}
								}
							}
							if (DanhSachSoNoFragment.tranghienthi == 1) {
								int giaodich_id = listKeHoach1.get(chisochon)
										.getVi_Id();
								for (int i = 1; i < listKeHoach1.size(); i++) {
									if (listKeHoach1.get(i).getVi_Id() == giaodich_id) {
										listKeHoach1.remove(i);
										listGiaoDich1.remove(i);
										listNhom1.remove(i);
										listQuy1.remove(i);
									}
								}
							}
							adapter0.notifyDataSetChanged();
							adapter1.notifyDataSetChanged();
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

	private class deleteSoNoKhongLienQuan extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DanhSachSoNo_page_Fragment.this.getActivity());
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
						if (DanhSachSoNoFragment.tranghienthi == 0) {
							tblKH.setKeHoach_Id(listKeHoach0.get(chisochon)
									.getKeHoach_Id());
						}
						if (DanhSachSoNoFragment.tranghienthi == 1) {
							tblKH.setKeHoach_Id(listKeHoach1.get(chisochon)
									.getKeHoach_Id());
						}

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

							if (DanhSachSoNoFragment.tranghienthi == 0) {
								listKeHoach0.remove(chisochon);
								listNhom0.remove(chisochon);
								listGiaoDich0.remove(chisochon);
								listQuy0.remove(chisochon);
							}
							if (DanhSachSoNoFragment.tranghienthi == 1) {
								listKeHoach1.remove(chisochon);
								listNhom1.remove(chisochon);
								listGiaoDich1.remove(chisochon);
								listQuy1.remove(chisochon);
							}
							adapter0.notifyDataSetChanged();
							adapter1.notifyDataSetChanged();
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
		if (resultCode == FragmentActivity.RESULT_OK
				&& requestCode == REQUEST_CODE) {
			if (data.getExtras().getString("chucnang")
					.equals("thuchiengiaodichthutra")) {
				if (DanhSachSoNoFragment.tranghienthi == 0) {
					if (listKeHoach0.get(chisochon).getVi_Id() == 0) {
						new deleteSoNoKhongLienQuan().execute();
					} else {
						if (listKeHoach0.get(chisochon).getVi_Id() != 0) {
							if (listKeHoach0.get(chisochon).getLoaiKeHoach() == 0) {
								new deleteSoNoLienQuan().execute();
							} else {
								if (listKeHoach0.get(chisochon)
										.getLoaiKeHoach() != 0) {
									new deleteSoNoKhongLienQuan().execute();
								}
							}

						}
					}
				}
				if (DanhSachSoNoFragment.tranghienthi == 1) {
					if (listKeHoach1.get(chisochon).getVi_Id() == 0) {
						new deleteSoNoKhongLienQuan().execute();
					} else {
						if (listKeHoach1.get(chisochon).getVi_Id() != 0) {
							if (listKeHoach1.get(chisochon).getLoaiKeHoach() == 0) {
								new deleteSoNoLienQuan().execute();
							} else {
								if (listKeHoach1.get(chisochon)
										.getLoaiKeHoach() != 0) {
									new deleteSoNoKhongLienQuan().execute();
								}
							}

						}
					}
				}
			}
		}
	}

}
