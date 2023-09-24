<?php

if (isset($_POST['tag']) && $_POST['tag'] != '') {
    $chucnang = $_POST['tag'];
    require_once '../service/ser_quy.php';
    require_once '../utils/guimail.php';
    $db = new ser_quy();
    $send = new guimail();
    $response = array("tag" => $chucnang, "success" => 0, "error" => 0);

    if ($chucnang == 'selectQuy') {
        $email = $_POST['Email'];
        $quy = $db->selectQuy($email);
        if ($quy != false) {
            $response["success"] = 1;
            $response["error_msg"] = "Trên đây là dữ liệu các quỹ tiêu dùng của bạn.";
            $mang = array();
            while ($dong = mysql_fetch_array($quy)) {
                $tbl_quy = array();
                $tbl_quy["Quy_Id"] = $dong["Quy_Id"];
                $tbl_quy["TenQuy"] = $dong["TenQuy"];
                $tbl_quy["Anh"] = $dong["Anh"];
                $tbl_quy["SoTien"] = $dong["SoTien"];
                $tbl_quy["Email"] = $dong["Email"];

                $mang[] = $tbl_quy;
            }
            $response["tbl_quy"] = $mang;
            echo json_encode($response);
        } else {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý lấy dữ liệu.";
            echo json_encode($response);
        }
    }
    
    if ($chucnang == 'insertQuy') {
        $tenquy = $_POST['TenQuy'];
        $anh = $_POST['Anh'];
        $sotien = $_POST['SoTien'];
        $email = $_POST['Email'];
        if ($db->testTonTai($tenquy, $email)) {
            $response["error"] = 2;
            $response["error_msg"] = "Tên quỹ đã tồn tại, vui lòng kiểm tra lại.";
            echo json_encode($response);
        } else {
            $quy = $db->insertQuy($tenquy, $anh, $sotien, $email);
            if ($quy != false) {
                $response["success"] = 1;
                $response["error_msg"] = "Bạn vừa thêm một quỹ tiêu dùng mới.";
                $response["quy"]["Quy_Id"] = $quy["Quy_Id"];
                $response["quy"]["TenQuy"] = $quy["TenQuy"];
                $response["quy"]["Anh"] = $quy["Anh"];
                $response["quy"]["SoTien"] = $quy["SoTien"];
                $response["quy"]["Email"] = $quy["Email"];

                echo json_encode($response);
            } else {

                $response["error"] = 1;
                $response["error_msg"] = "Lỗi xảy ra khi xử lý thêm quỹ.";
                echo json_encode($response);
            }
        }
    }
    
    if($chucnang == 'updateQuy')
    {
        $quy_id = $_POST['Quy_Id'];
        $tenquy = $_POST['TenQuy'];
        $anh = $_POST['Anh'];
        $sotien = $_POST['SoTien'];
        
        $quy = $db->updateQuy($quy_id, $tenquy, $anh, $sotien);
        if($quy != false)
        {
            $response["success"] = 1;
            $response["error_msg"] = "Cập nhật dữ liệu quỹ tiêu dùng thành công.";
            echo json_encode($response);
        }
        else
        {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý dữ liệu.";
            echo json_encode($response);
        }
    }
    
    if($chucnang == "deleteQuy")
    {
        $quy_id = $_POST['Quy_Id'];
        
        $quy = $db->deleteQuy($quy_id);
        if($quy != false)
        {
            $response["success"] = 1;
            $response["error_msg"] = "Xóa dữ liệu quỹ tiêu dùng thành công.";
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
