

function CarritoTotales({ carrito }) {

  // Por ahora mostramos solo cantidad de items
  // Cuando el backend devuelva costo y precio por item los sumamos acá
  const totalItems = carrito.reduce((acc, item) => acc + item.cantidad, 0)

  return (
    <div>
      <p>Items en carrito: {totalItems}</p>
      {/* Costo, precio y ganancia los agregamos cuando
          conectemos con el backend en el próximo reto */}
    </div>
  )
}

export default CarritoTotales