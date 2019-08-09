CREATE TABLE IF NOT EXISTS `accesos`
(
    `id`    TINYINT(3) UNSIGNED NOT NULL AUTO_INCREMENT,
    `nivel` VARCHAR(24)         NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `nivel` (`nivel`)
);

CREATE TABLE IF NOT EXISTS `usuarios`
(
    `id`          TINYINT(3) UNSIGNED NOT NULL AUTO_INCREMENT,
    `username`    CHAR(40)            NOT NULL,
    `pass`        CHAR(41)            NOT NULL,
    `nombre`      VARCHAR(50)         NOT NULL,
    `telefono`    VARCHAR(20)         NULL     DEFAULT NULL,
    `email`       VARCHAR(50)         NULL     DEFAULT NULL,
    `direccion`   VARCHAR(50)         NULL     DEFAULT NULL,
    `descripcion` VARCHAR(50)         NULL     DEFAULT NULL,
    `idAcceso`    TINYINT(3) UNSIGNED NOT NULL,
    `activo`      TINYINT(1)          NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    INDEX `idAcceso` (`idAcceso`),
    CONSTRAINT `idAcceso` FOREIGN KEY (`idAcceso`) REFERENCES `accesos` (`id`)
);

CREATE TABLE IF NOT EXISTS `proveedores`
(
    `id`          SMALLINT(5) UNSIGNED NOT NULL AUTO_INCREMENT,
    `nif`         CHAR(10)             NOT NULL,
    `nombre`      VARCHAR(50)          NOT NULL,
    `telefono`    VARCHAR(20)          NULL     DEFAULT NULL,
    `email`       VARCHAR(50)          NULL     DEFAULT NULL,
    `direccion`   VARCHAR(50)          NULL     DEFAULT NULL,
    `descripcion` VARCHAR(50)          NULL     DEFAULT NULL,
    `activo`      TINYINT(1)           NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `nif` (`nif`)
);

CREATE TABLE IF NOT EXISTS `socios`
(
    `id`          SMALLINT(5) UNSIGNED NOT NULL AUTO_INCREMENT,
    `dni`         CHAR(10)             NOT NULL,
    `nombre`      VARCHAR(50)          NOT NULL,
    `telefono`    VARCHAR(20)          NULL     DEFAULT NULL,
    `direccion`   VARCHAR(50)          NULL     DEFAULT NULL,
    `descripcion` VARCHAR(127)         NULL     DEFAULT NULL,
    `fecha_in`    DATETIME             NOT NULL DEFAULT current_timestamp(),
    `activo`      TINYINT(1)           NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `dni` (`dni`)
);

CREATE TABLE IF NOT EXISTS `sedes`
(
    `id`        TINYINT(3) UNSIGNED NOT NULL AUTO_INCREMENT,
    `nombre`    VARCHAR(50)         NOT NULL,
    `telefono`  VARCHAR(50)         NOT NULL,
    `direccion` VARCHAR(100)        NOT NULL,
    `activo`    TINYINT(1)          NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `cajas`
(
    `id`     SMALLINT(5) UNSIGNED NOT NULL AUTO_INCREMENT,
    `idSede` TINYINT(3) UNSIGNED  NOT NULL,
    `nombre` VARCHAR(50)          NOT NULL,
    `activo` TINYINT(1)           NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    INDEX `FK_cajas_sedes` (`idSede`),
    CONSTRAINT `FK_cajas_sedes` FOREIGN KEY (`idSede`) REFERENCES `sedes` (`id`)
);

CREATE TABLE IF NOT EXISTS `zs`
(
    `id`       INT(10) UNSIGNED     NOT NULL AUTO_INCREMENT,
    `idCaja`   SMALLINT(5) UNSIGNED NOT NULL,
    `apertura` DATETIME             NOT NULL,
    `cierre`   DATETIME             NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK_zs_cajas` (`idCaja`),
    CONSTRAINT `FK_zs_cajas` FOREIGN KEY (`idCaja`) REFERENCES `cajas` (`id`)
);

CREATE TABLE IF NOT EXISTS `categorias`
(
    `id`     TINYINT(3) UNSIGNED NOT NULL AUTO_INCREMENT,
    `nombre` VARCHAR(20)         NOT NULL,
    `activo` TINYINT(1)          NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `productos`
(
    `id`           SMALLINT(5) UNSIGNED NOT NULL AUTO_INCREMENT,
    `nombre`       VARCHAR(50)          NOT NULL,
    `descripcion`  VARCHAR(100)         NULL     DEFAULT NULL,
    `precio_venta` INT(6)               NOT NULL,
    `iva`          TINYINT(3) UNSIGNED  NOT NULL DEFAULT 0,
    `idCategoria`  TINYINT(3) UNSIGNED  NOT NULL,
    `activo`       TINYINT(1)           NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    INDEX `FK_productos_categorias` (`idCategoria`),
    CONSTRAINT `FK_productos_categorias` FOREIGN KEY (`idCategoria`) REFERENCES `categorias` (`id`)
);

CREATE TABLE IF NOT EXISTS `compras`
(
    `id`          MEDIUMINT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
    `idUsuario`   TINYINT(3) UNSIGNED   NOT NULL,
    `idSede`      TINYINT(3) UNSIGNED   NOT NULL,
    `idProveedor` SMALLINT(5) UNSIGNED  NOT NULL,
    `fechahora`   DATETIME              NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`id`),
    INDEX `FK_compras_usuarios` (`idUsuario`),
    INDEX `FK_compras_proveedores` (`idProveedor`),
    INDEX `FK_compras_sedes` (`idSede`),
    CONSTRAINT `FK_compras_proveedores` FOREIGN KEY (`idProveedor`) REFERENCES `proveedores` (`id`),
    CONSTRAINT `FK_compras_sedes` FOREIGN KEY (`idSede`) REFERENCES `sedes` (`id`),
    CONSTRAINT `FK_compras_usuarios` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`id`)
);

CREATE TABLE IF NOT EXISTS `comprados`
(
    `id`            INT(10) UNSIGNED      NOT NULL AUTO_INCREMENT,
    `idCompra`      MEDIUMINT(8) UNSIGNED NOT NULL,
    `idProducto`    SMALLINT(5) UNSIGNED  NOT NULL,
    `cantidad`      INT(6) UNSIGNED       NOT NULL,
    `precio_unidad` INT(6) UNSIGNED       NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK_comprados_productos` (`idProducto`),
    INDEX `FK_comprados_compras` (`idCompra`),
    CONSTRAINT `FK_comprados_compras` FOREIGN KEY (`idCompra`) REFERENCES `compras` (`id`),
    CONSTRAINT `FK_comprados_productos` FOREIGN KEY (`idProducto`) REFERENCES `productos` (`id`)
);

CREATE TABLE IF NOT EXISTS `ventas`
(
    `id`        MEDIUMINT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
    `idUsuario` TINYINT(3) UNSIGNED   NOT NULL,
    `idCaja`    SMALLINT(5) UNSIGNED  NOT NULL,
    `idSocio`   SMALLINT(5) UNSIGNED  NOT NULL,
    `fechahora` DATETIME              NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`id`),
    INDEX `FK_compras_usuarios` (`idUsuario`),
    INDEX `FK_compras_proveedores` (`idSocio`),
    INDEX `FK_compras_sedes` (`idCaja`),
    CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`idSocio`) REFERENCES `socios` (`id`),
    CONSTRAINT `ventas_ibfk_2` FOREIGN KEY (`idCaja`) REFERENCES `cajas` (`id`),
    CONSTRAINT `ventas_ibfk_3` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`id`)
);

CREATE TABLE IF NOT EXISTS `vendidos`
(
    `id`            INT(10) UNSIGNED      NOT NULL AUTO_INCREMENT,
    `idVenta`       MEDIUMINT(8) UNSIGNED NOT NULL,
    `idProducto`    SMALLINT(5) UNSIGNED  NOT NULL,
    `cantidad`      INT(6) UNSIGNED       NOT NULL,
    `precio_unidad` INT(6) UNSIGNED       NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK_vendidos_productos` (`idProducto`),
    INDEX `FK_vendidos_ventas` (`idVenta`),
    CONSTRAINT `FK_vendidos_productos` FOREIGN KEY (`idProducto`) REFERENCES `productos` (`id`),
    CONSTRAINT `FK_vendidos_ventas` FOREIGN KEY (`idVenta`) REFERENCES `ventas` (`id`)
);

CREATE TABLE IF NOT EXISTS `transferencias`
(
    `id`            INT(11) UNSIGNED     NOT NULL AUTO_INCREMENT,
    `idUsuario`     TINYINT(3) UNSIGNED  NOT NULL,
    `idSedeOrigen`  TINYINT(3) UNSIGNED  NOT NULL,
    `idSedeDestino` TINYINT(3) UNSIGNED  NOT NULL,
    `idProducto`    SMALLINT(6) UNSIGNED NOT NULL,
    `Cantidad`      INT(6) UNSIGNED      NOT NULL,
    `fechahora`     DATETIME             NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK_transferencias_sedes` (`idSedeOrigen`),
    INDEX `FK_transferencias_sedes_2` (`idSedeDestino`),
    INDEX `FK_transferencias_productos` (`idProducto`),
    INDEX `FK_transferencias_usuarios` (`idUsuario`),
    CONSTRAINT `FK_transferencias_productos` FOREIGN KEY (`idProducto`) REFERENCES `productos` (`id`),
    CONSTRAINT `FK_transferencias_sedes` FOREIGN KEY (`idSedeOrigen`) REFERENCES `sedes` (`id`),
    CONSTRAINT `FK_transferencias_sedes_2` FOREIGN KEY (`idSedeDestino`) REFERENCES `sedes` (`id`),
    CONSTRAINT `FK_transferencias_usuarios` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`id`)
)
