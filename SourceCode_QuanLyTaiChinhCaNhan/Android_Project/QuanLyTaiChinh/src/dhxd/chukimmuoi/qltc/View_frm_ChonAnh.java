package dhxd.chukimmuoi.qltc;

import dhxd.chukimmuoi.adapter.Custom_GridView_ChonAnh;
import dhxd.chukimmuoi.utils.Icon_MenuItem;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;

public class View_frm_ChonAnh extends Activity {
	GridView danhsachanh;
	ImageButton quaylai;
	static final int[] Anh = Icon_MenuItem.danhsachanhchon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_frm_chonanh);

		danhsachanh = (GridView) findViewById(R.id.gridDanhSachAnh_CA);
		quaylai = (ImageButton) findViewById(R.id.imgbtnQuayLai_CA);

		danhsachanh.setAdapter(new Custom_GridView_ChonAnh(
				View_frm_ChonAnh.this, Anh));
		danhsachanh.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Intent myInten = new Intent();
				myInten.putExtra("valuesanh", "" + position + "");
				setResult(RESULT_OK, myInten);
				finish();
			}
		});

		quaylai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myInten = new Intent();
				setResult(RESULT_OK, myInten);
				finish();
			}
		});
	}
}
