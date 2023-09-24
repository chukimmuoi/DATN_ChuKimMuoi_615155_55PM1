package dhxd.chukimmuoi.adapter;

import java.util.ArrayList;
import java.util.Locale;

import dhxd.chukimmuoi.model.tbl_doitac;
import dhxd.chukimmuoi.qltc.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Custom_Listview_DanhSachDoiTac extends ArrayAdapter<tbl_doitac> {
	private Context context;
	private int layoutId;
	private ArrayList<tbl_doitac> list_tblDT;
	private ArrayList<tbl_doitac> arr_tblDT;

	public Custom_Listview_DanhSachDoiTac(Context context, int layoutId,
			ArrayList<tbl_doitac> list_tblDT) {
		super(context, layoutId, list_tblDT);
		this.context = context;
		this.layoutId = layoutId;
		this.list_tblDT = list_tblDT;

		this.arr_tblDT = new ArrayList<tbl_doitac>();
		this.arr_tblDT.addAll(list_tblDT);
	}

	private static class Holder {
		TextView HoTen;
		TextView Email;
		TextView QuanHe;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View obj = convertView;
		Holder holder;
		if (obj == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			obj = inflater.inflate(layoutId, parent, false);
			holder = new Holder();

			holder.HoTen = (TextView) obj
					.findViewById(R.id.tvTenDoiTac_CL_DSDT);
			holder.Email = (TextView) obj.findViewById(R.id.tvEmail_CL_DSDT);
			holder.QuanHe = (TextView) obj.findViewById(R.id.tvQuanHe_CL_DSDT);

			obj.setTag(holder);
		} else {
			holder = (Holder) obj.getTag();
		}
		holder.HoTen.setText(list_tblDT.get(position).getEmail());
		holder.Email.setText(list_tblDT.get(position).getEmailDoiTac());
		holder.QuanHe.setText(list_tblDT.get(position).getQuanHe());

		return obj;
	}

	public void TimKiem(String giatri) {
		giatri = giatri.toLowerCase(Locale.getDefault());
		list_tblDT.clear();
		if (giatri.length() == 0) {
			list_tblDT.addAll(arr_tblDT);
		} else {
			for (tbl_doitac wp : arr_tblDT) {
				if (wp.getEmail().toLowerCase(Locale.getDefault())
						.contains(giatri)) {
					list_tblDT.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}
}
