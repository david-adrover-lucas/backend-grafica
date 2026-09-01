package com.drover.demo.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.TrabajosTercerizado;
import com.drover.demo.backend.repository.ProveedorRepository;
import com.drover.demo.backend.repository.TrabajosTercerizadoRepository;

import jakarta.transaction.Transactional;



@Service
public class TrabajosTercerizadoService {

    private final TrabajosTercerizadoRepository trabajosTercerizadoRepository;
    private final ProveedorRepository proveedorRepository;
    
    private final List<String> unidadPermitidas = List.of("m2", "lineal", "unidad");

    public TrabajosTercerizadoService(TrabajosTercerizadoRepository trabajosTercerizadoRepository, 
                                      ProveedorRepository proveedorRepository) {
        this.trabajosTercerizadoRepository = trabajosTercerizadoRepository;
        this.proveedorRepository = proveedorRepository;
    }

    // --- MÉTODOS PÚBLICOS ---
    @Transactional
    public void guardar(TrabajosTercerizado trabajosTercerizado) {

        TrabajosTercerizado trabajoLimpio = validaTrabajosTercerizado(trabajosTercerizado);
        trabajosTercerizadoRepository.save(trabajoLimpio);
    }

    @Transactional
    public void editar(Long id, TrabajosTercerizado trabajosTercerizado) {
        if (id == null) {
            throw new IllegalArgumentException("Debes completar el campo ID para editar.");
        }

        TrabajosTercerizado trabajoExistente = trabajosTercerizadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Trabajo tercerizado no encontrado con el ID: " + id));

        TrabajosTercerizado datosLimpios = validaTrabajosTercerizado(trabajosTercerizado);

        trabajoExistente.setNombre(datosLimpios.getNombre());
        trabajoExistente.setUnidadCalculo(datosLimpios.getUnidadCalculo());
        trabajoExistente.setProveedor(datosLimpios.getProveedor());
        trabajoExistente.setActivo(datosLimpios.getActivo());

        trabajosTercerizadoRepository.save(trabajoExistente);
    }

    @Transactional
    public void desactivarTrabajo(Long id) {
        TrabajosTercerizado trabajo = trabajosTercerizadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Trabajo tercerizado no encontrado con el ID: " + id));
        trabajo.setActivo(false);
        trabajosTercerizadoRepository.save(trabajo);
    }

    public List<TrabajosTercerizado> listar() {
        return trabajosTercerizadoRepository.findAll();
    }


    public List<TrabajosTercerizado> listarPorProveedor(Long proveedorId) {
        if (proveedorId == null) {
            throw new IllegalArgumentException("El ID del proveedor es obligatorio para realizar el filtro.");
        }
        return trabajosTercerizadoRepository.findByProveedorId(proveedorId);
    }


    public List<TrabajosTercerizado> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.strip().isEmpty()) {
            throw new IllegalArgumentException("El parámetro de búsqueda no puede estar vacío.");
        }
        return trabajosTercerizadoRepository.findByNombreContainingIgnoreCase(nombre.strip().toLowerCase());
    }

    // --- MÉTODOS PRIVADOS ---

    private TrabajosTercerizado validaTrabajosTercerizado(TrabajosTercerizado trabajosTercerizado) {
        if (trabajosTercerizado == null) {
            throw new IllegalArgumentException("La consulta de trabajo tercerizado está vacía.");
        }
        
        // 1. Validamos nulidad y campos de texto obligatorios
        if (trabajosTercerizado.getNombre() == null || trabajosTercerizado.getNombre().strip().isEmpty() ||
            trabajosTercerizado.getUnidadCalculo() == null || trabajosTercerizado.getUnidadCalculo().strip().isEmpty()) {
            throw new IllegalArgumentException("El nombre y la unidad de cálculo son campos estrictamente obligatorios.");
        }

        // 2. Validamos la relación obligatoria con la entidad Proveedor
        if (trabajosTercerizado.getProveedor() == null || trabajosTercerizado.getProveedor().getId() == null) {
            throw new IllegalArgumentException("El trabajo debe estar obligatoriamente vinculado a un proveedor válido.");
        }

        if (!proveedorRepository.existsById(trabajosTercerizado.getProveedor().getId())) {
            throw new IllegalArgumentException("El proveedor con ID " + trabajosTercerizado.getProveedor().getId() + " no existe en el sistema.");
        }

        trabajosTercerizado.setNombre(trabajosTercerizado.getNombre().strip().toLowerCase());
        trabajosTercerizado.setUnidadCalculo(trabajosTercerizado.getUnidadCalculo().strip().toLowerCase());

        if (!unidadPermitidas.contains(trabajosTercerizado.getUnidadCalculo())) {
            throw new IllegalArgumentException("Unidad inválida. Las unidades permitidas para tercerizados son: " + unidadPermitidas);
        }

        if (trabajosTercerizado.getActivo() == null) {
            trabajosTercerizado.setActivo(true);
        }

        return trabajosTercerizado;
    }
}
