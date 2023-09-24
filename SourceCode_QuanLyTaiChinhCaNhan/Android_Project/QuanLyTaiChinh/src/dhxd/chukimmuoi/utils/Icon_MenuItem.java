package dhxd.chukimmuoi.utils;

import android.content.Context;
import dhxd.chukimmuoi.qltc.R;

public class Icon_MenuItem {

	// Set all the navigation icons and always to set "zero 0" for the item is a
	// category
	public static int[] iconNavigation = new int[] { 0,
			R.drawable.ic_danhsachgiaodich, R.drawable.ic_baocao,
			R.drawable.ic_sono, 0, R.drawable.ic_thongbao,
			R.drawable.ic_doimatkhau, R.drawable.ic_dangnhap, 0,
			R.drawable.ic_vi, R.drawable.ic_nhom, R.drawable.ic_dotac, 0,
			R.drawable.ic_ngansach, R.drawable.ic_congviec };

	// get title of the item navigation
	public static String getTitleItem(Context context, int posicao) {
		String[] titulos = context.getResources().getStringArray(
				R.array.nav_menu_items);
		return titulos[posicao];
	}

	public static int[] danhsachanhchon = new int[] { R.drawable.icon_1,
			R.drawable.icon_2, R.drawable.icon_3, R.drawable.icon_4,
			R.drawable.icon_5, R.drawable.icon_6, R.drawable.icon_7,
			R.drawable.icon_8, R.drawable.icon_9, R.drawable.icon_10,
			R.drawable.icon_11, R.drawable.icon_12, R.drawable.icon_13,
			R.drawable.icon_14, R.drawable.icon_15, R.drawable.icon_16,
			R.drawable.icon_17, R.drawable.icon_18, R.drawable.icon_19,
			R.drawable.icon_20, R.drawable.icon_21, R.drawable.icon_22,
			R.drawable.icon_23, R.drawable.icon_24, R.drawable.icon_25,
			R.drawable.icon_26, R.drawable.icon_27, R.drawable.icon_28,
			R.drawable.icon_29, R.drawable.icon_30, R.drawable.icon_31,
			R.drawable.icon_32, R.drawable.icon_33, R.drawable.icon_34,
			R.drawable.icon_35, R.drawable.icon_36, R.drawable.icon_37,
			R.drawable.icon_38, R.drawable.icon_39, R.drawable.icon_40,
			R.drawable.icon_41, R.drawable.icon_42, R.drawable.icon_43,
			R.drawable.icon_44, R.drawable.icon_45, R.drawable.icon_46,
			R.drawable.icon_47, R.drawable.icon_48, R.drawable.icon_49,
			R.drawable.icon_50, R.drawable.icon_51, R.drawable.icon_52,
			R.drawable.icon_53, R.drawable.icon_54, R.drawable.icon_55,
			R.drawable.icon_56, R.drawable.icon_57, R.drawable.icon_58,
			R.drawable.icon_59, R.drawable.icon_60, R.drawable.icon_61,
			R.drawable.icon_62, R.drawable.icon_63, R.drawable.icon_64,
			R.drawable.icon_65, R.drawable.icon_66, R.drawable.icon_67,
			R.drawable.icon_68, R.drawable.icon_69, R.drawable.icon_70,
			R.drawable.icon_71, R.drawable.icon_72, R.drawable.icon_73,
			R.drawable.icon_74, R.drawable.icon_75, R.drawable.icon_76,
			R.drawable.icon_77, R.drawable.icon_78, R.drawable.icon_79,
			R.drawable.icon_80, R.drawable.icon_81, R.drawable.icon_82,
			R.drawable.icon_83, R.drawable.icon_84, R.drawable.icon_85,
			R.drawable.icon_86, R.drawable.icon_87, R.drawable.icon_88,
			R.drawable.icon_89, R.drawable.icon_90, R.drawable.icon_91,
			R.drawable.icon_92, R.drawable.icon_93, R.drawable.icon_94,
			R.drawable.icon_95, R.drawable.icon_96, R.drawable.icon_97,
			R.drawable.icon_98, R.drawable.icon_99, R.drawable.icon_100,
			R.drawable.icon_101, R.drawable.icon_102, R.drawable.icon_103,
			R.drawable.icon_104, R.drawable.icon_105, R.drawable.icon_106,
			R.drawable.icon_107, R.drawable.icon_108, R.drawable.icon_109,
			R.drawable.icon_110, R.drawable.icon_111, R.drawable.icon_112,
			R.drawable.icon_113, R.drawable.icon_114, R.drawable.icon_115,
			R.drawable.icon_116, R.drawable.icon_117, R.drawable.icon_118,
			R.drawable.icon_119, R.drawable.icon_120, R.drawable.icon_121,
			R.drawable.icon_122, R.drawable.icon_123, R.drawable.icon_124,
			R.drawable.icon_125, R.drawable.icon_126, R.drawable.icon_127, 
			R.drawable.icon_128 };

	public static int[] anhchucnang = new int[] { R.drawable.ic_btn_sua,
			R.drawable.ic_btn_xoa, R.drawable.ic_brn_huy };

	public static int[] anhchucnang_chuyentien = new int[] {
			R.drawable.ic_btn_chuyetien, R.drawable.ic_btn_sua,
			R.drawable.ic_btn_xoa, R.drawable.ic_brn_huy };
	
	public static int[] anhchucnang_giaodich = new int[] {
		R.drawable.ic_btn_chuyetien, R.drawable.ic_btn_sua,
		R.drawable.ic_btn_xoa, R.drawable.ic_brn_huy };
	
	public static int[] anhchucnang_kehoachcongviec = new int[] {
		R.drawable.ic_ngaybatdau, R.drawable.ic_btn_sua,
		R.drawable.ic_btn_xoa, R.drawable.ic_brn_huy };
	
	public static int[] xacnhanbanbe = new int[] { R.drawable.ic_ngaybatdau,
		R.drawable.ic_btn_xoa, R.drawable.ic_brn_huy };
	
	public static int[] anhchucnang_sono = new int[] { R.drawable.ic_btn_chuyetien, 
		R.drawable.ic_ngaybatdau, R.drawable.ic_brn_huy };
}
