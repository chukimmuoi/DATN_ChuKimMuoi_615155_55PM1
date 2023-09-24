package dhxd.chukimmuoi.model;

public class item_spinnerLoai {
	int anh;
	String tenloai;

	public item_spinnerLoai(int anh, String tenloai) {
		super();
		this.anh = anh;
		this.tenloai = tenloai;
	}

	public int getAnh() {
		return anh;
	}

	public void setAnh(int anh) {
		this.anh = anh;
	}

	public String getTenloai() {
		return tenloai;
	}

	public void setTenloai(String tenloai) {
		this.tenloai = tenloai;
	}

}
