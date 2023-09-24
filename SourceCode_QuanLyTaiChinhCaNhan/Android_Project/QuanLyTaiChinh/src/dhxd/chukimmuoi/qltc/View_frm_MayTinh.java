package dhxd.chukimmuoi.qltc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import dhxd.chukimmuoi.utils.MayTinh;

public class View_frm_MayTinh extends Activity {
	EditText sotien;
	Button bang;
	ImageButton quaylai;
	/**
	 * KIỂM TRA PHÉP TÍNH + - x / VÀ . NẾU FALSE ĐƯỢC INSERT TRUE KHÔNG ĐC
	 * INSERT
	 * */
	boolean pheptinh = false;

	/**
	 * KIỂM TRA DẤU THẬP PHÂN NẾU FALSE ĐƯỢC INSERT NẾU TRUE KHÔNG ĐƯỢC INSERT
	 * */
	boolean thapphan = false;

	/**
	 * LƯU GIÁ TRỊ CỦA thapphan TRƯỚC KHI CLICK VÀO + - x /
	 * */
	boolean luuthapphan = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_frm_maytinh);
		sotien = (EditText) findViewById(R.id.etSoTien_MT);
		sotien.setText("0");

		bang = (Button) findViewById(R.id.btnBang_MT);
		quaylai = (ImageButton) findViewById(R.id.imgbtnQuayLai_MT);
		
		quaylai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String bieuthuc = sotien.getText().toString();
				String[] elementMath = null;
				String ketqua = "0";
				MayTinh caculator = new MayTinh();
				if (bieuthuc.length() > 0) {
					try {
						/**
						 * TÁCH BIỂU THỨC NHẬP VÀO THÀNH CÁC PHẦN THỬ
						 * */
						if (!caculator.check_error)
							elementMath = caculator.processString(bieuthuc);
						/**
						 * CHUYỂN BIỂU THỨC TRUNG TỐ SANG HẬU TỐ
						 * */
						if (!caculator.check_error)
							elementMath = caculator.postfix(elementMath);
						/**
						 * THỰC HIỆN TÍNH GIÁ TRỊ BIỂU THỨC
						 * */
						if (!caculator.check_error)
							ketqua = caculator.valueMath(elementMath);
						sotien.setText(ketqua);
					} catch (Exception e) {
						sotien.setText("0");
						Toast.makeText(getApplicationContext(),
								"Xẩy ra lỗi trong biểu thức của bạn.",
								Toast.LENGTH_SHORT).show();
					}
				}
				
				Intent myIntent = new Intent();
				myIntent.putExtra("anbang", "true");
				myIntent.putExtra("sotien", ketqua);
				setResult(RESULT_OK, myIntent);
				finish();
			}
		});
	}

	public void insertSoTien(String giatri) {
		String chuoi = sotien.getText().toString();
		char kytu = chuoi.charAt(chuoi.length() - 1);
		/**
		 * NẾU GIÁ TRỊ NHẬP VÀO LÀ CÁC + - x / . KIỂM TRA GIÁ TRỊ CUỐI CÙNG CỦA
		 * CHUỖI NẾU CŨNG LÀ = - x / THÌ SẼ KHÔNG CHO INSERT
		 * */
		if (giatri == "+" || giatri == "-" || giatri == "x" || giatri == "/"
				|| giatri == ".") {
			if (kytu == '+' || kytu == '-' || kytu == 'x' || kytu == '/'
					|| kytu == '.') {
				// KHÔNG LÀM GÌ CẢ
			} else {
				if (sotien.getText().toString().equals("0")) {
					sotien.setText(giatri);
				} else {
					sotien.append(giatri);
				}
			}
		} else {
			if (sotien.getText().toString().equals("0")) {
				sotien.setText(giatri);
			} else {
				sotien.append(giatri);
			}
		}
	}

	public void ClickButton(View v) {
		switch (v.getId()) {
		case R.id.btn0_MT:
			insertSoTien("0");
			pheptinh = false;
			bang.setText("=");
			break;
		case R.id.btn000_MT:
			insertSoTien("000");
			pheptinh = false;
			bang.setText("=");
			break;
		case R.id.btn1_MT:
			insertSoTien("1");
			pheptinh = false;
			bang.setText("=");
			break;
		case R.id.btn2_MT:
			insertSoTien("2");
			pheptinh = false;
			bang.setText("=");
			break;
		case R.id.btn3_MT:
			insertSoTien("3");
			pheptinh = false;
			bang.setText("=");
			break;
		case R.id.btn4_MT:
			insertSoTien("4");
			pheptinh = false;
			bang.setText("=");
			break;
		case R.id.btn5_MT:
			insertSoTien("5");
			pheptinh = false;
			bang.setText("=");
			break;
		case R.id.btn6_MT:
			insertSoTien("6");
			pheptinh = false;
			bang.setText("=");
			break;
		case R.id.btn7_MT:
			insertSoTien("7");
			pheptinh = false;
			bang.setText("=");
			break;
		case R.id.btn8_MT:
			insertSoTien("8");
			pheptinh = false;
			bang.setText("=");
			break;
		case R.id.btn9_MT:
			insertSoTien("9");
			pheptinh = false;
			bang.setText("=");
			break;
		case R.id.btnThapPhan_MT:
			if (sotien.getText().toString().equals("0")) {
				if (!pheptinh) {
					if (!thapphan) {
						insertSoTien("0.");
						pheptinh = true;
						thapphan = true;
						bang.setText("=");
					}
				}
				break;
			} else {
				if (!pheptinh) {
					if (!thapphan) {
						insertSoTien(".");
						pheptinh = true;
						thapphan = true;
						bang.setText("=");
					}
				}
				break;
			}
		case R.id.btnCong_MT:
			if (sotien.getText().toString().equals("0")) {
				insertSoTien("0+");
				pheptinh = true;
				luuthapphan = thapphan;
				thapphan = false;
				bang.setText("=");

			} else {
				if (!pheptinh) {
					insertSoTien("+");
					pheptinh = true;
					luuthapphan = thapphan;
					thapphan = false;
					bang.setText("=");
				}
			}
			break;
		case R.id.btnTru_MT:
			if (sotien.getText().toString().equals("0")) {
				insertSoTien("0-");
				pheptinh = true;
				luuthapphan = thapphan;
				thapphan = false;
				bang.setText("=");
			} else {
				if (!pheptinh) {
					insertSoTien("-");
					pheptinh = true;
					luuthapphan = thapphan;
					thapphan = false;
					bang.setText("=");
				}
			}
			break;
		case R.id.btnNhan_MT:
			if (sotien.getText().toString().equals("0")) {
				insertSoTien("0x");
				pheptinh = true;
				luuthapphan = thapphan;
				thapphan = false;
				bang.setText("=");
			} else {
				if (!pheptinh) {
					insertSoTien("x");
					pheptinh = true;
					luuthapphan = thapphan;
					thapphan = false;
					bang.setText("=");
				}
			}
			break;
		case R.id.btnChia_MT:
			if (sotien.getText().toString().equals("0")) {
				insertSoTien("0/");
				pheptinh = true;
				luuthapphan = thapphan;
				thapphan = false;
				bang.setText("=");
			} else {
				if (!pheptinh) {
					insertSoTien("/");
					pheptinh = true;
					luuthapphan = thapphan;
					thapphan = false;
					bang.setText("=");
				}
			}
			break;

		case R.id.btnClear_MT:
			sotien.setText("0");
			thapphan = false;
			pheptinh = false;
			bang.setText(">>");
			break;
		case R.id.btnDel_MT:

			String chuoi = sotien.getText().toString();
			char kytu = chuoi.charAt(chuoi.length() - 1);
			if (kytu == '+' || kytu == '-' || kytu == 'x' || kytu == '/'
					|| kytu == '.') {
				/**
				 * NẾU XÓA CÁC KÝ TỰ + - x / THÌ pheptinh = false CÓ THỂ INSERT
				 * KÝ TỰ + - x / .
				 * */
				pheptinh = false;

				if (kytu == '+' || kytu == '-' || kytu == 'x' || kytu == '/') {
					/**
					 * TRẢ LẠI GIÁ TRỊ thapphan BAN ĐẦU KHI CHƯA GÕ TOÁN TỬ
					 */
					thapphan = luuthapphan;
				}
				if (kytu == '.') {
					/**
					 * CÓ THỂ INSERT DẤU .
					 * */
					thapphan = false;
				}
			}

			if (sotien.getText().toString().length() > 1) {
				String sotien_del = sotien.getText().toString()
						.substring(0, sotien.getText().toString().length() - 1);
				sotien.setText(sotien_del);
				bang.setText("=");
			} else {
				sotien.setText("0");
				bang.setText(">>");
			}
			break;
		case R.id.btnBang_MT:
			if (bang.getText().toString().equals("=")) {
				String bieuthuc = sotien.getText().toString();
				String[] elementMath = null;
				String ketqua = "0";
				MayTinh caculator = new MayTinh();
				if (bieuthuc.length() > 0) {
					try {
						/**
						 * TÁCH BIỂU THỨC NHẬP VÀO THÀNH CÁC PHẦN THỬ
						 * */
						if (!caculator.check_error)
							elementMath = caculator.processString(bieuthuc);
						/**
						 * CHUYỂN BIỂU THỨC TRUNG TỐ SANG HẬU TỐ
						 * */
						if (!caculator.check_error)
							elementMath = caculator.postfix(elementMath);
						/**
						 * THỰC HIỆN TÍNH GIÁ TRỊ BIỂU THỨC
						 * */
						if (!caculator.check_error)
							ketqua = caculator.valueMath(elementMath);
						sotien.setText(ketqua);
					} catch (Exception e) {
						sotien.setText("0");
						Toast.makeText(getApplicationContext(),
								"Xẩy ra lỗi trong biểu thức của bạn.",
								Toast.LENGTH_SHORT).show();
					}
				}
				/**
				 * KIỂM TRA KẾT QUẢ TÍNH TOÁN NẾU CÓ KÝ TỰ . KHÔNG CHO INSERT
				 * DẤU . NỮA
				 */
				for (int i = 0; i < ketqua.length(); i++) {
					if (ketqua.charAt(i) == '.') {
						thapphan = true;
					}
				}
				pheptinh = false;
				bang.setText(">>");
			} else {
				if (bang.getText().toString().equals(">>")) {

					Intent myIntent = new Intent();
					myIntent.putExtra("anbang", "true");
					myIntent.putExtra("sotien", sotien.getText().toString());
					setResult(RESULT_OK, myIntent);
					finish();
				}
			}
			break;

		}
		int dodai = sotien.getText().length();
		sotien.setSelection(dodai);
	}
}
