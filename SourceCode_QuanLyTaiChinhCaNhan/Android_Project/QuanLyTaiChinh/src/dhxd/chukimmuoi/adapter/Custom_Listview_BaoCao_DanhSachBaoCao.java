package dhxd.chukimmuoi.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dhxd.chukimmuoi.model.tbl_giaodich;
import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.qltc.R;
import dhxd.chukimmuoi.utils.Icon_MenuItem;

public class Custom_Listview_BaoCao_DanhSachBaoCao extends ArrayAdapter<tbl_giaodich> {
	private Context context;
	private int layoutId;
	private ArrayList<tbl_giaodich> list_tblGiaoDich;
	private ArrayList<tbl_giaodich> giaodichdautien;
	private ArrayList<tbl_nhom> list_tblNhom;
	private double tong = 0;
	private int luungay = 0;
	private double tongngay = 0;
	DecimalFormat tien = new DecimalFormat("###,###,### đ");
	static final int[] Anh = Icon_MenuItem.danhsachanhchon;

	public Custom_Listview_BaoCao_DanhSachBaoCao(Context context, int resource,
			ArrayList<tbl_giaodich> list_tblGiaoDich,
			ArrayList<tbl_nhom> list_tblNhom) {
		super(context, resource, list_tblGiaoDich);
		this.context = context;
		this.layoutId = resource;
		this.list_tblGiaoDich = list_tblGiaoDich;
		this.list_tblNhom = list_tblNhom;
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
		
		View mau;
	}

	private void TinhTongTien() {
		for (int i = 1; i < list_tblGiaoDich.size(); i++) {
			tong = tong + list_tblGiaoDich.get(i).getSoTien();
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
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			obj = inflater.inflate(layoutId, parent, false);
			holder = new Holder();

			holder.tongtien = (TextView) obj
					.findViewById(R.id.tvTongTien_CL_DSSN);
			obj.setTag(holder);

			holder.tongtien.setText(tien.format(tong));
			if (list_tblNhom.get(position).getLoai_Id().toString().equals("0")) {
				holder.tongtien.setTextColor(Color.parseColor("#006600"));
			} else {
				if (list_tblNhom.get(position).getLoai_Id().toString().equals("1")) {
					holder.tongtien.setTextColor(Color.parseColor("#CC0000"));
				}
			}
		} else {
			if (kiemtragiaodich(list_tblGiaoDich.get(position))) {
				luungay = list_tblGiaoDich.get(position).getNgayThang()
						.getDate();
				for (int i = 1; i < list_tblGiaoDich.size(); i++) {
					if (list_tblGiaoDich.get(i).getNgayThang().getDate() == luungay) {
						tongngay = tongngay
								+ list_tblGiaoDich.get(i).getSoTien();
					}
				}

				LayoutInflater inflater = ((Activity) context)
						.getLayoutInflater();
				obj = inflater.inflate(
						R.layout.custom_listview_giaodich_ngaythang, parent,
						false);
				holder = new Holder();
				
				holder.mau = (View) obj.findViewById(R.id.view1);
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

				holder.mau.setBackgroundColor(Color.parseColor("#0099CC"));
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
				if (list_tblNhom.get(position).getLoai_Id().toString().equals("0")) {
					holder.tongtientrongngay.setTextColor(Color
							.parseColor("#006600"));
				} else {
					if (list_tblNhom.get(position).getLoai_Id().toString().equals("1")) {
						holder.tongtientrongngay.setTextColor(Color
								.parseColor("#CC0000"));
					}
				}
				holder.tongtientrongngay.setText(tien.format(tongngay));

				holder.tennhom.setText(list_tblNhom.get(position).getTenNhom());
				holder.ghichu
						.setText(list_tblGiaoDich.get(position).getGhiChu());
				if (list_tblNhom.get(position).getLoai_Id().toString()
						.equals("0")) {
					holder.sotien.setTextColor(Color.parseColor("#006600"));
				}
				if (list_tblNhom.get(position).getLoai_Id().toString()
						.equals("1")) {
					holder.sotien.setTextColor(Color.parseColor("#CC0000"));
				}
				holder.sotien.setText(tien.format(list_tblGiaoDich.get(position)
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
						.setText(list_tblGiaoDich.get(position).getGhiChu());
				if (list_tblNhom.get(position).getLoai_Id().toString()
						.equals("0")) {
					holder.sotien.setTextColor(Color.parseColor("#006600"));
				}
				if (list_tblNhom.get(position).getLoai_Id().toString()
						.equals("1")) {
					holder.sotien.setTextColor(Color.parseColor("#CC0000"));
				}
				holder.sotien.setText(tien.format(list_tblGiaoDich.get(position)
						.getSoTien()));
				int stt = Integer.parseInt(list_tblNhom.get(position).getAnh());
				holder.anhnhom.setImageResource(Anh[stt]);
				tongngay = 0;
			}
		}
		tong = 0;
		return obj;
	}
}
