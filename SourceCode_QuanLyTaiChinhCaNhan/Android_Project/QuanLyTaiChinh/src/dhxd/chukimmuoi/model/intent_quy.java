package dhxd.chukimmuoi.model;

import java.io.Serializable;
import java.util.ArrayList;

public class intent_quy implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<tbl_quy> arrQuy;

	public intent_quy(ArrayList<tbl_quy> arrQuy) {

		this.arrQuy = arrQuy;
	}

	public ArrayList<tbl_quy> getArrQuy() {
		return arrQuy;
	}

	public void setArrQuy(ArrayList<tbl_quy> arrQuy) {
		this.arrQuy = arrQuy;
	}
}
