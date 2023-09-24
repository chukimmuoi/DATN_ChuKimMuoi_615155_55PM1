package dhxd.chukimmuoi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class DanhSachKeHoachNganSach_page_Event extends FragmentStatePagerAdapter{
	private int SoTrang = 2;
	
	public DanhSachKeHoachNganSach_page_Event(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		DanhSachKeHoachNganSach_page_Fragment dsn_page_fragment = new DanhSachKeHoachNganSach_page_Fragment();
		Bundle data = new Bundle();
		data.putInt("sotrang", arg0);
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
			kq = "ĐANG ÁP DỤNG";
		}
		if (position == 1) {
			kq = "ĐÃ KẾT THÚC";
		}
		return kq;
	}
	
}
