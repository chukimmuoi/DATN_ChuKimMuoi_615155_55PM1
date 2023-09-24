package dhxd.chukimmuoi.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import dhxd.chukimmuoi.model.BarChartItem;

import dhxd.chukimmuoi.model.ChartItem;

import dhxd.chukimmuoi.model.list_baocaonam;
import dhxd.chukimmuoi.qltc.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Custom_Listview_Baocao_BaoCaoTongQuatNam extends
		ArrayAdapter<list_baocaonam> {
	private Context context;
	private int layoutId;
	private ArrayList<list_baocaonam> list_tblBaoCaoNam;
	DecimalFormat tien = new DecimalFormat("###,###,### đ");
	private double tongthu = 0;
	private double tongchi = 0;
	public Custom_Listview_Baocao_BaoCaoTongQuatNam(Context context,
			int resource, ArrayList<list_baocaonam> list_tblBaoCaoNam) {
		super(context, resource, list_tblBaoCaoNam);
		this.context = context;
		this.layoutId = resource;
		this.list_tblBaoCaoNam = list_tblBaoCaoNam;
	}

	private void TinhTien(){
		for (int i = 1; i < list_tblBaoCaoNam.size(); i++) {
			tongthu = list_tblBaoCaoNam.get(i).getTienthu();
			tongchi = list_tblBaoCaoNam.get(i).getTienchi();
		}
	}
	
	private static class Holder {
		ListView bieudo;
		TextView tieude_bieudo;
		TextView sotien_bieudo;
		
		TextView thang;
		TextView nam;
		TextView thunhap;
		TextView chitieu;
		TextView tong;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TinhTien();
		View obj = convertView;
		Holder holder;
		if (position == 0) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			obj = inflater.inflate(layoutId, parent,
					false);
			holder = new Holder();
			holder.bieudo = (ListView) obj
					.findViewById(R.id.listview_bieudotron);
			holder.tieude_bieudo = (TextView) obj
					.findViewById(R.id.tvTieuDe_CL_BDT);
			holder.sotien_bieudo = (TextView) obj
					.findViewById(R.id.tvSoTienThuChi_CL_BDT);
			obj.setTag(holder);
			
			ArrayList<ChartItem> list = new ArrayList<ChartItem>();
			list.add(new BarChartItem(generateDataBar(1), context));
			ChartDataAdapter cda = new ChartDataAdapter(context, list);
			holder.bieudo.setAdapter(cda);
			
			holder.tieude_bieudo.setText(tien.format(tongchi));
			holder.sotien_bieudo.setText(tien.format(tongthu));
			
			holder.tieude_bieudo.setTextColor(Color.parseColor("#CC0000"));
			holder.sotien_bieudo.setTextColor(Color.parseColor("#006600"));
			
		} else {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			obj = inflater.inflate(R.layout.custom_listview_baocao_thang_tongquannam, parent, false);
			holder = new Holder();
			
			holder.thang = (TextView) obj.findViewById(R.id.tvThang_CL_BCT_TQN);
			holder.nam = (TextView) obj.findViewById(R.id.tvNam_CL_BCT_TQN);
			holder.thunhap = (TextView) obj.findViewById(R.id.tvThuNhap_CL_BCT_TQN);
			holder.chitieu = (TextView) obj.findViewById(R.id.tvChiTieu_CL_BCT_TQN);
			holder.tong = (TextView) obj.findViewById(R.id.tvTong_CL_BCT_TQN);
			
			obj.setTag(holder);
			
			holder.thang.setText("Tháng " + list_tblBaoCaoNam.get(position).getThang());
			holder.nam.setText(""+list_tblBaoCaoNam.get(position).getNam()+"");
			holder.thunhap.setText(tien.format(list_tblBaoCaoNam.get(position).getTienthu()));
			holder.chitieu.setText(tien.format(list_tblBaoCaoNam.get(position).getTienchi()));
			holder.tong.setText(tien.format(list_tblBaoCaoNam.get(position).getTienthu() - list_tblBaoCaoNam.get(position).getTienchi()));
			if((list_tblBaoCaoNam.get(position).getTienthu() - list_tblBaoCaoNam.get(position).getTienchi()) > 0)
			{
				holder.tong.setTextColor(Color.parseColor("#006600"));
			}
			else
			{
				holder.tong.setTextColor(Color.parseColor("#CC0000"));
			}
		}
		tongthu = 0;
		tongchi = 0;
		return obj;

	}

	private BarData generateDataBar(int cnt) {
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 1; i < 14; i++) {
        	if(i == 13)
        	{
        		xVals.add(" ");
        	}
        	else
        	{
        		xVals.add("  "+i+"");
        	}
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        

        for (int i = 1; i < list_tblBaoCaoNam.size(); i++) {
            float val = (float) list_tblBaoCaoNam.get(i).getTienthu();
            yVals1.add(new BarEntry(val, i));
        }

        for (int i = 1; i < list_tblBaoCaoNam.size(); i++) {
            float val = (float) list_tblBaoCaoNam.get(i).getTienchi();
            yVals2.add(new BarEntry(val, i));
        }
        BarDataSet set1 = new BarDataSet(yVals1, "Thu nhập");
        set1.setColor(Color.rgb(0,218,0));
        BarDataSet set2 = new BarDataSet(yVals2, "Chi tiêu");
        set2.setColor(Color.rgb(255,0,0));
        
        
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(xVals, dataSets);
        data.setGroupSpace(110f);
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
