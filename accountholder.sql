-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 31, 2022 at 08:16 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test1`
--

-- --------------------------------------------------------

--
-- Table structure for table `accountholder`
--

CREATE TABLE `accountholder` (
  `uacno` int(10) NOT NULL,
  `uacname` varchar(100) DEFAULT NULL,
  `uactype` varchar(50) DEFAULT NULL,
  `uacpin` varchar(10) DEFAULT NULL,
  `uacbalance` double(30,5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `accountholder`
--

INSERT INTO `accountholder` (`uacno`, `uacname`, `uactype`, `uacpin`, `uacbalance`) VALUES
(100000, 'Ashlesha', 'Saving', '2624', 90000.00000),
(100001, 'Arbaz', 'Saving', 'OTI0OQ==', 80000.00000),
(100002, 'Avinash Shelke', 'Current', 'ODgwMA==', 50000.00000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accountholder`
--
ALTER TABLE `accountholder`
  ADD PRIMARY KEY (`uacno`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accountholder`
--
ALTER TABLE `accountholder`
  MODIFY `uacno` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=100004;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
