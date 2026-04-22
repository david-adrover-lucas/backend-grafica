package com.drover.demo.backend.service;

import com.drover.demo.backend.entity.VentaDetalleInsumoExtra;
import com.drover.demo.backend.exception.RecursoNoEncontradoException;
import com.drover.demo.backend.exception.ValidacionException;
import com.drover.demo.backend.repository.VentaDetalleInsumoExtraRepository;
import com.drover.demo.backend.repository.VentaDetalleRepository;

import com.drover.demo.backend.repository.InsumoRepository;
import com.drover.demo.backend.entity.Insumo;
import com.drover.demo.backend.entity.VentaDetalle;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VentaDetalleInsumoExtraService {

    private final VentaDetalleInsumoExtraRepository extraRepository;
    private final VentaDetalleRepository detalleRepository;
    private final InsumoRepository insumoRepository;

    public VentaDetalleInsumoExtraService(
            VentaDetalleInsumoExtraRepository extraRepository,
            VentaDetalleRepository detalleRepository,
            InsumoRepository insumoRepository) {
        this.extraRepository = extraRepository;
        this.detalleRepository = detalleRepository;
        this.insumoRepository = insumoRepository;
    }

    /**
     * 🥇 Agregar insumo extra a un detalle
     */
    public VentaDetalleInsumoExtra agregarExtra(VentaDetalleInsumoExtra extra) {

        // Obtener insumo
        Insumo insumo = insumoRepository.findById(extra.getInsumoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Insumo no encontrado"));

        // Si no viene costo → usar el del insumo
        if (extra.getCostoUnitario() == null) {
            extra.setCostoUnitario(insumo.getCostoUnitario());
        }

        // Guardar extra
        VentaDetalleInsumoExtra guardado = extraRepository.save(extra);

        // 💣 actualizar costo del detalle
        actualizarCostoDetalle(extra.getVentaDetalleId());

        return guardado;
    }

    /**
     * 🥈 Listar extras de un detalle
     */
    public List<VentaDetalleInsumoExtra> listarPorDetalle(Integer detalleId) {
        return extraRepository.findByVentaDetalleId(detalleId);
    }

    /**
     * 💣 Recalcular costo del detalle
     */
    private void actualizarCostoDetalle(Integer detalleId) {

        VentaDetalle detalle = detalleRepository.findById(detalleId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Detalle no encontrado"));

        List<VentaDetalleInsumoExtra> extras =
                extraRepository.findByVentaDetalleId(detalleId);

        BigDecimal costoExtras = BigDecimal.ZERO;

        for (VentaDetalleInsumoExtra e : extras) {
            BigDecimal cantidad = BigDecimal.valueOf(e.getCantidad()); // ✅ convertís el Integer a BigDecimal
            BigDecimal costo = e.getCostoUnitario().multiply(cantidad);
            costoExtras = costoExtras.add(costo);
        }

        // sumar al costo existente
        detalle.setCosto(detalle.getCosto().add(costoExtras));

        // ⚠️ opcional: también aumentar precio
        detalle.setPrecio(detalle.getPrecio().add(costoExtras));

        detalleRepository.save(detalle);
    }

    /**
     * ❌ Eliminar extra
     */
    public void eliminar(Integer id) {

        VentaDetalleInsumoExtra extra = extraRepository.findById(id) .orElseThrow(() -> new RecursoNoEncontradoException("Extra no encontrado"));

        Integer detalleId = extra.getVentaDetalleId();

        extraRepository.deleteById(id);

        actualizarCostoDetalle(detalleId);
    }
}