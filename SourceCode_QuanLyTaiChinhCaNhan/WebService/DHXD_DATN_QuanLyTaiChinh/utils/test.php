<?php

//Lấy ngày hiện tại
$today = date('Y-m-d');
echo "Today is " . $today . "<br/>";
//Cộng thêm 1 tuần
$week = strtotime(date("Y-m-d", strtotime($today)) . " +1 week");
$week = strftime("%Y-%m-%d", $week);
echo "A week later is " . $week . "<br/>";

//Cộng thêm 1 tháng
$month = strtotime(date("Y-m-d", strtotime($today)) . " +1 month");
$month = strftime("%Y-%m-%d", $month);
echo "A month later is 1 " . $month . "<br />";
//Cộng 3 tháng
$month3 = strtotime(date("Y-m-d", strtotime($today)) . " +3 month");
$month3 = strftime("%Y-%m-%d", $month3);
echo "A month later is 3 " . $month3 . "<br />";
//Cộng 6 tháng
$month6 = strtotime(date("Y-m-d", strtotime($today)) . " +6 month");
$month6 = strftime("%Y-%m-%d", $month6);
echo "A month later is 6 " . $month6 . "<br />";
//cộng 1 năm
$year = strtotime(date("Y-m-d", strtotime($today)) . " +1 year");
$year = strftime("%Y-%m-%d", $year);
echo "A month later is 12 " . $year . "<br />";

$first_date = strtotime('2012-07-11');
$second_date = strtotime('2012-07-15');
$datediff = abs($first_date - $second_date);
echo "".floor($datediff / (60 * 60 * 24))."<br/>";


$originalDate = "2010-03-21";
$newDate = date("d-m-Y", strtotime($originalDate));
echo "Ngày tháng năm ".$newDate."<br/>";

