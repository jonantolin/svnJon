

DROP DATABASE IF EXISTS `v2019_jon_gaizka`;
CREATE DATABASE IF NOT EXISTS `v2019_jon_gaizka`;
USE `v2019_jon_gaizka`;


DROP TABLE IF EXISTS `categoria`;
CREATE TABLE IF NOT EXISTS `categoria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

DELETE FROM `categoria`;

INSERT INTO `categoria` (`id`, `nombre`) VALUES
	(2, 'bromas'),
	(1, 'musica'),
	(15, 'nombre0'),
	(16, 'nombre1'),
	(17, 'nombre2'),
	(18, 'nombre3'),
	(19, 'nombre4'),
	(3, 'sustos');

DROP TABLE IF EXISTS `rol`;
CREATE TABLE IF NOT EXISTS `rol` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='1: Administrador	\n2: usuario';


DELETE FROM `rol`;

INSERT INTO `rol` (`id`, `nombre`) VALUES
	(1, 'administrador'),
	(2, 'usuario');

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE IF NOT EXISTS `usuario` (
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;


DELETE FROM `usuario`;

INSERT INTO `usuario` (`id`, `nombre`, `contrasenya`, `id_rol`, `fecha_creacion`, `fecha_eliminacion`) VALUES
	(2, 'pepe', 'pepe', 2, '2019-09-12 08:40:05', NULL),
	(3, 'soso', 'soso', 2, '2019-09-12 08:40:05', NULL),
	(4, 'Sr Burn', '123', 1, '2019-09-12 08:56:26', NULL),
	(5, 'eliminado', '123', 2, '2019-09-11 08:56:53', '2019-09-13 10:50:55');

DROP TABLE IF EXISTS `video`;
CREATE TABLE IF NOT EXISTS `video` (
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;


DELETE FROM `video`;

INSERT INTO `video` (`id`, `nombre`, `codigo`, `usuario_id`, `categoria_id`) VALUES
	(3, 'Red Hot Chili Peppers - Otherside', 'rn_YodiJO6k', 2, 1),
	(4, 'EL FARY -EL TORITO GUAPO', 'NFkI-zxZlHo', 3, 2),
	(5, 'No te olvides de poner el Where en el Delete From', 'i_cVJgIz_Cs', 5, 1);


DROP TABLE IF EXISTS `likes`;
CREATE TABLE IF NOT EXISTS `likes` (
  `usuario_id` int(11) NOT NULL,
  `video_id` int(11) NOT NULL,
  `fecha` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`usuario_id`,`video_id`),
  KEY `fk_usuario_has_video_video1_idx` (`video_id`),
  KEY `fk_usuario_has_video_usuario1_idx` (`usuario_id`),
  CONSTRAINT `fk_usuario_likes_video` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_video_likes_usuario` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DELETE FROM `likes`;

INSERT INTO `likes` (`usuario_id`, `video_id`, `fecha`) VALUES
	(2, 3, '2019-09-05 10:09:02'),
	(2, 4, '2019-09-05 10:09:02');

