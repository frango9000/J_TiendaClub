create view stock as
SELECT *, entrada + tentrada - salida - tsalida AS total
FROM (
         SELECT s.id                                                                     as idSed,
                p.id                                                                     as idProd,
                COALESCE((SELECT SUM(cantidad)
                          FROM comprados c
                          WHERE c.idProducto = idProd
                            AND idCompra IN
                                (SELECT id FROM compras WHERE idSede = idSed)), 0)       as entrada,

                COALESCE((SELECT SUM(cantidad)
                          FROM vendidos v
                          WHERE v.idProducto = idProd
                            AND idVenta IN
                                (SELECT id
                                 FROM ventas
                                 WHERE idCaja IN
                                       (SELECT id FROM cajas WHERE idSede = idSed))), 0) as salida,

                COALESCE((SELECT SUM(cantidad)
                          FROM transferencias t
                          WHERE idProducto = idProd
                            AND idSedeDestino = idSed), 0)                               AS tentrada,

                COALESCE(
                        (SELECT SUM(cantidad) FROM transferencias t WHERE idProducto = idProd AND idSedeOrigen = idSed),
                        0)                                                               AS tsalida

         FROM productos p
                  JOIN sedes s
     ) AS sub
