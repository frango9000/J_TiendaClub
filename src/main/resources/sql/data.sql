INSERT INTO `accesos`
VALUES (1, 'root'),
       (2, 'admin'),
       (3, 'administracion'),
       (4, 'ventas');

INSERT INTO `usuarios`
VALUES (1, 'root', 'root', 'root', null, null, null, null, 1, true),
       (2, 'admin', 'admin', 'admin', null, null, null, null, 2, true),
       (3, 'administracion', 'administracion', 'administracion', null, null, null, null, 3, true),
       (4, 'ventas', 'ventas', 'ventas', null, null, null, null, 4, true)