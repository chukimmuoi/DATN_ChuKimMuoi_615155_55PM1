package dhxd.chukimmuoi.model;

public class tbl_nguoidung {
	private String Unique_Id;
	private String HoTen;
	private String Email;
	private String MatKhau;
	private String MaBam;
	private String NgayTao;

	public String getNgayTao() {
		return NgayTao;
	}

	public void setNgayTao(String ngayTao) {
		NgayTao = ngayTao;
	}

	public String getUnique_Id() {
		return Unique_Id;
	}

	public void setUnique_Id(String unique_Id) {
		Unique_Id = unique_Id;
	}

	public String getHoTen() {
		return HoTen;
	}

	public void setHoTen(String hoTen) {
		HoTen = hoTen;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getMatKhau() {
		return MatKhau;
	}

	public void setMatKhau(String matKhau) {
		MatKhau = matKhau;
	}

	public String getMaBam() {
		return MaBam;
	}

	public void setMaBam(String maBam) {
		MaBam = maBam;
	}

}
