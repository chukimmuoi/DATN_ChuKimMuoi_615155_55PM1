<?php

if (isset($_POST['tag']) && $_POST['tag'] != '') {
    $chucnang = $_POST['tag'];
    require_once '../service/ser_nhom.php';
    require_once '../utils/guimail.php';
    $db = new ser_nhom();
    $send = new guimail();
    $response = array("tag" => $chucnang, "success" => 0, "error" => 0);

    if ($chucnang == 'insertNhom') {
        $tennhom = $_POST['TenNhom'];
        $loai_id = $_POST['Loai_Id'];
        $anh = $_POST['Anh'];
        $email = $_POST['Email'];
        if ($db->testTonTai($tennhom, $email)) {
            $response["error"] = 2;
            $response["error_msg"] = "Tên nhóm đã tồn tại, vui lòng kiểm tra lại.";
            echo json_encode($response);
        } else {
            $nhom = $db->insertNhom($tennhom, $loai_id, $anh, $email);
            if ($nhom != false) {
                $response["success"] = 1;
                $response["error_msg"] = "Bạn vừa thêm một nhóm thu chi mới.";
                $response["nhom"]["Nhom_Id"] = $nhom["Nhom_Id"];
                $response["nhom"]["TenNhom"] = $nhom["TenNhom"];
                $response["nhom"]["Loai_Id"] = $nhom["Loai_Id"];
                $response["nhom"]["Anh"] = $nhom["Anh"];
                $response["nhom"]["Email"] = $nhom["Email"];

                echo json_encode($response);
            } else {

                $response["error"] = 1;
                $response["error_msg"] = "Lỗi xảy ra khi xử lý thêm nhóm.";
                echo json_encode($response);
            }
        }
    }

    if ($chucnang == 'selectNhomLoai') {
        $loai_id = $_POST['Loai_Id'];
        $email = $_POST['Email'];
        $nhom = $db->selectNhom_Loai($loai_id, $email);
        if ($nhom != false) {
            $response["success"] = 1;
            $response["error_msg"] = "Trên đây là dữ liệu các nhóm thu - chi của bạn.";
            $mang = array();
            while ($dong = mysql_fetch_array($nhom)) {
                $tbl_nhom = array();
                $tbl_nhom["Nhom_Id"] = $dong["Nhom_Id"];
                $tbl_nhom["TenNhom"] = $dong["TenNhom"];
                $tbl_nhom["Loai_Id"] = $dong["Loai_Id"];
                $tbl_nhom["Anh"] = $dong["Anh"];
                $tbl_nhom["Email"] = $dong["Email"];

                $mang[] = $tbl_nhom;
            }
            $response["tbl_nhom"] = $mang;
            echo json_encode($response);
        } else {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý lấy dữ liệu.";
            echo json_encode($response);
        }
    }
    
    if ($chucnang == 'selectNhom') {
        $email = $_POST['Email'];
        $nhom = $db->selectNhom($email);
        if ($nhom != false) {
            $response["success"] = 1;
            $response["error_msg"] = "Trên đây là dữ liệu các nhóm thu - chi của bạn.";
            $mang = array();
            while ($dong = mysql_fetch_array($nhom)) {
                $tbl_nhom = array();
                $tbl_nhom["Nhom_Id"] = $dong["Nhom_Id"];
                $tbl_nhom["TenNhom"] = $dong["TenNhom"];
                $tbl_nhom["Loai_Id"] = $dong["Loai_Id"];
                $tbl_nhom["Anh"] = $dong["Anh"];
                $tbl_nhom["Email"] = $dong["Email"];

                $mang[] = $tbl_nhom;
            }
            $response["tbl_nhom"] = $mang;
            echo json_encode($response);
        } else {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý lấy dữ liệu.";
            echo json_encode($response);
        }
    }
    
    if($chucnang == 'updateNhom')
    {
        $nhom_id = $_POST['Nhom_Id'];
        $tennhom = $_POST['TenNhom'];
        $loai_id = $_POST['Loai_Id'];
        $anh = $_POST['Anh'];
        
        $nhom = $db->updateNhom($nhom_id, $tennhom, $loai_id, $anh);
        if($nhom != false)
        {
            $response["success"] = 1;
            $response["error_msg"] = "Cập nhật dữ liệu nhóm thu - chi thành công.";
            echo json_encode($response);
        }
        else
        {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý dữ liệu.";
            echo json_encode($response);
        }
    }
    
    if($chucnang == "deleteNhom")
    {
        $nhom_id = $_POST['Nhom_Id'];
        
        $nhom = $db->deleteNhom($nhom_id);
        if($nhom != false)
        {
            $response["success"] = 1;
            $response["error_msg"] = "Xóa dữ liệu nhóm thu - chi thành công.";
            echo json_encode($response);
        }
        else
        {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý dữ liệu.";
            echo json_encode($response);
        }
    }
} else {
    echo "Không có chức năng nào được thực hiện.";
}
