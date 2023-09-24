//package dhxd.chukimmuoi.qltc;
//
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import dhxd.chukimmuoi.adapter.Custom_Listview_DanhSachQuy;
//import dhxd.chukimmuoi.controller.ctr_doitac;
//import dhxd.chukimmuoi.controller.ctr_quy;
//import dhxd.chukimmuoi.model.tbl_doitac;
//import dhxd.chukimmuoi.model.tbl_nguoidung;
//import dhxd.chukimmuoi.model.tbl_quy;
//import dhxd.chukimmuoi.service.ser_nguoidung;
//import android.app.Activity;
//import android.content.Context;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
//import android.widget.ListView;
//import android.widget.Toast;
//
//public class test extends Activity {
//	AutoCompleteTextView timkiem;
//
//	private static String KEY_SUCCESS = "success";
//	private static String KEY_ERROR = "error";
//	private static String KEY_THONGBAO = "error_msg";
//
//	private String KEY_QUY_ID = "Quy_Id";
//	private String KEY_TENQUY = "TenQuy";
//	private String KEY_ANH = "Anh";
//	private String KEY_SOTIEN = "SoTien";
//	private String KEY_EMAIL = "Email";
//	private String KEY_EMAILDOITAC = "EmailDoiTac";
//
//	JSONArray arrJSON = null;
//	
//	String[] Email;
//	ArrayAdapter<String> adapter;
//	Custom_Listview_DanhSachQuy adapterDanhSachQuy;
//	ArrayList<tbl_quy> list_Quy;
//
//	ListView danhsachquy;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.test);
//
//		timkiem = (AutoCompleteTextView) findViewById(R.id.timkiem_test);
//		danhsachquy = (ListView) findViewById(R.id.listviewTest);
//		new selectDoiTac().execute();
//		new selectQuy().execute();
//	}
//
//	private class selectDoiTac extends AsyncTask<String, String, JSONObject> {
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//		}
//
//		@Override
//		protected JSONObject doInBackground(String... params) {
//			ConnectivityManager cm = (ConnectivityManager) test.this
//					.getSystemService(Context.CONNECTIVITY_SERVICE);
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
//						ser_nguoidung ser_ND = new ser_nguoidung(test.this);
//
//						tblND = ser_ND.selectNguoiDung();
//						tblDT.setEmail(tblND.getEmail());
//
//						JSONObject objJSON = ctrDT.selectDoiTac(tblDT);
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
//						String err = objJSON.getString(KEY_ERROR);
//						String thongbao = objJSON.getString(KEY_THONGBAO);
//
//						if (Integer.parseInt(success) == 1) {
//							Toast.makeText(getApplicationContext(),
//									"Danh sách bạn bè đã sẵn sàng.",
//									Toast.LENGTH_SHORT).show();
//
//							JSONArray arrJSON = objJSON
//									.getJSONArray("tbl_doitac");
//
//							Email = new String[arrJSON.length()];
//
//							for (int i = 0; i < arrJSON.length(); i++) {
//								JSONObject c = arrJSON.getJSONObject(i);
//
//								Email[i] = c.getString(KEY_EMAILDOITAC);
//							}
//
//							adapter = new ArrayAdapter<String>(
//									test.this,
//									android.R.layout.simple_dropdown_item_1line,
//									Email);
//
//							timkiem.setAdapter(adapter);
//
//						} else {
//							if (Integer.parseInt(err) == 1) {
//								Toast.makeText(getApplicationContext(),
//										thongbao, Toast.LENGTH_SHORT).show();
//							}
//						}
//					} else {
//						Toast.makeText(getApplicationContext(),
//								"Không có giá trị success trả về.",
//								Toast.LENGTH_SHORT).show();
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			} else {
//				Toast.makeText(getApplicationContext(),
//						"Không có kết nối internet", Toast.LENGTH_SHORT).show();
//			}
//		}
//	}
//	
//	private class selectQuy extends AsyncTask<String, String, JSONObject> {
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//		}
//
//		@Override
//		protected JSONObject doInBackground(String... params) {
//			ConnectivityManager cm = (ConnectivityManager) test.this
//					.getSystemService(Context.CONNECTIVITY_SERVICE);
//			NetworkInfo netInfo = cm.getActiveNetworkInfo();
//			if (netInfo != null && netInfo.isConnected()) {
//				try {
//					URL url = new URL("http://www.google.com");
//					HttpURLConnection httpUrlConn = (HttpURLConnection) url
//							.openConnection();
//					httpUrlConn.setConnectTimeout(3000);
//					httpUrlConn.connect();
//					if (httpUrlConn.getResponseCode() == 200) {
//						ctr_quy ctrQ = new ctr_quy();
//						tbl_quy tblQ = new tbl_quy();
//						tbl_nguoidung tblND = new tbl_nguoidung();
//						ser_nguoidung serND = new ser_nguoidung(test.this);
//
//						tblND = serND.selectNguoiDung();
//
//						tblQ.setEmail(tblND.getEmail());
//
//						JSONObject objJSON = ctrQ.selectQuy(tblQ);
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
//						String success = objJSON.getString(KEY_SUCCESS);
//						String err = objJSON.getString(KEY_ERROR);
//						String thongbao = objJSON.getString(KEY_THONGBAO);
//						if (Integer.parseInt(success) == 1) {
//							Toast.makeText(
//									getApplicationContext(),
//									thongbao, Toast.LENGTH_SHORT).show();
//							list_Quy = new ArrayList<tbl_quy>();
//							arrJSON = objJSON.getJSONArray("tbl_quy");
//							for (int i = 0; i < arrJSON.length(); i++) {
//								JSONObject c = arrJSON.getJSONObject(i);
//								tbl_quy tblQ = new tbl_quy();
//								tblQ.setQuy_Id(Integer.parseInt(c
//										.getString(KEY_QUY_ID)));
//								tblQ.setTenQuy(c.getString(KEY_TENQUY));
//								tblQ.setAnh(c.getString(KEY_ANH));
//								tblQ.setSoTien(Double.parseDouble(c
//										.getString(KEY_SOTIEN)));
//								tblQ.setEmail(c.getString(KEY_EMAIL));
//
//								list_Quy.add(tblQ);
//							}
//							adapterDanhSachQuy = new Custom_Listview_DanhSachQuy(
//									test.this,
//									R.layout.custom_listview_danhsachquy,
//									list_Quy);
//							danhsachquy.setAdapter(adapterDanhSachQuy);
//						} else {
//							if (Integer.parseInt(err) == 1) {
//								Toast.makeText(
//										getApplicationContext(),
//										thongbao, Toast.LENGTH_SHORT).show();
//							}
//						}
//					} else {
//						Toast.makeText(getApplicationContext(),
//								"Không có giá trị success trả về.",
//								Toast.LENGTH_SHORT).show();
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			} else {
//				Toast.makeText(getApplicationContext(),
//						"Không có kết nối internet", Toast.LENGTH_SHORT).show();
//			}
//		}
//	}
//
//}
