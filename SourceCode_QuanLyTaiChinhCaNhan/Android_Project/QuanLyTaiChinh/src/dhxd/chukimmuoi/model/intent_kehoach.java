package dhxd.chukimmuoi.model;

import java.io.Serializable;
import java.util.ArrayList;

public class intent_kehoach implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<tbl_kehoach> arrKeHoach;

	public intent_kehoach(ArrayList<tbl_kehoach> arrKeHoach) {
		this.arrKeHoach = arrKeHoach;
	}

	public ArrayList<tbl_kehoach> getArrKeHoach() {
		return arrKeHoach;
	}

	public void setArrKeHoach(ArrayList<tbl_kehoach> arrKeHoach) {
		this.arrKeHoach = arrKeHoach;
	}

}
