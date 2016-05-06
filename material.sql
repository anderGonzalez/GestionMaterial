DROP DATABASE IF EXISTS material;
CREATE DATABASE material;
USE material;

CREATE TABLE tipousuario
(
 idTipoUsuario 	    INT AUTO_INCREMENT,
 tipousuario	    VARCHAR(30) NOT NULL,
 CONSTRAINT pk_tipousuario PRIMARY KEY (idTipoUsuario)
);

INSERT INTO tipousuario VALUES (default, 'Administrador');
INSERT INTO tipousuario VALUES (default, 'Usuario');

CREATE TABLE persona 
(
 dni		    INT AUTO_INCREMENT,
 nombre		    VARCHAR(30) NOT NULL,
 password	    VARCHAR(64) NOT NULL,
 userName	    VARCHAR(35) NOT NULL,
 email		    VARCHAR(45) NOT NULL,
 idTipoUsuario  INT NOT NULL,
 CONSTRAINT pk_persona PRIMARY KEY (dni),
 CONSTRAINT fk_persona_tipousuario FOREIGN KEY (idTipoUsuario) REFERENCES tipousuario(idTipoUsuario)
);

INSERT INTO persona VALUES (default,'Oier','1234','osaizar','oier.saizar@alumni.mondragon.edu',2);
INSERT INTO persona VALUES (default,'Ander','1234','agonzalez','ander.gonzalez.t@alumni.mondragon.edu',2);
INSERT INTO persona VALUES (default,'Joanes','1234','jplazaola','joanes.plazaola@alumni.mondragon.edu',2);
INSERT INTO persona VALUES (default,'root','root','root','root@root.com',1);


CREATE TABLE recurso 
(
 idRecurso		    INT AUTO_INCREMENT,
 nombre			    VARCHAR(30) NOT NULL,
 descripcion	    VARCHAR(60),
 ubicacion		    VARCHAR(30) NOT NULL,
 dniResponsable	    INT NOT NULL,
 CONSTRAINT pk_recurso PRIMARY KEY (idRecurso),
 CONSTRAINT fk_recurso_persona FOREIGN KEY (dniResponsable) REFERENCES persona(dni)
);

INSERT INTO recurso VALUES (default,'Portatil-1','Portatil Depto. Nº1','Secretaria',1);
INSERT INTO recurso VALUES (default,'Portatil-2','Portatil Depto. Nº2','Secretaria',1);
INSERT INTO recurso VALUES (default,'Telf-1','Smartphone Nº1','Secretaria',2);
INSERT INTO recurso VALUES (default,'Modem3G-1','Modem USB 3G Nº1','Secretaria',3);

INSERT INTO recurso VALUES (default,'Impresora canon','Impresora Depto. Nº3','Secretaria',2);
INSERT INTO recurso VALUES (default,'Router cisco','Router 5864 para labs','Lab 1324',1);
INSERT INTO recurso VALUES (default,'Saco de patatas','Vacio, sin patatas. Para carreras','Secretaria',2);
INSERT INTO recurso VALUES (default,'Grapadora','Automatica, grapas no incluidas','Secretaria',3);

CREATE TABLE reserva
(
 idReserva		    INT AUTO_INCREMENT,
 fechaInicio        DATETIME NOT NULL,
 fechaFin	        DATETIME NOT NULL,
 urgencia		    INT,
 dniPeticionario    INT NOT NULL,
 idRecurso		    INT NOT NULL,
 CONSTRAINT pk_reserva PRIMARY KEY (idReserva),
 CONSTRAINT fk_reserva_persona FOREIGN KEY (dniPeticionario) REFERENCES persona(dni),
 CONSTRAINT fk_reserva_recurso FOREIGN KEY (idRecurso) REFERENCES recurso(idRecurso)
);


CREATE TABLE prestamo
(
 idPrestamo		    		INT AUTO_INCREMENT,
 idRecurso		     		INT NOT NULL,
 dniPrestatario	     		INT NOT NULL,
 fechaInicio	    		DATETIME NOT NULL,
 fechaFin		    		DATETIME NOT NULL,
 fechaDevolucion     		DATETIME,
 fechaUltimaNotificacion    DATETIME,
 CONSTRAINT pk_prestamo PRIMARY KEY (idPrestamo),
 CONSTRAINT fk_prestamo_persona FOREIGN KEY (dniPrestatario) REFERENCES persona(dni),
 CONSTRAINT fk_prestamo_recurso FOREIGN KEY (idRecurso) REFERENCES recurso(idRecurso)
);

CREATE TABLE penalizaciones
(
 dniPenalizado	    INT NOT NULL,
 idPrestamo		    INT NOT NULL,
 fechaInicio	    DATETIME NOT NULL,
 fechaFin		    DATETIME NOT NULL,
 CONSTRAINT pk_penalizaciones PRIMARY KEY (idPrestamo, fechaInicio, dniPenalizado),
 CONSTRAINT fk_penalizaciones_persona FOREIGN KEY (dniPenalizado) REFERENCES persona(dni),
 CONSTRAINT fk_penalizaciones_prestamo FOREIGN KEY (idPrestamo) REFERENCES prestamo(idPrestamo)
);