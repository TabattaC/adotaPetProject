package com.adocaoProject.controller;

import com.adocaoProject.model.Ong;
import com.adocaoProject.model.Pets;
import com.adocaoProject.service.ServicePet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/pets")
public class ControlerPet {
    @Autowired
    private ServicePet servicePet;

    // Endpoint para listar todos os pets de uma ONG específica
    @GetMapping("/ong/{ongId}")
    public List<Pets> listarPetsPorOng(@PathVariable Long ongId) {
        return servicePet.listarPetsPorOng(ongId);
    }

    // Endpoint para buscar um pet por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pets> buscarPetPorId(@PathVariable Long id) {
        Pets pet = servicePet.buscarPetPorId(id);
        return ResponseEntity.ok(pet);
    }

    // Endpoint para adicionar um novo pet a uma ONG
    @PostMapping("/ong/{ongId}")
    public ResponseEntity<Pets> adicionarPet(@RequestBody Pets pet) {
        Pets novoPet = servicePet.salvarPet(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPet);
    }

    // Endpoint para atualizar um pet existente
    @PutMapping("/{id}")
    public ResponseEntity<Pets> atualizarPet(@PathVariable Long id, @RequestBody Pets petAtualizado) {
        Pets petAtualizadoResponse = servicePet.atualizarPet(id, petAtualizado);
        return ResponseEntity.ok(petAtualizadoResponse);
    }

    // Endpoint para deletar um pet
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPet(@PathVariable Long id) {
        servicePet.deletarPet(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{petId}/ong")
    public ResponseEntity<Ong> buscarOngPorPet(@PathVariable Long petId) {
        Ong ong = servicePet.buscarOngPorPet(petId);
        return ResponseEntity.ok(ong);
    }
}
