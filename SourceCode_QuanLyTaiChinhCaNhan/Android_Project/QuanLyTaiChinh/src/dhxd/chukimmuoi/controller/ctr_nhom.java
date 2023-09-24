package dhxd.chukimmuoi.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.utils.Constant;
import dhxd.chukimmuoi.utils.JSONParser;

public class ctr_nhom {
	private JSONParser jsonParser;

	private static String urlNhom = Constant.LINK + "ctr_nhom.php";

	private static String taginsertNhom = "insertNhom";
	private static String tagselectNhomLoai = "selectNhomLoai";
	private static String tagselectNhom = "selectNhom";
	private static String tagupdateNhom = "updateNhom";
	private static String tagdeleteNhom = "deleteNhom";

	private static final String KEY_NHOM_ID = "Nhom_Id";
	private static final String KEY_TENNHOM = "TenNhom";
	private static final String KEY_LOAI_ID = "Loai_Id";
	private static final String KEY_ANH = "Anh";
	private static final String KEY_EMAIL = "Email";

	public ctr_nhom() {
		jsonParser = new JSONParser();
	}

	public JSONObject insertNhom(tbl_nhom tblN) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", taginsertNhom));
		params.add(new BasicNameValuePair(KEY_TENNHOM, tblN.getTenNhom()));
		params.add(new BasicNameValuePair(KEY_LOAI_ID, tblN.getLoai_Id()));
		params.add(new BasicNameValuePair(KEY_ANH, tblN.getAnh()));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblN.getEmail()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlNhom, params);
		return objJSON;
	}

	public JSONObject selectNhomLoai(tbl_nhom tblN) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagselectNhomLoai));
		params.add(new BasicNameValuePair(KEY_LOAI_ID, tblN.getLoai_Id()));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblN.getEmail()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlNhom, params);
		return objJSON;
	}
	
	public JSONObject selectNhom(tbl_nhom tblN) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagselectNhom));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblN.getEmail()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlNhom, params);
		return objJSON;
	}

	public JSONObject updateNhom(tbl_nhom tblN) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagupdateNhom));
		params.add(new BasicNameValuePair(KEY_NHOM_ID, "" + tblN.getNhom_Id()
				+ ""));
		params.add(new BasicNameValuePair(KEY_TENNHOM, tblN.getTenNhom()));
		params.add(new BasicNameValuePair(KEY_LOAI_ID, tblN.getLoai_Id()));
		params.add(new BasicNameValuePair(KEY_ANH, tblN.getAnh()));
		// params.add(new BasicNameValuePair(KEY_EMAIL, tblN.getEmail()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlNhom, params);
		return objJSON;
	}

	public JSONObject deleteNhom(tbl_nhom tblN) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagdeleteNhom));
		params.add(new BasicNameValuePair(KEY_NHOM_ID, "" + tblN.getNhom_Id()
				+ ""));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlNhom, params);
		return objJSON;
	}
}
