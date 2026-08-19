-- DEV ONLY: borra todos los datos de la app y reinicia los IDs.
-- Pensado para ejecutar en phpMyAdmin sobre la base drover_grafica2.

SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM venta_detalle_insumo_extra;
DELETE FROM venta_detalle;
DELETE FROM pagos;
DELETE FROM pagos_comisiones;
DELETE FROM pagos_sueldos;
DELETE FROM movimientos_caja;
DELETE FROM trabajos_tercerizados;
DELETE FROM envios;
DELETE FROM ventas;

DELETE FROM presupuesto_detalle;
DELETE FROM historial_presupuestos;
DELETE FROM presupuestos;

DELETE FROM comisiones_venta;
DELETE FROM comisiones;
DELETE FROM producto_insumo;
DELETE FROM productos;
DELETE FROM insumos;

DELETE FROM caja;
DELETE FROM cuentas;
DELETE FROM empresas_tercerizadas;
DELETE FROM clientes;
DELETE FROM usuarios;
DELETE FROM reportes;
DELETE FROM disenos;

ALTER TABLE venta_detalle_insumo_extra AUTO_INCREMENT = 1;
ALTER TABLE venta_detalle AUTO_INCREMENT = 1;
ALTER TABLE pagos AUTO_INCREMENT = 1;
ALTER TABLE pagos_comisiones AUTO_INCREMENT = 1;
ALTER TABLE pagos_sueldos AUTO_INCREMENT = 1;
ALTER TABLE movimientos_caja AUTO_INCREMENT = 1;
ALTER TABLE trabajos_tercerizados AUTO_INCREMENT = 1;
ALTER TABLE envios AUTO_INCREMENT = 1;
ALTER TABLE ventas AUTO_INCREMENT = 1;

ALTER TABLE presupuesto_detalle AUTO_INCREMENT = 1;
ALTER TABLE historial_presupuestos AUTO_INCREMENT = 1;
ALTER TABLE presupuestos AUTO_INCREMENT = 1;

ALTER TABLE comisiones_venta AUTO_INCREMENT = 1;
ALTER TABLE comisiones AUTO_INCREMENT = 1;
ALTER TABLE producto_insumo AUTO_INCREMENT = 1;
ALTER TABLE productos AUTO_INCREMENT = 1;
ALTER TABLE insumos AUTO_INCREMENT = 1;

ALTER TABLE caja AUTO_INCREMENT = 1;
ALTER TABLE cuentas AUTO_INCREMENT = 1;
ALTER TABLE empresas_tercerizadas AUTO_INCREMENT = 1;
ALTER TABLE clientes AUTO_INCREMENT = 1;
ALTER TABLE usuarios AUTO_INCREMENT = 1;
ALTER TABLE reportes AUTO_INCREMENT = 1;
ALTER TABLE disenos AUTO_INCREMENT = 1;

SET FOREIGN_KEY_CHECKS = 1;
