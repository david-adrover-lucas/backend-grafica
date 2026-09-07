# Logica de negocio - Drover Grafica

Este documento explica la logica funcional del sistema de gestion de Drover Grafica. El objetivo es que cualquier persona que lo lea pueda entender como funciona el negocio, que reglas se deben cumplir y como se relacionan ventas, presupuestos, stock, caja, cuentas, empleados, revendedores, proveedores y trabajos tercerizados.

## 1. Vision general del sistema

Drover Grafica es un sistema para administrar una grafica/taller. El negocio trabaja con productos fabricados a partir de insumos, ventas a clientes finales, ventas a revendedores, presupuestos, compras de insumos, pagos, control de stock, comisiones, sueldos, proveedores y servicios tercerizados.

El sistema no debe ser solo un CRUD. Debe funcionar como un sistema de gestion integral donde cada accion importante genera consecuencias en otras areas:

- Una compra de insumos aumenta stock y registra salida de dinero.
- Una venta puede descontar stock cuando avanza a produccion/entrega.
- Un pago de cliente aumenta el saldo de una cuenta.
- Un trabajo tercerizado genera una deuda o pago a proveedor.
- Una venta entregada puede generar comisiones para empleados o vendedores.
- Los sueldos y comisiones generan deudas internas que luego se pagan desde una cuenta.

## 2. Personas y roles

La entidad central de personas representa a cualquier persona relacionada con el negocio. Cada persona tiene un rol.

Roles permitidos:

- `clientes`
- `revendedores`
- `vendedores`
- `empleados`

Cuando se crea una persona, se crea tambien su registro hijo segun el rol. Por ejemplo, si la persona tiene rol `vendedores`, tambien se crea un vendedor con el mismo ID.

Reglas:

- El telefono debe ser unico.
- Una persona puede estar activa o inactiva.
- Si se desactiva una persona, tambien se desactiva su registro hijo correspondiente.
- Una persona activa no deberia borrarse definitivamente.
- Los clientes y revendedores son externos al negocio.
- Los vendedores y empleados son internos al negocio.

## 3. Proveedores

Los proveedores representan empresas o personas externas que venden insumos o realizan trabajos tercerizados.

Tipos de proveedor:

- `stock`: proveedor de insumos.
- `tercerizado`: proveedor que realiza trabajos externos.

Reglas:

- El proveedor debe tener nombre y tipo.
- El telefono, si se carga, debe tener formato valido.
- El telefono no puede repetirse entre proveedores.
- El proveedor puede estar activo o inactivo.

## 4. Insumos

Los insumos son materiales usados para fabricar productos.

Datos principales:

- proveedor
- nombre
- unidad
- costo unitario
- stock actual
- stock minimo
- activo

Unidades permitidas:

- `m2`
- `lineal`
- `unidad`

Reglas:

- Todo insumo debe estar asociado a un proveedor.
- El costo unitario, stock actual y stock minimo no pueden ser negativos.
- El stock actual representa la existencia real disponible.
- El stock minimo sirve para detectar necesidad de reposicion.

## 5. Productos

Los productos son articulos o trabajos que la grafica vende. Cada producto se calcula a partir de una receta de insumos.

Datos principales:

- nombre
- descripcion
- unidad de venta
- costo actual
- porcentaje de ganancia
- precio de venta
- activo
- receta de insumos

Unidades de venta permitidas:

- `m2`
- `lineal`
- `unidad`
- `plancha`

Reglas:

- Un producto debe tener al menos un insumo en su receta.
- El costo actual se calcula sumando los costos de los insumos de la receta.
- El precio de venta se calcula con:

```text
precioVenta = costoActual * (1 + porcentajeGanancia / 100)
```

- Si se edita un producto y se carga un precio manual, el sistema calcula el porcentaje de ganancia resultante.
- No se permite vender un producto por debajo del costo de sus insumos.
- Para productos por `m2`, la receta esta pensada para 1 m2.
- Cuando se vende un producto por `m2`, el consumo real se multiplica por cantidad, ancho y alto.
- Para productos por `unidad`, `lineal` o `plancha`, el calculo usa cantidad y receta segun corresponda al producto.

## 6. Receta de producto

La receta de producto indica que insumos consume un producto.

Ejemplo:

```text
Producto: cartel vinilo
Unidad: m2
Receta:
- vinilo: 1 m2
- tinta: 0.05 unidad
- laminado: 1 m2
```

Si se venden 3 carteles de 2m x 1m:

```text
superficie total = cantidad * ancho * alto
superficie total = 3 * 2 * 1 = 6 m2
```

Entonces cada insumo de la receta se multiplica por 6.

## 7. Escalas de precio de producto

Las escalas de precio permiten modificar el porcentaje de ganancia segun la cantidad vendida.

Datos:

- producto
- cantidad desde
- cantidad hasta
- porcentaje de ganancia de escala

Uso:

- Aplica principalmente a clientes comunes.
- Sirve para que cantidades grandes tengan otro margen.
- Si no existe escala para la cantidad vendida, se usa el porcentaje de ganancia base del producto.

Reglas:

- `cantidadDesde` es obligatoria.
- `cantidadHasta` puede ser nula para representar rango abierto.
- `cantidadHasta` debe ser mayor que `cantidadDesde`.
- El porcentaje no puede ser negativo.

## 8. Revendedores

Los revendedores son clientes premium externos. No forman parte de la empresa.

Reglas:

- No cobran comision.
- No usan precio por escala.
- Cada revendedor puede tener precios especiales.
- El precio especial se define segun el caso y segun lo que pida.
- El precio especial no puede estar por debajo del costo del producto.

Cuando se vende a un revendedor, el sistema debe congelar el precio usado en el detalle de venta para que cambios futuros del producto no alteren ventas historicas.

## 9. Presupuestos

Un presupuesto es una cotizacion previa a una venta.

Datos principales:

- numero de presupuesto
- cliente o revendedor
- responsable
- fecha
- estado
- monto total
- observaciones
- detalles

Estados permitidos:

- `pendiente`
- `confirmado`
- `cancelado`

Reglas:

- El presupuesto debe pertenecer a un cliente o a un revendedor, pero nunca a ambos.
- Debe tener un responsable interno.
- Debe tener al menos un detalle.
- El total se calcula sumando los subtotales de sus detalles.
- Si el presupuesto se acepta/confirma, debe convertirse en venta.

Flujo esperado:

```text
presupuesto pendiente -> presupuesto confirmado -> venta generada
presupuesto pendiente -> presupuesto cancelado
```

## 10. Detalle de presupuesto

Cada detalle de presupuesto representa un producto cotizado.

Datos:

- presupuesto
- producto
- cantidad
- ancho
- alto
- costo unitario historico
- ganancia historica
- precio unitario
- subtotal

Reglas:

- El producto debe existir.
- La cantidad debe ser mayor a cero.
- Si el producto se vende por `m2`, ancho y alto son obligatorios.
- Al crear el detalle, se congelan los valores historicos del producto:
  costo, ganancia, precio unitario y subtotal.

Calculo para productos por `m2`:

```text
subtotal = cantidad * ancho * alto * precioUnitario
```

Calculo para otras unidades:

```text
subtotal = cantidad * precioUnitario
```

## 11. Ventas

La venta es el comprobante principal del negocio. Puede generarse desde un presupuesto confirmado o cargarse manualmente.

Datos principales:

- numero de venta
- cliente o revendedor
- responsable
- fecha
- estado de venta
- estado de pago
- monto total
- observaciones
- detalles
- stock descontado

La venta debe tener un cliente o un revendedor, pero nunca ambos.

El responsable debe ser una persona interna: vendedor o empleado.

## 12. Estados de venta

Estados de taller:

- `diseñar`
- `confirmar`
- `retirar a tercerizado`
- `entrega`
- `entregado`
- `posponer`

Interpretacion funcional:

- `diseñar`: el trabajo esta en etapa de diseno.
- `confirmar`: el trabajo fue confirmado para avanzar.
- `retirar a tercerizado`: el trabajo se envio o se debe retirar de una empresa tercerizada.
- `entrega`: el trabajo esta listo para entregar o en etapa final de entrega.
- `entregado`: el cliente o revendedor ya recibio el trabajo.
- `posponer`: el trabajo queda pausado temporalmente.

Regla clave:

El stock se descuenta cuando la venta pasa a estado `entrega`.

El descuento debe ejecutarse una sola vez. Para eso la venta debe tener una marca como:

```text
stockDescontado = true
```

Si la venta vuelve a pasar por `entrega`, no debe descontar stock otra vez.

## 13. Estados de pago de venta

Estados de pago:

- `pendiente`
- `señeado`
- `pagado`
- `deuda`

Interpretacion:

- `pendiente`: no entro dinero.
- `señeado`: entro una parte del dinero.
- `pagado`: se pago el total.
- `deuda`: el trabajo fue entregado o cerrado, pero queda saldo pendiente.

Reglas:

- Una venta puede avanzar a `entrega` aunque este pendiente de pago en casos especiales.
- Una venta `señeada` ya no se puede editar.
- Los pagos actualizan automaticamente el estado de pago.
- Si los pagos acumulados igualan el total, la venta queda `pagado`.
- Si los pagos acumulados son menores al total, la venta queda `señeado`.
- No se puede pagar mas que el total de la venta.

## 14. Detalle de venta

Cada detalle de venta representa un producto vendido.

Datos:

- venta
- producto
- cantidad
- ancho
- alto
- costo historico
- ganancia historica
- comision historica
- precio unitario historico
- subtotal

Reglas:

- El producto debe existir.
- La cantidad debe ser mayor a cero.
- Si el producto se vende por `m2`, ancho y alto son obligatorios.
- Al crear el detalle, se congelan costo, ganancia, comision, precio y subtotal.
- Para clientes comunes puede aplicar escala de precio.
- Para revendedores no aplica escala; se usa precio especial o precio definido en la venta.

Calculo para `m2`:

```text
subtotal = cantidad * ancho * alto * precioUnitarioHistorico
```

Calculo para otras unidades:

```text
subtotal = cantidad * precioUnitarioHistorico
```

## 15. Descuento de stock por venta

El stock se descuenta cuando la venta pasa a `entrega`.

Reglas:

- Debe descontarse una sola vez.
- Antes de descontar, el sistema debe validar que haya stock suficiente.
- Si no hay stock suficiente, no debe permitir el cambio de estado.
- Debe registrarse un movimiento de stock por cada insumo consumido.

Para productos por `m2`:

```text
consumo = cantidadVendida * ancho * alto * cantidadInsumoReceta
```

Para otros productos:

```text
consumo = cantidadVendida * cantidadInsumoReceta
```

Cada salida debe quedar en el historial como `salida_venta`.

## 16. Compras de insumos

Una compra representa la adquisicion de insumos a un proveedor.

Datos:

- proveedor
- fecha
- numero de compra
- total
- observaciones
- detalles

Reglas:

- Debe tener proveedor.
- Debe tener al menos un detalle.
- El total se calcula sumando subtotales.
- Al guardar una compra, el stock de cada insumo debe aumentar automaticamente.
- Debe registrarse un movimiento de stock por cada insumo comprado.
- Si la compra se paga en el momento, debe registrar egreso de caja.
- Si no se paga en el momento, debe generar deuda al proveedor.

## 17. Movimiento de stock

Movimiento de stock es el historial de todo lo que ocurre con el inventario.

Tipos:

- `entrada_compra`
- `salida_venta`
- `ajuste_manual`
- `perdida`

Datos:

- insumo
- compra opcional
- venta opcional
- detalle de venta opcional
- tipo
- cantidad
- costo unitario
- costo total
- stock anterior
- stock posterior
- fecha
- observaciones

Reglas:

- No debe permitir stock negativo.
- Debe guardar stock anterior y stock posterior.
- Sirve como auditoria de inventario.
- Las compras y ventas deben generar movimientos automaticamente.
- Los ajustes manuales y perdidas tambien deben quedar registrados.

## 18. Pagos de venta

Un pago de venta registra dinero recibido por una venta.

Datos:

- venta
- cuenta donde ingreso el dinero
- fecha
- monto
- medio de pago
- observaciones

Reglas:

- El pago debe estar asociado a una venta.
- El monto debe ser mayor a cero.
- No se puede pagar mas que el total pendiente de la venta.
- Cada pago debe aumentar el saldo real de la cuenta donde entra el dinero.
- Cada pago debe actualizar el estado de pago de la venta.

Ejemplos:

- Si no habia pagos y entra una seña, la venta queda `señeado`.
- Si el total pagado llega al monto total, la venta queda `pagado`.
- Si se entrega y queda saldo pendiente, se puede generar deuda al cliente.

## 19. Cuentas y caja

El sistema debe controlar saldos reales por cuenta.

Ejemplos de cuentas:

- efectivo
- mercado pago
- banco
- otra cuenta

Cada cuenta debe mostrar:

- saldo actual
- ingresos por fecha
- egresos por fecha
- movimientos asociados

Todo movimiento de dinero debe afectar una cuenta.

Tipos generales:

- ingreso
- egreso
- transferencia entre cuentas
- ajuste

Conceptos posibles:

- seña de cliente
- pago completo de cliente
- pago de revendedor
- compra de insumos
- pago a tercerizado
- sueldo
- comision
- impuesto
- inversion en el negocio
- retiro fuera del negocio
- gasto general

Regla central:

El saldo de una cuenta debe ser siempre trazable desde sus movimientos.

## 20. Deudas

El sistema debe poder generar y pagar deudas.

Tipos de deuda:

- deuda de cliente
- deuda a proveedor de insumos
- deuda a proveedor tercerizado
- deuda de sueldo a empleado
- deuda de comision a vendedor o empleado

Datos sugeridos:

- persona o proveedor asociado
- venta relacionada opcional
- compra relacionada opcional
- tipo
- monto total
- monto pagado
- saldo pendiente
- estado
- fecha de generacion
- fecha de vencimiento opcional

Estados sugeridos:

- `pendiente`
- `parcial`
- `pagada`
- `cancelada`

Reglas:

- Una deuda puede pagarse total o parcialmente.
- Cada pago de deuda debe mover dinero desde una cuenta.
- Si el monto pagado iguala el total, la deuda queda `pagada`.
- Las deudas permiten saber que se debe cobrar y que se debe pagar.

## 21. Sueldos

Los empleados cobran sueldo mensual.

Reglas:

- Cada mes se debe generar una deuda de sueldo.
- Esa deuda queda pendiente hasta que se pague.
- El pago del sueldo sale de una cuenta.
- Al pagar, se registra movimiento de caja.
- Debe quedar historial de sueldos pagados y pendientes.

## 22. Comisiones

Las comisiones corresponden a vendedores y empleados internos.

Reglas:

- Los revendedores no cobran comision.
- La comision se genera cuando la venta pasa a `entregado`.
- La comision se calcula como parte de la ganancia.
- Las comisiones se pagan semanalmente.
- Al generarse, quedan como deuda interna.
- Al pagarse, salen de una cuenta y se registra movimiento de caja.

Caso especial:

Si una venta fue entregada pero el cliente no pago todo, el sistema debe permitir generar deuda al cliente y mostrar una decision:

```text
El cliente aun debe dinero.
Desea generar igualmente la comision para el vendedor/empleado?
```

El usuario puede decidir pagar o no la comision en ese caso.

## 23. Trabajos tercerizados

Un trabajo tercerizado es un servicio externo realizado por un proveedor.

Ejemplos:

- corte
- impresion externa
- laminado externo
- instalacion externa

Datos:

- proveedor
- nombre
- unidad de calculo
- activo

Unidades permitidas:

- `m2`
- `lineal`
- `unidad`

Reglas:

- Debe estar asociado a un proveedor tercerizado.
- Debe tener unidad valida.
- Puede tener escalas de costo.

## 24. Escalas de costo tercerizado

Estas escalas definen cuanto cobra una empresa tercerizada segun cantidad.

Datos:

- trabajo tercerizado
- cantidad desde
- cantidad hasta
- precio unitario
- activo

Reglas:

- La escala debe pertenecer a un trabajo tercerizado existente.
- `cantidadDesde` y `precioUnitario` son obligatorios.
- `cantidadHasta` puede quedar nula para rango abierto.
- El precio no puede ser negativo.

## 25. Ordenes de trabajo tercerizado

Una orden de trabajo tercerizado vincula una venta y un detalle de venta con una empresa externa.

Datos:

- venta
- detalle de venta
- trabajo tercerizado
- proveedor
- cantidad
- precio unitario historico
- costo total historico
- estado
- fecha de envio
- fecha de retiro/finalizacion
- monto pagado
- cuenta de pago

Estados sugeridos:

- `pendiente`
- `enviado`
- `en_proceso`
- `listo`
- `retirado`
- `cancelado`

Reglas:

- Al enviar, queda registrado que el trabajo salio a un proveedor.
- Al retirar, se debe registrar cuanto se le pago al proveedor.
- Si se paga en el momento, sale dinero de una cuenta.
- Si no se paga en el momento, se genera deuda al proveedor.
- El costo se calcula segun escala de costo tercerizado.

## 26. Conversion de presupuesto a venta

Cuando un presupuesto se confirma, debe poder convertirse en venta.

Reglas:

- La venta conserva cliente o revendedor.
- La venta conserva responsable.
- La venta copia los detalles del presupuesto.
- La venta congela precios, costos y subtotales.
- El presupuesto queda marcado como `confirmado`.
- La venta arranca con estado de taller inicial definido por el negocio.
- La venta arranca con estado de pago `pendiente`, salvo que se registre pago inicial.

## 27. Reglas de edicion

Reglas importantes:

- Una venta `señeada` no se puede editar.
- Una venta `pagada` no se puede editar.
- Una venta con stock ya descontado no debe permitir cambios de detalle sin un proceso especial de ajuste.
- Si se necesita corregir una venta avanzada, debe existir una operacion controlada que registre ajustes de stock y caja.
- Los precios historicos de venta no deben cambiar aunque luego cambie el precio del producto.
- Los costos historicos de venta no deben cambiar aunque luego cambie el costo de los insumos.

## 28. Auditoria esperada

El sistema debe permitir responder preguntas como:

- Cuanto stock tengo de cada insumo?
- Cuanto stock entro por compras?
- Cuanto stock salio por ventas?
- Cuanto dinero hay en efectivo?
- Cuanto dinero hay en Mercado Pago?
- Cuanto entro por fecha?
- Cuanto salio por fecha?
- Que clientes deben dinero?
- Que proveedores deben cobrar?
- Que empleados tienen sueldos pendientes?
- Que vendedores o empleados tienen comisiones pendientes?
- Que ventas estan pendientes, en produccion, listas o entregadas?
- Que trabajos tercerizados estan pendientes de retirar o pagar?

## 29. Inconsistencias actuales detectadas

Estas son diferencias entre la logica deseada y parte del codigo actual:

- Actualmente la venta descuenta stock cuando el estado de pago es `señeado` o `pagado`; deberia descontar cuando la venta pasa a `entrega`.
- La venta necesita una marca para saber si el stock ya fue descontado.
- Una venta señeada no deberia poder editarse.
- Las compras todavia no aumentan stock automaticamente.
- Las compras todavia no generan movimientos de stock automaticamente.
- Los pagos de venta todavia no estan asociados a una cuenta.
- No existe todavia el modelo completo de cuentas/caja.
- No existe todavia el modelo completo de deudas.
- No existe todavia generacion mensual de sueldos como deuda.
- No existe todavia generacion semanal de comisiones como deuda.
- Los estados de tercerizado son pocos para el flujo real.
- Al retirar un tercerizado falta registrar pago, deuda o cuenta de salida.
- Hay migraciones SQL viejas que mencionan tablas no representadas en las entidades actuales.

## 30. Prioridad recomendada de desarrollo

Orden recomendado para avanzar sin romper el sistema:

1. Ajustar venta para cambiar estado correctamente.
2. Agregar `stockDescontado` a venta.
3. Mover el descuento de stock al cambio de estado `entrega`.
4. Impedir editar ventas señeadas, pagadas o con stock descontado.
5. Hacer que compras aumenten stock y creen movimientos de stock.
6. Crear cuentas y movimientos de caja.
7. Asociar pagos de venta a cuentas.
8. Crear deudas y pagos de deuda.
9. Agregar sueldos mensuales como deuda.
10. Agregar comisiones semanales como deuda.
11. Mejorar flujo de tercerizados con estados, pagos y deudas.
12. Crear conversion formal de presupuesto confirmado a venta.

## 31. Resumen corto

Drover Grafica necesita controlar tres cosas al mismo tiempo:

- Produccion: presupuestos, ventas, estados, entregas y tercerizados.
- Stock: insumos, recetas, compras, consumos y movimientos.
- Dinero: pagos, cuentas, saldos, deudas, sueldos, comisiones y gastos.

La regla central del sistema es que cada accion real del negocio debe dejar historial:

- Si entra dinero, queda movimiento de caja.
- Si sale dinero, queda movimiento de caja.
- Si entra stock, queda movimiento de stock.
- Si sale stock, queda movimiento de stock.
- Si alguien debe pagar o cobrar, queda una deuda.
- Si una venta avanza de estado, debe respetar las reglas de produccion.
