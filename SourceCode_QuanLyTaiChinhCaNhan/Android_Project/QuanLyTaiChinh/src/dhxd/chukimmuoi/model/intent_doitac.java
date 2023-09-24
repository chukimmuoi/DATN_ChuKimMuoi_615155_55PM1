package dhxd.chukimmuoi.model;

import java.io.Serializable;
import java.util.ArrayList;

public class intent_doitac implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<tbl_doitac> arrDoiTac;

	public intent_doitac(ArrayList<tbl_doitac> arrDoiTac) {
		this.arrDoiTac = arrDoiTac;
	}

	public ArrayList<tbl_doitac> getArrDoiTac() {
		return arrDoiTac;
	}

	public void setArrDoiTac(ArrayList<tbl_doitac> arrDoiTac) {
		this.arrDoiTac = arrDoiTac;
	}

}
