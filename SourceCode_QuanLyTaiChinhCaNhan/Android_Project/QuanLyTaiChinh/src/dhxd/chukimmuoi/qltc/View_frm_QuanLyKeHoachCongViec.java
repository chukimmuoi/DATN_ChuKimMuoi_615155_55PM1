package dhxd.chukimmuoi.qltc;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import dhxd.chukimmuoi.adapter.Custom_Listview_DanhSachQuy;
import dhxd.chukimmuoi.controller.ctr_kehoach;
import dhxd.chukimmuoi.controller.ctr_quy;
import dhxd.chukimmuoi.model.intent_kehoach;
import dhxd.chukimmuoi.model.intent_nhom;
import dhxd.chukimmuoi.model.intent_quy;
import dhxd.chukimmuoi.model.tbl_kehoach;
import dhxd.chukimmuoi.model.tbl_nguoidung;
import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.model.tbl_quy;
import dhxd.chukimmuoi.service.ser_nguoidung;
import dhxd.chukimmuoi.utils.Icon_MenuItem;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("SimpleDateFormat")
public class View_frm_QuanLyKeHoachCongViec extends FragmentActivity {
	ImageButton xong, quaylai;
	TextView tieude;
	ImageView anhnhom, anhtien, anhquy;
	EditText tennhom, sotien, ngaythuchien, ghichu, tenquy;

	DecimalFormat tien = new DecimalFormat("###,###,### đ");
	private static final int REQUEST_CODE = 10;
	private tbl_nhom editNhom;
	private String sotientruyenve = null;
	static final int[] Anh = Icon_MenuItem.danhsachanhchon;

	/** NGÀY THÁNG NĂM */
	private CaldroidFragment caldroidFragment;
	private CaldroidFragment dialogCaldroidFragment;
	private Date ngaythangnam = new Date();

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

	private Dialog dialog;
	Custom_Listview_DanhSachQuy adapterDanhSachQuy;
	ArrayList<tbl_quy> list_Quy;
	tbl_quy quychon = new tbl_quy();
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_THONGBAO = "error_msg";
	private String KEY_QUY_ID = "Quy_Id";
	private String KEY_TENQUY = "TenQuy";
	private String KEY_ANH = "Anh";
	private String KEY_SOTIEN = "SoTien";
	private String KEY_EMAIL = "Email";
	JSONArray arrJSON = null;

	private ArrayList<tbl_kehoach> arrTruyenVe;
	private ArrayList<tbl_nhom> arrNhomTruyenVe;
	private ArrayList<tbl_quy> arrQuyTruyenVe;

	private String KEY_KEHOACH_ID = "KeHoach_Id";
	private String KEY_NHOM_ID = "Nhom_Id";
	private String KEY_LOAI_ID = "Loai_Id";
	private String KEY_VI_ID = "Vi_Id";
	private String KEY_EMAILDOITAC = "EmailDoiTac";
	private String KEY_NGAYBATDAU = "NgayBatDau";
	private String KEY_NGAYKETTHUC = "NgayKetThuc";
	private String KEY_DIENTA = "DienTa";
	private String KEY_TRANGTHAI = "TrangThai";
	private String KEY_LOAIKEHOACH = "LoaiKeHoach";

	private tbl_kehoach editKeHoachCongViecSua;
	private tbl_nhom editNhomSua;
	private tbl_quy editQuySua;

	private tbl_kehoach editKeHoachTruyen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_frm_quanlykehoachcongviec);
		final Bundle intent = getIntent().getExtras();

		arrTruyenVe = new ArrayList<tbl_kehoach>();
		arrNhomTruyenVe = new ArrayList<tbl_nhom>();
		arrQuyTruyenVe = new ArrayList<tbl_quy>();
		xong = (ImageButton) findViewById(R.id.imgbtnXong_QLKHCV);
		quaylai = (ImageButton) findViewById(R.id.imgbtnQuayLai_QLKHCV);
		tieude = (TextView) findViewById(R.id.tvTieuDe_QLKHCV);

		anhnhom = (ImageView) findViewById(R.id.imgviewAnhNhom_QLKHCV);
		anhtien = (ImageView) findViewById(R.id.imgviewAnhTien_QLKHCV);
		anhquy = (ImageView) findViewById(R.id.imgviewAnhQuy_QLKHCV);

		tennhom = (EditText) findViewById(R.id.etTenNhom_QLKHCV);
		sotien = (EditText) findViewById(R.id.etSoTien_QLKHCV);
		ngaythuchien = (EditText) findViewById(R.id.etNgayThucHien_QLKHCV);
		ghichu = (EditText) findViewById(R.id.etGhiChu_QLKHCV);
		tenquy = (EditText) findViewById(R.id.etTenQuy_QLKHCV);

		if (intent.getString("chucnang").equals("them")) {
			tieude.setText(intent.getString("tieude"));
			sotien.setText(tien.format(0));
		}

		try {
			new selectQuy().execute();
		} catch (Exception e) {
		}
		tennhom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(
						View_frm_QuanLyKeHoachCongViec.this,
						View_frm_DanhSachNhom_Page_Chon.class);
				startActivityForResult(myIntent, REQUEST_CODE);
			}
		});

		sotien.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(
						View_frm_QuanLyKeHoachCongViec.this,
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
				ngaythuchien.setText(formatter.format(date));
				ngaythangnam = date;
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

		tenquy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogChonVi();
			}
		});

		xong.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (intent.getString("chucnang").equals("them")) {
					if (!tennhom.getText().toString().equals("")
							&& !sotien.getText().toString().equals("")
							&& !ngaythuchien.getText().toString().equals("")
							&& !ghichu.getText().toString().equals("")
							&& !tenquy.getText().toString().equals("")) {
						new insertKeHoachCongViec().execute();
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
							&& !ghichu.getText().toString().equals("")
							&& !tenquy.getText().toString().equals("")) {
						new updateKeHoachCongViec().execute();
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
				myIntent.putExtra("arrKeHoachCongViec_Them",
						new intent_kehoach(arrTruyenVe));
				myIntent.putExtra("arrNhom_Them", new intent_nhom(
						arrNhomTruyenVe));
				myIntent.putExtra("arrQuy_Them", new intent_quy(arrQuyTruyenVe));
				setResult(RESULT_OK, myIntent);
				finish();
			}
		});

		if (intent.getString("chucnang").equals("sua")) {
			tieude.setText(intent.getString("tieude"));
			editKeHoachCongViecSua = new tbl_kehoach();
			editNhomSua = new tbl_nhom();
			editQuySua = new tbl_quy();

			editKeHoachCongViecSua = (tbl_kehoach) intent
					.getSerializable("objKeHoachCongViecSua");
			editNhomSua = (tbl_nhom) intent.getSerializable("objNhomSua");
			editQuySua = (tbl_quy) intent.getSerializable("objQuySua");

			tennhom.setText(editNhomSua.getTenNhom());
			anhnhom.setImageResource(Anh[Integer.parseInt(editNhomSua.getAnh())]);
			sotien.setText(tien.format(editKeHoachCongViecSua.getSoTien()));
			ghichu.setText(editKeHoachCongViecSua.getDienTa());
			ngaythuchien.setText(formatter.format(editKeHoachCongViecSua
					.getNgayBatDau()));
			tenquy.setText(editQuySua.getTenQuy());
			anhquy.setImageResource(Anh[Integer.parseInt(editQuySua.getAnh())]);

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
			quychon = editQuySua;
			sotientruyenve = "" + editKeHoachCongViecSua.getSoTien() + "";
			ngaythangnam = editKeHoachCongViecSua.getNgayBatDau();
			/** =========== */
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

	public void DialogChonVi() {
		try {
			dialog = new Dialog(View_frm_QuanLyKeHoachCongViec.this);
			dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
			dialog.setContentView(R.layout.custom_dialog_listview);
			dialog.setTitle("Chọn ví");

			ListView dsChucNang = (ListView) dialog
					.findViewById(R.id.lvDSChucNang_CD_Lv);

			adapterDanhSachQuy = new Custom_Listview_DanhSachQuy(
					View_frm_QuanLyKeHoachCongViec.this,
					R.layout.custom_listview_danhsachquy, list_Quy);
			dsChucNang.setAdapter(adapterDanhSachQuy);
			dialog.show();
			dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
					R.drawable.ic_xacnhanxoa);

			dsChucNang.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					try {
						quychon = list_Quy.get(arg2);
						tenquy.setText(quychon.getTenQuy().toString());
						anhquy.setImageResource(Anh[Integer.parseInt(quychon
								.getAnh())]);
						dialog.dismiss();

					} catch (Exception e) {

					}
				}
			});
		} catch (Exception e) {
			Toast.makeText(
					getApplicationContext(),
					"Danh sách quỹ tiền của bạn chưa được load do kết nối mạng bị ngắt.",
					Toast.LENGTH_SHORT).show();
		}
	}

	private class selectQuy extends AsyncTask<String, String, JSONObject> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(String... params) {
			ConnectivityManager cm = (ConnectivityManager) View_frm_QuanLyKeHoachCongViec.this
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
						ctr_quy ctrQ = new ctr_quy();
						tbl_quy tblQ = new tbl_quy();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(
								View_frm_QuanLyKeHoachCongViec.this);

						tblND = serND.selectNguoiDung();

						tblQ.setEmail(tblND.getEmail());

						JSONObject objJSON = ctrQ.selectQuy(tblQ);
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
						if (Integer.parseInt(success) == 1) {
							Toast.makeText(getApplicationContext(),
									"Danh sách ví đã sẵn sàng.",
									Toast.LENGTH_SHORT).show();
							list_Quy = new ArrayList<tbl_quy>();
							arrJSON = objJSON.getJSONArray("tbl_quy");
							for (int i = 0; i < arrJSON.length(); i++) {
								JSONObject c = arrJSON.getJSONObject(i);
								tbl_quy tblQ = new tbl_quy();
								tblQ.setQuy_Id(Integer.parseInt(c
										.getString(KEY_QUY_ID)));
								tblQ.setTenQuy(c.getString(KEY_TENQUY));
								tblQ.setAnh(c.getString(KEY_ANH));
								tblQ.setSoTien(Double.parseDouble(c
										.getString(KEY_SOTIEN)));
								tblQ.setEmail(c.getString(KEY_EMAIL));

								list_Quy.add(tblQ);
							}
						} else {
							if (Integer.parseInt(err) == 1) {
								try {
									new selectQuy().execute();
								} catch (Exception e) {
								}
							}
						}
					} else {
						Toast.makeText(getApplicationContext(),
								"Không có giá trị success trả về.",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					new selectQuy().execute();
					e.printStackTrace();
				}
			} else {
				try {
					new selectQuy().execute();
				} catch (Exception e) {
				}
			}
		}
	}

	private class insertKeHoachCongViec extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_frm_QuanLyKeHoachCongViec.this);
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
						tblKH.setVi_Id(quychon.getQuy_Id());
						tblKH.setEmail(tblND.getEmail());
						tblKH.setNgayBatDau(ngaythangnam);
						tblKH.setDienTa(ghichu.getText().toString());
						tblKH.setSoTien(Double.parseDouble(sotientruyenve));

						JSONObject objJSON = ctrKH.insertKeHoach(tblKH);
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

							tbl_kehoach tblKH = new tbl_kehoach();
							JSONObject objJSON_KH = objJSON
									.getJSONObject("kehoach");
							tblKH.setKeHoach_Id(objJSON_KH
									.getInt(KEY_KEHOACH_ID));
							tblKH.setNhom_Id(objJSON_KH.getInt(KEY_NHOM_ID));
							tblKH.setLoai_Id(objJSON_KH.getInt(KEY_LOAI_ID));
							tblKH.setVi_Id(objJSON_KH.getInt(KEY_VI_ID));
							tblKH.setEmailDoiTac(objJSON_KH
									.getString(KEY_EMAILDOITAC));
							tblKH.setEmail(objJSON_KH.getString(KEY_EMAIL));
							final SimpleDateFormat formatngay = new SimpleDateFormat(
									"yyyy-MM-dd");
							try {
								tblKH.setNgayBatDau(formatngay.parse(objJSON_KH
										.getString(KEY_NGAYBATDAU)));
							} catch (ParseException e) {
								e.printStackTrace();
							}

							try {
								tblKH.setNgayKetThuc(formatngay
										.parse(objJSON_KH
												.getString(KEY_NGAYKETTHUC)));
							} catch (Exception e) {
							}
							tblKH.setDienTa(objJSON_KH.getString(KEY_DIENTA));
							tblKH.setSoTien(objJSON_KH.getDouble(KEY_SOTIEN));
							tblKH.setTrangThai(objJSON_KH.getInt(KEY_TRANGTHAI));
							tblKH.setLoaiKeHoach(objJSON_KH
									.getInt(KEY_LOAIKEHOACH));
							arrTruyenVe.add(tblKH);
							arrNhomTruyenVe.add(editNhom);
							arrQuyTruyenVe.add(quychon);

							tennhom.setText("");
							anhnhom.setImageResource(R.drawable.ic_khonganh);
							anhtien.setImageResource(R.drawable.ic_khonganh);
							sotien.setText("");
							ngaythuchien.setText("");
							ghichu.setText("");
							tenquy.setText("");
							anhquy.setImageResource(R.drawable.ic_khonganh);
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

	private class updateKeHoachCongViec extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_frm_QuanLyKeHoachCongViec.this);
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
						editKeHoachTruyen = new tbl_kehoach();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(
								getApplicationContext());
						tblND = serND.selectNguoiDung();

						tblKH.setKeHoach_Id(editKeHoachCongViecSua
								.getKeHoach_Id());
						tblKH.setNhom_Id(editNhom.getNhom_Id());
						tblKH.setLoai_Id(Integer.parseInt(editNhom.getLoai_Id()));
						tblKH.setVi_Id(quychon.getQuy_Id());
						tblKH.setEmail(tblND.getEmail());
						tblKH.setNgayBatDau(ngaythangnam);
						tblKH.setDienTa(ghichu.getText().toString());
						tblKH.setSoTien(Double.parseDouble(sotientruyenve));
						editKeHoachTruyen = tblKH;
						JSONObject objJSON = ctrKH.updateKeHoach(tblKH);
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
							myIntent.putExtra("objNhom", editNhom);
							myIntent.putExtra("objKeHoachCongViec",
									editKeHoachTruyen);
							myIntent.putExtra("objQuy", quychon);
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
