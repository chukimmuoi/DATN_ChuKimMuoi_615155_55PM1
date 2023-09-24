package dhxd.chukimmuoi.qltc;

import java.util.ArrayList;

import dhxd.chukimmuoi.adapter.Custom_Listview_KeHoachNganSachChiTiet;
import dhxd.chukimmuoi.model.intent_giaodich;
import dhxd.chukimmuoi.model.intent_kehoach;
import dhxd.chukimmuoi.model.intent_nhom;
import dhxd.chukimmuoi.model.intent_quy;
import dhxd.chukimmuoi.model.tbl_giaodich;
import dhxd.chukimmuoi.model.tbl_kehoach;
import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.model.tbl_quy;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class View_frm_KeHoachNganSachChiTiet extends Activity {
	ImageButton quaylai;
	TextView tieude;
	ListView danhsachchitiet;
	Custom_Listview_KeHoachNganSachChiTiet adapter;

	private ArrayList<tbl_kehoach> list_tblKeHoach;
	private ArrayList<tbl_giaodich> list_tblGiaoDich;
	private ArrayList<tbl_nhom> list_tblNhom;
	private ArrayList<tbl_quy> list_tblQuy;

	private static final int REQUEST_CODE = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_frm_kehoachngansachchitiet);
		final Bundle intent = getIntent().getExtras();

		quaylai = (ImageButton) findViewById(R.id.imgbtnQuayLai_KHNSCT);
		tieude = (TextView) findViewById(R.id.tvTieuDe_KHNSCT);
		danhsachchitiet = (ListView) findViewById(R.id.danhsach_KHNSCT);
		tieude.setText("CHI TIẾT NGÂN SÁCH");

		if (intent.getString("chucnang").equals("xemchitiet")) {
			intent_kehoach arrKeHoach_NSCT = (intent_kehoach) getIntent()
					.getSerializableExtra("objKeHoachXem");
			intent_nhom arrNhom_NSCT = (intent_nhom) getIntent()
					.getSerializableExtra("objNhomXem");
			intent_quy arrQuy_NSCT = (intent_quy) getIntent()
					.getSerializableExtra("objQuyXem");
			intent_giaodich arrGiaoDich_NSCT = (intent_giaodich) getIntent()
					.getSerializableExtra("objGiaoDichXem");

			ArrayList<tbl_kehoach> arrKeHoach = arrKeHoach_NSCT.getArrKeHoach();
			ArrayList<tbl_nhom> arrNhom = arrNhom_NSCT.getArrNhom();
			ArrayList<tbl_quy> arrQuy = arrQuy_NSCT.getArrQuy();
			ArrayList<tbl_giaodich> arrGiaoDich = arrGiaoDich_NSCT
					.getArrGiaoDich();
			int kehoach_id = intent.getInt("kehoach_id");

			list_tblKeHoach = new ArrayList<tbl_kehoach>();
			list_tblGiaoDich = new ArrayList<tbl_giaodich>();
			list_tblNhom = new ArrayList<tbl_nhom>();
			list_tblQuy = new ArrayList<tbl_quy>();

			tbl_nhom tblN0 = new tbl_nhom();
			tbl_quy tblQ0 = new tbl_quy();
			tbl_giaodich tblGD0 = new tbl_giaodich();
			tbl_kehoach tblKH0 = new tbl_kehoach();

			list_tblKeHoach.add(tblKH0);
			list_tblGiaoDich.add(tblGD0);
			list_tblNhom.add(tblN0);
			list_tblQuy.add(tblQ0);

			for (int i = 0; i < arrKeHoach.size(); i++) {
				if (arrKeHoach.get(i).getKeHoach_Id() == kehoach_id) {
					this.list_tblKeHoach.add(arrKeHoach.get(i));
					this.list_tblGiaoDich.add(arrGiaoDich.get(i));
					this.list_tblNhom.add(arrNhom.get(i));
					this.list_tblQuy.add(arrQuy.get(i));
				}
			}

			adapter = new Custom_Listview_KeHoachNganSachChiTiet(
					View_frm_KeHoachNganSachChiTiet.this,
					R.layout.custom_listview_kehoach_ngansach, list_tblKeHoach,
					list_tblGiaoDich, list_tblNhom, list_tblQuy, kehoach_id);

			danhsachchitiet.setAdapter(adapter);

		}

		quaylai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent();
				myIntent.putExtra("chucnang", "xemchitiet");

				setResult(RESULT_OK, myIntent);
				finish();
			}
		});

		danhsachchitiet.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 != 0) {
					Intent myIntent = new Intent(
							View_frm_KeHoachNganSachChiTiet.this,
							View_frm_QuanLyGiaoDich.class);
					myIntent.putExtra("chucnang", "xemchitiet");
					myIntent.putExtra("tieude", "XEM CHI TIẾT     ");

					myIntent.putExtra("objGiaoDichSua",
							list_tblGiaoDich.get(arg2));
					myIntent.putExtra("objNhomSua", list_tblNhom.get(arg2));
					myIntent.putExtra("objQuySua", list_tblQuy.get(arg2));

					startActivityForResult(myIntent, REQUEST_CODE);
				}
			}
		});
	}

}
