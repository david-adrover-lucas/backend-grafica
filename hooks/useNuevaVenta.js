import { useState, useEffect } from 'react'
import { buscarClientes }  from '../api/clientes.api'
import { getProductos }    from '../api/productos.api'
import { getUsuarios }     from '../api/usuarios.api'
import { crearVenta }      from '../api/ventas.api'
import { agregarDetalle }  from '../api/ventaDetalles.api'

export function useNuevaVenta() {
  

  // Estados
  const [form, setForm] = useState({
    usuarioId:  null,
    clienteId:  null,
    comentario: ''
  })
  const [busquedaCliente, setBusquedaCliente]         = useState('')
  const [resultadosCliente, setResultadosCliente]     = useState([])
  const [clienteSeleccionado, setClienteSeleccionado] = useState(null)
  const [productos, setProductos]                     = useState([])
  const [busquedaProducto, setBusquedaProducto]       = useState('')
  const [productoActivo, setProductoActivo]           = useState(null)
  const [medidas, setMedidas] = useState({ alto: '', ancho: '', cantidad: 1 })
  const [usuarios, setUsuarios]                       = useState([])
  const [carrito, setCarrito]                         = useState([])

  // Cargar datos iniciales
  useEffect(() => {
    getUsuarios().then(r => setUsuarios(r.data))
    getProductos().then(r => setProductos(r.data))
  }, [])

  // Actualiza cualquier campo del form
  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value })
  }

  // Busca clientes mientras el usuario escribe
  const handleBuscarCliente = (e) => {
    const valor = e.target.value
    setBusquedaCliente(valor)
    if (valor.length >= 2) {
      buscarClientes(valor).then(r => setResultadosCliente(r.data))
    } else {
      setResultadosCliente([])
    }
  }

  // Selecciona un cliente de los resultados
  const handleSeleccionarCliente = (cliente) => {
    setClienteSeleccionado(cliente)
    setForm({ ...form, clienteId: cliente.id })
    setBusquedaCliente('')
    setResultadosCliente([])
  }

  // Agrega producto al carrito
  const handleAgregarAlCarrito = () => {
    const item = {
      productoId: productoActivo.id,
      nombre:     productoActivo.nombre,
      tipo:       productoActivo.tipo,
      cantidad:   Number(medidas.cantidad),
      alto:  productoActivo.tipo === 'M2' ? Number(medidas.alto)  : null,
      ancho: productoActivo.tipo === 'M2' ? Number(medidas.ancho) : null,
    }
    setCarrito([...carrito, item])
    setProductoActivo(null)
    setMedidas({ alto: '', ancho: '', cantidad: 1 })
  }

  // Quita producto del carrito
  const handleQuitarDelCarrito = (indice) => {
    setCarrito(carrito.filter((_, i) => i !== indice))
  }

  return {
    form,
    usuarios,
    productos,
    busquedaCliente,
    resultadosCliente,
    clienteSeleccionado,
    busquedaProducto,
    setBusquedaProducto,
    productoActivo,
    setProductoActivo,
    medidas,
    setMedidas,
    carrito,
    handleChange,
    handleBuscarCliente,
    handleSeleccionarCliente,
    handleAgregarAlCarrito,
    handleQuitarDelCarrito,
  }
}
