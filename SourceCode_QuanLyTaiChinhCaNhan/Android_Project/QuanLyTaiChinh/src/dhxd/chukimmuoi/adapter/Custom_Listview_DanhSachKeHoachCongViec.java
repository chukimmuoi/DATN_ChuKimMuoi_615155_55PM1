package dhxd.chukimmuoi.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import dhxd.chukimmuoi.model.tbl_kehoach;
import dhxd.chukimmuoi.model.tbl_nhom;
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

public class Custom_Listview_DanhSachKeHoachCongViec extends ArrayAdapter<tbl_kehoach>{
	private Context context;
	private int layoutId;
	private ArrayList<tbl_kehoach> list_tblKeHoach;
	private ArrayList<tbl_kehoach> arr_tblKeHoach;
	private ArrayList<tbl_nhom> list_tblNhom;
	private ArrayList<tbl_kehoach> kehoachdautien;
	static final int[] Anh = Icon_MenuItem.danhsachanhchon;
	private double tongthuthang = 0;
	private double tongchithang = 0;
	private int luuthang = -1;

	DecimalFormat tien = new DecimalFormat("###,###,### đ");
	
	public Custom_Listview_DanhSachKeHoachCongViec(Context context, int resource,
			ArrayList<tbl_kehoach> objects, ArrayList<tbl_nhom> objects1) {
		super(context, resource, objects);
		this.context = context;
		this.layoutId = resource;
		this.list_tblKeHoach = objects;
		this.list_tblNhom = objects1;

		this.arr_tblKeHoach = new ArrayList<tbl_kehoach>();
		this.arr_tblKeHoach.addAll(list_tblKeHoach);
	}
	
	private static class Holder {
		TextView txtnam;
		TextView thang;
		TextView nam;
		TextView tongtientrongthang;

		TextView tennhom;
		TextView ghichu;
		TextView sotien;
		ImageView anhnhom;
	}
	
	@SuppressWarnings("deprecation")
	private void GiaoDichDauTien() {
		kehoachdautien = new ArrayList<tbl_kehoach>();
		for (int i = 0; i < list_tblKeHoach.size(); i++) {
			if (list_tblKeHoach.get(i).getNgayBatDau().getMonth() != luuthang) {
				luuthang = list_tblKeHoach.get(i).getNgayBatDau().getMonth();
				kehoachdautien.add(list_tblKeHoach.get(i));
			}
		}
		luuthang = -1;
	}

	private boolean kiemtrakehoach(tbl_kehoach KH) {
		boolean ketqua = false;
		for (int i = 0; i < kehoachdautien.size(); i++) {
			if (kehoachdautien.get(i).getKeHoach_Id() == KH.getKeHoach_Id()) {
				ketqua = true;
			}
		}
		return ketqua;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		GiaoDichDauTien();
		View obj = convertView;
		Holder holder;
		
		if (kiemtrakehoach(list_tblKeHoach.get(position))) {
			luuthang = list_tblKeHoach.get(position).getNgayBatDau().getMonth();
			for (int i = 0; i < list_tblKeHoach.size(); i++) {
				if (list_tblKeHoach.get(i).getNgayBatDau().getMonth() == luuthang) {
					if (list_tblNhom.get(i).getLoai_Id().toString()
							.equals("0")) {
						tongthuthang = tongthuthang
								+ list_tblKeHoach.get(i).getSoTien();
					} else {
						if (list_tblNhom.get(i).getLoai_Id().toString()
								.equals("1")) {
							tongchithang = tongchithang
									+ list_tblKeHoach.get(i).getSoTien();
						}
					}
				}
			}

			LayoutInflater inflater = ((Activity) context)
					.getLayoutInflater();
			obj = inflater.inflate(
					layoutId, parent,
					false);
			holder = new Holder();

			holder.txtnam = (TextView) obj.findViewById(R.id.tvThu_CL_GD_NT);
			holder.thang = (TextView) obj.findViewById(R.id.tvNgay_CL_GD_NT);
			holder.nam = (TextView) obj
					.findViewById(R.id.tvThangNam_CL_GD_NT);
			holder.tongtientrongthang = (TextView) obj
					.findViewById(R.id.tvTienTrongNgay_CL_GD_NT);

			holder.anhnhom = (ImageView) obj
					.findViewById(R.id.imgAnhNhom_CL_GD_NT);
			holder.tennhom = (TextView) obj
					.findViewById(R.id.tvTenNhom_CL_GD_NT);
			holder.ghichu = (TextView) obj
					.findViewById(R.id.tvGhiChu_CL_GD_NT);
			holder.sotien = (TextView) obj
					.findViewById(R.id.tvSoTien_CL_GD_NT);

			obj.setTag(holder);

			
			holder.txtnam.setText("NĂM");
			holder.thang.setText(""
					+ (list_tblKeHoach.get(position).getNgayBatDau().getMonth() + 1) + "");
			holder.nam.setText(""
					+ (list_tblKeHoach.get(position).getNgayBatDau()
							.getYear() + 1900) + "");
			if ((tongthuthang - tongchithang) > 0) {
				holder.tongtientrongthang.setTextColor(Color
						.parseColor("#006600"));
			} else {
				holder.tongtientrongthang.setTextColor(Color
						.parseColor("#CC0000"));
			}
			holder.tongtientrongthang.setText(tien.format(tongthuthang
					- tongchithang));

			holder.tennhom.setText(list_tblNhom.get(position).getTenNhom());
			holder.ghichu.setText(list_tblKeHoach.get(position)
					.getDienTa());
			if (list_tblNhom.get(position).getLoai_Id().toString()
					.equals("0")) {
				holder.sotien.setTextColor(Color.parseColor("#006600"));
			}
			if (list_tblNhom.get(position).getLoai_Id().toString()
					.equals("1")) {
				holder.sotien.setTextColor(Color.parseColor("#CC0000"));
			}
			holder.sotien.setText(tien.format(list_tblKeHoach
					.get(position).getSoTien()));
			int stt = Integer.parseInt(list_tblNhom.get(position).getAnh());
			holder.anhnhom.setImageResource(Anh[stt]);
			tongthuthang = 0;
			tongchithang = 0;
			luuthang = -1;
		} else {
			LayoutInflater inflater = ((Activity) context)
					.getLayoutInflater();
			obj = inflater.inflate(R.layout.custom_listview_giaodich,
					parent, false);
			holder = new Holder();

			holder.anhnhom = (ImageView) obj
					.findViewById(R.id.imgAnhNhom_CL_GD);
			holder.tennhom = (TextView) obj
					.findViewById(R.id.tvTenNhom_CL_GD);
			holder.ghichu = (TextView) obj
					.findViewById(R.id.tvGhiChu_CL_GD);
			holder.sotien = (TextView) obj
					.findViewById(R.id.tvSoTien_CL_GD);

			obj.setTag(holder);

			holder.tennhom.setText(list_tblNhom.get(position).getTenNhom());
			holder.ghichu.setText(list_tblKeHoach.get(position)
					.getDienTa());
			if (list_tblNhom.get(position).getLoai_Id().toString()
					.equals("0")) {
				holder.sotien.setTextColor(Color.parseColor("#006600"));
			}
			if (list_tblNhom.get(position).getLoai_Id().toString()
					.equals("1")) {
				holder.sotien.setTextColor(Color.parseColor("#CC0000"));
			}
			holder.sotien.setText(tien.format(list_tblKeHoach
					.get(position).getSoTien()));
			int stt = Integer.parseInt(list_tblNhom.get(position).getAnh());
			holder.anhnhom.setImageResource(Anh[stt]);
			tongthuthang = 0;
			tongchithang = 0;
			luuthang = -1;
		}
		return obj;
	}
	
	public void TimKiem(String giatri) {
		giatri = giatri.toLowerCase(Locale.getDefault());
		list_tblKeHoach.clear();
		if (giatri.length() == 0) {
			list_tblKeHoach.addAll(arr_tblKeHoach);
		} else {
			for (int i = 0; i < arr_tblKeHoach.size(); i++) {
				if(arr_tblKeHoach.get(i).getDienTa().toString().toLowerCase(Locale.getDefault())
						.contains(giatri)){
					list_tblKeHoach.add(arr_tblKeHoach.get(i));
				}
			}
		}
		notifyDataSetChanged();
	}
}
