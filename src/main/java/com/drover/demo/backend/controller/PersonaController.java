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

import com.drover.demo.backend.entity.Persona;
import com.drover.demo.backend.service.PersonaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @PostMapping
    public ResponseEntity<Void> guardar(@Valid @RequestBody Persona persona) {
        personaService.guardar(persona);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editarPersona(@PathVariable Long id, @Valid @RequestBody Persona persona) {
        personaService.editarPersona(id, persona);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarPersona(@PathVariable Long id) {
        personaService.desactivarPersona(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarPersonaDesactivada(@PathVariable Long id) {
        personaService.borrarPersonaDesactivada(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Persona> mostrarPersona() {
        return personaService.mostrarPersona();
    }

    @GetMapping("/rol/{rol}")
    public List<Persona> listarPorRol(@PathVariable String rol) {
        return personaService.listarPorRol(rol);
    }

    @GetMapping("/activos/{activos}")
    public List<Persona> mostrasActivos(@PathVariable Boolean activos) {
        return personaService.mostrasActivos(activos);
    }

    @GetMapping("/nombre/{nombre}")
    public List<Persona> buscarPorNombre(@PathVariable String nombre) {
        return personaService.buscarPorNombre(nombre);
    }

    @GetMapping("/telefono/{telefono}")
    public ResponseEntity<Persona> buscarPorTelefono(@PathVariable String telefono) {
        return ResponseEntity.of(personaService.buscarPorTelefono(telefono));
    }
}
