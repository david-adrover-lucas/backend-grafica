import { useState, useEffect } from 'react'
import { confirmarVenta, getVentasPorEstado, cambiarEstadoVenta } from '../api/ventas.api'
import { CONFIG_ESTADOS } from '../helpers/estadosConfig'

export function useVentas() {

  const [ventasPorEstado, setVentasPorEstado] = useState({
    DISENAR:     [],
    CONFIRMAR:   [],
    ENVIAR:      [],
    RETIRAR:     [],
    PREPARACION: [],
    ENTREGA:     []
  })

  const [loading, setLoading] = useState(true)

  const cargarVentas = () => {
    const estados = ['DISENAR', 'CONFIRMAR', 'ENVIAR', 'RETIRAR', 'PREPARACION', 'ENTREGA']
    const promesas = estados.map(estado => getVentasPorEstado(estado))

    Promise.all(promesas)
      .then(respuestas => {
        const resultado = estados.reduce((acumulador, estado, indice) => {
          acumulador[estado] = respuestas[indice].data
          return acumulador
        }, {})
        setVentasPorEstado(resultado)
      })
      .catch(err => console.error('Error cargando ventas:', err))
      .finally(() => setLoading(false))
  }

  const avanzar = (venta) => {
    const config = CONFIG_ESTADOS[venta.estado]

    if (venta.estado === 'DISENAR') {
      confirmarVenta(venta.id)
        .then(() => cargarVentas())
        .catch(err => console.error('Error confirmando:', err))

    } else if (venta.estado === 'CONFIRMAR') {
      // CORREGIDO: el siguiente depende de tieneTercerizado
      const siguienteEstado = venta.tieneTercerizado ? 'ENVIAR' : 'PREPARACION'
      cambiarEstadoVenta(venta.id, siguienteEstado)
        .then(() => cargarVentas())
        .catch(err => console.error('Error cambiando estado:', err))

    } else {
      // Resto de estados: flujo normal
      cambiarEstadoVenta(venta.id, config.siguiente)
        .then(() => cargarVentas())
        .catch(err => console.error('Error cambiando estado:', err))
    }
  }

  useEffect(() => {
    cargarVentas()
  }, [])

  return { ventasPorEstado, loading, avanzar }
}
