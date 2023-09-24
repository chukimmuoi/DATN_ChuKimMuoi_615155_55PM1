package dhxd.chukimmuoi.qltc;

import java.util.ArrayList;
import java.util.Calendar;

import dhxd.chukimmuoi.adapter.Custom_Listview_DanhSachGiaoDich;
import dhxd.chukimmuoi.fragments.DanhSachGiaoDichFragment;
import dhxd.chukimmuoi.model.intent_giaodich;
import dhxd.chukimmuoi.model.intent_nhom;
import dhxd.chukimmuoi.model.intent_quy;
import dhxd.chukimmuoi.model.tbl_giaodich;
import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.model.tbl_quy;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class View_frm_BaoCao_DanhSachGiaoDich_Thang extends Activity{
	ImageButton quaylai;
	ListView danhsachgiaodich;

	private ArrayList<tbl_nhom> list_tblNhom;
	private ArrayList<tbl_quy> list_tblQuy;
	private ArrayList<tbl_giaodich> list_tblGiaoDich;
	private static final int REQUEST_CODE = 10;
	
	public static Custom_Listview_DanhSachGiaoDich adapter;
	
	private int chisochon = 0;
	
	private Calendar lich;
	private int year;
	private int month;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_frm_baocao_danhsachgiaodich);
		quaylai = (ImageButton) findViewById(R.id.btnQuayLai_BC_DSGD);
		danhsachgiaodich = (ListView) findViewById(R.id.lvDanhSach_BC_DSGD);
		final Bundle intent = getIntent().getExtras();
		
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

			
			adapter = new Custom_Listview_DanhSachGiaoDich(
					View_frm_BaoCao_DanhSachGiaoDich_Thang.this,
					R.layout.custom_listview_giaodich_tongquan,
					list_tblGiaoDich, list_tblNhom);
			danhsachgiaodich.setAdapter(adapter);
		}
		
		quaylai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent();
				myIntent.putExtra("chucnang", "xemdanhsachgiaodich");
				setResult(RESULT_OK, myIntent);
				finish();
			}
		});
		
		danhsachgiaodich.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(arg2 == 0)
				{
					Intent myIntent = new Intent(
							View_frm_BaoCao_DanhSachGiaoDich_Thang.this,
							View_frm_BaoCao_BaoCaoThang.class);
					
					lich = Calendar.getInstance();
					lich.add(Calendar.MONTH, (DanhSachGiaoDichFragment.tranghienthi - 50));
					month = lich.get(Calendar.MONTH) + 1;
					year = lich.get(Calendar.YEAR);
					
					myIntent.putExtra("chucnang", "xembaocao");
					myIntent.putExtra("tieude", "Báo cáo\ntháng "+month+" năm "+year+"");
					
						myIntent.putExtra("GiaoDich", new intent_giaodich(list_tblGiaoDich));
						myIntent.putExtra("Nhom", new intent_nhom(list_tblNhom));
						myIntent.putExtra("Quy", new intent_quy(list_tblQuy));
					
					startActivityForResult(myIntent, REQUEST_CODE);
				}
				else{
					chisochon = arg2;
					Intent myIntent = new Intent(
							View_frm_BaoCao_DanhSachGiaoDich_Thang.this,
							View_frm_QuanLyGiaoDich.class);
					myIntent.putExtra("chucnang", "sua");
					myIntent.putExtra("tieude", "SỬA GIAO DỊCH");
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
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == FragmentActivity.RESULT_OK
				&& requestCode == REQUEST_CODE) {
			if (data.getExtras().getString("chucnang").equals("sua")) {
				tbl_giaodich tblGD = new tbl_giaodich();
				tbl_nhom tblN = new tbl_nhom();
				tbl_quy tblQ = new tbl_quy();
				tblGD = (tbl_giaodich) data.getSerializableExtra("objGiaoDich");
				tblN = (tbl_nhom) data.getSerializableExtra("objNhom");
				tblQ = (tbl_quy) data.getSerializableExtra("objQuy");
				
				
					list_tblGiaoDich.remove(chisochon);
					list_tblGiaoDich.add(chisochon, tblGD);
					list_tblNhom.remove(chisochon);
					list_tblNhom.add(chisochon, tblN);
					list_tblQuy.remove(chisochon);
					list_tblQuy.add(chisochon, tblQ);
				
				for (int i = 1; i < list_tblQuy.size(); i++) {
					if (list_tblQuy.get(i).getQuy_Id() == tblQ.getQuy_Id()) {
						list_tblQuy.get(i).setSoTien(tblQ.getSoTien());
					}
				}
				adapter.notifyDataSetChanged();
			}
			
			if (data.getExtras().getString("chucnang").equals("xembaocao")) {
				
			}
		}
	}
}
