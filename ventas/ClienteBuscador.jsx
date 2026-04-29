function ClienteBuscador({   busquedaCliente,
  resultadosCliente,
  clienteSeleccionado,
  onBuscar,
  onSeleccionar}) {
    return (
        <div>
         <label>Cliente:</label>
         <input 
             value={busquedaCliente}
             onChange={onBuscar}
             placeholder="Buscar cliente por nombre"
            />
         {resultadosCliente.map(c =>(
               <div key={c.id} onClick={() => onSeleccionar(c)}>
                   {c.nombre} - {c.telefono}
               </div>
            ))}
            {clienteSeleccionado && (
                <p>{clienteSeleccionado.nombre} - {clienteSeleccionado.telefono}</p>
            )}


        </div>
    )
}
 export default ClienteBuscador
