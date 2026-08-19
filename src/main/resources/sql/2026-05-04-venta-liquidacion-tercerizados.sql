-- Liquidación pago a empresas tercerizadas al pasar RETIRAR → PREPARACION
ALTER TABLE ventas
  ADD COLUMN tercerizados_liquidado TINYINT(1) NOT NULL DEFAULT 0 AFTER tiene_tercerizado,
  ADD COLUMN cuenta_pago_tercerizados_id INT NULL AFTER tercerizados_liquidado;

ALTER TABLE ventas
  ADD CONSTRAINT fk_venta_cuenta_tercerizados
  FOREIGN KEY (cuenta_pago_tercerizados_id) REFERENCES cuentas(id);
