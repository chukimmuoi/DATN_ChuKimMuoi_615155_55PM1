package dhxd.chukimmuoi.qltc;

import java.util.ArrayList;

import dhxd.chukimmuoi.fragments.DanhSachNhom_page_Event;
import dhxd.chukimmuoi.fragments.DanhSachNhom_page_Fragment;
import dhxd.chukimmuoi.model.intent_nhom;
import dhxd.chukimmuoi.model.tbl_nhom;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class View_frm_DanhSachNhom_Page_Chon extends FragmentActivity {
	static PagerTabStrip tab_tieude;
	public static int tranghienthi = 0;
	Button themnhom;
	ImageButton quaylai;
	private static final int REQUEST_CODE = 10;

	/** BẮT SỰ KIỆN THAY ĐỔI TRANG */
	private static class PageListener extends SimpleOnPageChangeListener {
		public void onPageSelected(int position) {
			tranghienthi = position;
			if (tranghienthi == 0) {
				try {
					DanhSachNhom_page_Fragment.adapterThu.notifyDataSetChanged();
				} catch (Exception e) {
				}
				tab_tieude.setBackgroundResource(R.color.tab_thunhap);
			}
			if (tranghienthi == 1) {
				try {
					DanhSachNhom_page_Fragment.adapterChi.notifyDataSetChanged();
				} catch (Exception e) {
				}
				tab_tieude.setBackgroundResource(R.color.tab_chitieu);
			}
		}
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.view_frm_danhsachnhom_page_chon);
		ViewPager page = (ViewPager) findViewById(R.id.vpPage_DSN_P_C);
		themnhom = (Button) findViewById(R.id.btnThemNhom_DSN_P_C);
		quaylai = (ImageButton) findViewById(R.id.imgbtnQuayLai_DSN_P_C);
		
		themnhom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(
						View_frm_DanhSachNhom_Page_Chon.this,
						View_frm_QuanLyNhom.class);
				myIntent.putExtra("chucnang", "them");
				myIntent.putExtra("tieude", "Thêm nhóm");
				startActivityForResult(myIntent, REQUEST_CODE);
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
		tab_tieude = (PagerTabStrip) findViewById(R.id.tabTieuDe_DSN_P_C);
		tab_tieude.setBackgroundResource(R.color.tab_thunhap);
		PageListener pageListener = new PageListener();
		page.setOnPageChangeListener(pageListener);
		FragmentManager fm = getSupportFragmentManager();

		DanhSachNhom_page_Event event = new DanhSachNhom_page_Event(fm,
				"chonnhom");

		page.setAdapter(event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	// Có dữ liệu trả về
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == FragmentActivity.RESULT_OK
				&& requestCode == REQUEST_CODE) {

			if (data.getExtras().getString("chucnang").equals("them")) {
				intent_nhom arrNhom_Them = (intent_nhom) data
						.getSerializableExtra("arrNhom_Them");
				ArrayList<tbl_nhom> arrNhom = arrNhom_Them.getArrNhom();
				for (int i = 0; i < arrNhom.size(); i++) {
					if (arrNhom.get(i).getLoai_Id().toString().equals("0")) {
						DanhSachNhom_page_Fragment.listNhomThu.add(arrNhom
								.get(i));
						DanhSachNhom_page_Fragment.adapterThu
								.notifyDataSetChanged();
					}
					if (arrNhom.get(i).getLoai_Id().toString().equals("1")) {
						DanhSachNhom_page_Fragment.listNhomChi.add(arrNhom
								.get(i));
						DanhSachNhom_page_Fragment.adapterChi
								.notifyDataSetChanged();
					}
				}

			}
		}
	}
}
