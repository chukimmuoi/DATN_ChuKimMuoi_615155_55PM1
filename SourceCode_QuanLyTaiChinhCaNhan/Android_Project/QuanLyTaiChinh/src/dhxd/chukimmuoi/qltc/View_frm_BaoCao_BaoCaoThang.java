package dhxd.chukimmuoi.qltc;

import java.util.ArrayList;

import dhxd.chukimmuoi.adapter.Custom_Listview_DanhSach_BaoCaoThang;
import dhxd.chukimmuoi.model.intent_giaodich;
import dhxd.chukimmuoi.model.intent_nhom;
import dhxd.chukimmuoi.model.intent_quy;
import dhxd.chukimmuoi.model.list_nhom_bc;
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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class View_frm_BaoCao_BaoCaoThang extends Activity {
	ImageButton quaylai;
	TextView tieude;
	ListView danhsachbaocao;

	private ArrayList<list_nhom_bc> nhom_baocao;
	private ArrayList<list_nhom_bc> nhom_baocao_thu;
	private ArrayList<list_nhom_bc> nhom_baocao_chi;
	
	private ArrayList<tbl_nhom> list_tblNhom;
	private ArrayList<tbl_quy> list_tblQuy;
	private ArrayList<tbl_giaodich> list_tblGiaoDich;
	
	private ArrayList<tbl_nhom> arr_tblNhom;
	private ArrayList<tbl_quy> arr_tblQuy;
	private ArrayList<tbl_giaodich> arr_tblGiaoDich;

	public static Custom_Listview_DanhSach_BaoCaoThang adapter;
	private int stt;
	private static final int REQUEST_CODE = 10;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_frm_baocao_baocaothang);
		final Bundle intent = getIntent().getExtras();

		quaylai = (ImageButton) findViewById(R.id.btnQuayLai_BC_BCT);
		tieude = (TextView) findViewById(R.id.tvTieuDe_BC_BCT);
		danhsachbaocao = (ListView) findViewById(R.id.lvDanhSach_BC_BCT);

		if (intent.getString("chucnang").equals("xembaocao")) {
			nhom_baocao = new ArrayList<list_nhom_bc>();
			nhom_baocao_thu = new ArrayList<list_nhom_bc>();
			nhom_baocao_chi = new ArrayList<list_nhom_bc>();

			tieude.setText(intent.getString("tieude"));
			intent_nhom Nhom = (intent_nhom) getIntent().getSerializableExtra(
					"Nhom");
			intent_quy Quy = (intent_quy) getIntent().getSerializableExtra(
					"Quy");
			intent_giaodich GiaoDich = (intent_giaodich) getIntent()
					.getSerializableExtra("GiaoDich");

			list_tblNhom = Nhom.getArrNhom();
			list_tblQuy = Quy.getArrQuy();
			list_tblGiaoDich = GiaoDich
					.getArrGiaoDich();

			for (int i = 1; i < list_tblGiaoDich.size(); i++) {
				if (list_tblNhom.get(i).getLoai_Id().equals("0")) {
					if (kiemtraNhom_Id(list_tblGiaoDich.get(i), nhom_baocao_thu)) {
						list_nhom_bc objNhomBC = new list_nhom_bc();
						double tongtien = 0;
						objNhomBC.setNhom_Id(list_tblNhom.get(i).getNhom_Id());
						objNhomBC.setTenNhom(list_tblNhom.get(i).getTenNhom());
						objNhomBC.setAnh(list_tblNhom.get(i).getAnh());
						objNhomBC.setLoai_Id(list_tblNhom.get(i).getLoai_Id());

						for (int j = 1; j < list_tblGiaoDich.size(); j++) {
							if (list_tblGiaoDich.get(j).getNhom_Id() == objNhomBC
									.getNhom_Id()) {
								tongtien = tongtien
										+ list_tblGiaoDich.get(j).getSoTien();
							}
						}
						objNhomBC.setTongTien(tongtien);

						nhom_baocao_thu.add(objNhomBC);
					}
				} else {
					if (list_tblNhom.get(i).getLoai_Id().equals("1")) {
						if (kiemtraNhom_Id(list_tblGiaoDich.get(i),
								nhom_baocao_chi)) {
							list_nhom_bc objNhomBC = new list_nhom_bc();
							double tongtien = 0;
							objNhomBC.setNhom_Id(list_tblNhom.get(i)
									.getNhom_Id());
							objNhomBC.setTenNhom(list_tblNhom.get(i)
									.getTenNhom());
							objNhomBC.setAnh(list_tblNhom.get(i).getAnh());
							objNhomBC.setLoai_Id(list_tblNhom.get(i).getLoai_Id());

							for (int j = 1; j < list_tblGiaoDich.size(); j++) {
								if (list_tblGiaoDich.get(j).getNhom_Id() == objNhomBC
										.getNhom_Id()) {
									tongtien = tongtien
											+ list_tblGiaoDich.get(j)
													.getSoTien();
								}
							}
							objNhomBC.setTongTien(tongtien);

							nhom_baocao_chi.add(objNhomBC);
						}
					}
				}
			}
			list_nhom_bc nhombc0 = new list_nhom_bc();
			
			nhombc0.setLoai_Id("tinhtien");
			nhom_baocao.add(nhombc0);
			
			nhombc0.setLoai_Id("biedothu");
			nhom_baocao.add(nhombc0);
			
			nhom_baocao.addAll(nhom_baocao_thu);
			
			stt = nhom_baocao.size();
			
			nhombc0.setLoai_Id("biedochi");
			nhom_baocao.add(nhombc0);
			
			nhom_baocao.addAll(nhom_baocao_chi);
			
			adapter = new Custom_Listview_DanhSach_BaoCaoThang(
					View_frm_BaoCao_BaoCaoThang.this, R.layout.custom_listview_baocao_tongquan,
					nhom_baocao, stt, nhom_baocao_thu, nhom_baocao_chi);
			danhsachbaocao.setAdapter(adapter);
		}
		
		danhsachbaocao.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(arg2 != 0 && arg2 != 1 && arg2 != stt)
				{
					arr_tblGiaoDich = new ArrayList<tbl_giaodich>();
					arr_tblNhom = new ArrayList<tbl_nhom>();
					arr_tblQuy = new ArrayList<tbl_quy>();
					
					tbl_giaodich tblGD0 = new tbl_giaodich();
					tbl_nhom tblN0 = new tbl_nhom();
					if(arg2 < stt)
					{
						tblN0.setLoai_Id("0");
					}
					else
					{
						if(arg2 > stt)
						{
							tblN0.setLoai_Id("1");
						}
					}
					tbl_quy tblQ0 = new tbl_quy();
					
					arr_tblGiaoDich.add(tblGD0);
					arr_tblNhom.add(tblN0);
					arr_tblQuy.add(tblQ0);
					
					for (int i = 1; i < list_tblGiaoDich.size(); i++) {
						if(list_tblGiaoDich.get(i).getNhom_Id() == nhom_baocao.get(arg2).getNhom_Id())
						{
							arr_tblGiaoDich.add(list_tblGiaoDich.get(i));
							arr_tblNhom.add(list_tblNhom.get(i));
							arr_tblQuy.add(list_tblQuy.get(i));
						}
					}
					
					Intent myIntent = new Intent(
							View_frm_BaoCao_BaoCaoThang.this,
							View_frm_BaoCao_DanhSachGiaoDich.class);
					myIntent.putExtra("chucnang", "xemdanhsachgiaodich");
					myIntent.putExtra("GiaoDich", new intent_giaodich(arr_tblGiaoDich));
					myIntent.putExtra("Nhom", new intent_nhom(arr_tblNhom));
					myIntent.putExtra("Quy", new intent_quy(arr_tblQuy));
					
					startActivityForResult(myIntent, REQUEST_CODE);
				}
			}
		
		});
		
		quaylai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent();
				myIntent.putExtra("chucnang", "xembaocao");
				setResult(RESULT_OK, myIntent);
				finish();
			}
		});
	}

	private boolean kiemtraNhom_Id(tbl_giaodich giaodich,
			ArrayList<list_nhom_bc> list_nhom) {
		boolean ketqua = true;
		for (int i = 0; i < list_nhom.size(); i++) {
			if (list_nhom.get(i).getNhom_Id() == giaodich.getNhom_Id()) {
				ketqua = false;
			}
		}
		return ketqua;
	}
}
