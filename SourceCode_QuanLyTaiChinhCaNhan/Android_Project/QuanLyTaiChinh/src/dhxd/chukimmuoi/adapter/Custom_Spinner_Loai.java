package dhxd.chukimmuoi.adapter;

import java.util.List;

import dhxd.chukimmuoi.model.item_spinnerLoai;
import dhxd.chukimmuoi.qltc.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Custom_Spinner_Loai extends ArrayAdapter<item_spinnerLoai> {

	Context context;
	int layoutId;
	List<item_spinnerLoai> spinnerLoaiData;

	public Custom_Spinner_Loai(Context context, int resource,
			List<item_spinnerLoai> objects) {
		super(context, resource, objects);

		this.context = context;
		this.layoutId = resource;
		this.spinnerLoaiData = objects;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return CustomSpinner(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return CustomSpinner(position, convertView, parent);
	}

	private static class view_frm_loai {
		ImageView anh;
		TextView loai;
	}

	public View CustomSpinner(int position, View convertView, ViewGroup parent) {
		View dong = convertView;
		view_frm_loai holder;

		if (dong == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			dong = inflater.inflate(layoutId, parent, false);
			holder = new view_frm_loai();

			holder.anh = (ImageView) dong.findViewById(R.id.imgLoai_CSL);
			holder.loai = (TextView) dong.findViewById(R.id.tvTenLoai_CSL);
			dong.setTag(holder);
		} else {
			holder = (view_frm_loai) dong.getTag();
		}

		item_spinnerLoai itemSpinnerLoai = spinnerLoaiData.get(position);
		holder.anh.setImageDrawable(dong.getResources().getDrawable(
				itemSpinnerLoai.getAnh()));
		holder.loai.setText(itemSpinnerLoai.getTenloai());

		return dong;
	}

}
