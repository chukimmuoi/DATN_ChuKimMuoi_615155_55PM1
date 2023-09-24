package dhxd.chukimmuoi.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import dhxd.chukimmuoi.model.tbl_quy;
import dhxd.chukimmuoi.qltc.R;
import dhxd.chukimmuoi.utils.Icon_MenuItem;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Custom_Listview_DanhSachQuy extends ArrayAdapter<tbl_quy> {
	private Context context;
	private int layoutId;
	private ArrayList<tbl_quy> list_tblQuy;
	private ArrayList<tbl_quy> arr_tblQuy;
	static final int[] Anh = Icon_MenuItem.danhsachanhchon;

	DecimalFormat tien = new DecimalFormat("###,###,### đ");

	public Custom_Listview_DanhSachQuy(Context context, int resource,
			ArrayList<tbl_quy> objects) {
		super(context, resource, objects);
		this.context = context;
		this.layoutId = resource;
		this.list_tblQuy = objects;

		this.arr_tblQuy = new ArrayList<tbl_quy>();
		this.arr_tblQuy.addAll(list_tblQuy);
	}

	private static class Holder {
		ImageView anh;
		TextView tenquy;
		TextView sotien;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View obj = convertView;
		Holder holder;
		if (obj == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			obj = inflater.inflate(layoutId, parent, false);
			holder = new Holder();

			holder.anh = (ImageView) obj
					.findViewById(R.id.imgviewAnhQuy_CL_DSQ);
			holder.tenquy = (TextView) obj.findViewById(R.id.tvTenQuy_CL_DSQ);
			holder.sotien = (TextView) obj.findViewById(R.id.tvSoTien_CL_DSQ);

			obj.setTag(holder);
		} else {
			holder = (Holder) obj.getTag();
		}

		holder.tenquy.setText(list_tblQuy.get(position).getTenQuy());
		holder.sotien.setText(tien
				.format(list_tblQuy.get(position).getSoTien()));
		if (list_tblQuy.get(position).getSoTien() > 0) {
			holder.sotien.setTextColor(Color.parseColor("#006600"));
		} else {
			holder.sotien.setTextColor(Color.parseColor("#CC0000"));
		}
		int stt = Integer.parseInt(list_tblQuy.get(position).getAnh());
		holder.anh.setImageResource(Anh[stt]);

		return obj;
	}

	public void TimKiem(String giatri) {
		giatri = giatri.toLowerCase(Locale.getDefault());
		list_tblQuy.clear();
		if (giatri.length() == 0) {
			list_tblQuy.addAll(arr_tblQuy);
		} else {
			for (tbl_quy wp : arr_tblQuy) {
				if (wp.getTenQuy().toLowerCase(Locale.getDefault())
						.contains(giatri)) {
					list_tblQuy.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

}
