package dhxd.chukimmuoi.qltc;

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

import dhxd.chukimmuoi.adapter.Custom_Listview_Baocao_BaoCaoTongQuatNam;
import dhxd.chukimmuoi.controller.ctr_giaodich;
import dhxd.chukimmuoi.model.intent_giaodich;
import dhxd.chukimmuoi.model.intent_nhom;
import dhxd.chukimmuoi.model.intent_quy;
import dhxd.chukimmuoi.model.list_baocaonam;
import dhxd.chukimmuoi.model.tbl_giaodich;
import dhxd.chukimmuoi.model.tbl_nguoidung;
import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.model.tbl_quy;
import dhxd.chukimmuoi.service.ser_nguoidung;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("SimpleDateFormat")
public class View_frm_BaoCao_TongQuatNam extends Activity {
	ImageButton quaylai, congnam, trunam;
	TextView nam;
	ListView danhsachbaocao;

	private Calendar lich;
	private int year;

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

	public static ArrayList<tbl_giaodich> listGiaoDich;
	public static ArrayList<tbl_nhom> listNhom;
	public static ArrayList<tbl_quy> listQuy;

	private ArrayList<list_baocaonam> list_BaoCaoNam;
	JSONArray arrJSON = null;

	public static Custom_Listview_Baocao_BaoCaoTongQuatNam adapter;

	private static final int REQUEST_CODE = 10;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_frm_baocao_tongquat_nam);
		lich = Calendar.getInstance();

		quaylai = (ImageButton) findViewById(R.id.btnQuayLai_BC_TQN);
		congnam = (ImageButton) findViewById(R.id.btnCongNam_BC_TQN);
		trunam = (ImageButton) findViewById(R.id.btnTruNam_BC_TQN);

		nam = (TextView) findViewById(R.id.tvNam_BC_TQN);
		
		year = lich.get(Calendar.YEAR);
		nam.setText("" + year + "");
		
		danhsachbaocao = (ListView) findViewById(R.id.lvDanhSachBaoCao_BC_TQN);

		new selectGiaoDichBaoCaoNam().execute();

		congnam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				lich.add(Calendar.YEAR, 1);
				year = lich.get(Calendar.YEAR);
				nam.setText("" + year + "");
				new selectGiaoDichBaoCaoNam().execute();
			}
		});

		trunam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				lich.add(Calendar.YEAR, -1);
				year = lich.get(Calendar.YEAR);
				nam.setText("" + year + "");
				new selectGiaoDichBaoCaoNam().execute();
			}
		});
		
		danhsachbaocao.setOnItemClickListener(new OnItemClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(arg2 != 0)
				{
					ArrayList<tbl_giaodich> arrGiaoDich = new ArrayList<tbl_giaodich>();
					ArrayList<tbl_nhom> arrNhom = new ArrayList<tbl_nhom>();
					ArrayList<tbl_quy> arrQuy = new ArrayList<tbl_quy>();

					tbl_nhom tblN0 = new tbl_nhom();
					tbl_quy tblQ0 = new tbl_quy();
					tbl_giaodich tblGD0 = new tbl_giaodich();

					arrGiaoDich.add(tblGD0);
					arrNhom.add(tblN0);
					arrQuy.add(tblQ0);
					
					for (int i = 0; i < listGiaoDich.size(); i++) {
						if((listGiaoDich.get(i).getNgayThang().getMonth() + 1) == list_BaoCaoNam.get(arg2).getThang())
						{
							arrGiaoDich.add(listGiaoDich.get(i));
							arrNhom.add(listNhom.get(i));
							arrQuy.add(listQuy.get(i));
						}
					}
					
					Intent myIntent = new Intent(
							View_frm_BaoCao_TongQuatNam.this,
							View_frm_BaoCao_DanhSachGiaoDich_Thang.class);
					myIntent.putExtra("chucnang", "xemdanhsachgiaodich");
					myIntent.putExtra("GiaoDich", new intent_giaodich(arrGiaoDich));
					myIntent.putExtra("Nhom", new intent_nhom(arrNhom));
					myIntent.putExtra("Quy", new intent_quy(arrQuy));
					startActivityForResult(myIntent, REQUEST_CODE);
				}
			}
		});
		
		quaylai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(View_frm_BaoCao_TongQuatNam.this,
						View_Navigation_QLTC.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
			}
		});
	}

	private class selectGiaoDichBaoCaoNam extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_frm_BaoCao_TongQuatNam.this);
			pDialog.setTitle("Kết nối đến máy chủ");
			pDialog.setMessage("Đang kết nối đến máy chủ ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... params) {
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
						ctr_giaodich ctrGD = new ctr_giaodich();
						tbl_giaodich tblGD = new tbl_giaodich();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(
								getApplicationContext());
						tblND = serND.selectNguoiDung();

						year = lich.get(Calendar.YEAR);
						tblGD.setNhom_Id(year);

						tblGD.setEmail(tblND.getEmail());
						JSONObject objJSON = ctrGD
								.selectGiaoDichBaoCaoNam(tblGD);
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

		@SuppressWarnings("deprecation")
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
							Toast.makeText(getApplicationContext(), thongbao,
									Toast.LENGTH_SHORT).show();

							listGiaoDich = new ArrayList<tbl_giaodich>();
							listNhom = new ArrayList<tbl_nhom>();
							listQuy = new ArrayList<tbl_quy>();

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
							list_BaoCaoNam = new ArrayList<list_baocaonam>();
							list_baocaonam obj0 = new list_baocaonam();
							list_BaoCaoNam.add(obj0);
							for (int i = 0; i < 12; i++) {
								double tongthu = 0;
								double tongchi = 0;
								for (int j = 0; j < listGiaoDich.size(); j++) {
									if (listGiaoDich.get(j).getNgayThang()
											.getMonth() == i) {
										if (listNhom.get(j).getLoai_Id()
												.equals("0")) {
											tongthu = tongthu
													+ listGiaoDich.get(j)
															.getSoTien();
										} else {
											if (listNhom.get(j).getLoai_Id()
													.equals("1")) {
												tongchi = tongchi
														+ listGiaoDich.get(j)
																.getSoTien();
											}
										}

									}
								}
								list_baocaonam obj = new list_baocaonam();
								obj.setThang(i + 1);
								obj.setNam(lich.get(Calendar.YEAR));
								obj.setTienthu(tongthu);
								obj.setTienchi(tongchi);

								list_BaoCaoNam.add(obj);
							}

							adapter = new Custom_Listview_Baocao_BaoCaoTongQuatNam(
									View_frm_BaoCao_TongQuatNam.this,
									R.layout.custom_listview_bieudotron,
									list_BaoCaoNam);
							danhsachbaocao.setAdapter(adapter);
							pDialog.dismiss();
						} else {
							if (Integer.parseInt(err) == 1) {
								pDialog.dismiss();
								// new selectGiaoDich().execute();
								Toast.makeText(getApplicationContext(),
										thongbao, Toast.LENGTH_SHORT).show();
							}
						}
					} else {
						pDialog.dismiss();
						Toast.makeText(getApplicationContext(),
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
				Toast.makeText(getApplicationContext(),
						"Không có kết nối internet", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
