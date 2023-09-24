<?php

class ser_quy {
     private $db;

    function __construct() {
        require_once '../utils/ketnoi_csdl.php';
        $this->db = new KetNoi_CSDL();
    }

    function __destruct() {
        
    }
    
    public function selectQuy($email) {
        try {
            $this->db->open();
            $result = mysql_query("SELECT * FROM tbl_quy WHERE Email = '$email' ORDER BY TenQuy ASC");
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
    
     public function insertQuy($tenquy, $anh, $sotien, $email) {
        try {
            $this->db->open();
            $result = mysql_query("INSERT INTO tbl_quy(TenQuy, Anh, SoTien, Email) VALUES ('$tenquy','$anh','$sotien','$email')");
            if ($result) {
                $result = mysql_query("SELECT * FROM tbl_quy WHERE TenQuy = '$tenquy' AND Email = '$email'");
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
    
    public function testTonTai($tenquy, $email) {
        try {
            $this->db->open();
            $result = mysql_query("SELECT TenQuy FROM tbl_quy WHERE TenQuy = '$tenquy' AND Email = '$email'");
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
    
    public function updateQuy($quy_id, $tenquy, $anh, $sotien) {
        try {
            $this->db->open();
            $result = mysql_query("UPDATE tbl_quy SET TenQuy = '$tenquy', Anh = '$anh', SoTien = '$sotien' WHERE Quy_Id = '$quy_id'");
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
    
    public function updateSoTienQuy($quy_id, $sotienquy) {
        try {
            $this->db->open();
            $result = mysql_query("UPDATE tbl_quy SET SoTien = '$sotienquy' WHERE Quy_Id = '$quy_id'");
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
    
    public function deleteQuy($quy_id)
    {
        try {
            $this->db->open();
            $result = mysql_query("DELETE FROM tbl_quy WHERE Quy_Id = '$quy_id'");
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
