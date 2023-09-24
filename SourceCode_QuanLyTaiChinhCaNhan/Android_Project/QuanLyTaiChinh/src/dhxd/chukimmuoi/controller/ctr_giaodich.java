package dhxd.chukimmuoi.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import dhxd.chukimmuoi.model.tbl_giaodich;
import dhxd.chukimmuoi.model.tbl_kehoach;
import dhxd.chukimmuoi.model.tbl_nhom;
import dhxd.chukimmuoi.model.tbl_quy;
import dhxd.chukimmuoi.utils.Constant;
import dhxd.chukimmuoi.utils.JSONParser;

@SuppressLint("SimpleDateFormat") 
public class ctr_giaodich {
	private JSONParser jsonParser;

	private String urlGiaoDich = Constant.LINK + "ctr_giaodich.php";

	private String taginsertGiaoDich = "insertGiaoDich";
	private String taginsertGiaoDichChuyenTien = "insertGiaoDichChuyenTien";
	private String taginsertGiaoDichVayMuon = "insertGiaoDichVayMuon";
	private String taginsertGiaoDichVayMuonCoLai = "insertGiaoDichVayMuonCoLai";
	private String tagselectGiaoDich = "selectGiaoDich";
	private String tagdeleteGiaoDich = "deleteGiaoDich";
	private String tagselectGiaoDichBaoCaoNam = "selectGiaoDichBaoCaoNam";
	
	private String KEY_GIAODICH_ID = "GiaoDich_Id";
	private String KEY_NHOM_ID = "Nhom_Id";
	private String KEY_SOTIEN = "SoTien";
	private String KEY_GHICHU = "GhiChu";
	private String KEY_EMAILDOITAC = "EmailDoiTac";
	private String KEY_NGAYTHANG = "NgayThang";
	private String KEY_QUY_ID = "Quy_Id";
	private String KEY_EMAIL = "Email";
	private String KEY_NHOM_ID_PHU = "Nhom_Id_Phu";
	private String KEY_QUY_ID_PHU = "Quy_Id_Phu";
	private String KEY_GHICHU_PHU = "GhiChu_Phu";
	private String KEY_SOTIENQUY = "SoTienQuy";
	private String KEY_SOTIENQUY_PHU = "SoTienQuy_Phu";
	private String KEY_NGAYBATDAU = "NgayBatDau";
	private String KEY_SOTIENLAI = "SoTienLai";
	private String KEY_LOAITHULAI = "LoaiThuLai";
	private String KEY_NAM = "Nam";
	private String KEY_LOAI_ID = "Loai_Id";
	
	final SimpleDateFormat formatngay = new SimpleDateFormat("yyyy-MM-dd");
	public ctr_giaodich(){
		jsonParser = new JSONParser();
	}
	
	public JSONObject insertGiaoDich_ChuyenTien(tbl_giaodich tblGD){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", taginsertGiaoDichChuyenTien));
		params.add(new BasicNameValuePair(KEY_NHOM_ID, ""+tblGD.getNhom_Id()+""));
		params.add(new BasicNameValuePair(KEY_SOTIEN, ""+tblGD.getSoTien()+""));
		params.add(new BasicNameValuePair(KEY_GHICHU, tblGD.getGhiChu()));
//		params.add(new BasicNameValuePair(KEY_EMAILDOITAC, tblGD.getEmailDoiTac()));
		params.add(new BasicNameValuePair(KEY_NGAYTHANG, ""+formatngay.format(tblGD.getNgayThang())+""));
		params.add(new BasicNameValuePair(KEY_QUY_ID, ""+tblGD.getQuy_Id()+""));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblGD.getEmail()));
		params.add(new BasicNameValuePair(KEY_NHOM_ID_PHU, ""+tblGD.getNhom_Id_Phu()+""));
		params.add(new BasicNameValuePair(KEY_GHICHU_PHU, tblGD.getGhiChu_Phu()));
		params.add(new BasicNameValuePair(KEY_QUY_ID_PHU, ""+tblGD.getQuy_Id_Phu()+""));
		params.add(new BasicNameValuePair(KEY_SOTIENQUY, ""+tblGD.getSoTienQuy()+""));
		params.add(new BasicNameValuePair(KEY_SOTIENQUY_PHU, ""+tblGD.getSoTienQuy_Phu()+""));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlGiaoDich, params);
		return objJSON;
	}
	
	public JSONObject insertGiaoDich(tbl_giaodich tblGD){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", taginsertGiaoDich));
		params.add(new BasicNameValuePair(KEY_NHOM_ID, ""+tblGD.getNhom_Id()+""));
		params.add(new BasicNameValuePair(KEY_SOTIEN, ""+tblGD.getSoTien()+""));
		params.add(new BasicNameValuePair(KEY_GHICHU, tblGD.getGhiChu()));
		params.add(new BasicNameValuePair(KEY_EMAILDOITAC, tblGD.getEmailDoiTac()));
		params.add(new BasicNameValuePair(KEY_NGAYTHANG, ""+formatngay.format(tblGD.getNgayThang())+""));
		params.add(new BasicNameValuePair(KEY_QUY_ID, ""+tblGD.getQuy_Id()+""));
		params.add(new BasicNameValuePair(KEY_SOTIENQUY, ""+tblGD.getSoTienQuy()+""));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblGD.getEmail()));
		params.add(new BasicNameValuePair(KEY_GIAODICH_ID, ""+tblGD.getGiaoDich_Id()+""));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlGiaoDich, params);
		return objJSON;
	}
	
	public JSONObject insertGiaoDichVayMuon(tbl_giaodich tblGD, tbl_kehoach tblKH){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", taginsertGiaoDichVayMuon));
		params.add(new BasicNameValuePair(KEY_NHOM_ID, ""+tblGD.getNhom_Id()+""));
		params.add(new BasicNameValuePair(KEY_SOTIEN, ""+tblGD.getSoTien()+""));
		params.add(new BasicNameValuePair(KEY_GHICHU, tblGD.getGhiChu()));
		params.add(new BasicNameValuePair(KEY_EMAILDOITAC, tblGD.getEmailDoiTac()));
		params.add(new BasicNameValuePair(KEY_NGAYTHANG, ""+formatngay.format(tblGD.getNgayThang())+""));
		params.add(new BasicNameValuePair(KEY_QUY_ID, ""+tblGD.getQuy_Id()+""));
		params.add(new BasicNameValuePair(KEY_SOTIENQUY, ""+tblGD.getSoTienQuy()+""));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblGD.getEmail()));
		params.add(new BasicNameValuePair(KEY_GIAODICH_ID, ""+tblGD.getGiaoDich_Id()+""));
		params.add(new BasicNameValuePair(KEY_NGAYBATDAU, ""+formatngay.format(tblKH.getNgayBatDau())+""));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlGiaoDich, params);
		return objJSON;
	}
	
	public JSONObject insertGiaoDichVayMuonCoLai(tbl_giaodich tblGD, tbl_kehoach tblKH){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", taginsertGiaoDichVayMuonCoLai));
		params.add(new BasicNameValuePair(KEY_NHOM_ID, ""+tblGD.getNhom_Id()+""));
		params.add(new BasicNameValuePair(KEY_SOTIEN, ""+tblGD.getSoTien()+""));
		params.add(new BasicNameValuePair(KEY_GHICHU, tblGD.getGhiChu()));
		params.add(new BasicNameValuePair(KEY_EMAILDOITAC, tblGD.getEmailDoiTac()));
		params.add(new BasicNameValuePair(KEY_NGAYTHANG, ""+formatngay.format(tblGD.getNgayThang())+""));
		params.add(new BasicNameValuePair(KEY_QUY_ID, ""+tblGD.getQuy_Id()+""));
		params.add(new BasicNameValuePair(KEY_SOTIENQUY, ""+tblGD.getSoTienQuy()+""));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblGD.getEmail()));
		params.add(new BasicNameValuePair(KEY_GIAODICH_ID, ""+tblGD.getGiaoDich_Id()+""));
		params.add(new BasicNameValuePair(KEY_NGAYBATDAU, ""+formatngay.format(tblKH.getNgayBatDau())+""));
		params.add(new BasicNameValuePair(KEY_SOTIENLAI, ""+tblKH.getSoTien()+""));
		params.add(new BasicNameValuePair(KEY_LOAITHULAI, ""+tblKH.getLoai_Id()+""));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlGiaoDich, params);
		return objJSON;
	}
	
	public JSONObject selectGiaoDich(tbl_giaodich tblGD){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagselectGiaoDich));
		params.add(new BasicNameValuePair(KEY_NGAYTHANG, ""+tblGD.getQuy_Id()+""));
		params.add(new BasicNameValuePair(KEY_NAM, ""+tblGD.getNhom_Id()+""));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblGD.getEmail()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlGiaoDich, params);
		return objJSON;
	}
	
	public JSONObject deleteGiaoDich(tbl_giaodich tblGD, tbl_nhom tblN, tbl_quy tblQ){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagdeleteGiaoDich));
		params.add(new BasicNameValuePair(KEY_GIAODICH_ID, ""+tblGD.getGiaoDich_Id()+""));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblGD.getEmail()));
		params.add(new BasicNameValuePair(KEY_SOTIEN, ""+tblGD.getSoTien()+""));
		params.add(new BasicNameValuePair(KEY_LOAI_ID, tblN.getLoai_Id()));
		params.add(new BasicNameValuePair(KEY_QUY_ID, ""+tblGD.getQuy_Id()+""));
		params.add(new BasicNameValuePair(KEY_SOTIENQUY, ""+tblQ.getSoTien()+""));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlGiaoDich, params);
		return objJSON;
	}
	
	public JSONObject selectGiaoDichBaoCaoNam(tbl_giaodich tblGD){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tagselectGiaoDichBaoCaoNam));
		params.add(new BasicNameValuePair(KEY_NAM, ""+tblGD.getNhom_Id()+""));
		params.add(new BasicNameValuePair(KEY_EMAIL, tblGD.getEmail()));
		JSONObject objJSON = jsonParser.getJsonTuUrl(urlGiaoDich, params);
		return objJSON;
	}
}
