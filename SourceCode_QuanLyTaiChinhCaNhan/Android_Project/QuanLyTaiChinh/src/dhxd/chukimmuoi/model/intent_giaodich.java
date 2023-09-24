package dhxd.chukimmuoi.model;

import java.io.Serializable;
import java.util.ArrayList;

public class intent_giaodich implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<tbl_giaodich> arrGiaoDich;

	public intent_giaodich(ArrayList<tbl_giaodich> arrGiaoDich) {
		this.arrGiaoDich = arrGiaoDich;
	}

	public ArrayList<tbl_giaodich> getArrGiaoDich() {
		return arrGiaoDich;
	}

	public void setArrGiaoDich(ArrayList<tbl_giaodich> arrGiaoDich) {
		this.arrGiaoDich = arrGiaoDich;
	}

}
