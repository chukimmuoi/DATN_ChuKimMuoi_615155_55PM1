package dhxd.chukimmuoi.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import dhxd.chukimmuoi.model.tbl_quy;
import dhxd.chukimmuoi.utils.Constant;
import dhxd.chukimmuoi.utils.JSONParser;

public class ctr_quy {
	private JSONParser jsonParser;

	private String urlQuy = Constant.LINK + "ctr_quy.php";

	private String taginsertQuy = "insertQuy";
	private String tagselectQuy = "selectQuy";
	private String tagupdateQuy = "updateQuy";
	private String tagdeleteQuy = "deleteQuy";

	private String KEY_QUY_ID = "Quy_Id";
	private String KEY_TENQUY = "TenQuy";
	private String KEY_ANH = "Anh";
	private String KEY_SOTIEN = "SoTien";
	private String KEY_EMAIL = "Email";

	public ctr_quy() {
		jsonParser = new JSONParser();
	}

	public JSONObject insertQuy(tbl_quy tblQ) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", taginsertQuy));
		params.add(new BasicNameValuePair(KEY_TENQUY, tblQ.getTenQuy()));
		params.add(new BasicNameValuePair(KEY_ANH, tblQ.getAnh()));
		params.add(new BasicNameValuePair(KEY_SOTIEN, "" + tblQ.getSoTien()
				+ ""));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblQ.getEmail()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlQuy, params);
		return objJSON;
	}

	public JSONObject selectQuy(tbl_quy tblQ) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagselectQuy));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblQ.getEmail()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlQuy, params);
		return objJSON;
	}

	public JSONObject updateQuy(tbl_quy tblQ) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagupdateQuy));
		params.add(new BasicNameValuePair(KEY_QUY_ID, "" + tblQ.getQuy_Id()
				+ ""));
		params.add(new BasicNameValuePair(KEY_TENQUY, tblQ.getTenQuy()));
		params.add(new BasicNameValuePair(KEY_ANH, tblQ.getAnh()));
		params.add(new BasicNameValuePair(KEY_SOTIEN, "" + tblQ.getSoTien()
				+ ""));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlQuy, params);
		return objJSON;
	}

	public JSONObject deleteQuy(tbl_quy tblQ) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagdeleteQuy));
		params.add(new BasicNameValuePair(KEY_QUY_ID, "" + tblQ.getQuy_Id()
				+ ""));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlQuy, params);
		return objJSON;
	}
}
