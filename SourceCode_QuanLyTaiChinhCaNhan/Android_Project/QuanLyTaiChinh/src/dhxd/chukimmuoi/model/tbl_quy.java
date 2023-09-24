package dhxd.chukimmuoi.model;

import java.io.Serializable;

public class tbl_quy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int Quy_Id;
	private String TenQuy;
	private String Anh;
	private double SoTien;
	private String Email;

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public int getQuy_Id() {
		return Quy_Id;
	}

	public void setQuy_Id(int quy_Id) {
		Quy_Id = quy_Id;
	}

	public String getTenQuy() {
		return TenQuy;
	}

	public void setTenQuy(String tenQuy) {
		TenQuy = tenQuy;
	}

	public String getAnh() {
		return Anh;
	}

	public void setAnh(String anh) {
		Anh = anh;
	}

	public double getSoTien() {
		return SoTien;
	}

	public void setSoTien(double soTien) {
		SoTien = soTien;
	}

}
