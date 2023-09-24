package dhxd.chukimmuoi.model;

import java.io.Serializable;
import java.util.Date;

public class tbl_giaodich implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int GiaoDich_Id;
	private int Nhom_Id;
	private double SoTien;
	private String GhiChu;
	private String EmailDoiTac;
	private Date NgayThang;
	private int Quy_Id;
	private String Email;
	private int Nhom_Id_Phu;
	private String GhiChu_Phu;
	private int Quy_Id_Phu;
	private double SoTienQuy;
	private double SoTienQuy_Phu;
	private Date NgayTra;
	private double TienLai;
	private int LoaiThuLai;

	public Date getNgayTra() {
		return NgayTra;
	}

	public void setNgayTra(Date ngayTra) {
		NgayTra = ngayTra;
	}

	public double getTienLai() {
		return TienLai;
	}

	public void setTienLai(double tienLai) {
		TienLai = tienLai;
	}

	public int getLoaiThuLai() {
		return LoaiThuLai;
	}

	public void setLoaiThuLai(int loaiThuLai) {
		LoaiThuLai = loaiThuLai;
	}

	public double getSoTienQuy() {
		return SoTienQuy;
	}

	public void setSoTienQuy(double soTienQuy) {
		SoTienQuy = soTienQuy;
	}

	public double getSoTienQuy_Phu() {
		return SoTienQuy_Phu;
	}

	public void setSoTienQuy_Phu(double soTienQuy_Phu) {
		SoTienQuy_Phu = soTienQuy_Phu;
	}

	public int getNhom_Id_Phu() {
		return Nhom_Id_Phu;
	}

	public void setNhom_Id_Phu(int nhom_Id_Phu) {
		Nhom_Id_Phu = nhom_Id_Phu;
	}

	public String getGhiChu_Phu() {
		return GhiChu_Phu;
	}

	public void setGhiChu_Phu(String ghiChu_Phu) {
		GhiChu_Phu = ghiChu_Phu;
	}

	public int getQuy_Id_Phu() {
		return Quy_Id_Phu;
	}

	public void setQuy_Id_Phu(int quy_Id_Phu) {
		Quy_Id_Phu = quy_Id_Phu;
	}

	public int getGiaoDich_Id() {
		return GiaoDich_Id;
	}

	public void setGiaoDich_Id(int giaoDich_Id) {
		GiaoDich_Id = giaoDich_Id;
	}

	public int getNhom_Id() {
		return Nhom_Id;
	}

	public void setNhom_Id(int nhom_Id) {
		Nhom_Id = nhom_Id;
	}

	public double getSoTien() {
		return SoTien;
	}

	public void setSoTien(double soTien) {
		SoTien = soTien;
	}

	public String getGhiChu() {
		return GhiChu;
	}

	public void setGhiChu(String ghiChu) {
		GhiChu = ghiChu;
	}

	public String getEmailDoiTac() {
		return EmailDoiTac;
	}

	public void setEmailDoiTac(String emailDoiTac) {
		EmailDoiTac = emailDoiTac;
	}

	public Date getNgayThang() {
		return NgayThang;
	}

	public void setNgayThang(Date ngayThang) {
		NgayThang = ngayThang;
	}

	public int getQuy_Id() {
		return Quy_Id;
	}

	public void setQuy_Id(int quy_Id) {
		Quy_Id = quy_Id;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

}
