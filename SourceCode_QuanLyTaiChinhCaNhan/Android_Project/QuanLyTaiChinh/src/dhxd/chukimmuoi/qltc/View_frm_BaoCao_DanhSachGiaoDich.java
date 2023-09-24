package dhxd.chukimmuoi.qltc;

import java.util.ArrayList;

import dhxd.chukimmuoi.adapter.Custom_Listview_BaoCao_DanhSachBaoCao;
import dhxd.chukimmuoi.model.intent_giaodich;
import dhxd.chukimmuoi.model.intent_nhom;
import dhxd.chukimmuoi.model.intent_quy;
import dhxd.chukimmuoi.model.tbl_giaodich;
import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.model.tbl_quy;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class View_frm_BaoCao_DanhSachGiaoDich extends Activity {
	ImageButton quaylai;
	ListView danhsachgiaodich;

	private ArrayList<tbl_nhom> list_tblNhom;
	private ArrayList<tbl_quy> list_tblQuy;
	private ArrayList<tbl_giaodich> list_tblGiaoDich;

	public static Custom_Listview_BaoCao_DanhSachBaoCao adapter;

	private static final int REQUEST_CODE = 10;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_frm_baocao_danhsachgiaodich);
		final Bundle intent = getIntent().getExtras();

		quaylai = (ImageButton) findViewById(R.id.btnQuayLai_BC_DSGD);
		danhsachgiaodich = (ListView) findViewById(R.id.lvDanhSach_BC_DSGD);

		if (intent.getString("chucnang").equals("xemdanhsachgiaodich")) {
			intent_nhom Nhom = (intent_nhom) getIntent().getSerializableExtra(
					"Nhom");
			intent_quy Quy = (intent_quy) getIntent().getSerializableExtra(
					"Quy");
			intent_giaodich GiaoDich = (intent_giaodich) getIntent()
					.getSerializableExtra("GiaoDich");

			list_tblNhom = Nhom.getArrNhom();
			list_tblQuy = Quy.getArrQuy();
			list_tblGiaoDich = GiaoDich.getArrGiaoDich();

			adapter = new Custom_Listview_BaoCao_DanhSachBaoCao(
					View_frm_BaoCao_DanhSachGiaoDich.this,
					R.layout.custom_listview_sono_tongtien, list_tblGiaoDich,
					list_tblNhom);
			
			danhsachgiaodich.setAdapter(adapter);
		}
		
		danhsachgiaodich.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(arg2 != 0)
				{
					Intent myIntent = new Intent(
							View_frm_BaoCao_DanhSachGiaoDich.this,
							View_frm_QuanLyGiaoDich.class);
					myIntent.putExtra("chucnang", "xemchitiet");
					myIntent.putExtra("tieude", "XEM CHI TIẾT     ");
					myIntent.putExtra("objGiaoDichSua",
							list_tblGiaoDich.get(arg2));
					myIntent.putExtra("objNhomSua",
							list_tblNhom.get(arg2));
					myIntent.putExtra("objQuySua",
							list_tblQuy.get(arg2));
					startActivityForResult(myIntent, REQUEST_CODE);
				}
			}
		
		});
		
		quaylai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent();
				myIntent.putExtra("chucnang", "xemdanhsachgiaodich");
				setResult(RESULT_OK, myIntent);
				finish();
			}
		});
	}
}
