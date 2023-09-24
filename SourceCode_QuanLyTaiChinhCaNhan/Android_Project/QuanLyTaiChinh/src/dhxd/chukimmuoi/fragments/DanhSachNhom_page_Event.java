package dhxd.chukimmuoi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class DanhSachNhom_page_Event extends FragmentStatePagerAdapter {

	private int SoTrang = 2;
	private String ChucNang = null;
	
	public DanhSachNhom_page_Event(FragmentManager fm, String chucnang) {
		super(fm);
		this.ChucNang = chucnang;
	}

	@Override
	public Fragment getItem(int arg0) {
		DanhSachNhom_page_Fragment dsn_page_fragment = new DanhSachNhom_page_Fragment();
		Bundle data = new Bundle();
		data.putInt("sotrang", arg0);
		data.putString("chucnang", ChucNang);
		dsn_page_fragment.setArguments(data);
		return dsn_page_fragment;
	}

	@Override
	public int getCount() {
		return SoTrang;
	}

	@Override
	public int getItemPosition(Object object) {

		return POSITION_NONE;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		String kq = null;
		if (position == 0) {
			kq = "THU NHẬP";
		}
		if (position == 1) {
			kq = "CHI TIÊU";
		}
		return kq;
	}
}
