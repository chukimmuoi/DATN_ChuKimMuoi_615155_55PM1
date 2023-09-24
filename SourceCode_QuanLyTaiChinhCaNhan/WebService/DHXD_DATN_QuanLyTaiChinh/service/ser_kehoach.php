<?php

class ser_kehoach {

    private $db;

    function __construct() {
        require_once '../utils/ketnoi_csdl.php';
        $this->db = new KetNoi_CSDL();
    }

    function __destruct() {
        
    }

    public function insertKeHoachGiaoDich($loai_id, $emaildoitac, $email, $ngaybatdau, $dienta, $sotien, $loaikehoach, $giaodich_id){
         try {
            $this->db->open();
            $result = mysql_query("INSERT INTO tbl_kehoach(Loai_Id, EmailDoiTac, Email, NgayBatDau, DienTa, SoTien, TrangThai, LoaiKeHoach, GiaoDich_Id) VALUES"
                    . "('$loai_id', '$emaildoitac', '$email', '$ngaybatdau', '$dienta', '$sotien', '0', '$loaikehoach', '$giaodich_id')");
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
    
    public function  insertKeHoachGiaoDichVayMuon($nhom_id, $loai_id, $emaildoitac, $email, $ngaybatdau, $dienta, $sotien, $giaodich_id){
         try {
            $this->db->open();
            $result = mysql_query("INSERT INTO tbl_kehoach(Nhom_Id, Loai_Id, EmailDoiTac, Email, NgayBatDau, DienTa, SoTien, TrangThai, LoaiKeHoach, GiaoDich_Id) VALUES"
                    . "('$nhom_id', '$loai_id', '$emaildoitac', '$email', '$ngaybatdau', '$dienta', '$sotien', '0', '0', '$giaodich_id')");
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
    
    public function deleteKeHoachVayMuon($giaodich_id){
        try {
            $this->db->open();
            $result = mysql_query("DELETE FROM tbl_kehoach WHERE GiaoDich_Id = '$giaodich_id';");
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
    
    public function insertKeHoachCongViec($nhom_id, $loai_id, $vi_id, $email, $ngaybatdau, $dienta, $sotien)
    {
        try {
            $this->db->open();
            $result = mysql_query("INSERT INTO tbl_kehoach(Nhom_Id, Loai_Id, Vi_Id, Email, NgayBatDau, DienTa, SoTien, TrangThai, LoaiKeHoach) VALUES"
                    . "('$nhom_id', '$loai_id', '$vi_id', '$email', '$ngaybatdau', '$dienta', '$sotien', '0', '2')");
            if ($result) {
                $kehoach_id = mysql_insert_id();
                $result = mysql_query("SELECT * FROM tbl_kehoach WHERE KeHoach_Id = '$kehoach_id'");
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
    
    public function insertKeHoachNganSach($nhom_id, $loai_id, $email, $ngaybatdau, $ngayketthuc, $dienta, $sotien)
    {
        try {
            $this->db->open();
            $result = mysql_query("INSERT INTO tbl_kehoach(Nhom_Id, Loai_Id, Email, NgayBatDau, NgayKetThuc, DienTa, SoTien, TrangThai, LoaiKeHoach) VALUES"
                    . "('$nhom_id', '$loai_id', '$email', '$ngaybatdau', '$ngayketthuc', '$dienta', '$sotien', '0', '3')");
            if ($result) {
                $kehoach_id = mysql_insert_id();
                $result = mysql_query("SELECT * FROM tbl_kehoach WHERE KeHoach_Id = '$kehoach_id'");
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
    
    public function selectKeHoachCongViec($email)
    {
        try {
            $this->db->open();
            $result = mysql_query("SELECT A.KeHoach_Id, A.Nhom_Id, B.TenNhom, B.Loai_Id, B.Anh, A.SoTien, A.DienTa, A.NgayBatDau, A.Vi_Id, C.TenQuy, C.SoTien SoTienQuy, C.Anh AnhQuy, A.Email FROM tbl_kehoach A, tbl_nhom B, tbl_quy C WHERE A.Nhom_Id = B.Nhom_Id AND A.Vi_Id = C.Quy_id AND A.Email = '$email' AND A.LoaiKeHoach = '2' ORDER BY A.NgayBatDau DESC");
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
    
    public function updateKeHoachCongViec($kehoach_id, $nhom_id, $loai_id, $vi_id, $email, $ngaybatdau, $dienta, $sotien)
    {
        try {
            $this->db->open();
            $result = mysql_query("UPDATE tbl_kehoach SET Nhom_Id = '$nhom_id', Loai_Id = '$loai_id', Vi_Id = '$vi_id', NgayBatDau = '$ngaybatdau', DienTa = '$dienta', SoTien = '$sotien' WHERE KeHoach_Id = '$kehoach_id' AND Email = '$email'");
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
    
    public function  deleteKeHoach($kehoach_id, $email)
    {
        try {
            $this->db->open();
            $result = mysql_query("DELETE FROM tbl_kehoach WHERE KeHoach_Id = '$kehoach_id' AND Email = '$email'");
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
    
    public function selectKeHoachNganSach($email, $ngayhientai)
    {
        try {
            $this->db->open();
            $result = mysql_query("SELECT A.KeHoach_Id, A.Nhom_Id, A.Loai_Id, C.TenNhom, C.Anh, A.Email, A.NgayBatDau, A.NgayKetThuc, A.DienTa, A.SoTien SoTienNganSach, B.GiaoDich_Id, B.SoTien, B.GhiChu, B.EmailDoiTac, B.NgayThang, B.Quy_Id, B.NgayTra, B.TienLai, B.LoaiThuLai, D.TenQuy, D.SoTien SoTienQuy, D.Anh AnhQuy FROM tbl_kehoach A, tbl_giaodich B, tbl_nhom C, tbl_quy D WHERE A.Nhom_Id = B.Nhom_Id AND A.Nhom_Id = C.Nhom_Id AND B.Quy_Id = D.Quy_Id AND A.LoaiKeHoach ='3' AND A.Email = '$email' AND A.NgayBatDau <= '$ngayhientai' AND A.NgayKetThuc >= '$ngayhientai' AND A.NgayBatDau <= B.NgayThang AND A.NgayKetThuc >= B.NgayThang ORDER BY A.KeHoach_Id ASC, B.NgayThang DESC;");
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
    
    public function selectKeHoachNganSach1($email, $ngayhientai)
    {
        try {
            $this->db->open();
            $result = mysql_query("SELECT A.KeHoach_Id, A.Nhom_Id, A.Loai_Id, C.TenNhom, C.Anh, A.Email, A.NgayBatDau, A.NgayKetThuc, A.DienTa, A.SoTien SoTienNganSach, B.GiaoDich_Id, B.SoTien, B.GhiChu, B.EmailDoiTac, B.NgayThang, B.Quy_Id, B.NgayTra, B.TienLai, B.LoaiThuLai, D.TenQuy, D.SoTien SoTienQuy, D.Anh AnhQuy FROM tbl_kehoach A, tbl_giaodich B, tbl_nhom C, tbl_quy D WHERE A.Nhom_Id = B.Nhom_Id AND A.Nhom_Id = C.Nhom_Id AND B.Quy_Id = D.Quy_Id AND A.LoaiKeHoach ='3' AND A.Email = '$email' AND A.NgayKetThuc < '$ngayhientai' AND A.NgayBatDau <= B.NgayThang AND A.NgayKetThuc >= B.NgayThang ORDER BY A.KeHoach_Id ASC, B.NgayThang DESC;");
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
    
    public function updatKeHoachNganSach($kehoach_id, $nhom_id, $loai_id, $email, $ngaybatdau, $ngayketthuc, $dienta, $sotien)
    {
        try {
            $this->db->open();
            $result = mysql_query("UPDATE tbl_kehoach SET Nhom_Id = '$nhom_id', Loai_Id = '$loai_id', NgayBatDau = '$ngaybatdau', NgayKetThuc = '$ngayketthuc', DienTa = '$dienta', SoTien = '$sotien' WHERE KeHoach_Id = '$kehoach_id' AND Email = '$email';");
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
    
    public function selectThongBaoGiaoDich($email)
    {
         try {
            $this->db->open();
            $result = mysql_query("SELECT A.KeHoach_Id, A.Nhom_Id, A.Loai_Id, A.Vi_Id, A.EmailDoiTac, A.Email, A.NgayBatDau, A.NgayKetThuc, A.DienTa, A.SoTien, A.TrangThai, A.LoaiKeHoach, A.GiaoDich_Id, B.NgayTra, B.TienLai, B.LoaiThuLai FROM tbl_kehoach A, tbl_giaodich B WHERE A.GiaoDich_Id = B.GiaoDich_Id AND A.NgayBatDau <= CURDATE() AND (A.TrangThai = '0' OR A.TrangThai = '2') AND (A.LoaiKeHoach = '0' OR A.LoaiKeHoach = '1' OR A.LoaiKeHoach = '2') AND A.Email = '$email' ORDER BY A.TrangThai ASC, A.NgayBatDau DESC;");
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
    
    public function updateDaXem($email)
    {
        try {
            $this->db->open();
            $result = mysql_query("UPDATE tbl_kehoach SET TrangThai = '2' WHERE Email = '$email' AND TrangThai = '0' AND (LoaiKeHoach = '0' OR LoaiKeHoach = '1' OR LoaiKeHoach = '2') AND NgayBatDau <= CURDATE();");
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
    
    public function  deleteKeHoachGiaoDich($giaodich_id, $email)
    {
        try {
            $this->db->open();
            $result = mysql_query("DELETE FROM tbl_kehoach WHERE GiaoDich_Id = '$giaodich_id' AND Email = '$email' AND (LoaiKeHoach = '0' OR LoaiKeHoach = '1');");
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
    
    public function selectSoNo($email, $loai_id)
    {
         try {
            $this->db->open();
            $result = mysql_query("SELECT A.KeHoach_Id, A.Loai_Id Loai_Id_tblKH, A.EmailDoiTac EmailDoiTac_tblKH, A.Email Email_tblKH, A.NgayBatDau, A.DienTa, A.SoTien SoTien_tblKH, A.LoaiKeHoach, A.GiaoDich_Id, B.Nhom_Id, C.TenNhom, C.Loai_Id Loai_Id_tblN, C.Anh, B.SoTien SoTien_tblGD, B.GhiChu, B.EmailDoiTac EmailDoiTac_tblGD, B.NgayThang, B.Quy_Id, D.TenQuy, D.SoTien SoTien_tblQ, B.Email Email_tblGD, B.NgayTra, B.TienLai, B.LoaiThuLai FROM tbl_kehoach A, tbl_giaodich B, tbl_nhom C, tbl_quy D WHERE A.Email = '$email' AND (A.LoaiKeHoach = '0' OR A.LoaiKeHoach = '1') AND A.GiaoDich_Id IN (SELECT GiaoDich_Id FROM tbl_giaodich WHERE Email = '$email') AND A.GiaoDich_Id = B.GiaoDich_Id AND B.Nhom_Id = C.Nhom_Id AND B.Quy_Id = D.Quy_Id AND A.Loai_Id = '$loai_id' ORDER BY A.NgayBatdau ASC;");
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
