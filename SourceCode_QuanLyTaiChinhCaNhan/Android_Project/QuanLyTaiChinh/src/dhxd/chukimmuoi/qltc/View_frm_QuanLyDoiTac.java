package dhxd.chukimmuoi.qltc;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import dhxd.chukimmuoi.controller.ctr_doitac;
import dhxd.chukimmuoi.model.tbl_doitac;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class View_frm_QuanLyDoiTac extends Activity {
	ImageButton quaylai;
	TextView tieude;
	ImageButton xong;
	EditText email;
	EditText quanhe;

	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_THONGBAO = "error_msg";

	// private String KEY_DOITAC_ID = "DoiTac_Id";
	// private String KEY_EMAIL = "Email";
	// private String KEY_EMAILDOITAC = "EmailDoiTac";
	// private String KEY_QUANHE = "QuanHe";
	// private String KEY_XACNHAN = "XacNhan";

	private tbl_doitac editDoiTac;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_frm_quanlydoitac);
		final Bundle intent = getIntent().getExtras();

		quaylai = (ImageButton) findViewById(R.id.imgbtnQuayLai_QLDT);
		tieude = (TextView) findViewById(R.id.tvTieuDe_QLDT);
		xong = (ImageButton) findViewById(R.id.imgbtnXong_QLDT);
		email = (EditText) findViewById(R.id.etEmail_QLDT);
		quanhe = (EditText) findViewById(R.id.etQuanHe_QLDT);

		if (intent.getString("chucnang").equals("them")) {
			tieude.setText(intent.getString("tieude"));
		}

		if (intent.getString("chucnang").equals("sua")) {
			editDoiTac = new tbl_doitac();
			tieude.setText(intent.getString("tieude"));
			editDoiTac = (tbl_doitac) intent.getSerializable("objDoiTac_Sua");

			email.setText(editDoiTac.getEmailDoiTac().toString());
			email.setEnabled(false);
			quanhe.setText(editDoiTac.getQuanHe().toString());
		}
		
		if (intent.getString("chucnang").equals("dongyketban")) {
			editDoiTac = new tbl_doitac();
			tieude.setText(intent.getString("tieude"));
			editDoiTac = (tbl_doitac) intent.getSerializable("objDoiTac_DongY");

			email.setText(editDoiTac.getEmailDoiTac().toString());
			email.setEnabled(false);
			quanhe.setText(editDoiTac.getQuanHe().toString());
		}

		xong.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (intent.getString("chucnang").equals("them")) {
					if (!email.getText().toString().equals("")
							&& !quanhe.getText().toString().equals("")) {
						new insertDoiTac().execute();
					} else {
						Toast.makeText(
								getApplicationContext(),
								"Bạn chưa nhập đủ thông tin cần thiết, hãy xem lại thông tin nhập vào.",
								Toast.LENGTH_SHORT).show();
					}
				}

				if (intent.getString("chucnang").equals("sua")) {
					if (!quanhe.getText().toString().equals("")) {
						new updateDoiTac().execute();
					} else {
						Toast.makeText(
								getApplicationContext(),
								"Bạn chưa nhập đủ thông tin cần thiết, hãy xem lại thông tin nhập vào.",
								Toast.LENGTH_SHORT).show();
					}
				}
				
				if (intent.getString("chucnang").equals("dongyketban")) {
					if (!quanhe.getText().toString().equals("")) {
						new insertDoiTacDongY().execute();
					} else {
						Toast.makeText(
								getApplicationContext(),
								"Bạn chưa nhập đủ thông tin cần thiết, hãy xem lại thông tin nhập vào.",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		quaylai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent();
				myIntent.putExtra("chucnang", "them");
				setResult(RESULT_OK, myIntent);
				finish();
			}
		});
	}

	private class insertDoiTac extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_frm_QuanLyDoiTac.this);
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
						ctr_doitac ctrDT = new ctr_doitac();
						tbl_doitac tblDT = new tbl_doitac();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(
								getApplicationContext());

						tblND = serND.selectNguoiDung();

						tblDT.setEmail(tblND.getEmail());
						tblDT.setEmailDoiTac(email.getText().toString());
						tblDT.setQuanHe(quanhe.getText().toString());

						JSONObject objJSON = ctrDT.insertDoiTac(tblDT);
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

							email.setText("");
							quanhe.setText("");

							pDialog.dismiss();
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
								} else {
									if (Integer.parseInt(err) == 3) {
										pDialog.dismiss();
										Toast.makeText(getApplicationContext(),
												thongbao, Toast.LENGTH_SHORT)
												.show();
									}
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

	private class updateDoiTac extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_frm_QuanLyDoiTac.this);
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
						ctr_doitac ctrDT = new ctr_doitac();

						editDoiTac.setQuanHe(quanhe.getText().toString());

						JSONObject objJSON = ctrDT.updateDoiTac(editDoiTac);
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

							email.setText("");
							quanhe.setText("");

							pDialog.dismiss();

							Intent myIntent = new Intent();

							myIntent.putExtra("chucnang", "sua");
							myIntent.putExtra("objDoiTac", editDoiTac);

							setResult(RESULT_OK, myIntent);
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
	
	private class insertDoiTacDongY extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_frm_QuanLyDoiTac.this);
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
						ctr_doitac ctrDT = new ctr_doitac();
						tbl_doitac tblDT = new tbl_doitac();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(
								getApplicationContext());

						tblND = serND.selectNguoiDung();

						tblDT.setEmail(tblND.getEmail());
						tblDT.setEmailDoiTac(editDoiTac.getEmailDoiTac().toString());
						tblDT.setQuanHe(quanhe.getText().toString());

						JSONObject objJSON = ctrDT.insertDoiTacDongY(tblDT);
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

							email.setText("");
							quanhe.setText("");
							pDialog.dismiss();
							
							Intent myIntent = new Intent();
							myIntent.putExtra("chucnang", "dongyketban");

							setResult(RESULT_OK, myIntent);
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
								} else {
									if (Integer.parseInt(err) == 3) {
										pDialog.dismiss();
										Toast.makeText(getApplicationContext(),
												thongbao, Toast.LENGTH_SHORT)
												.show();
									}
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
