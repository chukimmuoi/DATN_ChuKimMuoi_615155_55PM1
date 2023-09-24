package dhxd.chukimmuoi.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import dhxd.chukimmuoi.model.tbl_doitac;
import dhxd.chukimmuoi.utils.Constant;
import dhxd.chukimmuoi.utils.JSONParser;

public class ctr_doitac {
	private JSONParser jsonParser;
	private static String urlDoiTac = Constant.LINK + "ctr_doitac.php";
	private String tagInsert = "insertDoiTac";
	private String tagInsertDongY = "insertDoiTacDongY";
	private String tagUpdate = "updateDoiTac";
	private String tagDelete = "deleteDoiTac";
	private String tagDeleteThat = "deleteDoiTacThat";
	private String tagSelect = "selectDoiTac";
	private String tagSelectThongBao = "selectDoiTacThongBao";

	private String KEY_DOITAC_ID = "DoiTac_Id";
	private String KEY_EMAIL = "Email";
	private String KEY_EMAILDOITAC = "EmailDoiTac";
	private String KEY_QUANHE = "QuanHe";

	// private String KEY_XACNHAN = "XacNhan";

	public ctr_doitac() {
		jsonParser = new JSONParser();
	}

	public JSONObject selectDoiTac(tbl_doitac tblDT) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagSelect));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblDT.getEmail()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlDoiTac, params);
		return objJSON;
	}

	public JSONObject insertDoiTac(tbl_doitac tblDT) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagInsert));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblDT.getEmail()));
		params.add(new BasicNameValuePair(KEY_EMAILDOITAC, tblDT
				.getEmailDoiTac()));
		params.add(new BasicNameValuePair(KEY_QUANHE, tblDT.getQuanHe()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlDoiTac, params);
		return objJSON;
	}
	
	public JSONObject insertDoiTacDongY(tbl_doitac tblDT) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagInsertDongY));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblDT.getEmail()));
		params.add(new BasicNameValuePair(KEY_EMAILDOITAC, tblDT
				.getEmailDoiTac()));
		params.add(new BasicNameValuePair(KEY_QUANHE, tblDT.getQuanHe()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlDoiTac, params);
		return objJSON;
	}

	public JSONObject updateDoiTac(tbl_doitac tblDT) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagUpdate));
		params.add(new BasicNameValuePair(KEY_DOITAC_ID, ""
				+ tblDT.getDoiTac_Id() + ""));
		params.add(new BasicNameValuePair(KEY_QUANHE, tblDT.getQuanHe()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlDoiTac, params);
		return objJSON;
	}

	public JSONObject deleteDoiTac(tbl_doitac tblDT) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagDelete));
		params.add(new BasicNameValuePair(KEY_DOITAC_ID, ""
				+ tblDT.getDoiTac_Id() + ""));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblDT.getEmail()));
		params.add(new BasicNameValuePair(KEY_EMAILDOITAC, tblDT
				.getEmailDoiTac()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlDoiTac, params);
		return objJSON;
	}
	
	public JSONObject selectDoiTacThongBao(tbl_doitac tblDT) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagSelectThongBao));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblDT.getEmail()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlDoiTac, params);
		return objJSON;
	}
	
	public JSONObject deleteDoiTacThat(tbl_doitac tblDT) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagDeleteThat));
		params.add(new BasicNameValuePair(KEY_DOITAC_ID, ""
				+ tblDT.getDoiTac_Id() + ""));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlDoiTac, params);
		return objJSON;
	}
}
