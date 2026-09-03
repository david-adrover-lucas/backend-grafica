package com.drover.demo.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.entity.Proveedor;
import com.drover.demo.backend.service.ProveedorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @PostMapping
    public ResponseEntity<Void> guardar(@Valid @RequestBody Proveedor proveedor) {
        proveedorService.guardar(proveedor);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(@PathVariable Long id, @Valid @RequestBody Proveedor proveedor) {
        proveedorService.editar(id, proveedor);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Proveedor> listar() {
        return proveedorService.listar();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Long id) {
        proveedorService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarProvedor(@PathVariable Long id) {
        proveedorService.desactivarProvedor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/telefono/{telefono}")
    public Proveedor buscarPorTelefono(@PathVariable String telefono) {
        return proveedorService.buscarPorTelefono(telefono);
    }

    @GetMapping("/activos/{activos}")
    public List<Proveedor> mostrarActivos(@PathVariable Boolean activos) {
        return proveedorService.mostrarActivos(activos);
    }

    @GetMapping("/tipo/{tipo}")
    public List<Proveedor> buscarPorTipo(@PathVariable String tipo) {
        return proveedorService.buscarPorTipo(tipo);
    }
}
