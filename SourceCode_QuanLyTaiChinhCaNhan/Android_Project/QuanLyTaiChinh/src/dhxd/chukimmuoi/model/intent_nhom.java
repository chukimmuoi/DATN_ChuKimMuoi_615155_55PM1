package dhxd.chukimmuoi.model;

import java.io.Serializable;
import java.util.ArrayList;

public class intent_nhom implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<tbl_nhom> arrNhom;

	public intent_nhom(ArrayList<tbl_nhom> arrNhom) {
		this.arrNhom = arrNhom;
	}

	public ArrayList<tbl_nhom> getArrNhom() {
		return arrNhom;
	}

	public void setArrNhom(ArrayList<tbl_nhom> arrNhom) {
		this.arrNhom = arrNhom;
	}

}
