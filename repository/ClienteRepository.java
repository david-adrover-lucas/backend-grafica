package com.drover.demo.backend.repository;



import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import com.drover.demo.backend.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    // Buscar cliente por teléfono
     Optional <Cliente> findByTelefono(String telefono);
    // Buscar clientes por nombre (contiene texto)
     List<Cliente> findByNombreContainingIgnoreCase( String nombre);

    // Clientes con deuda mayor a X
     List<Cliente> findByDeudaGreaterThan(BigDecimal deuda);    
    
    List<Cliente> findByActivo(Boolean activo);
    @Query("SELECT c FROM Cliente c WHERE c.deuda >= :monto AND c.activo = :activo AND c.telefono = :telefono")
    List<Cliente> findClientesConDuedas( @Param("monto") BigDecimal monto,@Param("activo") Boolean activo,@Param("telefono") String telefono);
}

