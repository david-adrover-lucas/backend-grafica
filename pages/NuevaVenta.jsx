import React from "react"
import { useNavigate } from 'react-router-dom'
import { useNuevaVenta } from '../hooks/useNuevaVenta'
import VendedorSelect    from '../components/ventas/VendedorSelect'
import ClienteBuscador   from '../components/ventas/ClienteBuscador'
import ComentarioInput   from '../components/ventas/ComentarioInput'
import ProductosBuscador from '../components/ventas/ProductosBuscador'
import CarritoLista      from '../components/ventas/CarritoLista'
import CarritoTotales    from '../components/ventas/CarritoTotales'

function NuevaVenta() {
  const navigate = useNavigate()

  const {
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
  } = useNuevaVenta()

  return (
    <div>
      <button onClick={() => navigate('/')}>← Volver</button>
      <h1>Nueva Venta</h1>

      <VendedorSelect
        usuarios={usuarios}
        onChange={handleChange}
      />

      <ClienteBuscador
        busquedaCliente={busquedaCliente}
        resultadosCliente={resultadosCliente}
        clienteSeleccionado={clienteSeleccionado}
        onBuscar={handleBuscarCliente}
        onSeleccionar={handleSeleccionarCliente}
      />

      <ProductosBuscador
        productos={productos}
        busquedaProducto={busquedaProducto}
        onBuscar={e => setBusquedaProducto(e.target.value)}
        productoActivo={productoActivo}
        onActivar={setProductoActivo}
        medidas={medidas}
        onCambiarMedidas={setMedidas}
        onAgregar={handleAgregarAlCarrito}
        onCancelar={() => setProductoActivo(null)}
      />

      <CarritoLista
        carrito={carrito}
        onQuitar={handleQuitarDelCarrito}
      />

      <CarritoTotales
        carrito={carrito}
      />

      <ComentarioInput
        value={form.comentario}
        onChange={handleChange}
      />

      <button onClick={() => navigate('/')}>Cancelar</button>
      <button onClick={() => {}}>Crear Venta</button>
    </div>
  )
}

export default NuevaVenta