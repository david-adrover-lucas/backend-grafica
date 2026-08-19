# Arquitectura del Modelo de Base de Datos - Drover Gráfica
## Sistema de Control Interno (Estructura por Bloques Aislados)

Este documento contiene el diseño definitivo y pulido de las tablas para la aplicación de control interno, organizado por bloques de funcionalidad independientes. Las tablas se encuentran estructuradas y listas para la posterior fase de definición de claves foráneas y relaciones.

---

### 👥 BLOQUE 1: Personas y Entidades Activas
*Unifica la identidad básica y maneja la herencia para roles específicos con fechas de auditoría.*

#### 1. Tabla: `personas` (Tabla principal de identidad)
*   `id` (INT, Clave Primaria, Auto_Increment)
*   `nombre` (VARCHAR)
*   `apellido` (VARCHAR)
*   `rol` (ENUM: 'revendedor', 'cliente', 'vendedor', 'empleado')
*   `telefono` (VARCHAR)
*   `activo` (TINYINT/BIT - Control de baja lógica)

#### 2. Tabla: `vendedores` (Tabla Hija - Extensión)
*   `id` (INT, Clave Primaria - Espejo de `personas.id`)
*   `fecha_alta` (DATETIME)
*   `fecha_baja` (DATETIME, Permite Nulos)

#### 3. Tabla: `clientes` (Tabla Hija - Extensión)
*   `id` (INT, Clave Primaria - Espejo de `personas.id`)
*   `departamento` (VARCHAR - Ej: Guaymallén, Maipú, etc.)
*   `direccion_numero` (VARCHAR)

#### 4. Tabla: `revendedores` (Tabla Hija - Extensión)
*   `id` (INT, Clave Primaria - Espejo de `personas.id`)
*   `fecha_alta` (DATETIME)
*   `fecha_baja` (DATETIME, Permite Nulos)

#### 5. Tabla: `empleados` (Tabla Hija - Extensión con Credenciales/Sueldo)
*   `id` (INT, Clave Primaria - Espejo de `personas.id`)
*   `sueldo` (DECIMAL)
*   `fecha_cobro` (INT - Día del mes asignado para el pago)
*   `fecha_alta` (DATETIME)
*   `fecha_baja` (DATETIME, Permite Nulos)

---

### 🏢 BLOQUE 2: Proveedores y Talleres Externos
*Controla de forma independiente a quienes proveen insumos materiales de quienes realizan producción o trabajos terminados externos.*

#### 1. Tabla: `proveedores_tercerizados`
*   `id` (INT, Clave Primaria, Auto_Increment)
*   `nombre_empresa` (VARCHAR)
*   `telefono` (VARCHAR)
*   `email` (VARCHAR)
*   `red_social` (VARCHAR - Ej: Instagram o Sitio Web)
*   `tipo` (ENUM: 'stock', 'produccion') *Nota: 'stock' provee materia prima; 'produccion' realiza el trabajo final.*
*   `activo` (TINYINT/BIT - Control de baja lógica)

---

### 📦 BLOQUE 3: Productos, Insumos y Precios
*Estructura de costos dinámicos, recetas de fabricación y escalas mayoristas por metros (m²) o lineales.*

#### 1. Tabla: `insumos` (Materia prima y stock en taller)
*   `id` (INT, Clave Primaria, Auto_Increment)
*   `nombre` (VARCHAR - Ej: 'Lona Front', 'Argolla', 'Tinta')
*   `tipo` (ENUM: 'm2', 'lineal', 'unidad')
*   `costo_unitario` (DECIMAL)
*   `stock_actual` (DECIMAL)
*   `stock_minimo` (DECIMAL - Alerta para reposición de materiales)
*   `activo` (TINYINT/BIT)

#### 2. Tabla: `productos` (Catálogo general de venta)
*   `id` (INT, Clave Primaria, Auto_Increment)
*   `nombre` (VARCHAR - Ej: 'Banner Estándar', 'Folletería x1000')
*   `categoria` (VARCHAR)
*   `tipo_calculo` (ENUM: 'M2', 'UNIDAD')
*   `porcentaje_ganancia` (DECIMAL - Margen fijo por defecto)
*   `precio_base_manual` (DECIMAL, Permite Nulos - Para precios directos rápidos)
*   `maneja_escalas` (TINYINT/BIT - Define si busca precios variables por volumen)
*   `activo` (TINYINT/BIT)

#### 3. Tabla: `receta_producto` (Estructura de costos - Unión producto e insumo)
*   `id` (INT, Clave Primaria, Auto_Increment)
*   `cantidad_requerida` (DECIMAL)
*   `es_extra` (TINYINT/BIT - Define si es material obligatorio u opcional/adicional)

#### 4. Tabla: `escalas_producto` (Precios mayoristas y comisiones variables por tramos)
*   `id` (INT, Clave Primaria, Auto_Increment)
*   `desde_cantidad` (DECIMAL)
*   `hasta_cantidad` (DECIMAL)
*   `porcentaje_ganancia` (DECIMAL - Ajuste del margen según el volumen pedido)
*   `comision_vendedor` (DECIMAL - Porcentaje asignado al vendedor según escala)

---

### 📄 BLOQUE 4: Presupuestos y Ventas (Circuitos Independientes)
*Separa la etapa de negociación dinámica y productos a medida del circuito firme de ventas con liquidación de comisiones por ítem.*

#### PARTE A: Circuito de Presupuestos
#### 1. Tabla: `presupuestos` (Cabecera comercial flexible)
*   `id` (INT, Clave Primaria, Auto_Increment)
*   `nro_presupuesto` (VARCHAR/INT - Correlativo interno)
*   `fecha_emision` (DATETIME)
*   `fecha_vencimiento` (DATETIME - Límite de validez del precio en contexto inflacionario)
*   `descuento_general` (DECIMAL)
*   `monto_total` (DECIMAL)
*   `estado_presupuesto` (ENUM: 'BORRADOR', 'ENVIADO', 'APROBADO', 'RECHAZADO', 'VENCIDO')
*   `observaciones` (TEXT)

#### 2. Tabla: `detalles_presupuestos` (Renglones libres y modificables)
*   `id` (INT, Clave Primaria, Auto_Increment)
*   `descripcion_personalizada` (TEXT - Permite detallar productos no estandarizados creados al momento)
*   `cantidad` (DECIMAL)
*   `ancho` (DECIMAL, Permite Nulos)
*   `alto` (DECIMAL, Permite Nulos)
*   `precio_unitario_pactado` (DECIMAL)
*   `subtotal` (DECIMAL)

#### PARTE B: Circuito de Ventas y Producción
#### 1. Tabla: `ventas` (Cabecera firme de control interno)
*   `id` (INT, Clave Primaria, Auto_Increment)
*   `nro_nota_venta` (VARCHAR/INT - Número de control interno, sin datos fiscales de factura)
*   `fecha_venta` (DATETIME)
*   `estado_trabajo` (ENUM: 'EN_PRODUCCION', 'LISTO_ENTREGA', 'ENTREGADO', 'CANCELADO')
*   `estado_pago` (ENUM: 'IMPAGO', 'SENADO', 'PAGADO')
*   `monto_total` (DECIMAL)
*   `monto_senado` (DECIMAL - Pago inicial registrado para dar inicio a la orden)

#### 2. Tabla: `detalles_ventas` (Renglones e historial firme para auditoría de comisiones)
*   `id` (INT, Clave Primaria, Auto_Increment)
*   `cantidad` (DECIMAL)
*   `ancho` (DECIMAL, Permite Nulos)
*   `alto` (DECIMAL, Permite Nulos)
*   `precio_unitario_historico` (DECIMAL - Precio cobrado real en la fecha de la transacción)
*   `porcentaje_comision_producto` (DECIMAL - Porcentaje de comisión individual aplicado al ítem)
*   `monto_comision_calculado` (DECIMAL - Dinero exacto devengado por el vendedor en este renglón)
*   `subtotal` (DECIMAL)

---

### 💰 BLOQUE 5: Cajas, Cuentas y Movimientos de Dinero
*Centraliza saldos para futuras vinculaciones de API (Mercado Pago, Naranja X, etc.) y audita el origen/destino del flujo financiero.*

#### 1. Tabla: `cuentas_bancarias` (Disponibilidad y saldos unificados en tiempo real)
*   `id` (INT, Clave Primaria, Auto_Increment)
*   `nombre_cuenta` (VARCHAR - Ej: 'Caja Chica Efectivo', 'Mercado Pago Negocio', 'Naranja X')
*   `tipo_cuenta` (ENUM: 'EFECTIVO', 'BILLETERA_DIGITAL', 'BANCO')
*   `saldo_actual` (DECIMAL)
*   `api_external_id` (VARCHAR, Permite Nulos - Token/ID identificador para Webhooks e integraciones con pasarelas de pago)
*   `activo` (TINYINT/BIT)

#### 2. Tabla: `movimientos_dinero` (Libro diario de auditoría - Flujo de fondos)
*   `id` (INT, Clave Primaria, Auto_Increment)
*   `fecha_hora` (DATETIME)
*   `tipo_movimiento` (ENUM: 'INGRESO', 'EGRESO', 'TRANSFERENCIA_INTERNA')
*   `monto` (DECIMAL)
*   `concepto` (ENUM: 'SEÑA_CLIENTE', 'PAGO_FINAL_CLIENTE', 'PAGO_TERCERIZADO', 'COMPRA_INSUMOS', 'PAGO_SUELDO', 'GASTO_GENERAL', 'RETIRO_SOCIO')
*   `descripcion_ajuste` (TEXT - Detalle complementario del movimiento)
