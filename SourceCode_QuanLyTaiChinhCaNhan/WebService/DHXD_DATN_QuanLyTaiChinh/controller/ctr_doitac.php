<?php

if (isset($_POST['tag']) && $_POST['tag'] != '') {
    $chucnang = $_POST['tag'];
    require_once '../service/ser_doitac.php';
    require_once '../utils/guimail.php';
    $db = new ser_doitac();
    $send = new guimail();
    $response = array("tag" => $chucnang, "success" => 0, "error" => 0);

    if ($chucnang == 'selectDoiTac') {
        $email = $_POST['Email'];
        $doitac = $db->selectDoiTac($email);
        if ($doitac != false) {
            $response["success"] = 1;
            $response["error_msg"] = "Trên đây là danh sách bạn bè của bạn.";
            $mang = array();
            while ($dong = mysql_fetch_array($doitac)) {
                $tbl_doitac = array();
                $tbl_doitac["DoiTac_Id"] = $dong["DoiTac_Id"];
                $tbl_doitac["Email"] = $dong["HoTen"];
                $tbl_doitac["EmailDoiTac"] = $dong["EmailDoiTac"];
                $tbl_doitac["QuanHe"] = $dong["QuanHe"];
                $tbl_doitac["XacNhan"] = $dong["XacNhan"];

                $mang[] = $tbl_doitac;
            }
            $response["tbl_doitac"] = $mang;
            echo json_encode($response);
        } else {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý lấy dữ liệu.";
            echo json_encode($response);
        }
    }

    if ($chucnang == "insertDoiTac") {
        $email = $_POST['Email'];
        $emaildoitac = $_POST['EmailDoiTac'];
        $quanhe = $_POST['QuanHe'];
        if ($db->testEmail($emaildoitac)) {
            if ($db->testTonTai($email, $emaildoitac)) {
                $response["error"] = 2;
                $response["error_msg"] = "Yêu cầu kết bạn đã được gửi đi trước đây.";
                echo json_encode($response);
            } else {
                $doitac = $db->insertDoiTac($email, $emaildoitac, $quanhe);
                if ($doitac != false) {
                    $response["success"] = 1;
                    $response["error_msg"] = "Yêu cầu kết bạn đã được gửi đi, vui lòng chờ hồi âm.";
                    echo json_encode($response);
                } else {
                    $response["error"] = 1;
                    $response["error_msg"] = "Lỗi xảy ra khi xử lý lấy dữ liệu.";
                    echo json_encode($response);
                }
            }
        } else {
            $response["error"] = 3;
            $response["error_msg"] = "Email không tồn tại trong hệ thống, vui lòng kiểm tra lại.";
            echo json_encode($response);
        }
    }
    
    if($chucnang == 'updateDoiTac')
    {
        $doitac_id = $_POST['DoiTac_Id'];
        $quanhe = $_POST['QuanHe'];
        $doitac = $db->updateDoiTac($doitac_id, $quanhe);
        if($doitac != false)
        {
            $response["success"] = 1;
            $response["error_msg"] = "Hệ thống đã cập nhật mối quan hệ của bạn.";
            echo json_encode($response);
        }
        else
        {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý dữ liệu.";
            echo json_encode($response);
        }
    }
    
    if($chucnang == 'deleteDoiTac')
    {
        $doitac_id = $_POST['DoiTac_Id'];
        $email = $_POST['Email'];
        $emaildoitac = $_POST['EmailDoiTac'];
        $doitac = $db->deleteDoiTac($doitac_id);
        if($doitac != false)
        {
            $response["success"] = 1;
            $response["error_msg"] = "Hệ thống đã hủy mối quan hệ mà bạn yêu cầu.";
            echo json_encode($response);
            $db->deleteDoiTac2($email, $emaildoitac);
        }
        else
        {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý dữ liệu.";
            echo json_encode($response);
        }
    }
    
    if ($chucnang == 'selectDoiTacThongBao') {
        $email = $_POST['Email'];
        $doitac = $db->selectDoiTacThongBao($email);
        if ($doitac != false) {
            $response["success"] = 1;
            $response["error_msg"] = "Trên đây là thông báo kết bạn của bạn.";
            $mang = array();
            while ($dong = mysql_fetch_array($doitac)) {
                $tbl_doitac = array();
                $tbl_doitac["DoiTac_Id"] = $dong["DoiTac_Id"];
                $tbl_doitac["EmailDoiTac"] = $dong["Email"];
                $tbl_doitac["Email"] = $dong["HoTen"];
                $tbl_doitac["QuanHe"] = $dong["QuanHe"];
                $tbl_doitac["XacNhan"] = $dong["XacNhan"];
                $mang[] = $tbl_doitac;
            }
            $response["tbl_doitac"] = $mang;
            echo json_encode($response);
            $db->updateDaXem($email);
        } else {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý lấy dữ liệu.";
            echo json_encode($response);
        }
    }
    
    if ($chucnang == "insertDoiTacDongY") {
        $email = $_POST['Email'];
        $emaildoitac = $_POST['EmailDoiTac'];
        $quanhe = $_POST['QuanHe'];
        if ($db->testEmail($emaildoitac)) {
            if ($db->testTonTai($email, $emaildoitac)) {
                $response["error"] = 2;
                $response["error_msg"] = "Yêu cầu kết bạn đã được gửi đi trước đây.";
                echo json_encode($response);
            } else {
                $doitac = $db->insertDoiTacDongY($email, $emaildoitac, $quanhe);
                if ($doitac != false) {
                    $response["success"] = 1;
                    $response["error_msg"] = "Thêm thành công mối quan hệ.";
                    echo json_encode($response);
                } else {
                    $response["error"] = 1;
                    $response["error_msg"] = "Lỗi xảy ra khi xử lý lấy dữ liệu.";
                    echo json_encode($response);
                }
                $db->updateDoiTacDongY($emaildoitac, $email);
            }
        } else {
            $response["error"] = 3;
            $response["error_msg"] = "Email không tồn tại trong hệ thống, vui lòng kiểm tra lại.";
            echo json_encode($response);
        }
    }
    
     if($chucnang == 'deleteDoiTacThat')
    {
        $doitac_id = $_POST['DoiTac_Id'];
        $doitac = $db->deleteDoiTacThat($doitac_id);
        if($doitac != false)
        {
            $response["success"] = 1;
            $response["error_msg"] = "Đã xóa yêu cầu kết bạn khỏi hệ thống.";
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
