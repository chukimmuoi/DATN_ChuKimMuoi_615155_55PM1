package dhxd.chukimmuoi.fragments;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dhxd.chukimmuoi.adapter.Custom_Dialog_ChucNang_GiaoDich;
import dhxd.chukimmuoi.adapter.Custom_Listview_DanhSachKeHoachNganSach;
import dhxd.chukimmuoi.controller.ctr_kehoach;
import dhxd.chukimmuoi.model.intent_giaodich;
import dhxd.chukimmuoi.model.intent_kehoach;
import dhxd.chukimmuoi.model.intent_nhom;
import dhxd.chukimmuoi.model.intent_quy;
import dhxd.chukimmuoi.model.item_chucnang;
import dhxd.chukimmuoi.model.tbl_giaodich;
import dhxd.chukimmuoi.model.tbl_kehoach;
import dhxd.chukimmuoi.model.tbl_nguoidung;
import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.model.tbl_quy;
import dhxd.chukimmuoi.qltc.R;
import dhxd.chukimmuoi.qltc.View_frm_KeHoachNganSachChiTiet;
import dhxd.chukimmuoi.qltc.View_frm_QuanLyKeHoachNganSach;
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
public class DanhSachKeHoachNganSach_page_Fragment extends Fragment {
	ListView danhsachkehoachngansach;
	public static int sotrang;

	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_THONGBAO = "error_msg";

	private String KEY_KEHOACH_ID = "KeHoach_Id";
	private String KEY_NHOM_ID = "Nhom_Id";
	private String KEY_LOAI_ID = "Loai_Id";
	private String KEY_EMAIL = "Email";
	private String KEY_NGAYBATDAU = "NgayBatDau";
	private String KEY_NGAYKETTHUC = "NgayKetThuc";
	private String KEY_DIENTA = "DienTa";
	private String KEY_SOTIEN = "SoTien";

	private String KEY_TENNHOM = "TenNhom";
	private String KEY_ANH = "Anh";

	private String KEY_GIAODICH_ID = "GiaoDich_Id";
	private String KEY_GHICHU = "GhiChu";
	private String KEY_EMAILDOITAC = "EmailDoiTac";
	private String KEY_NGAYTHANG = "NgayThang";
	private String KEY_QUY_ID = "Quy_Id";
	private String KEY_NGAYTRA = "NgayTra";
	private String KEY_TIENLAI = "TienLai";
	private String KEY_LOAITHULAI = "LoaiThuLai";
	private String KEY_TENQUY = "TenQuy";

	public static ArrayList<tbl_kehoach> listKeHoach0, listKeHoach1;
	public static ArrayList<tbl_nhom> listNhom0, listNhom1;
	public static ArrayList<tbl_giaodich> listGiaoDich0, listGiaoDich1;
	public static ArrayList<tbl_quy> listQuy0, listQuy1;
	JSONArray arrJSON = null;
	public static Custom_Listview_DanhSachKeHoachNganSach adapter0, adapter1;

	private Date ngayhientai = new Date();
	private Dialog dialog;
	private static final int REQUEST_CODE = 10;
	private int chisochon;

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
		danhsachkehoachngansach = (ListView) rootView
				.findViewById(R.id.lv_DanhSachHienThi_DSHT);

		try {
			if (sotrang == 0) {
				new selectKeHoachNganSach0().execute();
			} else {
				if (sotrang == 1) {
					new selectKeHoachNganSach1().execute();
				}
			}
		} catch (Exception e) {
		}

		danhsachkehoachngansach
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						chisochon = arg2;
						Intent myIntent = new Intent(
								DanhSachKeHoachNganSach_page_Fragment.this
										.getActivity(),
								View_frm_KeHoachNganSachChiTiet.class);
						myIntent.putExtra("chucnang", "xemchitiet");
						if (DanhSachKeHoachNganSachFragment.tranghienthi == 0) {
							myIntent.putExtra("kehoach_id", listKeHoach0.get(chisochon).getKeHoach_Id());
							myIntent.putExtra("objKeHoachXem", new intent_kehoach(listKeHoach0));
							myIntent.putExtra("objNhomXem", new intent_nhom(listNhom0));
							myIntent.putExtra("objQuyXem", new intent_quy(listQuy0));
							myIntent.putExtra("objGiaoDichXem", new intent_giaodich(listGiaoDich0));
						}
						if (DanhSachKeHoachNganSachFragment.tranghienthi == 1) {
							myIntent.putExtra("kehoach_id", listKeHoach1.get(chisochon).getKeHoach_Id());
							myIntent.putExtra("objKeHoachXem", new intent_kehoach(listKeHoach1));
							myIntent.putExtra("objNhomXem", new intent_nhom(listNhom1));
							myIntent.putExtra("objQuyXem", new intent_quy(listQuy1));
							myIntent.putExtra("objGiaoDichXem", new intent_giaodich(listGiaoDich1));
						}
						startActivityForResult(myIntent, REQUEST_CODE);
					}

				});
		
		danhsachkehoachngansach.setOnItemLongClickListener(new OnItemLongClickListener() {
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

	private class selectKeHoachNganSach0 extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DanhSachKeHoachNganSach_page_Fragment.this.getActivity());
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

						tblKH.setNgayBatDau(ngayhientai);
						tblKH.setEmail(tblND.getEmail());
						JSONObject objJSON = ctrKH
								.selectKeHoachNganSach0(tblKH);
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

							listKeHoach0 = new ArrayList<tbl_kehoach>();
							listNhom0 = new ArrayList<tbl_nhom>();
							listGiaoDich0 = new ArrayList<tbl_giaodich>();
							listQuy0 = new ArrayList<tbl_quy>();

							arrJSON = objJSON.getJSONArray("tbl_kehoach");
							for (int i = 0; i < arrJSON.length(); i++) {
								JSONObject c = arrJSON.getJSONObject(i);
								tbl_nhom tblN = new tbl_nhom();
								tbl_quy tblQ = new tbl_quy();
								tbl_kehoach tblKH = new tbl_kehoach();
								tbl_giaodich tblGD = new tbl_giaodich();

								tblKH.setKeHoach_Id(c.getInt(KEY_KEHOACH_ID));
								tblKH.setNhom_Id(c.getInt(KEY_NHOM_ID));
								tblKH.setLoai_Id(c.getInt(KEY_LOAI_ID));
								tblKH.setEmail(c.getString(KEY_EMAIL));
								final SimpleDateFormat formatngay = new SimpleDateFormat(
										"yyyy-MM-dd");
								try {
									tblKH.setNgayBatDau(formatngay.parse(c
											.getString(KEY_NGAYBATDAU)));
								} catch (Exception e) {
								}

								try {
									tblKH.setNgayKetThuc(formatngay.parse(c
											.getString(KEY_NGAYKETTHUC)));
								} catch (Exception e) {
								}
								tblKH.setDienTa(c.getString(KEY_DIENTA));
								tblKH.setSoTien(c.getDouble("SoTienNganSach"));
								listKeHoach0.add(tblKH);

								tblGD.setGiaoDich_Id(Integer.parseInt(c
										.getString(KEY_GIAODICH_ID)));
								tblGD.setNhom_Id(Integer.parseInt(c
										.getString(KEY_NHOM_ID)));
								tblGD.setSoTien(c.getDouble(KEY_SOTIEN));
								tblGD.setGhiChu(c.getString(KEY_GHICHU));
								tblGD.setEmailDoiTac(c
										.getString(KEY_EMAILDOITAC));

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
								listGiaoDich0.add(tblGD);

								tblQ.setQuy_Id(c.getInt(KEY_QUY_ID));
								tblQ.setTenQuy(c.getString(KEY_TENQUY));
								tblQ.setSoTien(c.getDouble("SoTienQuy"));
								tblQ.setAnh(c.getString("AnhQuy"));
								listQuy0.add(tblQ);

								tblN.setNhom_Id(c.getInt(KEY_NHOM_ID));
								tblN.setTenNhom(c.getString(KEY_TENNHOM));
								tblN.setLoai_Id(c.getString(KEY_LOAI_ID));
								tblN.setAnh(c.getString(KEY_ANH));
								listNhom0.add(tblN);

							}
							adapter0 = new Custom_Listview_DanhSachKeHoachNganSach(
									DanhSachKeHoachNganSach_page_Fragment.this
											.getActivity(),
									R.layout.custom_listview_kehoach_ngansach,
									listKeHoach0, listGiaoDich0, listNhom0);

							danhsachkehoachngansach.setAdapter(adapter0);

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

	private class selectKeHoachNganSach1 extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DanhSachKeHoachNganSach_page_Fragment.this.getActivity());
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

						tblKH.setNgayBatDau(ngayhientai);
						tblKH.setEmail(tblND.getEmail());
						JSONObject objJSON = ctrKH
								.selectKeHoachNganSach1(tblKH);
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

							listKeHoach1 = new ArrayList<tbl_kehoach>();
							listNhom1 = new ArrayList<tbl_nhom>();
							listGiaoDich1 = new ArrayList<tbl_giaodich>();
							listQuy1 = new ArrayList<tbl_quy>();

							arrJSON = objJSON.getJSONArray("tbl_kehoach");
							for (int i = 0; i < arrJSON.length(); i++) {
								JSONObject c = arrJSON.getJSONObject(i);
								tbl_nhom tblN = new tbl_nhom();
								tbl_quy tblQ = new tbl_quy();
								tbl_kehoach tblKH = new tbl_kehoach();
								tbl_giaodich tblGD = new tbl_giaodich();

								tblKH.setKeHoach_Id(c.getInt(KEY_KEHOACH_ID));
								tblKH.setNhom_Id(c.getInt(KEY_NHOM_ID));
								tblKH.setLoai_Id(c.getInt(KEY_LOAI_ID));
								tblKH.setEmail(c.getString(KEY_EMAIL));
								final SimpleDateFormat formatngay = new SimpleDateFormat(
										"yyyy-MM-dd");
								try {
									tblKH.setNgayBatDau(formatngay.parse(c
											.getString(KEY_NGAYBATDAU)));
								} catch (Exception e) {
								}

								try {
									tblKH.setNgayKetThuc(formatngay.parse(c
											.getString(KEY_NGAYKETTHUC)));
								} catch (Exception e) {
								}
								tblKH.setDienTa(c.getString(KEY_DIENTA));
								tblKH.setSoTien(c.getDouble("SoTienNganSach"));
								listKeHoach1.add(tblKH);

								tblGD.setGiaoDich_Id(Integer.parseInt(c
										.getString(KEY_GIAODICH_ID)));
								tblGD.setNhom_Id(Integer.parseInt(c
										.getString(KEY_NHOM_ID)));
								tblGD.setSoTien(c.getDouble(KEY_SOTIEN));
								tblGD.setGhiChu(c.getString(KEY_GHICHU));
								tblGD.setEmailDoiTac(c
										.getString(KEY_EMAILDOITAC));

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
								listGiaoDich1.add(tblGD);

								tblQ.setQuy_Id(c.getInt(KEY_QUY_ID));
								tblQ.setTenQuy(c.getString(KEY_TENQUY));
								tblQ.setSoTien(c.getDouble("SoTienQuy"));
								tblQ.setAnh(c.getString("AnhQuy"));
								listQuy1.add(tblQ);

								tblN.setNhom_Id(c.getInt(KEY_NHOM_ID));
								tblN.setTenNhom(c.getString(KEY_TENNHOM));
								tblN.setLoai_Id(c.getString(KEY_LOAI_ID));
								tblN.setAnh(c.getString(KEY_ANH));
								listNhom1.add(tblN);

							}
							adapter1 = new Custom_Listview_DanhSachKeHoachNganSach(
									DanhSachKeHoachNganSach_page_Fragment.this
											.getActivity(),
									R.layout.custom_listview_kehoach_ngansach,
									listKeHoach1, listGiaoDich1, listNhom1);

							danhsachkehoachngansach.setAdapter(adapter1);

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
		dialog.setTitle("Chức năng");

		ListView dsChucNang = (ListView) dialog
				.findViewById(R.id.lvDSChucNang_CD_Lv);
		ArrayList<item_chucnang> arrChucNang = new ArrayList<item_chucnang>();
		String[] anh = new String[] { "0", "1", "2", "3" };
		String[] chucnang = new String[] { "Xem chi tiết.", "Sửa kế hoạch.",
				"Xóa kế hoạch.", "Hoàn tác." };
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
							DanhSachKeHoachNganSach_page_Fragment.this
									.getActivity(),
							View_frm_KeHoachNganSachChiTiet.class);
					myIntent.putExtra("chucnang", "xemchitiet");
					if (DanhSachKeHoachNganSachFragment.tranghienthi == 0) {
						myIntent.putExtra("kehoach_id", listKeHoach0.get(chisochon).getKeHoach_Id());
						myIntent.putExtra("objKeHoachXem", new intent_kehoach(listKeHoach0));
						myIntent.putExtra("objNhomXem", new intent_nhom(listNhom0));
						myIntent.putExtra("objQuyXem", new intent_quy(listQuy0));
						myIntent.putExtra("objGiaoDichXem", new intent_giaodich(listGiaoDich0));
					}
					if (DanhSachKeHoachNganSachFragment.tranghienthi == 1) {
						myIntent.putExtra("kehoach_id", listKeHoach1.get(chisochon).getKeHoach_Id());
						myIntent.putExtra("objKeHoachXem", new intent_kehoach(listKeHoach1));
						myIntent.putExtra("objNhomXem", new intent_nhom(listNhom1));
						myIntent.putExtra("objQuyXem", new intent_quy(listQuy1));
						myIntent.putExtra("objGiaoDichXem", new intent_giaodich(listGiaoDich1));
					}
					startActivityForResult(myIntent, REQUEST_CODE);
					dialog.dismiss();
				}
				// SỬA
				if (arg2 == 1) {
					Intent myIntent = new Intent(
							DanhSachKeHoachNganSach_page_Fragment.this
									.getActivity(),
							View_frm_QuanLyKeHoachNganSach.class);
					myIntent.putExtra("chucnang", "sua");
					myIntent.putExtra("tieude", "SỬA KẾ HOẠCH");
					if (DanhSachKeHoachNganSachFragment.tranghienthi == 0) {
						myIntent.putExtra("objKeHoachSua",
								listKeHoach0.get(chisochon));
						myIntent.putExtra("objNhomSua",
								listNhom0.get(chisochon));
					}
					if (DanhSachKeHoachNganSachFragment.tranghienthi == 1) {
						myIntent.putExtra("objKeHoachSua",
								listKeHoach1.get(chisochon));
						myIntent.putExtra("objNhomSua",
								listNhom1.get(chisochon));
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
				new deleteKeHoachNganSach().execute();
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

	private class deleteKeHoachNganSach extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DanhSachKeHoachNganSach_page_Fragment.this.getActivity());
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

						if (DanhSachKeHoachNganSachFragment.tranghienthi == 0) {
							tblKH.setKeHoach_Id(listKeHoach0.get(chisochon)
									.getKeHoach_Id());
						}
						if (DanhSachKeHoachNganSachFragment.tranghienthi == 1) {
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
							if (DanhSachKeHoachNganSachFragment.tranghienthi == 0) {
								new selectKeHoachNganSach0().execute();
								// for (int i = 0; i < listKeHoach0.size(); i++)
								// {
								// if (listKeHoach0.get(i).getKeHoach_Id() ==
								// listKeHoach0
								// .get(chisochon).getKeHoach_Id()) {
								// listKeHoach0.remove(i);
								// listGiaoDich0.remove(i);
								// listNhom0.remove(i);
								// adapter0.notifyDataSetChanged();
								// }
								// }
							} else {
								if (DanhSachKeHoachNganSachFragment.tranghienthi == 1) {
									new selectKeHoachNganSach1().execute();
									// for (int i = 0; i < listKeHoach1.size();
									// i++)
									// {
									// if (listKeHoach1.get(i).getKeHoach_Id()
									// ==
									// listKeHoach1
									// .get(chisochon).getKeHoach_Id()) {
									// listKeHoach1.remove(i);
									// listGiaoDich1.remove(i);
									// listNhom1.remove(i);
									// adapter1.notifyDataSetChanged();
									// }
									// }
								}
							}
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
			if (data.getExtras().getString("chucnang").equals("sua")) {
				try {
					if (DanhSachKeHoachNganSachFragment.tranghienthi == 0) {
						new selectKeHoachNganSach0().execute();
					} else {
						if (DanhSachKeHoachNganSachFragment.tranghienthi == 1) {
							new selectKeHoachNganSach1().execute();
						}
					}
				} catch (Exception e) {
				}
				// tbl_kehoach tblKH = new tbl_kehoach();
				// tbl_nhom tblN = new tbl_nhom();
				// tblKH = (tbl_kehoach) data
				// .getSerializableExtra("objKeHoachSua");
				// tblN = (tbl_nhom) data.getSerializableExtra("objNhom");
				//
				// if(DanhSachKeHoachNganSachFragment.tranghienthi == 0){
				//
				// }
				// if(DanhSachKeHoachNganSachFragment.tranghienthi == 1){
				//
				// }
			}
			
			if (data.getExtras().getString("chucnang").equals("xemchitiet")) {
				
			}
		}
	}

}
