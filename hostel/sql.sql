/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 10.4.14-MariaDB : Database - hostel
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`hostel` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `hostel`;

/*Table structure for table `bill` */

DROP TABLE IF EXISTS `bill`;

CREATE TABLE `bill` (
  `bill_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` varchar(100) DEFAULT NULL,
  `bill_date` varchar(100) DEFAULT NULL,
  `no_of_leave` varchar(20) DEFAULT NULL,
  `no_of_present` varchar(20) DEFAULT NULL,
  `proom_rent` varchar(20) DEFAULT NULL,
  `pmess_rent` varchar(20) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`bill_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `bill` */

insert  into `bill`(`bill_id`,`student_id`,`bill_date`,`no_of_leave`,`no_of_present`,`proom_rent`,`pmess_rent`,`amount`,`status`) values 
(1,'2','2022-02-09',NULL,NULL,NULL,NULL,'0','paid'),
(2,'2','2022-02-12',NULL,NULL,NULL,NULL,'2000','paid'),
(3,'2','2022-02-11',NULL,NULL,NULL,NULL,'2000','pending'),
(4,'2','2022-02-11',NULL,NULL,NULL,NULL,'2000','pending'),
(5,'2','2022-02-08',NULL,NULL,NULL,NULL,'2500','pending'),
(6,'2','2022-02-24','2','10','74','166.66666666666669','240.66666666666669','paid'),
(7,'2','2022-02-25','2','10','74','166.66666666666669','240.66666666666669','pending'),
(8,'2','2022-02-25','2','20','148','333.33333333333337','481.33333333333337','pending'),
(9,'2','2022-02-25','2','10','74.0','166.67','240.67','pending');

/*Table structure for table `floor` */

DROP TABLE IF EXISTS `floor`;

CREATE TABLE `floor` (
  `floor_id` int(11) NOT NULL AUTO_INCREMENT,
  `floor_name` varchar(50) DEFAULT NULL,
  `floor_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`floor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `floor` */

insert  into `floor`(`floor_id`,`floor_name`,`floor_type`) values 
(1,'Ground Floor','UG'),
(2,'1st Floor','UG'),
(3,'2nd Floor','PG'),
(4,'3rd Floor','PG');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `usertype` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(1,'admin','admin','admin'),
(2,'ammu','aa','student'),
(4,'gibivysyz','aaaa','student'),
(5,'nemobiro','Pa$$w0rd!','student');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `bill_id` int(11) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

insert  into `payment`(`payment_id`,`bill_id`,`amount`,`date`) values 
(1,1,'0','2022-02-10'),
(2,1,'0','2022-02-10'),
(4,6,'481.33333333333337','2022-02-24'),
(5,2,'2000','2022-02-27');

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `request_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `leavedate` varchar(100) DEFAULT NULL,
  `reason` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`request_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `request` */

insert  into `request`(`request_id`,`student_id`,`leavedate`,`reason`,`date`,`status`) values 
(7,2,'19-02-2022','fghjjn','2022-02-10','accept'),
(8,2,'28-02-2022','Sample','2022-02-24','accept');

/*Table structure for table `room` */

DROP TABLE IF EXISTS `room`;

CREATE TABLE `room` (
  `room_id` int(11) NOT NULL AUTO_INCREMENT,
  `floor_id` int(11) DEFAULT NULL,
  `no_persons` varchar(20) DEFAULT NULL,
  `room_number` varchar(100) DEFAULT NULL,
  `room_rent` varchar(100) DEFAULT NULL,
  `mess_rent` varchar(20) DEFAULT NULL,
  `details` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`room_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `room` */

insert  into `room`(`room_id`,`floor_id`,`no_persons`,`room_number`,`room_rent`,`mess_rent`,`details`,`status`) values 
(2,NULL,NULL,'11','3456',NULL,'wertyu','available'),
(3,1,'2','12','222','500','qwerty','available'),
(4,NULL,NULL,'13','2000',NULL,'qwerty','available'),
(5,NULL,NULL,'14','3000',NULL,'qwerty','available'),
(6,1,'2','ug1','5000','4000','Architecto adipisci ','available');

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL,
  `firstname` varchar(100) DEFAULT NULL,
  `lastname` varchar(100) DEFAULT NULL,
  `gender` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `district` varchar(100) DEFAULT NULL,
  `adhar_no` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `student` */

insert  into `student`(`student_id`,`login_id`,`room_id`,`firstname`,`lastname`,`gender`,`phone`,`email`,`place`,`district`,`adhar_no`) values 
(2,2,3,'ammu','a','female','7451236985','as@gmail.com','ekm','ammu','ammu'),
(3,4,6,'Zena','Harding','male','+1 (748) 135-8762','zates@mailinator.com','Ducimus sapiente nu','gibivysyz','aaaa'),
(4,5,6,'Brent','Gould','male','+1 (899) 273-5572','gomajac@mailinator.com','In labore ut provide','nemobiro','Pa$$w0rd!');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
