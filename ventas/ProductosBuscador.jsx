function ProductosBuscador({
  productos,
  busquedaProducto,
  onBuscar,
  productoActivo,
  onActivar,
  medidas,
  onCambiarMedidas,
  onAgregar,
  onCancelar
}) {
  return (
    <div>
      <label>Productos:</label>

      <input
        value={busquedaProducto}
        onChange={onBuscar}
        placeholder="Buscar producto..."
      />

      {/* Lista filtrada por lo que escribió */}
      {productos
        .filter(p =>
          p.nombre.toLowerCase().includes(busquedaProducto.toLowerCase())
        )
        .map(p => (
          <div key={p.id}>
            <span>{p.nombre}</span>
            <span> — {p.tipo}</span>
            <button onClick={() => onActivar(p)}>+</button>
          </div>
        ))
      }

      {/* Panel de medidas — solo si hay producto activo */}
      {productoActivo && (
        <div>
          <p>Agregando: {productoActivo.nombre}</p>

          {/* Alto y ancho solo si es M2 */}
          {productoActivo.tipo === 'M2' && (
            <>
              <input
                type="number"
                placeholder="Alto"
                value={medidas.alto}
                onChange={e => onCambiarMedidas({ ...medidas, alto: e.target.value })}
              />
              <input
                type="number"
                placeholder="Ancho"
                value={medidas.ancho}
                onChange={e => onCambiarMedidas({ ...medidas, ancho: e.target.value })}
              />
            </>
          )}

          <input
            type="number"
            placeholder="Cantidad"
            value={medidas.cantidad}
            onChange={e => onCambiarMedidas({ ...medidas, cantidad: e.target.value })}
          />

          <button onClick={onAgregar}>Agregar al carrito</button>
          <button onClick={onCancelar}>Cancelar</button>
        </div>
      )}
    </div>
  )
}

export default ProductosBuscador