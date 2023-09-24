package dhxd.chukimmuoi.fragments;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class DanhSachGiaoDich_page_Event extends FragmentStatePagerAdapter {
	private int SoTrang = 101;
	Calendar lich;
	private int year;
	private int month;

	public DanhSachGiaoDich_page_Event(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		DanhSachGiaoDich_page_Fragment dsgd_page_fragment = new DanhSachGiaoDich_page_Fragment();
		Bundle data = new Bundle();
		data.putInt("sotrang", arg0);
		dsgd_page_fragment.setArguments(data);
		return dsgd_page_fragment;
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
		for (int i = 0; i < 101; i++) {
			if (position == i) {
				lich = Calendar.getInstance();
				lich.add(Calendar.MONTH, (i - 50));
				month = lich.get(Calendar.MONTH);
				year = lich.get(Calendar.YEAR);
				kq = "" + (month + 1) + "/" + year + "";
			}
		}
		return kq;
	}

}
