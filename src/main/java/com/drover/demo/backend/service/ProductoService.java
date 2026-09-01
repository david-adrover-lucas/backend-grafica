package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Producto;
import com.drover.demo.backend.entity.ProductoInsumo;
import com.drover.demo.backend.repository.ProductoRepository;

import jakarta.transaction.Transactional;



@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
    
    @Transactional
    public void guardar(Producto producto) {
        // Al guardar de la forma tradicional, calcula el costo por insumos y le aplica el % de ganancia
        if (producto == null) {
            throw new IllegalArgumentException("La consulta de producto no puede estar vacía.");
        }
        
        producto.setNombre(validarString(producto.getNombre()));
        producto.setUnidadVenta(validarString(producto.getUnidadVenta()));
        
        if (producto.getMontoGanancia() == null || producto.getMontoGanancia().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El porcentaje de ganancia es obligatorio y no puede ser negativo.");
        }
        
        if (producto.getActivo() == null) {
            producto.setActivo(true);
        }

        enlazarComponentesAlPadre(producto);

        // 1. Calculamos costo actual
        BigDecimal costoCalculado = calcularCostoActual(producto.getInsumosComponentes());
        producto.setCostoActual(costoCalculado);

        // 2. Calculamos precio final basándonos en el PORCENTAJE (monto_ganancia actúa como % de ahora en más)
        BigDecimal precioCalculado = calcularPrecioVentaPorPorcentaje(producto.getCostoActual(), producto.getMontoGanancia());
        producto.setPrecioVenta(precioCalculado);

        productoRepository.save(producto);
    }

    @Transactional
    public void editar(Long id, Producto producto) {
        if (id == null) {
            throw new IllegalArgumentException("Debes completar el campo ID para editar.");
        }

        Producto productoEncontrado = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto con ID no encontrado: " + id));

        // 1. Recalculamos el costo real actual basado en la receta de insumos provista
        BigDecimal costoCalculado = calcularCostoActual(producto.getInsumosComponentes());
        producto.setCostoActual(costoCalculado);

       
        if (producto.getPrecioVenta() != null && producto.getPrecioVenta().compareTo(BigDecimal.ZERO) > 0) {
            
            if (producto.getPrecioVenta().compareTo(producto.getCostoActual()) < 0) {
                throw new IllegalArgumentException("El precio de venta manual (" + producto.getPrecioVenta() 
                    + ") no puede ser menor que el costo de sus insumos (" + producto.getCostoActual() + "). ¡Venta a pérdida!");
            }

         
            BigDecimal nuevoPorcentaje = producto.getPrecioVenta()
                .divide(producto.getCostoActual(), 4, RoundingMode.HALF_UP)
                .subtract(BigDecimal.ONE)
                .multiply(new BigDecimal("100"))
                .setScale(2, RoundingMode.HALF_UP); // Guardamos con dos decimales de precisión
            
            producto.setMontoGanancia(nuevoPorcentaje);
        } 
        // Si no enviaron precio manual, calculamos el precio de venta normal usando el porcentaje provisto
        else {
            if (producto.getMontoGanancia() == null || producto.getMontoGanancia().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Si no define un precio manual, el porcentaje de ganancia es obligatorio.");
            }
            BigDecimal precioCalculado = calcularPrecioVentaPorPorcentaje(producto.getCostoActual(), producto.getMontoGanancia());
            producto.setPrecioVenta(precioCalculado);
        }

        // 3. Traspasamos valores normalizados al registro persistente de Hibernate
        productoEncontrado.setActivo(producto.getActivo() != null ? producto.getActivo() : true);
        productoEncontrado.setNombre(validarString(producto.getNombre()));
        productoEncontrado.setUnidadVenta(validarString(producto.getUnidadVenta()));
        productoEncontrado.setCostoActual(producto.getCostoActual());
        productoEncontrado.setMontoGanancia(producto.getMontoGanancia()); // Guarda el % nuevo o viejo
        productoEncontrado.setPrecioVenta(producto.getPrecioVenta());

        // 4. Actualizamos la lista de materiales componentes en cascada
        productoEncontrado.getInsumosComponentes().clear();
        if (producto.getInsumosComponentes() != null) {
            for (ProductoInsumo nuevoComponente : producto.getInsumosComponentes()) {
                productoEncontrado.getInsumosComponentes().add(nuevoComponente);
                nuevoComponente.setProducto(productoEncontrado);
            }
        }

        productoRepository.save(productoEncontrado);
    }

    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    public List<Producto> listarNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(validarString(nombre));
    }
   
    public List<Producto> listarUnidad_venta(String unidadVenta) {
        return productoRepository.findByUnidadVenta(validarString(unidadVenta));
    }

    // --- MÉTODOS PRIVADOS ---
    
 
    private BigDecimal calcularPrecioVentaPorPorcentaje(BigDecimal costo, BigDecimal porcentaje) {
        // Factor = (Porcentaje / 100) + 1
        BigDecimal factorGanancia = porcentaje
            .divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP)
            .add(BigDecimal.ONE);
        
        return costo.multiply(factorGanancia).setScale(2, RoundingMode.HALF_UP);
    }
 
    private String validarString(String string) {
        if (string == null || string.strip().isEmpty()) {
            throw new IllegalArgumentException("El campo de texto es obligatorio o inválido."); 
        }
        return string.strip().toLowerCase();
    }
   
    private BigDecimal calcularCostoActual(List<ProductoInsumo> componentes) {
        if (componentes == null || componentes.isEmpty()) {
            throw new IllegalArgumentException("Un producto comercial debe tener al menos un insumo en su composición.");
        }

        BigDecimal costoAcumulado = BigDecimal.ZERO;
        for (ProductoInsumo componente : componentes) {
            if (componente.getInsumo() == null || componente.getInsumo().getId() == null) {
                throw new IllegalArgumentException("Cada componente de la receta debe apuntar a un insumo válido.");
            }
            if (componente.getCantidad() == null || componente.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("La cantidad consumida de cada insumo debe ser mayor a cero.");
            }
            BigDecimal costoComponente = componente.getInsumo().getCostoUnitario().multiply(componente.getCantidad());
            costoAcumulado = costoAcumulado.add(costoComponente);
        }
        return costoAcumulado;
    }    

    private void enlazarComponentesAlPadre(Producto producto) {
        if (producto.getInsumosComponentes() != null) {
            for (ProductoInsumo componente : producto.getInsumosComponentes()) {
                componente.setProducto(producto);
            }
        }
    }
}

