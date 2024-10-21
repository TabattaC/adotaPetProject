package com.adocaoProject.controller;

import com.adocaoProject.model.Ong;
 import com.adocaoProject.service.ServiceOng;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ong")
public class ControlerOng {
    @Autowired
    private ServiceOng serviceOng;
    @GetMapping("/listar_Ongs")
    @Operation(summary = "Listar Ongs", description = "Retorna uma lista de ongs cadastrada no banco de dados")
    public List<Ong> listarOngs() {
        return serviceOng.listarOngs();
    }

    // Endpoint para buscar uma ONG pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Ong> buscarOngPorId(@PathVariable Long id) {
        Ong ong = serviceOng.buscarOngPorId(id);
        return ResponseEntity.ok(ong);
    }

    // Endpoint para buscar ONGs pelo nome
    @GetMapping("/buscar")
    public List<Ong> buscarOngPorNome(@RequestParam String nome) {
        return serviceOng.buscarOngPorNome(nome);
    }

    // Endpoint para criar uma nova ONG
    @PostMapping("/save")
    public ResponseEntity<Ong> criarOng(@RequestBody Ong ong) {
        Ong novaOng = serviceOng.salvarOng(ong);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaOng);
    }

    // Endpoint para atualizar uma ONG existente
    @PutMapping("/{id}")
    public ResponseEntity<Ong> atualizarOng(@PathVariable Long id, @RequestBody Ong ongAtualizada) {
        Ong ongAtualizadaResponse = serviceOng.atualizarOng(id, ongAtualizada);
        return ResponseEntity.ok(ongAtualizadaResponse);
    }

    // Endpoint para deletar uma ONG
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOng(@PathVariable Long id) {
        serviceOng.deletarOng(id);
        return ResponseEntity.noContent().build();
    }
}
