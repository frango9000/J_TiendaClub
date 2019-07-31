create view if not exists mv.stock as
select `sub`.`idSed`                                                         AS `idSed`,
       `sub`.`idProd`                                                        AS `idProd`,
       `sub`.`entrada`                                                       AS `entrada`,
       `sub`.`salida`                                                        AS `salida`,
       `sub`.`tentrada`                                                      AS `tentrada`,
       `sub`.`tsalida`                                                       AS `tsalida`,
       `sub`.`entrada` + `sub`.`tentrada` - `sub`.`salida` - `sub`.`tsalida` AS `total`
from (select `s`.`id`                                           AS `idSed`,
             `p`.`id`                                           AS `idProd`,
             coalesce((select sum(`c`.`cantidad`)
                       from `mv`.`comprados` `c`
                       where `c`.`idProducto` = `idProd`
                         and `c`.`idCompra` in
                             (select `mv`.`compras`.`id` from `mv`.`compras` where `mv`.`compras`.`idSede` = `idSed`)),
                      0)                                        AS `entrada`,
             coalesce((select sum(`v`.`cantidad`)
                       from `mv`.`vendidos` `v`
                       where `v`.`idProducto` = `idProd`
                         and `v`.`idVenta` in (select `mv`.`ventas`.`id`
                                               from `mv`.`ventas`
                                               where `mv`.`ventas`.`idCaja` in (select `mv`.`cajas`.`id`
                                                                                from `mv`.`cajas`
                                                                                where `mv`.`cajas`.`idSede` = `idSed`))),
                      0)                                        AS `salida`,
             coalesce((select sum(`t`.`Cantidad`)
                       from `mv`.`transferencias` `t`
                       where `t`.`idProducto` = `idProd`
                         and `t`.`idSedeDestino` = `idSed`), 0) AS `tentrada`,
             coalesce((select sum(`t`.`Cantidad`)
                       from `mv`.`transferencias` `t`
                       where `t`.`idProducto` = `idProd`
                         and `t`.`idSedeOrigen` = `idSed`), 0)  AS `tsalida`
      from (`mv`.`productos` `p`
               join `mv`.`sedes` `s`)) `sub`;



create view monedero as
select `s`.`id`                                                                AS `id`,
       coalesce(`t1`.`ingresos`, 0)                                            AS `ingresos`,
       coalesce(`t2`.`egresos`, 0)                                             AS `egresos`,
       coalesce(coalesce(`t1`.`ingresos`, 0) - coalesce(`t2`.`egresos`, 0), 0) AS `saldo`
from ((`mv`.`socios` `s` left join (select `iv`.`idSocio`                                AS `idSocio`,
                                           sum(`iv2`.`cantidad` * `iv2`.`precio_unidad`) AS `ingresos`
                                    from (`mv`.`ventas` `iv`
                                             join `mv`.`vendidos` `iv2`)
                                    where `iv`.`id` = `iv2`.`idVenta`
                                      and `iv2`.`idProducto` = '4'
                                    group by `iv`.`idSocio`) `t1` on (`s`.`id` = `t1`.`idSocio`))
         left join (select `iv`.`idSocio` AS `idSocio`, sum(`iv2`.`cantidad` * `iv2`.`precio_unidad`) AS `egresos`
                    from (`mv`.`ventas` `iv`
                             join `mv`.`vendidos` `iv2`)
                    where `iv`.`id` = `iv2`.`idVenta`
                      and `iv2`.`idProducto` = '5'
                    group by `iv`.`idSocio`) `t2` on (`s`.`id` = `t2`.`idSocio`));


