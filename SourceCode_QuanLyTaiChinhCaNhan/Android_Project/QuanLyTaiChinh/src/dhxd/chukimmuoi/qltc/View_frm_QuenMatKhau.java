package dhxd.chukimmuoi.qltc;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import dhxd.chukimmuoi.controller.ctr_nguoidung;
import dhxd.chukimmuoi.model.tbl_nguoidung;
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

public class View_frm_QuenMatKhau extends Activity {
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_THONGBAO = "error_msg";

	EditText email;
	Button quenmatkhau;
	ImageButton quaylai;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_frm_quenmatkhau);

		email = (EditText) findViewById(R.id.etEmail_QMK);
		quenmatkhau = (Button) findViewById(R.id.btnQuenMatKhau_QMK);
		quaylai = (ImageButton) findViewById(R.id.btnQuayLai_QMK);

		quenmatkhau.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!email.getText().toString().equals("")) {
					new QuenMatKhau().execute();
				} else {
					Toast.makeText(getApplicationContext(),
							"Bạn chưa nhập địa chỉ email.", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		quaylai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(View_frm_QuenMatKhau.this,
						View_frm_DangNhap.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
	}

	public class QuenMatKhau extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_frm_QuenMatKhau.this);
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

						tblND.setEmail(email.getText().toString());
						JSONObject objJSON = ctrND.QuenMatKhau(tblND);
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
							pDialog.dismiss();
							Toast.makeText(getApplicationContext(), thongbao,
									Toast.LENGTH_SHORT).show();
							Intent myIntent = new Intent(
									View_frm_QuenMatKhau.this,
									View_frm_DangNhap.class);
							myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
