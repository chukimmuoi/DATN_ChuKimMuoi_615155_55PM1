<?php

class ser_doitac {

    private $db;

    function __construct() {
        require_once '../utils/ketnoi_csdl.php';
        $this->db = new KetNoi_CSDL();
    }

    function __destruct() {
        
    }

    public function selectDoiTac($email) {
        try {
            $this->db->open();
            $result = mysql_query("SELECT a.DoiTac_Id, a.EmailDoiTac, a.QuanHe, a.XacNhan, b.HoTen FROM tbl_doitac a, tbl_nguoidung b WHERE a.Email = '$email' AND XacNhan = '1' AND b.Email = a.EmailDoiTac ORDER BY b.HoTen ASC");
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

    public function insertDoiTac($email, $emaidoitac, $quanhe)
    {
         try {
            $this->db->open();
            $result = mysql_query("INSERT INTO tbl_doitac(Email, EmailDoiTac, QuanHe, XacNhan) VALUES"
                    . "('$email','$emaidoitac','$quanhe','0')");
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
    
    public function testTonTai($email, $emaildoitac)
    {
        try {
            $this->db->open();
            $result = mysql_query("SELECT * FROM tbl_doitac WHERE Email = '$email' AND EmailDoiTac = '$emaildoitac' AND (XacNhan = '0' OR XacNhan = '1' OR 'XacNhan = 2')");
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
    
    public function testEmail($emaildoitac)
    {
        try {
            $this->db->open();
            $result = mysql_query("SELECT Email FROM tbl_nguoidung WHERE Email = '$emaildoitac'");
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
    
    public function updateDoiTac($doitac_id, $quanhe)
    {
         try {
            $this->db->open();
            $result = mysql_query("UPDATE tbl_doitac SET QuanHe = '$quanhe' WHERE DoiTac_Id = '$doitac_id'");
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
    
    public function deleteDoiTac($doitac_id)
    {
         try {
            $this->db->open();
            $result = mysql_query("UPDATE tbl_doitac SET XacNhan = '3' WHERE DoiTac_Id = '$doitac_id'");
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
    
    public function deleteDoiTac2($email, $emaildoitac)
    {
         try {
            $this->db->open();
            $result = mysql_query("UPDATE tbl_doitac SET XacNhan = '3' WHERE Email = '$emaildoitac' AND EmailDoiTac = '$email';");
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
    
    public function selectDoiTacThongBao($email)
    {
        try {
            $this->db->open();
            $result = mysql_query("SELECT A.DoiTac_Id, B.HoTen, A.Email, A.QuanHe, A.XacNhan FROM tbl_doitac A, tbl_nguoidung B WHERE A.Email = B.Email AND A.EmailDoiTac = '$email' AND (A.XacNhan = '0' OR A.XacNhan = '2') ORDER BY A.XacNhan ASC;");
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
            $result = mysql_query("UPDATE tbl_doitac SET XacNhan = '2' WHERE EmailDoiTac = '$email' AND XacNhan = '0';");
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
    
    public function insertDoiTacDongY($email, $emaidoitac, $quanhe)
    {
         try {
            $this->db->open();
            $result = mysql_query("INSERT INTO tbl_doitac(Email, EmailDoiTac, QuanHe, XacNhan) VALUES"
                    . "('$email','$emaidoitac','$quanhe','1')");
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
     
    public function updateDoiTacDongY($email, $emaildoitac)
    {
         try {
            $this->db->open();
            $result = mysql_query("UPDATE tbl_doitac SET XacNhan = '1' WHERE Email = '$email' AND EmailDoiTac = '$emaildoitac' AND (XacNhan = '0' OR XacNhan = '2');");
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
    
    public function deleteDoiTacThat($doitac_id)
    {
         try {
            $this->db->open();
            $result = mysql_query("DELETE FROM tbl_doitac WHERE DoiTac_Id = '$doitac_id'");
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
