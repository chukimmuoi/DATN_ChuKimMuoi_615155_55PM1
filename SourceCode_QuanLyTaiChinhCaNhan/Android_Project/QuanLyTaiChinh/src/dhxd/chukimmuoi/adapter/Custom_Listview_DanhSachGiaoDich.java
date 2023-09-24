package dhxd.chukimmuoi.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import dhxd.chukimmuoi.model.tbl_giaodich;
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

public class Custom_Listview_DanhSachGiaoDich extends
		ArrayAdapter<tbl_giaodich> {
	private Context context;
	private int layoutId;
	private ArrayList<tbl_giaodich> list_tblGiaoDich;
	private ArrayList<tbl_giaodich> arr_tblGiaoDich;
	private ArrayList<tbl_nhom> list_tblNhom;
	private ArrayList<tbl_giaodich> giaodichdautien;
	static final int[] Anh = Icon_MenuItem.danhsachanhchon;
	private double tongthu = 0;
	private double tongchi = 0;
	private double tongthungay = 0;
	private double tongchingay = 0;
	private int luungay = 0;

	DecimalFormat tien = new DecimalFormat("###,###,### đ");

	public Custom_Listview_DanhSachGiaoDich(Context context, int resource,
			ArrayList<tbl_giaodich> objects, ArrayList<tbl_nhom> objects1) {
		super(context, resource, objects);
		this.context = context;
		this.layoutId = resource;
		this.list_tblGiaoDich = objects;
		this.list_tblNhom = objects1;

		this.arr_tblGiaoDich = new ArrayList<tbl_giaodich>();
		this.arr_tblGiaoDich.addAll(list_tblGiaoDich);
//		TinhTongTien();
//		GiaoDichDauTien();
	}

	private static class Holder {
		TextView thunhap;
		TextView chitieu;
		TextView tong;

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
		for (int i = 1; i < list_tblGiaoDich.size(); i++) {
			if (list_tblNhom.get(i).getLoai_Id().toString().equals("0")) {
				tongthu = tongthu + list_tblGiaoDich.get(i).getSoTien();
			} else {
				if (list_tblNhom.get(i).getLoai_Id().toString().equals("1")) {
					tongchi = tongchi + list_tblGiaoDich.get(i).getSoTien();
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void GiaoDichDauTien() {
		giaodichdautien = new ArrayList<tbl_giaodich>();
		for (int i = 1; i < list_tblGiaoDich.size(); i++) {
			if (list_tblGiaoDich.get(i).getNgayThang().getDate() != luungay) {
				luungay = list_tblGiaoDich.get(i).getNgayThang().getDate();
				giaodichdautien.add(list_tblGiaoDich.get(i));
			}
		}
		luungay = 0;
	}

	private boolean kiemtragiaodich(tbl_giaodich GD) {
		boolean ketqua = false;
		for (int i = 0; i < giaodichdautien.size(); i++) {
			if (giaodichdautien.get(i).getGiaoDich_Id() == GD.getGiaoDich_Id()) {
				ketqua = true;
			}
		}
		return ketqua;
	}

	@Override
	public int getCount() {
		return list_tblGiaoDich.size();
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		TinhTongTien();
		GiaoDichDauTien();
		
		View obj = convertView;
		Holder holder;
		if (position == 0) {
			// if (obj1 == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			obj = inflater.inflate(layoutId, parent, false);
			holder = new Holder();
			holder.thunhap = (TextView) obj
					.findViewById(R.id.tvThuNhap_CL_GD_TQ);
			holder.chitieu = (TextView) obj
					.findViewById(R.id.tvChiTieu_CL_GD_TQ);
			holder.tong = (TextView) obj.findViewById(R.id.tvTong_CL_GD_TQ);

			obj.setTag(holder);
			// } else {
			// holder = (Holder) obj1.getTag();
			// }
			// try {
			holder.thunhap.setText(tien.format(tongthu));
			holder.chitieu.setText(tien.format(tongchi));
			if ((tongthu - tongchi) > 0) {
				holder.tong.setTextColor(Color.parseColor("#006600"));
			} else {
				holder.tong.setTextColor(Color.parseColor("#CC0000"));
			}
			holder.tong.setText(tien.format(tongthu - tongchi));
			tongthungay = 0;
			tongchingay = 0;
			// } catch (Exception e) {
			// tongthungay = 0;
			// tongchingay = 0;
			// }
			// return obj;
		} else {

			if (kiemtragiaodich(list_tblGiaoDich.get(position))) {
				luungay = list_tblGiaoDich.get(position).getNgayThang()
						.getDate();
				for (int i = 1; i < list_tblGiaoDich.size(); i++) {
					if (list_tblGiaoDich.get(i).getNgayThang().getDate() == luungay) {
						if (list_tblNhom.get(i).getLoai_Id().toString()
								.equals("0")) {
							tongthungay = tongthungay
									+ list_tblGiaoDich.get(i).getSoTien();
						} else {
							if (list_tblNhom.get(i).getLoai_Id().toString()
									.equals("1")) {
								tongchingay = tongchingay
										+ list_tblGiaoDich.get(i).getSoTien();
							}
						}
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

				if (list_tblGiaoDich.get(position).getNgayThang().getDay() != 0) {
					holder.thu.setText("Thứ "
							+ (list_tblGiaoDich.get(position).getNgayThang()
									.getDay() + 1));
				} else {
					holder.thu.setText("Chủ nhật");
				}
				holder.ngay.setText(""
						+ list_tblGiaoDich.get(position).getNgayThang()
								.getDate() + "");
				holder.thangnam.setText("Tháng "
						+ (list_tblGiaoDich.get(position).getNgayThang()
								.getMonth() + 1)
						+ " năm "
						+ (list_tblGiaoDich.get(position).getNgayThang()
								.getYear() + 1900) + "");
				if ((tongthungay - tongchingay) > 0) {
					holder.tongtientrongngay.setTextColor(Color
							.parseColor("#006600"));
				} else {
					holder.tongtientrongngay.setTextColor(Color
							.parseColor("#CC0000"));
				}
				holder.tongtientrongngay.setText(tien.format(tongthungay
						- tongchingay));

				holder.tennhom.setText(list_tblNhom.get(position).getTenNhom());
				holder.ghichu.setText(list_tblGiaoDich.get(position)
						.getGhiChu());
				if (list_tblNhom.get(position).getLoai_Id().toString()
						.equals("0")) {
					holder.sotien.setTextColor(Color.parseColor("#006600"));
				}
				if (list_tblNhom.get(position).getLoai_Id().toString()
						.equals("1")) {
					holder.sotien.setTextColor(Color.parseColor("#CC0000"));
				}
				holder.sotien.setText(tien.format(list_tblGiaoDich
						.get(position).getSoTien()));
				int stt = Integer.parseInt(list_tblNhom.get(position).getAnh());
				holder.anhnhom.setImageResource(Anh[stt]);
				tongthungay = 0;
				tongchingay = 0;
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
				holder.ghichu.setText(list_tblGiaoDich.get(position)
						.getGhiChu());
				if (list_tblNhom.get(position).getLoai_Id().toString()
						.equals("0")) {
					holder.sotien.setTextColor(Color.parseColor("#006600"));
				}
				if (list_tblNhom.get(position).getLoai_Id().toString()
						.equals("1")) {
					holder.sotien.setTextColor(Color.parseColor("#CC0000"));
				}
				holder.sotien.setText(tien.format(list_tblGiaoDich
						.get(position).getSoTien()));
				int stt = Integer.parseInt(list_tblNhom.get(position).getAnh());
				holder.anhnhom.setImageResource(Anh[stt]);
				tongthungay = 0;
				tongchingay = 0;
			}
		}
		tongthu = 0;
		tongchi = 0;
		return obj;
	}
	
	public void TimKiem(String giatri) {
		giatri = giatri.toLowerCase(Locale.getDefault());
		list_tblGiaoDich.clear();
		if (giatri.length() == 0) {
			list_tblGiaoDich.addAll(arr_tblGiaoDich);
		} else {
			tbl_giaodich tblGD0 = new tbl_giaodich();
			list_tblGiaoDich.add(tblGD0);
			
			for (int i = 1; i < arr_tblGiaoDich.size(); i++) {
				if(arr_tblGiaoDich.get(i).getGhiChu().toString().toLowerCase(Locale.getDefault())
						.contains(giatri)){
					list_tblGiaoDich.add(arr_tblGiaoDich.get(i));
				}
			}
		}
		notifyDataSetChanged();
	}
}
