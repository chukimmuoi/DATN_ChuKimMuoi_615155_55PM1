<?php

class ser_nhom {

    private $db;

    function __construct() {
        require_once '../utils/ketnoi_csdl.php';
        $this->db = new KetNoi_CSDL();
    }

    function __destruct() {
        
    }

    public function insertNhom($tennhom, $loai_id, $anh, $email) {
        try {
            $this->db->open();
            $result = mysql_query("INSERT INTO tbl_nhom(TenNhom, Loai_Id, Anh, Email) VALUES"
                    . "('$tennhom','$loai_id','$anh','$email')");
            if ($result) {
                $result = mysql_query("SELECT * FROM tbl_nhom WHERE TenNhom = '$tennhom' AND Email = '$email'");
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

    public function selectNhom_Loai($loai_id, $email) {
        try {
            $this->db->open();
            $result = mysql_query("SELECT * FROM tbl_nhom WHERE Loai_Id = '$loai_id' AND Email = '$email' ORDER BY TenNhom ASC");
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

    public function selectIdNhom_VayMuon($loai_id, $email, $tennhom){
        try {
            $this->db->open();
            $result = mysql_query("SELECT * FROM tbl_nhom WHERE Email = '$email' AND Loai_Id = '$loai_id' AND TenNhom = '$tennhom' LIMIT 1");
            if ($result) {
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

    public function selectNhom($email) {
        try {
            $this->db->open();
            $result = mysql_query("SELECT * FROM tbl_nhom WHERE Email = '$email' ORDER BY TenNhom ASC");
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
    
    public function testTonTai($tennhom, $email) {
        try {
            $this->db->open();
            $result = mysql_query("SELECT TenNhom FROM tbl_nhom WHERE TenNhom = '$tennhom' AND Email = '$email'");
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

    public function updateNhom($nhom_id, $tennhom, $loai_id, $anh) {
        try {
            $this->db->open();
            $result = mysql_query("UPDATE tbl_nhom SET TenNhom = '$tennhom', Loai_Id = '$loai_id', Anh = '$anh' WHERE Nhom_Id = '$nhom_id'");
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
    
    public function deleteNhom($nhom_id)
    {
        try {
            $this->db->open();
            $result = mysql_query("DELETE FROM tbl_nhom WHERE Nhom_Id = '$nhom_id'");
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
    
    public function selectNhom_Id($nhom_id)
    {
        try {
            $this->db->open();
            $result = mysql_query("SELECT * FROM tbl_nhom WHERE Nhom_Id = '$nhom_id'");
            if ($result) {
                return @mysql_fetch_array($result);
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
