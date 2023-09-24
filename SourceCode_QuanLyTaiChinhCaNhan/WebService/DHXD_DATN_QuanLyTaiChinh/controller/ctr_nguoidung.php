<?php

if (isset($_POST['tag']) && $_POST['tag'] != '') {
    $chucnang = $_POST['tag'];
    require_once '../service/ser_nguoidung.php';
    require_once '../utils/guimail.php';
    $db = new ser_nguoidung();
    $send = new guimail();
    $response = array("tag" => $chucnang, "success" => 0, "error" => 0);

    if ($chucnang == 'dangky') {
        $hoten = $_POST['HoTen'];
        $email = $_POST['Email'];
        $matkhau = $_POST['MatKhau'];
        if (!$db->testEmail($email)) {
            $response["error"] = 2;
            $response["error_msg"] = "Email của bạn không hợp lệ";
            echo json_encode($response);
        } else {
            $nguoidung = $db->insertNguoiDung($hoten, $email, $matkhau);
            if ($nguoidung != false) {
                $response["success"] = 1;
                $response["error_msg"] = "Đăng ký thành công.";
                $response["nguoidung"]["Unique_Id"] = $nguoidung["Unique_Id"];
                $response["nguoidung"]["HoTen"] = $nguoidung["HoTen"];
                $response["nguoidung"]["Email"] = $nguoidung["Email"];
                $response["nguoidung"]["MatKhau"] = $nguoidung["MatKhau"];
                $response["nguoidung"]["MaBam"] = $nguoidung["MaBam"];
                $response["nguoidung"]["NgayTao"] = $nguoidung["NgayTao"];

                echo json_encode($response);
                $db->KhoiTaoDuLieuQuy($email);
                $db->KhoiTaoDuLieuNhom($email);
            } else {

                $response["error"] = 1;
                $response["error_msg"] = "Lỗi xảy ra khi xử lý đăng ký, email đã được sử dụng";
                echo json_encode($response);
            }
        }
    }

    if($chucnang == 'doimatkhau')
    {
        $email = $_POST['Email'];
        $matkhaumoi = $_POST['MatKhau'];
        
        $nguoidung = $db->updateMatKhau($email, $matkhaumoi);
        if($nguoidung != false)
        {
            $send->GuiMail($email, "Khách hàng", "Đổi mật khẩu", "Xin chào,\n\nBạn đã thay đổi mật khẩu trong hệ thống của chúng tôi. "
                        . "Mật khẩu hiện giờ của bạn là:<b> $matkhaumoi </b>. "
                        . "Hãy sử dụng mật khẩu này để sử dụng hệ thống của chúng tôi.\n\n"
                        . "Xin chân thành cảm ơn!");
            $response["success"] = 1;
                $response["error_msg"] = "Đổi mật khẩu thành công.";
                $response["nguoidung"]["Unique_Id"] = $nguoidung["Unique_Id"];
                $response["nguoidung"]["HoTen"] = $nguoidung["HoTen"];
                $response["nguoidung"]["Email"] = $nguoidung["Email"];
                $response["nguoidung"]["MatKhau"] = $nguoidung["MatKhau"];
                $response["nguoidung"]["MaBam"] = $nguoidung["MaBam"];
                $response["nguoidung"]["NgayTao"] = $nguoidung["NgayTao"];

                echo json_encode($response);
        }
        else
        {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý đổi mật khẩu. Vui lòng kiểm tra lại.";
            echo json_encode($response);
        }
    }
    if ($chucnang == 'dangnhap') {
        $email = $_POST['Email'];
        $matkhau = $_POST['MatKhau'];

        $nguoidung = $db->selectNguoiDung($email, $matkhau);
        if ($nguoidung != false) {
            $response["success"] = 1;
            $response["error_msg"] = "Đăng nhập thành công.";
            $response["nguoidung"]["Unique_Id"] = $nguoidung["Unique_Id"];
            $response["nguoidung"]["HoTen"] = $nguoidung["HoTen"];
            $response["nguoidung"]["Email"] = $nguoidung["Email"];
            $response["nguoidung"]["MatKhau"] = $nguoidung["MatKhau"];
            $response["nguoidung"]["MaBam"] = $nguoidung["MaBam"];
            $response["nguoidung"]["NgayTao"] = $nguoidung["NgayTao"];

            echo json_encode($response);
        } else {
            $response["error"] = 1;
            $response["error_msg"] = "Có lỗi xảy ra khi xử lý đăng nhập, hãy kiểm tra thông tin bạn đã nhập.";
            echo json_encode($response);
        }
    }

    if ($chucnang == 'quenmatkhau') {
        $email = $_POST['Email'];
        $matkhau = $db->random_string();
        $hash = $db->MaHoa($matkhau);
        $matkhaumahoa = $hash["matkhaumahoa"];
        $mabam = $hash["mabam"];
        if ($db->testTonTai($email)) {
            $nguoidung = $db->QuenMatKhau($email, $matkhaumahoa, $mabam);
            if ($nguoidung) {
                $send->GuiMail($email, "Khách hàng", "Cấp lại mật khẩu", "Xin chào,\n\nMật khẩu của bạn đã được tạo lại. "
                        . "Mật khẩu mới của bạn là:<b> $matkhau </b>. "
                        . "Hãy sử dụng mật khẩu này để sử dụng hệ thống của chúng tôi.\n\n"
                        . "Xin chân thành cảm ơn!");

                $response["success"] = 1;
                $response["error_msg"] = "Yêu cầu của bạn đã được gửi đi, hãy kiểm tra email của bạn.";
                echo json_encode($response);
            } else {
                $response["error"] = 1;
                $response["error_msg"] = "Xảy ra lỗi xảy ra khi xử lý gửi mail.";
                echo json_encode($response);
            }
        } else {
            $response["error"] = 2;
            $response["error_msg"] = "Email của bạn chưa được sử dụng, vui lòng kiểm tra lại.";
            echo json_encode($response);
        }
    }
} else {
    echo "Không có chức năng nào được thực hiện.";
}


