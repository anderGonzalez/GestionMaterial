DROP DATABASE IF EXISTS material;
CREATE DATABASE material;
USE material;

CREATE TABLE tipousuario
(
 idTipoUsuario 		INT AUTO_INCREMENT,
 tipousuario		VARCHAR(30) NOT NULL,
 CONSTRAINT pk_tipousuario PRIMARY KEY (idTipoUsuario)
);

INSERT INTO tipousuario VALUES (default, 'Administrador');
INSERT INTO tipousuario VALUES (default, 'Usuario');

CREATE TABLE persona 
(
 dni			INT AUTO_INCREMENT,
 nombre			VARCHAR(30) NOT NULL,
 password		VARCHAR(64) NOT NULL,
 userName		VARCHAR(35) NOT NULL,
 idTipoUsuario	INT NOT NULL,
 CONSTRAINT pk_persona PRIMARY KEY (dni),
 CONSTRAINT fk_persona_tipousuario FOREIGN KEY (idTipoUsuario) REFERENCES tipousuario(idTipoUsuario)
);

INSERT INTO persona VALUES (default,'Monse','usansolo','mereno',2);
INSERT INTO persona VALUES (default,'Txema','gasteiz','txperez',2);
INSERT INTO persona VALUES (default,'Urtzi','arrasate','umarkiegi',2);
INSERT INTO persona VALUES (default,'Xabier','arrasate','xelkorobarrutia',1);


CREATE TABLE recurso 
(
 idRecurso			INT AUTO_INCREMENT,
 nombre				VARCHAR(30) NOT NULL,
 descripcion		VARCHAR(60),
 ubicacion			VARCHAR(30) NOT NULL,
 dniResponsable		INT NOT NULL,
 CONSTRAINT pk_recurso PRIMARY KEY (idRecurso),
 CONSTRAINT fk_recurso_persona FOREIGN KEY (dniResponsable) REFERENCES persona(dni)
);

INSERT INTO recurso VALUES (default,'Portatil-1','Portatil Depto. Nº1','Secretaria',1);
INSERT INTO recurso VALUES (default,'Portatil-2','Portatil Depto. Nº2','Secretaria',1);
INSERT INTO recurso VALUES (default,'Telf-1','Smartphone Nº1','Secretaria',2);
INSERT INTO recurso VALUES (default,'Modem3G-1','Modem USB 3G Nº1','Secretaria',3);

CREATE TABLE reserva
(
 idReserva			INT AUTO_INCREMENT,
 fechaInicio		DATETIME NOT NULL,
 fechaFin			DATETIME NOT NULL,
 urgencia			INT,
 dniPeticionario	INT NOT NULL,
 idRecurso			INT NOT NULL,
 CONSTRAINT pk_reserva PRIMARY KEY (idReserva),
 CONSTRAINT fk_reserva_persona FOREIGN KEY (dniPeticionario) REFERENCES persona(dni),
 CONSTRAINT fk_reserva_recurso FOREIGN KEY (idRecurso) REFERENCES recurso(idRecurso)
);

INSERT INTO reserva  VALUES (default, '2016-02-08', '2016-02-10', 1, 1, 1);
INSERT INTO reserva  VALUES (default, '2016-02-15', '2016-02-19', 1, 1, 1);
INSERT INTO reserva  VALUES (default, '2016-02-15', '2016-02-17', 2, 2, 3);
INSERT INTO reserva  VALUES (default, '2016-02-15', '2016-02-18', 2, 3, 2);



CREATE TABLE prestamo
(
 idRecurso			INT NOT NULL,
 dniPrestatario		INT NOT NULL,
 fechaInicio		DATETIME NOT NULL,
 fechaFin			DATETIME NOT NULL,
 fechaDevolucion 	DATETIME NOT NULL,
 fechaUltimaNotificacion 	DATETIME,
 CONSTRAINT pk_prestamo PRIMARY KEY (idRecurso, fechaInicio, dniPrestatario),
 CONSTRAINT fk_prestamo_persona FOREIGN KEY (dniPrestatario) REFERENCES persona(dni),
 CONSTRAINT fk_prestamo_recurso FOREIGN KEY (idRecurso) REFERENCES recurso(idRecurso)
);

INSERT INTO prestamo  VALUES (default, '2016-02-08', '2016-02-10', 1, 1);
INSERT INTO prestamo  VALUES (default, '2016-02-15', '2016-02-18', 1, 1);
INSERT INTO prestamo  VALUES (default, '2016-02-15', '2016-02-17', 2, 3);
