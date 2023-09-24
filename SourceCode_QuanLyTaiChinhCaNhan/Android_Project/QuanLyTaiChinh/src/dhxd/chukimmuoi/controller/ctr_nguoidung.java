package dhxd.chukimmuoi.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import dhxd.chukimmuoi.model.tbl_nguoidung;
import dhxd.chukimmuoi.service.ser_nguoidung;
import dhxd.chukimmuoi.utils.Constant;
import dhxd.chukimmuoi.utils.JSONParser;

public class ctr_nguoidung {
	private JSONParser jsonParser;

	private static String urlNguoiDung = Constant.LINK + "ctr_nguoidung.php";

	private static String tagDangKy = "dangky";
	private static String tagDangNhap = "dangnhap";
	private static String tagQuenMatKhau = "quenmatkhau";
	private static String tagDoiMatKhau = "doimatkhau";

	private static final String KEY_HOTEN = "HoTen";
	private static final String KEY_EMAIL = "Email";
	private static final String KEY_MATKHAU = "MatKhau";

	public ctr_nguoidung() {
		jsonParser = new JSONParser();
	}

	public JSONObject DangKy(tbl_nguoidung tblND) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagDangKy));
		params.add(new BasicNameValuePair(KEY_HOTEN, tblND.getHoTen()));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblND.getEmail()));
		params.add(new BasicNameValuePair(KEY_MATKHAU, tblND.getMatKhau()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlNguoiDung, params);
		return objJSON;
	}

	public JSONObject DangNhap(tbl_nguoidung tblND) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagDangNhap));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblND.getEmail()));
		params.add(new BasicNameValuePair(KEY_MATKHAU, tblND.getMatKhau()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlNguoiDung, params);
		return objJSON;
	}

	public boolean DangXuat(Context context) {
		ser_nguoidung db = new ser_nguoidung(context);
		db.deleteNguoiDung();
		return true;
	}

	public JSONObject QuenMatKhau(tbl_nguoidung tblND) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagQuenMatKhau));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblND.getEmail()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlNguoiDung, params);
		return objJSON;
	}

	public JSONObject DoiMatKhau(tbl_nguoidung tblND) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagDoiMatKhau));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblND.getEmail()));
		params.add(new BasicNameValuePair(KEY_MATKHAU, tblND.getMatKhau()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlNguoiDung, params);
		return objJSON;
	}
}
