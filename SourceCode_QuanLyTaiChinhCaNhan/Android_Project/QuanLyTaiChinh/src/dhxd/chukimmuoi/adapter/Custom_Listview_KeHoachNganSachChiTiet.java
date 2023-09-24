package dhxd.chukimmuoi.adapter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
import dhxd.chukimmuoi.model.tbl_giaodich;
import dhxd.chukimmuoi.model.tbl_kehoach;
import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.model.tbl_quy;
import dhxd.chukimmuoi.qltc.R;
import dhxd.chukimmuoi.utils.Icon_MenuItem;

@SuppressLint("SimpleDateFormat")
public class Custom_Listview_KeHoachNganSachChiTiet extends
		ArrayAdapter<tbl_kehoach> {
	private Context context;
	private int layoutId;
	private ArrayList<tbl_kehoach> list_tblKeHoach = new ArrayList<tbl_kehoach>();
	private ArrayList<tbl_giaodich> list_tblGiaoDich = new ArrayList<tbl_giaodich>();
	private ArrayList<tbl_nhom> list_tblNhom = new ArrayList<tbl_nhom>();
	private ArrayList<tbl_quy> list_tblQuy = new ArrayList<tbl_quy>();

	private ArrayList<tbl_giaodich> giaodichdautien;
	static final int[] Anh = Icon_MenuItem.danhsachanhchon;
	// private int luu_kehoach_id = -1;
	private double tongtien = 0;
	private Date ngayhientai = new Date();
	final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	DecimalFormat tien = new DecimalFormat("###,###,### đ");
	Calendar c1 = Calendar.getInstance();
	Calendar c2 = Calendar.getInstance();

	private double tongthungay = 0;
	private double tongchingay = 0;
	private int luungay = 0;

	public Custom_Listview_KeHoachNganSachChiTiet(Context context,
			int resource, ArrayList<tbl_kehoach> list_tblKeHoach,
			ArrayList<tbl_giaodich> list_tblGiaoDich,
			ArrayList<tbl_nhom> list_tblNhom, ArrayList<tbl_quy> list_tblQuy,
			int kehoach_id) {
		super(context, resource);
		this.context = context;
		this.layoutId = resource;

		// khoitao();
		//
		// for (int i = 0; i < list_tblKeHoach.size(); i++) {
		// if (list_tblKeHoach.get(i).getKeHoach_Id() == kehoach_id) {
		// this.list_tblKeHoach.add(list_tblKeHoach.get(i));
		// this.list_tblGiaoDich.add(list_tblGiaoDich.get(i));
		// this.list_tblNhom.add(list_tblNhom.get(i));
		// this.list_tblQuy.add(list_tblQuy.get(i));
		// }
		// }

		this.list_tblKeHoach.addAll(list_tblKeHoach);
		this.list_tblGiaoDich.addAll(list_tblGiaoDich);
		this.list_tblNhom.addAll(list_tblNhom);
		this.list_tblQuy.addAll(list_tblQuy);
	}

	// private void khoitao(){
	//
	// tbl_nhom tblN0 = new tbl_nhom();
	// tbl_quy tblQ0 = new tbl_quy();
	// tbl_giaodich tblGD0 = new tbl_giaodich();
	// tbl_kehoach tblKH0 = new tbl_kehoach();
	//
	// list_tblKeHoach.add(tblKH0);
	// list_tblGiaoDich.add(tblGD0);
	// list_tblNhom.add(tblN0);
	// list_tblQuy.add(tblQ0);
	// }

	private static class Holder {
		ImageView anhnhom1;
		TextView tennhom1;
		TextView ghichu1;
		TextView ngaythang;
		TextView sotiendudinh;
		TextView sotienthucthe;
		TextView sotienconlai;
		TextView songayconlai;
		TextView tieudesotiendudinh;
		TextView tieudesotienthucte;
		TextView ketqua;

		TextView thu;
		TextView ngay;
		TextView thangnam;
		TextView tongtientrongngay;

		TextView tennhom2;
		TextView ghichu2;
		TextView sotien;
		ImageView anhnhom2;
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
		return list_tblKeHoach.size();
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		GiaoDichDauTien();

		View obj = convertView;
		Holder holder;
		if (position == 0) {
			// luu_kehoach_id = list_tblKeHoach.get(position).getKeHoach_Id();
			for (int i = 0; i < list_tblKeHoach.size(); i++) {
				// if (list_tblKeHoach.get(i).getKeHoach_Id() == luu_kehoach_id)
				// {
				tongtien = tongtien + list_tblGiaoDich.get(i).getSoTien();
				// }
			}

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			obj = inflater.inflate(layoutId, parent, false);
			holder = new Holder();

			holder.anhnhom1 = (ImageView) obj
					.findViewById(R.id.imgAnhNhom_CL_KHNS);
			holder.tennhom1 = (TextView) obj
					.findViewById(R.id.tvTenNhom_CL_KHNS);
			holder.ghichu1 = (TextView) obj.findViewById(R.id.tvGhiChu_CL_KHNS);
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

			holder.tennhom1.setText(list_tblNhom.get(1).getTenNhom());
			int stt = Integer.parseInt(list_tblNhom.get(1).getAnh());
			holder.anhnhom1.setImageResource(Anh[stt]);
			holder.ghichu1.setText(list_tblKeHoach.get(1).getDienTa());
			holder.ngaythang.setText(""
					+ formatter.format(list_tblKeHoach.get(1).getNgayBatDau())
					+ " - "
					+ formatter.format(list_tblKeHoach.get(1).getNgayKetThuc())
					+ "");
			holder.sotiendudinh.setText(""
					+ tien.format(list_tblKeHoach.get(1).getSoTien()) + "");
			holder.sotienthucthe.setText("" + tien.format(tongtien) + "");
			holder.sotienconlai
					.setText(""
							+ tien.format((list_tblKeHoach.get(1).getSoTien() - tongtien))
							+ "");
			c2.setTime(list_tblKeHoach.get(1).getNgayKetThuc());
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
			if (list_tblKeHoach.get(1).getLoai_Id() == 0) {
				holder.tieudesotiendudinh.setText("Mục tiêu");
				holder.tieudesotienthucte.setText("Đã đạt");
				holder.tieudesotienthucte.setTextColor(Color
						.parseColor("#006600"));
				holder.sotienthucthe.setTextColor(Color.parseColor("#006600"));
				if ((list_tblKeHoach.get(1).getSoTien() - tongtien) > 0) {
					holder.ketqua.setText("Cần thêm");
					holder.sotienconlai.setTextColor(Color
							.parseColor("#CC0000"));
				}
				if ((list_tblKeHoach.get(1).getSoTien() - tongtien) == 0) {
					holder.ketqua.setText("Đạt mục tiêu");
					holder.sotienconlai.setTextColor(Color
							.parseColor("#006600"));
				}
				if ((list_tblKeHoach.get(1).getSoTien() - tongtien) < 0) {
					holder.ketqua.setText("Vượt kế hoạch");
					holder.sotienconlai.setTextColor(Color
							.parseColor("#006600"));
					holder.sotienconlai
							.setText("+"
									+ tien.format(((list_tblKeHoach.get(1)
											.getSoTien() - tongtien)) * (-1))
									+ "");
				}
			}
			if (list_tblKeHoach.get(1).getLoai_Id() == 1) {
				holder.tieudesotiendudinh.setText("Ngân sách");
				holder.tieudesotienthucte.setText("Đã chi");
				holder.tieudesotienthucte.setTextColor(Color
						.parseColor("#CC0000"));
				holder.sotienthucthe.setTextColor(Color.parseColor("#CC0000"));
				if ((list_tblKeHoach.get(1).getSoTien() - tongtien) > 0) {
					holder.ketqua.setText("Còn lại");
					holder.sotienconlai.setTextColor(Color
							.parseColor("#006600"));
				}
				if ((list_tblKeHoach.get(1).getSoTien() - tongtien) == 0) {
					holder.ketqua.setText("Đạt mục tiêu");
					holder.sotienconlai.setTextColor(Color
							.parseColor("#006600"));
				}
				if ((list_tblKeHoach.get(1).getSoTien() - tongtien) < 0) {
					holder.ketqua.setText("Vượt ngân sách");
					holder.sotienconlai.setTextColor(Color
							.parseColor("#CC0000"));
				}
			}
			tongtien = 0;
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

				holder.anhnhom2 = (ImageView) obj
						.findViewById(R.id.imgAnhNhom_CL_GD_NT);
				holder.tennhom2 = (TextView) obj
						.findViewById(R.id.tvTenNhom_CL_GD_NT);
				holder.ghichu2 = (TextView) obj
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

				holder.tennhom2
						.setText(list_tblNhom.get(position).getTenNhom());
				holder.ghichu2.setText(list_tblGiaoDich.get(position)
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
				holder.anhnhom2.setImageResource(Anh[stt]);
				tongthungay = 0;
				tongchingay = 0;
			} else {
				LayoutInflater inflater = ((Activity) context)
						.getLayoutInflater();
				obj = inflater.inflate(R.layout.custom_listview_giaodich,
						parent, false);
				holder = new Holder();

				holder.anhnhom2 = (ImageView) obj
						.findViewById(R.id.imgAnhNhom_CL_GD);
				holder.tennhom2 = (TextView) obj
						.findViewById(R.id.tvTenNhom_CL_GD);
				holder.ghichu2 = (TextView) obj
						.findViewById(R.id.tvGhiChu_CL_GD);
				holder.sotien = (TextView) obj
						.findViewById(R.id.tvSoTien_CL_GD);

				obj.setTag(holder);

				holder.tennhom2
						.setText(list_tblNhom.get(position).getTenNhom());
				holder.ghichu2.setText(list_tblGiaoDich.get(position)
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
				holder.anhnhom2.setImageResource(Anh[stt]);
				tongthungay = 0;
				tongchingay = 0;
			}
		}
		tongtien = 0;
		return obj;
	}
}
