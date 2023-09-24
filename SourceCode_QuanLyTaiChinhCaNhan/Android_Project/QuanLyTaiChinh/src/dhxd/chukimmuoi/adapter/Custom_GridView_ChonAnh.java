package dhxd.chukimmuoi.adapter;

import dhxd.chukimmuoi.qltc.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class Custom_GridView_ChonAnh extends BaseAdapter {
	private Context context;
	private final int[] anh;

	public Custom_GridView_ChonAnh(Context context, int[] anh) {
		super();
		this.context = context;
		this.anh = anh;
	}

	@Override
	public int getCount() {
		return anh.length;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	private static class Holder {
		ImageView anh;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View obj = arg1;
		Holder holder;
		if (obj == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			obj = inflater.inflate(R.layout.custom_listview_danhsachanh, arg2,
					false);
			holder = new Holder();

			holder.anh = (ImageView) obj.findViewById(R.id.imgbtnAnh_CL_DSA);
			obj.setTag(holder);
		} else {
			holder = (Holder) obj.getTag();
		}
		holder.anh.setImageResource(anh[arg0]);
		return obj;
	}

}
