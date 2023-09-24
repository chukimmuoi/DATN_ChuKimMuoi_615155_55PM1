<?php

class ser_giaodich {

    private $db;
    public $giaodich_id;

    function __construct() {
        require_once '../utils/ketnoi_csdl.php';
        $this->db = new KetNoi_CSDL();
    }

    function __destruct() {
        
    }

    public function insertGiaoDich_ChuyenTien_($nhom_id, $nhom_id_phu, $sotien, $ghichu, $ghichu_phu, $ngaythang, $quy_id, $quy_id_phu, $email) {
        try {
            $this->db->open();
            $result = mysql_query("INSERT INTO tbl_giaodich(Nhom_Id, SoTien, GhiChu, NgayThang, Quy_Id, Email) VALUES"
                    . "('$nhom_id','$sotien','Chuyển tiền cho $ghichu', '$ngaythang', '$quy_id', '$email'), "
                    . "('$nhom_id_phu','$sotien','Được tặng từ $ghichu_phu', '$ngaythang', '$quy_id_phu', '$email')");
            if ($result) {
//                $giaodich_id = mysql_insert_id();
//                $result = mysql_query("SELECT * FROM tbl_giaodich WHERE GiaoDich_Id = '$giaodich_id'");
//                return mysql_fetch_array($result);
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

    public function insertGiaoDich($nhom_id, $sotien, $ghichu, $emaildoitac, $ngaythang, $quy_id, $email, $ngaytra, $tienlai, $loaithulai) {
        try {
            $this->db->open();
            $result = mysql_query("INSERT INTO tbl_giaodich(Nhom_Id, SoTien, GhiChu, EmailDoiTac, NgayThang, Quy_Id, Email, NgayTra, TienLai, LoaiThuLai) VALUES"
                    . "('$nhom_id', '$sotien', '$ghichu', '$emaildoitac', '$ngaythang', '$quy_id', '$email', '$ngaytra', '$tienlai', '$loaithulai')");
            if ($result) {
                $this->giaodich_id = mysql_insert_id();
                $result = mysql_query("SELECT * FROM tbl_giaodich WHERE GiaoDich_Id = '$this->giaodich_id'");
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

    public function selectGiaoDich($thang, $nam, $email) {
        try {
            $this->db->open();
            $result = mysql_query("SELECT A.GiaoDich_Id, A.Nhom_Id, B.TenNhom, B.Loai_Id, B.Anh, A.SoTien, A.GhiChu, A.EmailDoiTac, A.NgayThang, A.Quy_Id, C.TenQuy, C.SoTien SoTienQuy, A.Email, A.NgayTra, A.TienLai, A.LoaiThuLai FROM tbl_giaodich A, tbl_nhom B, tbl_quy C WHERE A.Nhom_Id = B.Nhom_Id AND A.Quy_Id = C.Quy_id AND MONTH(A.NgayThang) = '$thang' AND YEAR(A.NgayThang) = '$nam' AND A.Email = '$email' ORDER BY A.NgayThang DESC;");
            if ($result) {
                return $result;
            } else {
                return false;
            }
        } catch (Exception $exc) {
            echo $exc->getTraceAsString();
        } finally {
            $this->db->close();
        }
    }

    public function deleteGiaoDich($giaodich_id, $email){
        try {
            $this->db->open();
            $result = mysql_query("DELETE FROM tbl_giaodich WHERE GiaoDich_Id = '$giaodich_id' AND Email = '$email';");
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
    
    public function selectGiaoDichBaoCaoNam($nam, $email) {
        try {
            $this->db->open();
            $result = mysql_query("SELECT A.GiaoDich_Id, A.Nhom_Id, B.TenNhom, B.Loai_Id, B.Anh, A.SoTien, A.GhiChu, A.EmailDoiTac, A.NgayThang, A.Quy_Id, C.TenQuy, C.SoTien SoTienQuy, A.Email, A.NgayTra, A.TienLai, A.LoaiThuLai FROM tbl_giaodich A, tbl_nhom B, tbl_quy C WHERE A.Nhom_Id = B.Nhom_Id AND A.Quy_Id = C.Quy_id AND YEAR(A.NgayThang) = '$nam' AND A.Email = '$email' ORDER BY A.NgayThang DESC;");
            if ($result) {
                return $result;
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
