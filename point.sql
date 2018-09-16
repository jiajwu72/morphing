-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Mer 22 Mars 2017 à 23:31
-- Version du serveur :  10.1.19-MariaDB
-- Version de PHP :  5.5.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `morphing`
--

-- --------------------------------------------------------

--
-- Structure de la table `point`
--

CREATE TABLE `point` (
  `id` int(10) NOT NULL,
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  `nomPhoto` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `point`
--

INSERT INTO `point` (`id`, `x`, `y`, `nomPhoto`) VALUES
(1, 10, 10, 'chien.jpg'),
(1, 10, 10, 'ours.jpg'),
(1, 10, 10, 'singe.jpg'),
(2, 390, 10, 'chien.jpg'),
(2, 390, 10, 'ours.jpg'),
(2, 390, 10, 'singe.jpg'),
(3, 10, 390, 'chien.jpg'),
(3, 10, 390, 'ours.jpg'),
(3, 10, 390, 'singe.jpg'),
(4, 390, 390, 'chien.jpg'),
(4, 390, 390, 'ours.jpg'),
(4, 390, 390, 'singe.jpg'),
(5, 135, 389, 'chien.jpg'),
(5, 47, 372, 'ours.jpg'),
(6, 127, 330, 'chien.jpg'),
(6, 62, 315, 'ours.jpg'),
(7, 124, 314, 'chien.jpg'),
(7, 79, 249, 'ours.jpg'),
(8, 138, 246, 'chien.jpg'),
(8, 108, 180, 'ours.jpg'),
(9, 117, 180, 'chien.jpg'),
(9, 116, 161, 'ours.jpg'),
(10, 80, 299, 'chien.jpg'),
(10, 95, 127, 'ours.jpg'),
(11, 73, 172, 'chien.jpg'),
(11, 95, 76, 'ours.jpg'),
(12, 107, 94, 'chien.jpg'),
(12, 111, 67, 'ours.jpg'),
(13, 120, 114, 'chien.jpg'),
(13, 139, 88, 'ours.jpg'),
(14, 147, 78, 'chien.jpg'),
(14, 152, 93, 'ours.jpg'),
(15, 128, 87, 'chien.jpg'),
(15, 169, 80, 'ours.jpg'),
(16, 168, 64, 'chien.jpg'),
(16, 205, 71, 'ours.jpg'),
(17, 213, 54, 'chien.jpg'),
(17, 241, 71, 'ours.jpg'),
(18, 221, 54, 'chien.jpg'),
(18, 266, 92, 'ours.jpg'),
(19, 229, 46, 'chien.jpg'),
(19, 283, 96, 'ours.jpg'),
(20, 233, 24, 'chien.jpg'),
(20, 316, 59, 'ours.jpg'),
(21, 263, 37, 'chien.jpg'),
(21, 331, 60, 'ours.jpg'),
(22, 280, 67, 'chien.jpg'),
(22, 346, 84, 'ours.jpg'),
(23, 279, 92, 'chien.jpg'),
(23, 341, 123, 'ours.jpg'),
(24, 304, 183, 'chien.jpg'),
(24, 323, 147, 'ours.jpg'),
(25, 287, 230, 'chien.jpg'),
(25, 333, 249, 'ours.jpg'),
(26, 304, 329, 'chien.jpg'),
(26, 339, 299, 'ours.jpg'),
(27, 323, 354, 'chien.jpg'),
(27, 341, 348, 'ours.jpg'),
(28, 339, 378, 'chien.jpg'),
(28, 336, 378, 'ours.jpg'),
(29, 182, 322, 'chien.jpg'),
(29, 119, 182, 'ours.jpg'),
(30, 178, 313, 'chien.jpg'),
(30, 113, 223, 'ours.jpg'),
(31, 151, 276, 'chien.jpg'),
(31, 123, 305, 'ours.jpg'),
(32, 215, 332, 'chien.jpg'),
(32, 160, 351, 'ours.jpg'),
(33, 134, 161, 'chien.jpg'),
(33, 170, 131, 'ours.jpg'),
(34, 153, 158, 'chien.jpg'),
(34, 196, 132, 'ours.jpg'),
(35, 138, 181, 'chien.jpg'),
(35, 182, 145, 'ours.jpg'),
(36, 197, 130, 'chien.jpg'),
(36, 249, 123, 'ours.jpg'),
(37, 220, 121, 'chien.jpg'),
(37, 274, 134, 'ours.jpg'),
(38, 216, 135, 'chien.jpg'),
(38, 256, 151, 'ours.jpg'),
(39, 160, 173, 'chien.jpg'),
(39, 198, 145, 'ours.jpg'),
(40, 160, 274, 'chien.jpg'),
(40, 179, 247, 'ours.jpg'),
(41, 191, 294, 'chien.jpg'),
(41, 201, 269, 'ours.jpg'),
(42, 242, 289, 'chien.jpg'),
(42, 249, 264, 'ours.jpg'),
(43, 246, 231, 'chien.jpg'),
(43, 261, 227, 'ours.jpg'),
(44, 238, 191, 'chien.jpg'),
(44, 259, 179, 'ours.jpg'),
(45, 220, 159, 'chien.jpg'),
(45, 241, 141, 'ours.jpg'),
(46, 182, 157, 'chien.jpg'),
(46, 207, 129, 'ours.jpg'),
(47, 176, 182, 'chien.jpg'),
(47, 197, 165, 'ours.jpg'),
(48, 190, 169, 'chien.jpg'),
(48, 212, 145, 'ours.jpg'),
(49, 202, 171, 'chien.jpg'),
(49, 228, 145, 'ours.jpg'),
(50, 219, 200, 'chien.jpg'),
(50, 242, 172, 'ours.jpg'),
(51, 217, 221, 'chien.jpg'),
(51, 235, 192, 'ours.jpg'),
(52, 193, 236, 'chien.jpg'),
(52, 213, 208, 'ours.jpg'),
(53, 171, 214, 'chien.jpg'),
(53, 197, 194, 'ours.jpg'),
(54, 172, 267, 'chien.jpg'),
(54, 197, 234, 'ours.jpg'),
(55, 208, 244, 'chien.jpg'),
(55, 219, 224, 'ours.jpg'),
(56, 232, 264, 'chien.jpg'),
(56, 251, 237, 'ours.jpg'),
(57, 216, 302, 'chien.jpg'),
(57, 222, 240, 'ours.jpg');

--
-- Index pour les tables exportées
--

--
-- Index pour la table `point`
--
ALTER TABLE `point`
  ADD PRIMARY KEY (`id`,`nomPhoto`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
