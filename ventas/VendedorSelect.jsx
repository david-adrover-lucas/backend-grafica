

function VendedorSelect({ usuarios, onChange }) {
  return (
    <div>
      <label>Vendedor:</label>
      <select name="usuarioId" onChange={onChange}>
        <option value="">Seleccionar vendedor</option>
        {usuarios.map(u => (
          <option key={u.id} value={u.id}>{u.nombre}</option>
        ))}
      </select>
    </div>
  )
}

export default VendedorSelect