package dhxd.chukimmuoi.adapter;

import java.util.ArrayList;
import java.util.Locale;

import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.qltc.R;
import dhxd.chukimmuoi.utils.Icon_MenuItem;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Custom_Listview_DanhSachNhom extends ArrayAdapter<tbl_nhom> {

	private Context context;
	private int layoutId;
	private ArrayList<tbl_nhom> list_tblNhom;
	private ArrayList<tbl_nhom> arr_tblNhom;
	static final int[] Anh = Icon_MenuItem.danhsachanhchon;

	public Custom_Listview_DanhSachNhom(Context context, int resource,
			ArrayList<tbl_nhom> objects) {
		super(context, resource, objects);
		this.context = context;
		this.layoutId = resource;
		this.list_tblNhom = objects;

		this.arr_tblNhom = new ArrayList<tbl_nhom>();
		this.arr_tblNhom.addAll(list_tblNhom);
	}

	private static class Holder {
		ImageView anh;
		TextView tennhom;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View obj = convertView;
		Holder holder;
		if (obj == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			obj = inflater.inflate(layoutId, parent, false);
			holder = new Holder();

			holder.anh = (ImageView) obj.findViewById(R.id.imgAnhNhom_DSN);
			holder.tennhom = (TextView) obj.findViewById(R.id.tvTenNhom_DSN);

			obj.setTag(holder);
		} else {
			holder = (Holder) obj.getTag();
		}
		holder.tennhom.setText(list_tblNhom.get(position).getTenNhom());
		int stt = Integer.parseInt(list_tblNhom.get(position).getAnh());
		holder.anh.setImageResource(Anh[stt]);

		return obj;
	}

	public void TimKiem(String giatri) {
		giatri = giatri.toLowerCase(Locale.getDefault());
		list_tblNhom.clear();
		if (giatri.length() == 0) {
			list_tblNhom.addAll(arr_tblNhom);
		} else {
			for (tbl_nhom wp : arr_tblNhom) {
				if (wp.getTenNhom().toLowerCase(Locale.getDefault())
						.contains(giatri)) {
					list_tblNhom.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}
}
