package dhxd.chukimmuoi.fragments;

//import java.util.ArrayList;
import java.util.Locale;
//
//import dhxd.chukimmuoi.fragments.DanhSachKeHoachNganSach_page_Fragment.selectKeHoachNganSach0;
//import dhxd.chukimmuoi.fragments.DanhSachKeHoachNganSach_page_Fragment.selectKeHoachNganSach1;
//import dhxd.chukimmuoi.model.intent_nhom;
//import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.qltc.R;
import dhxd.chukimmuoi.qltc.View_frm_QuanLyKeHoachNganSach;
//import dhxd.chukimmuoi.qltc.View_frm_QuanLyNhom;
import dhxd.chukimmuoi.utils.Constant;
import dhxd.chukimmuoi.utils.Menu_Phai;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
//import android.support.v4.view.PagerTabStrip;
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

public class DanhSachKeHoachNganSachFragment extends Fragment {
//	static PagerTabStrip tab_tieude;
	ViewPager page;
	SearchView searchView;
	boolean chontimkiem;
	private static final int REQUEST_CODE = 10;

	public static int tranghienthi = 0;

	/** BẮT SỰ KIỆN THAY ĐỔI TRANG */
	private static class PageListener extends SimpleOnPageChangeListener {
		public void onPageSelected(int position) {
			tranghienthi = position;
			if (tranghienthi == 0) {
//				tab_tieude.setBackgroundResource(R.color.tab_thunhap);
				try {
					DanhSachKeHoachNganSach_page_Fragment.adapter0
					.notifyDataSetChanged();
				} catch (Exception e) {
				}
			}
			if (tranghienthi == 1) {
//				tab_tieude.setBackgroundResource(R.color.tab_chitieu);
				try {
					DanhSachKeHoachNganSach_page_Fragment.adapter1
					.notifyDataSetChanged();
				} catch (Exception e) {
				}
			}
		}
	}

	public DanhSachKeHoachNganSachFragment newInstance(String text) {
		DanhSachKeHoachNganSachFragment mFragment = new DanhSachKeHoachNganSachFragment();
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
		page = (ViewPager) rootView.findViewById(R.id.vpPage_DSN_P);
//		tab_tieude = (PagerTabStrip) rootView
//				.findViewById(R.id.tabTieuDe_DSN_P);
//		tab_tieude.setBackgroundResource(R.color.tab_thunhap);
		PageListener pageListener = new PageListener();
		page.setOnPageChangeListener(pageListener);

		FragmentManager fm = getActivity().getSupportFragmentManager();
		DanhSachKeHoachNganSach_page_Event event = new DanhSachKeHoachNganSach_page_Event(
				fm);
		page.setAdapter(event);
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

	// Bắt sự kiện xử lý khi chọn chức năng trên menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case Menu_Phai.THEMMOI:
			Intent myIntent = new Intent(
					DanhSachKeHoachNganSachFragment.this.getActivity(),
					View_frm_QuanLyKeHoachNganSach.class);
			myIntent.putExtra("chucnang", "them");
			myIntent.putExtra("tieude", "THÊM KẾ HOẠCH");
			startActivityForResult(myIntent, REQUEST_CODE);
			break;

		case Menu_Phai.TIMKIEM:
			chontimkiem = true;
			break;
		}
		return true;
	}

	// Hàm xử lý sự kiện trên ô tìm kiếm
	private OnQueryTextListener HienThiOTimKiem = new OnQueryTextListener() {

		@Override
		public boolean onQueryTextSubmit(String arg0) {
			return false;
		}

		// Khi giá trị của ô tìm kiếm thay đổi.
		@Override
		public boolean onQueryTextChange(String arg0) {
			if (chontimkiem) {
				String text = arg0.toString().toLowerCase(Locale.getDefault());
				if (tranghienthi == 0) {
					DanhSachKeHoachNganSach_page_Fragment.adapter0
							.TimKiem(text);
				}
				if (tranghienthi == 1) {
					DanhSachKeHoachNganSach_page_Fragment.adapter1
							.TimKiem(text);
				}
			}
			return true;
		}
	};

	// Có dữ liệu trả về
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		getActivity();
		if (resultCode == FragmentActivity.RESULT_OK
				&& requestCode == REQUEST_CODE) {
			if (data.getExtras().getString("chucnang").equals("them")) {
				// FragmentManager fm =
				// getActivity().getSupportFragmentManager();
				// DanhSachKeHoachNganSach_page_Event event = new
				// DanhSachKeHoachNganSach_page_Event(
				// fm);
				// page.setAdapter(event);
			}
		}
	}
}
