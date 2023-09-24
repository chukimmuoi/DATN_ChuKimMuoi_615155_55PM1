package dhxd.chukimmuoi.adapter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dhxd.chukimmuoi.model.tbl_giaodich;
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
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class Custom_Listview_DanhSachKeHoachNganSach extends
		ArrayAdapter<tbl_kehoach> {
	private Context context;
	private int layoutId;
	private ArrayList<tbl_kehoach> list_tblKeHoach;
	private ArrayList<tbl_kehoach> arr_tblKeHoach;
	private ArrayList<tbl_giaodich> list_tblGiaoDich;
	private ArrayList<tbl_giaodich> arr_tblGiaoDich;
	private ArrayList<tbl_nhom> list_tblNhom;
	private ArrayList<tbl_nhom> arr_tblNhom;
	static final int[] Anh = Icon_MenuItem.danhsachanhchon;
	private int luu_kehoach_id = -1;
	private double tongtien = 0;
	private Date ngayhientai = new Date();
	final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	DecimalFormat tien = new DecimalFormat("###,###,### đ");
	Calendar c1 = Calendar.getInstance();
	Calendar c2 = Calendar.getInstance();

	public Custom_Listview_DanhSachKeHoachNganSach(Context context,
			int resource, ArrayList<tbl_kehoach> list_tblKeHoach,
			ArrayList<tbl_giaodich> list_tblGiaoDich,
			ArrayList<tbl_nhom> list_tblNhom) {
		super(context, resource, list_tblKeHoach);
		this.context = context;
		this.layoutId = resource;
		this.list_tblKeHoach = list_tblKeHoach;
		this.list_tblGiaoDich = list_tblGiaoDich;
		this.list_tblNhom = list_tblNhom;

		this.arr_tblKeHoach = new ArrayList<tbl_kehoach>();
		this.arr_tblKeHoach.addAll(list_tblKeHoach);

		this.arr_tblGiaoDich = new ArrayList<tbl_giaodich>();
		this.arr_tblGiaoDich.addAll(list_tblGiaoDich);

		this.arr_tblNhom = new ArrayList<tbl_nhom>();
		this.arr_tblNhom.addAll(list_tblNhom);
	}

	private static class Holder {
		ImageView anhnhom;
		TextView tennhom;
		TextView ghichu;
		TextView ngaythang;
		TextView sotiendudinh;
		TextView sotienthucthe;
		TextView sotienconlai;
		TextView songayconlai;
		TextView tieudesotiendudinh;
		TextView tieudesotienthucte;
		TextView ketqua;
	}

	@Override
	public int getCount() {
		return list_tblKeHoach.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View obj = convertView;
		Holder holder;

		if (list_tblKeHoach.get(position).getKeHoach_Id() != luu_kehoach_id) {
			luu_kehoach_id = list_tblKeHoach.get(position).getKeHoach_Id();
			for (int i = 0; i < list_tblKeHoach.size(); i++) {
				if (list_tblKeHoach.get(i).getKeHoach_Id() == luu_kehoach_id) {
					tongtien = tongtien + list_tblGiaoDich.get(i).getSoTien();
				}
			}

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			obj = inflater.inflate(layoutId, parent, false);
			holder = new Holder();

			holder.anhnhom = (ImageView) obj
					.findViewById(R.id.imgAnhNhom_CL_KHNS);
			holder.tennhom = (TextView) obj
					.findViewById(R.id.tvTenNhom_CL_KHNS);
			holder.ghichu = (TextView) obj.findViewById(R.id.tvGhiChu_CL_KHNS);
			holder.ngaythang = (TextView) obj
					.findViewById(R.id.tvNgayThang_CL_KHNS);
			holder.sotiendudinh = (TextView) obj
					.findViewById(R.id.tvMucTieu_CL_KHNS);
			holder.sotienthucthe = (TextView) obj
					.findViewById(R.id.tvDaDat_CL_KHNS);
			holder.sotienconlai = (TextView) obj
					.findViewById(R.id.tvConLai_CL_KHNS);
			holder.songayconlai = (TextView) obj
					.findViewById(R.id.tvSoNgayConLai_CL_KHNS);
			holder.tieudesotiendudinh = (TextView) obj
					.findViewById(R.id.tvTieuDeMucTieu_CL_KHNS);
			holder.tieudesotienthucte = (TextView) obj
					.findViewById(R.id.tvTieuDeDaDat_CL_KHNS);
			holder.ketqua = (TextView) obj.findViewById(R.id.tvKetQua_CL_KHNS);
			obj.setTag(holder);

			holder.tennhom.setText(list_tblNhom.get(position).getTenNhom());
			int stt = Integer.parseInt(list_tblNhom.get(position).getAnh());
			holder.anhnhom.setImageResource(Anh[stt]);
			holder.ghichu.setText(list_tblKeHoach.get(position).getDienTa());
			holder.ngaythang.setText(""
					+ formatter.format(list_tblKeHoach.get(position)
							.getNgayBatDau())
					+ " - "
					+ formatter.format(list_tblKeHoach.get(position)
							.getNgayKetThuc()) + "");
			holder.sotiendudinh.setText(""
					+ tien.format(list_tblKeHoach.get(position).getSoTien())
					+ "");
			holder.sotienthucthe.setText("" + tien.format(tongtien) + "");
			holder.sotienconlai
					.setText(""
							+ tien.format((list_tblKeHoach.get(position)
									.getSoTien() - tongtien)) + "");
			c2.setTime(list_tblKeHoach.get(position).getNgayKetThuc());
			c1.setTime(ngayhientai);
			if (((c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000)) >= 0) {
				holder.songayconlai
						.setText("Còn "
								+ ((c2.getTime().getTime() - c1.getTime()
										.getTime()) / (24 * 3600 * 1000))
								+ " ngày");
			} else {
				holder.songayconlai.setText("Đã hết hạn");
			}
			if (list_tblKeHoach.get(position).getLoai_Id() == 0) {
				holder.tieudesotiendudinh.setText("Mục tiêu");
				holder.tieudesotienthucte.setText("Đã đạt");
				holder.tieudesotienthucte.setTextColor(Color
						.parseColor("#006600"));
				holder.sotienthucthe.setTextColor(Color.parseColor("#006600"));
				if ((list_tblKeHoach.get(position).getSoTien() - tongtien) > 0) {
					holder.ketqua.setText("Cần thêm");
					holder.sotienconlai.setTextColor(Color
							.parseColor("#CC0000"));
				}
				if ((list_tblKeHoach.get(position).getSoTien() - tongtien) == 0) {
					holder.ketqua.setText("Đạt mục tiêu");
					holder.sotienconlai.setTextColor(Color
							.parseColor("#006600"));
				}
				if ((list_tblKeHoach.get(position).getSoTien() - tongtien) < 0) {
					holder.ketqua.setText("Vượt kế hoạch");
					holder.sotienconlai.setTextColor(Color
							.parseColor("#006600"));
					holder.sotienconlai.setText("+"
							+ tien.format(((list_tblKeHoach.get(position)
									.getSoTien() - tongtien)) * (-1)) + "");
				}
			}
			if (list_tblKeHoach.get(position).getLoai_Id() == 1) {
				holder.tieudesotiendudinh.setText("Ngân sách");
				holder.tieudesotienthucte.setText("Đã chi");
				holder.tieudesotienthucte.setTextColor(Color
						.parseColor("#CC0000"));
				holder.sotienthucthe.setTextColor(Color.parseColor("#CC0000"));
				if ((list_tblKeHoach.get(position).getSoTien() - tongtien) > 0) {
					holder.ketqua.setText("Còn lại");
					holder.sotienconlai.setTextColor(Color
							.parseColor("#006600"));
				}
				if ((list_tblKeHoach.get(position).getSoTien() - tongtien) == 0) {
					holder.ketqua.setText("Đạt mục tiêu");
					holder.sotienconlai.setTextColor(Color
							.parseColor("#006600"));
				}
				if ((list_tblKeHoach.get(position).getSoTien() - tongtien) < 0) {
					holder.ketqua.setText("Vượt ngân sách");
					holder.sotienconlai.setTextColor(Color
							.parseColor("#CC0000"));
				}

			}
			tongtien = 0;
		} else {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			obj = inflater.inflate(R.layout.test1, parent, false);
			
			tongtien = 0;
		}
		
		if(position == (list_tblKeHoach.size() - 1))
		{
			luu_kehoach_id = -1;
		}
		return obj;
	}

	public void TimKiem(String giatri) {
		giatri = giatri.toLowerCase(Locale.getDefault());
		list_tblKeHoach.clear();
		list_tblGiaoDich.clear();
		list_tblNhom.clear();
		if (giatri.length() == 0) {
			list_tblKeHoach.addAll(arr_tblKeHoach);
			list_tblGiaoDich.addAll(arr_tblGiaoDich);
			list_tblNhom.addAll(arr_tblNhom);
		} else {
			for (int i = 0; i < arr_tblKeHoach.size(); i++) {
				if (arr_tblNhom.get(i).getTenNhom().toString()
						.toLowerCase(Locale.getDefault()).contains(giatri)) {
					list_tblKeHoach.add(arr_tblKeHoach.get(i));
					list_tblGiaoDich.add(arr_tblGiaoDich.get(i));
					list_tblNhom.add(arr_tblNhom.get(i));
				}
			}
			luu_kehoach_id = -1;
		}
		notifyDataSetChanged();
	}
}
