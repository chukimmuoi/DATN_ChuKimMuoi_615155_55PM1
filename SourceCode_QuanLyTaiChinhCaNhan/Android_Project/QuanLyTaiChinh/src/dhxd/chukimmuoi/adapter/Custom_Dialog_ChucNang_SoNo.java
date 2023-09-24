package dhxd.chukimmuoi.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dhxd.chukimmuoi.model.item_chucnang;
import dhxd.chukimmuoi.qltc.R;
import dhxd.chukimmuoi.utils.Icon_MenuItem;

public class Custom_Dialog_ChucNang_SoNo extends ArrayAdapter<item_chucnang> {

	private Context context;
	private int layoutId;
	private ArrayList<item_chucnang> list_itemChuNang;
	static final int[] Anh = Icon_MenuItem.anhchucnang_sono;

	public Custom_Dialog_ChucNang_SoNo(Context context, int resource,
			ArrayList<item_chucnang> objects) {
		super(context, resource, objects);
		this.context = context;
		this.layoutId = resource;
		this.list_itemChuNang = objects;
	}

	private static class Holder {
		ImageView anh;
		TextView tenchucnang;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View obj = convertView;
		Holder holder;
		if (obj == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			obj = inflater.inflate(layoutId, parent, false);
			holder = new Holder();

			holder.anh = (ImageView) obj.findViewById(R.id.imgAnhChuNang_CD_CN);
			holder.tenchucnang = (TextView) obj
					.findViewById(R.id.tvTenChucNang_CD_CN);

			obj.setTag(holder);
		} else {
			holder = (Holder) obj.getTag();
		}
		holder.tenchucnang.setText(list_itemChuNang.get(position)
				.getTenChucNang());
		int stt = Integer.parseInt(list_itemChuNang.get(position).getAnh());
		holder.anh.setImageResource(Anh[stt]);

		return obj;
	}
}

