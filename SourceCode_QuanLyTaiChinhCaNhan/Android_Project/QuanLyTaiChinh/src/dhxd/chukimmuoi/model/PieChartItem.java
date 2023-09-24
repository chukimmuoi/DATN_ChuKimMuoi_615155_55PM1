
package dhxd.chukimmuoi.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

import dhxd.chukimmuoi.qltc.R;

public class PieChartItem extends ChartItem {

    private Typeface mTf;
    private String chuogiua;
    public PieChartItem(ChartData<?> cd, Context c, String chuogiua) {
        super(cd);

        mTf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");
        this.chuogiua = chuogiua;
    }

    @Override
    public int getItemType() {
        return TYPE_PIECHART;
    }

    @SuppressLint("InflateParams") 
    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_piechart, null);
            holder.chart = (PieChart) convertView.findViewById(R.id.chart);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // apply styling
        holder.chart.setValueTypeface(mTf);
        holder.chart.setDescription("Biểu đồ tròn");
        holder.chart.setHoleRadius(60f);
        holder.chart.setHoleColor(Color.rgb(255, 255, 255));
        holder.chart.setValueTextColor(Color.rgb(0,0,0));
        holder.chart.setTransparentCircleRadius(65f);
        holder.chart.setCenterText(chuogiua);
        holder.chart.setCenterTextTypeface(mTf);
        holder.chart.setCenterTextSize(15f);
        holder.chart.setDrawXValues(false);
        holder.chart.setUsePercentValues(true);

        // set data
        holder.chart.setData((PieData) mChartData);

        Legend l = holder.chart.getLegend();
        l.setPosition(LegendPosition.RIGHT_OF_CHART);

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chart.animateXY(900, 900);

        return convertView;
    }

    private static class ViewHolder {
        PieChart chart;
    }
}
