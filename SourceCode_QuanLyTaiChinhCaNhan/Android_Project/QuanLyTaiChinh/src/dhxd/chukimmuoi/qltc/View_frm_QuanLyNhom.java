package dhxd.chukimmuoi.qltc;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import dhxd.chukimmuoi.adapter.Custom_Spinner_Loai;
import dhxd.chukimmuoi.controller.ctr_nhom;
import dhxd.chukimmuoi.model.intent_nhom;
import dhxd.chukimmuoi.model.item_spinnerLoai;
import dhxd.chukimmuoi.model.tbl_nguoidung;
import dhxd.chukimmuoi.model.tbl_nhom;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class View_frm_QuanLyNhom extends Activity {
	EditText tennhom;
	ImageButton quaylai, xong;
	ImageView anhnhom;
	Spinner loai;
	TextView tieude;

	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_THONGBAO = "error_msg";

	private static final String KEY_NHOM_ID = "Nhom_Id";
	private static final String KEY_LOAI_ID = "Loai_Id";
	private static final String KEY_TENNHOM = "TenNhom";
	private static final String KEY_ANH = "Anh";
	private static final String KEY_EMAIL = "Email";

	// THÊM MỚI
	int luachon;
	String anhchon = null;
	static final int[] Anh = Icon_MenuItem.danhsachanhchon;
	private static final int REQUEST_CODE = 10;
	private ArrayList<tbl_nhom> arrTruyenVe;
	// SỬA
	private tbl_nhom editNhom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_frm_quanlynhom);
		final Bundle intent = getIntent().getExtras();
		arrTruyenVe = new ArrayList<tbl_nhom>();

		quaylai = (ImageButton) findViewById(R.id.imgbtnQuayLai_QLN);
		xong = (ImageButton) findViewById(R.id.imgbtnXong_QLN);

		tieude = (TextView) findViewById(R.id.tvTieuDe_QLN);

		tennhom = (EditText) findViewById(R.id.etTenNhom_QLN);
		loai = (Spinner) findViewById(R.id.spinnLoai_QLN);
		anhnhom = (ImageView) findViewById(R.id.imgbtnAnhNhom_QLN);

		List<item_spinnerLoai> Loai = new ArrayList<item_spinnerLoai>();
		Loai.add(new item_spinnerLoai(R.drawable.ic_thunhap, "Thu nhập"));
		Loai.add(new item_spinnerLoai(R.drawable.ic_chitieu, "Chi tiêu"));

		Custom_Spinner_Loai adapter = new Custom_Spinner_Loai(
				View_frm_QuanLyNhom.this, R.layout.custom_spinner_loai, Loai);
		loai.setAdapter(adapter);

		loai.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				luachon = arg2;

				// Toast.makeText(getApplicationContext(),""+arg2+"",
				// Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		anhnhom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(View_frm_QuanLyNhom.this,
						View_frm_ChonAnh.class);
				startActivityForResult(myIntent, REQUEST_CODE);
			}
		});

		xong.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (intent.getString("chucnang").equals("them")) {
					if ((anhchon != null)
							&& (!tennhom.getText().toString().equals(""))) {
						new insertNhom().execute();
					} else {
						Toast.makeText(
								getApplicationContext(),
								"Bạn chưa nhập đủ thông tin cần thiết, hãy xem lại thông tin nhập vào.",
								Toast.LENGTH_SHORT).show();
					}
				}

				if (intent.getString("chucnang").equals("sua")) {
					if (anhchon == null) {
						anhchon = editNhom.getAnh();
					}
					if (!tennhom.getText().toString().equals("")) {
						new updateNhom().execute();
					} else {
						Toast.makeText(getApplicationContext(),
								"Bạn chưa nhập tên nhóm thu - chi",
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
				myIntent.putExtra("arrNhom_Them", new intent_nhom(arrTruyenVe));

				setResult(RESULT_OK, myIntent);
				finish();
			}
		});

		if (intent.getString("chucnang").equals("them")) {
			tieude.setText(intent.getString("tieude"));
		}

		if (intent.getString("chucnang").equals("sua")) {
			editNhom = new tbl_nhom();
			tieude.setText(intent.getString("tieude"));
			editNhom = (tbl_nhom) intent.getSerializable("objNhomSua");

			tennhom.setText(editNhom.getTenNhom());
			anhnhom.setImageResource(Anh[Integer.parseInt(editNhom.getAnh())]);
			int spinner = 0;
			if (editNhom.getLoai_Id().equals("0")) {
				spinner = 0;
			} else {
				spinner = 1;
			}
			loai.setSelection(spinner);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			if (data.hasExtra("valuesanh")) {
				anhnhom.setImageResource(Anh[Integer.parseInt(data.getExtras()
						.getString("valuesanh"))]);
				anhchon = data.getExtras().getString("valuesanh");
			}
		}
	}

	private class insertNhom extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_frm_QuanLyNhom.this);
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
						ctr_nhom ctrN = new ctr_nhom();
						tbl_nhom tblN = new tbl_nhom();
						tbl_nguoidung tblND = new tbl_nguoidung();

						ser_nguoidung serND = new ser_nguoidung(
								getApplicationContext());
						tblND = serND.selectNguoiDung();

						tblN.setTenNhom(tennhom.getText().toString());
						tblN.setLoai_Id("" + luachon + "");
						tblN.setAnh(anhchon);
						tblN.setEmail(tblND.getEmail());

						JSONObject objJSON = ctrN.insertNhom(tblN);
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

							// LƯU THAY ĐỔI DỮ LIỆU VÀO MẢNG
							tbl_nhom tblN = new tbl_nhom();
							JSONObject objJSON_N = objJSON
									.getJSONObject("nhom");
							tblN.setNhom_Id(Integer.parseInt(objJSON_N
									.getString(KEY_NHOM_ID)));
							tblN.setTenNhom(objJSON_N.getString(KEY_TENNHOM));
							tblN.setLoai_Id(objJSON_N.getString(KEY_LOAI_ID));
							tblN.setAnh(objJSON_N.getString(KEY_ANH));
							tblN.setEmail(objJSON_N.getString(KEY_EMAIL));

							arrTruyenVe.add(tblN);

							tennhom.setText("");
							anhnhom.setImageResource(R.drawable.ic_khonganh);

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

	private class updateNhom extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_frm_QuanLyNhom.this);
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
						ctr_nhom ctrN = new ctr_nhom();

						editNhom.setTenNhom(tennhom.getText().toString());
						editNhom.setLoai_Id("" + luachon + "");
						editNhom.setAnh(anhchon);

						JSONObject objJSON = ctrN.updateNhom(editNhom);
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

							tennhom.setText("");
							anhnhom.setImageResource(R.drawable.ic_khonganh);

							pDialog.dismiss();
							// Trở về giao diện danh sách nhóm.
							Intent myIntent = new Intent();

							myIntent.putExtra("chucnang", "sua");
							myIntent.putExtra("objNhom", editNhom);

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
