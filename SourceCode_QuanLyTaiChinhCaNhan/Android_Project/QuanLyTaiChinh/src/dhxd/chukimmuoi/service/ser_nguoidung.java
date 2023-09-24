package dhxd.chukimmuoi.service;

//import java.util.HashMap;

import dhxd.chukimmuoi.model.tbl_nguoidung;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ser_nguoidung extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "dhxd_datn_quanlytaichinh";

	private static final String TABLE_NGUOIDUNG = "tbl_nguoidung";

	private static final String KEY_UNIQUE_ID = "Unique_Id";
	private static final String KEY_HOTEN = "HoTen";
	private static final String KEY_EMAIL = "Email";
	private static final String KEY_MATKHAU = "MatKhau";
	private static final String KEY_MABAM = "MaBam";
	private static final String KEY_NGAYTAO = "NgayTao";

	public ser_nguoidung(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE_NGUOIDUNG = "CREATE TABLE " + TABLE_NGUOIDUNG
				+ " (" + KEY_UNIQUE_ID + " TEXT, " + KEY_HOTEN + " TEXT, "
				+ KEY_EMAIL + " TEXT PRIMARY KEY UNIQUE, " + KEY_MATKHAU
				+ " TEXT, " + KEY_MABAM + " TEXT, " + KEY_NGAYTAO + " TEXT)";
		db.execSQL(CREATE_TABLE_NGUOIDUNG);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NGUOIDUNG);
		onCreate(db);
	}

	public void insertNguoiDung(tbl_nguoidung tblND) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_UNIQUE_ID, tblND.getUnique_Id());
		values.put(KEY_HOTEN, tblND.getHoTen());
		values.put(KEY_EMAIL, tblND.getEmail());
		values.put(KEY_MATKHAU, tblND.getMatKhau());
		values.put(KEY_MABAM, tblND.getMaBam());
		values.put(KEY_NGAYTAO, tblND.getNgayTao());

		db.insert(TABLE_NGUOIDUNG, null, values);
		db.close();
	}

	public tbl_nguoidung selectNguoiDung() {
		tbl_nguoidung tblND = new tbl_nguoidung();
		String sqlSelect = "SELECT * FROM " + TABLE_NGUOIDUNG;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sqlSelect, null);
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			tblND.setUnique_Id(cursor.getString(0));
			tblND.setHoTen(cursor.getString(1));
			tblND.setEmail(cursor.getString(2));
			tblND.setMatKhau(cursor.getString(3));
			tblND.setMaBam(cursor.getString(4));
			tblND.setNgayTao(cursor.getString(5));
		}
		cursor.close();
		db.close();
		return tblND;
	}

	public int getSoDong() {
		String sqlSelect = "SELECT * FROM " + TABLE_NGUOIDUNG;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sqlSelect, null);
		int sodong = cursor.getCount();
		db.close();
		cursor.close();
		return sodong;
	}

	public void deleteNguoiDung() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NGUOIDUNG, null, null);
		db.close();
	}

}
