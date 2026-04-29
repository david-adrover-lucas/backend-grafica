import React from "react"
import { useVentas } from "../hooks/useVentas"
import KanbanTablero from "../components/kanban/KanbanTablero"
import { useNavigate } from 'react-router-dom'

function Principal() {
  const { ventasPorEstado, loading, avanzar } = useVentas()
  const navigate = useNavigate()

  if (loading) return <p>Cargando tablero...</p>

  return (
    <div>
      <button onClick={() => navigate('/ventas/nueva')}>
        Nueva Venta
      </button>
      <KanbanTablero
        ventasPorEstado={ventasPorEstado}
        onAvanzar={avanzar}
      />
    </div>
  )
}

export default Principal