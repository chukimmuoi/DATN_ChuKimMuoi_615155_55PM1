package dhxd.chukimmuoi.qltc;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
//import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import dhxd.chukimmuoi.controller.ctr_kehoach;
import dhxd.chukimmuoi.model.tbl_kehoach;
import dhxd.chukimmuoi.model.tbl_nguoidung;
import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.service.ser_nguoidung;
import dhxd.chukimmuoi.utils.Icon_MenuItem;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class View_frm_QuanLyKeHoachNganSach extends FragmentActivity {
	ImageButton xong, quaylai;
	TextView tieude;
	ImageView anhnhom, anhtien;
	EditText tennhom, sotien, ngaythuchien, ngayketthuc, ghichu;

	private static final int REQUEST_CODE = 10;
	DecimalFormat tien = new DecimalFormat("###,###,### đ");
	private tbl_nhom editNhom;
	private String sotientruyenve = null;
	static final int[] Anh = Icon_MenuItem.danhsachanhchon;

	/** NGÀY THÁNG NĂM */
	private CaldroidFragment caldroidFragment;
	private CaldroidFragment dialogCaldroidFragment;
	private Date ngaythangnamthuchien = new Date();
	private Date ngaythangnamketthuc = new Date();
	private boolean congaythang = false;

	private void setCustomResourceForDates() {
		Calendar cal = Calendar.getInstance();

		// TỐI THIỂU HIỂN THỊ 7 NGÀY TRƯỚC.
		cal.add(Calendar.DATE, -18);
		Date blueDate = cal.getTime();

		// TỐI ĐA HIỂN THỊ 7 NGÀY KẾ TIẾP.
		cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 16);
		Date greenDate = cal.getTime();

		if (caldroidFragment != null) {
			caldroidFragment.setBackgroundResourceForDate(R.color.blue,
					blueDate);
			caldroidFragment.setBackgroundResourceForDate(R.color.green,
					greenDate);
			caldroidFragment.setTextColorForDate(R.color.white, blueDate);
			caldroidFragment.setTextColorForDate(R.color.white, greenDate);
		}
	}

	/** ============== */

	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_THONGBAO = "error_msg";

	// private String KEY_KEHOACH_ID = "KeHoach_Id";
	// private String KEY_NHOM_ID = "Nhom_Id";
	// private String KEY_LOAI_ID = "Loai_Id";
	// private String KEY_EMAILDOITAC = "EmailDoiTac";
	// private String KEY_NGAYBATDAU = "NgayBatDau";
	// private String KEY_NGAYKETTHUC = "NgayKetThuc";
	// private String KEY_DIENTA = "DienTa";
	// private String KEY_TRANGTHAI = "TrangThai";
	// private String KEY_LOAIKEHOACH = "LoaiKeHoach";
	// private String KEY_SOTIEN = "SoTien";
	// private String KEY_EMAIL = "Email";
	//
	// private ArrayList<tbl_kehoach> arrTruyenVe;
	// private ArrayList<tbl_nhom> arrNhomTruyenVe;

	private tbl_kehoach editKeHoachNganSachSua;
	private tbl_nhom editNhomSua;

	// private tbl_kehoach editKeHoachTruyen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_frm_quanlykehoachngansach);
		final Bundle intent = getIntent().getExtras();

		// arrTruyenVe = new ArrayList<tbl_kehoach>();
		// arrNhomTruyenVe = new ArrayList<tbl_nhom>();

		xong = (ImageButton) findViewById(R.id.imgbtnXong_QLKHNS);
		quaylai = (ImageButton) findViewById(R.id.imgbtnQuayLai_QLKHNS);
		tieude = (TextView) findViewById(R.id.tvTieuDe_QLKHNS);

		anhnhom = (ImageView) findViewById(R.id.imgviewAnhNhom_QLKHNS);
		anhtien = (ImageView) findViewById(R.id.imgviewAnhTien_QLKHNS);

		tennhom = (EditText) findViewById(R.id.etTenNhom_QLKHNS);
		sotien = (EditText) findViewById(R.id.etSoTien_QLKHNS);
		ngaythuchien = (EditText) findViewById(R.id.etNgayThucHien_QLKHNS);
		ngayketthuc = (EditText) findViewById(R.id.etNgayKetThuc_QLKHNS);
		ghichu = (EditText) findViewById(R.id.etGhiChu_QLKHNS);

		if (intent.getString("chucnang").equals("them")) {
			tieude.setText(intent.getString("tieude"));
			sotien.setText(tien.format(0));
		}

		tennhom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(
						View_frm_QuanLyKeHoachNganSach.this,
						View_frm_DanhSachNhom_Page_Chon.class);
				startActivityForResult(myIntent, REQUEST_CODE);
			}
		});

		sotien.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(
						View_frm_QuanLyKeHoachNganSach.this,
						View_frm_MayTinh.class);
				startActivityForResult(myIntent, REQUEST_CODE);
			}
		});

		/** NGÀY THÁNG NĂM */
		final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		// THIẾT LẬP caldroid fragment.
		// caldroid fragment BÌNH THƯỜNG, KHÔNG CHỈNH SỬA.
		caldroidFragment = new CaldroidFragment();
		setCustomResourceForDates();

		// THIẾT LẬP HÀM LẮNG NGHE SỰ KIỆN.
		final CaldroidListener listener = new CaldroidListener() {
			// KHI CHỌN NGÀY
			@Override
			public void onSelectDate(Date date, View view) {
				if (congaythang == false) {
					ngaythuchien.setText(formatter.format(date));
					ngaythangnamthuchien = date;
				}
				if (congaythang == true) {
					ngayketthuc.setText(formatter.format(date));
					ngaythangnamketthuc = date;
				}
				dialogCaldroidFragment.dismiss();
			}

			// KHI ĐỔI THÁNG
			@Override
			public void onChangeMonth(int month, int year) {
				String text = "Tháng " + month + " năm " + year;
				Toast.makeText(getApplicationContext(), text,
						Toast.LENGTH_SHORT).show();
			}
		};

		// THIẾT LẬP GỌI LỊCH ĐỂ LẮNG NGHE
		caldroidFragment.setCaldroidListener(listener);

		// SHOW DIALOG
		final Bundle state = savedInstanceState;

		ngaythuchien.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				congaythang = false;
				// Thiết lập để sử dụng trượt ngang
				dialogCaldroidFragment = new CaldroidFragment();
				dialogCaldroidFragment.setCaldroidListener(listener);

				// Nếu activity đc gọi khi xoay
				final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
				if (state != null) {
					dialogCaldroidFragment.restoreDialogStatesFromKey(
							getSupportFragmentManager(), state,
							"DIALOG_CALDROID_SAVED_STATE", dialogTag);
					Bundle args = dialogCaldroidFragment.getArguments();
					if (args == null) {
						args = new Bundle();
						dialogCaldroidFragment.setArguments(args);
					}
					args.putString(CaldroidFragment.DIALOG_TITLE,
							"Chọn ngày tháng");
				} else {
					// Cài đặt arguments
					Bundle bundle = new Bundle();
					// tiêu đề cho dialog
					bundle.putString(CaldroidFragment.DIALOG_TITLE,
							"Chọn ngày tháng");
					dialogCaldroidFragment.setArguments(bundle);
				}

				dialogCaldroidFragment.show(getSupportFragmentManager(),
						dialogTag);
			}
		});

		ngayketthuc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				congaythang = true;
				// Thiết lập để sử dụng trượt ngang
				dialogCaldroidFragment = new CaldroidFragment();
				dialogCaldroidFragment.setCaldroidListener(listener);

				// Nếu activity đc gọi khi xoay
				final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
				if (state != null) {
					dialogCaldroidFragment.restoreDialogStatesFromKey(
							getSupportFragmentManager(), state,
							"DIALOG_CALDROID_SAVED_STATE", dialogTag);
					Bundle args = dialogCaldroidFragment.getArguments();
					if (args == null) {
						args = new Bundle();
						dialogCaldroidFragment.setArguments(args);
					}
					args.putString(CaldroidFragment.DIALOG_TITLE,
							"Chọn ngày tháng");
				} else {
					// Cài đặt arguments
					Bundle bundle = new Bundle();
					// tiêu đề cho dialog
					bundle.putString(CaldroidFragment.DIALOG_TITLE,
							"Chọn ngày tháng");
					dialogCaldroidFragment.setArguments(bundle);
				}

				dialogCaldroidFragment.show(getSupportFragmentManager(),
						dialogTag);
			}
		});
		/** ============== */

		xong.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (intent.getString("chucnang").equals("them")) {
					if (!tennhom.getText().toString().equals("")
							&& !sotien.getText().toString().equals("")
							&& !ngaythuchien.getText().toString().equals("")
							&& !ghichu.getText().toString().equals("")) {
						new insertKeHoachNganSach().execute();
					} else {
						Toast.makeText(
								getApplicationContext(),
								"Bạn chưa nhập đủ thông tin cần thiết, hãy xem lại thông tin nhập vào.",
								Toast.LENGTH_SHORT).show();
					}
				}

				if (intent.getString("chucnang").equals("sua")) {
					if (!tennhom.getText().toString().equals("")
							&& !sotien.getText().toString().equals("")
							&& !ngaythuchien.getText().toString().equals("")
							&& !ghichu.getText().toString().equals("")) {
						new updateKeHoachNganSach().execute();
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

		if (intent.getString("chucnang").equals("sua")) {
			tieude.setText(intent.getString("tieude"));
			editKeHoachNganSachSua = new tbl_kehoach();
			editNhomSua = new tbl_nhom();

			editKeHoachNganSachSua = (tbl_kehoach) intent
					.getSerializable("objKeHoachSua");
			editNhomSua = (tbl_nhom) intent.getSerializable("objNhomSua");

			tennhom.setText(editNhomSua.getTenNhom());
			anhnhom.setImageResource(Anh[Integer.parseInt(editNhomSua.getAnh())]);
			sotien.setText(tien.format(editKeHoachNganSachSua.getSoTien()));
			ghichu.setText(editKeHoachNganSachSua.getDienTa());
			ngaythuchien.setText(formatter.format(editKeHoachNganSachSua
					.getNgayBatDau()));
			ngayketthuc.setText(formatter.format(editKeHoachNganSachSua
					.getNgayKetThuc()));

			if (editNhomSua.getLoai_Id().toString().equals("0")) {
				tennhom.setTextColor(Color.parseColor("#006600"));
				sotien.setTextColor(Color.parseColor("#006600"));
				anhtien.setImageResource(R.drawable.ic_thunhaptron);
			}

			if (editNhomSua.getLoai_Id().toString().equals("1")) {
				tennhom.setTextColor(Color.parseColor("#CC0000"));
				sotien.setTextColor(Color.parseColor("#CC0000"));
				anhtien.setImageResource(R.drawable.ic_chitieutron);
			}

			/** SET GIÁ TRỊ */
			editNhom = new tbl_nhom();
			editNhom = editNhomSua;
			sotientruyenve = "" + editKeHoachNganSachSua.getSoTien() + "";
			ngaythangnamthuchien = editKeHoachNganSachSua.getNgayBatDau();
			ngaythangnamketthuc = editKeHoachNganSachSua.getNgayKetThuc();
			/** ============ */
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == -1 && requestCode == REQUEST_CODE) {
			if (data.hasExtra("chucnang")) {
				if (data.getExtras().getString("chucnang").equals("chonnhom")) {
					editNhom = new tbl_nhom();
					editNhom = (tbl_nhom) data.getExtras().getSerializable(
							"objNhomChon");

					tennhom.setText(editNhom.getTenNhom());
					anhnhom.setImageResource(Anh[Integer.parseInt(editNhom
							.getAnh())]);

					if (editNhom.getLoai_Id().toString().equals("0")) {
						tennhom.setTextColor(Color.parseColor("#006600"));
						sotien.setTextColor(Color.parseColor("#006600"));
						anhtien.setImageResource(R.drawable.ic_thunhaptron);
					}

					if (editNhom.getLoai_Id().toString().equals("1")) {
						tennhom.setTextColor(Color.parseColor("#CC0000"));
						sotien.setTextColor(Color.parseColor("#CC0000"));
						anhtien.setImageResource(R.drawable.ic_chitieutron);
					}
				}

				if (data.getExtras().getString("chucnang").equals("quaylai")) {

				}
			} else {
				if (data.getExtras().getString("anbang").equals("true")) {
					sotientruyenve = data.getExtras().getString("sotien");
					sotien.setText(tien.format(Double
							.parseDouble(sotientruyenve)));
				}
			}
		}
	}

	/** NGÀY THÁNG NĂM */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		if (caldroidFragment != null) {
			caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
		}

		if (dialogCaldroidFragment != null) {
			dialogCaldroidFragment.saveStatesToKey(outState,
					"DIALOG_CALDROID_SAVED_STATE");
		}
	}

	/** ============== */

	private class insertKeHoachNganSach extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_frm_QuanLyKeHoachNganSach.this);
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
						ctr_kehoach ctrKH = new ctr_kehoach();
						tbl_kehoach tblKH = new tbl_kehoach();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(
								getApplicationContext());
						tblND = serND.selectNguoiDung();

						tblKH.setNhom_Id(editNhom.getNhom_Id());
						tblKH.setLoai_Id(Integer.parseInt(editNhom.getLoai_Id()));
						tblKH.setEmail(tblND.getEmail());
						tblKH.setNgayBatDau(ngaythangnamthuchien);
						tblKH.setNgayKetThuc(ngaythangnamketthuc);
						tblKH.setDienTa(ghichu.getText().toString());
						tblKH.setSoTien(Double.parseDouble(sotientruyenve));

						JSONObject objJSON = ctrKH.insertKeHoachNganSach(tblKH);
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

							// tbl_kehoach tblKH = new tbl_kehoach();
							// JSONObject objJSON_KH = objJSON
							// .getJSONObject("kehoach");
							// tblKH.setKeHoach_Id(objJSON_KH
							// .getInt(KEY_KEHOACH_ID));
							// tblKH.setNhom_Id(objJSON_KH.getInt(KEY_NHOM_ID));
							// tblKH.setLoai_Id(objJSON_KH.getInt(KEY_LOAI_ID));
							// // tblKH.setVi_Id(objJSON_KH.getInt(KEY_VI_ID));
							// tblKH.setEmailDoiTac(objJSON_KH
							// .getString(KEY_EMAILDOITAC));
							// tblKH.setEmail(objJSON_KH.getString(KEY_EMAIL));
							// final SimpleDateFormat formatngay = new
							// SimpleDateFormat(
							// "yyyy-MM-dd");
							// try {
							// tblKH.setNgayBatDau(formatngay.parse(objJSON_KH
							// .getString(KEY_NGAYBATDAU)));
							// } catch (ParseException e) {
							// e.printStackTrace();
							// }
							//
							// try {
							// tblKH.setNgayKetThuc(formatngay
							// .parse(objJSON_KH
							// .getString(KEY_NGAYKETTHUC)));
							// } catch (Exception e) {
							// }
							// tblKH.setDienTa(objJSON_KH.getString(KEY_DIENTA));
							// tblKH.setSoTien(objJSON_KH.getDouble(KEY_SOTIEN));
							// tblKH.setTrangThai(objJSON_KH.getInt(KEY_TRANGTHAI));
							// tblKH.setLoaiKeHoach(objJSON_KH
							// .getInt(KEY_LOAIKEHOACH));

							// arrTruyenVe.add(tblKH);
							// arrNhomTruyenVe.add(editNhom);

							tennhom.setText("");
							anhnhom.setImageResource(R.drawable.ic_khonganh);
							anhtien.setImageResource(R.drawable.ic_khonganh);
							sotien.setText("");
							ngaythuchien.setText("");
							ngayketthuc.setText("");
							ghichu.setText("");
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

	private class updateKeHoachNganSach extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_frm_QuanLyKeHoachNganSach.this);
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
						ctr_kehoach ctrKH = new ctr_kehoach();
						tbl_kehoach tblKH = new tbl_kehoach();
						// editKeHoachTruyen = new tbl_kehoach();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(
								getApplicationContext());
						tblND = serND.selectNguoiDung();

						tblKH.setKeHoach_Id(editKeHoachNganSachSua
								.getKeHoach_Id());
						tblKH.setNhom_Id(editNhom.getNhom_Id());
						tblKH.setLoai_Id(Integer.parseInt(editNhom.getLoai_Id()));
						tblKH.setEmail(tblND.getEmail());
						tblKH.setNgayBatDau(ngaythangnamthuchien);
						tblKH.setNgayKetThuc(ngaythangnamketthuc);
						tblKH.setDienTa(ghichu.getText().toString());
						tblKH.setSoTien(Double.parseDouble(sotientruyenve));
						// editKeHoachTruyen = tblKH;
						JSONObject objJSON = ctrKH.updateKeHoachNganSach(tblKH);
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
							pDialog.dismiss();

							Intent myIntent = new Intent();
							myIntent.putExtra("chucnang", "sua");
							// myIntent.putExtra("objNhom", editNhom);
							// myIntent.putExtra("objKeHoachSua",
							// editKeHoachTruyen);
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
