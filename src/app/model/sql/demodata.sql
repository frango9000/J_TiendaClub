-- --------------------------------------------------------
-- Host:                         192.168.1.196
-- Server version:               10.1.40-MariaDB-0ubuntu0.18.04.1 - Ubuntu 18.04
-- Server OS:                    debian-linux-gnu
-- HeidiSQL Version:             10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping data for table mv.accesos: ~4 rows (approximately)
/*!40000 ALTER TABLE `accesos`
    DISABLE KEYS */;
INSERT INTO `accesos` (`id`, `nivel`)
VALUES (2, 'admin'),
       (4, 'audit'),
       (1, 'root'),
       (3, 'vendedor');
/*!40000 ALTER TABLE `accesos`
    ENABLE KEYS */;

-- Dumping data for table mv.cajas: ~4 rows (approximately)
/*!40000 ALTER TABLE `cajas`
    DISABLE KEYS */;
INSERT INTO `cajas` (`id`, `idSede`, `nombre`)
VALUES (1, 1, 'Principe 1'),
       (2, 1, 'Principe 2'),
       (3, 2, 'Balaidos 1'),
       (4, 2, 'Balaidos 2');
/*!40000 ALTER TABLE `cajas`
    ENABLE KEYS */;

-- Dumping data for table mv.categorias: ~3 rows (approximately)
/*!40000 ALTER TABLE `categorias`
    DISABLE KEYS */;
INSERT INTO `categorias` (`id`, `nombre`)
VALUES (1, 'Refrescos'),
       (2, 'Cervezas'),
       (3, 'Mecheros');
/*!40000 ALTER TABLE `categorias`
    ENABLE KEYS */;

-- Dumping data for table mv.comprados: ~7 rows (approximately)
/*!40000 ALTER TABLE `comprados`
    DISABLE KEYS */;
INSERT INTO `comprados` (`id`, `idCompra`, `idProducto`, `cantidad`, `precio_unidad`)
VALUES (1, 1, 1, 10, 500),
       (2, 1, 7, 5, 500),
       (3, 2, 4, 10, 500),
       (4, 3, 6, 6, 1000),
       (5, 3, 5, 12, 500),
       (6, 2, 2, 8, 500),
       (8, 4, 6, 10, 1000);
/*!40000 ALTER TABLE `comprados`
    ENABLE KEYS */;

-- Dumping data for table mv.compras: ~4 rows (approximately)
/*!40000 ALTER TABLE `compras`
    DISABLE KEYS */;
INSERT INTO `compras` (`id`, `idUsuario`, `idSede`, `idProveedor`, `fechahora`)
VALUES (1, 3, 1, 1, '2019-07-28 15:51:18'),
       (2, 2, 1, 2, '2019-07-28 15:52:10'),
       (3, 2, 1, 3, '2019-07-28 15:52:21'),
       (4, 2, 1, 3, '2019-07-28 16:02:32');
/*!40000 ALTER TABLE `compras`
    ENABLE KEYS */;

-- Dumping data for table mv.productos: ~8 rows (approximately)
/*!40000 ALTER TABLE `productos`
    DISABLE KEYS */;
INSERT INTO `productos` (`id`, `nombre`, `descripcion`, `precio_venta`, `iva`, `idCategoria`)
VALUES (1, 'Coca Cola', NULL, 150, 0, 1),
       (2, 'Estrella 0', NULL, 200, 0, 2),
       (3, 'Coca Cola Zero', NULL, 150, 0, 1),
       (4, 'Estrella', NULL, 200, 0, 2),
       (5, 'Mechero S', NULL, 100, 0, 3),
       (6, 'Mechero M', NULL, 150, 0, 3),
       (7, 'Fanta', NULL, 150, 0, 1),
       (8, 'Mechero Metalico', NULL, 250, 0, 3);
/*!40000 ALTER TABLE `productos`
    ENABLE KEYS */;

-- Dumping data for table mv.proveedores: ~3 rows (approximately)
/*!40000 ALTER TABLE `proveedores`
    DISABLE KEYS */;
INSERT INTO `proveedores` (`id`, `nif`, `nombre`, `telefono`, `email`, `direccion`, `descripcion`)
VALUES (1, '1', 'Cocacola', NULL, NULL, NULL, NULL),
       (2, '2', 'Clipper', NULL, NULL, NULL, NULL),
       (3, '3', 'Estrella Galicia', NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `proveedores`
    ENABLE KEYS */;

-- Dumping data for table mv.sedes: ~2 rows (approximately)
/*!40000 ALTER TABLE `sedes`
    DISABLE KEYS */;
INSERT INTO `sedes` (`id`, `nombre`, `telefono`, `direccion`)
VALUES (1, 'Principe', '', ''),
       (2, 'Balaidos', '', '');
/*!40000 ALTER TABLE `sedes`
    ENABLE KEYS */;

-- Dumping data for table mv.socios: ~5 rows (approximately)
/*!40000 ALTER TABLE `socios`
    DISABLE KEYS */;
INSERT INTO `socios` (`id`, `dni`, `nombre`, `telefono`, `direccion`, `descripcion`, `fecha_in`, `fecha_active`,
                      `fecha_inactive`)
VALUES (1, '1', 'socio 1', NULL, NULL, NULL, '2019-07-28 15:42:57', '2019-07-28 15:42:57', NULL),
       (2, '2', 'socio 2', NULL, NULL, NULL, '2019-07-28 15:43:21', '2019-07-28 15:43:21', NULL),
       (3, '3', 'Socio 3', NULL, NULL, NULL, '2019-07-28 15:43:23', '2019-07-28 15:43:23', NULL),
       (4, '4', 'Socio 4', NULL, NULL, NULL, '2019-07-28 15:43:35', '2019-07-28 15:43:35', NULL),
       (5, '5', 'Socio 5', NULL, NULL, NULL, '2019-07-28 15:43:38', '2019-07-28 15:43:38', NULL);
/*!40000 ALTER TABLE `socios`
    ENABLE KEYS */;

-- Dumping data for table mv.stock: ~0 rows (approximately)
/*!40000 ALTER TABLE `stock`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `stock`
    ENABLE KEYS */;

-- Dumping data for table mv.usuarios: ~4 rows (approximately)
/*!40000 ALTER TABLE `usuarios`
    DISABLE KEYS */;
INSERT INTO `usuarios` (`id`, `user`, `pass`, `nombre`, `telefono`, `email`, `direccion`, `descripcion`, `idAcceso`)
VALUES (1, 'root', 'root', 'root', NULL, NULL, NULL, NULL, 1),
       (2, 'admin', 'admin', 'admin', NULL, NULL, NULL, NULL, 2),
       (3, 'vendedor', 'vendedor', 'vendedor', NULL, NULL, NULL, NULL, 3),
       (4, 'audit', 'audit', 'audit', NULL, NULL, NULL, NULL, 4);
/*!40000 ALTER TABLE `usuarios`
    ENABLE KEYS */;

-- Dumping data for table mv.vendidos: ~6 rows (approximately)
/*!40000 ALTER TABLE `vendidos`
    DISABLE KEYS */;
INSERT INTO `vendidos` (`id`, `idVenta`, `idProducto`, `cantidad`, `precio_unidad`)
VALUES (1, 1, 1, 1, 150),
       (2, 1, 5, 2, 100),
       (3, 2, 8, 1, 1000),
       (4, 3, 4, 2, 150),
       (5, 3, 6, 3, 200),
       (6, 5, 4, 4, 150);
/*!40000 ALTER TABLE `vendidos`
    ENABLE KEYS */;

-- Dumping data for table mv.ventas: ~4 rows (approximately)
/*!40000 ALTER TABLE `ventas`
    DISABLE KEYS */;
INSERT INTO `ventas` (`id`, `idUsuario`, `idCaja`, `idSocio`, `fechahora`)
VALUES (1, 3, 1, 1, '2019-07-28 15:56:32'),
       (2, 3, 1, 2, '2019-07-28 15:56:46'),
       (3, 2, 2, 3, '2019-07-28 15:56:57'),
       (5, 3, 1, 4, '2019-07-28 16:03:30');
/*!40000 ALTER TABLE `ventas`
    ENABLE KEYS */;

-- Dumping data for table mv.zs: ~2 rows (approximately)
/*!40000 ALTER TABLE `zs`
    DISABLE KEYS */;
INSERT INTO `zs` (`id`, `idCaja`, `apertura`, `cierre`)
VALUES (1, 1, '2019-07-27 12:46:24', '2019-07-27 17:46:33'),
       (2, 2, '2019-07-27 12:46:24', '2019-07-27 17:46:33');
/*!40000 ALTER TABLE `zs`
    ENABLE KEYS */;

/*!40101 SET SQL_MODE = IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS = IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
