package dhxd.chukimmuoi.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import dhxd.chukimmuoi.model.ChartItem;
import dhxd.chukimmuoi.model.PieChartItem;
import dhxd.chukimmuoi.model.list_nhom_bc;
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
import android.widget.ListView;
import android.widget.TextView;

public class Custom_Listview_DanhSach_BaoCaoThang extends
		ArrayAdapter<list_nhom_bc> {
	private Context context;
	private int layoutId;
	private ArrayList<list_nhom_bc> list_tblNhomBaoCao;
	private ArrayList<list_nhom_bc> list_tblNhomBaoCaoThu;
	private ArrayList<list_nhom_bc> list_tblNhomBaoCaoChi;
	static final int[] Anh = Icon_MenuItem.danhsachanhchon;
	private double tongthu = 0;
	private double tongchi = 0;
	private int stt = 0;
	DecimalFormat tien = new DecimalFormat("###,###,### đ");

	public Custom_Listview_DanhSach_BaoCaoThang(Context context, int resource,
			ArrayList<list_nhom_bc> list_tblNhomBaoCao, int stt,
			ArrayList<list_nhom_bc> list_tblNhomBaoCaoThu,
			ArrayList<list_nhom_bc> list_tblNhomBaoCaoChi) {
		super(context, resource, list_tblNhomBaoCao);
		this.context = context;
		this.layoutId = resource;
		this.list_tblNhomBaoCao = list_tblNhomBaoCao;
		this.list_tblNhomBaoCaoThu = list_tblNhomBaoCaoThu;
		this.list_tblNhomBaoCaoChi = list_tblNhomBaoCaoChi;

		this.stt = stt;
	}

	private void TinhTongTien() {
		for (int i = 0; i < list_tblNhomBaoCao.size(); i++) {
			if (list_tblNhomBaoCao.get(i).getLoai_Id().toString().equals("0")) {
				tongthu = tongthu + list_tblNhomBaoCao.get(i).getTongTien();
			} else {
				if (list_tblNhomBaoCao.get(i).getLoai_Id().toString()
						.equals("1")) {
					tongchi = tongchi + list_tblNhomBaoCao.get(i).getTongTien();
				}
			}

		}
	}

	private static class Holder {
		TextView thunhap;
		TextView chitieu;
		TextView tong;

		TextView tennhom;
		TextView ghichu;
		TextView sotien;
		ImageView anhnhom;

		ListView bieudo;
		TextView tieude_bieudo;
		TextView sotien_bieudo;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TinhTongTien();
		View obj = convertView;
		Holder holder;
		if (position == 0) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			obj = inflater.inflate(layoutId, parent, false);
			holder = new Holder();

			holder.thunhap = (TextView) obj
					.findViewById(R.id.tvThuNhap_CL_BC_BCTQ);
			holder.chitieu = (TextView) obj
					.findViewById(R.id.tvChiTieu_CL_BC_BCTQ);
			holder.tong = (TextView) obj.findViewById(R.id.tvTong_CL_BC_BCTQ);

			obj.setTag(holder);

			holder.thunhap.setText(tien.format(tongthu));
			holder.chitieu.setText(tien.format(tongchi));
			holder.tong.setText(tien.format(tongthu - tongchi));
			if((tongthu - tongchi) > 0)
			{
				holder.tong.setTextColor(Color.parseColor("#006600"));
			}
			else
			{
				holder.tong.setTextColor(Color.parseColor("#CC0000"));
			}

		} else {
			if (position == 1) {
				LayoutInflater inflater = ((Activity) context)
						.getLayoutInflater();
				obj = inflater.inflate(R.layout.custom_listview_bieudotron,
						parent, false);
				holder = new Holder();
				holder.bieudo = (ListView) obj
						.findViewById(R.id.listview_bieudotron);
				holder.tieude_bieudo = (TextView) obj
						.findViewById(R.id.tvTieuDe_CL_BDT);
				holder.sotien_bieudo = (TextView) obj
						.findViewById(R.id.tvSoTienThuChi_CL_BDT);
				obj.setTag(holder);

				ArrayList<ChartItem> list = new ArrayList<ChartItem>();
				list.add(new PieChartItem(generateDataPie(
						list_tblNhomBaoCaoThu, "Nhóm thu"), context, "Thống kê\nthu nhập"));
				ChartDataAdapter cda = new ChartDataAdapter(context, list);
				holder.bieudo.setAdapter(cda);

				holder.tieude_bieudo.setText("THU NHẬP");
				holder.sotien_bieudo.setText(tien.format(tongthu));
				holder.sotien_bieudo.setTextColor(Color.parseColor("#006600"));
				holder.tieude_bieudo.setTextColor(Color.parseColor("#006600"));
			} else {
				if (position == stt) {
					LayoutInflater inflater = ((Activity) context)
							.getLayoutInflater();
					obj = inflater.inflate(R.layout.custom_listview_bieudotron,
							parent, false);
					holder = new Holder();
					holder.bieudo = (ListView) obj
							.findViewById(R.id.listview_bieudotron);
					holder.tieude_bieudo = (TextView) obj
							.findViewById(R.id.tvTieuDe_CL_BDT);
					holder.sotien_bieudo = (TextView) obj
							.findViewById(R.id.tvSoTienThuChi_CL_BDT);
					obj.setTag(holder);

					ArrayList<ChartItem> list = new ArrayList<ChartItem>();
					list.add(new PieChartItem(generateDataPie(
							list_tblNhomBaoCaoChi, "Nhóm chi"), context, "Thống kê\nchi tiêu"));
					ChartDataAdapter cda = new ChartDataAdapter(context, list);
					holder.bieudo.setAdapter(cda);

					holder.tieude_bieudo.setText("CHI TIÊU");
					holder.sotien_bieudo.setText(tien.format(tongchi));
					holder.sotien_bieudo.setTextColor(Color.parseColor("#CC0000"));
					holder.tieude_bieudo.setTextColor(Color.parseColor("#CC0000"));
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

					holder.tennhom.setText(list_tblNhomBaoCao.get(position)
							.getTenNhom());
					holder.ghichu.setVisibility(View.GONE);
					if (list_tblNhomBaoCao.get(position).getLoai_Id()
							.toString().equals("0")) {
						holder.sotien.setTextColor(Color.parseColor("#006600"));
					}
					if (list_tblNhomBaoCao.get(position).getLoai_Id()
							.toString().equals("1")) {
						holder.sotien.setTextColor(Color.parseColor("#CC0000"));
					}
					holder.sotien.setText(tien.format(list_tblNhomBaoCao.get(
							position).getTongTien()));
					int stt = Integer.parseInt(list_tblNhomBaoCao.get(position)
							.getAnh());
					holder.anhnhom.setImageResource(Anh[stt]);
				}
			}
		}
		tongthu = 0;
		tongchi = 0;
		return obj;
	}

	private PieData generateDataPie(ArrayList<list_nhom_bc> listNhom,
			String mucluc) {

		ArrayList<Entry> yVals1 = new ArrayList<Entry>();

		for (int i = 0; i < listNhom.size(); i++) {
			float giatri = (float) listNhom.get(i).getTongTien();
			yVals1.add(new Entry(giatri, i));
		}

		ArrayList<String> xVals = new ArrayList<String>();

		for (int i = 0; i < listNhom.size(); i++)
			xVals.add(listNhom.get(i).getTenNhom().toString());

		PieDataSet set1 = new PieDataSet(yVals1, mucluc);
		set1.setSliceSpace(3f);

		// add a lot of colors

		ArrayList<Integer> colors = new ArrayList<Integer>();

		for (int c : ColorTemplate.VORDIPLOM_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.JOYFUL_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.COLORFUL_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.LIBERTY_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.PASTEL_COLORS)
			colors.add(c);

		colors.add(ColorTemplate.getHoloBlue());

		set1.setColors(colors);

		PieData data = new PieData(xVals, set1);
		return data;
	}

	private class ChartDataAdapter extends ArrayAdapter<ChartItem> {

		public ChartDataAdapter(Context context, List<ChartItem> objects) {
			super(context, 0, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getItem(position).getView(position, convertView,
					getContext());
		}

		@Override
		public int getItemViewType(int position) {
			return getItem(position).getItemType();
		}

		@Override
		public int getViewTypeCount() {
			return 1;
		}
	}
}
