package dhxd.chukimmuoi.qltc;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import dhxd.chukimmuoi.controller.ctr_nguoidung;
import dhxd.chukimmuoi.model.tbl_nguoidung;
import dhxd.chukimmuoi.service.ser_nguoidung;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class View_frm_DangKy extends Activity {
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_THONGBAO = "error_msg";
	private static final String KEY_UNIQUE_ID = "Unique_Id";
	private static final String KEY_HOTEN = "HoTen";
	private static final String KEY_EMAIL = "Email";
	// private static final String KEY_MATKHAU = "MatKhau";
	private static final String KEY_MABAM = "MaBam";
	private static final String KEY_NGAYTAO = "NgayTao";

	EditText hoten, email, matkhau;
	Button dangky;
	ImageButton quaylai;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_frm_dangky);

		hoten = (EditText) findViewById(R.id.etHoTen_DK);
		email = (EditText) findViewById(R.id.etEmail_DK);
		matkhau = (EditText) findViewById(R.id.etMatKhau_DK);
		dangky = (Button) findViewById(R.id.btnDangKy_DK);
		quaylai = (ImageButton) findViewById(R.id.btnQuayLai_DK);

		dangky.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if ((!hoten.getText().toString().equals(""))
						&& (!email.getText().toString().equals(""))
						&& (!matkhau.getText().toString().equals(""))) {
					if (hoten.getText().toString().length() > 1) {
						new DangKy().execute();
					} else {
						Toast.makeText(getApplicationContext(),
								"Họ tên người dùng phải có 2 ký tự trở nên",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Lỗi, bạn chưa nhập đủ thông tin cần thiết.",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		quaylai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(View_frm_DangKy.this,
						View_frm_DangNhap.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
	}

	private class DangKy extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_frm_DangKy.this);
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
						ctr_nguoidung ctrND = new ctr_nguoidung();
						tbl_nguoidung tblND = new tbl_nguoidung();
						tblND.setHoTen(hoten.getText().toString());
						tblND.setEmail(email.getText().toString());
						tblND.setMatKhau(matkhau.getText().toString());
						JSONObject objJSON = ctrND.DangKy(tblND);
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
							Toast.makeText(getApplicationContext(), thongbao,
									Toast.LENGTH_SHORT).show();
							ser_nguoidung db = new ser_nguoidung(
									getApplicationContext());
							tbl_nguoidung tblND = new tbl_nguoidung();
							JSONObject objJSON_ND = objJSON
									.getJSONObject("nguoidung");
							tblND.setUnique_Id(objJSON_ND
									.getString(KEY_UNIQUE_ID));
							tblND.setHoTen(objJSON_ND.getString(KEY_HOTEN));
							tblND.setEmail(objJSON_ND.getString(KEY_EMAIL));
							// tblND.setMatKhau(objJSON_ND.getString(KEY_MATKHAU));
							tblND.setMatKhau(matkhau.getText().toString());
							tblND.setMaBam(objJSON_ND.getString(KEY_MABAM));
							tblND.setNgayTao(objJSON_ND.getString(KEY_NGAYTAO));
							ctr_nguoidung ctrND = new ctr_nguoidung();
							ctrND.DangXuat(getApplicationContext());
							db.insertNguoiDung(tblND);

							Intent myIntent = new Intent(View_frm_DangKy.this,
									View_Navigation_QLTC.class);
							myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							pDialog.dismiss();
							startActivity(myIntent);
							finish();
						} else {
							if (Integer.parseInt(err) == 1) {
								pDialog.dismiss();
								Toast.makeText(getApplicationContext(),
										thongbao, Toast.LENGTH_SHORT).show();
							} else {
								if (Integer.parseInt(err) == 2) {
									pDialog.dismiss();
									Toast.makeText(getApplicationContext(),
											thongbao, Toast.LENGTH_SHORT)
											.show();
								}
							}
						}
					} else {
						pDialog.dismiss();
						Toast.makeText(getApplicationContext(),
								"Không có giá trị success trả về",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				pDialog.dismiss();
				Toast.makeText(getApplicationContext(),
						"Không có kết nối internet", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
