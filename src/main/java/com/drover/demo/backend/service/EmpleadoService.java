package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Empleado;
import com.drover.demo.backend.repository.EmpleadoRepository;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }
    public void asignarSueldo(Long id, BigDecimal sueldo){
        if (id==null) {
            throw new IllegalArgumentException("el campo id no puede ser nulo");
        }
        BigDecimal sueldoLimpio= validarSueldo(sueldo);
        Empleado empleado= empleadoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Empleado no encontrado con el ID: " + id));
        empleado.setSueldoMensual(sueldoLimpio);
        empleadoRepository.save(empleado);
    }

    public List<Empleado> listar() {
         return empleadoRepository.findAll();
    }


    private BigDecimal validarSueldo(BigDecimal sueldo) {

        if (sueldo == null) {
            throw new IllegalArgumentException("Por favor, no ingrese un dato vacío.");
        }
        
        if (sueldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El sueldo no puede ser menor a 0.");
        }
        
        return sueldo;
    }

}
