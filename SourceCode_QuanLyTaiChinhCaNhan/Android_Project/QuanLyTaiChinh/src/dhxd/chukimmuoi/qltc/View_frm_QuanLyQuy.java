package dhxd.chukimmuoi.qltc;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import dhxd.chukimmuoi.controller.ctr_quy;
import dhxd.chukimmuoi.model.intent_quy;
import dhxd.chukimmuoi.model.tbl_nguoidung;
import dhxd.chukimmuoi.model.tbl_quy;
import dhxd.chukimmuoi.service.ser_nguoidung;
import dhxd.chukimmuoi.utils.Icon_MenuItem;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class View_frm_QuanLyQuy extends Activity {
	ImageButton quaylai, xong;
	TextView tieude;
	ImageView anhquy;
	EditText tenquy, sotien;

	String anhchon = null;
	static final int[] Anh = Icon_MenuItem.danhsachanhchon;
	private static final int REQUEST_CODE = 10;
	private ArrayList<tbl_quy> arrTruyenVe;

	private String KEY_SUCCESS = "success";
	private String KEY_ERROR = "error";
	private String KEY_THONGBAO = "error_msg";

	private String KEY_QUY_ID = "Quy_Id";
	private String KEY_TENQUY = "TenQuy";
	private String KEY_ANH = "Anh";
	private String KEY_SOTIEN = "SoTien";
	private String KEY_EMAIL = "Email";

	private tbl_quy editQuy;
	DecimalFormat tien = new DecimalFormat("###,###,### đ");

	private String sotientruyenve = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_frm_quanlyquy);
		final Bundle intent = getIntent().getExtras();
		arrTruyenVe = new ArrayList<tbl_quy>();

		quaylai = (ImageButton) findViewById(R.id.imgQuaLai_QLQ);
		xong = (ImageButton) findViewById(R.id.imgXong_QLQ);
		tieude = (TextView) findViewById(R.id.tvTieuDe_QLQ);
		anhquy = (ImageView) findViewById(R.id.imgAnh_QLQ);
		tenquy = (EditText) findViewById(R.id.etTenQuy_QLQ);
		sotien = (EditText) findViewById(R.id.etSoTien_QLQ);

		if (intent.getString("chucnang").equals("them")) {
			tieude.setText(intent.getString("tieude"));
			sotien.setText(tien.format(0));
		}

		if (intent.getString("chucnang").equals("sua")) {
			editQuy = new tbl_quy();
			tieude.setText(intent.getString("tieude"));
			editQuy = (tbl_quy) intent.getSerializable("objQuySua");

			tenquy.setText(editQuy.getTenQuy());
			sotien.setText(tien.format(editQuy.getSoTien()));

			anhquy.setImageResource(Anh[Integer.parseInt(editQuy.getAnh())]);
		}

		anhquy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(View_frm_QuanLyQuy.this,
						View_frm_ChonAnh.class);
				startActivityForResult(myIntent, REQUEST_CODE);
			}
		});
		xong.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (intent.getString("chucnang").equals("them")) {
					if (anhchon == null) {
						anhchon = "108";
					}
					if (sotientruyenve == null) {
						sotientruyenve = "0";
					}
					if (!tenquy.getText().toString().equals("")
							&& !sotien.getText().toString().equals("")) {
						new insertQuy().execute();
					} else {
						Toast.makeText(
								getApplicationContext(),
								"Bạn chưa nhập đủ thông tin cần thiết, hãy xem lại thông tin nhập vào.",
								Toast.LENGTH_SHORT).show();
					}
				}

				if (intent.getString("chucnang").equals("sua")) {
					if (anhchon == null) {
						anhchon = editQuy.getAnh();
					}
					if (sotientruyenve == null) {
						sotientruyenve = "" + editQuy.getSoTien() + "";
					}
					if (!tenquy.getText().toString().equals("")
							&& !sotien.getText().toString().equals("")) {
						new updateQuy().execute();
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
			public void onClick(View v) {
				Intent myIntent = new Intent();

				myIntent.putExtra("chucnang", "them");
				myIntent.putExtra("arrQuy_Them", new intent_quy(arrTruyenVe));

				setResult(RESULT_OK, myIntent);
				finish();
			}
		});

		sotien.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(View_frm_QuanLyQuy.this,
						View_frm_MayTinh.class);
				startActivityForResult(myIntent, REQUEST_CODE);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
				if (data.hasExtra("valuesanh")) {
					anhquy.setImageResource(Anh[Integer.parseInt(data.getExtras()
							.getString("valuesanh"))]);
					anhchon = data.getExtras().getString("valuesanh");
				} else {
					if (data.getExtras().getString("anbang").equals("true")) {
						sotientruyenve = data.getExtras().getString("sotien");
						sotien.setText(tien.format(Double
								.parseDouble(sotientruyenve)));
					}
				}
			}
		} catch (Exception e) {
		}
	}

	private class insertQuy extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_frm_QuanLyQuy.this);
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
						ctr_quy ctrQ = new ctr_quy();
						tbl_quy tblQ = new tbl_quy();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(
								getApplicationContext());

						tblND = serND.selectNguoiDung();

						tblQ.setTenQuy(tenquy.getText().toString());
						tblQ.setAnh(anhchon);
						tblQ.setSoTien(Double.parseDouble(sotientruyenve));
						tblQ.setEmail(tblND.getEmail());

						JSONObject objJSON = ctrQ.insertQuy(tblQ);
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
							tbl_quy tblQ = new tbl_quy();
							JSONObject objJSON_Q = objJSON.getJSONObject("quy");
							tblQ.setQuy_Id(Integer.parseInt(objJSON_Q
									.getString(KEY_QUY_ID)));
							tblQ.setTenQuy(objJSON_Q.getString(KEY_TENQUY));
							tblQ.setAnh(objJSON_Q.getString(KEY_ANH));
							tblQ.setSoTien(Double.parseDouble(objJSON_Q
									.getString(KEY_SOTIEN)));
							tblQ.setEmail(objJSON_Q.getString(KEY_EMAIL));

							arrTruyenVe.add(tblQ);

							tenquy.setText("");
							sotien.setText("0 đ");
							anhquy.setImageResource(R.drawable.icon_109);

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

	private class updateQuy extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_frm_QuanLyQuy.this);
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
						ctr_quy ctrQ = new ctr_quy();
						editQuy.setTenQuy(tenquy.getText().toString());
						editQuy.setSoTien(Double.parseDouble(sotientruyenve));
						editQuy.setAnh(anhchon);

						JSONObject objJSON = ctrQ.updateQuy(editQuy);
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

							tenquy.setText("");
							sotien.setText("");
							anhquy.setImageResource(R.drawable.icon_109);

							pDialog.dismiss();
							// Trở về giao diện danh sách nhóm.
							Intent myIntent = new Intent();

							myIntent.putExtra("chucnang", "sua");
							myIntent.putExtra("objQuy", editQuy);

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
}
