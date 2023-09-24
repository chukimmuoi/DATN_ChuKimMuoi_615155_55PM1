package dhxd.chukimmuoi.fragments;

import java.util.Locale;

import dhxd.chukimmuoi.qltc.R;
import dhxd.chukimmuoi.utils.Constant;
import dhxd.chukimmuoi.utils.Menu_Phai;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
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

public class DanhSachSoNoFragment extends Fragment {
	SearchView searchView;
	boolean chontimkiem;
	public static int tranghienthi = 0;

	/** BẮT SỰ KIỆN THAY ĐỔI TRANG */
	private static class PageListener extends SimpleOnPageChangeListener {
		public void onPageSelected(int position) {
			tranghienthi = position;
			if (tranghienthi == 0) {
				try {
					DanhSachSoNo_page_Fragment.adapter0.notifyDataSetChanged();
				} catch (Exception e) {
				}
			}
			if (tranghienthi == 1) {
				try {
					DanhSachSoNo_page_Fragment.adapter1.notifyDataSetChanged();
				} catch (Exception e) {
				}
			}
		}
	}

	public DanhSachSoNoFragment newInstance(String text) {
		DanhSachSoNoFragment mFragment = new DanhSachSoNoFragment();
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
		PageListener pageListener = new PageListener();
		page.setOnPageChangeListener(pageListener);

		FragmentManager fm = getActivity().getSupportFragmentManager();
		DanhSachSoNo_page_Event event = new DanhSachSoNo_page_Event(fm);
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

		menu.findItem(Menu_Phai.THEMMOI).setVisible(false);
		menu.findItem(Menu_Phai.TIMKIEM).setVisible(true);

		chontimkiem = false;
	}

	// Bắt sự kiện xử lý khi chọn chức năng trên menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case Menu_Phai.THEMMOI:
			// Intent myIntent = new Intent(
			// DanhSachNhomFragment.this.getActivity(),
			// View_frm_QuanLyNhom.class);
			// myIntent.putExtra("chucnang", "them");
			// myIntent.putExtra("tieude", "Thêm nhóm");
			// startActivityForResult(myIntent, REQUEST_CODE);
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

		// Khi giá trị của ô tìm kiếm thay đổi.
		@Override
		public boolean onQueryTextChange(String arg0) {
			if (chontimkiem) {
				String text = arg0.toString().toLowerCase(Locale.getDefault());
				if (tranghienthi == 0) {
					DanhSachSoNo_page_Fragment.adapter0.TimKiem(text);
				}
				if (tranghienthi == 1) {
					DanhSachSoNo_page_Fragment.adapter1.TimKiem(text);
				}
			}
			return true;
		}
	};
}
