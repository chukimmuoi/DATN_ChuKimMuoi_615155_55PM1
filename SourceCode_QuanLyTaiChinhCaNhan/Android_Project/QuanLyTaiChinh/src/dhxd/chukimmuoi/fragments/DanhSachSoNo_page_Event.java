package dhxd.chukimmuoi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class DanhSachSoNo_page_Event extends FragmentStatePagerAdapter {
	private int SoTrang = 2;

	public DanhSachSoNo_page_Event(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		DanhSachSoNo_page_Fragment dssn_page_fragment = new DanhSachSoNo_page_Fragment();
		Bundle data = new Bundle();
		data.putInt("sotrang", arg0);
		dssn_page_fragment.setArguments(data);
		return dssn_page_fragment;
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
			kq = "CẦN THU";
		}
		if (position == 1) {
			kq = "CẦN TRẢ";
		}
		return kq;
	}
}
