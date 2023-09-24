<?php

if (isset($_POST['tag']) && $_POST['tag'] != '') {
    $chucnang = $_POST['tag'];
    require_once '../service/ser_kehoach.php';
    require_once '../service/ser_nhom.php';
    require_once '../utils/guimail.php';
    $db = new ser_kehoach();
    $db_nhom = new ser_nhom();
    $send = new guimail();
    $response = array("tag" => $chucnang, "success" => 0, "error" => 0);

    if ($chucnang == 'insertKeHoach') {
        $nhom_id = $_POST['Nhom_Id'];
        $loai_id = $_POST['Loai_Id'];
        $vi_id = $_POST['Vi_Id'];
        $email = $_POST['Email'];
        $ngaybatdau = $_POST['NgayBatDau'];
        $dienta = $_POST['DienTa'];
        $sotien = $_POST['SoTien'];
        $kehoach = $db->insertKeHoachCongViec($nhom_id, $loai_id, $vi_id, $email, $ngaybatdau, $dienta, $sotien);
        if ($kehoach != false) {
            $response["success"] = 1;
            $response["error_msg"] = "Bạn vừa thêm một kế hoạch mới.";
            $response["kehoach"]["KeHoach_Id"] = $kehoach["KeHoach_Id"];
            $response["kehoach"]["Nhom_Id"] = $kehoach["Nhom_Id"];
            $response["kehoach"]["Loai_Id"] = $kehoach["Loai_Id"];
            $response["kehoach"]["Vi_Id"] = $kehoach["Vi_Id"];
            $response["kehoach"]["EmailDoiTac"] = $kehoach["EmailDoiTac"];
            $response["kehoach"]["Email"] = $kehoach["Email"];
            $response["kehoach"]["NgayBatDau"] = $kehoach["NgayBatDau"];
            $response["kehoach"]["NgayKetThuc"] = $kehoach["NgayKetThuc"];
            $response["kehoach"]["DienTa"] = $kehoach["DienTa"];
            $response["kehoach"]["SoTien"] = $kehoach["SoTien"];
            $response["kehoach"]["TrangThai"] = $kehoach["TrangThai"];
            $response["kehoach"]["LoaiKeHoach"] = $kehoach["LoaiKeHoach"];
            echo json_encode($response);
        } else {

            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý thêm kế hoạch.";
            echo json_encode($response);
        }
    }

    if ($chucnang == "selectKeHoachCongViec") {
        $email = $_POST['Email'];
        $kehoach = $db->selectKeHoachCongViec($email);
        if ($kehoach != false) {
            $response["success"] = 1;
            $response["error_msg"] = "Trên đây là dữ liệu kế hoạch công việc của bạn trong các tháng.";
            $mang = array();
            while ($dong = mysql_fetch_array($kehoach)) {
                $tbl_kehoach = array();
                $tbl_kehoach["KeHoach_Id"] = $dong["KeHoach_Id"];
                $tbl_kehoach["Nhom_Id"] = $dong["Nhom_Id"];
                $tbl_kehoach["TenNhom"] = $dong["TenNhom"];
                $tbl_kehoach["Loai_Id"] = $dong["Loai_Id"];
                $tbl_kehoach["Anh"] = $dong["Anh"];
                $tbl_kehoach["SoTien"] = $dong["SoTien"];
                $tbl_kehoach["DienTa"] = $dong["DienTa"];
                $tbl_kehoach["NgayBatDau"] = $dong["NgayBatDau"];
                $tbl_kehoach["Vi_Id"] = $dong["Vi_Id"];
                $tbl_kehoach["TenQuy"] = $dong["TenQuy"];
                $tbl_kehoach["SoTienQuy"] = $dong["SoTienQuy"];
                $tbl_kehoach["AnhQuy"] = $dong["AnhQuy"];
                $tbl_kehoach["Email"] = $dong["Email"];

                $mang[] = $tbl_kehoach;
            }
            $response["tbl_kehoach"] = $mang;
            echo json_encode($response);
        } else {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý lấy dữ liệu.";
            echo json_encode($response);
        }
    }

    if ($chucnang == "updateKeHoachCongViec") {
        $kehoach_id = $_POST['KeHoach_Id'];
        $nhom_id = $_POST['Nhom_Id'];
        $loai_id = $_POST['Loai_Id'];
        $vi_id = $_POST['Vi_Id'];
        $email = $_POST['Email'];
        $ngaybatdau = $_POST['NgayBatDau'];
        $dienta = $_POST['DienTa'];
        $sotien = $_POST['SoTien'];
        $kehoach = $db->updateKeHoachCongViec($kehoach_id, $nhom_id, $loai_id, $vi_id, $email, $ngaybatdau, $dienta, $sotien);
        if ($kehoach != false) {
            $response["success"] = 1;
            $response["error_msg"] = "Sửa kế hoạch công việc thành công";
            echo json_encode($response);
        } else {

            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý sửa kế hoạch.";
            echo json_encode($response);
        }
    }

    if ($chucnang == "deleteKeHoach") {
        $kehoach_id = $_POST['KeHoach_Id'];
        $email = $_POST['Email'];
        $kehoach = $db->deleteKeHoach($kehoach_id, $email);
        if ($kehoach != false) {
            $response["success"] = 1;
            $response["error_msg"] = "Đã xóa dữ liệu yêu cầu khỏi danh sách.";
            echo json_encode($response);
        } else {

            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý dữ liệu.";
            echo json_encode($response);
        }
    }

    if ($chucnang == 'insertKeHoachNganSach') {
        $nhom_id = $_POST['Nhom_Id'];
        $loai_id = $_POST['Loai_Id'];
        $email = $_POST['Email'];
        $ngaybatdau = $_POST['NgayBatDau'];
        $ngayketthuc = $_POST['NgayKetThuc'];
        $dienta = $_POST['DienTa'];
        $sotien = $_POST['SoTien'];
        $kehoach = $db->insertKeHoachNganSach($nhom_id, $loai_id, $email, $ngaybatdau, $ngayketthuc, $dienta, $sotien);
        if ($kehoach != false) {
            $response["success"] = 1;
            $response["error_msg"] = "Bạn vừa thêm một kế hoạch mới.\nKế hoạch ngân sách sẽ khả dụng khi bạn thực hiện giao dịch có liên quan.";
            $response["kehoach"]["KeHoach_Id"] = $kehoach["KeHoach_Id"];
            $response["kehoach"]["Nhom_Id"] = $kehoach["Nhom_Id"];
            $response["kehoach"]["Loai_Id"] = $kehoach["Loai_Id"];
            $response["kehoach"]["Vi_Id"] = $kehoach["Vi_Id"];
            $response["kehoach"]["EmailDoiTac"] = $kehoach["EmailDoiTac"];
            $response["kehoach"]["Email"] = $kehoach["Email"];
            $response["kehoach"]["NgayBatDau"] = $kehoach["NgayBatDau"];
            $response["kehoach"]["NgayKetThuc"] = $kehoach["NgayKetThuc"];
            $response["kehoach"]["DienTa"] = $kehoach["DienTa"];
            $response["kehoach"]["SoTien"] = $kehoach["SoTien"];
            $response["kehoach"]["TrangThai"] = $kehoach["TrangThai"];
            $response["kehoach"]["LoaiKeHoach"] = $kehoach["LoaiKeHoach"];
            echo json_encode($response);
        } else {

            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý thêm kế hoạch.";
            echo json_encode($response);
        }
    }

    if ($chucnang == "selectKeHoachNganSach0") {
        $email = $_POST['Email'];
        $ngayhientai = $_POST['NgayBatDau'];
        $kehoach = $db->selectKeHoachNganSach($email, $ngayhientai);
        if ($kehoach != false) {
            $response["success"] = 1;
            $response["error_msg"] = "Trên đây là dữ liệu kế hoạch ngân sách của bạn.";
            $mang = array();
            while ($dong = mysql_fetch_array($kehoach)) {
                $tbl_kehoach = array();
                $tbl_kehoach["KeHoach_Id"] = $dong["KeHoach_Id"];
                $tbl_kehoach["Nhom_Id"] = $dong["Nhom_Id"];
                $tbl_kehoach["TenNhom"] = $dong["TenNhom"];
                $tbl_kehoach["Loai_Id"] = $dong["Loai_Id"];
                $tbl_kehoach["Anh"] = $dong["Anh"];
                $tbl_kehoach["Email"] = $dong["Email"];
                $tbl_kehoach["NgayBatDau"] = $dong["NgayBatDau"];
                $tbl_kehoach["NgayKetThuc"] = $dong["NgayKetThuc"];
                $tbl_kehoach["DienTa"] = $dong["DienTa"];
                $tbl_kehoach["SoTienNganSach"] = $dong["SoTienNganSach"];

                $tbl_kehoach["GiaoDich_Id"] = $dong["GiaoDich_Id"];
                $tbl_kehoach["SoTien"] = $dong["SoTien"];
                $tbl_kehoach["GhiChu"] = $dong["GhiChu"];
                $tbl_kehoach["EmailDoiTac"] = $dong["EmailDoiTac"];
                $tbl_kehoach["NgayThang"] = $dong["NgayThang"];
                $tbl_kehoach["Quy_Id"] = $dong["Quy_Id"];
                $tbl_kehoach["NgayTra"] = $dong["NgayTra"];
                $tbl_kehoach["TienLai"] = $dong["TienLai"];
                $tbl_kehoach["LoaiThuLai"] = $dong["LoaiThuLai"];
                $tbl_kehoach["TenQuy"] = $dong["TenQuy"];
                $tbl_kehoach["SoTienQuy"] = $dong["SoTienQuy"];
                $tbl_kehoach["AnhQuy"] = $dong["AnhQuy"];

                $mang[] = $tbl_kehoach;
            }
            $response["tbl_kehoach"] = $mang;
            echo json_encode($response);
        } else {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý lấy dữ liệu.";
            echo json_encode($response);
        }
    }

    if ($chucnang == "selectKeHoachNganSach1") {
        $email = $_POST['Email'];
        $ngayhientai = $_POST['NgayBatDau'];
        $kehoach = $db->selectKeHoachNganSach1($email, $ngayhientai);
        if ($kehoach != false) {
            $response["success"] = 1;
            $response["error_msg"] = "Trên đây là dữ liệu kế hoạch ngân sách của bạn.";
            $mang = array();
            while ($dong = mysql_fetch_array($kehoach)) {
                $tbl_kehoach = array();
                $tbl_kehoach["KeHoach_Id"] = $dong["KeHoach_Id"];
                $tbl_kehoach["Nhom_Id"] = $dong["Nhom_Id"];
                $tbl_kehoach["TenNhom"] = $dong["TenNhom"];
                $tbl_kehoach["Loai_Id"] = $dong["Loai_Id"];
                $tbl_kehoach["Anh"] = $dong["Anh"];
                $tbl_kehoach["Email"] = $dong["Email"];
                $tbl_kehoach["NgayBatDau"] = $dong["NgayBatDau"];
                $tbl_kehoach["NgayKetThuc"] = $dong["NgayKetThuc"];
                $tbl_kehoach["DienTa"] = $dong["DienTa"];
                $tbl_kehoach["SoTienNganSach"] = $dong["SoTienNganSach"];

                $tbl_kehoach["GiaoDich_Id"] = $dong["GiaoDich_Id"];
                $tbl_kehoach["SoTien"] = $dong["SoTien"];
                $tbl_kehoach["GhiChu"] = $dong["GhiChu"];
                $tbl_kehoach["EmailDoiTac"] = $dong["EmailDoiTac"];
                $tbl_kehoach["NgayThang"] = $dong["NgayThang"];
                $tbl_kehoach["Quy_Id"] = $dong["Quy_Id"];
                $tbl_kehoach["NgayTra"] = $dong["NgayTra"];
                $tbl_kehoach["TienLai"] = $dong["TienLai"];
                $tbl_kehoach["LoaiThuLai"] = $dong["LoaiThuLai"];
                $tbl_kehoach["TenQuy"] = $dong["TenQuy"];
                $tbl_kehoach["SoTienQuy"] = $dong["SoTienQuy"];
                $tbl_kehoach["AnhQuy"] = $dong["AnhQuy"];

                $mang[] = $tbl_kehoach;
            }
            $response["tbl_kehoach"] = $mang;
            echo json_encode($response);
        } else {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý lấy dữ liệu.";
            echo json_encode($response);
        }
    }

    if ($chucnang == "updateKeHoachNganSach") {
        $kehoach_id = $_POST['KeHoach_Id'];
        $nhom_id = $_POST['Nhom_Id'];
        $loai_id = $_POST['Loai_Id'];
        $email = $_POST['Email'];
        $ngaybatdau = $_POST['NgayBatDau'];
        $ngayketthuc = $_POST['NgayKetThuc'];
        $dienta = $_POST['DienTa'];
        $sotien = $_POST['SoTien'];
        $kehoach = $db->updatKeHoachNganSach($kehoach_id, $nhom_id, $loai_id, $email, $ngaybatdau, $ngayketthuc, $dienta, $sotien);
        if ($kehoach != false) {
            $response["success"] = 1;
            $response["error_msg"] = "Sửa kế hoạch ngân sách thành công";
            echo json_encode($response);
        } else {

            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý sửa kế hoạch.";
            echo json_encode($response);
        }
    }

    if ($chucnang == "selectThongBaoGiaoDich") {
        $email = $_POST['Email'];
        $kehoach = $db->selectThongBaoGiaoDich($email);
        if ($kehoach != false) {
            $response["success"] = 1;
            $response["error_msg"] = "Trên đây là thông báo các giao dịch của bạn.";
            $mang = array();
            while ($dong = mysql_fetch_array($kehoach)) {
                $tbl_kehoach = array();
                $tbl_kehoach["KeHoach_Id"] = $dong["KeHoach_Id"];
                $tbl_kehoach["Nhom_Id"] = $dong["Nhom_Id"];
                $tbl_kehoach["Loai_Id"] = $dong["Loai_Id"];
                $tbl_kehoach["SoTien"] = $dong["SoTien"];
                $tbl_kehoach["DienTa"] = $dong["DienTa"];
                $tbl_kehoach["Email"] = $dong["Email"];
                $tbl_kehoach["EmailDoiTac"] = $dong["EmailDoiTac"];
                $tbl_kehoach["NgayBatDau"] = $dong["NgayBatDau"];
                $tbl_kehoach["GiaoDich_Id"] = $dong["GiaoDich_Id"];
                $tbl_kehoach["LoaiKeHoach"] = $dong["LoaiKeHoach"];
                $tbl_kehoach["TrangThai"] = $dong["TrangThai"];
                if ($dong["Nhom_Id"] != 'null') {
                    $nhom = $db_nhom->selectNhom_Id($dong["Nhom_Id"]);
                    $tbl_kehoach["Anh"] = $nhom["Anh"];
                    $tbl_kehoach["TenNhom"] = $nhom["TenNhom"];
                }
                if($dong["GiaoDich_Id"] != 'null' || $dong["GiaoDich_Id"] != 0)
                {
                    $tbl_kehoach["NgayTra"] = $dong["NgayTra"];
                    $tbl_kehoach["TienLai"] = $dong["TienLai"];
                    $tbl_kehoach["LoaiThuLai"] = $dong["LoaiThuLai"];
                }

                $mang[] = $tbl_kehoach;
            }

            $response["tbl_kehoach"] = $mang;
            echo json_encode($response);
            $db->updateDaXem($email);
        } else {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý dữ liệu.";
            echo json_encode($response);
        }
    }
    
    if ($chucnang == "deleteKeHoachGiaoDich") {
        $giaodich_id = $_POST['GiaoDich_Id'];
        $email = $_POST['Email'];
        $kehoach = $db->deleteKeHoachGiaoDich($giaodich_id, $email);
        if ($kehoach != false) {
            $response["success"] = 1;
            $response["error_msg"] = "Đã xóa dữ liệu yêu cầu khỏi danh sách.";
            echo json_encode($response);
        } else {

            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý dữ liệu.";
            echo json_encode($response);
        }
    }
    
    if($chucnang == "selectSoNo")
    {
        $email = $_POST['Email'];
        $loai_id = $_POST['Loai_Id'];
        $kehoach = $db->selectSoNo($email, $loai_id);
        if($kehoach != false)
        {
            $response["success"] = 1;
            $response["error_msg"] = "Trên đây là tổng hợp dữ liệu vay và cho vay của bạn.";
            $mang = array();
            while ($dong = mysql_fetch_array($kehoach)) {
                $tbl_kehoach = array();
                $tbl_kehoach["KeHoach_Id"] = $dong["KeHoach_Id"];
                $tbl_kehoach["Loai_Id_tblKH"] = $dong["Loai_Id_tblKH"];
                $tbl_kehoach["EmailDoiTac_tblKH"] = $dong["EmailDoiTac_tblKH"];
                $tbl_kehoach["Email_tblKH"] = $dong["Email_tblKH"];
                $tbl_kehoach["NgayBatDau"] = $dong["NgayBatDau"];
                $tbl_kehoach["DienTa"] = $dong["DienTa"];
                $tbl_kehoach["SoTien_tblKH"] = $dong["SoTien_tblKH"];
                $tbl_kehoach["LoaiKeHoach"] = $dong["LoaiKeHoach"];
                
                $tbl_kehoach["GiaoDich_Id"] = $dong["GiaoDich_Id"];
                $tbl_kehoach["Nhom_Id"] = $dong["Nhom_Id"];
                $tbl_kehoach["TenNhom"] = $dong["TenNhom"];
                $tbl_kehoach["Loai_Id_tblN"] = $dong["Loai_Id_tblN"];
                $tbl_kehoach["Anh"] = $dong["Anh"];
                
                $tbl_kehoach["SoTien_tblGD"] = $dong["SoTien_tblGD"];
                $tbl_kehoach["GhiChu"] = $dong["GhiChu"];
                $tbl_kehoach["EmailDoiTac_tblGD"] = $dong["EmailDoiTac_tblGD"];
                $tbl_kehoach["NgayThang"] = $dong["NgayThang"];
                
                $tbl_kehoach["Quy_Id"] = $dong["Quy_Id"];
                $tbl_kehoach["SoTien_tblQ"] = $dong["SoTien_tblQ"];
                $tbl_kehoach["TenQuy"] = $dong["TenQuy"];
                
                $tbl_kehoach["Email_tblGD"] = $dong["Email_tblGD"];
                $tbl_kehoach["NgayTra"] = $dong["NgayTra"];
                $tbl_kehoach["TienLai"] = $dong["TienLai"];
                $tbl_kehoach["LoaiThuLai"] = $dong["LoaiThuLai"];
                
                $mang[] = $tbl_kehoach;
            }
            $response["tbl_kehoach"] = $mang;
            echo json_encode($response);
        }else {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý dữ liệu.";
            echo json_encode($response);
        }
    }
} else {
    echo "Không có chức năng nào được thực hiện.";
}

