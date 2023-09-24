package dhxd.chukimmuoi.fragments;

import java.util.ArrayList;
import java.util.Locale;

import dhxd.chukimmuoi.model.intent_giaodich;
import dhxd.chukimmuoi.model.intent_nhom;
import dhxd.chukimmuoi.model.intent_quy;
import dhxd.chukimmuoi.model.tbl_giaodich;
import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.model.tbl_quy;
import dhxd.chukimmuoi.qltc.R;
import dhxd.chukimmuoi.qltc.View_frm_QuanLyGiaoDich;
import dhxd.chukimmuoi.utils.Constant;
import dhxd.chukimmuoi.utils.Menu_Phai;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class DanhSachGiaoDichFragment extends Fragment {
	static PagerTabStrip tab_tieude;
	private SearchView searchView;
	private boolean chontimkiem;

	private static final int REQUEST_CODE = 10;
	public static int tranghienthi = 50;

	/** BẮT SỰ KIỆN THAY ĐỔI TRANG */
	private static class PageListener extends SimpleOnPageChangeListener {
		public void onPageSelected(int position) {
			tranghienthi = position;
		}
	}

	public DanhSachGiaoDichFragment newInstance(String text) {
		DanhSachGiaoDichFragment mFragment = new DanhSachGiaoDichFragment();
		Bundle mBundle = new Bundle();
		mBundle.putString(Constant.TEXT_FRAGMENT, text);
		mFragment.setArguments(mBundle);
		return mFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.view_frm_danhsachnhom_page,
				container, false);
		ViewPager page = (ViewPager) rootView.findViewById(R.id.vpPage_DSN_P);
		tab_tieude = (PagerTabStrip) rootView
				.findViewById(R.id.tabTieuDe_DSN_P);
		PageListener pageListener = new PageListener();
		page.setOnPageChangeListener(pageListener);

		FragmentManager fm = getActivity().getSupportFragmentManager();
		DanhSachGiaoDich_page_Event event = new DanhSachGiaoDich_page_Event(fm);
		page.setAdapter(event);
		page.setCurrentItem(50);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu, menu);

		searchView = (SearchView) MenuItemCompat.getActionView(menu
				.findItem(Menu_Phai.TIMKIEM));
		// Thiết kế ô text tìm kiếm
		// Chữ chìm
		searchView.setQueryHint(this.getString(R.string.search));
		// màu chữ
		((EditText) searchView
				.findViewById(android.support.v7.appcompat.R.id.search_src_text))
				.setHintTextColor(getResources().getColor(R.color.trang));

		// Gọi hàm để xử lý sự kiện trên ô tìm kiếm
		searchView.setOnQueryTextListener(HienThiOTimKiem);

		menu.findItem(Menu_Phai.THEMMOI).setVisible(true);
		menu.findItem(Menu_Phai.TIMKIEM).setVisible(true);

		chontimkiem = false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case Menu_Phai.THEMMOI:
			Intent myIntent = new Intent(
					DanhSachGiaoDichFragment.this.getActivity(),
					View_frm_QuanLyGiaoDich.class);
			myIntent.putExtra("chucnang", "them");
			myIntent.putExtra("tieude", "THÊM GIAO DỊCH");
			startActivityForResult(myIntent, REQUEST_CODE);
			break;

		case Menu_Phai.TIMKIEM:
			chontimkiem = true;
			break;
		}
		return true;
	}

	private OnQueryTextListener HienThiOTimKiem = new OnQueryTextListener() {

		@Override
		public boolean onQueryTextSubmit(String arg0) {
			return false;
		}

		@Override
		public boolean onQueryTextChange(String arg0) {
			if (chontimkiem) {
				String text = arg0.toString().toLowerCase(Locale.getDefault());
				if ((tranghienthi % 3) == 1) {
					DanhSachGiaoDich_page_Fragment.adapterdu1.TimKiem(text);
				}
				if ((tranghienthi % 3) == 2) {
					DanhSachGiaoDich_page_Fragment.adapterdu2.TimKiem(text);
				}
				if ((tranghienthi % 3) == 0) {
					DanhSachGiaoDich_page_Fragment.adapterdu3.TimKiem(text);
				}
			}
			return true;
		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		getActivity();
		if (resultCode == FragmentActivity.RESULT_OK
				&& requestCode == REQUEST_CODE) {
			if (data.getExtras().getString("chucnang").equals("them")) {

				intent_giaodich arrGiaoDich_Them = (intent_giaodich) data
						.getSerializableExtra("arrGiaoDich_Them");
				intent_nhom arrNhom_Them = (intent_nhom) data
						.getSerializableExtra("arrNhom_Them");
				intent_quy arrQuy_Them = (intent_quy) data
						.getSerializableExtra("arrQuy_Them");

				ArrayList<tbl_giaodich> arrGiaoDich = arrGiaoDich_Them
						.getArrGiaoDich();
				ArrayList<tbl_nhom> arrNhom = arrNhom_Them.getArrNhom();
				ArrayList<tbl_quy> arrQuy = arrQuy_Them.getArrQuy();
				for (int i = 0; i < arrGiaoDich.size(); i++) {

					for (int j = 1; j < DanhSachGiaoDich_page_Fragment.listQuyDu1
							.size(); j++) {
						if (DanhSachGiaoDich_page_Fragment.listQuyDu1.get(j)
								.getQuy_Id() == arrQuy.get(i).getQuy_Id()) {
							DanhSachGiaoDich_page_Fragment.listQuyDu1.get(j)
									.setSoTien(arrQuy.get(i).getSoTien());
						}
					}
					for (int j = 1; j < DanhSachGiaoDich_page_Fragment.listQuyDu2
							.size(); j++) {
						if (DanhSachGiaoDich_page_Fragment.listQuyDu2.get(j)
								.getQuy_Id() == arrQuy.get(i).getQuy_Id()) {
							DanhSachGiaoDich_page_Fragment.listQuyDu2.get(j)
									.setSoTien(arrQuy.get(i).getSoTien());
						}
					}
					for (int j = 1; j < DanhSachGiaoDich_page_Fragment.listQuyDu3
							.size(); j++) {
						if (DanhSachGiaoDich_page_Fragment.listQuyDu3.get(j)
								.getQuy_Id() == arrQuy.get(i).getQuy_Id()) {
							DanhSachGiaoDich_page_Fragment.listQuyDu3.get(j)
									.setSoTien(arrQuy.get(i).getSoTien());
						}
					}

					if ((tranghienthi % 3) == 1) {
						DanhSachGiaoDich_page_Fragment.listGiaoDichDu1
								.add(arrGiaoDich.get(i));
						DanhSachGiaoDich_page_Fragment.listNhomDu1.add(arrNhom
								.get(i));
						DanhSachGiaoDich_page_Fragment.listQuyDu1.add(arrQuy
								.get(i));
						// DanhSachGiaoDich_page_Fragment.adapterdu1
						// .notifyDataSetChanged();
					}
					if ((tranghienthi % 3) == 2) {
						DanhSachGiaoDich_page_Fragment.listGiaoDichDu2
								.add(arrGiaoDich.get(i));
						DanhSachGiaoDich_page_Fragment.listNhomDu2.add(arrNhom
								.get(i));
						DanhSachGiaoDich_page_Fragment.listQuyDu2.add(arrQuy
								.get(i));
						// DanhSachGiaoDich_page_Fragment.adapterdu2
						// .notifyDataSetChanged();
					}
					if ((tranghienthi % 3) == 0) {
						DanhSachGiaoDich_page_Fragment.listGiaoDichDu3
								.add(arrGiaoDich.get(i));
						DanhSachGiaoDich_page_Fragment.listNhomDu3.add(arrNhom
								.get(i));
						DanhSachGiaoDich_page_Fragment.listQuyDu3.add(arrQuy
								.get(i));
						// DanhSachGiaoDich_page_Fragment.adapterdu3
						// .notifyDataSetChanged();
					}
					DanhSachGiaoDich_page_Fragment.adapterdu1
							.notifyDataSetChanged();
					DanhSachGiaoDich_page_Fragment.adapterdu2
							.notifyDataSetChanged();
					DanhSachGiaoDich_page_Fragment.adapterdu3
							.notifyDataSetChanged();
				}
			}
		}
	}
}
