-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 11, 2015 at 02:53 PM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dhxd_datn_quanlytaichinh`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_doitac`
--

CREATE TABLE IF NOT EXISTS `tbl_doitac` (
`DoiTac_Id` int(11) NOT NULL,
  `Email` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `EmailDoiTac` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `QuanHe` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `XacNhan` int(11) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=10 ;

--
-- Dumping data for table `tbl_doitac`
--

INSERT INTO `tbl_doitac` (`DoiTac_Id`, `Email`, `EmailDoiTac`, `QuanHe`, `XacNhan`) VALUES
(1, 'chukimmuoi@gmail.com', 'dosimk55pm@gmail.com', 'Bạn thân', 0),
(2, 'chukimmuoi@gmail.com', 'kimmuoi92@gmail.com', 'Anh em', 1),
(3, 'chukimmuoi@gmail.com', 'vuthanglang@gmail.com', 'Anh em họ', 1),
(4, 'chukimmuoi@gmail.com', 'lecongthai@gmail.com', 'Trí cốt', 1),
(5, 'chukimmuoi@gmail.com', 'nguyenthicham@gmail.com', 'Bạn từ bé', 2),
(6, 'chukimmuoi@gmail.com', 'mjnguyen183@gmail.com', 'Bạn thân đại học', 2),
(7, 'kimmuoi92@gmail.com', 'chukimmuoi@gmail.com', 'Anh em', 1),
(8, 'vuthanglang@gmail.com', 'chukimmuoi@gmail.com', 'Anh em họ', 1),
(9, 'lecongthai@gmail.com', 'chukimmuoi@gmail.com', 'Trí cốt', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_giaodich`
--

CREATE TABLE IF NOT EXISTS `tbl_giaodich` (
`GiaoDich_Id` int(11) NOT NULL,
  `Nhom_Id` int(11) NOT NULL,
  `SoTien` double NOT NULL,
  `GhiChu` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `EmailDoiTac` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NgayThang` date NOT NULL,
  `Quy_Id` int(11) NOT NULL,
  `Email` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `NgayTra` date DEFAULT NULL,
  `TienLai` double DEFAULT NULL,
  `LoaiThuLai` int(2) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=10 ;

--
-- Dumping data for table `tbl_giaodich`
--

INSERT INTO `tbl_giaodich` (`GiaoDich_Id`, `Nhom_Id`, `SoTien`, `GhiChu`, `EmailDoiTac`, `NgayThang`, `Quy_Id`, `Email`, `NgayTra`, `TienLai`, `LoaiThuLai`) VALUES
(0, 0, 0, '', '', '0000-00-00', 0, '', '0000-00-00', 0, 0),
(2, 15, 40000, 'Đi chợ', '', '2015-01-04', 1, 'chukimmuoi@gmail.com', '0000-00-00', 0, 0),
(3, 1, 50000, 'Bán vỏ chai nhựa', '', '2015-01-04', 1, 'chukimmuoi@gmail.com', '0000-00-00', 0, 0),
(4, 19, 6000, 'Mua keo 502', '', '2015-01-04', 1, 'chukimmuoi@gmail.com', '0000-00-00', 0, 0),
(5, 3, 100000, 'Vay $ của Thái', 'lecongthai@gmail.com', '2015-01-03', 6, 'chukimmuoi@gmail.com', '2015-02-03', 0, 0),
(6, 9, 50000, 'Cho Thái vay $ đi chợ', 'lecongthai@gmail.com', '2014-12-28', 3, 'chukimmuoi@gmail.com', '2015-02-07', 0, 0),
(7, 3, 50000, 'Vay $ anh Lăng mua hoa', 'vuthanglang@gmail.com', '2014-12-25', 6, 'chukimmuoi@gmail.com', '2015-02-06', 0, 0),
(8, 9, 50000, 'Cho Oanh vay $', '', '2015-01-05', 3, 'chukimmuoi@gmail.com', '2015-01-30', 0, 0),
(9, 3, 100000, 'Vay $ bạn', '', '2015-01-05', 3, 'chukimmuoi@gmail.com', '2015-02-07', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_kehoach`
--

CREATE TABLE IF NOT EXISTS `tbl_kehoach` (
`KeHoach_Id` int(11) NOT NULL,
  `Nhom_Id` int(11) DEFAULT NULL,
  `Loai_Id` int(11) NOT NULL,
  `Vi_Id` int(11) DEFAULT NULL,
  `EmailDoiTac` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Email` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `NgayBatDau` date NOT NULL,
  `NgayKetThuc` date DEFAULT NULL,
  `DienTa` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SoTien` double NOT NULL,
  `TrangThai` int(1) NOT NULL,
  `LoaiKeHoach` int(1) NOT NULL,
  `GiaoDich_Id` int(11) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=19 ;

--
-- Dumping data for table `tbl_kehoach`
--

INSERT INTO `tbl_kehoach` (`KeHoach_Id`, `Nhom_Id`, `Loai_Id`, `Vi_Id`, `EmailDoiTac`, `Email`, `NgayBatDau`, `NgayKetThuc`, `DienTa`, `SoTien`, `TrangThai`, `LoaiKeHoach`, `GiaoDich_Id`) VALUES
(1, NULL, 1, NULL, 'lecongthai@gmail.com', 'chukimmuoi@gmail.com', '2015-02-03', NULL, 'Trả khoản nợ vay lecongthai@gmail.com ngày 03-01-2015', 100000, 0, 1, 5),
(2, 29, 1, NULL, 'chukimmuoi@gmail.com', 'lecongthai@gmail.com', '2015-01-03', NULL, 'Cho chukimmuoi@gmail.com vay tiền', 100000, 2, 0, 5),
(3, NULL, 0, NULL, 'chukimmuoi@gmail.com', 'lecongthai@gmail.com', '2015-02-03', NULL, 'Thu khoản tiền cho chukimmuoi@gmail.com vay ngày 03-01-2015', 100000, 0, 1, 5),
(4, NULL, 0, NULL, 'lecongthai@gmail.com', 'chukimmuoi@gmail.com', '2015-02-07', NULL, 'Thu khoản tiền cho lecongthai@gmail.com vay ngày 28-12-2014', 50000, 0, 1, 6),
(5, 23, 0, NULL, 'chukimmuoi@gmail.com', 'lecongthai@gmail.com', '2014-12-28', NULL, 'Vay tiền của chukimmuoi@gmail.com', 50000, 2, 0, 6),
(6, NULL, 1, NULL, 'chukimmuoi@gmail.com', 'lecongthai@gmail.com', '2015-02-07', NULL, 'Trả khoản nợ vay chukimmuoi@gmail.com ngày 28-12-2014', 50000, 0, 1, 6),
(7, 10, 1, 3, NULL, 'chukimmuoi@gmail.com', '2015-01-22', NULL, 'Du lịch Sapa', 1000000, 0, 2, 0),
(8, 17, 1, 1, NULL, 'chukimmuoi@gmail.com', '2015-03-17', NULL, 'Ăn cưới bạn Trang', 300000, 0, 2, 0),
(9, 15, 1, NULL, NULL, 'chukimmuoi@gmail.com', '2015-01-01', '2015-01-31', 'Kế hoạch đi chợ tháng 1/2015', 500000, 0, 3, 0),
(10, 3, 0, NULL, NULL, 'chukimmuoi@gmail.com', '2014-12-01', '2014-12-31', 'Kế hoạch vay nợ tháng 12/2014', 50000, 0, 3, 0),
(11, NULL, 1, NULL, 'vuthanglang@gmail.com', 'chukimmuoi@gmail.com', '2015-02-06', NULL, 'Trả khoản nợ vay vuthanglang@gmail.com ngày 25-12-2014', 50000, 0, 1, 7),
(12, 49, 1, NULL, 'chukimmuoi@gmail.com', 'vuthanglang@gmail.com', '2014-12-25', NULL, 'Cho chukimmuoi@gmail.com vay tiền', 50000, 0, 0, 7),
(13, NULL, 0, NULL, 'chukimmuoi@gmail.com', 'vuthanglang@gmail.com', '2015-02-06', NULL, 'Thu khoản tiền cho chukimmuoi@gmail.com vay ngày 25-12-2014', 50000, 0, 1, 7),
(14, 13, 1, 4, NULL, 'chukimmuoi@gmail.com', '2015-02-16', NULL, 'Đóng học phí học kỳ 2', 2500000, 0, 2, 0),
(15, 13, 1, 6, NULL, 'chukimmuoi@gmail.com', '2015-01-15', NULL, 'Khám sức khỏe định kỳ', 100000, 0, 2, 0),
(16, 1, 0, NULL, NULL, 'chukimmuoi@gmail.com', '2015-01-01', '2015-01-31', 'Kế hoạch bán đồ tháng 1/2015', 500000, 0, 3, 0),
(17, NULL, 0, NULL, '', 'chukimmuoi@gmail.com', '2015-01-30', NULL, 'Thu khoản tiền cho vay ngày 05-01-2015', 50000, 0, 1, 8),
(18, NULL, 1, NULL, '', 'chukimmuoi@gmail.com', '2015-02-07', NULL, 'Trả khoản nợ vay ngày 05-01-2015', 100000, 0, 1, 9);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_loai`
--

CREATE TABLE IF NOT EXISTS `tbl_loai` (
  `Loai_Id` int(11) NOT NULL,
  `TenLoai` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tbl_loai`
--

INSERT INTO `tbl_loai` (`Loai_Id`, `TenLoai`) VALUES
(0, 'Thu nhập'),
(1, 'Chi tiêu');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_nguoidung`
--

CREATE TABLE IF NOT EXISTS `tbl_nguoidung` (
  `Unique_Id` varchar(23) COLLATE utf8_unicode_ci NOT NULL,
  `HoTen` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Email` varchar(100) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `MatKhau` varchar(80) COLLATE utf8_unicode_ci NOT NULL,
  `MaBam` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `NgayTao` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tbl_nguoidung`
--

INSERT INTO `tbl_nguoidung` (`Unique_Id`, `HoTen`, `Email`, `MatKhau`, `MaBam`, `NgayTao`) VALUES
('54a90c6307d494.33315402', 'Chử Kim Mười', 'chukimmuoi@gmail.com', '1OMWMfFG2kFge5bWBY+WHTIrjxQ1ZmMwMThkOWU5', '5fc018d9e9', '2015-01-04'),
('54a90d81de29a9.15866291', 'Đỗ Thị Sim', 'dosimk55pm@gmail.com', 'nwYkV1uBfFDki7sCMGdAcPWCmwI5ZmFlMGUwMTUy', '9fae0e0152', '2015-01-04'),
('54a90de539e148.86627197', 'Kim Mười', 'kimmuoi92@gmail.com', 'a85PQqE2PhW453v5eFp8yqtBKEAwMmJiNmExNDRm', '02bb6a144f', '2015-01-04'),
('54a90cc532b208.85078619', 'Lê Công Thái', 'lecongthai@gmail.com', 'rdBoqpuQl5mS4w8Zi8K2gSO8JdI1YWZiNDQ4NjA0', '5afb448604', '2015-01-04'),
('54a90db8dc6171.59196556', 'Nguyễn Thị Thủy', 'mjnguyen183@gmail.com', 'D5DJ64K9KnpiiktZS03bcfx87F4wMDkwNGM0ZjEw', '00904c4f10', '2015-01-04'),
('54a90d441f3408.44905924', 'Nguyễn Thị Châm', 'nguyenthicham@gmail.com', 'iov3r9PDriujmYV+DwnyHz/Wh/05NzQxZjc5YWNh', '9741f79aca', '2015-01-04'),
('54a90d0bd3a970.85947389', 'Vũ Thành Lăng', 'vuthanglang@gmail.com', 'lQF6tnTahVCljxif/KqiM4CoC3syZjc3MGY3NDEy', '2f770f7412', '2015-01-04');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_nhom`
--

CREATE TABLE IF NOT EXISTS `tbl_nhom` (
`Nhom_Id` int(11) NOT NULL,
  `TenNhom` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Loai_Id` int(11) NOT NULL,
  `Anh` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Email` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=141 ;

--
-- Dumping data for table `tbl_nhom`
--

INSERT INTO `tbl_nhom` (`Nhom_Id`, `TenNhom`, `Loai_Id`, `Anh`, `Email`) VALUES
(1, 'Bán đồ', 0, '120', 'chukimmuoi@gmail.com'),
(2, 'Lương', 0, '74', 'chukimmuoi@gmail.com'),
(3, 'Nợ', 0, '111', 'chukimmuoi@gmail.com'),
(4, 'Thưởng', 0, '110', 'chukimmuoi@gmail.com'),
(5, 'Tiền lãi', 0, '117', 'chukimmuoi@gmail.com'),
(6, 'Được tặng', 0, '116', 'chukimmuoi@gmail.com'),
(7, 'Khác', 0, '22', 'chukimmuoi@gmail.com'),
(8, 'Bạn bè và tình yêu', 1, '0', 'chukimmuoi@gmail.com'),
(9, 'Cho vay', 1, '119', 'chukimmuoi@gmail.com'),
(10, 'Du lịch', 1, '123', 'chukimmuoi@gmail.com'),
(11, 'Di chuyển', 1, '124', 'chukimmuoi@gmail.com'),
(12, 'Gia đình', 1, '7', 'chukimmuoi@gmail.com'),
(13, 'Giáo dục', 1, '112', 'chukimmuoi@gmail.com'),
(14, 'Giải trí', 1, '125', 'chukimmuoi@gmail.com'),
(15, 'Mua sắm', 1, '2', 'chukimmuoi@gmail.com'),
(16, 'Sức khỏe', 1, '126', 'chukimmuoi@gmail.com'),
(17, 'Ăn uống', 1, '115', 'chukimmuoi@gmail.com'),
(18, 'Đầu tư', 1, '118', 'chukimmuoi@gmail.com'),
(19, 'Khác', 1, '21', 'chukimmuoi@gmail.com'),
(20, 'Chuyển khoản', 1, '127', 'chukimmuoi@gmail.com'),
(21, 'Bán đồ', 0, '120', 'lecongthai@gmail.com'),
(22, 'Lương', 0, '74', 'lecongthai@gmail.com'),
(23, 'Nợ', 0, '111', 'lecongthai@gmail.com'),
(24, 'Thưởng', 0, '110', 'lecongthai@gmail.com'),
(25, 'Tiền lãi', 0, '117', 'lecongthai@gmail.com'),
(26, 'Được tặng', 0, '116', 'lecongthai@gmail.com'),
(27, 'Khác', 0, '22', 'lecongthai@gmail.com'),
(28, 'Bạn bè và tình yêu', 1, '0', 'lecongthai@gmail.com'),
(29, 'Cho vay', 1, '119', 'lecongthai@gmail.com'),
(30, 'Du lịch', 1, '123', 'lecongthai@gmail.com'),
(31, 'Di chuyển', 1, '124', 'lecongthai@gmail.com'),
(32, 'Gia đình', 1, '7', 'lecongthai@gmail.com'),
(33, 'Giáo dục', 1, '112', 'lecongthai@gmail.com'),
(34, 'Giải trí', 1, '125', 'lecongthai@gmail.com'),
(35, 'Mua sắm', 1, '2', 'lecongthai@gmail.com'),
(36, 'Sức khỏe', 1, '126', 'lecongthai@gmail.com'),
(37, 'Ăn uống', 1, '115', 'lecongthai@gmail.com'),
(38, 'Đầu tư', 1, '118', 'lecongthai@gmail.com'),
(39, 'Khác', 1, '21', 'lecongthai@gmail.com'),
(40, 'Chuyển khoản', 1, '127', 'lecongthai@gmail.com'),
(41, 'Bán đồ', 0, '120', 'vuthanglang@gmail.com'),
(42, 'Lương', 0, '74', 'vuthanglang@gmail.com'),
(43, 'Nợ', 0, '111', 'vuthanglang@gmail.com'),
(44, 'Thưởng', 0, '110', 'vuthanglang@gmail.com'),
(45, 'Tiền lãi', 0, '117', 'vuthanglang@gmail.com'),
(46, 'Được tặng', 0, '116', 'vuthanglang@gmail.com'),
(47, 'Khác', 0, '22', 'vuthanglang@gmail.com'),
(48, 'Bạn bè và tình yêu', 1, '0', 'vuthanglang@gmail.com'),
(49, 'Cho vay', 1, '119', 'vuthanglang@gmail.com'),
(50, 'Du lịch', 1, '123', 'vuthanglang@gmail.com'),
(51, 'Di chuyển', 1, '124', 'vuthanglang@gmail.com'),
(52, 'Gia đình', 1, '7', 'vuthanglang@gmail.com'),
(53, 'Giáo dục', 1, '112', 'vuthanglang@gmail.com'),
(54, 'Giải trí', 1, '125', 'vuthanglang@gmail.com'),
(55, 'Mua sắm', 1, '2', 'vuthanglang@gmail.com'),
(56, 'Sức khỏe', 1, '126', 'vuthanglang@gmail.com'),
(57, 'Ăn uống', 1, '115', 'vuthanglang@gmail.com'),
(58, 'Đầu tư', 1, '118', 'vuthanglang@gmail.com'),
(59, 'Khác', 1, '21', 'vuthanglang@gmail.com'),
(60, 'Chuyển khoản', 1, '127', 'vuthanglang@gmail.com'),
(61, 'Bán đồ', 0, '120', 'nguyenthicham@gmail.com'),
(62, 'Lương', 0, '74', 'nguyenthicham@gmail.com'),
(63, 'Nợ', 0, '111', 'nguyenthicham@gmail.com'),
(64, 'Thưởng', 0, '110', 'nguyenthicham@gmail.com'),
(65, 'Tiền lãi', 0, '117', 'nguyenthicham@gmail.com'),
(66, 'Được tặng', 0, '116', 'nguyenthicham@gmail.com'),
(67, 'Khác', 0, '22', 'nguyenthicham@gmail.com'),
(68, 'Bạn bè và tình yêu', 1, '0', 'nguyenthicham@gmail.com'),
(69, 'Cho vay', 1, '119', 'nguyenthicham@gmail.com'),
(70, 'Du lịch', 1, '123', 'nguyenthicham@gmail.com'),
(71, 'Di chuyển', 1, '124', 'nguyenthicham@gmail.com'),
(72, 'Gia đình', 1, '7', 'nguyenthicham@gmail.com'),
(73, 'Giáo dục', 1, '112', 'nguyenthicham@gmail.com'),
(74, 'Giải trí', 1, '125', 'nguyenthicham@gmail.com'),
(75, 'Mua sắm', 1, '2', 'nguyenthicham@gmail.com'),
(76, 'Sức khỏe', 1, '126', 'nguyenthicham@gmail.com'),
(77, 'Ăn uống', 1, '115', 'nguyenthicham@gmail.com'),
(78, 'Đầu tư', 1, '118', 'nguyenthicham@gmail.com'),
(79, 'Khác', 1, '21', 'nguyenthicham@gmail.com'),
(80, 'Chuyển khoản', 1, '127', 'nguyenthicham@gmail.com'),
(81, 'Bán đồ', 0, '120', 'dosimk55pm@gmail.com'),
(82, 'Lương', 0, '74', 'dosimk55pm@gmail.com'),
(83, 'Nợ', 0, '111', 'dosimk55pm@gmail.com'),
(84, 'Thưởng', 0, '110', 'dosimk55pm@gmail.com'),
(85, 'Tiền lãi', 0, '117', 'dosimk55pm@gmail.com'),
(86, 'Được tặng', 0, '116', 'dosimk55pm@gmail.com'),
(87, 'Khác', 0, '22', 'dosimk55pm@gmail.com'),
(88, 'Bạn bè và tình yêu', 1, '0', 'dosimk55pm@gmail.com'),
(89, 'Cho vay', 1, '119', 'dosimk55pm@gmail.com'),
(90, 'Du lịch', 1, '123', 'dosimk55pm@gmail.com'),
(91, 'Di chuyển', 1, '124', 'dosimk55pm@gmail.com'),
(92, 'Gia đình', 1, '7', 'dosimk55pm@gmail.com'),
(93, 'Giáo dục', 1, '112', 'dosimk55pm@gmail.com'),
(94, 'Giải trí', 1, '125', 'dosimk55pm@gmail.com'),
(95, 'Mua sắm', 1, '2', 'dosimk55pm@gmail.com'),
(96, 'Sức khỏe', 1, '126', 'dosimk55pm@gmail.com'),
(97, 'Ăn uống', 1, '115', 'dosimk55pm@gmail.com'),
(98, 'Đầu tư', 1, '118', 'dosimk55pm@gmail.com'),
(99, 'Khác', 1, '21', 'dosimk55pm@gmail.com'),
(100, 'Chuyển khoản', 1, '127', 'dosimk55pm@gmail.com'),
(101, 'Bán đồ', 0, '120', 'mjnguyen183@gmail.com'),
(102, 'Lương', 0, '74', 'mjnguyen183@gmail.com'),
(103, 'Nợ', 0, '111', 'mjnguyen183@gmail.com'),
(104, 'Thưởng', 0, '110', 'mjnguyen183@gmail.com'),
(105, 'Tiền lãi', 0, '117', 'mjnguyen183@gmail.com'),
(106, 'Được tặng', 0, '116', 'mjnguyen183@gmail.com'),
(107, 'Khác', 0, '22', 'mjnguyen183@gmail.com'),
(108, 'Bạn bè và tình yêu', 1, '0', 'mjnguyen183@gmail.com'),
(109, 'Cho vay', 1, '119', 'mjnguyen183@gmail.com'),
(110, 'Du lịch', 1, '123', 'mjnguyen183@gmail.com'),
(111, 'Di chuyển', 1, '124', 'mjnguyen183@gmail.com'),
(112, 'Gia đình', 1, '7', 'mjnguyen183@gmail.com'),
(113, 'Giáo dục', 1, '112', 'mjnguyen183@gmail.com'),
(114, 'Giải trí', 1, '125', 'mjnguyen183@gmail.com'),
(115, 'Mua sắm', 1, '2', 'mjnguyen183@gmail.com'),
(116, 'Sức khỏe', 1, '126', 'mjnguyen183@gmail.com'),
(117, 'Ăn uống', 1, '115', 'mjnguyen183@gmail.com'),
(118, 'Đầu tư', 1, '118', 'mjnguyen183@gmail.com'),
(119, 'Khác', 1, '21', 'mjnguyen183@gmail.com'),
(120, 'Chuyển khoản', 1, '127', 'mjnguyen183@gmail.com'),
(121, 'Bán đồ', 0, '120', 'kimmuoi92@gmail.com'),
(122, 'Lương', 0, '74', 'kimmuoi92@gmail.com'),
(123, 'Nợ', 0, '111', 'kimmuoi92@gmail.com'),
(124, 'Thưởng', 0, '110', 'kimmuoi92@gmail.com'),
(125, 'Tiền lãi', 0, '117', 'kimmuoi92@gmail.com'),
(126, 'Được tặng', 0, '116', 'kimmuoi92@gmail.com'),
(127, 'Khác', 0, '22', 'kimmuoi92@gmail.com'),
(128, 'Bạn bè và tình yêu', 1, '0', 'kimmuoi92@gmail.com'),
(129, 'Cho vay', 1, '119', 'kimmuoi92@gmail.com'),
(130, 'Du lịch', 1, '123', 'kimmuoi92@gmail.com'),
(131, 'Di chuyển', 1, '124', 'kimmuoi92@gmail.com'),
(132, 'Gia đình', 1, '7', 'kimmuoi92@gmail.com'),
(133, 'Giáo dục', 1, '112', 'kimmuoi92@gmail.com'),
(134, 'Giải trí', 1, '125', 'kimmuoi92@gmail.com'),
(135, 'Mua sắm', 1, '2', 'kimmuoi92@gmail.com'),
(136, 'Sức khỏe', 1, '126', 'kimmuoi92@gmail.com'),
(137, 'Ăn uống', 1, '115', 'kimmuoi92@gmail.com'),
(138, 'Đầu tư', 1, '118', 'kimmuoi92@gmail.com'),
(139, 'Khác', 1, '21', 'kimmuoi92@gmail.com'),
(140, 'Chuyển khoản', 1, '127', 'kimmuoi92@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_quy`
--

CREATE TABLE IF NOT EXISTS `tbl_quy` (
`Quy_Id` int(11) NOT NULL,
  `TenQuy` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Anh` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `SoTien` double NOT NULL,
  `Email` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=43 ;

--
-- Dumping data for table `tbl_quy`
--

INSERT INTO `tbl_quy` (`Quy_Id`, `TenQuy`, `Anh`, `SoTien`, `Email`) VALUES
(1, 'Tiêu dùng', '108', 4000, 'chukimmuoi@gmail.com'),
(2, 'Tự do tài chính', '108', 0, 'chukimmuoi@gmail.com'),
(3, 'Tiết kiệm', '108', 0, 'chukimmuoi@gmail.com'),
(4, 'Học tập', '34', 0, 'chukimmuoi@gmail.com'),
(5, 'Làm từ thiện', '111', 0, 'chukimmuoi@gmail.com'),
(6, 'Hưởng thụ', '45', 150000, 'chukimmuoi@gmail.com'),
(7, 'Tiêu dùng', '108', 0, 'lecongthai@gmail.com'),
(8, 'Tự do tài chính', '108', 0, 'lecongthai@gmail.com'),
(9, 'Tiết kiệm', '108', 0, 'lecongthai@gmail.com'),
(10, 'Học tập', '108', 0, 'lecongthai@gmail.com'),
(11, 'Làm từ thiện', '108', 0, 'lecongthai@gmail.com'),
(12, 'Hưởng thụ', '108', 0, 'lecongthai@gmail.com'),
(13, 'Tiêu dùng', '108', 0, 'vuthanglang@gmail.com'),
(14, 'Tự do tài chính', '108', 0, 'vuthanglang@gmail.com'),
(15, 'Tiết kiệm', '108', 0, 'vuthanglang@gmail.com'),
(16, 'Học tập', '108', 0, 'vuthanglang@gmail.com'),
(17, 'Làm từ thiện', '108', 0, 'vuthanglang@gmail.com'),
(18, 'Hưởng thụ', '108', 0, 'vuthanglang@gmail.com'),
(19, 'Tiêu dùng', '108', 0, 'nguyenthicham@gmail.com'),
(20, 'Tự do tài chính', '108', 0, 'nguyenthicham@gmail.com'),
(21, 'Tiết kiệm', '108', 0, 'nguyenthicham@gmail.com'),
(22, 'Học tập', '108', 0, 'nguyenthicham@gmail.com'),
(23, 'Làm từ thiện', '108', 0, 'nguyenthicham@gmail.com'),
(24, 'Hưởng thụ', '108', 0, 'nguyenthicham@gmail.com'),
(25, 'Tiêu dùng', '108', 0, 'dosimk55pm@gmail.com'),
(26, 'Tự do tài chính', '108', 0, 'dosimk55pm@gmail.com'),
(27, 'Tiết kiệm', '108', 0, 'dosimk55pm@gmail.com'),
(28, 'Học tập', '108', 0, 'dosimk55pm@gmail.com'),
(29, 'Làm từ thiện', '108', 0, 'dosimk55pm@gmail.com'),
(30, 'Hưởng thụ', '108', 0, 'dosimk55pm@gmail.com'),
(31, 'Tiêu dùng', '108', 0, 'mjnguyen183@gmail.com'),
(32, 'Tự do tài chính', '108', 0, 'mjnguyen183@gmail.com'),
(33, 'Tiết kiệm', '108', 0, 'mjnguyen183@gmail.com'),
(34, 'Học tập', '108', 0, 'mjnguyen183@gmail.com'),
(35, 'Làm từ thiện', '108', 0, 'mjnguyen183@gmail.com'),
(36, 'Hưởng thụ', '108', 0, 'mjnguyen183@gmail.com'),
(37, 'Tiêu dùng', '108', 0, 'kimmuoi92@gmail.com'),
(38, 'Tự do tài chính', '108', 0, 'kimmuoi92@gmail.com'),
(39, 'Tiết kiệm', '108', 0, 'kimmuoi92@gmail.com'),
(40, 'Học tập', '108', 0, 'kimmuoi92@gmail.com'),
(41, 'Làm từ thiện', '108', 0, 'kimmuoi92@gmail.com'),
(42, 'Hưởng thụ', '108', 0, 'kimmuoi92@gmail.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_doitac`
--
ALTER TABLE `tbl_doitac`
 ADD PRIMARY KEY (`DoiTac_Id`);

--
-- Indexes for table `tbl_giaodich`
--
ALTER TABLE `tbl_giaodich`
 ADD PRIMARY KEY (`GiaoDich_Id`);

--
-- Indexes for table `tbl_kehoach`
--
ALTER TABLE `tbl_kehoach`
 ADD PRIMARY KEY (`KeHoach_Id`);

--
-- Indexes for table `tbl_loai`
--
ALTER TABLE `tbl_loai`
 ADD PRIMARY KEY (`Loai_Id`);

--
-- Indexes for table `tbl_nguoidung`
--
ALTER TABLE `tbl_nguoidung`
 ADD PRIMARY KEY (`Email`);

--
-- Indexes for table `tbl_nhom`
--
ALTER TABLE `tbl_nhom`
 ADD PRIMARY KEY (`Nhom_Id`);

--
-- Indexes for table `tbl_quy`
--
ALTER TABLE `tbl_quy`
 ADD PRIMARY KEY (`Quy_Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_doitac`
--
ALTER TABLE `tbl_doitac`
MODIFY `DoiTac_Id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `tbl_giaodich`
--
ALTER TABLE `tbl_giaodich`
MODIFY `GiaoDich_Id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `tbl_kehoach`
--
ALTER TABLE `tbl_kehoach`
MODIFY `KeHoach_Id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT for table `tbl_nhom`
--
ALTER TABLE `tbl_nhom`
MODIFY `Nhom_Id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=141;
--
-- AUTO_INCREMENT for table `tbl_quy`
--
ALTER TABLE `tbl_quy`
MODIFY `Quy_Id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=43;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
