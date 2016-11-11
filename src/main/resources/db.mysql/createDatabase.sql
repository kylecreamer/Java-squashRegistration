--
-- Database: `cis2232_squash_registration`
--

create database squash;
use squash;

/*create a user in database*/
grant select, insert, update, delete on squash.*
to 'cis2232_admin'@'localhost'
identified by 'Test1234';
flush privileges;


--
-- Table structure for table `player`
--

CREATE TABLE IF NOT EXISTS `player` (
`name` varchar(20) NOT NULL,
`parentName` varchar(20) NOT NULL,
`phoneNumber` varchar(20) NOT NULL,
`emailAddress` varchar(30) NOT NULL,
`amountPaid` int(11) NOT NULL
);