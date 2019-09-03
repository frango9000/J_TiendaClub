module casteldao {
    requires java.sql;
    requires com.google.common;
    requires org.checkerframework.checker.qual;
    requires flogger;
    exports casteldao;
    exports casteldao.dao;
    exports casteldao.index;
    exports casteldao.index.core;
    exports casteldao.index.core.maps;
    exports casteldao.model;
}