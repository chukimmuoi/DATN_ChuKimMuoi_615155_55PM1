package dhxd.chukimmuoi.model;

import java.io.Serializable;

public class tbl_nhom implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int Nhom_Id;
	private String TenNhom;
	private String Loai_Id;
	private String Anh;
	private String Email;

	public int getNhom_Id() {
		return Nhom_Id;
	}

	public void setNhom_Id(int nhom_Id) {
		Nhom_Id = nhom_Id;
	}

	public String getTenNhom() {
		return TenNhom;
	}

	public void setTenNhom(String tenNhom) {
		TenNhom = tenNhom;
	}

	public String getLoai_Id() {
		return Loai_Id;
	}

	public void setLoai_Id(String loai_Id) {
		Loai_Id = loai_Id;
	}

	public String getAnh() {
		return Anh;
	}

	public void setAnh(String anh) {
		Anh = anh;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}
}
