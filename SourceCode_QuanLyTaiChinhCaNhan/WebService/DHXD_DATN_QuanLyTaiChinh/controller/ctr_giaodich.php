<?php

if (isset($_POST['tag']) && $_POST['tag'] != '') {
    $chucnang = $_POST['tag'];
    require_once '../service/ser_giaodich.php';
    require_once '../service/ser_quy.php';
    require_once '../service/ser_kehoach.php';
    require_once '../service/ser_nhom.php';
    require_once '../utils/guimail.php';
    $db = new ser_giaodich();
    $dbquy = new ser_quy();
    $dbkehoach = new ser_kehoach();
    $dbnhom = new ser_nhom();
    $send = new guimail();
    $response = array("tag" => $chucnang, "success" => 0, "error" => 0);

    if ($chucnang == 'insertGiaoDichChuyenTien') {
        $nhom_id = $_POST['Nhom_Id'];
        $nhom_id_phu = $_POST['Nhom_Id_Phu'];
        $sotien = $_POST['SoTien'];
        $ghichu = $_POST['GhiChu'];
        $ghichu_phu = $_POST['GhiChu_Phu'];
        $ngaythang = $_POST['NgayThang'];
        $quy_id = $_POST['Quy_Id'];
        $quy_id_phu = $_POST['Quy_Id_Phu'];
        $email = $_POST['Email'];
        $sotienquy = $_POST['SoTienQuy'];
        $sotienquy_phu = $_POST['SoTienQuy_Phu'];
        $giaodich = $db->insertGiaoDich_ChuyenTien_($nhom_id, $nhom_id_phu, $sotien, $ghichu, $ghichu_phu, $ngaythang, $quy_id, $quy_id_phu, $email);
        if ($giaodich != false) {
            $response["success"] = 1;
            $response["error_msg"] = "Bạn vừa thêm một giao dịch.";
            echo json_encode($response);
            $dbquy->updateSoTienQuy($quy_id, $sotienquy - $sotien);
            $dbquy->updateSoTienQuy($quy_id_phu, $sotienquy_phu + $sotien);
        } else {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý thêm giao dịch.";
            echo json_encode($response);
        }
    }

    if ($chucnang == 'insertGiaoDich') {
        $nhom_id = $_POST['Nhom_Id'];
        $sotien = $_POST['SoTien'];
        $ghichu = $_POST['GhiChu'];
        $emaildoitac = $_POST['EmailDoiTac'];
        $ngaythang = $_POST['NgayThang'];
        $quy_id = $_POST['Quy_Id'];
        $sotienquy = $_POST['SoTienQuy'];
        $email = $_POST['Email'];
        $giaodich_id = $_POST['GiaoDich_Id'];

        $giaodich = $db->insertGiaoDich($nhom_id, $sotien, $ghichu, $emaildoitac, $ngaythang, $quy_id, $email, "", "", "");
        if ($giaodich != false) {
            $id = $db->giaodich_id;
            $response["success"] = 1;
            $response["error_msg"] = "Bạn vừa thêm một giao dịch.";
            $response["giaodich"]["GiaoDich_Id"] = $giaodich["GiaoDich_Id"];
            $response["giaodich"]["Nhom_Id"] = $giaodich["Nhom_Id"];
            $response["giaodich"]["SoTien"] = $giaodich["SoTien"];
            $response["giaodich"]["GhiChu"] = $giaodich["GhiChu"];
            $response["giaodich"]["EmailDoiTac"] = $giaodich["EmailDoiTac"];
            $response["giaodich"]["NgayThang"] = $giaodich["NgayThang"];
            $response["giaodich"]["Quy_Id"] = $giaodich["Quy_Id"];
            $response["giaodich"]["Email"] = $giaodich["Email"];
            if($giaodich_id == 0)
            {
                 $response["giaodich"]["SoTienQuy"] = ($sotienquy + $sotien);
            }
            else
            {
                if($giaodich_id == 1)
                {
                    $response["giaodich"]["SoTienQuy"] = ($sotienquy - $sotien);
                }
            }
            $response["giaodich"]["NgayTra"] = $giaodich["NgayTra"];
            $response["giaodich"]["TienLai"] = $giaodich["TienLai"];
            $response["giaodich"]["LoaiThuLai"] = $giaodich["LoaiThuLai"];
            echo json_encode($response);
            if ($giaodich_id == 0) {
                $dbquy->updateSoTienQuy($quy_id, $sotienquy + $sotien);
                if ($emaildoitac != "") {
                    $dbkehoach->insertKeHoachGiaoDich(1, $email, $emaildoitac, $ngaythang, "Thực hiện giao dịch chi tiêu với $email", $sotien, 0, $id);
                }
            } else {
                if ($giaodich_id == 1) {
                    $dbquy->updateSoTienQuy($quy_id, $sotienquy - $sotien);
                    if ($emaildoitac != "") {
                        $dbkehoach->insertKeHoachGiaoDich(0, $email, $emaildoitac, $ngaythang, "Thực hiện giao dịch thu nhập với $email", $sotien, 0, $id);
                    }
                }
            }
        } else {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý thêm giao dịch.";
            echo json_encode($response);
        }
    }

    if ($chucnang == 'insertGiaoDichVayMuon') {
        $nhom_id = $_POST['Nhom_Id'];
        $sotien = $_POST['SoTien'];
        $ghichu = $_POST['GhiChu'];
        $emaildoitac = $_POST['EmailDoiTac'];
        $ngaythang = $_POST['NgayThang'];
        $quy_id = $_POST['Quy_Id'];
        $sotienquy = $_POST['SoTienQuy'];
        $email = $_POST['Email'];
        $giaodich_id = $_POST['GiaoDich_Id'];
        $ngaybatdau = $_POST['NgayBatDau'];
        
        $ngayluu = date("d-m-Y", strtotime($ngaythang));
        
        $giaodich = $db->insertGiaoDich($nhom_id, $sotien, $ghichu, $emaildoitac, $ngaythang, $quy_id, $email, $ngaybatdau, "", "");
        if ($giaodich != false) {
            $id = $db->giaodich_id;
            $response["success"] = 1;
            $response["error_msg"] = "Bạn vừa thêm một giao dịch.";
            $response["giaodich"]["GiaoDich_Id"] = $giaodich["GiaoDich_Id"];
            $response["giaodich"]["Nhom_Id"] = $giaodich["Nhom_Id"];
            $response["giaodich"]["SoTien"] = $giaodich["SoTien"];
            $response["giaodich"]["GhiChu"] = $giaodich["GhiChu"];
            $response["giaodich"]["EmailDoiTac"] = $giaodich["EmailDoiTac"];
            $response["giaodich"]["NgayThang"] = $giaodich["NgayThang"];
            $response["giaodich"]["Quy_Id"] = $giaodich["Quy_Id"];
            $response["giaodich"]["Email"] = $giaodich["Email"];
            if($giaodich_id == 0)
            {
                 $response["giaodich"]["SoTienQuy"] = ($sotienquy + $sotien);
            }
            else
            {
                if($giaodich_id == 1)
                {
                    $response["giaodich"]["SoTienQuy"] = ($sotienquy - $sotien);
                }
            }
            $response["giaodich"]["NgayTra"] = $giaodich["NgayTra"];
            $response["giaodich"]["TienLai"] = $giaodich["TienLai"];
            $response["giaodich"]["LoaiThuLai"] = $giaodich["LoaiThuLai"];
            echo json_encode($response);
            /*             * giaodich_id = 0 tức loai_id = 0 => cộng tiền => đi vay mà có */
            if ($giaodich_id == 0) {
                $dbquy->updateSoTienQuy($quy_id, $sotienquy + $sotien);
                if ($emaildoitac == "") {
                    $dbkehoach->insertKeHoachGiaoDich(1, "", $email, $ngaybatdau, "Trả khoản nợ vay ngày $ngayluu", $sotien, 1, $id);
                }
                if ($emaildoitac != "") {
                    $dbkehoach->insertKeHoachGiaoDich(1, $emaildoitac, $email, $ngaybatdau, "Trả khoản nợ vay $emaildoitac ngày $ngayluu", $sotien, 1, $id);

                    $nhom = $dbnhom->selectIdNhom_VayMuon(1, $emaildoitac, "Cho vay");
                    $dbkehoach->insertKeHoachGiaoDichVayMuon($nhom["Nhom_Id"], 1, $email, $emaildoitac, $ngaythang, "Cho $email vay tiền", $sotien, $id);

                    $dbkehoach->insertKeHoachGiaoDich(0, $email, $emaildoitac, $ngaybatdau, "Thu khoản tiền cho $email vay ngày $ngayluu", $sotien, 1, $id);
                }
            } else {
                /*                 * giao dịch_id = 1 tức loai_id = 1 => trừ tiền => cho người ta vay */
                if ($giaodich_id == 1) {
                    $dbquy->updateSoTienQuy($quy_id, $sotienquy - $sotien);
                    if ($emaildoitac == "") {
                        $dbkehoach->insertKeHoachGiaoDich(0, "", $email, $ngaybatdau, "Thu khoản tiền cho vay ngày $ngayluu", $sotien, 1, $id);
                    }
                    if ($emaildoitac != "") {
                        $dbkehoach->insertKeHoachGiaoDich(0, $emaildoitac, $email, $ngaybatdau, "Thu khoản tiền cho $emaildoitac vay ngày $ngayluu", $sotien, 1, $id);

                        $nhom = $dbnhom->selectIdNhom_VayMuon(0, $emaildoitac, "Nợ");
                        $dbkehoach->insertKeHoachGiaoDichVayMuon($nhom["Nhom_Id"], 0, $email, $emaildoitac, $ngaythang, "Vay tiền của $email", $sotien, $id);

                        $dbkehoach->insertKeHoachGiaoDich(1, $email, $emaildoitac, $ngaybatdau, "Trả khoản nợ vay $email ngày $ngayluu", $sotien, 1, $id);
                    }
                }
            }
        } else {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý thêm giao dịch.";
            echo json_encode($response);
        }
    }

    if ($chucnang == 'insertGiaoDichVayMuonCoLai') {
        $nhom_id = $_POST['Nhom_Id'];
        $sotien = $_POST['SoTien'];
        $ghichu = $_POST['GhiChu'];
        $emaildoitac = $_POST['EmailDoiTac'];
        $ngaythang = $_POST['NgayThang'];
        $quy_id = $_POST['Quy_Id'];
        $sotienquy = $_POST['SoTienQuy'];
        $email = $_POST['Email'];
        $giaodich_id = $_POST['GiaoDich_Id'];
        $ngaybatdau = $_POST['NgayBatDau'];
        $sotienlai = $_POST['SoTienLai'];
        $loaithulai = $_POST['LoaiThuLai'];

        $ngayluu = date("d-m-Y", strtotime($ngaythang));
        
        $giaodich = $db->insertGiaoDich($nhom_id, $sotien, $ghichu, $emaildoitac, $ngaythang, $quy_id, $email, $ngaybatdau, $sotienlai, $loaithulai);
        if ($giaodich != false) {
            $id = $db->giaodich_id;
            $response["success"] = 1;
            $response["error_msg"] = "Bạn vừa thêm một giao dịch.";
            $response["giaodich"]["GiaoDich_Id"] = $giaodich["GiaoDich_Id"];
            $response["giaodich"]["Nhom_Id"] = $giaodich["Nhom_Id"];
            $response["giaodich"]["SoTien"] = $giaodich["SoTien"];
            $response["giaodich"]["GhiChu"] = $giaodich["GhiChu"];
            $response["giaodich"]["EmailDoiTac"] = $giaodich["EmailDoiTac"];
            $response["giaodich"]["NgayThang"] = $giaodich["NgayThang"];
            $response["giaodich"]["Quy_Id"] = $giaodich["Quy_Id"];
            $response["giaodich"]["Email"] = $giaodich["Email"];
            if($giaodich_id == 0)
            {
                 $response["giaodich"]["SoTienQuy"] = ($sotienquy + $sotien);
            }
            else
            {
                if($giaodich_id == 1)
                {
                    $response["giaodich"]["SoTienQuy"] = ($sotienquy - $sotien);
                }
            }
            $response["giaodich"]["NgayTra"] = $giaodich["NgayTra"];
            $response["giaodich"]["TienLai"] = $giaodich["TienLai"];
            $response["giaodich"]["LoaiThuLai"] = $giaodich["LoaiThuLai"];
            echo json_encode($response);
            /*             * giaodich_id = 0 tức loai_id = 0 => cộng tiền => đi vay mà có */
            if ($giaodich_id == 0) {
                $dbquy->updateSoTienQuy($quy_id, $sotienquy + $sotien);
                if ($emaildoitac == "") {
                    $dbkehoach->insertKeHoachGiaoDich(1, "", $email, $ngaybatdau, "Trả khoản nợ vay ngày $ngayluu", $sotien, 1, $id);
                    /** ===================================================================== */
                    $ngayvay = strtotime($ngaythang);
                    $ngaytra = strtotime($ngaybatdau);
                    $hieu = abs($ngayvay - $ngaytra);
                    $songay = floor($hieu / (60 * 60 * 24));
                    if ($loaithulai == 0) {
                        for ($i = 1; $i <= (($songay - $songay % 7) / 7); $i++) {
                            $week = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$i week");
                            $week = strftime("%Y-%m-%d", $week);
                            $dbkehoach->insertKeHoachGiaoDich(1, "", $email, $week, "Trả lãi lần $i khoản nợ vay ngày $ngayluu", $sotienlai, 1, $id);
                        }
                    }
                    if ($loaithulai == 1) {
                        for ($i = 1; $i <= (($songay - $songay % 30) / 30); $i++) {
                            $month = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$i month");
                            $month = strftime("%Y-%m-%d", $month);
                            $dbkehoach->insertKeHoachGiaoDich(1, "", $email, $month, "Trả lãi lần $i khoản nợ vay ngày $ngayluu", $sotienlai, 1, $id);
                        }
                    }
                    if ($loaithulai == 2) {
                        for ($i = 1; $i <= (($songay - $songay % 90) / 90); $i++) {
                            $m = $i * 3;
                            $month3 = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$m month");
                            $month3 = strftime("%Y-%m-%d", $month3);
                            $dbkehoach->insertKeHoachGiaoDich(1, "", $email, $month3, "Trả lãi lần $i khoản nợ vay ngày $ngayluu", $sotienlai, 1, $id);
                        }
                    }
                    if ($loaithulai == 3) {
                        for ($i = 1; $i <= (($songay - $songay % 180) / 180); $i++) {
                            $m = $i * 6;
                            $month6 = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$m month");
                            $month6 = strftime("%Y-%m-%d", $month6);
                            $dbkehoach->insertKeHoachGiaoDich(1, "", $email, $month6, "Trả lãi lần $i khoản nợ vay ngày $ngayluu", $sotienlai, 1, $id);
                        }
                    }
                    if ($loaithulai == 4) {
                        for ($i = 1; $i <= (($songay - $songay % 365) / 365); $i++) {
                            $year = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$i year");
                            $year = strftime("%Y-%m-%d", $year);
                            $dbkehoach->insertKeHoachGiaoDich(1, "", $email, $year, "Trả lãi lần $i khoản nợ vay ngày $ngayluu", $sotienlai, 1, $id);
                        }
                    }
                    /** ===================================================================== */
                }
                if ($emaildoitac != "") {
                    $dbkehoach->insertKeHoachGiaoDich(1, $emaildoitac, $email, $ngaybatdau, "Trả khoản nợ vay $emaildoitac ngày $ngayluu", $sotien, 1, $id);
                    /** ===================================================================== */
                    $ngayvay = strtotime($ngaythang);
                    $ngaytra = strtotime($ngaybatdau);
                    $hieu = abs($ngayvay - $ngaytra);
                    $songay = floor($hieu / (60 * 60 * 24));
                    if ($loaithulai == 0) {
                        for ($i = 1; $i <= (($songay - $songay % 7) / 7); $i++) {
                            $week = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$i week");
                            $week = strftime("%Y-%m-%d", $week);
                            $dbkehoach->insertKeHoachGiaoDich(1, $emaildoitac, $email, $week, "Trả lãi lần $i khoản nợ vay $emaildoitac ngày $ngayluu", $sotienlai, 1, $id);
                        }
                    }
                    if ($loaithulai == 1) {
                        for ($i = 1; $i <= (($songay - $songay % 30) / 30); $i++) {
                            $month = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$i month");
                            $month = strftime("%Y-%m-%d", $month);
                            $dbkehoach->insertKeHoachGiaoDich(1, $emaildoitac, $email, $month, "Trả lãi lần $i khoản nợ vay $emaildoitac ngày $ngayluu", $sotienlai, 1, $id);
                        }
                    }
                    if ($loaithulai == 2) {
                        for ($i = 1; $i <= (($songay - $songay % 90) / 90); $i++) {
                            $m = $i * 3;
                            $month3 = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$m month");
                            $month3 = strftime("%Y-%m-%d", $month3);
                            $dbkehoach->insertKeHoachGiaoDich(1, $emaildoitac, $email, $month3, "Trả lãi lần $i khoản nợ vay $emaildoitac ngày $ngayluu", $sotienlai, 1, $id);
                        }
                    }
                    if ($loaithulai == 3) {
                        for ($i = 1; $i <= (($songay - $songay % 180) / 180); $i++) {
                            $m = $i * 6;
                            $month6 = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$m month");
                            $month6 = strftime("%Y-%m-%d", $month6);
                            $dbkehoach->insertKeHoachGiaoDich(1, $emaildoitac, $email, $month6, "Trả lãi lần $i khoản nợ vay $emaildoitac ngày $ngayluu", $sotienlai, 1, $id);
                        }
                    }
                    if ($loaithulai == 4) {
                        for ($i = 1; $i <= (($songay - $songay % 365) / 365); $i++) {
                            $year = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$i year");
                            $year = strftime("%Y-%m-%d", $year);
                            $dbkehoach->insertKeHoachGiaoDich(1, $emaildoitac, $email, $year, "Trả lãi lần $i khoản nợ vay $emaildoitac ngày $ngayluu", $sotienlai, 1, $id);
                        }
                    }
                    /** ===================================================================== */
                    $nhom = $dbnhom->selectIdNhom_VayMuon(1, $emaildoitac, "Cho vay");
                    $dbkehoach->insertKeHoachGiaoDichVayMuon($nhom["Nhom_Id"], 1, $email, $emaildoitac, $ngaythang, "Cho $email vay tiền", $sotien, $id);

                    $dbkehoach->insertKeHoachGiaoDich(0, $email, $emaildoitac, $ngaybatdau, "Thu khoản tiền cho $email vay ngày $ngayluu", $sotien, 1, $id);
                    /** ===================================================================== */
                    $ngayvay = strtotime($ngaythang);
                    $ngaytra = strtotime($ngaybatdau);
                    $hieu = abs($ngayvay - $ngaytra);
                    $songay = floor($hieu / (60 * 60 * 24));
                    if ($loaithulai == 0) {
                        for ($i = 1; $i <= (($songay - $songay % 7) / 7); $i++) {
                            $week = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$i week");
                            $week = strftime("%Y-%m-%d", $week);
                            $dbkehoach->insertKeHoachGiaoDich(0, $email, $emaildoitac, $week, "Thu lãi lần $i khoản nợ cho $email vay ngày $ngayluu", $sotienlai, 1, $id);
                        }
                    }
                    if ($loaithulai == 1) {
                        for ($i = 1; $i <= (($songay - $songay % 30) / 30); $i++) {
                            $month = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$i month");
                            $month = strftime("%Y-%m-%d", $month);
                            $dbkehoach->insertKeHoachGiaoDich(0, $email, $emaildoitac, $month, "Thu lãi lần $i khoản nợ cho $email vay ngày $ngayluu", $sotienlai, 1, $id);
                        }
                    }
                    if ($loaithulai == 2) {
                        for ($i = 1; $i <= (($songay - $songay % 90) / 90); $i++) {
                            $m = $i * 3;
                            $month3 = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$m month");
                            $month3 = strftime("%Y-%m-%d", $month3);
                            $dbkehoach->insertKeHoachGiaoDich(0, $email, $emaildoitac, $month3, "Thu lãi lần $i khoản nợ cho $email vay ngày $ngayluu", $sotienlai, 1, $id);
                        }
                    }
                    if ($loaithulai == 3) {
                        for ($i = 1; $i <= (($songay - $songay % 180) / 180); $i++) {
                            $m = $i * 6;
                            $month6 = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$m month");
                            $month6 = strftime("%Y-%m-%d", $month6);
                            $dbkehoach->insertKeHoachGiaoDich(0, $email, $emaildoitac, $month6, "Thu lãi lần $i khoản nợ cho $email vay ngày $ngayluu", $sotienlai, 1, $id);
                        }
                    }
                    if ($loaithulai == 4) {
                        for ($i = 1; $i <= (($songay - $songay % 365) / 365); $i++) {
                            $year = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$i year");
                            $year = strftime("%Y-%m-%d", $year);
                            $dbkehoach->insertKeHoachGiaoDich(0, $email, $emaildoitac, $year, "Thu lãi lần $i khoản nợ cho $email vay ngày $ngayluu", $sotienlai, 1, $id);
                        }
                    }
                    /** ===================================================================== */
                }
            } else {
                /*                 * giao dịch_id = 1 tức loai_id = 1 => trừ tiền => cho người ta vay */
                if ($giaodich_id == 1) {
                    $dbquy->updateSoTienQuy($quy_id, $sotienquy - $sotien);
                    if ($emaildoitac == "") {
                        $dbkehoach->insertKeHoachGiaoDich(0, "", $email, $ngaybatdau, "Thu khoản tiền cho vay ngày $ngayluu", $sotien, 1, $id);
                        /** ===================================================================== */
                        $ngayvay = strtotime($ngaythang);
                        $ngaytra = strtotime($ngaybatdau);
                        $hieu = abs($ngayvay - $ngaytra);
                        $songay = floor($hieu / (60 * 60 * 24));
                        if ($loaithulai == 0) {
                            for ($i = 1; $i <= (($songay - $songay % 7) / 7); $i++) {
                                $week = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$i week");
                                $week = strftime("%Y-%m-%d", $week);
                                $dbkehoach->insertKeHoachGiaoDich(0, "", $email, $week, "Thu lãi lần $i khoản nợ cho vay ngày $ngayluu", $sotienlai, 1, $id);
                            }
                        }
                        if ($loaithulai == 1) {
                            for ($i = 1; $i <= (($songay - $songay % 30) / 30); $i++) {
                                $month = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$i month");
                                $month = strftime("%Y-%m-%d", $month);
                                $dbkehoach->insertKeHoachGiaoDich(0, "", $email, $month, "Thu lãi lần $i khoản nợ cho vay ngày $ngayluu", $sotienlai, 1, $id);
                            }
                        }
                        if ($loaithulai == 2) {
                            for ($i = 1; $i <= (($songay - $songay % 90) / 90); $i++) {
                                $m = $i * 3;
                                $month3 = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$m month");
                                $month3 = strftime("%Y-%m-%d", $month3);
                                $dbkehoach->insertKeHoachGiaoDich(0, "", $email, $month3, "Thu lãi lần $i khoản nợ cho vay ngày $ngayluu", $sotienlai, 1, $id);
                            }
                        }
                        if ($loaithulai == 3) {
                            for ($i = 1; $i <= (($songay - $songay % 180) / 180); $i++) {
                                $m = $i * 6;
                                $month6 = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$m month");
                                $month6 = strftime("%Y-%m-%d", $month6);
                                $dbkehoach->insertKeHoachGiaoDich(0, "", $email, $month6, "Thu lãi lần $i khoản nợ cho vay ngày $ngayluu", $sotienlai, 1, $id);
                            }
                        }
                        if ($loaithulai == 4) {
                            for ($i = 1; $i <= (($songay - $songay % 365) / 365); $i++) {
                                $year = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$i year");
                                $year = strftime("%Y-%m-%d", $year);
                                $dbkehoach->insertKeHoachGiaoDich(0, "", $email, $year, "Thu lãi lần $i khoản nợ cho vay ngày $ngayluu", $sotienlai, 1, $id);
                            }
                        }
                        /** ===================================================================== */
                    }
                    if ($emaildoitac != "") {
                        $dbkehoach->insertKeHoachGiaoDich(0, $emaildoitac, $email, $ngaybatdau, "Thu khoản tiền cho $emaildoitac vay ngày $ngayluu", $sotien, 1, $id);
                        /** ===================================================================== */
                        $ngayvay = strtotime($ngaythang);
                        $ngaytra = strtotime($ngaybatdau);
                        $hieu = abs($ngayvay - $ngaytra);
                        $songay = floor($hieu / (60 * 60 * 24));
                        if ($loaithulai == 0) {
                            for ($i = 1; $i <= (($songay - $songay % 7) / 7); $i++) {
                                $week = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$i week");
                                $week = strftime("%Y-%m-%d", $week);
                                $dbkehoach->insertKeHoachGiaoDich(0, $emaildoitac, $email, $week, "Thu lãi lần $i khoản nợ cho $emaildoitac vay ngày $ngayluu", $sotienlai, 1, $id);
                            }
                        }
                        if ($loaithulai == 1) {
                            for ($i = 1; $i <= (($songay - $songay % 30) / 30); $i++) {
                                $month = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$i month");
                                $month = strftime("%Y-%m-%d", $month);
                                $dbkehoach->insertKeHoachGiaoDich(0, $emaildoitac, $email, $month, "Thu lãi lần $i khoản nợ cho $emaildoitac vay ngày $ngayluu", $sotienlai, 1, $id);
                            }
                        }
                        if ($loaithulai == 2) {
                            for ($i = 1; $i <= (($songay - $songay % 90) / 90); $i++) {
                                $m = $i * 3;
                                $month3 = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$m month");
                                $month3 = strftime("%Y-%m-%d", $month3);
                                $dbkehoach->insertKeHoachGiaoDich(0, $emaildoitac, $email, $month3, "Thu lãi lần $i khoản nợ cho $emaildoitac vay ngày $ngayluu", $sotienlai, 1, $id);
                            }
                        }
                        if ($loaithulai == 3) {
                            for ($i = 1; $i <= (($songay - $songay % 180) / 180); $i++) {
                                $m = $i * 6;
                                $month6 = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$m month");
                                $month6 = strftime("%Y-%m-%d", $month6);
                                $dbkehoach->insertKeHoachGiaoDich(0, $emaildoitac, $email, $month6, "Thu lãi lần $i khoản nợ cho $emaildoitac vay ngày $ngayluu", $sotienlai, 1, $id);
                            }
                        }
                        if ($loaithulai == 4) {
                            for ($i = 1; $i <= (($songay - $songay % 365) / 365); $i++) {
                                $year = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$i year");
                                $year = strftime("%Y-%m-%d", $year);
                                $dbkehoach->insertKeHoachGiaoDich(0, $emaildoitac, $email, $year, "Thu lãi lần $i khoản nợ cho $emaildoitac vay ngày $ngayluu", $sotienlai, 1, $id);
                            }
                        }
                        /** ===================================================================== */
                        $nhom = $dbnhom->selectIdNhom_VayMuon(0, $emaildoitac, "Nợ");
                        $dbkehoach->insertKeHoachGiaoDichVayMuon($nhom["Nhom_Id"], 0, $email, $emaildoitac, $ngaythang, "Vay tiền của $email", $sotien, $id);

                        $dbkehoach->insertKeHoachGiaoDich(1, $email, $emaildoitac, $ngaybatdau, "Trả khoản nợ vay $email ngày $ngayluu", $sotien, 1, $id);
                        /** ===================================================================== */
                        $ngayvay = strtotime($ngaythang);
                        $ngaytra = strtotime($ngaybatdau);
                        $hieu = abs($ngayvay - $ngaytra);
                        $songay = floor($hieu / (60 * 60 * 24));
                        if ($loaithulai == 0) {
                            for ($i = 1; $i <= (($songay - $songay % 7) / 7); $i++) {
                                $week = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$i week");
                                $week = strftime("%Y-%m-%d", $week);
                                $dbkehoach->insertKeHoachGiaoDich(1, $email, $emaildoitac, $week, "Trả lãi lần $i khoản nợ vay $email ngày $ngayluu", $sotienlai, 1, $id);
                            }
                        }
                        if ($loaithulai == 1) {
                            for ($i = 1; $i <= (($songay - $songay % 30) / 30); $i++) {
                                $month = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$i month");
                                $month = strftime("%Y-%m-%d", $month);
                                $dbkehoach->insertKeHoachGiaoDich(1, $email, $emaildoitac, $month, "Trả lãi lần $i khoản nợ vay $email ngày $ngayluu", $sotienlai, 1, $id);
                            }
                        }
                        if ($loaithulai == 2) {
                            for ($i = 1; $i <= (($songay - $songay % 90) / 90); $i++) {
                                $m = $i * 3;
                                $month3 = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$m month");
                                $month3 = strftime("%Y-%m-%d", $month3);
                                $dbkehoach->insertKeHoachGiaoDich(1, $email, $emaildoitac, $month3, "Trả lãi lần $i khoản nợ vay $email ngày $ngayluu", $sotienlai, 1, $id);
                            }
                        }
                        if ($loaithulai == 3) {
                            for ($i = 1; $i <= (($songay - $songay % 180) / 180); $i++) {
                                $m = $i * 6;
                                $month6 = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$m month");
                                $month6 = strftime("%Y-%m-%d", $month6);
                                $dbkehoach->insertKeHoachGiaoDich(1, $email, $emaildoitac, $month6, "Trả lãi lần $i khoản nợ vay $email ngày $ngayluu", $sotienlai, 1, $id);
                            }
                        }
                        if ($loaithulai == 4) {
                            for ($i = 1; $i <= (($songay - $songay % 365) / 365); $i++) {
                                $year = strtotime(date("Y-m-d", strtotime($ngaythang)) . " +$i year");
                                $year = strftime("%Y-%m-%d", $year);
                                $dbkehoach->insertKeHoachGiaoDich(1, $email, $emaildoitac, $year, "Trả lãi lần $i khoản nợ vay $email ngày $ngayluu", $sotienlai, 1, $id);
                            }
                        }
                        /** ===================================================================== */
                    }
                }
            }
        } else {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý thêm giao dịch.";
            echo json_encode($response);
        }
    }
    
    if($chucnang == "selectGiaoDich")
    {
        $thang = $_POST['NgayThang'];
        $email = $_POST['Email'];
        $nam = $_POST['Nam'];
        $giaodich = $db->selectGiaoDich($thang, $nam, $email);
        if($giaodich != false)
        {
            $response["success"] = 1;
            $response["error_msg"] = "Trên đây là dữ liệu giao dịch của bạn trong tháng.";
            $mang = array();
            while ($dong = mysql_fetch_array($giaodich)) {
                $tbl_giaodich = array();
                $tbl_giaodich["GiaoDich_Id"] = $dong["GiaoDich_Id"];
                $tbl_giaodich["Nhom_Id"] = $dong["Nhom_Id"];
                $tbl_giaodich["TenNhom"] = $dong["TenNhom"];
                $tbl_giaodich["Loai_Id"] = $dong["Loai_Id"];
                $tbl_giaodich["SoTien"] = $dong["SoTien"];
                $tbl_giaodich["GhiChu"] = $dong["GhiChu"];
                $tbl_giaodich["EmailDoiTac"] = $dong["EmailDoiTac"];
                $tbl_giaodich["NgayThang"] = $dong["NgayThang"];
                $tbl_giaodich["Quy_Id"] = $dong["Quy_Id"];
                $tbl_giaodich["SoTienQuy"] = $dong["SoTienQuy"];
                $tbl_giaodich["TenQuy"] = $dong["TenQuy"];
                $tbl_giaodich["Email"] = $dong["Email"];
                $tbl_giaodich["Anh"] = $dong["Anh"];
                $tbl_giaodich["NgayTra"] = $dong["NgayTra"];
                $tbl_giaodich["TienLai"] = $dong["TienLai"];
                $tbl_giaodich["LoaiThuLai"] = $dong["LoaiThuLai"];
                $mang[] = $tbl_giaodich;
            }
            $response["tbl_giaodich"] = $mang;
            echo json_encode($response);
        }else {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý lấy dữ liệu.";
            echo json_encode($response);
        }
    }
    
    if($chucnang == "deleteGiaoDich")
    {
        $giaodich_id = $_POST['GiaoDich_Id'];
        $email = $_POST['Email'];
        $sotien = $_POST['SoTien'];
        $loai_id = $_POST['Loai_Id'];
        $quy_id = $_POST['Quy_Id'];
        $sotienquy = $_POST['SoTienQuy'];
        $kehoach = $dbkehoach->deleteKeHoachVayMuon($giaodich_id);
        $giaodich = $db->deleteGiaoDich($giaodich_id, $email);
        if($kehoach != false && $giaodich != false)
        {
            if($loai_id == 0)
            {
                $dbquy->updateSoTienQuy($quy_id, $sotienquy - $sotien);
                $response["SoTienQuy"] = ($sotienquy - $sotien);
            }
            if($loai_id == 1)
            {
                $dbquy->updateSoTienQuy($quy_id, $sotienquy + $sotien);
                $response["SoTienQuy"] = ($sotienquy + $sotien);
            }
            $response["success"] = 1;
            $response["error_msg"] = "Xóa dữ liệu giao dịch thành công.";
            echo json_encode($response);
        }
        else
        {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý dữ liệu.";
            echo json_encode($response);
        }
    }
    
    if($chucnang == "selectGiaoDichBaoCaoNam")
    {
        $email = $_POST['Email'];
        $nam = $_POST['Nam'];
        $giaodich = $db->selectGiaoDichBaoCaoNam($nam, $email);
        if($giaodich != false)
        {
            $response["success"] = 1;
            $response["error_msg"] = "Trên đây là dữ liệu giao dịch của bạn trong tháng.";
            $mang = array();
            while ($dong = mysql_fetch_array($giaodich)) {
                $tbl_giaodich = array();
                $tbl_giaodich["GiaoDich_Id"] = $dong["GiaoDich_Id"];
                $tbl_giaodich["Nhom_Id"] = $dong["Nhom_Id"];
                $tbl_giaodich["TenNhom"] = $dong["TenNhom"];
                $tbl_giaodich["Loai_Id"] = $dong["Loai_Id"];
                $tbl_giaodich["SoTien"] = $dong["SoTien"];
                $tbl_giaodich["GhiChu"] = $dong["GhiChu"];
                $tbl_giaodich["EmailDoiTac"] = $dong["EmailDoiTac"];
                $tbl_giaodich["NgayThang"] = $dong["NgayThang"];
                $tbl_giaodich["Quy_Id"] = $dong["Quy_Id"];
                $tbl_giaodich["SoTienQuy"] = $dong["SoTienQuy"];
                $tbl_giaodich["TenQuy"] = $dong["TenQuy"];
                $tbl_giaodich["Email"] = $dong["Email"];
                $tbl_giaodich["Anh"] = $dong["Anh"];
                $tbl_giaodich["NgayTra"] = $dong["NgayTra"];
                $tbl_giaodich["TienLai"] = $dong["TienLai"];
                $tbl_giaodich["LoaiThuLai"] = $dong["LoaiThuLai"];
                $mang[] = $tbl_giaodich;
            }
            $response["tbl_giaodich"] = $mang;
            echo json_encode($response);
        }else {
            $response["error"] = 1;
            $response["error_msg"] = "Lỗi xảy ra khi xử lý lấy dữ liệu.";
            echo json_encode($response);
        }
    }
} else {
    echo "Không có chức năng nào được thực hiện.";
}

