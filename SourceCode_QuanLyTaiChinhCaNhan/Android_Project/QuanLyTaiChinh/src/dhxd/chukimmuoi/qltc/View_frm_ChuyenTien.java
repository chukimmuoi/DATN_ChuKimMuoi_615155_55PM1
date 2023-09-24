package dhxd.chukimmuoi.qltc;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
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
import dhxd.chukimmuoi.controller.ctr_giaodich;
import dhxd.chukimmuoi.controller.ctr_nhom;
import dhxd.chukimmuoi.controller.ctr_quy;
import dhxd.chukimmuoi.model.tbl_giaodich;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("SimpleDateFormat")
public class View_frm_ChuyenTien extends FragmentActivity {
	ImageView anhnhom;
	EditText tuvi, denvi, sotien, ghichu, tennhom, ngay;
	LinearLayout layoutDenVi, layoutSoTien, layoutChonNhom, layoutNgay;
	ImageButton xong, quaylai;

	DecimalFormat tien = new DecimalFormat("###,###,### đ");
	private String sotientruyenve = null;

	// private tbl_nhom editNhom;
	static final int[] Anh = Icon_MenuItem.danhsachanhchon;

	/** NGÀY THÁNG NĂM */
	private CaldroidFragment caldroidFragment;
	private CaldroidFragment dialogCaldroidFragment;
	
	private Date ngaythangnam;

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

	private String KEY_QUY_ID = "Quy_Id";
	private String KEY_TENQUY = "TenQuy";
	private String KEY_ANH = "Anh";
	private String KEY_SOTIEN = "SoTien";
	private String KEY_EMAIL = "Email";
	
	private static final String KEY_NHOM_ID = "Nhom_Id";
	private static final String KEY_LOAI_ID = "Loai_Id";
	private static final String KEY_TENNHOM = "TenNhom";

	JSONArray arrJSON = null;
	ArrayList<tbl_quy> list_Quy;
	private Dialog dialog;
	Custom_Listview_DanhSachQuy adapterDanhSachQuy;
	tbl_quy quychon = new tbl_quy();

	private tbl_quy quychuyen;
	
	public static ArrayList<tbl_nhom> listNhom;
	
	private tbl_nhom duocthang = new tbl_nhom();
	private tbl_nhom chuyentien  = new tbl_nhom();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_frm_chuyentien);
		final Bundle intent = getIntent().getExtras();

		// anhnhom = (ImageView) findViewById(R.id.imgAnhNhom_CT);
		tuvi = (EditText) findViewById(R.id.etTuVi_CT);
		denvi = (EditText) findViewById(R.id.etDenVi_CT);
		sotien = (EditText) findViewById(R.id.etSoTien_CT);
		// ghichu = (EditText) findViewById(R.id.etGhiChu_CT);
		// tennhom = (EditText) findViewById(R.id.etTenNhom_CT);
		ngay = (EditText) findViewById(R.id.etNgay_CT);
		layoutDenVi = (LinearLayout) findViewById(R.id.linearDenVi_CT);
		layoutSoTien = (LinearLayout) findViewById(R.id.linearSoTien_CT);
		// layoutChonNhom = (LinearLayout) findViewById(R.id.linearChonNhom_CT);
		layoutNgay = (LinearLayout) findViewById(R.id.linearNgay_CT);
		xong = (ImageButton) findViewById(R.id.imgbtnXong_CT);
		quaylai = (ImageButton) findViewById(R.id.imgbtnQuayLai_CT);
		new selectQuy().execute();
		new selectNhom().execute();
		
		denvi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DialogChonVi();
			}
		});

		sotien.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(View_frm_ChuyenTien.this,
						View_frm_MayTinh.class);
				startActivityForResult(myIntent, 9);
			}
		});

		// tennhom.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View arg0) {
		// Intent myIntent = new Intent(View_frm_ChuyenTien.this,
		// View_frm_DanhSachNhom_Page_Chon.class);
		// startActivityForResult(myIntent, 10);
		// }
		// });

		// layoutChonNhom.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View arg0) {
		// Intent myIntent = new Intent(View_frm_ChuyenTien.this,
		// View_frm_DanhSachNhom_Page_Chon.class);
		// startActivityForResult(myIntent, 10);
		// }
		// });

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
				ngay.setText(formatter.format(date));
				ngaythangnam = date;
				dialogCaldroidFragment.dismiss();

			}
		};

		// THIẾT LẬP GỌI LỊCH ĐỂ LẮNG NGHE
		caldroidFragment.setCaldroidListener(listener);

		// SHOW DIALOG
		final Bundle state = savedInstanceState;

		ngay.setOnClickListener(new View.OnClickListener() {
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

		if (intent.getString("chucnang").equals("chuyentien")) {
			quychuyen = new tbl_quy();
			quychuyen = (tbl_quy) intent.getSerializable("objQuyChuyen");
			tuvi.setText(quychuyen.getTenQuy());
		}

		xong.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (intent.getString("chucnang").equals("chuyentien")) {
					if (sotientruyenve == null) {
						sotientruyenve = "0";
					}
					if (!tuvi.getText().toString().equals("")
							&& !denvi.getText().toString().equals("")
							&& !sotien.getText().toString().equals("")
							&& !ngay.getText().toString().equals("")) {
						
						new insertGiaoDich_ChuyenTien().execute();
						
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
				myIntent.putExtra("chucnang", "quaylai");
				setResult(RESULT_OK, myIntent);
				finish();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if (resultCode == -1 && requestCode == 10) {
		// if (data.getExtras().getString("chucnang").equals("chonnhom")) {
		// editNhom = new tbl_nhom();
		// editNhom = (tbl_nhom) data.getExtras().getSerializable(
		// "objNhomChon");
		//
		// tennhom.setText(editNhom.getTenNhom());
		// anhnhom.setImageResource(Anh[Integer.parseInt(editNhom
		// .getAnh())]);
		//
		// if (editNhom.getLoai_Id().toString().equals("0")) {
		// tennhom.setTextColor(Color.parseColor("#006600"));
		// sotien.setTextColor(Color.parseColor("#006600"));
		// }
		//
		// if (editNhom.getLoai_Id().toString().equals("1")) {
		// tennhom.setTextColor(Color.parseColor("#CC0000"));
		// sotien.setTextColor(Color.parseColor("#CC0000"));
		// }
		// }
		// }
		if (resultCode == -1 && requestCode == 9) {
			if (data.getExtras().getString("anbang").equals("true")) {
				sotientruyenve = data.getExtras().getString("sotien");
				sotien.setText(tien.format(Double.parseDouble(sotientruyenve)));
			}
		}
	}

	private class selectQuy extends AsyncTask<String, String, JSONObject> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(String... params) {
			ConnectivityManager cm = (ConnectivityManager) View_frm_ChuyenTien.this
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
								View_frm_ChuyenTien.this);

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
//						String thongbao = objJSON.getString(KEY_THONGBAO);
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

								if (tblQ.getQuy_Id() != quychuyen.getQuy_Id()) {
									list_Quy.add(tblQ);
								}
							}
						} else {
							if (Integer.parseInt(err) == 1) {
								new selectQuy().execute();
//								Toast.makeText(getApplicationContext(),
//										thongbao, Toast.LENGTH_SHORT).show();
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
				new selectQuy().execute();
//				Toast.makeText(getApplicationContext(),
//						"Không có kết nối internet", Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void DialogChonVi() {
		dialog = new Dialog(View_frm_ChuyenTien.this);
		dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
		dialog.setContentView(R.layout.custom_dialog_listview);
		dialog.setTitle("Chọn ví");

		ListView dsChucNang = (ListView) dialog
				.findViewById(R.id.lvDSChucNang_CD_Lv);

		adapterDanhSachQuy = new Custom_Listview_DanhSachQuy(
				View_frm_ChuyenTien.this, R.layout.custom_listview_danhsachquy,
				list_Quy);
		dsChucNang.setAdapter(adapterDanhSachQuy);
		dialog.show();
		dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.ic_xacnhanxoa);

		dsChucNang.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				try {
					quychon = list_Quy.get(arg2);
					denvi.setText(quychon.getTenQuy().toString());
					dialog.dismiss();

				} catch (Exception e) {

				}
			}
		});
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
	private class insertGiaoDich_ChuyenTien extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_frm_ChuyenTien.this);
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
						for (int i = 0; i < listNhom.size(); i++) {
							if(listNhom.get(i).getTenNhom().toString().equals("Được tặng"))
							{
								duocthang = listNhom.get(i);
							}
							if(listNhom.get(i).getTenNhom().toString().equals("Chuyển khoản"))
							{
								chuyentien = listNhom.get(i);
							}
						}
						ctr_giaodich ctrGD = new ctr_giaodich();
						tbl_giaodich tblGD = new tbl_giaodich();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(
								getApplicationContext());
						tblND = serND.selectNguoiDung();
						tblGD.setNhom_Id(chuyentien.getNhom_Id());
						tblGD.setNhom_Id_Phu(duocthang.getNhom_Id());
						tblGD.setSoTien(Double.parseDouble(sotientruyenve));
						tblGD.setGhiChu(denvi.getText().toString());
						tblGD.setGhiChu_Phu(tuvi.getText().toString());
						tblGD.setNgayThang(ngaythangnam);
						tblGD.setQuy_Id(quychuyen.getQuy_Id());
						tblGD.setSoTienQuy(quychuyen.getSoTien());
						tblGD.setQuy_Id_Phu(quychon.getQuy_Id());
						tblGD.setSoTienQuy_Phu(quychon.getSoTien());
						tblGD.setEmail(tblND.getEmail());
						
						JSONObject objJSON = ctrGD.insertGiaoDich_ChuyenTien(tblGD);
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
							// Trở về giao diện danh sách nhóm.
							Intent myIntent = new Intent();
							myIntent.putExtra("chucnang", "chuyentien");
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
	
	private class selectNhom extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
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
						ser_nguoidung serND = new ser_nguoidung(getApplicationContext());
						tblND = serND.selectNguoiDung();
						
						tblN.setEmail(tblND.getEmail());

						JSONObject objJSON = ctrN.selectNhom(tblN);
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
//						String thongbao = objJSON.getString(KEY_THONGBAO);

						if (Integer.parseInt(success) == 1) {
							Toast.makeText(
									getApplicationContext(),
									"Danh sách nhóm đã sẵn sàng.", Toast.LENGTH_SHORT).show();

							listNhom = new ArrayList<tbl_nhom>();

							arrJSON = objJSON.getJSONArray("tbl_nhom");
							for (int i = 0; i < arrJSON.length(); i++) {
								JSONObject c = arrJSON.getJSONObject(i);
								tbl_nhom tblN = new tbl_nhom();

								tblN.setNhom_Id(Integer.parseInt(c
										.getString(KEY_NHOM_ID)));
								tblN.setTenNhom(c.getString(KEY_TENNHOM));
								tblN.setLoai_Id(c.getString(KEY_LOAI_ID));
								tblN.setAnh(c.getString(KEY_ANH));
								tblN.setEmail(c.getString(KEY_EMAIL));

								listNhom.add(tblN);
							}
						} else {
							if (Integer.parseInt(err) == 1) {
								new selectNhom().execute();
//								Toast.makeText(
//										getApplicationContext(),
//										thongbao, Toast.LENGTH_SHORT).show();
							}
						}
					} else {
						pDialog.dismiss();
						Toast.makeText(getApplicationContext(),
								"Không có giá trị success trả về.",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					new selectNhom().execute();
					e.printStackTrace();
				}
			} else {
				new selectNhom().execute();
//				Toast.makeText(getApplicationContext(),
//						"Không có kết nối internet", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
