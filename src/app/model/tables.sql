CREATE TABLE `accesos`
(
    `id`    TINYINT(3) UNSIGNED NOT NULL AUTO_INCREMENT,
    `nivel` VARCHAR(12)         NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `nivel` (`nivel`)
);

CREATE TABLE `usuarios`
(
    `id`          TINYINT(3) UNSIGNED NOT NULL AUTO_INCREMENT,
    `user`        CHAR(40)            NOT NULL,
    `pass`        CHAR(41)            NOT NULL,
    `nombre`      VARCHAR(50)         NOT NULL,
    `telefono`    VARCHAR(20)         NULL DEFAULT NULL,
    `email`       VARCHAR(50)         NULL DEFAULT NULL,
    `direccion`   VARCHAR(50)         NULL DEFAULT NULL,
    `descripcion` VARCHAR(50)         NULL DEFAULT NULL,
    `idAcceso`    TINYINT(3) UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `idAcceso` (`idAcceso`),
    CONSTRAINT `idAcceso` FOREIGN KEY (`idAcceso`) REFERENCES `accesos` (`id`)
);

CREATE TABLE `proveedores`
(
    `id`          SMALLINT(5) UNSIGNED NOT NULL AUTO_INCREMENT,
    `nif`         CHAR(10)             NOT NULL,
    `nombre`      VARCHAR(50)          NOT NULL,
    `telefono`    VARCHAR(20)          NULL DEFAULT NULL,
    `email`       VARCHAR(50)          NULL DEFAULT NULL,
    `direccion`   VARCHAR(50)          NULL DEFAULT NULL,
    `descripcion` VARCHAR(50)          NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `nif` (`nif`)
);

CREATE TABLE `socios`
(
    `id`             SMALLINT(5) UNSIGNED NOT NULL AUTO_INCREMENT,
    `dni`            CHAR(10)             NOT NULL,
    `nombre`         VARCHAR(50)          NOT NULL,
    `telefono`       VARCHAR(20)          NULL     DEFAULT NULL,
    `direccion`      VARCHAR(50)          NULL     DEFAULT NULL,
    `descripcion`    VARCHAR(127)         NULL     DEFAULT NULL,
    `fecha_in`       DATETIME             NOT NULL DEFAULT current_timestamp(),
    `fecha_active`   DATETIME             NULL     DEFAULT current_timestamp(),
    `fecha_inactive` DATETIME             NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `dni` (`dni`)
);

CREATE TABLE `sedes`
(
    `id`        TINYINT(3) UNSIGNED NOT NULL AUTO_INCREMENT,
    `nombre`    VARCHAR(50)         NOT NULL,
    `telefono`  VARCHAR(50)         NOT NULL,
    `direccion` VARCHAR(100)        NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `cajas`
(
    `id`     SMALLINT(5) UNSIGNED NOT NULL AUTO_INCREMENT,
    `idSede` TINYINT(3) UNSIGNED  NOT NULL,
    `nombre` VARCHAR(50)          NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK_cajas_sedes` (`idSede`),
    CONSTRAINT `FK_cajas_sedes` FOREIGN KEY (`idSede`) REFERENCES `sedes` (`id`)
);

CREATE TABLE `zs`
(
    `id`       INT(10) UNSIGNED     NOT NULL AUTO_INCREMENT,
    `idCaja`   SMALLINT(5) UNSIGNED NOT NULL,
    `apertura` DATETIME             NOT NULL,
    `cierre`   DATETIME             NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `categorias`
(
    `id`     TINYINT(3) UNSIGNED NOT NULL AUTO_INCREMENT,
    `nombre` VARCHAR(20)         NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `productos`
(
    `id`           SMALLINT(5) UNSIGNED NOT NULL AUTO_INCREMENT,
    `nombre`       VARCHAR(50)          NOT NULL,
    `descripcion`  VARCHAR(100)         NULL     DEFAULT NULL,
    `precio_venta` DECIMAL(6, 2)        NOT NULL,
    `iva`          TINYINT(3) UNSIGNED  NOT NULL DEFAULT 0,
    `idCategoria`  TINYINT(3) UNSIGNED  NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK_productos_categorias` (`idCategoria`),
    CONSTRAINT `FK_productos_categorias` FOREIGN KEY (`idCategoria`) REFERENCES `categorias` (`id`)
);

CREATE TABLE `compras`
(
    `id`          MEDIUMINT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
    `idUsuario`   TINYINT(3) UNSIGNED   NOT NULL,
    `idSede`      TINYINT(3) UNSIGNED   NOT NULL,
    `idProveedor` SMALLINT(5) UNSIGNED  NOT NULL,
    `fecha`       DATETIME              NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`id`),
    INDEX `FK_compras_usuarios` (`idUsuario`),
    INDEX `FK_compras_proveedores` (`idProveedor`),
    INDEX `FK_compras_sedes` (`idSede`),
    CONSTRAINT `FK_compras_proveedores` FOREIGN KEY (`idProveedor`) REFERENCES `proveedores` (`id`),
    CONSTRAINT `FK_compras_sedes` FOREIGN KEY (`idSede`) REFERENCES `sedes` (`id`),
    CONSTRAINT `FK_compras_usuarios` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`id`)
);

CREATE TABLE `comprados`
(
    `id`            INT(10) UNSIGNED       NOT NULL AUTO_INCREMENT,
    `idCompra`      MEDIUMINT(8) UNSIGNED  NOT NULL,
    `idProducto`    SMALLINT(5) UNSIGNED   NOT NULL,
    `cantidad`      DECIMAL(5, 1) UNSIGNED NOT NULL,
    `precio_unidad` DECIMAL(6, 2) UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK_comprados_productos` (`idProducto`),
    INDEX `FK_comprados_compras` (`idCompra`),
    CONSTRAINT `FK_comprados_compras` FOREIGN KEY (`idCompra`) REFERENCES `compras` (`id`),
    CONSTRAINT `FK_comprados_productos` FOREIGN KEY (`idProducto`) REFERENCES `productos` (`id`)
);

CREATE TABLE `ventas`
(
    `id`        MEDIUMINT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
    `idUsuario` TINYINT(3) UNSIGNED   NOT NULL,
    `idCaja`    SMALLINT(5) UNSIGNED  NOT NULL,
    `idSocio`   SMALLINT(5) UNSIGNED  NOT NULL,
    `fecha`     DATETIME              NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`id`),
    INDEX `FK_compras_usuarios` (`idUsuario`),
    INDEX `FK_compras_proveedores` (`idSocio`),
    INDEX `FK_compras_sedes` (`idCaja`),
    CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`idSocio`) REFERENCES `socios` (`id`),
    CONSTRAINT `ventas_ibfk_2` FOREIGN KEY (`idCaja`) REFERENCES `cajas` (`id`),
    CONSTRAINT `ventas_ibfk_3` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`id`)
);

CREATE TABLE `vendidos`
(
    `id`            INT(10) UNSIGNED       NOT NULL AUTO_INCREMENT,
    `idVenta`       MEDIUMINT(8) UNSIGNED  NOT NULL,
    `idProducto`    SMALLINT(5) UNSIGNED   NOT NULL,
    `cantidad`      DECIMAL(5, 1) UNSIGNED NOT NULL,
    `precio_unidad` DECIMAL(6, 2) UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK_vendidos_productos` (`idProducto`),
    INDEX `FK_vendidos_ventas` (`idVenta`),
    CONSTRAINT `FK_vendidos_productos` FOREIGN KEY (`idProducto`) REFERENCES `productos` (`id`),
    CONSTRAINT `FK_vendidos_ventas` FOREIGN KEY (`idVenta`) REFERENCES `ventas` (`id`)
);

CREATE TABLE `stock`
(
    `idSede`     TINYINT(3) UNSIGNED  NOT NULL,
    `idProducto` SMALLINT(5) UNSIGNED NOT NULL,
    `cantidad`   DECIMAL(5, 1)        NOT NULL,
    PRIMARY KEY (`idSede`, `idProducto`),
    INDEX `FK_stock_productos` (`idProducto`, `idSede`),
    CONSTRAINT `FK_stock_productos` FOREIGN KEY (`idProducto`) REFERENCES `productos` (`id`),
    CONSTRAINT `FK_stock_sedes` FOREIGN KEY (`idSede`) REFERENCES `sedes` (`id`)
);