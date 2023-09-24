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

public class Custom_Listview_DanhSachSoNo extends ArrayAdapter<tbl_kehoach> {
	private Context context;
	private int layoutId;
	private ArrayList<tbl_kehoach> list_tblKeHoach;
	private ArrayList<tbl_kehoach> arr_tblKeHoach;
	private ArrayList<tbl_kehoach> giaodichdautien;
	private ArrayList<tbl_nhom> list_tblNhom;
	private double tong = 0;
	private int luungay = 0;
	private double tongngay = 0;
	DecimalFormat tien = new DecimalFormat("###,###,### đ");
	static final int[] Anh = Icon_MenuItem.danhsachanhchon;

	public Custom_Listview_DanhSachSoNo(Context context, int resource,
			ArrayList<tbl_kehoach> list_tblKeHoach,
			ArrayList<tbl_nhom> list_tblNhom) {
		super(context, resource, list_tblKeHoach);
		this.context = context;
		this.layoutId = resource;
		this.list_tblKeHoach = list_tblKeHoach;
		this.list_tblNhom = list_tblNhom;

		this.arr_tblKeHoach = new ArrayList<tbl_kehoach>();
		arr_tblKeHoach.addAll(list_tblKeHoach);
	}

	private static class Holder {
		TextView tongtien;

		TextView thu;
		TextView ngay;
		TextView thangnam;
		TextView tongtientrongngay;

		TextView tennhom;
		TextView ghichu;
		TextView sotien;
		ImageView anhnhom;
	}

	private void TinhTongTien() {
		for (int i = 1; i < list_tblKeHoach.size(); i++) {
			tong = tong + list_tblKeHoach.get(i).getSoTien();
		}
	}

	@SuppressWarnings("deprecation")
	private void GiaoDichDauTien() {
		giaodichdautien = new ArrayList<tbl_kehoach>();
		for (int i = 1; i < list_tblKeHoach.size(); i++) {
			if (list_tblKeHoach.get(i).getNgayBatDau().getDate() != luungay) {
				luungay = list_tblKeHoach.get(i).getNgayBatDau().getDate();
				giaodichdautien.add(list_tblKeHoach.get(i));
			}
		}
		luungay = 0;
	}

	private boolean kiemtragiaodich(tbl_kehoach KH) {
		boolean ketqua = false;
		for (int i = 0; i < giaodichdautien.size(); i++) {
			if (giaodichdautien.get(i).getKeHoach_Id() == KH.getKeHoach_Id()) {
				ketqua = true;
			}
		}
		return ketqua;
	}

	@Override
	public int getCount() {
		return list_tblKeHoach.size();
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TinhTongTien();
		GiaoDichDauTien();
		View obj = convertView;
		Holder holder;
		if (position == 0) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			obj = inflater.inflate(layoutId, parent, false);
			holder = new Holder();

			holder.tongtien = (TextView) obj
					.findViewById(R.id.tvTongTien_CL_DSSN);
			obj.setTag(holder);

			holder.tongtien.setText(tien.format(tong));
			if (list_tblKeHoach.get(position).getLoai_Id() == 0) {
				holder.tongtien.setTextColor(Color.parseColor("#006600"));
			} else {
				if (list_tblKeHoach.get(position).getLoai_Id() == 1) {
					holder.tongtien.setTextColor(Color.parseColor("#CC0000"));
				}
			}
		} else {
			if (kiemtragiaodich(list_tblKeHoach.get(position))) {
				luungay = list_tblKeHoach.get(position).getNgayBatDau()
						.getDate();
				for (int i = 1; i < list_tblKeHoach.size(); i++) {
					if (list_tblKeHoach.get(i).getNgayBatDau().getDate() == luungay) {
						tongngay = tongngay
								+ list_tblKeHoach.get(i).getSoTien();
					}
				}

				LayoutInflater inflater = ((Activity) context)
						.getLayoutInflater();
				obj = inflater.inflate(
						R.layout.custom_listview_giaodich_ngaythang, parent,
						false);
				holder = new Holder();

				holder.thu = (TextView) obj.findViewById(R.id.tvThu_CL_GD_NT);
				holder.ngay = (TextView) obj.findViewById(R.id.tvNgay_CL_GD_NT);
				holder.thangnam = (TextView) obj
						.findViewById(R.id.tvThangNam_CL_GD_NT);
				holder.tongtientrongngay = (TextView) obj
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

				if (list_tblKeHoach.get(position).getNgayBatDau().getDay() != 0) {
					holder.thu.setText("Thứ "
							+ (list_tblKeHoach.get(position).getNgayBatDau()
									.getDay() + 1));
				} else {
					holder.thu.setText("Chủ nhật");
				}
				holder.ngay.setText(""
						+ list_tblKeHoach.get(position).getNgayBatDau()
								.getDate() + "");
				holder.thangnam.setText("Tháng "
						+ (list_tblKeHoach.get(position).getNgayBatDau()
								.getMonth() + 1)
						+ " năm "
						+ (list_tblKeHoach.get(position).getNgayBatDau()
								.getYear() + 1900) + "");
				if (list_tblKeHoach.get(position).getLoai_Id() == 0) {
					holder.tongtientrongngay.setTextColor(Color
							.parseColor("#006600"));
				} else {
					if (list_tblKeHoach.get(position).getLoai_Id() == 1) {
						holder.tongtientrongngay.setTextColor(Color
								.parseColor("#CC0000"));
					}
				}
				holder.tongtientrongngay.setText(tien.format(tongngay));

				holder.tennhom.setText(list_tblNhom.get(position).getTenNhom());
				holder.ghichu
						.setText(list_tblKeHoach.get(position).getDienTa());
				if (list_tblNhom.get(position).getLoai_Id().toString()
						.equals("0")) {
					holder.sotien.setTextColor(Color.parseColor("#006600"));
				}
				if (list_tblNhom.get(position).getLoai_Id().toString()
						.equals("1")) {
					holder.sotien.setTextColor(Color.parseColor("#CC0000"));
				}
				holder.sotien.setText(tien.format(list_tblKeHoach.get(position)
						.getSoTien()));
				int stt = Integer.parseInt(list_tblNhom.get(position).getAnh());
				holder.anhnhom.setImageResource(Anh[stt]);
				tongngay = 0;
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
				holder.ghichu
						.setText(list_tblKeHoach.get(position).getDienTa());
				if (list_tblNhom.get(position).getLoai_Id().toString()
						.equals("0")) {
					holder.sotien.setTextColor(Color.parseColor("#006600"));
				}
				if (list_tblNhom.get(position).getLoai_Id().toString()
						.equals("1")) {
					holder.sotien.setTextColor(Color.parseColor("#CC0000"));
				}
				holder.sotien.setText(tien.format(list_tblKeHoach.get(position)
						.getSoTien()));
				int stt = Integer.parseInt(list_tblNhom.get(position).getAnh());
				holder.anhnhom.setImageResource(Anh[stt]);
				tongngay = 0;
			}

		}
		tong = 0;
		return obj;
	}

	public void TimKiem(String giatri) {
		giatri = giatri.toLowerCase(Locale.getDefault());
		list_tblKeHoach.clear();
		if (giatri.length() == 0) {
			list_tblKeHoach.addAll(arr_tblKeHoach);
		} else {
			tbl_kehoach tblKH0 = new tbl_kehoach();
			list_tblKeHoach.add(tblKH0);

			for (int i = 1; i < arr_tblKeHoach.size(); i++) {
				if (arr_tblKeHoach.get(i).getEmailDoiTac().toString()
						.toLowerCase(Locale.getDefault()).contains(giatri)) {
					list_tblKeHoach.add(arr_tblKeHoach.get(i));
				}
			}
		}
		notifyDataSetChanged();
	}
}
