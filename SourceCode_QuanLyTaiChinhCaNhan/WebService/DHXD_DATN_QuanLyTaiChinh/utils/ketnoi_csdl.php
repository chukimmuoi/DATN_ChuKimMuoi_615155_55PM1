<?php
class KetNoi_CSDL {
    function __construct() {
        
    }
    
    function __destruct() {
        // $this->close();
    }

    public function open() {
        require_once '../utils/config.php';
        $con = mysql_connect(DB_HOST, DB_USER, DB_PASSWORD);
        mysql_set_charset("utf8", $con);
        mysql_select_db(DB_DATABASE);
        return $con;
    }

    public function close() {
        mysql_close();
    }

}

