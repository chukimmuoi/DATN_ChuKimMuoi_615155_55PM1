package dhxd.chukimmuoi.model;

import java.io.Serializable;

public class tbl_doitac implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int DoiTac_Id;
	private String Email;
	private String EmailDoiTac;
	private String QuanHe;
	private int XacNhan;

	public int getDoiTac_Id() {
		return DoiTac_Id;
	}

	public void setDoiTac_Id(int doiTac_Id) {
		DoiTac_Id = doiTac_Id;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getEmailDoiTac() {
		return EmailDoiTac;
	}

	public void setEmailDoiTac(String emailDoiTac) {
		EmailDoiTac = emailDoiTac;
	}

	public String getQuanHe() {
		return QuanHe;
	}

	public void setQuanHe(String quanHe) {
		QuanHe = quanHe;
	}

	public int getXacNhan() {
		return XacNhan;
	}

	public void setXacNhan(int xacNhan) {
		XacNhan = xacNhan;
	}

}
