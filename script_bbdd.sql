CREATE DATABASE  IF NOT EXISTS `v2019`;
USE `v2019`;


 SET NAMES utf8 ;


DROP TABLE IF EXISTS `categoria`;

 SET character_set_client = utf8mb4 ;
CREATE TABLE `categoria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;



LOCK TABLES `categoria` WRITE;

INSERT INTO `categoria` VALUES (2,'bromas'),(1,'musica'),(16,'nombre1'),(17,'nombre2'),(19,'nombre4'),(3,'sustos');

UNLOCK TABLES;






DROP TABLE IF EXISTS `rol`;

 SET character_set_client = utf8mb4 ;
CREATE TABLE `rol` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='1: Administrador	\n2: usuario';




LOCK TABLES `rol` WRITE;

INSERT INTO `rol` VALUES (1,'administrador'),(2,'usuario');

UNLOCK TABLES;



DROP TABLE IF EXISTS `usuario`;

 SET character_set_client = utf8mb4 ;
CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `contrasenya` varchar(45) NOT NULL,
  `id_rol` int(11) NOT NULL DEFAULT '2' COMMENT 'por defecto es ''usuario'' y NO ''administrador''',
  `fecha_creacion` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_eliminacion` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  KEY `FK_usuario_rol_idx` (`id_rol`),
  CONSTRAINT `FK_usuario_rol` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;

INSERT INTO `usuario` VALUES (2,'pepe','pepe',2,'2019-09-12 08:40:05',NULL),(3,'soso','soso',2,'2019-09-12 08:40:05',NULL),(4,'Sr Burn','123',1,'2019-09-12 08:56:26',NULL),(5,'eliminado','123',2,'2019-09-11 08:56:53','2019-09-13 10:50:55'),(6,'admin','admin',1,'2019-09-20 16:02:42',NULL),(7,'Jon','jon',2,'2019-09-22 08:52:34',NULL);

UNLOCK TABLES;

--
-- Table structure for table `video`
--

DROP TABLE IF EXISTS `video`;

 SET character_set_client = utf8mb4 ;
CREATE TABLE `video` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `codigo` varchar(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `categoria_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo_UNIQUE` (`codigo`),
  KEY `fk_video_usuario_idx` (`usuario_id`),
  KEY `fk_video_categoria1_idx` (`categoria_id`),
  CONSTRAINT `fk_video_categoria` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`),
  CONSTRAINT `fk_video_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;


LOCK TABLES `video` WRITE;

INSERT INTO `video` VALUES (3,'Red Hot Chili Peppers - Otherside','rn_YodiJO6k',2,1),(4,'EL FARY -EL TORITO GUAPO','NFkI-zxZlHo',3,1),(5,'No te olvides de poner el Where en el Delete From','i_cVJgIz_Cs',5,1),(6,'Rammstein - Ohne Dich (Official Video)','LIPc1cfS-oQ',2,1),(7,'Rammstein - Ich Will (Official Video)','EOnSh3QlpbQ',2,1),(8,'Te quiero puta - Rammstein','idtD--EF1Dg',2,1),(9,'LAS BROMAS MÁS GRACIOSAS PARA AMIGOS || Guerras de bromas por 123 GO! Spanish','N9zUfbksbnQ',3,2),(10,'sustos graciosos de personas y animales 2019 intenta no explotar de risa | ARG_Smoke','-OYb-rs1eWo',3,3);

UNLOCK TABLES;

DROP TABLE IF EXISTS `likes`;

 SET character_set_client = utf8mb4 ;
CREATE TABLE `likes` (
  `usuario_id` int(11) NOT NULL,
  `video_id` int(11) NOT NULL,
  `fecha` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`usuario_id`,`video_id`),
  KEY `fk_usuario_has_video_video1_idx` (`video_id`),
  KEY `fk_usuario_has_video_usuario1_idx` (`usuario_id`),
  CONSTRAINT `fk_usuario_likes_video` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_video_likes_usuario` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



LOCK TABLES `likes` WRITE;

INSERT INTO `likes` VALUES (2,3,'2019-09-22 08:23:59'),(2,7,'2019-09-22 09:06:50'),(2,8,'2019-09-22 09:06:45'),(3,4,'2019-09-21 15:10:33'),(3,6,'2019-09-22 08:31:54'),(3,8,'2019-09-22 11:15:26'),(7,6,'2019-09-22 08:53:19'),(7,8,'2019-09-22 08:53:30');

UNLOCK TABLES;

DROP PROCEDURE IF EXISTS `getAll_categoria`;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAll_categoria`()
BEGIN
	SELECT id, nombre 
    FROM categoria LIMIT 500;
END ;;
DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAll_rol`()
BEGIN
	SELECT id, nombre 
    FROM rol LIMIT 500;

END ;;
DELIMITER ;

