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
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import dhxd.chukimmuoi.adapter.Custom_Listview_DanhSachQuy;
import dhxd.chukimmuoi.adapter.Custom_Spinner_Loai;
import dhxd.chukimmuoi.controller.ctr_doitac;
import dhxd.chukimmuoi.controller.ctr_giaodich;
import dhxd.chukimmuoi.controller.ctr_quy;
import dhxd.chukimmuoi.model.intent_giaodich;
import dhxd.chukimmuoi.model.intent_nhom;
import dhxd.chukimmuoi.model.intent_quy;
import dhxd.chukimmuoi.model.item_spinnerLoai;
import dhxd.chukimmuoi.model.tbl_doitac;
import dhxd.chukimmuoi.model.tbl_giaodich;
import dhxd.chukimmuoi.model.tbl_kehoach;
import dhxd.chukimmuoi.model.tbl_nguoidung;
import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.model.tbl_quy;
import dhxd.chukimmuoi.service.ser_nguoidung;
import dhxd.chukimmuoi.utils.Icon_MenuItem;
import android.annotation.SuppressLint;
//import android.app.Activity;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint("SimpleDateFormat")
public class View_frm_QuanLyGiaoDich extends FragmentActivity {
	ImageView anhnhom, anhtien;
	EditText tennhom, sotien, ghichu, tienlai, ngaytra;
	Button tinhlai;
	LinearLayout vaymuon, layoutTienLai, layoutHanTraLai, layoutChonVi,
			layoutChonNgayThang;
	AutoCompleteTextView banbe;
	TextView tenquy, tv_thu, tv_ngay, thangnam, tieude;
	Spinner hantralai;
	ImageButton xong, quaylai;
	private static final int REQUEST_CODE = 10;

	static final int[] Anh = Icon_MenuItem.danhsachanhchon;
	private tbl_nhom editNhom;
	DecimalFormat tien = new DecimalFormat("###,###,### đ");
	private String sotientruyenve = null;
	private String sotientruyenvelai = null;
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_THONGBAO = "error_msg";

	private String KEY_EMAILDOITAC = "EmailDoiTac";

	private String KEY_QUY_ID = "Quy_Id";
	private String KEY_TENQUY = "TenQuy";
	private String KEY_ANH = "Anh";
	private String KEY_SOTIEN = "SoTien";
	private String KEY_EMAIL = "Email";
	private String KEY_NGAYTRA = "NgayTra";
	private String KEY_TIENLAI = "TienLai";
	private String KEY_LOAITHULAI = "LoaiThuLai";

	private String KEY_GIAODICH_ID = "GiaoDich_Id";
	private String KEY_NHOM_ID = "Nhom_Id";
	private String KEY_GHICHU = "GhiChu";
	private String KEY_NGAYTHANG = "NgayThang";
	String[] Email;
	ArrayAdapter<String> adapter;

	private Dialog dialog;

	JSONArray arrJSON = null;
	Custom_Listview_DanhSachQuy adapterDanhSachQuy;
	ArrayList<tbl_quy> list_Quy;
	tbl_quy quychon = new tbl_quy();

	int hantralaichon;

	Calendar lich;
	private int year;
	private int month;
	private int day;
	private int rd;
	/** NGÀY THÁNG NĂM */
	private CaldroidFragment caldroidFragment;
	private CaldroidFragment dialogCaldroidFragment;
	private int cochon;

	private Date ngaythangnam = new Date();
	private Date ngaythangnamtra = new Date();

	private ArrayList<tbl_giaodich> arrTruyenVe;
	private ArrayList<tbl_nhom> arrNhomTruyenVe;
	private ArrayList<tbl_quy> arrQuyTruyenVe;

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

	private boolean vaymuonkhonglai = false;
	private boolean vaymuoncolai = false;

	private tbl_kehoach editKeHoachCongViecSua, objKeHoachThem;;
	private tbl_giaodich editGiaoDichSua, objGiaoDichThem;
	private tbl_giaodich objGiaoDich = new tbl_giaodich();
	private tbl_nhom editNhomSua, objNhomThem;
	private tbl_quy editQuySua;
	private Bundle intent;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_frm_quanlygiaodich);
		intent = getIntent().getExtras();
		arrTruyenVe = new ArrayList<tbl_giaodich>();
		arrNhomTruyenVe = new ArrayList<tbl_nhom>();
		arrQuyTruyenVe = new ArrayList<tbl_quy>();
		anhnhom = (ImageView) findViewById(R.id.imgviewAnhNhom_QLGD);
		tennhom = (EditText) findViewById(R.id.etTenNhom_QLGD);
		tinhlai = (Button) findViewById(R.id.btnTinhLai_QLGD);
		vaymuon = (LinearLayout) findViewById(R.id.linearVayMuon_QLGD);
		layoutTienLai = (LinearLayout) findViewById(R.id.linearTienLai_QLGD);
		layoutHanTraLai = (LinearLayout) findViewById(R.id.linearHanTraLai_QLGD);
		layoutChonVi = (LinearLayout) findViewById(R.id.linearlayoutChonVi_QLGD);
		layoutChonNgayThang = (LinearLayout) findViewById(R.id.linearlayoutChonNgayThang_QLGD);
		anhtien = (ImageView) findViewById(R.id.imgviewAnhTien_QLGD);
		sotien = (EditText) findViewById(R.id.etSoTien_QLGD);
		tienlai = (EditText) findViewById(R.id.etTienLai_QLGD);
		ghichu = (EditText) findViewById(R.id.etGhiChu_QLGD);
		banbe = (AutoCompleteTextView) findViewById(R.id.etBanBe_QLGD);
		tenquy = (TextView) findViewById(R.id.tvTenQuy_QLGD);
		hantralai = (Spinner) findViewById(R.id.spinnHanTraLai_QLGD);
		tv_thu = (TextView) findViewById(R.id.tvThu_QLGD);
		tv_ngay = (TextView) findViewById(R.id.tvNgay_QLGD);
		thangnam = (TextView) findViewById(R.id.tvThangNam_QLGD);
		ngaytra = (EditText) findViewById(R.id.etNgayTra_QLGD);
		lich = Calendar.getInstance();
		tieude = (TextView) findViewById(R.id.tvTieuDe_QLGD);

		xong = (ImageButton) findViewById(R.id.imgbtnXong_QLGD);
		quaylai = (ImageButton) findViewById(R.id.imgbtnQuayLai_QLGD);
		/** THÊM GIAO DỊCH */
		if (intent.getString("chucnang").equals("them")) {
			tieude.setText(intent.getString("tieude"));
			xong.setVisibility(View.VISIBLE);
		}

		year = lich.get(Calendar.YEAR);
		month = lich.get(Calendar.MONTH);
		day = lich.get(Calendar.DAY_OF_MONTH);
		rd = lich.get(Calendar.DAY_OF_WEEK);
		if (rd == 1) {
			tv_thu.setText("Chủ nhật");
		} else {
			tv_thu.setText("Thứ " + rd);
		}
		tv_ngay.setText("" + day + "");
		thangnam.setText("Tháng " + (month + 1) + " năm " + year);

		try {
			new selectDoiTac().execute();
			new selectQuy().execute();
		} catch (Exception e) {
		}

		vaymuon.setVisibility(View.GONE);

		tennhom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(View_frm_QuanLyGiaoDich.this,
						View_frm_DanhSachNhom_Page_Chon.class);
				startActivityForResult(myIntent, REQUEST_CODE);
			}
		});

		sotien.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(View_frm_QuanLyGiaoDich.this,
						View_frm_MayTinh.class);
				startActivityForResult(myIntent, REQUEST_CODE);
			}
		});

		tienlai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(View_frm_QuanLyGiaoDich.this,
						View_frm_MayTinh.class);
				startActivityForResult(myIntent, 9);
			}
		});
		tinhlai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (tinhlai.getText().toString().equals("KHÔNG THU LÃI")) {
					layoutTienLai.setVisibility(View.VISIBLE);
					layoutHanTraLai.setVisibility(View.VISIBLE);
					tinhlai.setText("TÍNH LÃI");
					tinhlai.setBackgroundResource(R.color.tab_thunhap);
					vaymuoncolai = true;
				} else {
					if (tinhlai.getText().toString().equals("TÍNH LÃI")) {
						layoutTienLai.setVisibility(View.GONE);
						layoutHanTraLai.setVisibility(View.GONE);
						tinhlai.setText("KHÔNG THU LÃI");
						tinhlai.setBackgroundResource(R.color.tab_chitieu);
						vaymuoncolai = false;
					}
				}
			}
		});

		layoutChonVi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogChonVi();
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
				if (cochon == 1) {
					ngaytra.setText(formatter.format(date));
					ngaythangnamtra = date;
				}
				if (cochon == 0) {
					ngaythangnam = date;
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

			@Override
			public void thu(int thu, int ngay, int thang, int nam) {
				if (cochon == 0) {
					year = nam;
					month = thang;
					day = ngay;
					rd = thu;
					if (rd == 1) {
						tv_thu.setText("Chủ nhật");
					} else {
						tv_thu.setText("Thứ " + rd);
					}
					tv_ngay.setText("" + day + "");
					thangnam.setText("Tháng " + month + " năm " + year);
				}
				dialogCaldroidFragment.dismiss();
			}

		};

		// THIẾT LẬP GỌI LỊCH ĐỂ LẮNG NGHE
		caldroidFragment.setCaldroidListener(listener);

		// SHOW DIALOG
		final Bundle state = savedInstanceState;
		layoutChonNgayThang.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Thiết lập để sử dụng trượt ngang
				dialogCaldroidFragment = new CaldroidFragment();
				dialogCaldroidFragment.setCaldroidListener(listener);
				cochon = 0;

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

		ngaytra.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// Thiết lập để sử dụng trượt ngang
				dialogCaldroidFragment = new CaldroidFragment();
				dialogCaldroidFragment.setCaldroidListener(listener);
				cochon = 1;

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

		List<item_spinnerLoai> Loai = new ArrayList<item_spinnerLoai>();
		Loai.add(new item_spinnerLoai(R.drawable.ic_tuan, "Lãi theo tuần"));
		Loai.add(new item_spinnerLoai(R.drawable.ic_thang, "Lãi theo tháng"));
		Loai.add(new item_spinnerLoai(R.drawable.ic_quyngay, "Lãi theo quý"));
		Loai.add(new item_spinnerLoai(R.drawable.ic_nuanam, "Lãi nửa năm"));
		Loai.add(new item_spinnerLoai(R.drawable.ic_nam, "Lãi theo năm"));

		Custom_Spinner_Loai adapter = new Custom_Spinner_Loai(
				View_frm_QuanLyGiaoDich.this, R.layout.custom_spinner_loai,
				Loai);
		hantralai.setAdapter(adapter);

		hantralai.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				hantralaichon = arg2;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		xong.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					if (!tennhom.getText().toString().equals("")
							&& !sotien.getText().toString().equals("")
							&& !ghichu.getText().toString().equals("")
							&& !tenquy.getText().toString().equals("Chọn ví")) {
						if (banbe.getText().toString().equals("")) {
							if (vaymuonkhonglai) {
								if (!ngaytra.getText().toString().equals("")) {
									if (vaymuoncolai) {
										if (!tienlai.getText().toString()
												.equals("")) {
											new insertGiaoDichVayMuonCoLai()
													.execute();
										} else {
											Toast.makeText(
													getApplicationContext(),
													"Bạn chưa nhập tiền lãi.",
													Toast.LENGTH_SHORT).show();
										}
									} else {
										new insertGiaoDichVayMuon().execute();
									}
								} else {
									Toast.makeText(getApplicationContext(),
											"Bạn chưa nhập ngày trả tiền.",
											Toast.LENGTH_SHORT).show();
								}
							} else {
								new insertGiaoDich().execute();
							}
						} else {
							if (!banbe.getText().toString().equals("")) {
								boolean kiemtra = false;
								try {
									for (int i = 0; i < Email.length; i++) {
										if (Email[i].toString().equals(
												banbe.getText().toString())) {
											kiemtra = true;
										}
									}
								} catch (Exception e) {
								}
								
								if (kiemtra) {
									if (vaymuonkhonglai) {
										if (!ngaytra.getText().toString()
												.equals("")) {
											if (vaymuoncolai) {
												if (!tienlai.getText()
														.toString().equals("")) {
													new insertGiaoDichVayMuonCoLai()
															.execute();
												} else {
													Toast.makeText(
															getApplicationContext(),
															"Bạn chưa nhập tiền lãi.",
															Toast.LENGTH_SHORT)
															.show();
												}
											} else {
												new insertGiaoDichVayMuon()
														.execute();
											}
										} else {
											Toast.makeText(
													getApplicationContext(),
													"Bạn chưa nhập ngày trả tiền.",
													Toast.LENGTH_SHORT).show();
										}
									} else {
										new insertGiaoDich().execute();
									}
								} else {
									banbe.setText("");
									Toast.makeText(
											getApplicationContext(),
											"Email bạn bè chưa được xác nhận, nhập lại email bạn bè của bạn hoặc thực hiện giao dịch không có email bạn bè.",
											Toast.LENGTH_SHORT).show();
								}
							}
						}
					} else {
						Toast.makeText(
								getApplicationContext(),
								"Bạn chưa nhập đủ thông tin cần thiết, hãy xem lại thông tin nhập vào.",
								Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		if (intent.getString("chucnang").equals("thuchienkehoach")) {
			tieude.setText(intent.getString("tieude"));
			editKeHoachCongViecSua = new tbl_kehoach();
			editNhomSua = new tbl_nhom();
			editQuySua = new tbl_quy();

			editKeHoachCongViecSua = (tbl_kehoach) intent
					.getSerializable("objKeHoachCongViecSua");
			editNhomSua = (tbl_nhom) intent.getSerializable("objNhomSua");
			editQuySua = (tbl_quy) intent.getSerializable("objQuySua");

			tennhom.setText(editNhomSua.getTenNhom());
			sotien.setText(tien.format(editKeHoachCongViecSua.getSoTien()));
			ghichu.setText(editKeHoachCongViecSua.getDienTa());
			tenquy.setText(editQuySua.getTenQuy());

			year = editKeHoachCongViecSua.getNgayBatDau().getYear() + 1900;
			month = editKeHoachCongViecSua.getNgayBatDau().getMonth();
			day = editKeHoachCongViecSua.getNgayBatDau().getDate();
			rd = editKeHoachCongViecSua.getNgayBatDau().getDay();

			if (rd == 0) {
				tv_thu.setText("Chủ nhật");
			} else {
				tv_thu.setText("Thứ " + (rd + 1));
			}
			tv_ngay.setText("" + day + "");
			thangnam.setText("Tháng " + (month + 1) + " năm " + year);

			anhnhom.setImageResource(Anh[Integer.parseInt(editNhomSua.getAnh())]);

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

			if (editNhomSua.getTenNhom().toString().equals("Nợ")
					|| editNhomSua.getTenNhom().toString().equals("Cho vay")) {
				vaymuon.setVisibility(View.VISIBLE);
				layoutTienLai.setVisibility(View.GONE);
				layoutHanTraLai.setVisibility(View.GONE);
				vaymuonkhonglai = true;
			} else {
				vaymuon.setVisibility(View.GONE);
				vaymuonkhonglai = false;
			}

			/** SET GIÁ TRỊ */
			editNhom = new tbl_nhom();
			editNhom = editNhomSua;
			quychon = editQuySua;
			sotientruyenve = "" + editKeHoachCongViecSua.getSoTien() + "";
			ngaythangnam = editKeHoachCongViecSua.getNgayBatDau();
			/** =========== */
		}

		/** XÁC NHẬN GIAO DỊCH */
		if (intent.getString("chucnang").equals("xacnhangiaodich")) {
			xong.setVisibility(View.VISIBLE);
			banbe.setEnabled(false);
			tieude.setText(intent.getString("tieude"));
			objGiaoDichThem = new tbl_giaodich();
			objKeHoachThem = new tbl_kehoach();
			objNhomThem = new tbl_nhom();

			objGiaoDichThem = (tbl_giaodich) intent
					.getSerializable("objGiaoDichThem");
			objNhomThem = (tbl_nhom) intent.getSerializable("objNhomThem");
			objKeHoachThem = (tbl_kehoach) intent
					.getSerializable("objKeHoachThem");

			sotien.setText(tien.format(objKeHoachThem.getSoTien()));
			ghichu.setText(objKeHoachThem.getDienTa());
			// banbe.setText(objKeHoachThem.getEmailDoiTac());

			year = objKeHoachThem.getNgayBatDau().getYear() + 1900;
			month = objKeHoachThem.getNgayBatDau().getMonth();
			day = objKeHoachThem.getNgayBatDau().getDate();
			rd = objKeHoachThem.getNgayBatDau().getDay();

			if (rd == 0) {
				tv_thu.setText("Chủ nhật");
			} else {
				tv_thu.setText("Thứ " + (rd + 1));
			}
			tv_ngay.setText("" + day + "");
			thangnam.setText("Tháng " + (month + 1) + " năm " + year);

			/** SET GIÁ TRỊ */
			sotientruyenve = "" + objKeHoachThem.getSoTien() + "";
			ngaythangnam = objKeHoachThem.getNgayBatDau();
			/** =========== */

			if (objKeHoachThem.getNhom_Id() != -1) {
				/** SET GIÁ TRỊ */
				editNhom = new tbl_nhom();
				editNhom = objNhomThem;
				/** ============= */

				tennhom.setText(objNhomThem.getTenNhom());
				anhnhom.setImageResource(Anh[Integer.parseInt(objNhomThem
						.getAnh())]);
				if (objNhomThem.getLoai_Id().toString().equals("0")) {
					tennhom.setTextColor(Color.parseColor("#006600"));
					sotien.setTextColor(Color.parseColor("#006600"));
					anhtien.setImageResource(R.drawable.ic_thunhaptron);
				}

				if (objNhomThem.getLoai_Id().toString().equals("1")) {
					tennhom.setTextColor(Color.parseColor("#CC0000"));
					sotien.setTextColor(Color.parseColor("#CC0000"));
					anhtien.setImageResource(R.drawable.ic_chitieutron);
				}

				if (objNhomThem.getTenNhom().toString().equals("Nợ")
						|| objNhomThem.getTenNhom().toString()
								.equals("Cho vay")) {
					vaymuon.setVisibility(View.VISIBLE);
					layoutTienLai.setVisibility(View.GONE);
					layoutHanTraLai.setVisibility(View.GONE);
					vaymuonkhonglai = true;
					ngaytra.setText(formatter.format(objGiaoDichThem
							.getNgayTra()));
					/** SET GIÁ TRỊ */
					ngaythangnamtra = objGiaoDichThem.getNgayTra();
					/** =========== */
					if (objGiaoDichThem.getTienLai() != 0) {
						layoutTienLai.setVisibility(View.VISIBLE);
						layoutHanTraLai.setVisibility(View.VISIBLE);
						tinhlai.setText("TÍNH LÃI");
						tinhlai.setBackgroundResource(R.color.tab_thunhap);
						tienlai.setText(tien.format(objGiaoDichThem
								.getTienLai()));
						hantralai.setSelection(objGiaoDichThem.getLoaiThuLai());
						/** SET GIÁ TRỊ */
						sotientruyenvelai = "" + objGiaoDichThem.getTienLai()
								+ "";
						hantralaichon = objGiaoDichThem.getLoaiThuLai();
						/** =========== */
						vaymuoncolai = true;
					} else {
						layoutTienLai.setVisibility(View.GONE);
						layoutHanTraLai.setVisibility(View.GONE);
						tinhlai.setText("KHÔNG THU LÃI");
						tinhlai.setBackgroundResource(R.color.tab_chitieu);
						vaymuoncolai = false;
					}
				} else {
					vaymuon.setVisibility(View.GONE);
					vaymuonkhonglai = false;
				}
			}
		}
		/** ================== */

		/** THỰC HIỆN GIAO DỊCH THU TRẢ */
		if (intent.getString("chucnang").equals("thuchiengiaodichthutra")) {
			xong.setVisibility(View.VISIBLE);
			tieude.setText(intent.getString("tieude"));
			objKeHoachThem = new tbl_kehoach();

			objKeHoachThem = (tbl_kehoach) intent
					.getSerializable("objKeHoachThem");

			sotien.setText(tien.format(objKeHoachThem.getSoTien()));
			ghichu.setText(objKeHoachThem.getDienTa());
			// banbe.setText(objKeHoachThem.getEmailDoiTac());

			year = objKeHoachThem.getNgayBatDau().getYear() + 1900;
			month = objKeHoachThem.getNgayBatDau().getMonth();
			day = objKeHoachThem.getNgayBatDau().getDate();
			rd = objKeHoachThem.getNgayBatDau().getDay();

			if (rd == 0) {
				tv_thu.setText("Chủ nhật");
			} else {
				tv_thu.setText("Thứ " + (rd + 1));
			}
			tv_ngay.setText("" + day + "");
			thangnam.setText("Tháng " + (month + 1) + " năm " + year);

			/** SET GIÁ TRỊ */
			sotientruyenve = "" + objKeHoachThem.getSoTien() + "";
			ngaythangnam = objKeHoachThem.getNgayBatDau();
			/** =========== */
		}
		/** =========================== */

		/** XEM CHI TIẾT */
		if (intent.getString("chucnang").equals("xemchitiet")
				|| intent.getString("chucnang").equals("sua")) {
			if (intent.getString("chucnang").equals("xemchitiet")) {
				xong.setVisibility(View.GONE);
			}
			if (intent.getString("chucnang").equals("sua")) {
				xong.setVisibility(View.VISIBLE);
			}
			tieude.setText(intent.getString("tieude"));
			editGiaoDichSua = new tbl_giaodich();
			editNhomSua = new tbl_nhom();
			editQuySua = new tbl_quy();

			editGiaoDichSua = (tbl_giaodich) intent
					.getSerializable("objGiaoDichSua");
			editNhomSua = (tbl_nhom) intent.getSerializable("objNhomSua");
			editQuySua = (tbl_quy) intent.getSerializable("objQuySua");

			tennhom.setText(editNhomSua.getTenNhom());
			sotien.setText(tien.format(editGiaoDichSua.getSoTien()));
			ghichu.setText(editGiaoDichSua.getGhiChu());
			banbe.setText(editGiaoDichSua.getEmailDoiTac());
			tenquy.setText(editQuySua.getTenQuy());

			year = editGiaoDichSua.getNgayThang().getYear() + 1900;
			month = editGiaoDichSua.getNgayThang().getMonth();
			day = editGiaoDichSua.getNgayThang().getDate();
			rd = editGiaoDichSua.getNgayThang().getDay();

			if (rd == 0) {
				tv_thu.setText("Chủ nhật");
			} else {
				tv_thu.setText("Thứ " + (rd + 1));
			}
			tv_ngay.setText("" + day + "");
			thangnam.setText("Tháng " + (month + 1) + " năm " + year);

			anhnhom.setImageResource(Anh[Integer.parseInt(editNhomSua.getAnh())]);

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
			sotientruyenve = "" + editGiaoDichSua.getSoTien() + "";
			ngaythangnam = editGiaoDichSua.getNgayThang();
			/** =========== */

			if (editNhomSua.getTenNhom().toString().equals("Nợ")
					|| editNhomSua.getTenNhom().toString().equals("Cho vay")) {
				vaymuon.setVisibility(View.VISIBLE);
				layoutTienLai.setVisibility(View.GONE);
				layoutHanTraLai.setVisibility(View.GONE);
				vaymuonkhonglai = true;
				ngaytra.setText(formatter.format(editGiaoDichSua.getNgayTra()));
				/** SET GIÁ TRỊ */
				ngaythangnamtra = editGiaoDichSua.getNgayTra();
				/** =========== */
				if (editGiaoDichSua.getTienLai() != 0) {
					layoutTienLai.setVisibility(View.VISIBLE);
					layoutHanTraLai.setVisibility(View.VISIBLE);
					tinhlai.setText("TÍNH LÃI");
					tinhlai.setBackgroundResource(R.color.tab_thunhap);
					tienlai.setText(tien.format(editGiaoDichSua.getTienLai()));
					hantralai.setSelection(editGiaoDichSua.getLoaiThuLai());
					/** SET GIÁ TRỊ */
					sotientruyenvelai = "" + editGiaoDichSua.getTienLai() + "";
					hantralaichon = editGiaoDichSua.getLoaiThuLai();
					/** =========== */
					vaymuoncolai = true;
				} else {
					layoutTienLai.setVisibility(View.GONE);
					layoutHanTraLai.setVisibility(View.GONE);
					tinhlai.setText("KHÔNG THU LÃI");
					tinhlai.setBackgroundResource(R.color.tab_chitieu);
					vaymuoncolai = false;
				}
			} else {
				vaymuon.setVisibility(View.GONE);
				vaymuonkhonglai = false;
			}
		}
		/** ================ */

		quaylai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent();
				myIntent.putExtra("chucnang", "them");
				myIntent.putExtra("arrGiaoDich_Them", new intent_giaodich(
						arrTruyenVe));
				myIntent.putExtra("arrNhom_Them", new intent_nhom(
						arrNhomTruyenVe));
				myIntent.putExtra("arrQuy_Them", new intent_quy(arrQuyTruyenVe));
				setResult(RESULT_OK, myIntent);
				finish();
			}
		});
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

					if (editNhom.getTenNhom().toString().equals("Nợ")
							|| editNhom.getTenNhom().toString()
									.equals("Cho vay")) {
						vaymuon.setVisibility(View.VISIBLE);
						layoutTienLai.setVisibility(View.GONE);
						layoutHanTraLai.setVisibility(View.GONE);
						vaymuonkhonglai = true;
					} else {
						vaymuon.setVisibility(View.GONE);
						vaymuonkhonglai = false;
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

		if (resultCode == -1 && requestCode == 9) {
			if (data.getExtras().getString("anbang").equals("true")) {
				sotientruyenvelai = data.getExtras().getString("sotien");
				tienlai.setText(tien.format(Double
						.parseDouble(sotientruyenvelai)));
			}
		}
	}

	private class selectDoiTac extends AsyncTask<String, String, JSONObject> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(String... params) {
			ConnectivityManager cm = (ConnectivityManager) View_frm_QuanLyGiaoDich.this
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
						tbl_doitac tblDT = new tbl_doitac();
						ctr_doitac ctrDT = new ctr_doitac();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung ser_ND = new ser_nguoidung(
								View_frm_QuanLyGiaoDich.this);

						tblND = ser_ND.selectNguoiDung();
						tblDT.setEmail(tblND.getEmail());

						JSONObject objJSON = ctrDT.selectDoiTac(tblDT);
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
						// String thongbao = objJSON.getString(KEY_THONGBAO);

						if (Integer.parseInt(success) == 1) {
							Toast.makeText(getApplicationContext(),
									"Danh sách bạn bè đã sẵn sàng.",
									Toast.LENGTH_SHORT).show();

							JSONArray arrJSON = objJSON
									.getJSONArray("tbl_doitac");

							Email = new String[arrJSON.length()];

							for (int i = 0; i < arrJSON.length(); i++) {
								JSONObject c = arrJSON.getJSONObject(i);

								Email[i] = c.getString(KEY_EMAILDOITAC);
							}

							adapter = new ArrayAdapter<String>(
									View_frm_QuanLyGiaoDich.this,
									android.R.layout.simple_dropdown_item_1line,
									Email);

							banbe.setAdapter(adapter);

						} else {
							if (Integer.parseInt(err) == 1) {
								// try {
								// new selectDoiTac().execute();
								// } catch (Exception e) {
								// }
								// Toast.makeText(getApplicationContext(),
								// thongbao, Toast.LENGTH_SHORT).show();
							}
						}
					} else {
						Toast.makeText(getApplicationContext(),
								"Không có giá trị success trả về.",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// new selectDoiTac().execute();
					e.printStackTrace();
				}
			} else {
				// try {
				// new selectDoiTac().execute();
				// } catch (Exception e) {
				// }
				// Toast.makeText(getApplicationContext(),
				// "Không có kết nối internet", Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void DialogChonVi() {
		try {
			dialog = new Dialog(View_frm_QuanLyGiaoDich.this);
			dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
			dialog.setContentView(R.layout.custom_dialog_listview);
			dialog.setTitle("Chọn ví");

			ListView dsChucNang = (ListView) dialog
					.findViewById(R.id.lvDSChucNang_CD_Lv);

			adapterDanhSachQuy = new Custom_Listview_DanhSachQuy(
					View_frm_QuanLyGiaoDich.this,
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
						if (editNhom.getLoai_Id().toString().equals("0")) {
							tenquy.setText(quychon.getTenQuy().toString());
							dialog.dismiss();
						}
						if (editNhom.getLoai_Id().toString().equals("1")) {
							if (quychon.getSoTien() >= Double
									.parseDouble(sotientruyenve)) {
								tenquy.setText(quychon.getTenQuy().toString());
								dialog.dismiss();
							} else {
								dialog.dismiss();
								DialogChuyenQuy();
							}
						}
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

	public void DialogChuyenQuy() {
		dialog = new Dialog(View_frm_QuanLyGiaoDich.this);
		dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
		dialog.setContentView(R.layout.custom_dialog_chuyenquy);
		dialog.setTitle("Cảnh báo");

		Button tieptuc = (Button) dialog.findViewById(R.id.btnTiepTuc_CD_CQ);
		Button huybo = (Button) dialog.findViewById(R.id.btnHuyBo_CD_CQ);
		dialog.show();
		dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.ic_xacnhanxoa);

		tieptuc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tenquy.setText(quychon.getTenQuy().toString());
				dialog.dismiss();
			}
		});

		huybo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	private class selectQuy extends AsyncTask<String, String, JSONObject> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(String... params) {
			ConnectivityManager cm = (ConnectivityManager) View_frm_QuanLyGiaoDich.this
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
								View_frm_QuanLyGiaoDich.this);

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
						// String thongbao = objJSON.getString(KEY_THONGBAO);
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
								// try {
								// new selectQuy().execute();
								// } catch (Exception e) {
								// }
								// Toast.makeText(getApplicationContext(),
								// thongbao, Toast.LENGTH_SHORT).show();
							}
						}
					} else {
						Toast.makeText(getApplicationContext(),
								"Không có giá trị success trả về.",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// new selectQuy().execute();
					e.printStackTrace();
				}
			} else {
				// try {
				// new selectQuy().execute();
				// } catch (Exception e) {
				// }
				// Toast.makeText(getApplicationContext(),
				// "Không có kết nối internet", Toast.LENGTH_SHORT).show();
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

	private class insertGiaoDich extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_frm_QuanLyGiaoDich.this);
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
						ctr_giaodich ctrGD = new ctr_giaodich();
						tbl_giaodich tblGD = new tbl_giaodich();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(
								getApplicationContext());
						tblND = serND.selectNguoiDung();

						tblGD.setNhom_Id(editNhom.getNhom_Id());
						tblGD.setSoTien(Double.parseDouble(sotientruyenve));
						tblGD.setGhiChu(ghichu.getText().toString());
						tblGD.setEmailDoiTac(banbe.getText().toString());
						tblGD.setNgayThang(ngaythangnam);
						tblGD.setQuy_Id(quychon.getQuy_Id());
						tblGD.setSoTienQuy(quychon.getSoTien());
						tblGD.setEmail(tblND.getEmail());
						tblGD.setGiaoDich_Id(Integer.parseInt(editNhom
								.getLoai_Id()));
						JSONObject objJSON = ctrGD.insertGiaoDich(tblGD);
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
							if (intent.getString("chucnang").equals("them")) {
								Toast.makeText(getApplicationContext(),
										thongbao, Toast.LENGTH_SHORT).show();
							}

							// LƯU THAY ĐỔI DỮ LIỆU VÀO MẢNG
							tbl_giaodich tblGD = new tbl_giaodich();
							JSONObject objJSON_GD = objJSON
									.getJSONObject("giaodich");
							tblGD.setGiaoDich_Id(Integer.parseInt(objJSON_GD
									.getString(KEY_GIAODICH_ID)));
							tblGD.setNhom_Id(Integer.parseInt(objJSON_GD
									.getString(KEY_NHOM_ID)));
							tblGD.setSoTien(Double.parseDouble(objJSON_GD
									.getString(KEY_SOTIEN)));
							tblGD.setGhiChu(objJSON_GD.getString(KEY_GHICHU));
							tblGD.setEmailDoiTac(objJSON_GD
									.getString(KEY_EMAILDOITAC));
							final SimpleDateFormat formatngay = new SimpleDateFormat(
									"yyyy-MM-dd");
							try {
								tblGD.setNgayThang(formatngay.parse(objJSON_GD
										.getString(KEY_NGAYTHANG)));
							} catch (Exception e) {
							}
							tblGD.setQuy_Id(Integer.parseInt(objJSON_GD
									.getString(KEY_QUY_ID)));
							tblGD.setEmail(objJSON_GD.getString(KEY_EMAIL));
							try {
								tblGD.setNgayTra(formatngay.parse(objJSON_GD
										.getString(KEY_NGAYTRA)));
							} catch (Exception e) {
							}
							tblGD.setTienLai(objJSON_GD.getDouble(KEY_TIENLAI));
							tblGD.setLoaiThuLai(objJSON_GD
									.getInt(KEY_LOAITHULAI));
							quychon.setSoTien(objJSON_GD.getDouble("SoTienQuy"));
							objGiaoDich = tblGD;
							arrTruyenVe.add(tblGD);
							arrNhomTruyenVe.add(editNhom);
							arrQuyTruyenVe.add(quychon);

							tennhom.setText("");
							anhnhom.setImageResource(R.drawable.ic_khonganh);
							anhtien.setImageResource(R.drawable.ic_khonganh);
							sotien.setText("");
							ghichu.setText("");
							banbe.setText("");
							tenquy.setText("Chọn ví");
							pDialog.dismiss();
							if (intent.getString("chucnang").equals("sua")) {
								new deleteGiaoDich().execute();
							}
							if (intent.getString("chucnang").equals(
									"thuchienkehoach")) {
								Intent myIntent = new Intent();
								myIntent.putExtra("chucnang", "thuchienkehoach");
								setResult(RESULT_OK, myIntent);
								finish();
							}
							if (intent.getString("chucnang").equals(
									"xacnhangiaodich")) {
								Intent myIntent = new Intent();
								myIntent.putExtra("chucnang", "xacnhangiaodich");
								myIntent.putExtra("giaodich_id",
										objJSON_GD.getString(KEY_GIAODICH_ID));
								setResult(RESULT_OK, myIntent);
								finish();
							}
							if (intent.getString("chucnang").equals(
									"thuchiengiaodichthutra")) {
								Intent myIntent = new Intent();
								myIntent.putExtra("chucnang",
										"thuchiengiaodichthutra");
								setResult(RESULT_OK, myIntent);
								finish();
							}
							try {
								new selectQuy().execute();
							} catch (Exception e) {
							}
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

	private class insertGiaoDichVayMuon extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_frm_QuanLyGiaoDich.this);
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
						ctr_giaodich ctrGD = new ctr_giaodich();
						tbl_giaodich tblGD = new tbl_giaodich();
						tbl_kehoach tblKH = new tbl_kehoach();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(
								getApplicationContext());
						tblND = serND.selectNguoiDung();

						tblGD.setNhom_Id(editNhom.getNhom_Id());
						tblGD.setSoTien(Double.parseDouble(sotientruyenve));
						tblGD.setGhiChu(ghichu.getText().toString());
						tblGD.setEmailDoiTac(banbe.getText().toString());
						tblGD.setNgayThang(ngaythangnam);
						tblGD.setQuy_Id(quychon.getQuy_Id());
						tblGD.setSoTienQuy(quychon.getSoTien());
						tblGD.setEmail(tblND.getEmail());
						tblGD.setGiaoDich_Id(Integer.parseInt(editNhom
								.getLoai_Id()));
						tblKH.setNgayBatDau(ngaythangnamtra);
						JSONObject objJSON = ctrGD.insertGiaoDichVayMuon(tblGD,
								tblKH);
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
							if (intent.getString("chucnang").equals("them")) {
								Toast.makeText(getApplicationContext(),
										thongbao, Toast.LENGTH_SHORT).show();
							}

							// LƯU THAY ĐỔI DỮ LIỆU VÀO MẢNG
							tbl_giaodich tblGD = new tbl_giaodich();
							JSONObject objJSON_GD = objJSON
									.getJSONObject("giaodich");
							tblGD.setGiaoDich_Id(Integer.parseInt(objJSON_GD
									.getString(KEY_GIAODICH_ID)));
							tblGD.setNhom_Id(Integer.parseInt(objJSON_GD
									.getString(KEY_NHOM_ID)));
							tblGD.setSoTien(Double.parseDouble(objJSON_GD
									.getString(KEY_SOTIEN)));
							tblGD.setGhiChu(objJSON_GD.getString(KEY_GHICHU));
							tblGD.setEmailDoiTac(objJSON_GD
									.getString(KEY_EMAILDOITAC));
							final SimpleDateFormat formatngay = new SimpleDateFormat(
									"yyyy-MM-dd");
							try {
								tblGD.setNgayThang(formatngay.parse(objJSON_GD
										.getString(KEY_NGAYTHANG)));
							} catch (Exception e) {
							}
							tblGD.setQuy_Id(Integer.parseInt(objJSON_GD
									.getString(KEY_QUY_ID)));
							tblGD.setEmail(objJSON_GD.getString(KEY_EMAIL));
							try {
								tblGD.setNgayTra(formatngay.parse(objJSON_GD
										.getString(KEY_NGAYTRA)));
							} catch (Exception e) {
							}
							tblGD.setTienLai(objJSON_GD.getDouble(KEY_TIENLAI));
							tblGD.setLoaiThuLai(objJSON_GD
									.getInt(KEY_LOAITHULAI));
							quychon.setSoTien(objJSON_GD.getDouble("SoTienQuy"));

							objGiaoDich = tblGD;
							arrTruyenVe.add(tblGD);
							arrNhomTruyenVe.add(editNhom);
							arrQuyTruyenVe.add(quychon);

							tennhom.setText("");
							anhnhom.setImageResource(R.drawable.ic_khonganh);
							anhtien.setImageResource(R.drawable.ic_khonganh);
							sotien.setText("");
							ghichu.setText("");
							banbe.setText("");
							ngaytra.setText("");
							tenquy.setText("Chọn ví");
							pDialog.dismiss();
							if (intent.getString("chucnang").equals("sua")) {
								new deleteGiaoDich().execute();
							}
							if (intent.getString("chucnang").equals(
									"thuchienkehoach")) {
								Intent myIntent = new Intent();
								myIntent.putExtra("chucnang", "thuchienkehoach");
								setResult(RESULT_OK, myIntent);
								finish();
							}
							if (intent.getString("chucnang").equals(
									"xacnhangiaodich")) {
								Intent myIntent = new Intent();
								myIntent.putExtra("chucnang", "xacnhangiaodich");
								myIntent.putExtra("giaodich_id",
										objJSON_GD.getString(KEY_GIAODICH_ID));
								setResult(RESULT_OK, myIntent);
								finish();
							}
							try {
								new selectQuy().execute();
							} catch (Exception e) {
							}
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

	private class insertGiaoDichVayMuonCoLai extends
			AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_frm_QuanLyGiaoDich.this);
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
						ctr_giaodich ctrGD = new ctr_giaodich();
						tbl_giaodich tblGD = new tbl_giaodich();
						tbl_kehoach tblKH = new tbl_kehoach();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(
								getApplicationContext());
						tblND = serND.selectNguoiDung();

						tblGD.setNhom_Id(editNhom.getNhom_Id());
						tblGD.setSoTien(Double.parseDouble(sotientruyenve));
						tblGD.setGhiChu(ghichu.getText().toString());
						tblGD.setEmailDoiTac(banbe.getText().toString());
						tblGD.setNgayThang(ngaythangnam);
						tblGD.setQuy_Id(quychon.getQuy_Id());
						tblGD.setSoTienQuy(quychon.getSoTien());
						tblGD.setEmail(tblND.getEmail());
						tblGD.setGiaoDich_Id(Integer.parseInt(editNhom
								.getLoai_Id()));
						tblKH.setNgayBatDau(ngaythangnamtra);
						tblKH.setSoTien(Double.parseDouble(sotientruyenvelai));
						tblKH.setLoai_Id(hantralaichon);
						JSONObject objJSON = ctrGD.insertGiaoDichVayMuonCoLai(
								tblGD, tblKH);
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
							if (intent.getString("chucnang").equals("them")) {
								Toast.makeText(getApplicationContext(),
										thongbao, Toast.LENGTH_SHORT).show();
							}

							// LƯU THAY ĐỔI DỮ LIỆU VÀO MẢNG
							tbl_giaodich tblGD = new tbl_giaodich();
							JSONObject objJSON_GD = objJSON
									.getJSONObject("giaodich");
							tblGD.setGiaoDich_Id(Integer.parseInt(objJSON_GD
									.getString(KEY_GIAODICH_ID)));
							tblGD.setNhom_Id(Integer.parseInt(objJSON_GD
									.getString(KEY_NHOM_ID)));
							tblGD.setSoTien(Double.parseDouble(objJSON_GD
									.getString(KEY_SOTIEN)));
							tblGD.setGhiChu(objJSON_GD.getString(KEY_GHICHU));
							tblGD.setEmailDoiTac(objJSON_GD
									.getString(KEY_EMAILDOITAC));
							final SimpleDateFormat formatngay = new SimpleDateFormat(
									"yyyy-MM-dd");
							try {
								tblGD.setNgayThang(formatngay.parse(objJSON_GD
										.getString(KEY_NGAYTHANG)));
							} catch (Exception e) {
							}
							tblGD.setQuy_Id(Integer.parseInt(objJSON_GD
									.getString(KEY_QUY_ID)));
							tblGD.setEmail(objJSON_GD.getString(KEY_EMAIL));
							try {
								tblGD.setNgayTra(formatngay.parse(objJSON_GD
										.getString(KEY_NGAYTRA)));
							} catch (Exception e) {
							}
							tblGD.setTienLai(objJSON_GD.getDouble(KEY_TIENLAI));
							tblGD.setLoaiThuLai(objJSON_GD
									.getInt(KEY_LOAITHULAI));
							quychon.setSoTien(objJSON_GD.getDouble("SoTienQuy"));

							objGiaoDich = tblGD;
							arrTruyenVe.add(tblGD);
							arrNhomTruyenVe.add(editNhom);
							arrQuyTruyenVe.add(quychon);

							tennhom.setText("");
							anhnhom.setImageResource(R.drawable.ic_khonganh);
							anhtien.setImageResource(R.drawable.ic_khonganh);
							sotien.setText("");
							ghichu.setText("");
							banbe.setText("");
							ngaytra.setText("");
							tienlai.setText("");
							tenquy.setText("Chọn ví");
							pDialog.dismiss();

							if (intent.getString("chucnang").equals("sua")) {
								new deleteGiaoDich().execute();
							}
							if (intent.getString("chucnang").equals(
									"thuchienkehoach")) {
								Intent myIntent = new Intent();
								myIntent.putExtra("chucnang", "thuchienkehoach");
								setResult(RESULT_OK, myIntent);
								finish();
							}
							if (intent.getString("chucnang").equals(
									"xacnhangiaodich")) {
								Intent myIntent = new Intent();
								myIntent.putExtra("chucnang", "xacnhangiaodich");
								myIntent.putExtra("giaodich_id",
										objJSON_GD.getString(KEY_GIAODICH_ID));
								setResult(RESULT_OK, myIntent);
								finish();
							}
							try {
								new selectQuy().execute();
							} catch (Exception e) {
							}
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

	private class deleteGiaoDich extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_frm_QuanLyGiaoDich.this);
			// pDialog.setTitle("Kết nối đến máy chủ");
			// pDialog.setMessage("Đang kết nối đến máy chủ ...");
			// pDialog.setIndeterminate(false);
			// pDialog.setCancelable(true);
			// pDialog.show();
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
						ctr_giaodich ctrGD = new ctr_giaodich();
						tbl_giaodich tblGD = new tbl_giaodich();
						tbl_nhom tblN = new tbl_nhom();
						tbl_quy tblQ = new tbl_quy();
						tbl_nguoidung tblND = new tbl_nguoidung();
						ser_nguoidung serND = new ser_nguoidung(
								getApplicationContext());
						tblND = serND.selectNguoiDung();

						tblGD.setGiaoDich_Id(editGiaoDichSua.getGiaoDich_Id());
						tblGD.setSoTien(editGiaoDichSua.getSoTien());
						tblGD.setQuy_Id(editGiaoDichSua.getQuy_Id());
						tblN.setLoai_Id(editNhomSua.getLoai_Id());
						tblQ.setSoTien(editQuySua.getSoTien());
						if (editQuySua.getQuy_Id() == quychon.getQuy_Id()) {
							tblQ.setSoTien(quychon.getSoTien());
						}

						tblGD.setEmail(tblND.getEmail());
						JSONObject objJSON = ctrGD.deleteGiaoDich(tblGD, tblN,
								tblQ);
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
							Toast.makeText(getApplicationContext(),
									"Sửa dữ liệu giao dịch thành công.",
									Toast.LENGTH_SHORT).show();
							quychon.setSoTien(objJSON.getDouble("SoTienQuy"));
							pDialog.dismiss();

							Intent myIntent = new Intent();
							myIntent.putExtra("chucnang", "sua");
							myIntent.putExtra("objNhom", editNhom);
							myIntent.putExtra("objGiaoDich", objGiaoDich);
							myIntent.putExtra("objQuy", quychon);
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
