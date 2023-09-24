package dhxd.chukimmuoi.adapter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import dhxd.chukimmuoi.model.tbl_kehoach;
import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.qltc.R;
import dhxd.chukimmuoi.utils.Icon_MenuItem;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class Custom_Listview_DanhSachThongBaoGiaoDich extends
		ArrayAdapter<tbl_kehoach> {
	private Context context;
	private int layoutId;
	private ArrayList<tbl_kehoach> list_tblKeHoach;
	private ArrayList<tbl_kehoach> arr_tblKeHoach;
	private ArrayList<tbl_nhom> list_tblNhom;
	static final int[] Anh = Icon_MenuItem.danhsachanhchon;
	DecimalFormat tien = new DecimalFormat("###,###,### đ");
	final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public Custom_Listview_DanhSachThongBaoGiaoDich(Context context,
			int resource, ArrayList<tbl_kehoach> list_tblKeHoach,
			ArrayList<tbl_nhom> list_tblNhom) {
		super(context, resource, list_tblKeHoach);
		this.context = context;
		this.layoutId = resource;
		this.list_tblKeHoach = list_tblKeHoach;
		this.list_tblNhom = list_tblNhom;

		this.arr_tblKeHoach = new ArrayList<tbl_kehoach>();
		this.arr_tblKeHoach.addAll(list_tblKeHoach);
	}

	private static class Holder {
		ImageView anh;
		EditText ghichu;
		TextView sotien;
		TextView ngaythang;
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
					.findViewById(R.id.imgAnhNhom_CL_DSTBGD);
			holder.ghichu = (EditText) obj
					.findViewById(R.id.tvGhiChu_CL_DSTBGD);
			holder.sotien = (TextView) obj
					.findViewById(R.id.tvSoTien_CL_DSTBGD);
			holder.ngaythang = (TextView) obj
					.findViewById(R.id.tvNgayThang_CL_DSTBGD);
			obj.setTag(holder);
		} else {
			holder = (Holder) obj.getTag();
		}

		holder.ghichu.setText(list_tblKeHoach.get(position).getDienTa());
		holder.sotien.setText(tien.format(list_tblKeHoach.get(position)
				.getSoTien()));
		holder.ngaythang.setText(formatter.format(list_tblKeHoach.get(position)
				.getNgayBatDau()));

		if (list_tblKeHoach.get(position).getLoai_Id() == 0) {
			holder.sotien.setTextColor(Color.parseColor("#006600"));
		}
		if (list_tblKeHoach.get(position).getLoai_Id() == 1) {
			holder.sotien.setTextColor(Color.parseColor("#CC0000"));
		}
		
		if (list_tblKeHoach.get(position).getNhom_Id() != -1) {
			int stt = Integer.parseInt(list_tblNhom.get(position).getAnh());
			holder.anh.setImageResource(Anh[stt]);
		}

		return obj;
	}
	
	public void TimKiem(String giatri) {
		giatri = giatri.toLowerCase(Locale.getDefault());
		list_tblKeHoach.clear();
		String timkiem = null;
		if (giatri.length() == 0) {
			list_tblKeHoach.addAll(arr_tblKeHoach);
		} else {
			for (tbl_kehoach wp : arr_tblKeHoach) {
				timkiem = ""+wp.getSoTien()+"";
				if (timkiem.toLowerCase(Locale.getDefault())
						.contains(giatri)) {
					list_tblKeHoach.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}
}
