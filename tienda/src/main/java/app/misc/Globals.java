/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.misc;

import java.util.logging.Level;

/**
 * @author NarF
 */
public interface Globals {

    boolean DEBUG = true;
    boolean SQL_DEBUG = false;
    boolean SQL_CONN = false;
    boolean VERBOSE_FACTORY = true;

    boolean QUICKSTART = false;

    Level LOG_LEVEL = Level.ALL;

    String DB_PREFIX = "tdc_";

    String ROOT_PATH = "src/main/java/tiendaclub/";

    boolean SAFE_UPDATE = true;//enforce objects to have a backup to fallback to in case update statement fails

    String CATALOG_MODEL =
        "accesos\n" + "cajas\n" + "categorias\n" + "comprados\n" + "compras\n" + "monedero\n" + "productos\n"
        + "proveedores\n" + "sedes\n" + "socios\n" + "stock\n" + "transferencias\n" + "usuarios\n"
        + "vendidos\n" + "ventas\n" + "zs\n";


}
