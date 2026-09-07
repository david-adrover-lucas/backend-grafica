package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.drover.demo.backend.entity.Cuenta;
import com.drover.demo.backend.entity.MovimientoCaja;
import com.drover.demo.backend.repository.CuentaRepository;
import com.drover.demo.backend.repository.MovimientoCajaRepository;

@Service
public class MovimientoCajaService {

    private final MovimientoCajaRepository movimientoCajaRepository;
    private final CuentaRepository cuentaRepository;
    private final List<String> tiposPermitidos = List.of("ingreso", "egreso", "ajuste");

    public MovimientoCajaService(MovimientoCajaRepository movimientoCajaRepository, CuentaRepository cuentaRepository) {
        this.movimientoCajaRepository = movimientoCajaRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Transactional
    public MovimientoCaja registrar(MovimientoCaja movimiento) {
        MovimientoCaja limpio = validarMovimiento(movimiento);
        Cuenta cuenta = cuentaRepository.findById(limpio.getCuenta().getId())
            .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con el ID: " + limpio.getCuenta().getId()));

        if (!Boolean.TRUE.equals(cuenta.getActivo())) {
            throw new IllegalStateException("No se puede registrar un movimiento en una cuenta inactiva.");
        }

        BigDecimal saldoAnterior = cuenta.getSaldoActual();
        BigDecimal saldoPosterior = calcularSaldoPosterior(saldoAnterior, limpio.getMonto(), limpio.getTipo());

        cuenta.setSaldoActual(saldoPosterior);
        cuentaRepository.save(cuenta);

        limpio.setCuenta(cuenta);
        limpio.setSaldoAnterior(saldoAnterior);
        limpio.setSaldoPosterior(saldoPosterior);

        return movimientoCajaRepository.save(limpio);
    }

    @Transactional
    public List<MovimientoCaja> transferir(Long cuentaOrigenId, Long cuentaDestinoId, BigDecimal monto, String concepto) {
        if (cuentaOrigenId == null || cuentaDestinoId == null) {
            throw new IllegalArgumentException("Las cuentas de origen y destino son obligatorias.");
        }
        if (cuentaOrigenId.equals(cuentaDestinoId)) {
            throw new IllegalArgumentException("La cuenta origen y destino no pueden ser la misma.");
        }

        Cuenta destino = cuentaRepository.findById(cuentaDestinoId)
            .orElseThrow(() -> new RuntimeException("Cuenta destino no encontrada con el ID: " + cuentaDestinoId));

        MovimientoCaja egreso = new MovimientoCaja();
        Cuenta origen = new Cuenta();
        origen.setId(cuentaOrigenId);
        egreso.setCuenta(origen);
        egreso.setCuentaDestino(destino);
        egreso.setTipo("egreso");
        egreso.setConcepto(concepto != null ? concepto : "transferencia entre cuentas");
        egreso.setMonto(monto);
        egreso.setObservaciones("Transferencia hacia cuenta " + destino.getNombre());

        MovimientoCaja ingreso = new MovimientoCaja();
        ingreso.setCuenta(destino);
        ingreso.setTipo("ingreso");
        ingreso.setConcepto(concepto != null ? concepto : "transferencia entre cuentas");
        ingreso.setMonto(monto);
        ingreso.setObservaciones("Transferencia desde cuenta ID " + cuentaOrigenId);

        return List.of(registrar(egreso), registrar(ingreso));
    }

    public List<MovimientoCaja> listar() {
        return movimientoCajaRepository.findAll();
    }

    public List<MovimientoCaja> listarPorCuenta(Long cuentaId) {
        if (cuentaId == null) {
            throw new IllegalArgumentException("El ID de la cuenta es obligatorio.");
        }
        return movimientoCajaRepository.findByCuentaIdOrderByFechaDesc(cuentaId);
    }

    public List<MovimientoCaja> listarPorFecha(LocalDateTime desde, LocalDateTime hasta) {
        validarRangoFechas(desde, hasta);
        return movimientoCajaRepository.findByFechaBetweenOrderByFechaDesc(desde, hasta);
    }

    public List<MovimientoCaja> listarPorCuentaYFecha(Long cuentaId, LocalDateTime desde, LocalDateTime hasta) {
        if (cuentaId == null) {
            throw new IllegalArgumentException("El ID de la cuenta es obligatorio.");
        }
        validarRangoFechas(desde, hasta);
        return movimientoCajaRepository.findByCuentaIdAndFechaBetweenOrderByFechaDesc(cuentaId, desde, hasta);
    }

    public BigDecimal totalPorTipoYFecha(String tipo, LocalDateTime desde, LocalDateTime hasta) {
        String tipoLimpio = validarTexto(tipo, "tipo");
        validarRangoFechas(desde, hasta);

        return movimientoCajaRepository.findByTipoAndFechaBetween(tipoLimpio, desde, hasta).stream()
            .map(MovimientoCaja::getMonto)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private MovimientoCaja validarMovimiento(MovimientoCaja movimiento) {
        if (movimiento == null) {
            throw new IllegalArgumentException("El movimiento de caja no puede estar vacio.");
        }
        if (movimiento.getCuenta() == null || movimiento.getCuenta().getId() == null) {
            throw new IllegalArgumentException("El movimiento debe estar asociado a una cuenta valida.");
        }

        movimiento.setTipo(validarTexto(movimiento.getTipo(), "tipo"));
        movimiento.setConcepto(validarTexto(movimiento.getConcepto(), "concepto"));

        if (!tiposPermitidos.contains(movimiento.getTipo())) {
            throw new IllegalArgumentException("Tipo de movimiento de caja invalido. Use: " + tiposPermitidos);
        }
        if ("transferencia".equals(movimiento.getTipo())) {
            throw new IllegalArgumentException("Use el metodo de transferencia para mover dinero entre cuentas.");
        }
        if (movimiento.getMonto() == null || movimiento.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }
        if (movimiento.getFecha() == null) {
            movimiento.setFecha(LocalDateTime.now());
        }

        return movimiento;
    }

    private BigDecimal calcularSaldoPosterior(BigDecimal saldoAnterior, BigDecimal monto, String tipo) {
        if ("ingreso".equals(tipo)) {
            return saldoAnterior.add(monto);
        }
        if ("egreso".equals(tipo)) {
            BigDecimal saldoPosterior = saldoAnterior.subtract(monto);
            if (saldoPosterior.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("La cuenta no tiene saldo suficiente para registrar este egreso.");
            }
            return saldoPosterior;
        }
        return monto;
    }

    private void validarRangoFechas(LocalDateTime desde, LocalDateTime hasta) {
        if (desde == null || hasta == null) {
            throw new IllegalArgumentException("Las fechas desde y hasta son obligatorias.");
        }
        if (desde.isAfter(hasta)) {
            throw new IllegalArgumentException("La fecha desde no puede ser posterior a hasta.");
        }
    }

    private String validarTexto(String texto, String campo) {
        if (texto == null || texto.strip().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " es obligatorio.");
        }
        return texto.strip().toLowerCase();
    }
}
