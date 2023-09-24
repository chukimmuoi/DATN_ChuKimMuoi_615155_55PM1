package dhxd.chukimmuoi.model;

import java.io.Serializable;
import java.util.Date;

public class tbl_kehoach implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int KeHoach_Id;
	private int Nhom_Id;
	private int Loai_Id;
	private int Vi_Id;
	private String EmailDoiTac;
	private String Email;
	private Date NgayBatDau;
	private Date NgayKetThuc;
	private String DienTa;
	private double SoTien;
	private int TrangThai;
	private int LoaiKeHoach;

	public int getKeHoach_Id() {
		return KeHoach_Id;
	}

	public void setKeHoach_Id(int keHoach_Id) {
		KeHoach_Id = keHoach_Id;
	}

	public int getNhom_Id() {
		return Nhom_Id;
	}

	public void setNhom_Id(int nhom_Id) {
		Nhom_Id = nhom_Id;
	}

	public int getLoai_Id() {
		return Loai_Id;
	}

	public void setLoai_Id(int loai_Id) {
		Loai_Id = loai_Id;
	}

	public int getVi_Id() {
		return Vi_Id;
	}

	public void setVi_Id(int vi_Id) {
		Vi_Id = vi_Id;
	}

	public String getEmailDoiTac() {
		return EmailDoiTac;
	}

	public void setEmailDoiTac(String emailDoiTac) {
		EmailDoiTac = emailDoiTac;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public Date getNgayBatDau() {
		return NgayBatDau;
	}

	public void setNgayBatDau(Date ngayBatDau) {
		NgayBatDau = ngayBatDau;
	}

	public Date getNgayKetThuc() {
		return NgayKetThuc;
	}

	public void setNgayKetThuc(Date ngayKetThuc) {
		NgayKetThuc = ngayKetThuc;
	}

	public String getDienTa() {
		return DienTa;
	}

	public void setDienTa(String dienTa) {
		DienTa = dienTa;
	}

	public double getSoTien() {
		return SoTien;
	}

	public void setSoTien(double soTien) {
		SoTien = soTien;
	}

	public int getTrangThai() {
		return TrangThai;
	}

	public void setTrangThai(int trangThai) {
		TrangThai = trangThai;
	}

	public int getLoaiKeHoach() {
		return LoaiKeHoach;
	}

	public void setLoaiKeHoach(int loaiKeHoach) {
		LoaiKeHoach = loaiKeHoach;
	}
}
