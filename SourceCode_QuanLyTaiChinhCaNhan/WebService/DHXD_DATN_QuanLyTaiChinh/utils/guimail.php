<?php

class guimail {

    function __construct() {
        require_once '../PHPMailer-master/PHPMailerAutoload.php';
    }

    public function GuiMail($EmailNhan, $HoTen, $ChuDe, $NoiDung) {
        $mail = new PHPMailer;
        $mail->CharSet="utf-8";
        $mail->isSMTP();
        $mail->Host = 'smtp.gmail.com';
        $mail->SMTPAuth = true;
        $mail->Username = 'chukimmuoi@gmail.com';
        $mail->Password = '142499359s';
        $mail->SMTPSecure = 'tls';
        $mail->Port = 587;

        $mail->From = 'chukimmuoi@gmail.com';
        $mail->FromName = 'Quản lý tài chính cá nhân.';
        $mail->addAddress($EmailNhan, $HoTen);

        $mail->isHTML(true);
        $mail->Subject = $ChuDe;
        $mail->Body = $NoiDung;
        $mail->send();
    }

}
