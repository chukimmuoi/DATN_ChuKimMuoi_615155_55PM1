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

public class View_frm_DoiMatKhau extends Activity {

	EditText matkhaucu, matkhaumoi;
	Button doimatkhau;
	ImageButton quaylai;
	String email;

	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_THONGBAO = "error_msg";

	private static final String KEY_UNIQUE_ID = "Unique_Id";
	private static final String KEY_HOTEN = "HoTen";
	private static final String KEY_EMAIL = "Email";
	private static final String KEY_MATKHAU = "MatKhau";
	private static final String KEY_MABAM = "MaBam";
	private static final String KEY_NGAYTAO = "NgayTao";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_frm_doimatkhau);

		doimatkhau = (Button) findViewById(R.id.btnDoiMatKhau_DMK);
		quaylai = (ImageButton) findViewById(R.id.btnQuayLai_DMK);
		matkhaucu = (EditText) findViewById(R.id.etMatKhauCu_DMK);
		matkhaumoi = (EditText) findViewById(R.id.etMatKhauMoi_DMK);

		doimatkhau.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tbl_nguoidung tblND = new tbl_nguoidung();
				ser_nguoidung serND = new ser_nguoidung(getApplicationContext());

				tblND = serND.selectNguoiDung();
				if (matkhaucu.getText().toString().equals(tblND.getMatKhau())) {
					email = tblND.getEmail();
					new QuenMatKhau().execute();
				} else {
					matkhaucu.setText("");
					Toast.makeText(getApplicationContext(),
							"Bạn nhập sai mật khẩu cũ, mời nhập lại.",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		quaylai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(View_frm_DoiMatKhau.this,
						View_Navigation_QLTC.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
			}
		});
	}

	private class QuenMatKhau extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_frm_DoiMatKhau.this);
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

						tblND.setEmail(email);
						tblND.setMatKhau(matkhaumoi.getText().toString());
						JSONObject objJSON = ctrND.DoiMatKhau(tblND);
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
							tblND.setMatKhau(objJSON_ND.getString(KEY_MATKHAU));
							tblND.setMaBam(objJSON_ND.getString(KEY_MABAM));
							tblND.setNgayTao(objJSON_ND.getString(KEY_NGAYTAO));

							ctr_nguoidung ctrND = new ctr_nguoidung();
							ctrND.DangXuat(getApplicationContext());
							db.insertNguoiDung(tblND);

							Intent myIntent = new Intent(
									View_frm_DoiMatKhau.this,
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
							}
						}
					} else {
						pDialog.dismiss();
						Toast.makeText(getApplicationContext(),
								"Không có giá trị success trả về.",
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
