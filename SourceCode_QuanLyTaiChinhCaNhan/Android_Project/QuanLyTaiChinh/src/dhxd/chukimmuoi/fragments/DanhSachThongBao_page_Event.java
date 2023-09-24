package dhxd.chukimmuoi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class DanhSachThongBao_page_Event extends FragmentStatePagerAdapter{
	
	private int SoTrang = 2;
	
	public DanhSachThongBao_page_Event(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		DanhSachThongBao_page_Fragment dstb_page_fragment = new DanhSachThongBao_page_Fragment();
		Bundle data = new Bundle();
		data.putInt("sotrang", arg0);
		dstb_page_fragment.setArguments(data);
		return dstb_page_fragment;
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
			kq = "GIAO DỊCH";
		}
		if (position == 1) {
			kq = "BẠN BÈ";
		}
		return kq;
	}
}
