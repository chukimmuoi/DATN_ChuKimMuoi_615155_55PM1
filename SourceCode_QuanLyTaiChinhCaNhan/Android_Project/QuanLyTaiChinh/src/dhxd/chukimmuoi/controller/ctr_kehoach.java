package dhxd.chukimmuoi.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import dhxd.chukimmuoi.model.tbl_kehoach;
import dhxd.chukimmuoi.utils.Constant;
import dhxd.chukimmuoi.utils.JSONParser;

@SuppressLint("SimpleDateFormat") 
public class ctr_kehoach {
	private JSONParser jsonParser;

	private String urlKeHoach = Constant.LINK + "ctr_kehoach.php";

	private String taginsertKeHoach = "insertKeHoach";
	private String taginsertKeHoachNganSach = "insertKeHoachNganSach";
	private String tagselectKeHoachCongViec = "selectKeHoachCongViec";
	private String tagselectThongBaoGiaoDich = "selectThongBaoGiaoDich";
	private String tagselectKeHoachNganSach0 = "selectKeHoachNganSach0";
	private String tagselectKeHoachNganSach1 = "selectKeHoachNganSach1";
	private String tagupdateKeHoachCongViec = "updateKeHoachCongViec";
	private String tagupdateKeHoachNganSach = "updateKeHoachNganSach";
	private String tagdeleteKeHoach = "deleteKeHoach";
	private String tagdeleteKeHoachGiaoDich = "deleteKeHoachGiaoDich";
	private String tagselectSoNo = "selectSoNo";
	
	private String KEY_KEHOACH_ID = "KeHoach_Id";
	private String KEY_NHOM_ID = "Nhom_Id";
	private String KEY_LOAI_ID = "Loai_Id";
	private String KEY_VI_ID = "Vi_Id";
	private String KEY_EMAILDOITAC = "EmailDoiTac";
	private String KEY_EMAIL = "Email";
	private String KEY_NGAYBATDAU = "NgayBatDau";
	private String KEY_NGAYKETTHUC = "NgayKetThuc";
	private String KEY_DIENTA = "DienTa";
	private String KEY_SOTIEN = "SoTien";
	private String KEY_TRANGTHAI = "TrangThai";
	private String KEY_LOAIKEHOACH = "LoaiKeHoach";
	
	final SimpleDateFormat formatngay = new SimpleDateFormat("yyyy-MM-dd");
	public ctr_kehoach(){
		jsonParser = new JSONParser();
	}
	
	public JSONObject insertKeHoach(tbl_kehoach tblKH){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", taginsertKeHoach));
		params.add(new BasicNameValuePair(KEY_NHOM_ID, ""+tblKH.getNhom_Id()+""));
		params.add(new BasicNameValuePair(KEY_LOAI_ID, ""+tblKH.getLoai_Id()+""));
		params.add(new BasicNameValuePair(KEY_VI_ID, ""+tblKH.getVi_Id()+""));
		params.add(new BasicNameValuePair(KEY_EMAILDOITAC, tblKH.getEmailDoiTac()));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblKH.getEmail()));
		params.add(new BasicNameValuePair(KEY_NGAYBATDAU, ""+formatngay.format(tblKH.getNgayBatDau())+""));
		params.add(new BasicNameValuePair(KEY_NGAYKETTHUC, ""+tblKH.getNgayKetThuc()+""));
		params.add(new BasicNameValuePair(KEY_DIENTA, tblKH.getDienTa()));
		params.add(new BasicNameValuePair(KEY_SOTIEN, ""+tblKH.getSoTien()+""));
		params.add(new BasicNameValuePair(KEY_TRANGTHAI, ""+tblKH.getTrangThai()+""));
		params.add(new BasicNameValuePair(KEY_LOAIKEHOACH, ""+tblKH.getLoaiKeHoach()+""));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlKeHoach, params);
		return objJSON;
	}
	
	public JSONObject selectKeHoachCongViec(tbl_kehoach tblKH){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagselectKeHoachCongViec));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblKH.getEmail()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlKeHoach, params);
		return objJSON;
	}
	
	public JSONObject updateKeHoach(tbl_kehoach tblKH){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagupdateKeHoachCongViec));
		params.add(new BasicNameValuePair(KEY_KEHOACH_ID, ""+tblKH.getKeHoach_Id()+""));
		params.add(new BasicNameValuePair(KEY_NHOM_ID, ""+tblKH.getNhom_Id()+""));
		params.add(new BasicNameValuePair(KEY_LOAI_ID, ""+tblKH.getLoai_Id()+""));
		params.add(new BasicNameValuePair(KEY_VI_ID, ""+tblKH.getVi_Id()+""));
		params.add(new BasicNameValuePair(KEY_EMAILDOITAC, tblKH.getEmailDoiTac()));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblKH.getEmail()));
		params.add(new BasicNameValuePair(KEY_NGAYBATDAU, ""+formatngay.format(tblKH.getNgayBatDau())+""));
		params.add(new BasicNameValuePair(KEY_NGAYKETTHUC, ""+tblKH.getNgayKetThuc()+""));
		params.add(new BasicNameValuePair(KEY_DIENTA, tblKH.getDienTa()));
		params.add(new BasicNameValuePair(KEY_SOTIEN, ""+tblKH.getSoTien()+""));
		params.add(new BasicNameValuePair(KEY_TRANGTHAI, ""+tblKH.getTrangThai()+""));
		params.add(new BasicNameValuePair(KEY_LOAIKEHOACH, ""+tblKH.getLoaiKeHoach()+""));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlKeHoach, params);
		return objJSON;
	}
	
	public JSONObject deleteKeHoach(tbl_kehoach tblKH){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagdeleteKeHoach));
		params.add(new BasicNameValuePair(KEY_KEHOACH_ID, ""+tblKH.getKeHoach_Id()+""));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblKH.getEmail()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlKeHoach, params);
		return objJSON;
	}
	
	public JSONObject insertKeHoachNganSach(tbl_kehoach tblKH){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", taginsertKeHoachNganSach));
		params.add(new BasicNameValuePair(KEY_NHOM_ID, ""+tblKH.getNhom_Id()+""));
		params.add(new BasicNameValuePair(KEY_LOAI_ID, ""+tblKH.getLoai_Id()+""));
		params.add(new BasicNameValuePair(KEY_VI_ID, ""+tblKH.getVi_Id()+""));
		params.add(new BasicNameValuePair(KEY_EMAILDOITAC, tblKH.getEmailDoiTac()));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblKH.getEmail()));
		params.add(new BasicNameValuePair(KEY_NGAYBATDAU, ""+formatngay.format(tblKH.getNgayBatDau())+""));
		params.add(new BasicNameValuePair(KEY_NGAYKETTHUC, ""+formatngay.format(tblKH.getNgayKetThuc())+""));
		params.add(new BasicNameValuePair(KEY_DIENTA, tblKH.getDienTa()));
		params.add(new BasicNameValuePair(KEY_SOTIEN, ""+tblKH.getSoTien()+""));
		params.add(new BasicNameValuePair(KEY_TRANGTHAI, ""+tblKH.getTrangThai()+""));
		params.add(new BasicNameValuePair(KEY_LOAIKEHOACH, ""+tblKH.getLoaiKeHoach()+""));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlKeHoach, params);
		return objJSON;
	}
	
	public JSONObject selectKeHoachNganSach0(tbl_kehoach tblKH){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagselectKeHoachNganSach0));
		params.add(new BasicNameValuePair(KEY_NGAYBATDAU, ""+formatngay.format(tblKH.getNgayBatDau())+""));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblKH.getEmail()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlKeHoach, params);
		return objJSON;
	}
	
	public JSONObject selectKeHoachNganSach1(tbl_kehoach tblKH){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagselectKeHoachNganSach1));
		params.add(new BasicNameValuePair(KEY_NGAYBATDAU, ""+formatngay.format(tblKH.getNgayBatDau())+""));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblKH.getEmail()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlKeHoach, params);
		return objJSON;
	}
	
	public JSONObject updateKeHoachNganSach(tbl_kehoach tblKH){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagupdateKeHoachNganSach));
		params.add(new BasicNameValuePair(KEY_KEHOACH_ID, ""+tblKH.getKeHoach_Id()+""));
		params.add(new BasicNameValuePair(KEY_NHOM_ID, ""+tblKH.getNhom_Id()+""));
		params.add(new BasicNameValuePair(KEY_LOAI_ID, ""+tblKH.getLoai_Id()+""));
		params.add(new BasicNameValuePair(KEY_VI_ID, ""+tblKH.getVi_Id()+""));
		params.add(new BasicNameValuePair(KEY_EMAILDOITAC, tblKH.getEmailDoiTac()));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblKH.getEmail()));
		params.add(new BasicNameValuePair(KEY_NGAYBATDAU, ""+formatngay.format(tblKH.getNgayBatDau())+""));
		params.add(new BasicNameValuePair(KEY_NGAYKETTHUC, ""+formatngay.format(tblKH.getNgayKetThuc())+""));
		params.add(new BasicNameValuePair(KEY_DIENTA, tblKH.getDienTa()));
		params.add(new BasicNameValuePair(KEY_SOTIEN, ""+tblKH.getSoTien()+""));
		params.add(new BasicNameValuePair(KEY_TRANGTHAI, ""+tblKH.getTrangThai()+""));
		params.add(new BasicNameValuePair(KEY_LOAIKEHOACH, ""+tblKH.getLoaiKeHoach()+""));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlKeHoach, params);
		return objJSON;
	}
	
	public JSONObject selectThongBaoGiaoDich(tbl_kehoach tblKH){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagselectThongBaoGiaoDich));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblKH.getEmail()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlKeHoach, params);
		return objJSON;
	}
	
	public JSONObject deleteKeHoachGiaoDich(tbl_kehoach tblKH){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagdeleteKeHoachGiaoDich));
		params.add(new BasicNameValuePair("GiaoDich_Id", ""+tblKH.getVi_Id()+""));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblKH.getEmail()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlKeHoach, params);
		return objJSON;
	}
	
	public JSONObject selectSoNo(tbl_kehoach tblKH){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagselectSoNo));
		params.add(new BasicNameValuePair(KEY_LOAI_ID, ""+tblKH.getLoai_Id()+""));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblKH.getEmail()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlKeHoach, params);
		return objJSON;
	}
}
