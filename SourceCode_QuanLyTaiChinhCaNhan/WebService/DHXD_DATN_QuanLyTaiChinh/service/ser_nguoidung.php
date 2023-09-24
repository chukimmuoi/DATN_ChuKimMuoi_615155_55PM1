<?php

class ser_nguoidung {

    private $db;

    function __construct() {
        require_once '../utils/ketnoi_csdl.php';
        $this->db = new KetNoi_CSDL();
    }

    function __destruct() {
        
    }

    public function MaHoa($matkhau) {
        $mabam = sha1(rand());
        $mabam = substr($mabam, 0, 10);
        $matkhaumahoa = base64_encode(sha1($matkhau . $mabam, true) . $mabam);
        $hash = array("mabam" => $mabam, "matkhaumahoa" => $matkhaumahoa);
        return $hash;
    }

    public function GiaiMa($mabam, $matkhau) {
        $hash = base64_encode(sha1($matkhau . $mabam, true) . $mabam);
        return $hash;
    }

    public function insertNguoiDung($hoten, $email, $matkhau) {
        try {
            $this->db->open();
            $unique_id = uniqid('', true);
            $hash = $this->MaHoa($matkhau);
            $MatKhauMaHoa = $hash["matkhaumahoa"];
            $MaBam = $hash["mabam"];
            $result = mysql_query("INSERT INTO tbl_nguoidung(Unique_Id, HoTen, Email, MatKhau, MaBam, NgayTao) VALUES"
                    . "('$unique_id','$hoten','$email','$MatKhauMaHoa','$MaBam',NOW())");
            if ($result) {
                $result = mysql_query("SELECT * FROM tbl_nguoidung WHERE Email = '$email'");
                return mysql_fetch_array($result);
            } else {
                return false;
            }
        } catch (Exception $exc) {
            echo $exc->getTraceAsString();
        } finally {
            $this->db->close();
        }
    }

    public function updateMatKhau($email, $matkhaumoi) {
        try {
            $this->db->open();
            $hash = $this->MaHoa($matkhaumoi);
            $MatKhauMaHoa = $hash["matkhaumahoa"];
            $MaBam = $hash["mabam"];
            $result = mysql_query("UPDATE tbl_nguoidung SET MatKhau = '$MatKhauMaHoa', MaBam = '$MaBam'"
                    . "WHERE Email = '$email'");
            if ($result) {
                $result = mysql_query("SELECT * FROM tbl_nguoidung WHERE Email = '$email'");
                return mysql_fetch_array($result);
            } else {
                return false;
            }
        } catch (Exception $exc) {
            echo $exc->getTraceAsString();
        } finally {
            $this->db->close();
        }
    }

    public function selectNguoiDung($email, $matkhau) {
        try {
            $this->db->open();
            $result = mysql_query("SELECT * FROM tbl_nguoidung WHERE Email = '$email'") or die(mysql_error());
            $dong = mysql_num_rows($result);
            if ($dong > 0) {
                $result = mysql_fetch_array($result);
                $mabam = $result['MaBam'];
                $matkhaumahoa = $result['MatKhau'];
                $hash = $this->giaima($mabam, $matkhau);
                if ($matkhaumahoa == $hash) {
                    return $result;
                } else {
                    return false;
                }
            }
        } catch (Exception $exc) {
            echo $exc->getTraceAsString();
        } finally {
            $this->db->close();
        }
    }

    public function testTonTai($email) {
        try {
            $this->db->open();
            $result = mysql_query("SELECT Email FROM tbl_nguoidung WHERE Email = '$email'");
            $no_of_rows = mysql_num_rows($result);
            if ($no_of_rows > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception $exc) {
            echo $exc->getTraceAsString();
        } finally {
            $this->db->close();
        }
    }

    public function random_string() {
        $MangKyTu = array();
        $MangKyTu[] = array('count' => 7, 'kytu' => 'abcdefghijklmnopqrstuvwxyz');
        $MangKyTu[] = array('count' => 1, 'kytu' => '0123456789');
        $mang = array();
        foreach ($MangKyTu as $KyTu_set) {
            for ($i = 0; $i < $KyTu_set['count']; $i++) {
                $mang[] = $KyTu_set['kytu'][rand(0, strlen($KyTu_set['kytu']) - 1)];
            }
        }
        shuffle($mang);
        return implode("", $mang);
    }

    public function QuenMatKhau($email, $matkhau, $mabam) {
        try {
            $this->db->open();
            $result = mysql_query("UPDATE tbl_nguoidung SET Matkhau = '$matkhau',MaBam = '$mabam' 
						  WHERE Email = '$email'");

            if ($result) {
                return true;
            } else {
                return false;
            }
        } catch (Exception $exc) {
            echo $exc->getTraceAsString();
        } finally {
            $this->db->close();
        }
    }

    public function testEmail($email) {
        $isValid = true;
        $atIndex = strrpos($email, "@");
        if (is_bool($atIndex) && !$atIndex) {
            $isValid = false;
        } else {
            $domain = substr($email, $atIndex + 1);
            $local = substr($email, 0, $atIndex);
            $localLen = strlen($local);
            $domainLen = strlen($domain);
            if ($localLen < 1 || $localLen > 64) {
                // local part length exceeded
                $isValid = false;
            } else if ($domainLen < 1 || $domainLen > 255) {
                // domain part length exceeded
                $isValid = false;
            } else if ($local[0] == '.' || $local[$localLen - 1] == '.') {
                // local part starts or ends with '.'
                $isValid = false;
            } else if (preg_match('/\\.\\./', $local)) {
                // local part has two consecutive dots
                $isValid = false;
            } else if (!preg_match('/^[A-Za-z0-9\\-\\.]+$/', $domain)) {
                // character not valid in domain part
                $isValid = false;
            } else if (preg_match('/\\.\\./', $domain)) {
                // domain part has two consecutive dots
                $isValid = false;
            } else if
            (!preg_match('/^(\\\\.|[A-Za-z0-9!#%&`_=\\/$\'*+?^{}|~.-])+$/', str_replace("\\\\", "", $local))) {
                // character not valid in local part unless 
                // local part is quoted
                if (!preg_match('/^"(\\\\"|[^"])+"$/', str_replace("\\\\", "", $local))) {
                    $isValid = false;
                }
            }
            if ($isValid && !(checkdnsrr($domain, "MX") || checkdnsrr($domain, "A"))) {
                // domain not found in DNS
                $isValid = false;
            }
        }
        return $isValid;
    }

    public function KhoiTaoDuLieuNhom($email)
    {
        try {
            $this->db->open();
            $result = mysql_query("INSERT INTO tbl_nhom(TenNhom, Loai_Id, Anh, Email) VALUES "
                    . "('Bán đồ', '0', '120', '$email'), "
                    . "('Lương', '0', '74', '$email'), "
                    . "('Nợ', '0', '111', '$email'), "
                    . "('Thưởng', '0', '110', '$email'), "
                    . "('Tiền lãi', '0', '117', '$email'), "
                    . "('Được tặng', '0', '116', '$email'), "
                    . "('Khác', '0', '22', '$email'), "
                    . "('Bạn bè và tình yêu', '1', '0', '$email'), "
                    . "('Cho vay', '1', '119', '$email'), "
                    . "('Du lịch', '1', '123', '$email'), "
                    . "('Di chuyển', '1', '124', '$email'), "
                    . "('Gia đình', '1', '7', '$email'), "
                    . "('Giáo dục', '1', '112', '$email'), "
                    . "('Giải trí', '1', '125', '$email'), "
                    . "('Mua sắm', '1', '2', '$email'), "
                    . "('Sức khỏe', '1', '126', '$email'), "
                    . "('Ăn uống', '1', '115', '$email'), "
                    . "('Đầu tư', '1', '118', '$email'), "
                    . "('Khác', '1', '21', '$email'), "
                    . "('Chuyển khoản', '1', '127', '$email')");
            if ($result) {
                return true;
            } else {
                return false;
            }
        } catch (Exception $exc) {
            echo $exc->getTraceAsString();
        } finally {
            $this->db->close();
        }
    }
    
    public function KhoiTaoDuLieuQuy($email)
    {
        try {
            $this->db->open();
            $result = mysql_query("INSERT INTO tbl_quy(TenQuy, Anh, SoTien, Email) VALUES "
                    . "('Tiêu dùng', '108', '0', '$email'), "
                    . "('Tự do tài chính', '108', '0', '$email'), "
                    . "('Tiết kiệm', '108', '0', '$email'), "
                    . "('Học tập', '108', '0', '$email'), "
                    . "('Làm từ thiện', '108', '0', '$email'), "
                    . "('Hưởng thụ', '108', '0', '$email')");
            if ($result) {
                return true;
            } else {
                return false;
            }
        } catch (Exception $exc) {
            echo $exc->getTraceAsString();
        } finally {
            $this->db->close();
        }
    }
    
}
