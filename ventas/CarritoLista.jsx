function CarritoLista({ carrito, onQuitar }) {
  return (
    <div>
      <label>Carrito:</label>

      {carrito.length === 0 && <p>Sin productos todavía</p>}

      {carrito.map((item, indice) => (
        <div key={indice}>
          <span>{item.nombre}</span>
          {item.tipo === 'M2' && (
            <span> {item.alto}×{item.ancho}m</span>
          )}
          <span> × {item.cantidad}</span>
          <button onClick={() => onQuitar(indice)}>x</button>
        </div>
      ))}
    </div>
  )
}

export default CarritoLista