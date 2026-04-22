package com.drover.demo.backend.service;

import org.springframework.stereotype.Service;


import com.drover.demo.backend.entity.Cuenta;
import com.drover.demo.backend.exception.RecursoDuplicadoException;
import com.drover.demo.backend.exception.RecursoNoEncontradoException;
import com.drover.demo.backend.repository.CuentaRepository;

import java.util.List;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    // ===============================
    // 1. CREAR / GUARDAR CUENTA
        public Cuenta guardar(Cuenta cuenta) {

            // si tiene id es una actualización, no validar nombre duplicado
            if (cuenta.getId() == null) {
                if (!cuentaRepository.findByNombre(cuenta.getNombre()).isEmpty()) {
                    throw new RecursoDuplicadoException("La cuenta ya existe");
                }
            }

            if (cuenta.getActiva() == null) {
                cuenta.setActiva(true);
            }

            return cuentaRepository.save(cuenta);
        }

    // ===============================
    // 2. LISTAR TODAS
    // ===============================
    public List<Cuenta> listar() {
        return cuentaRepository.findAll();
    }
    public List<Cuenta> buscarPorNombre(String nombre) {
        return cuentaRepository.findByNombre(nombre);
    }

    // ===============================
    // 3. LISTAR SOLO ACTIVAS
    // ===============================
    public List<Cuenta> listarActivas() {
        return cuentaRepository.findByActiva(true);
    }

    // ===============================
    // 4. DESACTIVAR CUENTA
    // ===============================
    public void desactivar(Integer id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuenta no encontrada"));

        cuenta.setActiva(false);
        cuentaRepository.save(cuenta);
    }
    

}
