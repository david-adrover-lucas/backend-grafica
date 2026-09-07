package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.drover.demo.backend.entity.Cuenta;
import com.drover.demo.backend.repository.CuentaRepository;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final List<String> tiposPermitidos = List.of("efectivo", "mercado_pago", "banco", "otra");

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Transactional
    public Cuenta guardar(Cuenta cuenta) {
        Cuenta cuentaLimpia = validarCuenta(cuenta);
        if (cuentaRepository.existsByNombre(cuentaLimpia.getNombre())) {
            throw new IllegalArgumentException("Ya existe una cuenta con ese nombre.");
        }
        return cuentaRepository.save(cuentaLimpia);
    }

    @Transactional
    public Cuenta editar(Long id, Cuenta cuenta) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la cuenta es obligatorio.");
        }

        Cuenta existente = cuentaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con el ID: " + id));

        Cuenta datosLimpios = validarCuenta(cuenta);
        if (cuentaRepository.existsByNombreAndIdNot(datosLimpios.getNombre(), id)) {
            throw new IllegalArgumentException("Ya existe otra cuenta con ese nombre.");
        }

        existente.setNombre(datosLimpios.getNombre());
        existente.setTipo(datosLimpios.getTipo());
        existente.setActivo(datosLimpios.getActivo());
        return cuentaRepository.save(existente);
    }

    @Transactional
    public void desactivar(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con el ID: " + id));
        cuenta.setActivo(false);
        cuentaRepository.save(cuenta);
    }

    public List<Cuenta> listar() {
        return cuentaRepository.findAll();
    }

    public List<Cuenta> listarActivas() {
        return cuentaRepository.findByActivo(true);
    }

    public List<Cuenta> listarPorTipo(String tipo) {
        String tipoLimpio = validarTexto(tipo, "tipo");
        if (!tiposPermitidos.contains(tipoLimpio)) {
            throw new IllegalArgumentException("Tipo de cuenta invalido. Use: " + tiposPermitidos);
        }
        return cuentaRepository.findByTipo(tipoLimpio);
    }

    private Cuenta validarCuenta(Cuenta cuenta) {
        if (cuenta == null) {
            throw new IllegalArgumentException("La cuenta no puede estar vacia.");
        }

        cuenta.setNombre(validarTexto(cuenta.getNombre(), "nombre"));
        cuenta.setTipo(validarTexto(cuenta.getTipo(), "tipo"));

        if (!tiposPermitidos.contains(cuenta.getTipo())) {
            throw new IllegalArgumentException("Tipo de cuenta invalido. Use: " + tiposPermitidos);
        }

        if (cuenta.getSaldoActual() == null) {
            cuenta.setSaldoActual(BigDecimal.ZERO);
        }

        if (cuenta.getActivo() == null) {
            cuenta.setActivo(true);
        }

        return cuenta;
    }

    private String validarTexto(String texto, String campo) {
        if (texto == null || texto.strip().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " es obligatorio.");
        }
        return texto.strip().toLowerCase();
    }
}
