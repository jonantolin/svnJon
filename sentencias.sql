USE v2019;

-- listar todos
-- SELECT id,nombre,contrasenya FROM usuario ORDER BY id DESC LIMIT 500;

-- busqueda por nombre
SELECT id,nombre,contrasenya FROM usuario WHERE nombre LIKE '%cas%' ORDER BY nombre ASC LIMIT 500;


-- eliminar un usuario por id
DELETE FROM usuario WHERE id = ?;

-- crear un usuario nuevo
INSERT INTO usuario ( nombre, contrasenya) VALUES ( ? , ?);

-- modificar un usuario
UPDATE usuario SET nombre= ?, contrasenya= ? WHERE id = ?;


-- numero de likes del Fary
-- SELECT COUNT(*) as 'numero_likes' FROM likes WHERE usuario_id = 4;

-- INNER JOIN EXPLICITA
-- mostrar los videos del usuario 'soso' por su id
SELECT 
	u.nombre as 'usuario',
    v.nombre as 'video',
    c.nombre as 'categoria'
FROM 
	usuario as u INNER JOIN video as v ON u.id = v.usuario_id 
    INNER JOIN categoria as c ON c.id = v.categoria_id 
    
WHERE
	u.id = 3;

-- INNER JOIN IMPLICITA	
-- mostrar los videos del usuario 'soso' por su id
SELECT 
	u.nombre as 'usuario',
    v.nombre as 'video',
    c.nombre as 'categoria'
FROM 
	usuario as u,
    video as v,
    categoria as c
   
WHERE
	u.id = v.usuario_id AND
    v.categoria_id = c.id AND
    u.id = 3;	
    
   
    
 -- Numero de likes que tiene cada categoria

SELECT c.nombre as nombre_categoria, 
(SELECT COUNT(*) FROM video as v, likes as l WHERE v.categoria_id = c.id AND v.id = l.video_id) as num_likes
FROM categoria as c;
	

-- Nombre de los departamentos donde todos los trabajadores cobren menos de 2000e

SELECT DISTINCT d.nombre
FROM departamento as d, empleado as e
WHERE d.id = e.departamento_id AND d.nombre NOT IN (SELECT d.nombre FROM departamento as d, empleado as e WHERE e.departamento_id = d.id AND e.salario > 2000 )
;

-- numero de empleados de cada departamento (hay 2 departamentos que NO tienen ningun empleado)

SELECT COUNT(empleado.nombre), d.nombre 
FROM empleado 
RIGHT JOIN departamento as d 
ON empleado.departamento_id = d.id 
GROUP BY d.nombre;

-- Trigger para meter los videos eliminados en una tabla auxiliar

CREATE DEFINER=`root`@`%` TRIGGER `TAD_videos` AFTER DELETE ON `video` FOR EACH ROW 
BEGIN

	INSERT INTO videos_eliminados(id_antiguo, nombre, codigo, usuarios_id, categoria_id) 
    VALUES (OLD.id, OLD.nombre, OLD.codigo, OLD.usuario_id, OLD.categoria_id);

END
	